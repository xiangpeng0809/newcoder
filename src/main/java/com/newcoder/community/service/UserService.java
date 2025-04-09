package com.newcoder.community.service;

import com.newcoder.community.dao.UserMapper;
import com.newcoder.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: UserService
 * Package: com.newcoder.community.service
 * Description:
 *
 * @author 向鹏
 * @version 1.0
 * @create 2025/4/4 17:20
 */
@Service
public class UserService{

    @Autowired
    private UserMapper userMapper;

    public User finderUserById(int id){
      return userMapper.selectById(id);
    }

}
