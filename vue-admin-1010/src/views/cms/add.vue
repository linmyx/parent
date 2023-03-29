<template>
    <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="页面名称">
        <el-input v-model="banner.title"/>
      </el-form-item>
      <el-form-item label="页面地址">
        <el-input v-model="banner.linkUrl"/>
      </el-form-item>
      <el-form-item label="排序">
        <el-input v-model="banner.sort"/>
      </el-form-item>
      <!-- 课程封面-->
      <el-form-item label="Banner图像">
            <el-upload
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            :action="BASE_API+'/eduoss/fileoss'"
            class="avatar-uploader">
            <img :src="banner.imageUrl">
            </el-upload>
           </el-form-item>
      <el-form-item style="margin-top: 10px;">
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import bannerApi from '@/api/banner'
   export default {
      data() {
         return {
            banner:{
               title:'',
               imageUrl:'/static/01.jpg',
               linkUrl:'',
               sort:0
            },
            bannerId:'',
            saveBtnDisabled: false, // 保存按钮是否禁用
            BASE_API: process.env.BASE_API, // 接口API地址
         }
      },
      created() {
         if(this.$route.params.id){
            this.bannerId=this.$route.params.id
            this.getBanner()
         }
      },
      methods: {
         //上传成功
         handleAvatarSuccess(res,file){
            this.banner.imageUrl = res.data
         },
        //上传之前
        beforeAvatarUpload(file){
            const isJPG = file.type === 'image/jpeg'
            const isPNG = file.type === 'image/png'
            const isLt2M = file.size / 1024 / 1024 < 2
            if (!isJPG && !isPNG) {
                this.$message.error('上传头像图片只能是 JPG 或 png 格式!')
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 2MB!')
            }
            return isJPG && isLt2M
        },
        //保存Banner或者修改Banner
        saveOrUpdate(){
           if(this.$route.params.id){
             this.updateBanner()
           } else {
            this.saveBanner()
           }
        },
        saveBanner(){
         bannerApi.addBanner(this.banner)
         .then(response =>{
             if(response.success){
                  this.$message({
                     type: 'success',
                     message: '添加Banner图成功!'
               })
               this.$router.push({ path: '/cms/list' })
             }
         })
        },
        getBanner(){
          bannerApi.getBannerById(this.bannerId)
          .then(response =>{
             this.banner=response.data
          })
        },
        updateBanner(){
           bannerApi.updataBanner(this.banner)
            .then(response =>{
               if(response.success){
                     this.$message({
                        type: 'success',
                        message: '修改Banner成功!'
                  })
                  this.$router.push({ path: '/cms/list' })
               }
            })
        },
      },
   }
</script>

<style>

</style>