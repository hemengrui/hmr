package com.health;

import com.health.entity.PageResult;
import com.health.entity.QueryPageBean;
import com.health.pojo.Setmeal;

import java.util.List;

/**
 * @author HMR
 * @create 2021/7/29 10:04
 */
public interface SetmealService {
    public void add(Setmeal setmeal, Integer[] checkgroupIds);
    public PageResult pageQuery(QueryPageBean queryPageBean);
    public Setmeal findById(Integer id);
    List<Integer> findSetmealByCheckItemIds(Integer id);
    public void edit(Setmeal setmeal, Integer[] checkitemIds);
    List<Setmeal> findAll();
    public Setmeal findById(int id);


}


