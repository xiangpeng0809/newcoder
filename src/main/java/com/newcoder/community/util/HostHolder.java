package com.newcoder.community.util;

import com.newcoder.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * ClassName: HostHolder
 * Package: com.newcoder.community.util
 * Description:
 * 持有用户信息，用于代替session对象
 * @author 向鹏
 * @version 1.0
 * @create 2025/4/20 16:25
 */
@Component
public class HostHolder {

    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user) {
        users.set(user);
    }

    public User getUser() {
        return users.get();
    }

    public void clear() {
        users.remove();
    }
}
