import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            // 1. 給定一組關鍵字集合 (N [keywords.name keywords.weight])
            ArrayList<Keywords> keywords = new ArrayList<>();
            keywords.add(new Keywords("2026年10月", 8.0));
            keywords.add(new Keywords("2026年11月", 8.0));
            keywords.add(new Keywords("2026年12月", 8.0));
            keywords.add(new Keywords("2026.10", 8.0));
            keywords.add(new Keywords("2026.11", 8.0));
            keywords.add(new Keywords("2026.12", 8.0));
            keywords.add(new Keywords("2026-10", 8.0));
            keywords.add(new Keywords("2026-11", 8.0));
            keywords.add(new Keywords("2026-12", 8.0));
            keywords.add(new Keywords("2026", 5.0));
            keywords.add(new Keywords("2027", 5.0));
            keywords.add(new Keywords("get tickets", 5.0));
            keywords.add(new Keywords("concert information", 3.0));
            keywords.add(new Keywords("演出地點", 3.0));
            keywords.add(new Keywords("演出時間", 3.0));
            keywords.add(new Keywords("演出日期", 3.0));
            keywords.add(new Keywords("票價", 3.0));
            keywords.add(new Keywords("VIP區", 3.0));
            keywords.add(new Keywords("world tour", 2.0));
            keywords.add(new Keywords("tour", 2.0));
            keywords.add(new Keywords("concert", 2.0));
            keywords.add(new Keywords("fanmeeting", 2.0));
            keywords.add(new Keywords("見面會", 2.0));
            keywords.add(new Keywords("巡迴", 2.0));
            keywords.add(new Keywords("演唱會", 2.0));

            // 2. 給定一組測試網址清單
            ArrayList<WebTree> trees = new ArrayList<>();

            WebTree t1 = new WebTree(new WebPage("https://ygfamily.com/en/artists/babymonster/concert", "官網"));
            WebTree t2 = new WebTree(new WebPage("https://www.artists.tw/gigs/", "演唱會資訊網"));
            WebTree t3 = new WebTree(new WebPage("https://kktix.com/events", "kktix"));

            // 計算每個網頁的分數
            for (WebPage page : webPages) {
                page.setScore(keywords);
            }

            // 4. 依照 score 由高到低進行排序
            trees.sort((t1, t2) -> Double.compare(t2.root.nodeScore, t1.root.nodeScore));

            // 印出 Stage 1 排名結果
            System.out.println("----- Stage 1 Page Ranking Results -----");
            for (int i = 0; i < webPages.size(); i++) {
                WebPage p = webPages.get(i);
                System.out.println("Rank " + (i + 1) + ": " + p.name + " | Score: " + p.score);
            }

        } catch (IOException e) {
            System.err.println("計算過程發生錯誤：" + e.getMessage());
        }
    }
}