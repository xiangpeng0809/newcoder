package com.newcoder.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * ClassName: CommunityUtil
 * Package: com.newcoder.community.util
 * Description:
 *
 * @author 向鹏
 * @version 1.0
 * @create 2025/4/11 17:55
 */
public class CommunityUtil {

    // 生成随机的字符串
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // MD5加密, 只能加密，不能解密
    // hello -> abc123def456
    // MD5在加密的过程中随机生成几个字符串例如 hello + 3e4a8 -> abc123def456abc
    public static String md5(String key) {
        // 判断key是否为空，"", " "
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

}
