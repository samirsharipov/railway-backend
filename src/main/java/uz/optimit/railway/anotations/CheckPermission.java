package uz.optimit.railway.anotations;

import uz.optimit.railway.enums.Permission;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPermission {
    String value();
}