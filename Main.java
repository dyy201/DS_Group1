import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            // 1. 給定一組關鍵字集合 (N [keywords.name keywords.weight])
            ArrayList<Keywords> keywords = new ArrayList<>();
            keywords.add(new Keywords("tickets", 10.0));
            keywords.add(new Keywords("concert information", 10.0));
            keywords.add(new Keywords("演出地點", 10.0));
            keywords.add(new Keywords("演出時間", 10.0));
            keywords.add(new Keywords("演出日期", 10.0));
            keywords.add(new Keywords("票價", 10.0));
            keywords.add(new Keywords("VIP區", 10.0));
            keywords.add(new Keywords("world tour", 5.0));
            keywords.add(new Keywords("tour", 5.0));
            keywords.add(new Keywords("concert", 5.0));
            keywords.add(new Keywords("fanmeeting", 5.0));
            keywords.add(new Keywords("見面會", 5.0));
            keywords.add(new Keywords("巡迴", 5.0));
            keywords.add(new Keywords("演唱會", 5.0));
            keywords.add(new Keywords("2026年10月", 1.0));
            keywords.add(new Keywords("2026年11月", 1.0));
            keywords.add(new Keywords("2026年12月", 1.0));
            keywords.add(new Keywords("2026.10", 1.0));
            keywords.add(new Keywords("2026.11", 1.0));
            keywords.add(new Keywords("2026.12", 1.0));
            keywords.add(new Keywords("2026-10", 1.0));
            keywords.add(new Keywords("2026-11", 1.0));
            keywords.add(new Keywords("2026-12", 1.0));
            keywords.add(new Keywords("2026", 1.0));
            keywords.add(new Keywords("2027", 1.0));

            // 2. 給定一組測試網址清單
            ArrayList<WebTree> trees = new ArrayList<>();

            WebTree t1 = new WebTree(new WebPage("https://www.livenation.com.tw/event/allevents", "Live Nation Taiwan"));
            t1.buildTree(3, 5); // 建立樹狀結構 最大深度 3、每個節點的最大子節點數量 5
            trees.add(t1);

            WebTree t2 = new WebTree(new WebPage("https://www.cosmopolitan.com/tw/entertainment/celebrity-gossip/", "Cosmopolitan Taiwan"));
            t2.buildTree(3, 5);
            trees.add(t2);

            WebTree t3 = new WebTree(new WebPage("https://twconcertview.com/calendar", "Concert Calendar"));
            t3.buildTree(3, 5);
            trees.add(t3);

            // 3. 計算每個網頁的分數
            for (WebTree tree : trees) {
                tree.setPostOrderScore(keywords);
            }

            // 4. 依照 score 由高到低進行排序
            trees.sort((treeA, treeB) -> Double.compare(treeB.getFinalSiteScore(), treeA.getFinalSiteScore()));

            // 5. 印出 Stage 2 排名結果
            System.out.println("---- stage 2 ranking results -----");

            for (int i = 0; i < trees.size(); i++) {
                WebTree tree = trees.get(i);
                System.out.println("\nRank " + (i + 1) + ": " + tree.root.webPage.name 
                                   + " | Average Score: " + tree.getFinalSiteScore() 
                                   + " | URL: " + tree.root.webPage.url);
                System.out.println("網站樹狀結構：");
                tree.printTree();
                System.out.println("------------------------------------------");
            }

        } catch (Exception e) { // 改用泛用 Exception 避免 catch 區塊編譯錯誤
            System.err.println("計算過程發生錯誤：" + e.getMessage());
        }
    }
}