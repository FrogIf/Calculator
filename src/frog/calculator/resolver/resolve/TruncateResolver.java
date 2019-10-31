package frog.calculator.resolver.resolve;

import frog.calculator.express.IExpression;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.IResolverResultFactory;
import frog.calculator.resolver.resolve.factory.ISymbolExpressionFactory;

/**
 * 截断解析器<br/>
 * 从某一位置开始向后遍历, 知道出现指定字符, 将该字符之前的子串作为一个符号
 */
public class TruncateResolver extends AbstractResolver {

    private TruncateSymbol[] borderSymbolArr;   // 边缘符号数组

    private int[] borderSymbolLens;

    public TruncateResolver(IResolverResultFactory resolverResultFactory, TruncateSymbol[] border) {
        super(resolverResultFactory);

        if(border == null || border.length == 0){
            throw new IllegalArgumentException("border is undefined.");
        }

        this.borderSymbolArr = border;
        this.borderSymbolLens = new int[border.length];

        for(int i = 0; i < border.length; i++){
            TruncateSymbol ts = border[i];
            this.borderSymbolLens[i] = ts.symbol.length;
        }
    }

    @Override
    protected void resolve(char[] expStr, int startIndex, IResolverResult resolveResult) {
        char ch = expStr[startIndex];
        if(ch < '0' || ch > '9'){
            int[] match = new int[borderSymbolArr.length];
            int start;
            int i = start = startIndex; // 遍历的指针位置
            int matchCharLen = 0;
            int findCharIndex = -1;
            out : for(; i < expStr.length; i++){
                for(int c = 0; c < borderSymbolArr.length; c++){
                    int mf = match[c];
                    if(borderSymbolArr[c].symbol[mf] == expStr[i]){
                        match[c]++;
                        if(mf + 1 == borderSymbolLens[c]){
                            matchCharLen = mf + 1;
                            findCharIndex = c;
                            break out;
                        }
                    }else{
                        match[c] = 0;
                    }
                }
            }

            StringBuilder sb = new StringBuilder();
            if(matchCharLen != 0){
                for(int k = start; k <= i - matchCharLen; k++){
                    sb.append(expStr[k]);
                }
                TruncateSymbol ts = this.borderSymbolArr[findCharIndex];
                IExpression variableExpression = ts.symbolExpressionFactory.createExpression(sb.toString());
                resolveResult.setExpression(variableExpression);
                resolveResult.setEndIndex(i - matchCharLen);
            }
        }
    }

    public static class TruncateSymbol {
        private char[] symbol;
        private ISymbolExpressionFactory symbolExpressionFactory;
        public TruncateSymbol(String symbol, ISymbolExpressionFactory symbolExpressionFactory) {
            this.symbol = symbol.toCharArray();
            this.symbolExpressionFactory = symbolExpressionFactory;
        }
    }
}
