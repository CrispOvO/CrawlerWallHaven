package indv.pojo;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Crisp
 * @date 2021/4/17
 */
public class ThreadTest {
    public static final int THREAD_POOL_SIZE = 12;
    public static void main(String[] args) {
        ThreadFactory factory = new ThreadFactoryBuilder()
                .setNameFormat("downPic-pool-%d").build();

        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(THREAD_POOL_SIZE, THREAD_POOL_SIZE,
                        0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(1024),
                        factory, new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 1000; i++) {
            executor.execute(() -> System.out.println(Thread.currentThread().getName()));
        }
        // 优雅关闭线程池
        executor.shutdown();
        try {
            executor.awaitTermination(1000L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 任务执行完毕后打印"Done"
        System.out.println("Done");

    }
}
