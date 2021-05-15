package indv.pojo;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import indv.gui.HomePage;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author: Crisp
 * @date: 2021/1/9
 */
public class Crawler {
    /**
     * 获得图片的url
     * @param url
     * @return
     * @throws Exception
     */
    public static ArrayList<String> getUrlList(String url){
        Document doc = null;
        try {
            doc = Jsoup.parse(new URL(url), 5000);
        } catch (IOException e) {
            HomePage.message.append(e.getMessage());
        }
        Elements aTag = doc.getElementsByTag("img");
        String href = aTag.toString();
        int charNumber = 0;
        char need = '\"';
        String subUrl = "";
        ArrayList<String> urlList = new ArrayList<>();
        String jpgSuffix = ".jpg";
        for(int i = 0; i < href.length(); i ++ ) {
            if(href.charAt(i) == need) {
                charNumber ++ ;
                if(charNumber%2 == 0) {
                    //判断是否为jpg后缀的文件
                    if(subUrl.length() <= 4) {
                        subUrl = "";
                        continue;
                    }
                    if (subUrl.substring(subUrl.length() - 4).equals(jpgSuffix)) {
                        urlList.add(subUrl);
                    }
                    subUrl = "";
                }
            } else if(charNumber%2 == 1) {
                subUrl += href.charAt(i);
            }
        }
        return urlList;
    }

    /**
     * 获得图片的标题
     * @param url
     * @return
     * @throws Exception
     */
    public static ArrayList<String> getImgAlt(String url) throws Exception {
        Document doc = Jsoup.connect(url)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0")
                .timeout(10000)
                .get()
                ;
        Elements aTag = doc.getElementsByTag("img");
        String href = aTag.toString();
        boolean meetAlt = false;
        ArrayList<String> alt = new ArrayList<>();
        String altBegin = "alt=\"";
        String imgAlt = "";
        char need = '\"';
        for(int i = 0; i < href.length(); i ++ ) {
            if(href.length() - i > 5 && altBegin.equals(href.substring(i, i + 5))) {
                i += 5;
                while(href.charAt(i) != need) {
                    imgAlt += href.charAt(i);
                    i ++ ;
                }
                alt.add(imgAlt);
                imgAlt = "";
            }
        }
        return alt;
    }

    public static String getClearerPic(String oriUrl) {
        String ID = "";
        int cnt = 0;
        for(int i = 0; i < oriUrl.length(); i ++ ) {
            if(cnt == 5) {
                ID += oriUrl.charAt(i);
            }
            if(oriUrl.charAt(i) == '/') {
                cnt ++ ;
            }
        }
        String trUrl = "https://w.wallhaven.cc/full/" + ID.charAt(0) + ID.charAt(1)
                + "/wallhaven-" + ID;
        return trUrl;
    }
}
