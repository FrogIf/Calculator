package frog.calculator.resolver.resolve;

import frog.calculator.express.IExpression;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.IResolverResultFactory;
import frog.calculator.resolver.ResolverResultType;
import frog.calculator.resolver.resolve.factory.ICustomSymbolExpressionFactory;
import frog.calculator.util.NumberUtil;

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
        if(NumberUtil.isNumber(expStr[startIndex])){
            return;
        }else{
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
                if(ts.contain){
                    sb.append(ts.symbol);
                }

                IExpression variableExpression = ts.customSymbolExpressionFactory.createExpression(sb.toString());
                resolveResult.setExpression(variableExpression);
                resolveResult.setType(ResolverResultType.DECLARE);
                resolveResult.setEndIndex(i - matchCharLen + (ts.contain ? ts.symbol.length : 0));
            }
        }
    }

    public static class TruncateSymbol {
        private boolean contain;
        private char[] symbol;
        private ICustomSymbolExpressionFactory customSymbolExpressionFactory;

        public TruncateSymbol(String symbol, ICustomSymbolExpressionFactory customSymbolExpressionFactory) {
            this(symbol, customSymbolExpressionFactory, false);
        }

        public TruncateSymbol(String symbol, ICustomSymbolExpressionFactory customSymbolExpressionFactory, boolean contain) {
            this.contain = contain;
            this.symbol = symbol.toCharArray();
            this.customSymbolExpressionFactory = customSymbolExpressionFactory;
        }
    }
}
