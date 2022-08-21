package sch.frog.calculator.compile.lexical.fetcher;

import sch.frog.calculator.compile.lexical.GeneralToken;
import sch.frog.calculator.compile.lexical.IScanner;
import sch.frog.calculator.compile.lexical.IToken;
import sch.frog.calculator.compile.syntax.ISyntaxNodeGenerator;

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
