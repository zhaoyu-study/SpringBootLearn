<template>
  <div class="wrapper">
    <div style="margin: 200px auto; background-color: #fff;width: 450px;height: 400px;padding: 20px;border-radius: 10px"><!--padding 边框和主体空一点距离，border-radius边框圆角-->
      <div style="margin: 20px 0;text-align: center;font-size: 24px"><b>注册</b></div>
        <el-form :model="user" :rules="rules" ref="userForm">
          <el-form-item prop="uname">
            <el-input placeholder="输入用户名"  size="medium" style="margin: 5px 0" prefix-icon="el-icon-user" v-model="user.uname"></el-input>
          </el-form-item>
          <el-form-item prop="upassword">
            <el-input placeholder="设置密码"  size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="user.upassword"></el-input>
          </el-form-item>
          <el-form-item prop="confirm_upassword">
            <el-input placeholder="确认密码"  size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="user.confirm_upassword"></el-input>
          </el-form-item>

          <div style="margin: 30px 0; text-align: center">
            <el-button type="primary" size="small" autocomplete="off" @click="registerUser">确定</el-button>
            <el-button type="warning" size="small" autocomplete="off " @click="$router.push('/login')">返回</el-button>
          </div>
        </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data(){
    return{
      user:{},
      rules: {
        uname: [
          { required: true, message: '请输入用户名', trigger: 'blur' },/*blur表示鼠标失焦时*/
          { min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur' }
        ],
        upassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },/*blur表示鼠标失焦时*/
          { min: 1, max: 15, message: '长度在 6 到 15 个字符', trigger: 'blur' }
        ],
        confirm_upassword: [
          { required: true, message: '请确认密码', trigger: 'blur' },/*blur表示鼠标失焦时*/
          { min: 1, max: 15, message: '长度在 6 到 15 个字符', trigger: 'blur' }
        ]
      },
    }
  },
  methods:{
    registerUser(){
      this.$refs["userForm"].validate((valid) => {
        if (valid) {

          if(this.user.confirm_upassword!==this.user.upassword){
            this.$message.error("两次输入的密码不一致")
            return false
          }

          this.request.post("/user/register",this.user).then(res=>{
            if(res.code === "200" ){/*/!*请求成功，跳到主页面*!/*/
              this.$router.push("/login")
              this.$message.success("注册成功")
            }else {
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