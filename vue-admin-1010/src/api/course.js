import request from '@/utils/request'

export default{
    addCourseInfo(courseInfo){
        return request({
            url: `/eduservice/course/addCourseInfo`,
            method: 'post',
            data:courseInfo
        })
    },
    getListTeacher(){
        return request({
            url: `/eduservice/teacher/findAll`,
            method: 'get',
        }) 
    },
    getCourseById(id){
        return request({
            url: `/eduservice/course/getCourseInfo/${id}`,
            method: 'get',
        }) 
    },
    //修改课程信息
    updateCourse(courseVo){ 
        return request({
            url: `/eduservice/course/updateCourseInfo`,
            method: 'post',
            data: courseVo
        }) 
    },
    getCourseList(page,limit,courseQuery){
        return request({
            url: `/eduservice/course/getCourseListPage/${page}/${limit}`,
            method: 'post',
            data : courseQuery
        }) 
    },
    deleteCourseById(courseId){
        return request({
            url: `/eduservice/course/deleteCourseById/${courseId}`,
            method: 'delete',
        }) 
    },
    //根据id修改课程状态
    updatedStu(courseId){
        return request({
            url: `/eduservice/course/updateCourseStatus/${courseId}`,
            method: 'put',
        }) 
    }
}