package com.lagou.dao;

import com.lagou.domain.Course;
import com.lagou.domain.CourseSection;

import java.util.List;

public interface CourseContentMapper {
    /**
     * 根据课程id查询关联的章节信息及章节关联的课时信息
     */
    public List<CourseSection> findSectionAndLessonByCourseId(Integer id);

    /**
     * 回显章节对应的课程信息
     * */
    public Course findCourseByCourseId(int courseId);

    /**
     * 保存新增章节
     * */
    public void saveSection(CourseSection section);

    /**
     * 修改章节信息
     * */
    void updateSection(CourseSection courseSection);

    /**
     * 修改章节状态
     */
    public void updateSectionStatus(CourseSection courseSection);




}
