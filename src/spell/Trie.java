package spell;

import java.util.Locale;

public class Trie implements ITrie {

    private INode root;
    private int wordCount;
    private int nodeCount;

    public Trie() {
        root = new Node();
        wordCount = 0;
        nodeCount = 1;
    }

    @Override
    public void add(String word) {
        // Convert string to lower case, so our dictionary is not case-sensitive
        word = word.toLowerCase();

        // Begin the addHelper call at the first character of 'word' and at the root node
        addHelper(word, 0, root);
    }

    private void addHelper(String word, int wordIndex, INode curNode) {
        // If there are more characters to be read from the word
        if(wordIndex < word.length()) {
            // Convert the current character into the appropriate index of the nodeArray
            char letter = word.charAt(wordIndex);
            int nodeIndex = (int)letter - (int)'a';
            // If a node at the above index does not currently exist, create one and increment the tree's node count
            if(curNode.getChildren()[nodeIndex] == null) {
                curNode.getChildren()[nodeIndex] = new Node();
                this.nodeCount++;
            }
            // Jump 'into' this node and recursively call addHelper on this new node and the next character
            curNode = curNode.getChildren()[nodeIndex];
            wordIndex++;
            addHelper(word, wordIndex, curNode);
        } else {
            // If this is a new word, increment the TREE's word count
            if(curNode.getValue() == 0) {
                this.wordCount++;
            }
            // Increment the WORD's frequency count
            curNode.incrementValue();
        }
    }

    @Override
    public INode find(String word) {
        // Convert string to lower case, so our dictionary is not case-sensitive
        word = word.toLowerCase();

        // Begin the findHelper call at the first character of 'word' and at the root node
        return findHelper(word, 0, root);
    }

    private INode findHelper(String word, int wordIndex, INode curNode) {
        // If there are more characters to be read from the word
        if(wordIndex < word.length()) {
            // Convert the current character into the appropriate index of the nodeArray
            char letter = word.charAt(wordIndex);
            int nodeIndex = (int)letter - (int)'a';
            // If a node at the above index does not exist, the word is not in the dictionary
            if(curNode.getChildren()[nodeIndex] == null) {
                return null;
            } else {
                // Jump 'into' this node and recursively call findHelper on this new node and the next character
                curNode = curNode.getChildren()[nodeIndex];
                wordIndex++;
                return findHelper(word, wordIndex, curNode);
            }
        } else {
            if(curNode.getValue() > 0) {
                return curNode;
            } else {
                return null;
            }
        }
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
        // Multiply the wordCount and nodeCount
        int code = wordCount * nodeCount;
        // Find each non-null child of root and multiply/add the index if it is odd/even
        for(int i = 0; i < root.getChildren().length; i++) {
            if(root.getChildren()[i] != null) {
                if(i % 2 == 1) {
                    code *= i;
                } else {
                    code += i;
                }
            }
        }

        return code;
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
                boolean equalTries = equalsHelper(child1, child2);
                if (!equalTries) {
                    return false;
                }
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
