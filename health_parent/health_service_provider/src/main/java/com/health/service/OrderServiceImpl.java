package com.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.health.OrderService;
import com.health.constant.MessageConstant;
import com.health.dao.MemberDao;
import com.health.dao.OrderDao;
import com.health.dao.OrderSettingDao;
import com.health.entity.Result;
import com.health.pojo.Member;
import com.health.pojo.Order;
import com.health.pojo.OrderSetting;
import com.health.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author HMR
 * @create 2021/8/15 9:47
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    /**
     * 体检预约
     *
     * @param map
     * @return
     * @throws Exception
     */
    public Result order(Map map) throws Exception {
        //1. 检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
        String orderDate = (String) map.get("orderDate");
        System.out.println("===========orderDate========="+orderDate);
        Date orderTime = DateUtils.parseString2Date(orderDate);
        System.out.println(orderTime);

        OrderSetting orderSetting = orderSettingDao.findByOrderDate(orderTime);
        if (orderSetting == null) {
            //指定日期没有进行预约设置,无法完成体检预约
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //2. 检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
        int number = orderSetting.getNumber();
        int reservations = orderSetting.getReservations();
        if (reservations >= number) {
            //已经约满,无法预约
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        //3. 检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
        //获取用户输入的手机号
        String telephone = (String) map.get("telephone");
        Member member = memberDao.findByTelephone(telephone);
        if (member != null) {
            //判断是否重复预约
            Integer id = member.getId();
            Integer setmealId = Integer.parseInt( map.get("setmealId").toString());
            Order order = new Order(id, orderTime, setmealId);
            //根据条件进行查询
            List<Order> orderList = orderDao.findByCondition(order);
            if (orderList != null && orderList.size() > 0) {
                //说明用户在重复预约,无法完成再次预约
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        } else {
            //4. 检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
            member = new Member();
            member.setName((String) map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegTime(new Date());
            //自动完成会员注册
            memberDao.add(member);
        }
        //5. 预约成功，更新当日的已预约人数
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(orderTime);
        order.setOrderType((String)map.get("orderType"));
        order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));
        orderDao.add(order);
        //更新当日的已预约人数
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
    }

    public Map findById(Integer id) throws Exception {
        Map map = orderDao.findById4Detail(id);
        //处理日期格式
        if (map != null) {
            Date orderDate = (Date) map.get("orderDate");
            map.put("orderDate", DateUtils.parseDate2String(orderDate));
        }
        return map;

    }
}