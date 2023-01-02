package pl.printo3d.onedcutter.cutter1d.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pl.printo3d.onedcutter.cutter1d.exceptions.project.NoProjectStorageSpaceException;
import pl.printo3d.onedcutter.cutter1d.exceptions.project.ProjectDoesntExistException;
import pl.printo3d.onedcutter.cutter1d.exceptions.user.RegisterErrorException;
import pl.printo3d.onedcutter.cutter1d.exceptions.user.UserDoesntExistsException;
import pl.printo3d.onedcutter.cutter1d.exceptions.user.UserExistsException;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(BadCredentialsException exception){
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<String> handleUserExists(UserExistsException exception){
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserDoesntExistsException.class)
    public ResponseEntity<String> handleUserExists(UserDoesntExistsException exception){
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(RegisterErrorException.class)
    public ResponseEntity<String> handleRegisterError(RegisterErrorException exception){
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ProjectDoesntExistException.class)
    public ResponseEntity<String> handleRegisterError(ProjectDoesntExistException exception){
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NoProjectStorageSpaceException.class)
    public ResponseEntity<String> handleRegisterError(NoProjectStorageSpaceException exception){
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }
}
