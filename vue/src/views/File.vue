<template>
  <div>
    <div style="padding: 10px 0">
      <el-input v-model="fileName" style="width: 300px" placeholder="请输入文件名" suffix-icon="el-icon-search" clearable @keydown.enter.native="load"></el-input>
      <el-button style="margin-left: 10px" type="primary" @click="load">search</el-button>
    </div>

    <div style="padding: 10px 0">
      <el-button type="primary" @click="delBatch">批量删除 <i class="el-icon-remove-outline"></i></el-button>

      <el-upload
          class="upload-demo"
          action="http://localhost:9090/file/upload"
          :auto-upload=true
          :show-file-list="false"
          :on-success="handleImportSuccess"
          :on-error="handleImportError"
          style="display: inline-block">
        <el-button type="primary" style="margin-left: 10px">上传文件<i class="el-icon-upload el-icon--right"></i></el-button>
      </el-upload>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
      <el-table-column width="50" type="selection" align="center">
      </el-table-column>
      <el-table-column prop="fileSid"  align="center" label="ID" width="80" >
      </el-table-column>
      <el-table-column prop="fileId"  align="center" label="ID" v-if="false">
      </el-table-column>
      <el-table-column prop="fileName" align="center" label="文件名" width="140px" >
      </el-table-column>
      <el-table-column prop="fileType" align="center" label="文件类型" width="120px">
      </el-table-column>
      <el-table-column prop="fileSize" align="center" label="文件大小" width="100px">
      </el-table-column>
      <el-table-column prop="updateTime" align="center" label="时间">
      </el-table-column>
      <el-table-column align="center" label="启用">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ff4949" @change="updateEnable(scope.row)"></el-switch>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
<!--          //file/download/'+scope.row.fileUrl-->
          <el-button type="primary" @click="downloadData(scope.row.fileUrl)" plain>下载<i class="el-icon-download el-icon--right"></i></el-button>
          <el-button type="danger" @click="deleteData(scope.row.fileId)" plain>删除<i class="el-icon-delete"></i></el-button>
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
  </div>
</template>

<script>
export default {
  name: "File",
  data(){
    return{
      fileName:"",
      tableData: [],
      enableChange:true,
      total: 0,
      pageNo:1,
      pageSize:10,
      multipleSelection:[],
    }
  },
  created() {
    this.load()
  },
  methods:{

    //加载数据
    load(){
      console.log("刷新并加载表格数据。。。")
      this.request.get("/file/page",{
        params:{
          pageNo:this.pageNo,
          pageSize:this.pageSize,
          fileName:this.fileName
        }
      }).then(res =>{
        console.log(res);
        this.tableData = res.data.data;
        this.total=res.data.total;
      })
    },



    //多选
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection=val
    },
    //批量删除，对要删除的id进行格式化，调用删除方法
    delBatch(){
      let ids=this.multipleSelection.map(v => v.fileId) //对象数组[{},{},{}]变成 [1,2,3]
      console.log(ids)
      if (ids.length !==  0){
        this.deleteBatchData(ids)
      } else {
        this.$message.error("请勾选数据！")
      }
    },
    //根据id批量删除
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
      await this.request.delete("/file/del/batch",{data:ids}).then(res =>{
        if(res){
          this.$message.success("删除成功")
          this.load()
        }else {
          this.$message.error("删除失败")
        }
      })

    },

    //下载数据（此处的url就是下载链接）
    downloadData(fileUrl){
      window.open(fileUrl)
    },

    //根据id删除单条数据
    async deleteData(fileId){
      const info = await this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true,

      }).catch(err=>err)
      if (info!=="confirm"){
        return this.$message.error('已取消删除')
      }
      await this.request.delete("/file/del/"+fileId).then(res =>{
        if(res){
          this.$message.success("删除成功")
          this.load()
        }else {
          this.$message.error("删除失败")
        }
      })

    },



    //页数调整
    handleCurrentChange(pageNo){
      console.log(pageNo)
      this.pageNo=pageNo
      this.load()
    },
    //页面表格尺码调整
    handleSizeChange(pageSize){
      console.log(pageSize)
      this.pageSize=pageSize
      this.load()
    },



    //增添文件的提示
    handleImportSuccess(){
      this.$message.success("上传成功")
      this.load()
    },
    handleImportError(){
      this.$message.error("上传失败")
    },


    /**
     * 修改数据库数据(主要用于修改enable值使其是否启用的状态改变)
     * 此处不需要手动改变file.enable的值，因为
     * @param file
     */
    updateEnable(file){
      this.request.post("http://localhost:9090/file/save",file)
      this.load();
    }
  }

}
</script>

<style scoped>

</style>