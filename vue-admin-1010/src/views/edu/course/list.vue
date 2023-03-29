<template>
  <div class="app-container">
    课程列表
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
     <el-form-item>
       <el-input v-model="courseQuery.title" placeholder="课程名称"/>
     </el-form-item>

     <el-form-item>
       <el-select v-model="courseQuery.status" clearable placeholder="课程状态">
         <el-option :value="'Normal'" label="已发布"/>
         <el-option :value="'Draft'" label="未发布"/>
       </el-select>
     </el-form-item>
     <el-button type="primary" icon="el-icon-search" @click="getListPage()">查询</el-button>
     <el-button type="default" @click="resetData()">清空</el-button>
   </el-form>
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

     <el-table-column prop="title" label="课程名称" width="200" />

     <el-table-column label="课程状态" width="200">
       <template slot-scope="scope">
         {{ scope.row.status==='Normal'?'已发布':'未发布' }}
       </template>
     </el-table-column>

     <el-table-column prop="lessonNum" label="课时数" width="100"/>

     <el-table-column prop="gmtCreate" label="添加时间" width="200"/>

     <el-table-column prop="viewCount" label="浏览数量" width="100" />

     <el-table-column label="操作" width="400" align="center">
       <template slot-scope="scope">
        <router-link :to="'/course/info/'+scope.row.id">
          <el-button type="primary" size="mini" icon="el-icon-edit">编辑课程的基本信息</el-button>
        </router-link>
         <router-link :to="'/course/chapter/'+scope.row.id">
          <el-button type="primary" size="mini" icon="el-icon-edit">编辑课程的大纲</el-button>
         </router-link>
         <el-button type="primary" size="mini" icon="el-icon-edit" @click="updateStatus(scope.row.id)">课程状态修改</el-button>
         <el-button type="danger" size="mini" icon="el-icon-delete" @click="deleteCourse(scope.row.id)">删除课程信息</el-button>
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
   @current-change="getListPage"
   />
  </div>
</template>

<script>

import course from '@/api/course'

export default {
   name:'List',
   data() {
      return{
         list:null,
         page:1,      //当前页
         limit:10,      //每页显示的条数
         total: 0,    //总记录数
         courseQuery:{},  //条件封装对象
      }
   },

   created() {  //页面渲染之前执行
       this.getListPage()
   },

   methods: {  //创建具体的方法，调用teacher.js定义的方法
       getListPage(page=1){
        this.page=page
        course.getCourseList(this.page,this.limit,this.courseQuery)
           .then(response =>{
               //接收接口传来的数据
               this.list=response.data.records
               this.total=response.data.total
           })
           .catch(error =>{
               console.log(error)
           })
       },
       resetData(){
           //清空表单数据
           this.courseQuery={}
           //查询所有数据
           this.getListPage()
       },
       deleteCourse(id){
        this.$confirm('此操作将永久删除该课程, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
            }).then(() => {   //点击确定
              course.deleteCourseById(id)
               .then(response => {
                if (response.success) {
                    this.$message({
                    type: 'success',
                    message: "删除课程成功"
                    })
                    this.getListPage()
                }
            })
          })
       },
       //修改课程状态
       updateStatus(id){
          this.$confirm('确定要修改课程状态?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
              }).then(() => {   //点击确定
                course.updatedStu(id)
                .then(response => {
                  if (response.success) {
                      this.$message({
                      type: 'success',
                      message: "修改课程状态成功！！"
                      })
                      this.getListPage()
                  }
              })
            })
       }
    }
}
</script>

<style>

</style>