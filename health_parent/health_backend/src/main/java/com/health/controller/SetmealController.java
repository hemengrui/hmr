package com.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.SetmealService;
import com.health.constant.MessageConstant;
import com.health.constant.RedisConstant;
import com.health.entity.PageResult;
import com.health.entity.QueryPageBean;
import com.health.entity.Result;
import com.health.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
/**
 * @author HMR
 * @create 2021/7/29 10:02
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;
    //使用JedisPool操作Redis服务
    @Autowired
    private JedisPool jedisPool;


    //文件上传
//    @RequestMapping("/upload")
//    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
//        System.out.println(imgFile);
//        String originalFilename = imgFile.getOriginalFilename();//原始文件名 3bd90d2c-4e82-42a1-a401-882c88b06a1a2.jpg
//        int index = originalFilename.lastIndexOf(".");
//        String extention = originalFilename.substring(index - 1);//.jpg
//        String fileName = UUID.randomUUID().toString() + extention;//  FuM1Sa5TtL_ekLsdkYWcf5pyjKGu.jpg
//        try {
//            //将文件上传到七牛云服务器
//            com.health.utils.QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
//        }
//        return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
//    }
    //新增套餐
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        try{
            setmealService.add(setmeal,checkgroupIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return setmealService.pageQuery(queryPageBean);
    }


    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        System.out.println(imgFile);
        String originalFilename = imgFile.getOriginalFilename();//原始文件名 3bd90d2c-4e82-42a1-a401-882c88b06a1a2.jpg
        int index = originalFilename.lastIndexOf(".");
        String extention = originalFilename.substring(index - 1);//.jpg
        String fileName = UUID.randomUUID().toString() + extention;//  FuM1Sa5TtL_ekLsdkYWcf5pyjKGu.jpg
        try {
            //将文件上传到七牛云服务器
            com.health.utils.QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
    }

    //根据ID查询检查套餐
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,setmeal);//查询成功
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);//查询失败
        }
    }


    //根据检查组ID查询检查组包含的多个检查项ID
    @RequestMapping("/findSetmealByCheckItemIds")
    public Result findSetmealByCheckItemIds(Integer id){
        try{
            System.out.println("Result findSetmealByCheckItemIds(Integer id"+id);
            List<Integer> checkitemIds = setmealService.findSetmealByCheckItemIds(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkitemIds);//查询成功
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);//查询失败
        }
    }
    //编辑检查组
    @RequestMapping("/edit")
    public Result edit(@RequestBody Setmeal setmeal, Integer[] checkitemIds){
        try{
            setmealService.edit(setmeal,checkitemIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);//新增失败
        }
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);//新增成功
    }


}
