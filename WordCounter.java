import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class WordCounter {
    private String urlStr;
    private String content;

    public WordCounter(String urlStr) {
        this.urlStr = urlStr;
    }

    // 抓取網頁內容並轉為字串
    private String fetchContent() {
        StringBuilder ret = new StringBuilder();
        try {
            URL url = new URL(this.urlStr);
            URLConnection conn = url.openConnection();
            // 設定 User-Agent 偽裝成正常瀏覽器，避免被伺服器阻擋
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");

            InputStream in = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            String line;
            while ((line = br.readLine()) != null) {
                ret.append(line);
            }
            br.close();
        } catch (Exception e) {
            System.err.println("連線失敗或網址無效：" + e.getMessage());
        }
        return ret.toString();
    }

    // 計算關鍵字出現次數
    public int countKeyword(String keyword) {
        if (this.content == null) {
            this.content = fetchContent();
        }

        String text = this.content.toLowerCase();
        String target = keyword.toLowerCase();

        int count = 0;
        int index = 0;

        while ((index = text.indexOf(target, index)) != -1) {
            count++;
            index += target.length();
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("請輸入 URL 與 Keyword (以空格分隔)：");

        String url = scanner.next();
        String keyword = scanner.next();

        WordCounter counter = new WordCounter(url);
        int count = counter.countKeyword(keyword);

        System.out.println(keyword + " 共出現了 " + count + " 次");

        scanner.close();
    }
}