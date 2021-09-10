package spell;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

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
        return null;
    }
}
