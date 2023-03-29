<template>
    <div class="app-container">
    <!--表单-->
    <el-form :inline="true" class="demo-form-inline">

      <el-form-item>
        <el-select v-model="searchObj.type" clearable placeholder="请选择">
          <el-option label="学员登录数统计" value="login_num"/>
          <el-option label="学员注册数统计" value="register_num"/>
          <el-option label="课程播放数统计" value="video_view_num"/>
          <el-option label="每日课程数统计" value="course_num"/>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-date-picker
          v-model="searchObj.begin"
          type="date"
          placeholder="选择开始日期"
          value-format="yyyy-MM-dd" />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="searchObj.end"
          type="date"
          placeholder="选择截止日期"
          value-format="yyyy-MM-dd" />
      </el-form-item>
      <el-button
        :disabled="btnDisabled"
        type="primary"
        icon="el-icon-search"
        @click="showChart()">查询</el-button>
        <el-button
        :disabled="btnDisabled2"
        type="primary"
        icon="el-icon-search"
        @click="showAllData()">查询所有</el-button>
    </el-form>
    <div class="chart-container">
      <div id="chart" class="chart" style="height:500px;width:100%" />
    </div>
  </div>
</template>

<script>
import echarts from 'echarts'
import staApi from '@/api/sta'
export default {
    data() {
        return {
            searchObj:{},
            btnDisabled:false,
            btnDisabled2:false,
            xData:[],
            yData:[],
            date:[],
            register:[],
            login:[],
            video:[],
            course:[]
        }
    },
    created() {
        
    },
    methods: {
        showChart(){
            staApi.showData(this.searchObj)
            .then(response =>{
                 this.xData = response.data.dateList
                 this.yData = response.data.numList
                 this.setChart()
            })
            
        },
        setChart(){
             // 基于准备好的dom，初始化echarts实例
            this.chart = echarts.init(document.getElementById('chart'))
            // console.log(this.chart)

            // 指定图表的配置项和数据
            var option = {

                title: {
                        text: '数据统计'
                },
                tooltip: {
                    trigger: 'axis'
                },

                dataZoom: [{
                    show: true,
                    height: 30,
                    xAxisIndex: [
                        0
                    ],
                    bottom: 30,
                    start: 10,
                    end: 80,
                    handleIcon: 'path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z',
                    handleSize: '110%',
                    handleStyle: {
                        color: '#d3dee5'

                    },
                    textStyle: {
                        color: '#fff'
                    },
                    borderColor: '#90979c'
                    },
                    {
                    type: 'inside',
                    show: true,
                    height: 15,
                    start: 1,
                    end: 35
                    }],
                // x轴是类目轴（离散数据）,必须通过data设置类目数据
                xAxis: {
                type: 'category',
                data: this.xData
                },
                // y轴是数据轴（连续数据）
                yAxis: {
                type: 'value'
                },
                // 系列列表。每个系列通过 type 决定自己的图表类型
                series: [{
                // 系列中的数据内容数组
                data: this.yData,
                // 折线图
                type: 'line'
                }]
        }
        this.chart.setOption(option)
     },
     showAllData(){
        staApi.showAllData(this.searchObj.begin,this.searchObj.end)
        .then(response =>{
            this.date=response.data.dateList
            this.register=response.data.registerList
            this.login=response.data.loginList
            this.video=response.data.videoList
            this.course=response.data.courseList
            this.getAllChart()
        })
     },
     getAllChart(){
        var chartDom = document.getElementById('chart');
        var myChart = echarts.init(chartDom);
        var option;
        option = {
        title: {
            text: '统计数据'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
            type: 'cross',
            label: {
                backgroundColor: '#6a7985'
            }
            }
        },
        legend: {
            data: ['注册人数', '登录人数', '视频播放人数', '新增课程数']
        },
        toolbox: {
            feature: {
            saveAsImage: {}
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [
            {
            type: 'category',
            boundaryGap: false,
            data: this.date
            }
        ],
        yAxis: [
            {
            type: 'value'
            }
        ],
        series: [
            {
            name: '注册人数',
            type: 'line',
            stack: 'Total',
            areaStyle: {},
            emphasis: {
                focus: 'series'
            },
            data: this.register
            },
            {
            name: '登录人数',
            type: 'line',
            stack: 'Total',
            areaStyle: {},
            emphasis: {
                focus: 'series'
            },
            data: this.login
            },
            {
            name: '视频播放人数',
            type: 'line',
            stack: 'Total',
            areaStyle: {},
            emphasis: {
                focus: 'series'
            },
            data: this.video
            },
            {
            name: '新增课程数',
            type: 'line',
            stack: 'Total',
            areaStyle: {},
            emphasis: {
                focus: 'series'
            },
            data: this.course
            },
        ]
        };

        option && myChart.setOption(option);

     }
    },
}
</script>

<style>

</style>