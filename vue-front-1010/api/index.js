import request from '@/utils/request'

export default {
    //查询前两条banner数据
  getListCourse() {
    return request({
      url: '/eduservice/indexFront/indexCourse',
      method: 'get'
    })
  },
  getListTeacher() {
    return request({
      url: '/eduservice/indexFront/indexTeacher',
      method: 'get'
    })
  }
}