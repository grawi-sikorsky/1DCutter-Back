package pl.printo3d.onedcutter.cutter1d.exceptions.project;

public class ProjectDoesntExistException extends RuntimeException{

    public ProjectDoesntExistException(String message) {
        super(message);
    }
}
