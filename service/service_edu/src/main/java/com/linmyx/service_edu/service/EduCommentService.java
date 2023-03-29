package com.linmyx.service_edu.service;

import com.linmyx.commonUtils.Result;
import com.linmyx.service_edu.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-13
 */
public interface EduCommentService extends IService<EduComment> {
    /**
     * 添加用户评论
     * @param comment
     * @param request
     * @return
     */
    Result addComment(EduComment comment, HttpServletRequest request);

    /**
     * 获取课程评论
     *
     * @param page
     * @param limit
     * @param courseId
     * @return
     */
    Result getCommentPage(long page, long limit, String courseId);
}
