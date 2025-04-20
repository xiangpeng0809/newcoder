package com.newcoder.community.service;

import com.newcoder.community.dao.AlphaDao;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: AlphaService
 * Package: com.newcoder.community.service
 * Description:
 *
 * @author 向鹏
 * @version 1.0
 * @create 2025/4/1 18:35
 */
@Service
public class AlphaService {

    @Autowired
    private AlphaDao alphaDao;

    @PostConstruct
    public void init() {

    }

    public String find(){
        return alphaDao.select();
    }
}
