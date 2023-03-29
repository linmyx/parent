import request from '@/utils/request'

export default{
    createSta(day){
        return request({
            url: `/staservice/sta/registerCount/${day}`,
            method: 'post',
        })
    },
    showData(searchObj){
        return request({
            url: `/staservice/sta/showData/${searchObj.type}/${searchObj.begin}/${searchObj.end}`,
            method: 'get',
        })
    },
    showAllData(begin,end){
        return request({
            url: `/staservice/sta/showAllData/${begin}/${end}`,
            method: 'get',
        })
    },
}