public class SkewHeap {

    private SkewNode root = null;



    private static int getSize(SkewNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + getSize(node.getLeft()) + getSize(node.getRight());
    }

    public int getSize() {
        return getSize(root);
    }

    private static SkewNode merge(SkewNode that, SkewNode other) {
        if (that == null) {
            return other;
        } else if (other == null) {
            return that;
        }

        if (that.getValue() < other.getValue()) {
            SkewNode temp = that.getLeft();
            that.setLeft(merge(that.getRight(), other));
            that.setRight(temp);
            return that;
        } else {
            SkewNode temp = other.getLeft();
            other.setLeft(merge(other.getRight(), that));
            other.setRight(temp);
            return other;
        }
    }

    public void insert(int value) {
        root = merge(root, new SkewNode(value));
    }

    public Integer removeSmallest() {
        if (root == null) {
            return null;
        }
        SkewNode temp = root;
        root = merge(root.getLeft(), root.getRight());
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
        SkewHeap heap = new SkewHeap();
        heap.getState();
        heap.insert(5);
        System.out.println(heap.getState());
        heap.insert(7);
        System.out.println(heap.getState());
        heap.insert(8);
        System.out.println(heap.getState());
        heap.insert(2);
        System.out.println(heap.getState());
    }
}

