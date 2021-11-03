package pl.printo3d.onedcutter.cutter1d.services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import pl.printo3d.onedcutter.cutter1d.models.project.CutModel;
import pl.printo3d.onedcutter.cutter1d.models.project.CutOptions;
import pl.printo3d.onedcutter.cutter1d.models.project.ProjectModel;
import pl.printo3d.onedcutter.cutter1d.models.project.StockModel;
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
        /** ZAPIS DO BAZY */
        UserModel userModel = (UserModel) userService.loadUserByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() );

        // najpierw czyscimy liste, aby w DB pozbyc sie osieroconych wpisow
        // dlatego getcutlist.addAll! zamiast setCutlist.add!
        userModel.getsavedProjectModels().get(userModel.getactiveProjectId()).getCutList().clear();
        userModel.getsavedProjectModels().get(userModel.getactiveProjectId()).getCutList().addAll(incomingProject.getCutList());

        userModel.getsavedProjectModels().get(userModel.getactiveProjectId()).getStockList().clear();
        userModel.getsavedProjectModels().get(userModel.getactiveProjectId()).getStockList().addAll(incomingProject.getStockList());

        incomingProject.getCutOptions().setId(userModel.getsavedProjectModels().get(userModel.getactiveProjectId()).getCutOptions().getId());// ID odczytaj i przypisz, bo w orderModel jeszcze nie ma..
        userModel.getsavedProjectModels().get(userModel.getactiveProjectId()).setCutOptions(incomingProject.getCutOptions());

        userModel.getsavedProjectModels().get(userModel.getactiveProjectId()).setProjectName(incomingProject.getProjectName());
        userModel.getsavedProjectModels().get(userModel.getactiveProjectId()).setProjectModified(LocalDateTime.now());

        userService.saveUserEntity(userModel);
        /** END ZAPIS DO BAZY */
    }

    /**
     * Zapisuje do bazy wyłącznie jeden bierzący order - nie zapisuje ich do slotów pamieci usera
     * @param ProjectModel
     */
    public ProjectModel saveActiveOrder(ProjectModel incomingProject) {
        /** ZAPIS DO BAZY */
        UserModel userModel = (UserModel) userService.loadUserByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() );

        // najpierw czyscimy liste, aby w DB pozbyc sie osieroconych wpisow
        // dlatego getcutlist.addAll! zamiast setCutlist.add!
        userModel.getactiveProjectModel().getCutList().clear();
        userModel.getactiveProjectModel().getCutList().addAll(incomingProject.getCutList());

        userModel.getactiveProjectModel().getStockList().clear();
        userModel.getactiveProjectModel().getStockList().addAll(incomingProject.getStockList());

        incomingProject.getCutOptions().setId(userModel.getactiveProjectModel().getCutOptions().getId());// ID odczytaj i przypisz, bo w orderModel jeszcze nie ma..
        userModel.getactiveProjectModel().setCutOptions(incomingProject.getCutOptions());

        userModel.getactiveProjectModel().setProjectName(incomingProject.getProjectName());
        userModel.getactiveProjectModel().setProjectModified(LocalDateTime.now());

        userService.saveUserEntity(userModel);
        /** END ZAPIS DO BAZY */
        return userModel.getactiveProjectModel();
    }


    public ProjectModel addNewProject(){
        UserModel userModel = (UserModel) userService.loadUserByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() );
        ProjectModel incomingProject = new ProjectModel();

        if(userModel.getNumberOfSavedItems() < 5){
            incomingProject.setCutList(Arrays.asList(new CutModel("220", "5"), new CutModel("260", "5")));
            incomingProject.setStockList(Arrays.asList(new StockModel("0", "1000", "6", "0"), new StockModel("1", "1000", "5", "0")));
            incomingProject.setCutOptions(new CutOptions(false, 0d, false, false, 1000));
            incomingProject.setProjectName("New project name");
            incomingProject.setProjectCreated(LocalDateTime.now());
            incomingProject.setProjectModified(LocalDateTime.now());
    
            userModel.getsavedProjectModels().add(incomingProject);
            userModel.setactiveProjectModel(incomingProject);
            userModel.setNumberOfSavedItems(userModel.getsavedProjectModels().size());

            userService.saveUserEntity(userModel);

            return userModel.getactiveProjectModel();
        }
        else throw new RuntimeException("There's no more space for this user");
    }

    public ProjectModel editProject(Long id, ProjectModel incomingProject) {
        UserModel userModel = (UserModel) userService.loadUserByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() );

        if( projectRepository.getByIdAndUserId(id, userModel.getId()) != null ){
            ProjectModel project = projectRepository.getByIdAndUserId(id, userModel.getId());
            project.getCutList().clear();
            project.getCutList().addAll(incomingProject.getCutList());

            project.getStockList().clear();
            project.getStockList().addAll(incomingProject.getStockList());

            project.setCutOptions(incomingProject.getCutOptions());

            project.setProjectName(incomingProject.getProjectName());
            project.setProjectModified(LocalDateTime.now());

            projectRepository.save(project);

            return project;
        }
        else throw new RuntimeException("User or model not found!");
    }

    public void removeOrderModel(Long id){
        UserModel userModel = (UserModel) userService.loadUserByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() );
        if(projectRepository.findByIdAndUserId(id, userModel.getId()) != null){
            if(userModel.getsavedProjectModels().size() > 1){
                userModel.setactiveProjectId( null );
                userModel.setactiveProjectModel( null );

                userModel.getsavedProjectModels().remove(userModel.getsavedProjectModels().stream()
                    .filter(e -> e.getId() == Long.valueOf(id))
                    .collect(Collectors.toList())
                    .get(0));
                
                projectRepository.deleteById(id);
    
                int index = userModel.getsavedProjectModels().size()-1;
                if(index<0) index = 0;
    
                userModel.setactiveProjectId( userModel.getsavedProjectModels().get(index).getId().intValue() );
                userModel.setactiveProjectModel( userModel.getsavedProjectModels().get(index) );
                userModel.setNumberOfSavedItems(userModel.getsavedProjectModels().size());
    
                userService.saveUserEntity(userModel);
            } else throw new RuntimeException("Can't remove all projects, must be at least one!");
        } else throw new RuntimeException("No user or ordermodel");
        
    }

    public ProjectModel getProject(Long projectId) {
        UserModel userModel = (UserModel) userService.loadUserByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() );
        if(projectRepository.findProjectModelByIdAndUserId(projectId, userModel.getId()) != null){
            return projectRepository.findProjectModelById(projectId);
        } else throw new RuntimeException("No project or user found with this ID!");
    }


    public List<ProjectModel> getAllUserProjects() {
        UserModel userModel = (UserModel) userService.loadUserByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() );
        return userModel.getsavedProjectModels();
    }
}
