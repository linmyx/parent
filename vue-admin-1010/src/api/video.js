import request from '@/utils/request'

export default{
    //添加章节
    addVideo(video){
        return request({
            url: `/eduservice/video/addVideo`,
            method: 'post',
            data: video
        })
    },
    //根据id获取章节信息回显
    getChapterById(videoId){
        return request({
            url: `/eduservice/video/getVideoInfo/${videoId}`,
            method: 'get',
        })
    },
    //修改章节信息
    updateChapter(eduVideo){
        return request({
            url: `/eduservice/video/updateVideo`,
            method: 'post',
            data: eduVideo
        })
    },
    //删除章节
    deleteChapter(videoId,){
        return request({
            url: `/eduservice/video/deleteVideo/${videoId}`,
            method: 'delete',
        })
    },

    //删除阿里视频
    removeAlyVideo(id){
        return request({
            url: `/eduvod/video/removeAlyVideo/${id}`,
            method: 'delete',
        })
    }
}