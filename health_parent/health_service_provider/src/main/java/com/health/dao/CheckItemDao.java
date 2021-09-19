package com.health.dao;

import com.github.pagehelper.Page;
import com.health.pojo.CheckItem;

import java.util.List;


public interface CheckItemDao {
     void add(CheckItem checkItem);

     Page<CheckItem> selectByCondition(String queryString);
     public void deleteById(Integer id);
     public long findCountByCheckItemId(Integer id);
     public void edit(CheckItem checkItem);
     public CheckItem findById(Integer id);
     public List<CheckItem> findAll();




}

