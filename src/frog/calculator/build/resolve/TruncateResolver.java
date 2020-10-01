package frog.calculator.build.resolve;

import frog.calculator.express.IExpression;

/**
 * 截断解析器<br/>
 * 从某一位置开始向后遍历, 知道出现指定字符, 将该字符之前的子串作为一个符号
 */
public class TruncateResolver extends AbstractResolver {

    private char[][] borderSymbolArr;   // 边缘符号数组

    private int[] borderSymbolLens;

    private ISymbolExpressionFactory symbolExpressionFactory;

    public TruncateResolver(ISymbolExpressionFactory symbolExpressionFactory, String[] border, IResolverResultFactory resolverResultFactory){
        super(resolverResultFactory);
        if(border == null || border.length == 0){
            throw new IllegalArgumentException("border is undefined.");
        }

        if(symbolExpressionFactory == null){
            throw new IllegalArgumentException("symbol expression factory is null.");
        }
        this.symbolExpressionFactory = symbolExpressionFactory;

        this.borderSymbolArr = new char[border.length][];
        this.borderSymbolLens = new int[border.length];

        for(int i = 0; i < border.length; i++){
            char[] charArr = border[i].toCharArray();
            borderSymbolArr[i] = charArr;
            this.borderSymbolLens[i] = charArr.length;
        }
    }

    @Override
    public IResolverResult resolve(char[] expStr, int startIndex) {
        char ch = expStr[startIndex];
        if(ch < '0' || ch > '9'){
            int[] match = new int[borderSymbolArr.length];
            int start;
            int i = start = startIndex; // 遍历的指针位置
            int matchCharLen = 0;
            out : for(; i < expStr.length; i++){
                for(int c = 0; c < borderSymbolArr.length; c++){
                    int mf = match[c];
                    if(borderSymbolArr[c][mf] == expStr[i]){
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
            if(matchCharLen != 0 && i - matchCharLen >= start){
                for(int k = start; k <= i - matchCharLen; k++){
                    sb.append(expStr[k]);
                }
                IExpression variableExpression = this.symbolExpressionFactory.createExpression(sb.toString());
                return this.resolverResultFactory.createResolverResultBean(variableExpression);
            }
        }

        return null;
    }
}
