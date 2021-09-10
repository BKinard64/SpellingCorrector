package spell;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SpellCorrector implements ISpellCorrector {

    private ITrie dictionary;

    public SpellCorrector() {
        dictionary = new Trie();
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File file = new File(dictionaryFileName);
        Scanner scanner = new Scanner(file);

        while(scanner.hasNext()) {
            dictionary.add(scanner.next());
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        // Search for word in the dictionary
        INode found = dictionary.find(inputWord);
        // If the word is in the dictionary, return inputWord
        if(found != null) {
            return inputWord;
        }

        // If the word is not in the dictionary, generate the edit-dist-1 words and search the dict for them
        Set<String> edits = new TreeSet<>();
        Set<String> suggestions = new TreeSet<>();
        int maxFrequency = 0;

        deletionDistance(inputWord, edits, suggestions, maxFrequency);
        transpositionDistance(inputWord, edits, suggestions, maxFrequency);
        alterationDistance(inputWord, edits, suggestions, maxFrequency);
        insertionDistance(inputWord, edits, suggestions, maxFrequency);

        // If there are no suggested words with edit distance of 1, generate edit-dist-2 words and search again
        if(suggestions.isEmpty()) {
            Set<String> edits2 = new HashSet<>();
            for(String word : edits) {
                deletionDistance(word, edits2, suggestions, maxFrequency);
                transpositionDistance(word, edits2, suggestions, maxFrequency);
                alterationDistance(word, edits2, suggestions, maxFrequency);
                insertionDistance(word, edits2, suggestions, maxFrequency);
            }
        }

        // If there are no suggested words with edit distance of 2, return null
        if(suggestions.isEmpty()) {
            return null;
        }

        // Of the suggested words, return the one that comes first alphabetically
        Iterator<String> itr = suggestions.iterator();
        return itr.next();
    }

    private void deletionDistance(String word, Set<String> edits, Set<String> suggestions, int maxVal) {
        for(int i = 0; i < word.length(); i++) {
            // Create string with character at index i removed
            String alternative = word.substring(0, i) + word.substring(i + 1);
            // Add this string to set of strings with edit distance of 1
            edits.add(alternative);
            // Search for this string in dictionary
            INode found = dictionary.find(alternative);
            // If the string was found, add it to set of suggested similar words
            if(found != null) {
                if(found.getValue() > maxVal) {
                    suggestions.clear();
                    maxVal = found.getValue();
                    suggestions.add(alternative);
                } else if(found.getValue() == maxVal) {
                    suggestions.add(alternative);
                }
            }
        }
    }

    private void transpositionDistance(String word, Set<String> edits, Set<String> suggestions, int maxVal) {
        for(int i = 0; i < word.length() - 1; i++) {
            // Transpose the ith and i+1th character and store as new string
            char[] c = word.toCharArray();
            char temp = c[i];
            c[i] = c[i+1];
            c[i+1] = temp;
            String alternative = new String(c);
            // Add this string to set of strings with edit distance of 1
            edits.add(alternative);
            // Search for this string in dictionary
            INode found = dictionary.find(alternative);
            // If the string was found, add it to set of suggested similar words
            if(found != null) {
                if(found.getValue() > maxVal) {
                    suggestions.clear();
                    maxVal = found.getValue();
                    suggestions.add(alternative);
                } else if(found.getValue() == maxVal) {
                    suggestions.add(alternative);
                }
            }
        }
    }

    private void alterationDistance(String word, Set<String> edits, Set<String> suggestions, int maxVal) {
        for(int i = 0; i < word.length(); i++) {
            for(int j = 0; j < 26; j++) {
                // Replace current letter at index i with every other letter in alphabet
                char alt = (char)((int)'a' + j);
                if(alt != word.charAt(i)) {
                    char[] c = word.toCharArray();
                    c[i] = alt;
                    String alternative = new String(c);
                    // Add this string to set of strings with edit distance of 1
                    edits.add(alternative);
                    // Search for this string in dictionary
                    INode found = dictionary.find(alternative);
                    // If the string was found, add it to set of suggested similar words
                    if(found != null) {
                        if(found.getValue() > maxVal) {
                            suggestions.clear();
                            maxVal = found.getValue();
                            suggestions.add(alternative);
                        } else if(found.getValue() == maxVal) {
                            suggestions.add(alternative);
                        }
                    }
                }
            }
        }
    }

    private void insertionDistance(String word, Set<String> edits, Set<String> suggestions, int maxVal) {
        for(int i = 0; i <= word.length(); i++) {
            for(int j = 0; j < 26; j++) {
                char alt = (char)((int)'a' + j);
                // Create a string with every letter added at the ith index
                String alternative = word.substring(0, i) + alt + word.substring(i);
                // Add this string to set of strings with edit distance of 1
                edits.add(alternative);
                // Search for this string in dictionary
                INode found = dictionary.find(alternative);
                // If the string was found, add it to set of suggested similar words
                if(found != null) {
                    if(found.getValue() > maxVal) {
                        suggestions.clear();
                        maxVal = found.getValue();
                        suggestions.add(alternative);
                    } else if(found.getValue() == maxVal) {
                        suggestions.add(alternative);
                    }
                }
            }
        }
    }

}
