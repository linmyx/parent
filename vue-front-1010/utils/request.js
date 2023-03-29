import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import cookie from 'js-cookie'
// 创建axios实例
const service = axios.create({
  baseURL: 'http://localhost:8222', // api的base_url
  timeout: 20000 // 请求超时时间
})

//第三步 http request 拦截器
service.interceptors.request.use(
  config => {
  //debugger
  if (cookie.get('userToken')) {
    config.headers['token'] = cookie.get('userToken');
  }
    return config
  },
  err => {
  return Promise.reject(err);
})


// http response 拦截器
service.interceptors.response.use(
  response => {
      return response;
  },
  error => {
    return Promise.reject(error.response)   // 返回接口返回的错误信息
});

export default service