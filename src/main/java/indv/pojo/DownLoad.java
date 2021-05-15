package indv.pojo;

import indv.gui.HomePage;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author: Crisp
 * @date: 2021/1/9
 */
public class DownLoad {
    public static void showUrl(String url) {
        HomePage.message.append(url);
    }

    public static void downUrl(String url, String name) {
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;

        try{
            URL fileUrl = new URL(url);
            //建立链接
            URLConnection urlConnection = fileUrl.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
            //得到输入流
            bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            byte[] getData = new byte[1024*8];
            String parentDir = HomePage.savePath.getAbsolutePath();
            File dir = HomePage.savePath;
            if(!dir.exists()) {
                if(dir.mkdir()) {
                    HomePage.message.append("路径创建成功\n");
                }
            }
            File picPath = new File(parentDir + '/' + name + ".jpg");
            if(picPath.exists()) {
                return;
            }
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(picPath));
            int len;
            while ((len = bufferedInputStream.read(getData)) != -1) {
                bufferedOutputStream.write(getData, 0, len);
            }

        } catch(Exception e) {
            HomePage.message.append(url + " 下载失败，不存在该路径\n");
            HomePage.message.append("将以png格式下载\n");
            downUrl(url.substring(0, url.length() - 3) + "png", name);
            HomePage.message.append(url + "... success\n");
        } finally {
            try {
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
                if(bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
