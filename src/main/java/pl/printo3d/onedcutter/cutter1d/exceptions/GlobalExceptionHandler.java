package pl.printo3d.onedcutter.cutter1d.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
