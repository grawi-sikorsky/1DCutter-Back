package pl.printo3d.onedcutter.cutter1d.exceptions.user;

public class RegisterErrorException extends RuntimeException{

    public RegisterErrorException(String message) {
        super(message);
    }
}