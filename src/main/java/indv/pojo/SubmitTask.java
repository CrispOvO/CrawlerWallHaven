package indv.pojo;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import indv.gui.AlertPanel;
import indv.gui.HomePage;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * @author: Crisp
 * @date: 2021/1/9
 */
public class SubmitTask {

    public static final int THREAD_POOL_SIZE = 12;

    public void submitTask(int from, int to) throws Exception {

        //手动创建线程池
        ThreadFactory factory = new ThreadFactoryBuilder()
                .setNameFormat("downPic-pool-%d").build();

        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(THREAD_POOL_SIZE, THREAD_POOL_SIZE,
                        0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(1024),
                        factory, new ThreadPoolExecutor.AbortPolicy());

        String basicUrl = "https://wallhaven.cc/toplist?page=";
        int number = 0;
        for(int i = from; i <= to; i ++ ) {
            String targetUrl = basicUrl + String.valueOf(i);
            ArrayList<String> urlList = Crawler.getUrlList(targetUrl);
            HomePage.message.append(urlList.toString());
            for(int j = 0; j < urlList.size(); j ++ ) {
                number ++ ;
                String url = Crawler.getClearerPic(urlList.get(j));
                String name = String.valueOf(i*10000 + j);
                executor.execute(new DownThread(url, name, number));
            }
            HomePage.message.append("本页下载任务提交完成\n");
        }
        HomePage.bar.setMaximum((int)executor.getTaskCount());
        while(executor.getTaskCount() - executor.getCompletedTaskCount() > 0) {
            HomePage.bar.setValue((int)executor.getCompletedTaskCount());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        HomePage.bar.setValue((int)executor.getCompletedTaskCount());
        executor.shutdown();

        executor.awaitTermination(30L, TimeUnit.SECONDS);

        AlertPanel.alert("下载完成OvO");

    }


    private static class DownThread implements Runnable {
        String url;
        String name;
        int number;
        DownThread(String url, String name, int number) {
            this.name = name;
            this.url = url;
            this.number = number;
        }
        @Override
        public void run() {
            Thread.currentThread().setPriority(6);
            DownLoad.downUrl(url, name);
            HomePage.message.append("第" + number + "张图片下载完成！\n");
        }
    }
}
