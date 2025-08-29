package rd.lock;

import congif.RedissonClientInstance;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

public class RedLockDemo {

    @Test
    public void testRedLock() {
        RedissonClient redisson = RedissonClientInstance.getInstance();

        RLock lock1 = redisson.getLock("lock1");
        RLock lock2 = redisson.getLock("lock2");
        RLock lock3 = redisson.getLock("lock3");

// 创建红锁
        RLock redLock = redisson.getRedLock(lock1, lock2, lock3);

        redLock.lock();
        try {
            // 执行业务逻辑
        } finally {
            redLock.unlock();
        }

    }
}
