package uz.optimit.railway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.SpringSecurityCoreVersion;

@SpringBootApplication
public class RailwayApplication {

    public static void main(String[] args) {
        SpringApplication.run(RailwayApplication.class, args);
        System.out.println("Spring Security Version: " + SpringSecurityCoreVersion.getVersion());
        System.out.println("Spring Boot Version: " + SpringBootVersion.getVersion());
    }

}