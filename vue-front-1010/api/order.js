import request from '@/utils/request'

export default {
  //生成订单接口
  addOrders(courseId) {
    return request({
      url: '/eduorder/order/createOrder/'+courseId,
      method: 'post'
    })
  },
  //根据id查询订单信息
  getOrdersInfo(id) {
    return request({
      url: '/eduorder/order/getOrderInfo/'+id,
      method: 'get'
    })
  },
  //生成二维码
  createNative(orderNo) {
    return request({
      url: '/eduorder/paylog/createNative/'+orderNo,
      method: 'get'
    })
  },
  //查询订单状态方法
  queryPayStatus(orderNo) {
    return request({
      url: '/eduorder/paylog/queryPayStatus/'+orderNo,
      method: 'get'
    })
  },
}