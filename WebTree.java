import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebTree { // 樹狀結構的根節點
    public WebNode root;

    // 建立樹狀結構 給定根節點的網頁
    public WebTree(WebPage rootPage) {
        this.root = new WebNode(rootPage);
    }

    // 建立樹狀結構 給定根節點的網頁、最大深度、每個節點的最大子節點數量
    public void buildTree(int maxDepth, int maxChildrenPerNode) { 
        HashSet<String> visitedUrls = new HashSet<>(); // 用於追蹤已訪問的 URL 避免重複抓取
        visitedUrls.add(this.root.webPage.url); // 將根節點的 URL 加入已訪問集合
        buildTreeRecursive(this.root, 1, maxDepth, maxChildrenPerNode, visitedUrls); // 從根節點開始遞迴建立樹狀結構
    }

    // 遞迴建立樹狀結構
    private void buildTreeRecursive(WebNode currentNode, int currentDepth, int maxDepth, int maxChildren, HashSet<String> visited) {
        if (currentDepth > maxDepth) return; // 遞迴終止條件 當前深度超過最大深度時停止遞迴

        // 使用 Jsoup 抓取當前節點的網頁內容 並解析出所有的超連結
        try {
            Document doc = Jsoup.connect(currentNode.webPage.url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .timeout(3000)
                    .get();

            Elements links = doc.select("a[href]"); // 選取所有的超連結元素
            int childCount = 0; // 記錄當前節點已建立的子節點數量

            // 遍歷所有的超連結 並建立子節點
            for (Element link : links) {
                if (childCount >= maxChildren) break; // 當前節點的子節點數量已達到最大值時停止建立子節點

                String subUrl = link.attr("abs:href"); // 取得絕對 URL 避免相對路徑問題

                // 避免重複抓取相同的 URL 並且只抓取以 http 開頭的 URL
                if (subUrl.startsWith("http") && !visited.contains(subUrl)) {
                    visited.add(subUrl);
                    
                    // 取得超連結的文字作為子頁面的名稱 如果沒有文字則使用預設名稱
                    String title = link.text().trim();
                    if (title.isEmpty()) title = "子頁面 " + (childCount + 1);

                    WebPage childPage = new WebPage(subUrl, title); // 建立子頁面的 WebPage 物件
                    WebNode childNode = new WebNode(childPage); // 建立子節點的 WebNode 物件
                    
                    currentNode.addChild(childNode); // 將子節點加入當前節點的子節點列表
                    childCount++; // 記錄已建立的子節點數量

                    buildTreeRecursive(childNode, currentDepth + 1, maxDepth, maxChildren, visited); // 遞迴建立子節點的子節點
                }
            }
        } catch (Exception e) {
            // 個別子網頁連線失敗或超時時忽略，確保整座樹繼續建立其他節點
        }
    }

    // 計算整棵樹的節點分數 (包含自身及子樹的累加分數)
    public void setPostOrderScore(ArrayList<Keywords> keywords) throws IOException {
        this.root.setNodeScore(keywords);
    }

    public double getFinalSiteScore() {
        return this.root.getAverageScore();
    }

    // 印出整棵樹的結構與節點分數
    public void printTree() {
        printTreeRecursive(this.root, 0); // 從根節點開始遞迴印出樹狀結構
    }

    // 遞迴印出樹狀結構
    private void printTreeRecursive(WebNode node, int depth) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indent.append("   "); 
        }

        if (depth > 0) {
            indent.append("|_ ");
        }

        System.out.println(indent.toString() + "(" + node.webPage.name + ", Score: " + node.nodeScore + ")");

        for (WebNode child : node.children) {
            printTreeRecursive(child, depth + 1);
        }
    }
}