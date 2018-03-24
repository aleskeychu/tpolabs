public class SkewHeap {

    private SkewNode root = null;
    private StringBuilder logSb;

    public SkewHeap() {}

    public SkewHeap(StringBuilder sb) {
        this.logSb = sb;
    }

    private static int getSize(SkewNode node) {
        if (node == null) {
            return 0;
        }
        return getSize(node.getLeft()) + getSize(node.getRight());
    }

    public int getSize() {
        return getSize(root);
    }

    private static void writeStateToLog(SkewNode node, StringBuilder log) {
        if (log == null) return;
        getState(node, log);
        log.append("\n");
    }

    private static SkewNode merge(SkewNode that, SkewNode other, StringBuilder log) {
        if (that == null) {
            writeStateToLog(other, log);
            return other;
        } else if (other == null) {
            writeStateToLog(that, log);
            return that;
        }

        if (that.getValue() < other.getValue()) {
            SkewNode temp = that.getLeft();
            that.setLeft(merge(that.getRight(), other, log));
            that.setRight(temp);
            writeStateToLog(that, log);
            return that;
        } else {
            SkewNode temp = other.getLeft();
            other.setLeft(merge(other.getRight(), that, log));
            other.setRight(temp);
            writeStateToLog(other, log);
            return other;
        }
    }

    private static SkewNode merge(SkewNode that, SkewNode other) {
        return merge(that, other, null);
    }

    public void insert(int value) {
        root = merge(root, new SkewNode(value), logSb);
    }

    public Integer removeSmallest() {
        if (root == null) {
            return null;
        }
        SkewNode temp = root;
        root = merge(root.getLeft(), root.getRight(), logSb);
        return temp.getValue();
    }

    public void clear() {
        root = null;
    }

    private String getState() {
        StringBuilder sb = new StringBuilder();
        getState(root, sb);
        return sb.toString();
    }

    private static void getState(SkewNode node, StringBuilder sb) {
        if (node == null) {
            return;
        }
        getNodeState(node, sb);
        getState(node.getLeft(), sb);
        getState(node.getRight(), sb);
    }

    private static void getNodeState(SkewNode node, StringBuilder sb) {
        if (node == null) {
            return;
        }
        sb.append("[");
        sb.append("" + node.getValue() + ";");
        if (node.getLeft() != null) {
            sb.append("" + node.getLeft().getValue() + ";");
        } else {
            sb.append("n;");
        }
        if (node.getRight() != null) {
            sb.append("" + node.getRight().getValue() + "]");
        } else {
            sb.append("n]");
        }
    }

    public static void main(String[] args) {
    }
}

