package com.linmyx.service_edu.controller.front;


import com.linmyx.commonUtils.Result;
import com.linmyx.service_edu.entity.EduComment;
import com.linmyx.service_edu.service.EduCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-13
 */
@RestController
@RequestMapping("/eduservice/comment")
//@CrossOrigin
public class EduCommentController {

    @Autowired
    EduCommentService commentService;

    @PostMapping("/addComment")
    public Result addComment(@RequestBody EduComment comment, HttpServletRequest request){

        return commentService.addComment(comment,request);
    }

    @GetMapping("/getCommentPage/{page}/{limit}/{courseId}")
    public Result getCommentPage(@PathVariable long page,@PathVariable long limit,@PathVariable String courseId){
        return commentService.getCommentPage(page,limit,courseId);
    }

}

