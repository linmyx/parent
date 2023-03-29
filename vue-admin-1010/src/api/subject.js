import request from '@/utils/request'

export default{
    getSubject(){
        return request({
            url: `/eduservice/subject/getAllSubject`,
            method: 'get',
            })
    },
}
