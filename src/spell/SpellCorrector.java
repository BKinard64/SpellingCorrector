package spell;

import java.io.IOException;

public class SpellCorrector implements ISpellCorrector {

    private ITrie dictionary;

    public SpellCorrector() {
        dictionary = new Trie();
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {

    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        return null;
    }
}
