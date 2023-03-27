package com.example.PracticeMybatis.controller;


import com.example.PracticeMybatis.service.StudyService;
import com.example.PracticeMybatis.vo.StudyVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/study_reg")
@RequiredArgsConstructor
@Slf4j
public class StudyRegCon {

    private final StudyService studyService;


    //수정
    @GetMapping("/modify")
    public String doMod(HttpServletRequest request, Model model){
        String strKeyId = request.getParameter("id");
        System.out.println(strKeyId);
        StudyVo studyVo = new StudyVo();
        studyVo = studyService.doStudyListOne(strKeyId);
        model.addAttribute("vo_study", studyVo);
        return "/study/study_mod";
    }

    @PostMapping("/modify_exe")
    public String doModExe(@ModelAttribute StudyVo studyVo){
        int intI = studyService.doStudyUpdate(studyVo);
        return "redirect:/study_reg";
        //jsp가 아닌 controller상 매핑되어있는 url로 뿌려주게 된다
    }


    //삭제
    @GetMapping("/delete")
    public String doDel(@RequestParam(value = "id", defaultValue = "--")String strkeyId){
        int intI = studyService.doStudyDel(strkeyId);
        log.info("intI" + intI);
        return "redirect:/study_reg";
    }
    //입력
    @GetMapping("/insert")
    public String doIns(){
        return "/study/study_ins";
    }

    @PostMapping("/ins_exe")
    public String doInsExe(@ModelAttribute StudyVo studyVo){
        int intI = studyService.doStudyIns(studyVo);
        return "redirect:/study_reg";
        //jsp가 아닌 controller상 매핑되어있는 url로 뿌려주게 된다
    }
}
