import request from '@/utils/request'

export default {
    //查询前两条banner数据
  sendCode(phone) {
    return request({
      url: `/eduMsm/msm/send/${phone}`,
      method: 'get'
    })
  },
  registerUser(formItem) {
    return request({
      url: '/educenter/member/register',
      method: 'post',
      data: formItem
    })
  }
}