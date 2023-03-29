import request from '@/utils/request'

export default{
    getPublishCourseInfo(courseId){
        return request({
            url: `/eduservice/course/getPublishCourseInfo/${courseId}`,
            method: 'get',
        })
    },
    updateCoursePublish(courseId){
        return request({
            url: `/eduservice/course/updateCoursePublish/${courseId}`,
            method: 'post',
        })
    }
}