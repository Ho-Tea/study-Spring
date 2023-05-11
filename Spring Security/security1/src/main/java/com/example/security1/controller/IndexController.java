package com.example.security1.controller;


import com.example.security1.model.User;
import com.example.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequiredArgsConstructor
public class IndexController {

    private final UserRepository userRepository;

    private final PasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/")
    public String hello(){
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user(){
        return "user";
    }


    @GetMapping("/manager")
    public @ResponseBody String manager(){
        log.info("들어오기는 하나");
        return "manager";
    }

    @GetMapping("/admin")
    @Secured("ROLE_ADMIN")
    public @ResponseBody String admin(){
        return "admin";
    }

    // 스프링 시큐리티 해당 주소를 밑의 메서드가 낚아 챈다 - (SecurityConfig 파일 생성 후 스프링 시큐리티 기본 로그인 동작이 작동 안함)
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user){
        log.info(user.getUsername());
        user.setRole("ROLE_ADMIN");
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user);
        return "redirect:/";
    }

}
