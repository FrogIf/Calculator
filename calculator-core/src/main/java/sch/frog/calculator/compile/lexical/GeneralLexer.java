package sch.frog.calculator.compile.lexical;

import sch.frog.calculator.compile.lexical.exception.UnrecognizedTokenException;
import sch.frog.calculator.compile.lexical.fetcher.ITokenFetcher;
import sch.frog.calculator.compile.syntax.ISyntaxNodeGenerator;
import sch.frog.calculator.util.collection.IList;
import sch.frog.calculator.util.collection.Iterator;

/**
 * 常规词法解析器
 */
public class GeneralLexer implements ILexer {

    private final ITokenFetcher[] tokenFetchers;

    private GeneralLexer(ITokenFetcher[] fetcherArray){
        // 根据order排序
        for(int i = 1; i < fetcherArray.length; i++){
            ITokenFetcher fa = fetcherArray[i];
            int j = i - 1;
            ITokenFetcher fb;
            for(; j > -1; j--){
                fb = fetcherArray[j];
                if(fa.order() < fb.order()){
                    fetcherArray[j + 1] = fb;
                }else{
                    break;
                }
            }
            fetcherArray[j + 1] = fa;
        }
        tokenFetchers = fetcherArray;
    }

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
        for(int i = 0; i < tokenFetchers.length; i++){
            ITokenFetcher fetcher = tokenFetchers[i];
            operator.moveToMark();
            IToken token = fetcher.fetch(operator);
            // 最长匹配原则
            if(token != null){
                if(operator.position() > endPos){
                    result = token;
                    endPos = operator.position();
                }
                if(!fetcher.overridable()){
                    break;
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

    public static GeneralLexer build(IList<ITokenFetcher> tList){
        ITokenFetcher[] fetcherArray = new ITokenFetcher[tList.size()];
        int i = 0;
        Iterator<ITokenFetcher> itr = tList.iterator();
        while(itr.hasNext()){
            fetcherArray[i++] = itr.next();
        }
        return new GeneralLexer(fetcherArray);
    }
}
