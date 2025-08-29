package congif;

import java.util.concurrent.*;

public class ExecutorConfig {

    public static final ExecutorService getInstance() {
        ExecutorService service = new ThreadPoolExecutor(
                10,
                10,
                100,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(50),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        return service;

    }
}
