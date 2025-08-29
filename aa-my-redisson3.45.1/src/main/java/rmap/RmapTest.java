package rmap;

import congif.RedissonClientInstance;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

public class RmapTest {

    public static void main(String[] args) {

        RedissonClient instance = RedissonClientInstance.getInstance();

        RLock ll = instance.getLock("ll");
        ll.lock();

        try {
            System.out.println(ll.isLocked());
        }finally {
            ll.unlock();
        }

    }

    @Test
    public void test() {
        RedissonClient instance = RedissonClientInstance.getInstance();

        RMap<String, Integer> map = instance.getMap("map");
        map.put("key1", 1);
        Integer val = map.get("key1");

        System.out.println(val);
    }




}
