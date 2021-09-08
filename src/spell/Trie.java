package spell;

public class Trie implements ITrie {

    private INode root;
    private int wordCount;
    private int nodeCount;

    public Trie() {
        root = new Node();
        wordCount = 0;
        nodeCount = 0;
    }

    @Override
    public void add(String word) {

    }

    @Override
    public INode find(String word) {
        return null;
    }

    public INode getRoot() {
        return root;
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
        // Find index of first null node in root
        int index = 0;
        while(root.getChildren()[index] != null && index < 26) {
            index++;
        }
        // Add 1 to index and multiply it by the sum of wordCount and nodeCount
        return (index + 1) * (wordCount + nodeCount);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        if(obj == this) {
            return true;
        }

        if(obj.getClass() != this.getClass()) {
            return false;
        }

        Trie dict = (Trie)obj;

        // If the tries have different word counts OR different node counts, they are not equal
        if(dict.getWordCount() != this.getWordCount() || dict.getNodeCount() != this.getNodeCount()) {
            return false;
        }

        return equalsHelper(dict.getRoot(), this.root);
    }

    private boolean equalsHelper(INode n1, INode n2) {
        // If the node's counts differ, they are not equal
        if(n1.getValue() != n2.getValue()) {
            return false;
        }

        // If the nodes have different children, they are not equal
        for(int i = 0; i < 26; i++) {
            if(n1.getChildren()[i] == null) {
                if(n2.getChildren()[i] != null) {
                    return false;
                }
            } else {
                if(n2.getChildren()[i] == null) {
                    return false;
                }
            }
        }

        // Recursively call equalsHelper on children until difference is found or entire tree is traversed
        for(int j = 0; j < 26; j++) {
            INode child1 = n1.getChildren()[j];
            INode child2 = n2.getChildren()[j];
            if(child1 != null) {
                return equalsHelper(child1, child2);
            }
        }

        return true;
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
