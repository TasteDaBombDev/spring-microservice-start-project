package com.postme.testService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/v1/test/{name}")
    public String hello(@PathVariable String name){
        return "hello " + name;
    }

}
