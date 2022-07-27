package com.postme.testService;

import com.postme.authService.models.enums.UserRolesEnum;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/v1/test/{name}")
    @Secured(UserRolesEnum.Constants.ROLE_ADMIN_VALUE)
    public String hello(@PathVariable String name){
        return "hello " + name;
    }

}
