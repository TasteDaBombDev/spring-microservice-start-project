package com.postme.testService.controllers;

import com.postme.coreAuthorisation.models.enums.UserRolesEnum;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/v1/test/admin/{name}")
    @Secured(UserRolesEnum.Constants.ROLE_ADMIN_VALUE)
    public String helloAdmin(@PathVariable String name){
        return "hello admin: " + name;
    }

    @GetMapping("/v1/test/user/{name}")
    @Secured(UserRolesEnum.Constants.ROLE_ADMIN_VALUE)
    public String helloUser(@PathVariable String name){
        return "hello user: " + name;
    }

    @GetMapping("/v1/test/public")
    public String hello2() {
        return "hello2";
    }

}
