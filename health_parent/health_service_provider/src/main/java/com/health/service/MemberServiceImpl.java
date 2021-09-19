package com.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.health.MemberService;
import com.health.dao.MemberDao;
import com.health.pojo.Member;
import com.health.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author HMR
 * @create 2021/8/20 14:58
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;

    //根据手机号查询会员
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    public List<Integer> findMemberCountByMonth(List<String> month) {


        List<Integer> list = new ArrayList();
        for (String m : month) {
            //获取当前月的最大天数
            int year = Integer.parseInt(m.split("\\.")[0]);
            int mon = Integer.parseInt(m.split("\\.")[1]);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DATE, 1);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, mon - 1);
            int maxDate = calendar.getActualMaximum(Calendar.DATE);


            m = m + "." + maxDate;//格式：2019.04.31
            Integer count = memberDao.findMemberCountBeforeDate(m);
            list.add(count);
        }
        return list;
    }



//        Map<String,String> mou=new HashMap<String,String>();
//        mou.put("01",".31");
//        mou.put("03",".31");
//        mou.put("04",".30");
//        mou.put("05",".31");
//        mou.put("06",".30");
//        mou.put("07",".31");
//        mou.put("08",".31");
//        mou.put("09",".30");
//        mou.put("10",".31");
//        mou.put("11",".30");
//        mou.put("12",".31");
//        List<Integer> list = new ArrayList<Integer>();
//        for(String m : month){
//            //格式：2019.04.31
//            if(Integer.parseInt(m.substring(0,4))%4==0){
//                mou.put("02",".29");
//            }else {
//                mou.put("02",".28");
//            }
//            m = m +mou.get(m.substring(5));
//            System.out.println("------------------"+m);
//            Integer count = memberDao.findMemberCountBeforeDate(m);
//                list.add(count);
//        }
//            return list;


    //新增会员
    public void add(Member member) {
        if (member.getPassword() != null) {
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        memberDao.add(member);

    }
}

