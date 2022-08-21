package sch.frog.calculator.compile.lexical;

import sch.frog.calculator.compile.lexical.exception.UnrecognizedTokenException;
import sch.frog.calculator.compile.lexical.fetcher.ITokenFetcher;
import sch.frog.calculator.compile.lexical.fetcher.MatcherTokenFetcher;
import sch.frog.calculator.compile.lexical.fetcher.WordTokenFetcher;
import sch.frog.calculator.compile.lexical.matcher.IMatcher;
import sch.frog.calculator.compile.syntax.ISyntaxNodeGenerator;

/**
 * 常规词法解析器
 */
public class GeneralLexer implements ILexer {

    private final WordTokenFetcher wordTokenFetcher = new WordTokenFetcher();

    private final MatcherTokenFetcher matcherTokenFetcher = new MatcherTokenFetcher();
    private final ITokenFetcher[] tokenFetchers = new ITokenFetcher[]{ wordTokenFetcher, matcherTokenFetcher };

    private static final IToken GUARD_TOKEN = new IToken(){
        @Override
        public String word() {
            return "";
        }

        @Override
        public ISyntaxNodeGenerator getSyntaxNodeGenerator() {
            return null;
        }
    };

    @Override
    public IToken parse(IScannerOperator operator) throws UnrecognizedTokenException {
        skipBlank(operator);

        IToken result = GUARD_TOKEN;

        int startPos = operator.position();
        int endPos = startPos;
        for (ITokenFetcher fetcher : tokenFetchers) {
            operator.moveToMark();
            IToken token = fetcher.fetch(operator);
            // 最长匹配原则
            if (token != null) {
                if (operator.position() > endPos) {
                    result = token;
                    endPos = operator.position();
                }
            }
        }

        operator.markTo(endPos - startPos);
        operator.moveToMark();

        if(result == GUARD_TOKEN){
            throw new UnrecognizedTokenException(operator.peek(), operator.position());
        }

        // 跳过后空格
        if(operator.isNotEnd()){
            skipBlank(operator);
        }

        return result;
    }

    private void skipBlank(IScannerOperator operator){
        int start = operator.position();
        char ch;
        do{
            ch = operator.peek();
            if(ch != ' '){
                break;
            }
            operator.take();
        }while(operator.isNotEnd());
        operator.markTo(operator.position() - start);
        operator.moveToMark();
    }

    public void register(IMatcher matcher, ISyntaxNodeGenerator generator){
        this.matcherTokenFetcher.register(matcher, generator);
    }

    public void register(String word, ISyntaxNodeGenerator generator){
        this.wordTokenFetcher.register(word, generator);
    }
}
