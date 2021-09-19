package com.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.MemberService;
import com.health.constant.MessageConstant;
import com.health.entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author HMR
 * @create 2021/8/24 9:51
 *
 *   统计报表
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    private MemberService memberService;
    /**
      * 会员数量统计
      * @return
     */
    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){

        Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.MONTH,-12);//获取当前日期之前12个月的日期
        List<String> list=new ArrayList<>();
        for (int i = 0; i <12 ; i++) {
            calendar.add(Calendar.MONTH,1);
            list.add(new SimpleDateFormat("yyyy.MM").format(calendar.getTime()));
        }
        Map<String ,Object> map=new HashMap<>();
        map.put("months",list);
        List<Integer> memberCount =memberService.findMemberCountByMonth(list);
        map.put("memberCount",memberCount);

        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map); }







}
