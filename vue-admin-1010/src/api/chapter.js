import request from '@/utils/request'

export default{
    getAllChapterVideo(courseId){
        return request({
            url: `/eduservice/chapter/getChapterVideo/${courseId}`,
            method: 'get',
        })
    },

    //添加章节
    addChapter(eduChapter){
        return request({
            url: `/eduservice/chapter/addChapter`,
            method: 'post',
            data: eduChapter
        })
    },
    //根据id获取章节信息回显
    getChapterById(chapterId){
        return request({
            url: `/eduservice/chapter/getChapterInfo/${chapterId}`,
            method: 'get',
        })
    },
    //修改章节信息
    updateChapter(eduChapter){
        return request({
            url: `/eduservice/chapter/updateChapter`,
            method: 'post',
            data: eduChapter
        })
    },
    //删除章节
    deleteChapter(chapterId){
        return request({
            url: `/eduservice/chapter/deleteChapter/${chapterId}`,
            method: 'delete',
        })
    },
}