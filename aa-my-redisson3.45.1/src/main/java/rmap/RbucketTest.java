package rmap;

import congif.RedissonClientInstance;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RFuture;
import org.redisson.api.RedissonClient;

public class RbucketTest {


    @Test
    public void testBucket() {
        RedissonClient redisson = RedissonClientInstance.getInstance();

        RBucket<String> bucket = redisson.getBucket("test");  //  这里其实就是一个key，一个key对应一个bucket

        boolean b = bucket.setIfAbsent("hello");

        bucket.set("hello");

        String s = bucket.get();

        RFuture<String> async = bucket.getAsync();


        String value = s; // 从 Redis 取出

        System.out.println("存进去的值是: " + value);

    }
    /**
     * | 方法                   | 同步/异步 | 返回类型            | 阻塞线程 |
     * | -------------------- | ----- | --------------- | ---- |
     * | `getBucket(key)`     | 同步    | `RBucket`       | 无    |
     * | `setIfAbsent(value)` | 同步    | `boolean`       | 阻塞   |
     * | `set(value)`         | 同步    | `void`          | 阻塞   |
     * | `get()`              | 同步    | `V`             | 阻塞   |
     * | `setAsync(value)`    | 异步    | `RFuture<Void>` | 不阻塞  |
     * | `getAsync()`         | 异步    | `RFuture<V>`    | 不阻塞  |
     */
}
