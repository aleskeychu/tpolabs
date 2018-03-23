public class SkewNode {
    private int value;
    private SkewNode left;
    private SkewNode right;

    public SkewNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public SkewNode getLeft() {
        return left;
    }

    public void setLeft(SkewNode left) {
        this.left = left;
    }

    public SkewNode getRight() {
        return right;
    }

    public void setRight(SkewNode right) {
        this.right = right;
    }
}
