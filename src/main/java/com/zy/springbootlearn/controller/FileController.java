package com.zy.springbootlearn.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.springbootlearn.common.Result;
import com.zy.springbootlearn.entity.Files;
import com.zy.springbootlearn.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {
    //读取配置文件 application.yml 定义的的文件路径
    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Resource
    private FileMapper fileMapper; //这里不搞service了，因为是小项目
    /**
     * 文件上传
     * @return
     */
    @PostMapping("/upload")//不用包装返回值是因为前端直接拿到字符串返回直接用，包装了反而不好用
    public String upload(@RequestParam MultipartFile file) throws IOException {
        //获取文件原始名称
        String originalFilename = file.getOriginalFilename();
        //获取文件类型
        String type = FileUtil.extName(originalFilename);
        //获取文件大小,long类型对应数据库的bigint
        long size = file.getSize();
        //存储数据库之前，把文件存到硬盘
        File uploadParentFile = new File(fileUploadPath);
        //判断配置的文件目录是否存在，若不存在，则创建目录
        if (!uploadParentFile.exists()) {
            uploadParentFile.mkdirs();
        }
        //定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileName = uuid + StrUtil.DOT + type;//类似 12345 . xlsx
        //通过路径定义要存的文件，如果 因为还没有存（file.transferTo(uploadFile);） ，所以此处文件为空，下面获取不到
        File uploadFile = new File(fileUploadPath + fileName);//类似 D:\JAVA\SpringBootLearn_store_files\UploadFiles\   12345.xlsx
        //先把文件存入磁盘，方便读取md5
        file.transferTo(uploadFile);
        //获取每个文件的MD5
        String md5 = SecureUtil.md5(uploadFile);
        //到数据库查询是否有相同的MD5，若有，则不存了，但是数据库记录要存
        QueryWrapper<Files> wrapper = new QueryWrapper<>();
        wrapper.eq("file_md5", md5);
        List<Files> list = fileMapper.selectList(wrapper);

        //获取url
        String url;
        //判定获取list是否为空
        if (list != null && list.size () !=0) {
            url = list.get(0).getFileUrl();
            //删除磁盘重复的文件，因为已经有相同的文件了
            uploadFile.delete();
        } else {
            //说明文件不存在，是新文件，不用删除刚刚存入的文件
            url = "http://localhost:9090/file/download/" + fileName;
        }

        //将一些参数存储到数据库
        Files saveFile = new Files();
        saveFile.setFileName(originalFilename);
        saveFile.setFileType(type);
        saveFile.setFileSize(size / 1024); //设置成kb
        saveFile.setFileUrl(url);
        saveFile.setFileMd5(md5);
        //将实体类插入到数据库
        fileMapper.insert(saveFile);

        //返回文件URL "http://localhost:9090/file/download/"+uuid
        return url;
    }

    /**
     * 文件下载
     * @param fileName
     * @param response
     * @throws IOException
     */
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        //根据文件的唯一标识码（已经写成了文件名）获取需要下载的文件
        File downloadFile = new File(fileUploadPath + fileName);
        //将文件写入到 浏览器响应流，设置输出的格式
        ServletOutputStream outputStream = response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
        response.setContentType("application/octet-stream");

        //读取文件的字节流
        outputStream.write(FileUtil.readBytes(downloadFile));
        outputStream.flush();
        outputStream.close();
    }

/*    @GetMapping("/selectById/{fileId}")
    public Result selectById(@PathVariable Integer fileId){
        Files file = fileMapper.selectById(fileId);
        return Result.success(file);
    }*/

    /**
     * 通过id删除数据库文件，用于表格内每条数据后的删除按钮
     * @param uId
     * @return
     */
    @DeleteMapping("/del/{uId}")
    public Result delete(@PathVariable Integer uId){
        fileMapper.deleteById(uId);
        return Result.success();
    }

    /**
     * 批量删除，用于多选框勾选删除
     * @param ids
     * @return
     */
    @DeleteMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        fileMapper.deleteBatchIds(ids);
        return Result.success();
    }

    /**
     * 分页
     * @param pageNo
     * @param pageSize
     * @param fileName
     * @return
     */
    @GetMapping("/page")
    //@RequestParam 相当于接收 ?pageNo=1&pageSize=5 这样的参数
    public Result findPage(@RequestParam Integer pageNo,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String fileName){
        Page<Files> page = new Page<>(pageNo,pageSize);
        System.out.println(fileName);
        //设置条件构造器
        QueryWrapper<Files> wrapper = new QueryWrapper<>();
        if(!fileName.equals(""))
            wrapper.like("file_name",fileName);//这里使用数据库字段名，不要用User属性名
        //在模糊查询出来的表格里分页
        fileMapper.selectPage(page,wrapper);
        HashMap<String, Object> res = new HashMap<>();
        res.put("total",page.getTotal());
        List<Files> list=page.getRecords();
        int fileSid=1;
        for (Files file:list){
            list.get(fileSid-1).setFileSid(fileSid++);
        }
        res.put("data",list);
        return Result.success(res);
    }

    //修改字段数据
    @PostMapping("/save")
    public Result save(@RequestBody Files file){
        fileMapper.updateById(file);
        return Result.success();
    }

}
