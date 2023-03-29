import request from '@/utils/request'

export default{

    getBannerPage(page,limit){
        return request({
            url: `/educms/bannerAdmin/pageBanner/${page}/${limit}`,
            method: 'get',
        })
    },

    addBanner(banner){
        return request({
            url:'/educms/bannerAdmin/addBanner',
            method: "post",
            data: banner
        })
    },

    getBannerById(id){
        return request({
            url:'/educms/bannerAdmin/getBanner/'+id,
            method: "get",
        })
    },

    updataBanner(banner){
        return request({
            url:'/educms/bannerAdmin/updateBanner/',
            method: "put",
            data: banner
        })
    },

    removeBanner(id){
        return request({
            url:'/educms/bannerAdmin/deleteBanner/'+id,
            method: "delete",
        })
    }

}