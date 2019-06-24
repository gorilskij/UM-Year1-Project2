package general_support;

public class Rotation {
    public final double horizontal;
    public final double vertical;

    public Rotation(double horizontal, double vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    public Rotation withHorizontal(double horizontal) {
        return new Rotation(horizontal, vertical);
    }

    public Rotation withVertical(double vertical) {
        return new Rotation(horizontal, vertical);
    }
}
