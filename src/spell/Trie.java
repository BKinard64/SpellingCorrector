package spell;

public class Trie implements ITrie {

    private INode root;
    private int wordCount;
    private int nodeCount;

    @Override
    public void add(String word) {

    }

    @Override
    public INode find(String word) {
        return null;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }
}
