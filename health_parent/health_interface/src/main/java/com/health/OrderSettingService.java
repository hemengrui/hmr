package com.health;

import com.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author HMR
 * @create 2021/8/5 10:58
 */
public interface OrderSettingService {
     void add(List<OrderSetting> list);
     public List<Map> getOrderSettingByMonth(String date);//参数格式为：2019‐03
     public void editNumberByDate(OrderSetting orderSetting);

}
