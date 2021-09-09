package spell;

public class Node implements INode {

    private int count;
    private INode[] nodes;

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

}
