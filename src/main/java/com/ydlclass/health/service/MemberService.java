package com.ydlclass.health.service;

import com.ydlclass.health.common.pojo.Member;

/**
 * @author sunGuoNan
 * @version 1.0
 */
public interface MemberService {
    Member findByTelephone(String telephone);

    void add(Member member);
}
