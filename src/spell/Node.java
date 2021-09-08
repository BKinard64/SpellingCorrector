package spell;

public class Node implements INode {
    public Node() {
        count = 0;
        nodes = new INode[26];
    }

    @Override
    public int getValue() {
        return count;
    }

    @Override
    public void incrementValue() {
        count++;
    }

    @Override
    public INode[] getChildren() {
        return nodes;
    }

    private int count;
    private INode[] nodes;
}
