package uz.optimit.railway.entity;

import uz.optimit.railway.enums.Permission;

import java.util.Arrays;
import java.util.List;

public class RolePermissions {

    public static final List<Permission> ONLY_VIEW = Arrays
            .asList(Permission.GET_USER, Permission.GET_DEVICE,
                    Permission.GET_ROLE, Permission.GET_ENTERPRISE,
                    Permission.GET_JOB, Permission.GET_CATEGORY,
                    Permission.GET_EMPLOYEE, Permission.GET_LEVEL_CROSSING,
                    Permission.GET_MTU, Permission.GET_PEREGON, Permission.GET_PLOT, Permission.GET_STATION);

    public static final List<Permission> SHNS = Arrays
            .asList(Permission.GET_USER, Permission.GET_DEVICE,
                    Permission.GET_ROLE, Permission.GET_ENTERPRISE,
                    Permission.GET_JOB, Permission.GET_CATEGORY,
                    Permission.GET_EMPLOYEE, Permission.GET_LEVEL_CROSSING,
                    Permission.GET_MTU, Permission.GET_PEREGON, Permission.GET_PLOT, Permission.GET_STATION,

                    Permission.ADD_JOB, Permission.GET_JOB,
                    Permission.DONE_JOB, Permission.PAUSE_JOB);


    public static final List<Permission> SHN_SHSM = Arrays
            .asList(Permission.GET_USER, Permission.GET_DEVICE,
                    Permission.GET_ROLE, Permission.GET_ENTERPRISE,
                    Permission.GET_JOB, Permission.GET_CATEGORY,
                    Permission.GET_EMPLOYEE, Permission.GET_LEVEL_CROSSING,
                    Permission.GET_MTU, Permission.GET_PEREGON, Permission.GET_PLOT, Permission.GET_STATION,

                    Permission.GET_JOB,
                    Permission.DONE_JOB, Permission.PAUSE_JOB);

    public static final List<Permission> SHCHD = Arrays
            .asList(Permission.GET_USER, Permission.GET_DEVICE,
                    Permission.GET_ROLE, Permission.GET_ENTERPRISE,
                    Permission.GET_JOB, Permission.GET_CATEGORY,
                    Permission.GET_EMPLOYEE, Permission.GET_LEVEL_CROSSING,
                    Permission.GET_MTU, Permission.GET_PEREGON, Permission.GET_PLOT, Permission.GET_STATION,

                    Permission.CONFIRM_JOB, Permission.GET_JOB);


}
