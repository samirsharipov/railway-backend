package uz.optimit.railway.exception;


import lombok.Getter;
@Getter
public class ForbiddenException extends RuntimeException {

    private final String permission;

    public ForbiddenException(String permission, String message) {
        super(message);
        this.permission = permission;
    }

}