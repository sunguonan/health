package com.ydlclass.health.service.impl;

import com.ydlclass.health.common.pojo.Member;
import com.ydlclass.health.common.util.MD5Utils;
import com.ydlclass.health.dao.MemberDao;
import com.ydlclass.health.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sunGuoNan
 * @version 1.0
 */
@Slf4j
@Service
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        String password = member.getPassword();
        if (password != null) {
            String encryptPassword = MD5Utils.md5(password);
            member.setPassword(encryptPassword);
        }
        memberDao.add(member);
    }
}
