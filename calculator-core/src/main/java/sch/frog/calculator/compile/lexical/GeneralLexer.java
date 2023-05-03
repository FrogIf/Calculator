package sch.frog.calculator.compile.lexical;

import sch.frog.calculator.compile.lexical.exception.UnrecognizedTokenException;
import sch.frog.calculator.compile.lexical.fetcher.ITokenFetcher;
import sch.frog.calculator.compile.lexical.fetcher.MatcherTokenFetcher;
import sch.frog.calculator.compile.lexical.fetcher.WordTokenFetcher;
import sch.frog.calculator.compile.lexical.matcher.IMatcher;
import sch.frog.calculator.compile.syntax.ISyntaxNodeGenerator;
import sch.frog.calculator.util.collection.IList;
import sch.frog.calculator.util.collection.LinkedList;

/**
 * 常规词法解析器
 */
public class GeneralLexer implements ILexer {

    private final WordTokenFetcher wordTokenFetcher = new WordTokenFetcher();

    private final MatcherTokenFetcher matcherTokenFetcher = new MatcherTokenFetcher();
    private final ITokenFetcher[] tokenFetchers = new ITokenFetcher[]{ wordTokenFetcher, matcherTokenFetcher };

    private static final IToken GUARD_TOKEN = new IToken(){
        @Override public String word() { return ""; }
        @Override public ISyntaxNodeGenerator getSyntaxNodeGenerator() { return null; }
        @Override public int position() { return -1; }
    };

    private IToken parse(IScanner scanner) throws UnrecognizedTokenException {
        skipBlank(scanner);

        IToken result = GUARD_TOKEN;

        int len = 0;
        IScanner.PointerSnapshot initPointerSnapshot = scanner.snapshot();
        IScanner.PointerSnapshot matchPointerSnapshot = null;
        for (ITokenFetcher fetcher : tokenFetchers) {
            scanner.applySnapshot(initPointerSnapshot);
            IToken token = fetcher.fetch(scanner);
            // 最长匹配原则
            if (token != null && token.word().length() > len) {
                result = token;
                matchPointerSnapshot = scanner.snapshot();
                len = token.word().length();
            }
        }
        if(matchPointerSnapshot != null){
            scanner.applySnapshot(matchPointerSnapshot);
        }

        if(result == GUARD_TOKEN){
            throw new UnrecognizedTokenException(scanner.peek(), scanner.position());
        }

        // 跳过后空格
        if(scanner.isNotEnd()){
            skipBlank(scanner);
        }

        return result;
    }

    @Override
    public IList<IToken> tokenization(IScanner scanner) throws UnrecognizedTokenException {
        LinkedList<IToken> tokens = new LinkedList<>();
        while(scanner.isNotEnd()){
            IToken token = this.parse(scanner);
            tokens.add(token);
        }
        return tokens;
    }

    private void skipBlank(IScanner scanner){
        do{
            char ch = scanner.peek();
            if(ch != ' ' && ch != '\t'){
                break;
            }
            scanner.take();
        }while(scanner.isNotEnd());
    }

    public void register(IMatcher matcher, ISyntaxNodeGenerator generator){
        this.matcherTokenFetcher.register(matcher, generator);
    }

    public void register(String word, ISyntaxNodeGenerator generator){
        this.wordTokenFetcher.register(word, generator);
    }
}
