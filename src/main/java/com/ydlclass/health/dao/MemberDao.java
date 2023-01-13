package com.ydlclass.health.dao;

import com.github.pagehelper.Page;
import com.ydlclass.health.common.pojo.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberDao {
    List<Member> findAll();

    Page<Member> selectByCondition(String queryString);

    void add(Member member);

    void deleteById(Integer id);

    Member findById(Integer id);

    Member findByTelephone(String telephone);

    void edit(Member member);

    Integer findMemberCountBeforeDate(String date);

    Integer findMemberCountByDate(String date);

    Integer findMemberCountAfterDate(String date);

    Integer findMemberTotalCount();
}
