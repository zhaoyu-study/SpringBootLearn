<template>
  <div>
    <div style="padding: 10px 0">
      <el-input v-model="uName" style="width: 300px" placeholder="请输入名称" suffix-icon="el-icon-search" clearable @keydown.enter.native="load"></el-input>
      <el-button style="margin-left: 10px" type="primary" @click="load">search</el-button>
    </div>

    <div style="padding: 10px 0">
      <el-button type="primary" @click="dialogFormVisible = true">增加 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-button type="primary" @click="delBatch">批量删除 <i class="el-icon-remove-outline"></i></el-button>

      <el-upload
        class="upload-demo"
        action="http://localhost:9090/user/import"
        :auto-upload=true
        :show-file-list="false"
        accept="xlsx"
        :on-success="handleImportSuccess"
        :on-error="handleImportError"
        style="display: inline-block">
        <el-button type="primary" style="margin-left: 10px">导入<i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="primary"  style="margin-left: 10px" @click="exportOut" >导出 <i class="el-icon-top"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
      <el-table-column width="50" type="selection" align="center">
      </el-table-column>
      <el-table-column prop="sid" align="center" label="ID" width="80" >
      </el-table-column>
      <el-table-column prop="uid" align="center" label="ID" v-if="false">
      </el-table-column>
      <el-table-column prop="uname" align="center" label="姓名" width="100" >
      </el-table-column>
      <el-table-column prop="urole" align="center" label="角色" width="100">
      </el-table-column>
      <el-table-column prop="unickname" align="center" label="昵称" width="100">
      </el-table-column>
      <el-table-column prop="uemail" align="center" label="邮箱" width="250">
      </el-table-column>
      <el-table-column prop="uphone" align="center" label="电话" width="150">
      </el-table-column>
      <el-table-column prop="uaddress" align="center" label="地址" width="200">
      </el-table-column>

      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="text" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
          <el-button type="danger" @click="deleteData(scope.row.uid)" >删除<i class="el-icon-delete"></i></el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNo"
          :page-sizes="[5, 10, 15]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total=total>
      </el-pagination>
    </div>
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" style="text-align:center" width="40%" :before-close="cancel" custom-class="class_radius">
      <el-form :model="{form}"  label-width="80px" size="big">
        <el-form-item label="用户名" >
          <el-input v-model="form.uname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色" >
          <el-select clearable v-model="form.urole" placeholder="请选择角色" style="width: 100%">
            <el-option v-for="item in roles" :key="item.roleName" :label="item.roleDescription" :value="item.roleKey">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="昵称" >
          <el-input v-model="form.unickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱"  >
          <el-input v-model="form.uemail" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话" >
          <el-input v-model="form.uphone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址" >
          <el-input v-model="form.uaddress" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer" style="text-align: center">
        <el-button @click="cancel()">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "User",
  data(){
    return{
      uName:"",
      //uid:null,
      tableData: [],
      total: 0,
      pageNo:1,
      pageSize:10,
      dialogFormVisible: false,
      form:{},
      multipleSelection:[],
      roles:[],
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("http://localhost:9090/user/page",{
        params:{
          pageNo:this.pageNo,
          pageSize:this.pageSize,
          uName:this.uName
        }
      }).then(res =>{
        console.log(res);
        this.tableData = res.Data;
        this.total=res.Total;
      })

      this.request.get("/role/selectAll").then(res =>{
        this.roles = res.data
      })
    },
    save(){
      this.request.post("http://localhost:9090/user",this.form).then(res =>{
        if(res){
          this.$message.success("保存成功")
        }else {
          this.$message.error("保存失败")
        }
      })
      this.form={}
      this.dialogFormVisible=false
      this.load();

    },

    handleEdit(row){
      this.form=row
      this.dialogFormVisible=true
    },

    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection=val
    },
    delBatch(){
      /*      if (this.multipleSelection !== null){
              let ids=this.multipleSelection.map(v => v.uid) //对象数组[{},{},{}]变成 [1,2,3]
              console.log(ids)
              this.deleteBatchData(ids)
            }else this.load()*/
      let ids=this.multipleSelection.map(v => v.uid) //对象数组[{},{},{}]变成 [1,2,3]
      console.log(ids)
      this.deleteBatchData(ids)
    },
    async deleteBatchData(ids){
      const info = await this.$confirm('此操作将永久删除所选文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true,

      }).catch(err=>err)
      if (info!=="confirm"){
        return this.$message.error('已取消删除')
      }
      await this.request.delete("http://localhost:9090/user/del/batch",{data:ids}).then(res =>{
        if(res){
          this.$message.success("删除成功")
          this.load()
        }else {
          this.$message.error("删除失败")
        }
      })

    },

    async deleteData(uid){
      const info = await this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true,

      }).catch(err=>err)
      if (info!=="confirm"){
        return this.$message.error('已取消删除')
      }
      // const {data:res}=await this.request.delete("http://localhost:9090/user/"+uid).then(res =>{
      await this.request.delete("http://localhost:9090/user/"+uid).then(res =>{
        if(res){
          this.$message.success("删除成功")
          this.load()
        }else {
          this.$message.error("删除失败")
        }
      })

    },
    cancel(){
      this.form=[];
      this.dialogFormVisible=false;
    },
    handleCurrentChange(pageNo){
      console.log(pageNo)
      this.pageNo=pageNo
      this.load()
    },
    handleSizeChange(pageSize){
      console.log(pageSize)
      this.pageSize=pageSize
      this.load()
    },
    handleImportSuccess(){
      this.$message.success("导入成功")
      this.load()
    },
    handleImportError(){
      this.$message.error("导入失败")
    },
    exportOut(){
      window.open("http://localhost:9090/user/export")
    }

  }
}
</script>

<style scoped>
.headerBg{
  background: aqua !important;
}
.class_radius{
  border-radius: 13px;
}
</style>