package com.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.CheckItemService;
import com.health.constant.MessageConstant;
import com.health.entity.PageResult;
import com.health.entity.QueryPageBean;
import com.health.entity.Result;
import com.health.pojo.CheckItem;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
        @Reference//查找服务
        private CheckItemService checkItemService;


        //新增检查项
        @PreAuthorize("hasAuthority('CHECKITEM_ADD')")//权限校验
        @RequestMapping("/add")
        public Result add(@RequestBody CheckItem checkItem){
            try{
                checkItemService.add(checkItem);
            }catch (Exception e){
                e.printStackTrace();
                //服务调用失败
                return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
            }
            return  new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        }

    //检查项分页查询

// @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")//权限校验
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){

        PageResult pageResult = checkItemService.pageQuery(queryPageBean);
        return pageResult;
    }
    //删除检查项
//    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")//权限校验
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try{
            checkItemService.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return  new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    //编辑检查项
//    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")//权限校验
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem){
        try{
            checkItemService.edit(checkItem);
        }catch (Exception e){
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return  new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }


    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
            CheckItem checkItem = checkItemService.findById(id);
            return  new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
        }catch (Exception e){
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/findAll")
    public Result findAll(){
        try{
            List<CheckItem> list = checkItemService.findAll();
            return  new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }




}
