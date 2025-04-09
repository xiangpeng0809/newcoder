package com.newcoder.community.dao.impl;

import com.newcoder.community.dao.AlphaDao;
import org.springframework.stereotype.Repository;
/**
 * ClassName: AlphaDaoImpl
 * Package: com.newcoder.community.dao.impl
 * Description:
 *
 * @author 向鹏
 * @version 1.0
 * @create 2025/4/1 18:26
 */
@Repository
public class AlphaDaoImpl implements AlphaDao {
    @Override
    public String select() {
        return "thisisok";
    }
}
