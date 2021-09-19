package com.health.dao;

import com.github.pagehelper.Page;
import com.health.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    public void add(CheckGroup checkGroup);

    public void setCheckGroupAndCheckItem(Map map);
    public Page<CheckGroup> findByCondition(String queryString);
    public CheckGroup findById(Integer id);
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id);
    public void edit(CheckGroup checkGroup);
    public void deleteAssocication(Integer id);
    void deleteTCheckgroupCheckitemBYId(Integer id);
    void deleteTCheckgroupBYId(Integer id);

    List<CheckGroup> findAll();




}
