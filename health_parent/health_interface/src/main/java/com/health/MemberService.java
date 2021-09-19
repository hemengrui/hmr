package com.health;

import com.health.pojo.Member;

import java.util.List;

/**
 * @author HMR
 * @create 2021/8/20 14:58
 */
public interface MemberService {
    public void add(Member member);
    public Member findByTelephone(String telephone);
    public List<Integer> findMemberCountByMonth(List<String> month);


}
