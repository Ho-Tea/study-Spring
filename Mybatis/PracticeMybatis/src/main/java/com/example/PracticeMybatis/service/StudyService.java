package com.example.PracticeMybatis.service;

import com.example.PracticeMybatis.dao.StudyDao;

import com.example.PracticeMybatis.vo.StudyVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class StudyService {
    private final StudyDao studyDao;

    public List<StudyVo> doStudyList(){
        List<StudyVo> list =  studyDao.doStudyList();
        list = studyDao.doStudyList();
        return list;
    }

    public StudyVo doStudyListOne(String id){
        StudyVo studyVo = new StudyVo();
        studyVo = studyDao.doStudyListOne(id);
        return studyVo;
    }

    public int doStudyUpdate(StudyVo studyVo){
        return studyDao.doStudyUpdate(studyVo);
    }

    public int doStudyDel(String strkeyId) {
        return studyDao.doStudyDelete(strkeyId);
    }

    public int doStudyIns(StudyVo studyVo) {
        return studyDao.doStudyIns(studyVo);
    }
}
