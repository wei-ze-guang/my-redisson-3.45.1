package congif;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author 86199
 */
public class RedissonClientInstance {

    public static RedissonClient getInstance(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379");
        RedissonClient redissonClient = (RedissonClient) Redisson.create(config);
        System.out.println("Watchdog TTL = " + config.getLockWatchdogTimeout());
        return redissonClient;
    };

    public static RedissonClient redissonClient = getInstance();
}
