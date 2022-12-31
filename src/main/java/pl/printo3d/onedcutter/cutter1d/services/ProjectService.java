package pl.printo3d.onedcutter.cutter1d.services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import pl.printo3d.onedcutter.cutter1d.models.project.CutUnit;
import pl.printo3d.onedcutter.cutter1d.exceptions.project.ProjectDoesntExistException;
import pl.printo3d.onedcutter.cutter1d.exceptions.services.NoProjectStorageSpaceException;
import pl.printo3d.onedcutter.cutter1d.models.project.CutOptions;
import pl.printo3d.onedcutter.cutter1d.models.project.ProjectModel;
import pl.printo3d.onedcutter.cutter1d.models.project.StockUnit;
import pl.printo3d.onedcutter.cutter1d.models.user.UserModel;
import pl.printo3d.onedcutter.cutter1d.repo.ProjectRepository;

@Service
public class ProjectService {

    private final UserService userService;
    private final ProjectRepository projectRepository;

    public ProjectService(UserService userService, ProjectRepository projectRepository){
        this.userService = userService;
        this.projectRepository = projectRepository;
    }


    /**
     * Zapisuje do bazy Order, który ma trafic w "pamiec stala" tj. wolne sloty kazdego usera
     * @param ProjectModel
     */
    public void saveUserOrders(ProjectModel incomingProject) {

        UserModel userModel = (UserModel) userService.loadUserByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() );

        // najpierw czyscimy liste, aby w DB pozbyc sie osieroconych wpisow
        // dlatego getcutlist.addAll! zamiast setCutlist.add!
        userModel.getSavedProjectModels().get(userModel.getActiveProjectId()).getCutList().clear();
        userModel.getSavedProjectModels().get(userModel.getActiveProjectId()).getCutList().addAll(incomingProject.getCutList());

        userModel.getSavedProjectModels().get(userModel.getActiveProjectId()).getStockList().clear();
        userModel.getSavedProjectModels().get(userModel.getActiveProjectId()).getStockList().addAll(incomingProject.getStockList());

        incomingProject.getCutOptions().setId(userModel.getSavedProjectModels().get(userModel.getActiveProjectId()).getCutOptions().getId());// ID odczytaj i przypisz, bo w orderModel jeszcze nie ma..
        userModel.getSavedProjectModels().get(userModel.getActiveProjectId()).setCutOptions(incomingProject.getCutOptions());

        userModel.getSavedProjectModels().get(userModel.getActiveProjectId()).setProjectName(incomingProject.getProjectName());
        userModel.getSavedProjectModels().get(userModel.getActiveProjectId()).setProjectModified(LocalDateTime.now());

