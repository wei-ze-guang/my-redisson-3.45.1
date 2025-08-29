package rd.lock;

import congif.ExecutorConfig;
import congif.RedissonClientInstance;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.ExecutorService;

public class lockDemo {
    public static void main(String[] args) {

        final Producer instance1 = Producer.getInstance();

        final int times = 10;

        ExecutorService instance = ExecutorConfig.getInstance();

        for (int i = 0; i < 10; i++) {
            instance.execute(new Runnable() {
                public void run() {
                    instance1.unlockMethod();
                }
            });
        }

    }
}
