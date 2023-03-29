<template>
 <div class="app-container">
    Banner列表
   <el-table
     :data="list"
     border
     fit
     highlight-current-row>
     <el-table-column
       label="序号"
       width="150"
       align="center">
       <template slot-scope="scope">
         {{ (page - 1) * limit + scope.$index + 1 }}
       </template>
     </el-table-column>

     <el-table-column prop="title" label="页面名称" width="200" />

     <el-table-column prop="image" label="图片" align="center">
          <template slot-scope="{ row }">
            <el-image style="width: auto; height: 40px; border:none;cursor: pointer;" 
            :src="getImage(row.imageUrl)"
            :preview-src-list="[row.imageUrl]">
            </el-image>
        </template>
      </el-table-column>
     <el-table-column prop="linkUrl" label="页面地址" width="100"/>

     <el-table-column prop="gmtCreate" label="添加时间" width="200"/>

     <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="updataById(scope.row.id)">修改</el-button>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
   </el-table>
   <!-- 分页 -->
   <el-pagination
   :current-page="page"
   :page-size="limit"
   :total="total"
   style="padding: 30px 0; text-align: center;"
   layout="total, prev, pager, next, jumper"
   @current-change="getBanner"
   />
  </div>
</template>

<script>
import bannerApi from '@/api/banner'
  export default {
      data() {
        return {
          list:[],
          page:1,
          limit:10,
          total:0,
        }
      },
      created() {
        
        this.getBanner()
      },
      methods:{
          getBanner(page=1){
             this.page=page
             bannerApi.getBannerPage(this.page,this.limit)
             .then(response =>{
                 this.list=response.data.rows
                 this.total=response.data.total
             })
          },
          getImage (image) {
            return image.toString();
          },
          updataById(id){
            this.$router.push({ path: '/cms/edit/'+id })
          },
          removeDataById(id){
            this.$confirm('此操作将永久删除该Banner图, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
            }).then(() => {   //点击确定
                bannerApi.removeBanner(id)
                .then(response =>{
                    if(response.success){
                        this.$message({
                        type: 'success',
                        message: '删除成功!'
                        });
                    } else {
                        this.$message({
                        type: 'error',
                        message: '删除失败'
                        })
                    }
                    this.getBanner()
                })
               
            })
          }
      },
      
  }
</script>

<style>

</style>