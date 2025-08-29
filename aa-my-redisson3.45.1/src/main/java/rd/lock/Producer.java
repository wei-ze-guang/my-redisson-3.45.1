package rd.lock;

import congif.RedissonClientInstance;
import org.redisson.Redisson;
import org.redisson.RedissonLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

public class Producer {


    RedissonClient redissonClient = RedissonClientInstance.redissonClient;

    private static final Producer instance = new Producer();

    public static Producer getInstance() {
        return instance;
    }

    private Producer() {

    }


    public void lockMethod(){


        RLock lockMethod = redissonClient.getLock("lockMethod");
        try {
            lockMethod.lock();
            System.out.println("线程="+Thread.currentThread().getName()+" 拿到锁");
        }finally {
            lockMethod.unlock();
        }

    }
    //  改进版
    public void unlockMethod(){

        RLock lockMethod = redissonClient.getLock("lockMethod");

        RedissonLock lockImpl = (RedissonLock) lockMethod;

             lockImpl.lock();
                try {
                    System.out.println("线程="+Thread.currentThread().getName()+" 拿到锁");
                    Thread.sleep(10);

                    repLockMethod();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    //  这里释放的话会立即发送del key的
                    lockImpl.unlock();
                }
    }

    public void repLockMethod(){
        RLock lockMethod = redissonClient.getLock("lockMethod");//这个和上面一把锁，以key为准
        RedissonLock lockImpl = (RedissonLock) lockMethod;
        lockImpl.lock();
        try {
            System.out.println("线程="+Thread.currentThread().getName()+" 拿到锁进入第二个");
        }finally {
            lockImpl.unlock();
        }
    }

}
