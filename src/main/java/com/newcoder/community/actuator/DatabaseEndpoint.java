package com.newcoder.community.actuator;

import com.newcoder.community.util.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * ClassName: DatabaseEndpoint
 * Package: com.newcoder.community.actuator
 * Description:
 *
 * @author 向鹏
 * @version 1.0
 * @create 2025/5/6 18:48
 */
@Component
@Endpoint(id = "database")
public class DatabaseEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseEndpoint.class);

    @Autowired
    private DataSource dataSource;

    @ReadOperation
    public String checkConnection() {
        try (
            Connection connection = dataSource.getConnection();
        ) {
            return CommunityUtil.getJSONString(0,"获取连接成功");
        }catch (SQLException e) {
            logger.error("获取连接失败" + e.getMessage());
            return CommunityUtil.getJSONString(1,"获取连接失败");
        }
    }

}