        userService.saveUserEntity(userModel);
    }

    /**
     * Zapisuje do bazy wyłącznie jeden bierzący order - nie zapisuje ich do slotów pamieci usera
     * @param ProjectModel
     */
    public ProjectModel saveActiveOrder(ProjectModel incomingProject) {

        UserModel userModel = (UserModel) userService.loadUserByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() );

        // najpierw czyscimy liste, aby w DB pozbyc sie osieroconych wpisow
        // dlatego getcutlist.addAll! zamiast setCutlist.add!
        userModel.getActiveProjectModel().getCutList().clear();
        userModel.getActiveProjectModel().getCutList().addAll(incomingProject.getCutList());

        userModel.getActiveProjectModel().getStockList().clear();
        userModel.getActiveProjectModel().getStockList().addAll(incomingProject.getStockList());

        incomingProject.getCutOptions().setId(userModel.getActiveProjectModel().getCutOptions().getId());// ID odczytaj i przypisz, bo w orderModel jeszcze nie ma..
        userModel.getActiveProjectModel().setCutOptions(incomingProject.getCutOptions());

        userModel.getActiveProjectModel().setProjectName(incomingProject.getProjectName());
        userModel.getActiveProjectModel().setProjectModified(LocalDateTime.now());

        userModel.getActiveProjectModel().setProjectResults(incomingProject.getProjectResults());

        userService.saveUserEntity(userModel);

        return userModel.getActiveProjectModel();
    }


    public ProjectModel addNewProject(){
        UserModel userModel = (UserModel) userService.loadUserByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() );
        ProjectModel incomingProject = new ProjectModel();

        if(userModel.getNumberOfSavedItems() < 5){
            incomingProject.setCutList(Arrays.asList(new CutUnit("220", "5"), new CutUnit("260", "5")));
            incomingProject.setStockList(Arrays.asList(new StockUnit("0", "1000", "6", "0"), new StockUnit("1", "1000", "5", "0")));
            incomingProject.setCutOptions(new CutOptions(false, 0d, false, false, 1000));
            incomingProject.setProjectName("New project name");
            incomingProject.setProjectCreated(LocalDateTime.now());
            incomingProject.setProjectModified(LocalDateTime.now());
    
            userModel.getSavedProjectModels().add(incomingProject);
            userModel.setActiveProjectModel(incomingProject);
            userModel.setNumberOfSavedItems(userModel.getSavedProjectModels().size());

            userService.saveUserEntity(userModel);

            return userModel.getActiveProjectModel();
        }
        else throw new NoProjectStorageSpaceException("There's no more free project storage for this user.");
    }


    public ProjectModel editProject(Long projectId, ProjectModel incomingProject) {
        UserModel userModel = (UserModel) userService.loadUserByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() );

        if( projectRepository.getByIdAndUserId(projectId, userModel.getId()) != null ){
            ProjectModel project = projectRepository.getByIdAndUserId(projectId, userModel.getId());
            project.getCutList().clear();
            project.getCutList().addAll(incomingProject.getCutList());

            project.getStockList().clear();
            project.getStockList().addAll(incomingProject.getStockList());

            project.setCutOptions(incomingProject.getCutOptions());

            project.setProjectName(incomingProject.getProjectName());
            project.setProjectModified(LocalDateTime.now());

            project.setProjectResults(incomingProject.getProjectResults());

            projectRepository.save(project);

            return project;
        }
        else throw new RuntimeException("User or model not found!");
    }


    public void removeOrderModel(Long projectId){
        UserModel userModel = (UserModel) userService.loadUserByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() );
        if(projectRepository.findByIdAndUserId(projectId, userModel.getId()) != null){
            if(userModel.getSavedProjectModels().size() > 1){
                userModel.setActiveProjectId( null );
                userModel.setActiveProjectModel( null );

                userModel.getSavedProjectModels().remove(userModel.getSavedProjectModels().stream()
                    .filter(e -> e.getId() == Long.valueOf(projectId))
                    .collect(Collectors.toList())
                    .get(0));
                
                projectRepository.deleteById(projectId);
    
                int index = userModel.getSavedProjectModels().size()-1;
                if(index<0) index = 0;
    
                userModel.setActiveProjectId( userModel.getSavedProjectModels().get(index).getId().intValue() );
                userModel.setActiveProjectModel( userModel.getSavedProjectModels().get(index) );
                userModel.setNumberOfSavedItems(userModel.getSavedProjectModels().size());
    
                userService.saveUserEntity(userModel);
            } else throw new RuntimeException("Can't remove all projects, must be at least one!");
        } else throw new RuntimeException("No user or ordermodel");
        
    }


    public ProjectModel getProject(Long projectId) {
        UserModel userModel = (UserModel) userService.loadUserByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() );
        if(projectRepository.findProjectModelByIdAndUserId(projectId, userModel.getId()) != null){
            return projectRepository.findProjectModelById(projectId);
        } else throw new ProjectDoesntExistException("No project found for this user!");
    }


    public List<ProjectModel> getAllUserProjects() {
        UserModel userModel = (UserModel) userService.loadUserByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() );
        return userModel.getSavedProjectModels();
    }
}
