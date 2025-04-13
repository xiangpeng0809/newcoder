package com.newcoder.community.dao;

import com.newcoder.community.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: UserMapper
 * Package: com.newcoder.community.dao
 * Description:
 *
 * @author 向鹏
 * @version 1.0
 * @create 2025/4/4 15:38
 */
@Mapper
public interface UserMapper {

    User selectById(int id);

    User selectByName(String username);

    User selectByEmail(String email);

    int insertUser(User user);

    int updateStatus(int id, int status);

    int updateHeader(int id, String headerUrl);

    int updatePassword(int id, String password);
}
