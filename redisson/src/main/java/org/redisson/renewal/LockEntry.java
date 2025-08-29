/**
 * Copyright (c) 2013-2024 Nikita Koksharov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.redisson.renewal;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Nikita Koksharov
 *
 */
@Slf4j
public class LockEntry {

    final Queue<Long> threadsQueue = new ConcurrentLinkedQueue<>();  //记录加锁的线程顺序
    final Map<Long, Integer> threadId2counter = new ConcurrentHashMap<>();  //每个线程在本地的可重入计数（holdCount）
    final Map<Long, String> threadId2lockName = new ConcurrentHashMap<>();  //线程对应的锁名（Redis key）

    LockEntry() {
        super();
    }

    public String getLockName(long threadId) {
        return threadId2lockName.get(threadId);
    }

    public void addThreadId(long threadId, String lockName) {
        threadId2counter.compute(threadId, (t, counter) -> {
            counter = Optional.ofNullable(counter).orElse(0);
            log.info("可重入锁的计数加1");
            counter++;
            threadsQueue.add(threadId);
            return counter;
        });
        threadId2lockName.putIfAbsent(threadId, lockName);
    }

    public boolean hasNoThreads() {
        return threadsQueue.isEmpty();
    }

    public Long getFirstThreadId() {
        log.info("获取最先获得这个锁的线程名字={}", threadsQueue.peek());
        return threadsQueue.peek();
    }

    public void removeThreadId(long threadId) {
        threadId2counter.computeIfPresent(threadId, (t, counter) -> {
            log.info("可重入锁的计数减去1");
            counter--;
            if (counter == 0) {
                threadsQueue.remove(threadId);
                threadId2lockName.remove(threadId);
                return null;
            }
            return counter;
        });
    }

}
