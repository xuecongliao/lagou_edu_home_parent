package com.lagou.service.impl;

import com.lagou.dao.CourseMapper;
import com.lagou.domain.Course;
import com.lagou.domain.CourseVO;
import com.lagou.domain.Teacher;
import com.lagou.service.CourseService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;


@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> findCourseByCondition(CourseVO courseVO) {
        List<Course> list = courseMapper.findCourseByCondition(courseVO);
        return list;
    }



   @Override
   public void saveCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException {

       //封装课程信息
       Course course = new Course();

       org.apache.commons.beanutils.BeanUtils.copyProperties(course,courseVO);

       // 补全课程信息
       Date date = new Date();
       course.setCreateTime(date);
       course.setUpdateTime(date);

       //保存课程
       courseMapper.saveCourse(course);

       // 获取新插入数据的id值
       int id = course.getId();
       System.out.println(id);

       // 封装讲师信息
       Teacher teacher = new Teacher();
       BeanUtils.copyProperties(teacher,courseVO);

       // 补全讲师信息
       teacher.setCreateTime(date);
       teacher.setUpdateTime(date);
       teacher.setIsDel(0);
       teacher.setCourseId(id);

       System.out.println(course);
       System.out.println(teacher);
       System.out.println(courseVO);

       //保存讲师信息
       courseMapper.saveTeacher(teacher);


   }

    @Override
    public CourseVO findCourseById(Integer id) {
        return courseMapper.findCourseById(id);
    }

    @Override
    public void updateCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException {
        //1.封装课程信息
        Course course = new Course();
        //把前台页面传入的coursevo对象中的course相关参数封装到course实体中
        BeanUtils.copyProperties(course,courseVO);
        //补全课程信息
        Date date = new Date();
        course.setUpdateTime(date);
        //更新课程信息
        courseMapper.updateCourse(course);

        //2.封装讲师信息
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacher,courseVO);
        //补全信息
        teacher.setId(course.getId());
        System.out.println(teacher.getCourseId());
        teacher.setUpdateTime(date);
        //更新讲师信息
        courseMapper.updateTeacher(teacher);
    }

    @Override
    public void updateCourseStatus(int id, int status) {
        //1.封装数据
        Course course = new Course();
        course.setId(id);
        course.setStatus(status);
        course.setUpdateTime(new Date());
        //调用mapper
        courseMapper.updateCourseStatus(course);
    }
}
