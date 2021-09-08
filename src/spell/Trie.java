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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        StringBuilder curWord = new StringBuilder();
        StringBuilder output = new StringBuilder();

        toStringHelper(root, curWord, output);

        return output.toString();
    }

    private void toStringHelper(INode n, StringBuilder curWord, StringBuilder output) {
        // If current node has a word count, append that word to the output followed by a newline
        if(n.getValue() > 0) {
            output.append(curWord.toString());
            output.append("\n");
        }

        // Traverse trie by recursively calling helper on children of current node
        for(int i = 0; i < n.getChildren().length; i++) {
            INode child = n.getChildren()[i];
            if(child != null) {
                // Convert index into character the current node is meant to represent
                char letter = (char)('a' + i);
                // Append the current letter to the curWord StringBuilder
                curWord.append(letter);
                toStringHelper(child, curWord, output);
                // Remove current letter from curWord before iterating to next child
                curWord.deleteCharAt(curWord.length() - 1);
            }
        }
    }

}
