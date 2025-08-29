package rd;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class StartDemo {
    public static void main(String[] args) {

        System.out.println("Hello World!");

        // 配置连接本地Redis
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");

        RedissonClient redisson = Redisson.create(config);

        RLock lock = redisson.getLock("myLock");
        lock.lock();
        try {
            System.out.println("拿到锁了，执行临界区逻辑...");
        } finally {
            lock.unlock();
        }

        redisson.shutdown();
    }
}
