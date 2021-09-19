package com.health;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.constant.MessageConstant;
import com.health.entity.Result;
import com.health.pojo.Setmeal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmealK")
public class SetmealController {
    @Reference//(check = false)
    private SetmealService setmealService;

    //获取所有套餐信息
    @RequestMapping("/getAllSetmeal")
    public Result getSetmeal() {
        try {
            List<Setmeal> list = setmealService.findAll();
            return new Result(true,
                    MessageConstant.GET_SETMEAL_LIST_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new
                    Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }

    //根据ID查询检查套餐
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, setmeal);//查询成功
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);//查询失败
        }
    }
}
