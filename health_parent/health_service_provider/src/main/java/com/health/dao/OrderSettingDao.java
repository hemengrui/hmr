package com.health.dao;

import com.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author HMR
 * @create 2021/8/5 11:00
 */
public interface OrderSettingDao {
    /**
     * 根据预约时间查询是否存在预约
     * @param orderDate
     * @return
     */
    public long findCountByOrderDate(Date orderDate);

    /**
     * 添加预约
     * @param orderSetting
     */
    public void add(OrderSetting orderSetting);

    /**
     * 根据预约时间修改预约人数
     * @param orderSetting
     */
    public void editNumberByOrderDate(OrderSetting orderSetting);

    /**
     * 根据月份查询对应的预约设置数据
     * @param map
     * @return
     */
    public List<OrderSetting> getOrderSettingByMonth(Map<String, String> map);

    /**
     * 根据时间查询预约设置信息
     * @param date
     * @return
     */
    public OrderSetting findByOrderDate(Date date);

    /**
     * 更新已预约人数
     * @param orderSetting
     */
    public void editReservationsByOrderDate(OrderSetting orderSetting);
}

