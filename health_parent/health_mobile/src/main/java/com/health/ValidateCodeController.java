package com.health;

import com.health.constant.MessageConstant;
import com.health.constant.RedisMessageConstant;
import com.health.entity.Result;
import com.health.utils.SMSUtils;
import com.health.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @author HMR
 * @create 2021/8/15 9:40
 * 验证码操作
 */

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    //用户在线体检预约发送验证码
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone) {
        //随机生成4位数字验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(4);
        //给用户发送验证码
        String[] datas = {validateCode.toString()};
        try {
            SMSUtils.sendShortMessage(telephone, "1", datas);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        //将验证码保存到redis（5分钟）
        jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_ORDER, 300000, validateCode.toString());
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    //手机快速登录时发送手机验证码
    @RequestMapping("/send4Login")
    public Result send4Login(String telephone) {
        //随机生成4位数字验证码
        Integer Code = ValidateCodeUtils.generateValidateCode(4);
        //给用户发送验证码
        String[] datas = {Code.toString()};
        try {
            SMSUtils.sendShortMessage(telephone, "1", datas);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        //将验证码保存到redis（5分钟）
        jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_ORDER, 300000, Code.toString());
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);

    }

}

