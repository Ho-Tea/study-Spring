package com.example.PracticeMybatis.dao;

import com.example.PracticeMybatis.vo.StudyVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface StudyDao {
    //공부기록 전체 리스트
    public List<StudyVo> doStudyList();


    //공부기록 One
    public StudyVo doStudyListOne(String id);

    //공부기록 수정
    public int doStudyUpdate(StudyVo studyVo);

    public int doStudyDelete(String id);

    public int doStudyIns(StudyVo studyVo);
}
