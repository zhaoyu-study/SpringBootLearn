<template>
  <div class="wrapper">
    <div style="margin: 200px auto; background-color: #fff;width: 450px;height: 300px;padding: 20px;border-radius: 10px"><!--padding 边框和主体空一点距离，border-radius边框圆角-->
      <div style="margin: 20px 0;text-align: center;font-size: 24px"><b>登录</b></div>
        <el-form :model="user" :rules="rules" ref="userForm" >
          <el-form-item prop="uname">
            <el-input  size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.uname"></el-input>
          </el-form-item>
          <el-form-item prop="upassword">
            <el-input  size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.upassword" @keydown.enter.native="login"></el-input>
          </el-form-item>

          <el-link type="info" style="float: right; margin-top: -20px">忘记密码</el-link>
          <div style="margin: 20px 0;text-align: center">
            <el-button type="primary" size="small" autocomplete="off"  @click="login">登录</el-button>
            <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/register')">注册</el-button>
          </div>
        </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      user: {},
      rules: {
        uname: [
          {required: true, message: '请输入用户名', trigger: 'blur'},/*blur表示鼠标失焦时*/
          {min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur'}
        ],
        upassword: [
          {required: true, message: '请输入密码', trigger: 'blur'},/*blur表示鼠标失焦时*/
          {min: 1, max: 15, message: '长度在 6 到 15 个字符', trigger: 'blur'}
        ]
      },
    }
  },
  methods: {
    login() {
      this.$refs["userForm"].validate((valid) => {
        if (valid) {
          this.request.post("/user/login", this.user).then(res => {
            localStorage.setItem("user", JSON.stringify(res.data))//存储用户信息到浏览器（永久存储，需要有限制的存储可以考虑session，目前不会）
            if (res.code === "200") {/*/!*请求成功，跳到主页面*!/*/
              this.$router.push("/")
              this.$message.success("登录成功")
            } else {
              this.$message.error(res.msg)
            }
          })
        } else {
          return false;
        }
      });
    },
  }
}
</script>

<style>
  .wrapper{
    height: 100vh; /*视图窗口高度，即100%窗口高度*/
    background-image: linear-gradient(to bottom right,#FC4668,#3F5EFB);/*渐变色*/
    overflow: hidden; /*隐藏超出部分*/
  }
</style>