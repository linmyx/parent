import request from '@/utils/request'

export default {
  submitLogin(loginVo) {
    return request({
      url: '/educenter/member/login',
      method: 'post',
      data: loginVo
    })
  },
  getUserInfo(){
    return request({
        url: '/educenter/member/getMemberInfo',
        method: "get"
    })
  }
}