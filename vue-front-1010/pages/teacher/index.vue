<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 讲师列表 开始 -->
    <section class="container">
      <header class="comm-title all-teacher-title">
        <h2 class="fl tac">
          <span class="c-333">全部讲师</span>
        </h2>
        <section class="c-tab-title">
          <a id="subjectAll" title="全部" href="#">全部</a>
        </section>
      </header>
      <section class="c-sort-box unBr">
        <div>
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-show="!teacherList">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article v-show="teacherList" class="i-teacher-list">
            <ul class="of" >
              <li v-for="teacher in teacherList.items" :key="teacher.id">
                <section class="i-teach-wrap">
                  <div class="i-teach-pic">
                    <a :href="'/teacher/'+teacher.id" :title="teacher.name" target="_blank">
                      <img :src="teacher.avatar" :alt="teacher.name">
                    </a>
                  </div>
                  <div class="mt10 hLh30 txtOf tac">
                    <a :href="'/teacher/'+teacher.id" :title="teacher.name" target="_blank" class="fsize18 c-666">{{teacher.name}}</a>
                  </div>
                  <div class="hLh30 txtOf tac">
                    <span class="fsize14 c-999">{{teacher.intro}}</span>
                  </div>
                  <div class="mt15 i-q-txt">
                    <p class="c-999 f-fA">{{teacher.career}}</p>
                  </div>
                </section>
              </li>

            </ul>
            <div class="clear"></div>
          </article>
        </div>
        <!-- 公共分页 开始 -->
        <!-- 公共分页 开始 -->
        <div>
          <div class="paging">
            <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
            <a
              :class="{undisable: !teacherList.hasPrevious}"
              href="#"
              title="首页"
              @click.prevent="getTeacherInfo(1)">首</a>
            <a
              :class="{undisable: !teacherList.hasPrevious}"
              href="#"
              title="前一页"
              @click.prevent="getTeacherInfo(teacherList.current-1)">&lt;</a>
            <a
              v-for="page in teacherList.pages"
              :key="page"
              :class="{current: teacherList.current == page, undisable: teacherList.current == page}"
              :title="'第'+page+'页'"
              href="#"
              @click.prevent="getTeacherInfo(page)">{{ page }}</a>
            <a
              :class="{undisable: !teacherList.hasNext}"
              href="#"
              title="后一页"
              @click.prevent="getTeacherInfo(teacherList.current+1)">&gt;</a>
            <a
              :class="{undisable: !teacherList.hasNext}"
              href="#"
              title="末页"
              @click.prevent="getTeacherInfo(teacherList.pages)">末</a>
            <div class="clear"/>
          </div>
        </div>
<!-- 公共分页 结束 -->
        <!-- 公共分页 结束 -->
      </section>
    </section>
    <!-- /讲师列表 结束 -->
  </div>
</template>
<script>
import teacherApi from '@/api/teacher'
  export default {

    data() {
      return {
         teacherList:[],
         page:""
      }
    },
    //使用异步调用
    // asyncData({ params, error }) {
    //   return teacherApi.getTeacherPage(1, 8)
    //   .then(response => {
    //     console.log(response.data.data);
    //     return { data: response.data.data }
    //   });
    // },
    created(){
      this.getTeacherInfo()
    },
    methods:{
      getTeacherInfo(page=1){
        teacherApi.getTeacherPage(page,8)
        .then(response =>{
            this.teacherList=response.data.data
            console.log(this.teacherList);
        })
      }
    }

  };
</script>