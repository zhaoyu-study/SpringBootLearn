<template>
  <div>
    <el-container style="height: 100%; border: 1px solid #eee">
      <el-aside :width="isCollapse?'64px':'200px'" style="background-color: rgb(238, 241, 246); height: 100vh; box-shadow: 2px 0 6px darkslategray">  <!--阴影效果-->
        <Aside :is-collapse="isCollapse" :logo-text-show="logoTextShow"/>
      </el-aside>

      <el-container>
        <el-header style="text-align: right; font-size: 12px; border-bottom: 1px solid;line-height: 100px">
          <Header :collapse-btn-class="collapseBtnClass" :collapse="collapse" :user="user"></Header>
        </el-header>

        <el-main>
        <!--表示当前页面的子路由会在 router-view 里面展示-->
          <router-view @refreshUser="getUser"/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>


import Aside from "@/components/Aside";
import Header from "@/components/Header";
import User from "@/views/User";

export default {
  name: 'HomeView',
  data(){
    return{

      collapseBtnClass:"el-icon-s-operation",
      isCollapse:false,
      sideWidth:200,
      logoTextShow:true,
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")):{},

      //loading: false
      //checked: true,

    }
  },
  created() {
    // this.loadingOrClose()
    this.getUser()
  },
  components:{
    User,
    Header,
    Aside
  },
  methods:{
    collapse(){ //点击收缩按钮触发
      this.isCollapse = !this.isCollapse
      this.logoTextShow=!this.logoTextShow
      if(this.isCollapse){
        this.logoTextShow=false
      }else{
        this.logoTextShow=true
      }
    },
    getUser(){
      //从后台获取数据
      this.request.get("/user/selectById/"+this.user.uid).then(res =>{
        this.user=res.data
      })
    }
  }
}
</script>

<style>
.headerBg{
  background: aqua !important;
}
</style>