package frog.calculator.build.resolve;

import frog.calculator.ICalculatorManager;
import frog.calculator.express.IExpression;

/**
 * plus or minus<br/>
 * 正负号解析器
 */
public class PMResolver extends AbstractResolver {

    private char[] plusSymbol = {'+'};  // 该char数组表示一个加号, 每一个元素表示符号的一部分

    private char[] minusSymbol = {'-'}; // 同上

    private final IExpression plusExpression;

    private final IExpression minusExpression;

    public PMResolver(ICalculatorManager manager, IExpression plusExpression, IExpression minusExpression) {
        super(manager);
        this.plusExpression = plusExpression;
        this.minusExpression = minusExpression;
        if(plusExpression.symbol() != null){
            this.plusSymbol = plusExpression.symbol().toCharArray();
        }
        if(minusExpression.symbol() != null){
            this.minusSymbol = minusExpression.symbol().toCharArray();
        }
        if(minusSymbol.length == plusSymbol.length){
            boolean isSame = true;
            for(int i = 0; i < minusSymbol.length; i++){
                if(minusSymbol[i] != plusSymbol[i]){
                    isSame = false;
                    break;
                }
            }
            if(isSame){
                throw new IllegalArgumentException("add sign is same with sub sign.");
            }
        }
    }

    @Override
    public IResolverResult resolve(char[] chars, int startIndex) {
        int[] counts = new int[2];
        int[] lens = {plusSymbol.length, minusSymbol.length};
        int mark = 3;   // 00b(0)两者都不是, 01b(1)是加, 10b(2)是减, 11(3)即加又减

        for(int i = startIndex, j = 0; i < chars.length; i++){
            if(mark == 3){
                mark = chars[i] == plusSymbol[j] ? 1 : 0;
                if(chars[i] == minusSymbol[j]){
                    mark |= 2;
                }
                if(mark == 0) break;
            }

            j++;
            if(mark != 3){
                if(j == lens[mark - 1]){
                    counts[mark - 1]++;
                    j = 0;
                    mark = 3;
                }
            }
        }

        if(counts[0] > 0 || counts[1] > 0){
            IResolverResult resolverResult = this.manager.createResolverResult(counts[1] % 2 == 0 ? plusExpression.clone() : minusExpression.clone());
            resolverResult.setOffset(counts[1] * minusSymbol.length + counts[0] * plusSymbol.length);
            return resolverResult;
        }

        return null;
    }
}
