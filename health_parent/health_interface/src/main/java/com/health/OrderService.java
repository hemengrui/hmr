package com.health;

import com.health.entity.Result;

import java.util.Map;

/**
 * @author HMR
 * @create 2021/8/15 9:47
 */
public interface OrderService {
    //体检预约
    public Result order(Map map) throws Exception;
    //根据id查询预约信息，包括体检人信息、套餐信息
    public Map findById(Integer id) throws Exception;


}
