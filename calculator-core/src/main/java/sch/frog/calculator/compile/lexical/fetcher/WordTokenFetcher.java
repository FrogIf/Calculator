package sch.frog.calculator.compile.lexical.fetcher;

import sch.frog.calculator.compile.lexical.GeneralToken;
import sch.frog.calculator.compile.lexical.IScanner;
import sch.frog.calculator.compile.lexical.IToken;
import sch.frog.calculator.compile.lexical.TokenRepository;
import sch.frog.calculator.compile.syntax.ISyntaxNodeGenerator;

public class WordTokenFetcher implements ITokenFetcher{

    private final TokenRepository tokenRepository = new TokenRepository();

    public void register(String word, ISyntaxNodeGenerator generator){
        tokenRepository.insert(new GeneralToken(word, generator));
    }

    @Override
    public IToken fetch(IScanner scanner) {
        return tokenRepository.retrieve(scanner);
    }

}
