<template>
  <div style="width: 500px;height: 400px;margin: 10% 25%">
    <el-form :model="{form}"  label-width="80px" size="big">
        <el-upload
            class="avatar-uploader"
            style="text-align: center; margin-bottom: 20px; width: 550px"
            action="http://localhost:9090/file/upload"
            :show-file-list="false"
            :disabled="change"
            :on-success="handleAvatarSuccess">
          <img v-if="form.avatar" :src="form.avatar" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>

      <el-form-item label="用户名" >
        <el-input v-model="form.uname" autocomplete="off" :disabled="change"></el-input>
      </el-form-item>
      <el-form-item label="密　码" >
        <el-input v-model="form.upassword" autocomplete="off" :disabled="change"></el-input>
      </el-form-item>
      <el-form-item label="昵　称" >
        <el-input v-model="form.unickname" autocomplete="off" :disabled="change"></el-input>
      </el-form-item>
      <el-form-item label="邮　箱"  >
        <el-input v-model="form.uemail" autocomplete="off" :disabled="change"></el-input>
      </el-form-item>
      <el-form-item label="电　话" >
        <el-input v-model="form.uphone" autocomplete="off" :disabled="change"></el-input>
      </el-form-item>
      <el-form-item label="地　址" >
        <el-input v-model="form.uaddress" autocomplete="off" :disabled="change"></el-input>
      </el-form-item>
    </el-form>

    <div slot="footer" class="dialog-footer" style="text-align: center">
      <el-button style="margin-left: 100px" @click="$router.push('/')">取 消</el-button>

      <el-popconfirm title="确认修改您的个人信息吗？" @confirm="changeInfo">
        <el-button  style="margin-left: 10px" slot="reference" type="primary" :disabled="!change">修 改</el-button>
      </el-popconfirm>
      <el-button style="float: right" type="success" plain :disabled="change" @click="beforeSave">提 交</el-button>
<!--      <el-popconfirm title="确认提交？" @confirm="beforeSave">
        <el-button  style="float: right" type="success" plain :disabled="change">提 交</el-button>
      </el-popconfirm>-->
    </div>

  </div>
</template>

<script>

export default {
  /****************************************/
/*  inject: ['reload'], // 引入方法*/
  /****************************************/
  name: "Person",
  data(){
    return{
      form:{},
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")):{},
      change:true,
    }
  },
  created() {
    this.request.get("/user/selectById/"+this.user.uid).then(res =>{
      if(res.code ==="200"){
        this.form=res.data
      }
    })
  },
  /****************************************/
/*  mounted(){
    this.reload()  // 哪里需要在哪里调用
  },*/
  /****************************************/
  methods:{

    getChangedUserInfo(){
      this.request.get("/user/selectById/"+this.user.uid).then(res =>{
        if(res.code ==="200"){
          this.form=res.data
          this.user.uname=res.data.uname
          this.user.upassword=res.data.upassword
          this.user.unickname=res.data.unickname
          this.user.avatar= res.data.avatar
          localStorage.setItem("user", JSON.stringify(this.user))
        }
      })
    },

    save(){
      this.request.post("/user",this.form).then(res =>{
        if(res){
          this.$message.success("保存成功")
          //触发父级更新user的方法
          this.$emit("refreshUser")
          //更新用户信息缓存
          this.getChangedUserInfo()
        }else {
          this.$message.error("保存失败")
        }
      })
    },
    changeInfo(){
      this.change=false
    },
    beforeSave(){
      this.save()
      this.change=true
    },

    handleAvatarSuccess(res){
      this.form.avatar = res
    }
  }
}
</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 100px;
  height: 100px;
  display: block;
}
</style>