import request from '@/utils/request'

export default {
    //查询前两条banner数据
  getTeacherPage(page,limit) {
    return request({
      url: `/eduservice/teacherFront/getTeacherFrontList/${page}/${limit}`,
      method: 'post'
    })
  },

  getTeacherCourseInfo(id){
    return request({
      url: `/eduservice/teacherFront/getTeacherInfo/${id}`,
      method: 'get'
    })
  }

}