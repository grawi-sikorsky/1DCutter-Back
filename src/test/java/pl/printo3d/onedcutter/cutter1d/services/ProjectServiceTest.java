package pl.printo3d.onedcutter.cutter1d.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import pl.printo3d.onedcutter.cutter1d.exceptions.services.NoProjectStorageSpaceException;
import pl.printo3d.onedcutter.cutter1d.models.project.ProjectModel;
import pl.printo3d.onedcutter.cutter1d.models.user.UserModel;
import pl.printo3d.onedcutter.cutter1d.repo.ProjectRepository;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;


    @Test
    void addNewProject_should_addNewProject() {
        UserModel testUser = new UserModel();
        testUser.setUsername("testuser");
        testUser.setNumberOfSavedItems(4);

        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(testUser);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
        when(userService.loadUserByUsername("testuser")).thenReturn(testUser);

        projectService.addNewProject();

        verify(userService, times(1)).saveUserEntity(testUser);
    }

    @Test
    void addNewProject_should_throwStorageException() {
        UserModel testUser = new UserModel();
        testUser.setUsername("testuser");
        testUser.setNumberOfSavedItems(5);

        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(testUser);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
        when(userService.loadUserByUsername("testuser")).thenReturn(testUser);

        assertThrows(NoProjectStorageSpaceException.class, () -> { projectService.addNewProject(); } );
    }

    @Test
    void testEditProject() {

    }

    @Test
    void testGetAllUserProjects() {

    }

    @Test
    void testGetProject() {

    }

    @Test
    void testRemoveOrderModel() {

    }

    @Test
    void testSaveActiveOrder() {

    }

    @Test
    void testSaveUserOrders() {

    }


    ProjectModel setupProject(){
        ProjectModel testProject = new ProjectModel();

        return testProject;
    }
    
}
