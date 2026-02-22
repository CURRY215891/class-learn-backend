package com.xd.xdclasslearnbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.xd.xdclasslearnbackend.commen.PageResult;
import com.xd.xdclasslearnbackend.dto.CourseDTO;
import com.xd.xdclasslearnbackend.entity.*;
import com.xd.xdclasslearnbackend.mapper.*;
import com.xd.xdclasslearnbackend.service.CourseService;
import com.xd.xdclasslearnbackend.vo.ChapterVO;
import com.xd.xdclasslearnbackend.vo.CourseDetailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 康志阳
 * @date 2026/2/18 19:25
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;


    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private VideoResourceMapper videoResourceMapper;

    @Autowired
    private LearningObjectiveMapper learningObjectiveMapper;

    @Autowired
    private TargetAudienceMapper targetAudienceMapper;

    @Override
    public PageResult<Course> getPage(CourseDTO query) {
        Course course = new Course();

        if (query != null) {
            course.setTitle(query.getTitle());
            course.setDescription(query.getDescription());
            course.setTeacherName(query.getTeacherName());
        }
        if (query.getTypeId() != null) {
            CourseType courseType = new CourseType();
            courseType.setId(query.getTypeId());
            course.setType(courseType);
        }
        if (query.getCreatorId() != null) {
            User creator = new User();
            creator.setId(query.getCreatorId());
            course.setCreator(creator);
        }
        //获取总数
        int totalCount = courseMapper.count(course,query.getMinRating(),query.getMinStudentCount());
        //计算偏移量
        int offset = (query.getPageNum()-1)*query.getPageSize();
        //获取分页数据
        List<Course> list = courseMapper.findListByPage(course,offset,query.getPageSize());
        //封装分页结果
        PageResult<Course> pageResult = new PageResult<>(list,totalCount,query.getPageNum(),query.getPageSize());

        return pageResult;
    }

    @Override
    public CourseDetailVO getCourseDetail(Long id) {
        Course course = courseMapper.selectById(id);
        if (course == null) {
            return null;
        }
        CourseDetailVO detailVO = new CourseDetailVO();
        BeanUtils.copyProperties(course, detailVO);
        // 补充 CourseVO 需要的额外字段
        detailVO.setAuthor(course.getTeacherName());
        if(course.getType()!=null){
            detailVO.setType(course.getType().getName());
        }
        if(course.getRating()!=null){
            detailVO.setRating(course.getRating().doubleValue());
        }
        if(course.getStudentCount()!=null){
            detailVO.setStudents(course.getStudentCount().intValue());
        }
        detailVO.setCover(course.getCoverImage());
        detailVO.setCreateTime(course.getCreatedTime());
        detailVO.setDetail(course.getDescription());
        // 3. 查询学习目标
        LambdaQueryWrapper<LearningObjective> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LearningObjective::getCourseId,id);
        List<LearningObjective> objectives = learningObjectiveMapper.selectList(queryWrapper);
        if(objectives!=null&&!objectives.isEmpty()){
            detailVO.setLearningObjectives(objectives.stream()
                    .map(LearningObjective :: getContent)
                    .collect(Collectors.toList()));
        }

        //4.查询适合人群
        LambdaQueryWrapper<TargetAudience> audienceQueryWrapper = new LambdaQueryWrapper<>();
        audienceQueryWrapper.eq(TargetAudience::getCourseId,id);
        List<TargetAudience> audiences = targetAudienceMapper.selectList(audienceQueryWrapper);
        if(audiences!=null&&!audiences.isEmpty()){
            detailVO.setTargetAudiences(audiences.stream()
                    .map(TargetAudience :: getContent)
                    .collect(Collectors.toList()));
        }

        return detailVO;
    }

    @Override
    public List<ChapterVO> getChapters(Long courseId) {
        // 1. 查询所有章节
        LambdaQueryWrapper<Chapter> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Chapter::getCourseId,courseId);
        queryWrapper.orderByAsc(Chapter::getSortOrder);
        List<Chapter> chapters = chapterMapper.selectList(queryWrapper);
        if(chapters == null||chapters.isEmpty()){
            return new ArrayList<>();
        }
        // 2. 查询该课程下所有资源
        LambdaQueryWrapper<VideoResource> ResourceWrapper = new LambdaQueryWrapper<>();
        ResourceWrapper.eq(VideoResource::getCourseId,courseId);
        ResourceWrapper.orderByAsc(VideoResource::getSortOrder);
        List<VideoResource> resources = videoResourceMapper.selectList(ResourceWrapper);
        // 3. 将资源按 chapterId 分组
        Map<Long, List<VideoResource>> resourceMap = resources.stream()
                .filter(r -> r.getChapterId() != null)
                .collect(Collectors.groupingBy(VideoResource::getChapterId));
        return List.of();
    }
}
