<template>
  <div>
    <div style="font-size: 30px;text-align: left ;height: 60px" >
      <span :class="collapseBtnClass" style="cursor: pointer ;height: 40px;position: relative;top: -15px" @click="collapse"></span>
    </div>
    <div style="position:relative; top:-34px;margin-left: 50px">

      <el-breadcrumb separator-class="el-icon-arrow-right" >
        <el-breadcrumb-item :to="{ path: 'home' }">首页</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
<!--    <div style=" position:relative;height: 20px;width: 10px; top:-100px;left: 90%">
    <el-dropdown style=" position:relative;width: 100px; cursor: pointer;height: 50px; left: 80%;top:10px">
      <div style="right: 20px">
        <el-avatar :src="user.avatar" style="position: relative; right: 25px; top: 10px">
        </el-avatar>
        <span style="position: relative;right: 20px">{{user.unickname}}</span><i class="el-icon-arrow-down"></i>
      </div>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>个人信息</el-dropdown-item>
        <router-link to="/login"><el-dropdown-item>退出</el-dropdown-item></router-link>

      </el-dropdown-menu>
    </el-dropdown>
    </div>-->
    <div style="height: 90px; width: 120px; ;position: fixed;top: 0;right: 50px">
      <el-dropdown style="cursor: pointer;height: 90px;width: 120px;float: right">
        <div>
          <el-avatar :src="user.avatar" size="large" style="">
          </el-avatar>
          <span style="margin-left: 10px;position: relative;bottom: 10px">{{user.unickname}}</span><i class="el-icon-arrow-down" style="position: relative;bottom: 10px"></i>
        </div>
        <el-dropdown-menu slot="dropdown" style="margin-top: -15px">
          <el-dropdown-item style="font-size: 14px;" >
            <span style="text-decoration: none" @click="$router.push('/person')">个人信息</span>
          </el-dropdown-item>
          <el-dropdown-item style="font-size: 14px; text-align: center">
            <span style="text-decoration: none" @click="logout">退出</span>
          </el-dropdown-item>

        </el-dropdown-menu>
      </el-dropdown>
    </div>

  </div>
</template>

<script>
export default {
  name: "Header",
  props:{
    collapseBtnClass:String,
    collapse : Function,
    //接收父页面传来的数据user
    user: Object
  },
  data(){
    return {
      //user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")):{}
    }
  },
  methods:{
    logout(){
      this.$router.push("/login")
      localStorage.removeItem("user")//移除保存在本地的数据
      this.$message.success("退出成功")
    }
  }
/*  computed: {
    currentPathName () {
      return this.$store.state.currentPathName;　　//需要监听的数据
    }
  },
  watch: {
    currentPathName (newVal, oldVal) {
      console.log(newVal)
    }
  },*/
}
</script>

<style scoped>

</style>