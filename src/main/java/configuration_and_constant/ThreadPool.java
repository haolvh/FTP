package configuration_and_constant;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池配置
 *
 * @author yinchao
 * @date 2020/7/2 11:06
 */
public class ThreadPool {
    private static volatile ThreadPoolExecutor threadPoolExecutor;

    public ThreadPool() {
    }

    public static ThreadPoolExecutor getThreadPool() {
        if (threadPoolExecutor == null) {
            synchronized (ThreadPool.class) {
                if (threadPoolExecutor == null) {
                    // I/O 密集型，估算配置 2×CPU 个线程
                    threadPoolExecutor = new ThreadPoolExecutor(8, 256, 1, TimeUnit.SECONDS, new LinkedBlockingQueue(10000));
                }
            }
        }
        return threadPoolExecutor;
    }
}
