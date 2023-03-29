import request from '@/utils/request'

export default {

  getComment(page, limit, courseId) {
    return request({
      url: `/eduservice/comment/getCommentPage/${page}/${limit}/${courseId}`,
      method: 'get',
    })
  },
  addComment(comment) {
    return request({
      url: `/eduservice/comment/addComment`,
      method: 'post',
      data: comment
    })
  }
}