import java.io.IOException;
import java.util.ArrayList;

public class WebNode {
    public WebNode parent;
    public ArrayList<WebNode> children;
    public WebPage webPage;
    public double nodeScore; // 節點分數 (包含自身及子樹的累加分數)

    public WebNode(WebPage webPage) {
        this.webPage = webPage;
        this.children = new ArrayList<>();
        this.nodeScore = 0.0;
    }

    public void addChild(WebNode child) {
        this.children.add(child);
        child.parent = this;
    }

    // 計算節點分數 (包含自身及子樹的累加分數)
    public void setNodeScore(ArrayList<Keywords> keywords) throws IOException { 
        
        // 計算自身網頁的分數
        this.webPage.setScore(keywords);
        this.nodeScore = this.webPage.score;

        // 遞迴計算子節點的分數 並將子節點分數加權累加到父節點
        for (WebNode child : children) {
            child.setNodeScore(keywords);
            this.nodeScore += child.nodeScore * 0.5; // 子節點分數加權 * 0.5 (第一層 * 0.5, 第二層 * 0.25, 第三層 * 0.125 ...)
        }
    }
        // 計算整棵樹的節點數量 (包含自身及子樹的節點數量)
        public int getTreeSize() {
        int count = 1; // 代表自己這個頁面
        for (WebNode child : children) {
            count += child.getTreeSize();
        }
        return count;
    }
        // 計算整棵樹的平均分數 (包含自身及子樹的平均分數) (避免因為很多母網站參雜了很多不必要資訊而導致分數偏高的問題)
        public double getAverageScore() {
        int totalNodes = getTreeSize();
        if (totalNodes == 0) return 0.0;
        
        return this.nodeScore / totalNodes;
    }
}