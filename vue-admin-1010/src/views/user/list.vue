<template>
    <div class="app-container">
    会员列表
   <!--查询表单-->
   <el-form :inline="true" class="demo-form-inline">
    <el-form-item>
      <el-input v-model="userVo.mobile" placeholder="会员手机号"/>
    </el-form-item>

    <el-form-item>
      <el-select v-model="userVo.sex" clearable placeholder="性别">
        <el-option :value="0" label="请选择性别"/>
        <el-option :value="1" label="女"/>
        <el-option :value="2" label="男"/>
      </el-select>
    </el-form-item>
    <el-form-item label="注册时间">
      <el-date-picker
        v-model="userVo.begin"
        type="datetime"
        placeholder="选择开始时间"
        value-format="yyyy-MM-dd HH:mm:ss"
        default-time="00:00:00"
        style="width:150px ;"/>
    </el-form-item>
    <el-form-item>
      <el-date-picker
        v-model="userVo.end"
        type="datetime"
        placeholder="选择截止时间"
        value-format="yyyy-MM-dd HH:mm:ss"
        default-time="00:00:00"
        style="width:150px ;"/>
    </el-form-item>
    <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
    <el-button type="default" @click="resetData()">清空</el-button>
  </el-form>
  <el-table
    :data="list"
    border
    fit
    highlight-current-row>
    <el-table-column
      label="序号"
      width="70"
      align="center">
      <template slot-scope="scope">
        {{ (page - 1) * limit + scope.$index + 1 }}
      </template>
    </el-table-column>
    <el-table-column prop="nickname" label="名称" width="174" />
    <el-table-column prop="mobile" label="手机号" width="196" />
    <el-table-column label="性别" width="107">
     <template slot-scope="scope">
       {{ scope.row.sex===1?'女':'男' }}
     </template>
   </el-table-column>
   <el-table-column prop="age" label="年龄" width="130" />
   <el-table-column label="账号状态" width="110">
     <template slot-scope="scope">
       {{ scope.row.isDisabled ?"已禁用":"正常"}}
     </template>
   </el-table-column>
    <el-table-column prop="gmtCreate" label="注册时间" width="252" />
    <el-table-column prop="gmtModified" label="更新时间" width="267" />
  </el-table>
  <!-- 分页 -->
  <el-pagination
  :current-page="page"
  :page-size="limit"
  :total="total"
  style="padding: 30px 0; text-align: center;"
  layout="total, prev, pager, next, jumper"
  @current-change="getList"
  />
 </div>
</template>

<script>
import userApi from '@/api/user'
export default {
  data() {
    return {
       list:[],
       page:1,
       limit:10,
       total:0,
       userVo:{
        mobile:"",
        sex:0,
        begin:"",
        end:""
       },
    }
  },
  created() {
      this.getList()
  },
  methods: {
    getList(page=1){
      this.page=page
      userApi.getAllUser(this.page,this.limit,this.userVo)
      .then(response =>{
          this.list=response.data.rows
          this.total=response.data.total
      })
    },
    resetData(){
      this.userVo={
        mobile:"",
        sex:0,
        begin:"",
        end:""
      }
      this.getList()
    }
  },
}
</script>

<style>

</style>