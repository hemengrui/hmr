package com.health;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.constant.MessageConstant;
import com.health.constant.RedisMessageConstant;
import com.health.entity.Result;
import com.health.pojo.Order;
import com.health.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @author HMR
 * @create 2021/8/15 9:46
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    @RequestMapping("submit")
    public Result submit(@RequestBody Map map) {

        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        String orderDate = (String) map.get("orderDate");
        System.out.println("===================" + orderDate);
        if (telephone == null || validateCode == null) {
            return new Result(false, MessageConstant.TELEPHONE_VALIDATECODE_NOTNULL);
        }
        //Redis中获取保存的验证码
        String validateCodeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);

        //将用户输入的验证码和Redis中保存的验证码进行比对
        if (validateCodeInRedis != null && validateCodeInRedis.equals(validateCode)) {
            //如果对比成功,调用服务完成预约业务处理
            Result result = null;
            try {
                map.put("orderType", Order.ORDERTYPE_WEIXIN);
                result = orderService.order(map);
            } catch (Exception e) {
                e.printStackTrace();
                return result;
            }
            if (result.isFlag()) {
                //预约成功短信通知
                String[] datas = {orderDate};
                SMSUtils.sendShortMessage(telephone, "1", datas);

            }
            return result;
        } else {
            //如果比对不成功,返回结果给页面
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }

    /**
     * 根据id查询预约详细信息，包括套餐信息和会员信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Map map = orderService.findById(id);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }

    }


}
