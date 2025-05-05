package com.newcoder.community;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.TimeUnit;

import static org.springframework.data.redis.util.ByteUtils.getBytes;

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

    // 统计20万个重复数据的独立总数
    @Test
    public void testHyperLoglog() {
        String redisKey = "test:hyperloglog:01";

        for (int i = 1; i <= 100000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey, i);
        }

        for (int i = 1; i <= 100000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey, i);
        }

        System.out.println(redisTemplate.opsForHyperLogLog().size(redisKey));
    }

    // 将3组数据合并，再统计合并后的重复数据的独立总数
    @Test
    public void testHyperLoglogUnion() {
        String redisKey2 = "test:hyperloglog:02";

        for (int i = 1; i <= 10000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey2, i);
        }

        String redisKey3 = "test:hyperloglog:03";

        for (int i = 5001; i <= 15000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey3, i);
        }

        String redisKey4 = "test:hyperloglog:04";

        for (int i = 10001; i <= 20000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey4, i);
        }

        String unionKey = "test:hyperloglog:union";

        redisTemplate.opsForHyperLogLog().union(unionKey, redisKey2, redisKey3, redisKey4);

        long size = redisTemplate.opsForHyperLogLog().size(unionKey);
        System.out.println(size);
    }

    // 统计一组数据的布尔值
    // 可以做签到，每个月哪天签到了
    @Test
    public void testBitMap(){
        String redisKey = "test:bitmap";

        // 记录
        redisTemplate.opsForValue().setBit(redisKey, 0, true);
        redisTemplate.opsForValue().setBit(redisKey, 4, true);
        redisTemplate.opsForValue().setBit(redisKey, 7, true);

        // 查询
        System.out.println(redisTemplate.opsForValue().getBit(redisKey, 0));
        System.out.println(redisTemplate.opsForValue().getBit(redisKey, 1));
        System.out.println(redisTemplate.opsForValue().getBit(redisKey, 4));

        // 统计
//        Object obj = redisTemplate.execute(new RedisCallback() {
//            @Override
//            public Object doInRedis(RedisConnection connection) throws DataAccessException {
//                return connection.stringCommands().bitCount(redisKey.getBytes());
//            }
//        });
        Object obj = redisTemplate.execute((RedisCallback<Object>) connection ->
                connection.stringCommands().bitCount(redisKey.getBytes())
        );
        System.out.println(obj);
    }

    // 统计3组数据的布尔值，并对这3组数据做OR运算
    @Test
    public void testBitMapOperation() {
        String redisKey2 = "test:bitmap:02";
        redisTemplate.opsForValue().setBit(redisKey2, 0, true);
        redisTemplate.opsForValue().setBit(redisKey2, 1, true);
        redisTemplate.opsForValue().setBit(redisKey2, 2, true);

        String redisKey3 = "test:bitmap:03";
        redisTemplate.opsForValue().setBit(redisKey3, 2, true);
        redisTemplate.opsForValue().setBit(redisKey3, 3, true);
        redisTemplate.opsForValue().setBit(redisKey3, 4, true);

        String redisKey4 = "test:bitmap:04";
        redisTemplate.opsForValue().setBit(redisKey4, 4, true);
        redisTemplate.opsForValue().setBit(redisKey4, 5, true);
        redisTemplate.opsForValue().setBit(redisKey4, 6, true);

        String redisKey = "test:bitmap:or";
        Object obj = redisTemplate.execute((RedisCallback<Object>) connection -> {
                    connection.stringCommands().bitOp(
                            RedisStringCommands.BitOperation.OR,
                            redisKey.getBytes(),
                            redisKey2.getBytes(),
                            redisKey3.getBytes(),
                            redisKey4.getBytes()
                    );
                    return connection.stringCommands().bitCount(redisKey.getBytes());
        });

        System.out.println(obj);

        for (int i = 0; i < 7; i++) {
            System.out.println(redisTemplate.opsForValue().getBit(redisKey, i));
        }
    }
}
