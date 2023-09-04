package io.github.frogif.calculator.compile.lexical.fetcher;

import io.github.frogif.calculator.compile.lexical.GeneralToken;
import io.github.frogif.calculator.compile.lexical.IScanner;
import io.github.frogif.calculator.compile.lexical.IToken;
import io.github.frogif.calculator.compile.syntax.ISyntaxNodeGenerator;

public class WordTokenFetcher implements ITokenFetcher{
    private final WordRepository wordRepository = new WordRepository();

    public void register(String word, ISyntaxNodeGenerator generator){
        wordRepository.insert(new WordObj(word, generator));
    }

    @Override
    public IToken fetch(IScanner scanner) {
        WordObj word = wordRepository.retrieve(scanner);
        return word == null ? null : new GeneralToken(word.word(), word.generator(), scanner.position());
    }
}
