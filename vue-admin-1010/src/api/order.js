import request from '@/utils/request'

export default{
    getAllOrder(page,limit,orderVo){
        return request({
            url: `/eduorder/orderAdmin/getAllOrder/${page}/${limit}`,
            method: 'post',
            data: orderVo
        })
    },
}