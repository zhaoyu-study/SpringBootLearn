<template>
  <div>
    <div style="padding: 10px 0">
      <el-input v-model="menuName" style="width: 300px" placeholder="请输入菜单名" suffix-icon="el-icon-search" clearable @keydown.enter.native="load"></el-input>
      <el-button style="margin-left: 10px" type="primary" @click="load">search</el-button>
    </div>

    <div style="padding: 10px 0">
      <el-button type="primary" @click="dialogFormVisible = true">增加 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-button type="primary" @click="delBatch">批量删除 <i class="el-icon-remove-outline"></i></el-button>

    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
      <el-table-column width="50" type="selection" align="center">
      </el-table-column>
      <el-table-column  align="center" label="ID" width="80" >
        <template slot-scope="scope">
          {{(scope.$index+1)+(pageNo-1)*pageSize}}
        </template>
      </el-table-column>
      <el-table-column prop="menuId" align="center" label="菜单ID" v-if="false">
      </el-table-column>
      <el-table-column prop="menuName" align="center" label="菜单名称" width="100" >
      </el-table-column>
      <el-table-column prop="menuPath" align="center" label="菜单路径" width="100">
      </el-table-column>
      <el-table-column prop="menuIcon" align="center" label="菜单图标" width="100">
      </el-table-column>
      <el-table-column prop="menuDescription" align="center" label="菜单描述" width="100">
      </el-table-column>

      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="text" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
          <el-button type="danger" @click="deleteBatchData([scope.row.menuId])" >删除<i class="el-icon-delete"></i></el-button>
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
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="40%" :before-close="cancel">
      <el-form :model="{form}"  label-width="80px" size="big">
        <el-form-item label="菜单名称" >
          <el-input v-model="form.menuName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="菜单路径" >
          <el-input v-model="form.menuPath" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="菜单图标" >
          <el-input v-model="form.menuIcon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="菜单描述" >
          <el-input v-model="form.menuDescription" autocomplete="off"></el-input>
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
  name: "Role",
  data(){
    return{
      menuName:"",
      tableData: [],
      total: 0,
      pageNo:1,
      pageSize:10,
      dialogFormVisible: false,
      form:{},
      multipleSelection:[],
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("/menu/page",{
        params:{
          pageNo:this.pageNo,
          pageSize:this.pageSize,
          menuName:this.menuName
        }
      }).then(res =>{
        //console.log(res);
        this.tableData = res.data.records;
        this.total=res.data.total;
      })
    },

    save(){
      if(Object.keys(this.form).length !== 0){
        this.request.post("/menu/saveOrUpdate",this.form).then(res =>{
          if(res.code==="200"){
            this.$message.success("保存成功")
          }else {
            this.$message.error("保存失败")
          }
          this.load()
          this.form={}
          this.dialogFormVisible=false
        })
      }else {
        this.$message.error("数据为空")
      }

    },

    handleEdit(row){
      this.form=row
      this.dialogFormVisible=true
    },

    handleSelectionChange(val){
      //console.log(val)
      this.multipleSelection=val
    },
    delBatch(){
      let ids=this.multipleSelection.map(v => v.menuId) //对象数组[{},{},{}]变成 [1,2,3]
      //console.log(ids)
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
      await this.request.delete("/menu/delBatchById",{data:ids}).then(res =>{
        if(res){
          this.$message.success("删除成功")
          this.load()
        }else {
          this.$message.error("删除失败")
        }
      })

    },

    cancel(){
      this.form={};
      this.dialogFormVisible=false;
    },
    handleCurrentChange(pageNo){
      //console.log(pageNo)
      this.pageNo=pageNo
      this.load()
    },
    handleSizeChange(pageSize){
      //console.log(pageSize)
      this.pageSize=pageSize
      this.load()
    },
/*    checkFormEmpty(form){
      for(var i=0;i<document.forms["form"].length-1;i++){
        if(document.forms[i].value===""){
          alert("当前表单不能存在空项")
          document.form[i].focus()
          return false
        }
        return true
      }
    },*/
  }
}
</script>

<style scoped>
.headerBg{
  background: aqua !important;
}
</style>