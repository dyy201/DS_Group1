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

    public void setScore(ArrayList<Keyword> keywords) throws IOException {
        this.score = 0.0;
        for (Keyword k : keywords) {
            int count = counter.countKeyword(k.name);
            this.score += k.weight * count;
        }
    }
}