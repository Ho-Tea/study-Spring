package dev.be.feign.controller;


import dev.be.feign.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DemoController {
    private final DemoService demoservice;

    @GetMapping("/get")
    public String getController(){
        return demoservice.get();

    }
    @PostMapping("/post")
    public String postController(){
        return demoservice.post();

    }

    @GetMapping("/error")
    public String errorDecoderController(){
        return demoservice.errorDecoder();
    }
}
