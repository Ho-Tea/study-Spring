package com.example.PracticeMybatis.controller;

import com.example.PracticeMybatis.service.StudyService;
import com.example.PracticeMybatis.vo.StudyVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final StudyService studyService;

    @GetMapping("")
    public String doHome(){
        return "/home/home";
    }


    @GetMapping("/study_reg")
    public String doStudy_reg(HttpServletRequest request, Model model){
        List<StudyVo> list = new ArrayList<>();
        list = studyService.doStudyList();
        for(StudyVo map: list){
            log.info(String.valueOf(map.getId()));
            log.info(map.getStudyDay());
            log.info(map.getContents());
            log.info(map.getRegDay());
        }
//        request.setAttribute("list", list);
        model.addAttribute("list" , list);
    return "/home/study_reg";
    }
    
}
