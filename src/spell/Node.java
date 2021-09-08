package spell;

public class Node implements INode {
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
    private INode[] nodes = new INode[26];
}
