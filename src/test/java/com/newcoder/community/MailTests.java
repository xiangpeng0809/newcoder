package com.newcoder.community;

import com.newcoder.community.util.MailClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * ClassName: MailTests
 * Package: com.newcoder.community
 * Description:
 *
 * @author 向鹏
 * @version 1.0
 * @create 2025/4/11 16:07
 */
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTests {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testTextMail() {
        mailClient.sendMail("xpeng0809@163.com", "TEST", "Welcome.");
    }

    @Test
    public void testHtmlMail() {
        Context context = new Context();
        context.setVariable("username", "monday");

        String content = templateEngine.process("mail/demo", context);
        System.out.println(content);

        mailClient.sendMail("xpeng0809@163.com", "HTML", content);
    }
}
