<template>
  <el-menu :default-openeds="['1', '2']" style="min-height: 100%; overflow-x:hidden"
           background-color="rgb(48,65,84)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse="isCollapse"
           router>
    <div style="height: 60px; line-height: 60px; text-align: center">
      <img src="../assets/logo.png" alt="" style="width: 20px;position: relative;top: 5px;margin-right: 2px">
      <b style="color: aliceblue" v-show="logoTextShow">后台管理系统</b>
    </div>
    <el-menu-item index="home">
          <i class="el-icon-house"></i>
          <span slot="title">主页</span>
    </el-menu-item>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-s-grid"></i>
        <span slot="title">系统管理</span>
      </template>
      <el-menu-item index="user">
        <i class="el-icon-user-solid"></i>
        <span slot="title">用户管理</span>
      </el-menu-item>
      <el-menu-item index="role">
        <i class="el-icon-s-custom"></i>
        <span slot="title">角色管理</span>
      </el-menu-item>
      <el-menu-item index="menu">
        <i class="el-icon-menu"></i>
        <span slot="title">菜单管理</span>
      </el-menu-item>
      <el-menu-item index="file">
        <i class="el-icon-document"></i>
        <span slot="title">文件管理</span>
      </el-menu-item>

    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props:{
    isCollapse:Boolean,
    logoTextShow:Boolean,
  }
}
</script>

<style scoped>

</style>