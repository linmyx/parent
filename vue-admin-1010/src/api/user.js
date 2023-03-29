import request from '@/utils/request'

export default{
    getAllUser(page,limit,userVo){
        return request({
            url: `/educenter/member/admin/getAllUserPage/${page}/${limit}`,
            method: 'post',
            data: userVo
        })
    },
}