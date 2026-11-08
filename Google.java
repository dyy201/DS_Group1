import java.io.IOException;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Google {
    private String searchKeyword;
    private String url;
    private String content;

    public Google(String searchKeyword) {
        this.searchKeyword = searchKeyword;
        this.url = "https://www.google.com/search?q=" + searchKeyword;
    }

    private String fetchContent() throws IOException {
        Document doc = Jsoup.connect(this.url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .timeout(5000)
                .get();
        return doc.html();
    }

    public HashMap<String, String> query() throws IOException {
        if (this.content == null) {
            this.content = fetchContent();
        }

        HashMap<String, String> retVal = new HashMap<>();
        Document doc = Jsoup.parse(this.content);
        
        Elements lis = doc.select("div.g");

        for (Element li : lis) {
            try {
                Element aTag = li.select("a").first();
                Element h3Tag = li.select("h3").first();

                if (aTag != null && h3Tag != null) {
                    String title = h3Tag.text();
                    String citeUrl = aTag.attr("href");
                    retVal.put(title, citeUrl);
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        
        return retVal;
    }
}