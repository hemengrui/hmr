package com.health.dao;

import com.github.pagehelper.Page;
import com.health.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @author HMR
 * @create 2021/7/29 10:05
 */
public interface SetmealDao {
    public void add(Setmeal setmeal);
    public void setSetmealAndCheckGroup(Map map);
    public Page<Setmeal> findByCondition(String queryString);
    public Setmeal findById(Integer id);
    public List<Integer> findSetmealByCheckItemIds(Integer id);
    public void edit(Setmeal setmeal);
    public void deleteTCheckgroupCheckitemBYId(Integer id);
    public List<Setmeal> findAll();
    public Setmeal findById(int id);
    public Setmeal findById4Detail(Integer id);




}
