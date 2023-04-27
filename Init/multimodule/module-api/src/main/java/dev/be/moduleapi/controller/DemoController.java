package dev.be.moduleapi.controller;


import dev.be.moduleapi.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DemoController {

    private final DemoService demoService;


    @GetMapping("/save")
    public String save(){
        return demoService.save();
    }
    @GetMapping("/find")
    public String find(){
        return demoService.find();
    }

    @GetMapping("/exception")
    public String exception(){
        return demoService.exception();
    }

}
