<template>
  <div>
    <div style="padding: 10px 0">
      <el-input v-model="roleName" style="width: 300px" placeholder="请输入用户名" suffix-icon="el-icon-search" clearable @keydown.enter.native="load"></el-input>
      <el-button style="margin-left: 10px" type="primary" @click="load">search</el-button>
    </div>

    <div style="padding: 10px 0">
      <el-button type="primary" @click="dialogFormVisible = true">增加 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-button type="primary" @click="delBatch">批量删除 <i class="el-icon-remove-outline"></i></el-button>

    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
      <el-table-column width="50" type="selection" align="center">
      </el-table-column>
      <el-table-column  align="center" label="ID" width="80" >
        <template slot-scope="scope">
          {{(scope.$index+1)+(pageNo-1)*pageSize}}
        </template>
      </el-table-column>
      <el-table-column prop="roleId" align="center" label="ID2" v-if="false">
      </el-table-column>
      <el-table-column prop="roleName" align="center" label="姓名" width="150" >
      </el-table-column>
      <el-table-column prop="roleDescription" align="center" label="描述" width="400">
      </el-table-column>

      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="info" @click="selectMenu(scope.row.roleId)">分配菜单<i class="el-icon-menu"></i></el-button>
          <el-button type="text" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
          <el-button type="danger" @click="deleteBatchData([scope.row.roleId])" >删除<i class="el-icon-delete"></i></el-button>
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
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="40%" :before-close="cancel" custom-class="class_radius">
      <el-form :model="{form}"  label-width="80px" size="big" >
        <el-form-item label="用户名" >
          <el-input v-model="form.roleName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述" >
          <el-input v-model="form.roleDescription" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer" style="text-align: center">
        <el-button @click="cancel()">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="菜单分配" :visible.sync="MenuDialogVisible" width="40%" :before-close="cancel" custom-class="class_radius">
      <el-tree
          :props="props"
          :data="menuData"
          show-checkbox
          node-key="id"
          :default-expanded-keys="[1,2]"
          @check-change="handleCheckChange">
      </el-tree>

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
      roleName:"",
      tableData: [],
      total: 0,
      pageNo:1,
      pageSize:10,
      dialogFormVisible: false,
      MenuDialogVisible: false,
      form:{},
      multipleSelection:[],
      menuData:[],
      props:{
        label:"menuName"
      },//指定节点标签为节点对象的某个属性值
/*      menuData: [{
        id: 1,
        label: '主页',
        children: []
      },{
        id: 2,
        label: '系统管理',
        children: [{
          id: 3,
          label: '用户管理',
          children: []
        },{
          id: 4,
          label: '角色管理',
          children: []
        },{
          id: 5,
          label: '菜单管理',
          children: []
        },{
          id: 6,
          label: '文件管理',
          children: []
        }]
      }],*/
/*      defaultProps: {
        children: 'children',
        label: 'label'
      }*/
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("/role/page",{
        params:{
          pageNo:this.pageNo,
          pageSize:this.pageSize,
          roleName:this.roleName
        }
      }).then(res =>{
        //console.log(res);
        this.tableData = res.data.records;
        this.total=res.data.total;
      })
    },

    save(){
      if(this.form.length!==0){
        this.request.post("/role/saveOrUpdate",this.form).then(res =>{
          if(res.code==="200"){
            this.$message.success("保存成功")
          }else {
            this.$message.error("保存失败")
          }
          this.load();
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
      let ids=this.multipleSelection.map(v => v.roleId) //对象数组[{},{},{}]变成 [1,2,3]
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
      await this.request.delete("/role/delBatchById",{data:ids}).then(res =>{
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
    selectMenu(roleId){
      this.MenuDialogVisible=true
      //请求菜单数据
      this.request.get("/menu/findAll",{
        params:{
          menuName:""
        }
      }).then(res =>{
        this.menuData = res.data;
      })

    },

    handleCheckChange(data, checked, indeterminate) {
      console.log(data, checked, indeterminate);
    },

  }
}
</script>

<style>
.headerBg{
  background: aqua !important;
}

.class_radius{
  border-radius: 13px;
}
</style>