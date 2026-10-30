import java.io.IOException;
import java.util.ArrayList;

public class WebPage {
    public String url;
    public String name;
    public WordCounter counter;
    public double score;

    public WebPage(String url, String name) {
        this.url = url;
        this.name = name;
        this.counter = new WordCounter(url);
        this.score = 0.0;
    }

    public void setScore(ArrayList<Keywords> keywords) throws IOException {
        double rawscore = 0.0;

        for (Keywords keyword : keywords) {
            int count = counter.countKeyword(keyword.name);
            rawscore += count * keyword.weight;
        }

        int totalWords = counter.getTotalWordCount();

        if (totalWords < 10) { // 如果總字數小於 10，直接使用 rawscore 作為分數
           this.score = rawscore; 
        } else {
            this.score = rawscore / Math.log10(totalWords); // 使用 log10 來計算分數 避免因為文章本身字數長而導致分數偏高的問題
        }
    }
}