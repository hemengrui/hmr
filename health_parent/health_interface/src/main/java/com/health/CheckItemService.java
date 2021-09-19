package com.health;

import com.health.entity.PageResult;
import com.health.entity.QueryPageBean;
import com.health.pojo.CheckItem;

import java.util.List;

//服务接口
public interface CheckItemService {
    void add(CheckItem checkItem);
        //服务接口
    PageResult pageQuery(QueryPageBean queryPageBean);
    void deleteById(Integer id);
    public void edit(CheckItem checkItem);
    public CheckItem findById(Integer id);
    public List<CheckItem> findAll();





}
