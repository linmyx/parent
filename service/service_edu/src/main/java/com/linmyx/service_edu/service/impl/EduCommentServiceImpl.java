package com.linmyx.service_edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linmyx.commonUtils.JwtUtils;
import com.linmyx.commonUtils.Result;
import com.linmyx.service_edu.client.UcenterClient;
import com.linmyx.service_edu.entity.EduComment;
import com.linmyx.service_edu.entity.UcenterMember;
import com.linmyx.service_edu.mapper.EduCommentMapper;
import com.linmyx.service_edu.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-13
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Autowired
    private UcenterClient ucenterClient;

    /**
     *添加用户评论
     * @param comment
     * @param request
     * @return
     */
    @Override
    public Result addComment(EduComment comment, HttpServletRequest request) {
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(userId)){
            return Result.error("请登录之后，在评论");
        }
        comment.setMemberId(userId);
        UcenterMember userInfo = ucenterClient.getUserInfo(userId);
        comment.setNickname(userInfo.getNickname());
        comment.setAvatar(userInfo.getAvatar());
        boolean save = save(comment);
        if (!save) {
            return Result.error("评论失败，请重新评论！！");
        }
        return Result.ok();
    }

    /**
     * 根据课程id获取评论
     * @param page
     * @param limit
     * @param courseId
     * @return
     */
    @Override
    public Result getCommentPage(long page, long limit, String courseId) {
        Page<EduComment> pageInfo=new Page<>(page,limit);
        QueryWrapper<EduComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        page(pageInfo,queryWrapper);

        List<EduComment> commentList = pageInfo.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", pageInfo.getCurrent());
        map.put("pages", pageInfo.getPages());
        map.put("size", pageInfo.getSize());
        map.put("total", pageInfo.getTotal());
        map.put("hasNext", pageInfo.hasNext());
        map.put("hasPrevious", pageInfo.hasPrevious());

        return Result.ok(map);
    }
}
