package com.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.CheckGroupService;
import com.health.constant.MessageConstant;
import com.health.entity.PageResult;
import com.health.entity.QueryPageBean;
import com.health.entity.Result;
import com.health.pojo.CheckGroup;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author HMR
 * @create 2021/7/25 9:41
 */
    @RestController
    @RequestMapping("/checkgroup")
    public class CheckGroupController {
        @Reference
        private CheckGroupService checkGroupService;

        /**新增检查组*/
        @RequestMapping("/add")
        public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
            try {
                checkGroupService.add(checkGroup, checkitemIds);
            } catch (Exception e) {
                e.printStackTrace();
                //新增失败
                return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);

            }
            //新增成功
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);

        }
    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return checkGroupService.pageQuery(queryPageBean);
    }
    //根据ID查询检查组
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
            CheckGroup checkGroup = checkGroupService.findById(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);//查询成功
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);//查询失败
        }
    }

    //根据检查组ID查询检查组包含的多个检查项ID
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id){
        try{
            List<Integer> checkitemIds = checkGroupService.findCheckItemIdsByCheckGroupId(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkitemIds);//查询成功
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);//查询失败
        }
    }

    //编辑检查组
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try{
            checkGroupService.edit(checkGroup,checkitemIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);//新增失败
        }
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);//新增成功
    }
    // public static final String DELETE_CHECKGROUP_FAIL = "删除检查组失败";
    //    public static final String DELETE_CHECKGROUP_SUCCESS = "删除检查组成功";
    //删除检查项
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try{
            checkGroupService.deleteById(id);
            System.out.println();
        }catch (Exception e){
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return  new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    //查询所有检查组
    @RequestMapping("/findAll")
    public Result findAll(){
        try{
            List<CheckGroup> list = checkGroupService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);//查询成功
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);//查询失败
        }
    }



}


