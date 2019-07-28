package frog.calculator.resolver.resolve;

import frog.calculator.express.IExpression;
import frog.calculator.resolver.ResolverResultType;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.IResolverResultFactory;
import frog.calculator.resolver.resolve.factory.ICustomSymbolExpressionFactory;
import frog.calculator.util.NumberUtil;

/**
 * 截断解析器<br/>
 * 从某一位置开始向后遍历, 知道出现指定字符, 将该字符之前的子串作为一个符号
 */
public class TruncateResolver extends AbstractResolver {

    private ICustomSymbolExpressionFactory customExpressionFactory;

    private char[][] borderSymbolChars;    // 二维数组, 第一个维度的每一个元素表示一个截断符号

    private int[] borderSymbolLens;

    public TruncateResolver(IResolverResultFactory resolverResultFactory, String[] border, ICustomSymbolExpressionFactory customExpressionFactory) {
        super(resolverResultFactory);

        if(border == null || border.length == 0){
            throw new IllegalArgumentException("border is undefined.");
        }

        this.borderSymbolChars = new char[border.length][];
        this.borderSymbolLens = new int[border.length];

        for(int i = 0; i < border.length; i++){
            String symbol = border[i];
            if(symbol == null){
                throw new IllegalArgumentException("symbol is null.");
            }
            this.borderSymbolChars[i] = symbol.toCharArray();
            this.borderSymbolLens[i] = symbol.length();
        }

        this.customExpressionFactory = customExpressionFactory;
    }

    @Override
    protected void resolve(char[] expStr, int startIndex, IResolverResult resolveResult) {
        if(NumberUtil.isNumber(expStr[startIndex])){
            return;
        }else{
            int[] match = new int[borderSymbolChars.length];
            int start;
            int i = start = startIndex; // 遍历的指针位置
            int matchCharLen = 0;
            out : for(; i < expStr.length; i++){
                for(int c = 0; c < borderSymbolChars.length; c++){
                    int mf = match[c];
                    if(borderSymbolChars[c][mf] == expStr[i]){
                        match[c]++;
                        if(mf + 1 == borderSymbolLens[c]){
                            matchCharLen = mf + 1;
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

                IExpression variableExpression = customExpressionFactory.createExpression(sb.toString());
                resolveResult.setExpression(variableExpression);
                resolveResult.setType(ResolverResultType.DECLARE);
                resolveResult.setEndIndex(i - matchCharLen);
            }
        }
    }
}
