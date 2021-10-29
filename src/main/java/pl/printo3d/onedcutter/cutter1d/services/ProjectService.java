package pl.printo3d.onedcutter.cutter1d.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import pl.printo3d.onedcutter.cutter1d.models.project.ProjectModel;
import pl.printo3d.onedcutter.cutter1d.models.user.UserModel;
import pl.printo3d.onedcutter.cutter1d.repo.ProjectRepository;

@Service
public class ProjectService {

    private final ResolveService cutService;
    private final ResultService resultService;
    private final UserService userService;
    private final ProjectRepository projectRepository;

    public ProjectService(ResolveService cutService,ResultService resultService,UserService userService, ProjectRepository projectRepository){
        this.cutService = cutService;
        this.resultService = resultService;
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
        userModel.getSavedOrderModels().get(userModel.getActiveOrderId()).getCutList().clear();
        userModel.getSavedOrderModels().get(userModel.getActiveOrderId()).getCutList().addAll(incomingProject.getCutList());

        userModel.getSavedOrderModels().get(userModel.getActiveOrderId()).getStockList().clear();
        userModel.getSavedOrderModels().get(userModel.getActiveOrderId()).getStockList().addAll(incomingProject.getStockList());

        incomingProject.getCutOptions().setId(userModel.getSavedOrderModels().get(userModel.getActiveOrderId()).getCutOptions().getId());// ID odczytaj i przypisz, bo w orderModel jeszcze nie ma..
        userModel.getSavedOrderModels().get(userModel.getActiveOrderId()).setCutOptions(incomingProject.getCutOptions());

        userModel.getSavedOrderModels().get(userModel.getActiveOrderId()).setProjectName(incomingProject.getProjectName());
        userModel.getSavedOrderModels().get(userModel.getActiveOrderId()).setProjectModified(LocalDateTime.now());

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
        userModel.getActiveOrderModel().getCutList().clear();
        userModel.getActiveOrderModel().getCutList().addAll(incomingProject.getCutList());

        userModel.getActiveOrderModel().getStockList().clear();
        userModel.getActiveOrderModel().getStockList().addAll(incomingProject.getStockList());

        incomingProject.getCutOptions().setId(userModel.getActiveOrderModel().getCutOptions().getId());// ID odczytaj i przypisz, bo w orderModel jeszcze nie ma..
        userModel.getActiveOrderModel().setCutOptions(incomingProject.getCutOptions());

        userModel.getActiveOrderModel().setProjectName(incomingProject.getProjectName());
        userModel.getActiveOrderModel().setProjectModified(LocalDateTime.now());

        userService.saveUserEntity(userModel);
        /** END ZAPIS DO BAZY */
        return userModel.getActiveOrderModel();
    }


    public ProjectModel addNewProject(ProjectModel incomingProject){
        UserModel userModel = (UserModel) userService.loadUserByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() );

        if(userModel.getNumberOfSavedItems() < 5){
            userModel.getSavedOrderModels().add(incomingProject);
            userModel.setActiveOrderModel(incomingProject);
            userModel.setNumberOfSavedItems(userModel.getSavedOrderModels().size());

            userService.saveUserEntity(userModel);

            return userModel.getActiveOrderModel();
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
            projectRepository.deleteById(id);
            
            userModel.setNumberOfSavedItems(userModel.getSavedOrderModels().size());
            userService.saveUserEntity(userModel);
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
        return userModel.getSavedOrderModels();
    }
}
