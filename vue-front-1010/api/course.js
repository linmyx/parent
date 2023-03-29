import request from '@/utils/request'

export default {
    //前端课程查询分类
  getCoursePage(page,limit,searchObj) {
    return request({
      url: `/eduservice/courseFront/getFrontCoursePage/${page}/${limit}`,
      method: 'post',
      data:searchObj
    })
  },
  //查询所有分类方法
  getAllSubject(){
    return request({
      url: `/eduservice/subject/getAllSubject`,
      method: 'get'
    })
  },
  getFrontCourseInfo(id){
    return request({
      url: `/eduservice/courseFront/getFrontCourseInfo/${id}`,
      method: 'get'
    })
  }
}