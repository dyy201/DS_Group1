public class Keywords {
    public String name;
    public double weight;

    public Keywords(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "[" + name + ", " + weight + "]";
    }
}