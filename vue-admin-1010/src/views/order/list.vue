<template>
      <div class="app-container">
    讲师列表
     <!--查询表单-->
     <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="orderVo.CourseTitle" placeholder="课程名"/>
      </el-form-item>

      <el-form-item>
        <el-select v-model="orderVo.payType" clearable placeholder="支付方式">
          <el-option :value="0" label="请选择支付方式"/>
          <el-option :value="1" label="微信"/>
          <el-option :value="2" label="支付宝"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-input v-model="orderVo.mobile" placeholder="购买者手机号"/>
      </el-form-item>
      <el-form-item label="订单时间">
        <el-date-picker
          v-model="orderVo.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="orderVo.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
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

      <el-table-column prop="courseTitle" label="课程名称" width="174" />
      <el-table-column prop="teacherName" label="讲师名称" width="98" />
      <el-table-column prop="nickname" label="会员呢称" width="104" />
      <el-table-column prop="mobile" label="会员手机" width="122" />
      <el-table-column label="订单金额" width="158">
       <template slot-scope="scope">
         {{ scope.row.totalFee*10}}
       </template>
     </el-table-column>
      <el-table-column label="支付类型" width="122">
       <template slot-scope="scope">
         {{ scope.row.payType===1?'微信':'支付宝' }}
       </template>
     </el-table-column>
     <el-table-column label="订单状态" width="252">
       <template slot-scope="scope">
         {{ scope.row.status===1?'已支付':'未支付' }}
       </template>
     </el-table-column>
      <el-table-column prop="gmtModified" label="下单时间" width="210" />
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
import orderApi from '@/api/order'
  export default {
    data() {
      return {
         list:[],
         page:1,
         limit:10,
         total:0,
         orderVo:{
          CourseTitle:"",
          payType:0,
          mobile:"",
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
        orderApi.getAllOrder(this.page,this.limit,this.orderVo)
        .then(response =>{
            this.list=response.data.rows
            this.total=response.data.total
        })
      },
      resetData(){
        this.orderVo={
          CourseTitle:"",
          payType:0,
          mobile:"",
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