package com.newcoder.community;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.TimeUnit;

/**
 * ClassName: RedisTest
 * Package: com.newcoder.community
 * Description:
 *
 * @author 向鹏
 * @version 1.0
 * @create 2025/5/2 22:06
 */
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testStrings() {
        String redisKey = "test:count";

        redisTemplate.opsForValue().set(redisKey, 1);

        System.out.println(redisTemplate.opsForValue().get(redisKey));
        System.out.println(redisTemplate.opsForValue().increment(redisKey, 1));
        System.out.println(redisTemplate.opsForValue().decrement(redisKey, 1));
    }

    @Test
    public void testHash() {
        String redisKey = "test:hash";

        redisTemplate.opsForHash().put(redisKey, "key1", "value1");
        System.out.println(redisTemplate.opsForHash().get(redisKey, "key1"));
    }

    @Test
    public void testList() {
        String redisKey = "test:list";
        redisTemplate.opsForList().leftPush(redisKey, 101);
        redisTemplate.opsForList().leftPush(redisKey, 102);
        redisTemplate.opsForList().leftPush(redisKey, 103);
        System.out.println(redisTemplate.opsForList().size(redisKey));
        System.out.println(redisTemplate.opsForList().index(redisKey,0));
        System.out.println(redisTemplate.opsForList().range(redisKey,0,2));
        System.out.println(redisTemplate.opsForList().leftPop(redisKey));
    }

    @Test
    public void testSet() {
        String redisKey = "test:set";

        redisTemplate.opsForSet().add(redisKey, "刘备", "关羽", "张飞");

        System.out.println(redisTemplate.opsForSet().size(redisKey));
        System.out.println(redisTemplate.opsForSet().pop(redisKey));
        System.out.println(redisTemplate.opsForSet().members(redisKey));
    }

    @Test
    public void testZSet() {
        String redisKey = "test:zset";

        redisTemplate.opsForZSet().add(redisKey, "a", 1);
        redisTemplate.opsForZSet().add(redisKey, "b", 2);
        redisTemplate.opsForZSet().add(redisKey, "c", 3);
        redisTemplate.opsForZSet().add(redisKey, "d", 4);

        System.out.println(redisTemplate.opsForZSet().zCard(redisKey));
        System.out.println(redisTemplate.opsForZSet().score(redisKey, "a"));
        System.out.println(redisTemplate.opsForZSet().reverseRank(redisKey, "b"));
        System.out.println(redisTemplate.opsForZSet().reverseRange(redisKey, 0, 2));
    }

    @Test
    public void testkeys() {
        redisTemplate.delete("test:count");

        System.out.println(redisTemplate.hasKey("test:count"));

        redisTemplate.expire("test:hash", 10, TimeUnit.SECONDS);
    }

    // 多次范文同一个key
    @Test
    public void testBoundOperations() {
        String redisKey = "test:count";
        BoundValueOperations operations = redisTemplate.boundValueOps(redisKey);
        operations.increment();
        operations.increment();
        operations.increment();
        operations.increment();
        operations.increment();
        System.out.println(operations.get());

    }

    // 编程式事务
    @Test
    public void testTransaction() {
        Object obj = redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String Rediskey = "test:transaction";
                operations.multi();

                operations.opsForSet().add(Rediskey, "a");
                operations.opsForSet().add(Rediskey, "b");
                operations.opsForSet().add(Rediskey, "c");

                System.out.println(operations.opsForSet().members(Rediskey));
                return operations.exec();
            }
        });
        System.out.println(obj);
    }
}
