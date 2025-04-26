package com.newcoder.community;

import com.newcoder.community.util.SensitiveFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * ClassName: SensitiveTest
 * Package: com.newcoder.community
 * Description:
 *
 * @author 向鹏
 * @version 1.0
 * @create 2025/4/26 16:05
 */
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SensitiveTest {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitiveFilter() {
        String text = "这里既可以赌博，也可以吸毒，更可以嫖娼";
        text = sensitiveFilter.filter(text);
        System.out.println(text);
    }

    @Test
    public void testSensitiveFilter2() {
        String text = "这里既可以☆★赌★★博★，★也可以★吸😂😂😂毒，更可以嫖￥！@娼";
        text = sensitiveFilter.filter(text);
        System.out.println(text);
    }
}
