package com.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.health.OrderSettingService;
import com.health.dao.OrderSettingDao;
import com.health.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HMR
 * @create 2021/8/5 10:59
 * 预约设置服务
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    //批量添加
    public void add(List<OrderSetting> list) {
        System.out.println("=============add==============");
        if(list != null && list.size() > 0){
            for (OrderSetting orderSetting : list) {
//检查此数据（日期）是否存在
                long count =orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if(count > 0){
//已经存在，执行更新操作
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                }else{
//不存在，执行添加操作
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }
    //根据日期查询预约设置数据
    public List<Map> getOrderSettingByMonth(String date) {
        String dateEnd= date+"-30";
        String day=date.substring(5);
        String [] dayI={"1","3","5","7","8","10","12"};
        for (String dayII:dayI
             ) {
            if(day.equals(dayII)){
                dateEnd = date+"-31";
            }

        }
        if(day.equals("2")){
            dateEnd = date+"-28";
        };
//        if(day.equals("2")){
//            dateEnd = date+"-28";
//        };
//        if (day.equals("1")||day.equals("3")||day.equals("5") || day.equals("7")||day.equals("8") || day.equals("10")||day.equals("12")){
//            dateEnd = date+"-31";
//        }else {
//            dateEnd = date+"-30";
//        }
        String dateBegin = date+"-1";//2021-6-1
        Map map = new HashMap();
        map.put("dateBegin",dateBegin);
        map.put("dateEnd",dateEnd);
        List<OrderSetting> list =  orderSettingDao.getOrderSettingByMonth(map);
        List<Map> data = new ArrayList();
        for(OrderSetting orderSetting:list){
            Map orderSettingMap = new HashMap();
            orderSettingMap.put("date",orderSetting.getOrderDate().getDate());
            orderSettingMap.put("number",orderSetting.getNumber());
            orderSettingMap.put("reservations",orderSetting.getReservations());
            data.add(orderSettingMap);
        }
        return data;
    }
    public void editNumberByDate(OrderSetting orderSetting) {
        long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if(count>0){
            //当前日期已经进行了预约设置，需要进行修改操作
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else{
            //当前日期没有进行预约设置，进行添加操作
            orderSettingDao.add(orderSetting);
        }
    }




}
