package frog.calculator.resolver.resolve;

import frog.calculator.express.IExpression;
import frog.calculator.register.IRegister;
import frog.calculator.resolver.IResolverResult;

public class AddSubResolver extends AResolver {

    private char[] addSymbol = {'+'};

    private char[] subSymbol = {'-'};

    private final IExpression addExpression;

    private final IExpression subExpression;

    public AddSubResolver(IResolverResult resolveResultPrototype, IExpression addExpression, IExpression subExpression) {
        super(resolveResultPrototype);
        this.addExpression = addExpression;
        this.subExpression = subExpression;
        if(addExpression.symbol() != null){
            this.addSymbol = addExpression.symbol().toCharArray();
        }
        if(subExpression.symbol() != null){
            this.subSymbol = subExpression.symbol().toCharArray();
        }
        if(subSymbol.length == addSymbol.length){
            boolean isSame = true;
            for(int i = 0; i < subSymbol.length; i++){
                if(subSymbol[i] != addSymbol[i]){
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
    protected void resolve(char[] chars, int startIndex, IResolverResult resolveResult) {
        int[] counts = new int[2];
        int[] lens = {addSymbol.length, subSymbol.length};
        int mark = 3;   // 00b(0)两者都不是, 01b(1)是加, 10b(2)是减, 11(3)即加又减

        for(int i = startIndex, j = 0; i < chars.length; i++){
            if(mark == 3){
                mark = 0;
                if(chars[i] == addSymbol[j]){
                    mark |= 1;
                }
                if(chars[i] == subSymbol[j]){
                    mark |= 2;
                }
                if(mark == 0) break;
            }

            j++;
            if(mark == 1 || mark == 2){
                if(j == lens[mark - 1]){
                    counts[mark - 1]++;
                    j = 0;
                    mark = 3;
                }
            }
        }

        if(counts[0] > 0 || counts[1] > 0){
            resolveResult.setExpression(counts[1] % 2 == 0 ? addExpression.clone() : subExpression.clone());
            resolveResult.setEndIndex(startIndex + counts[1] * subSymbol.length + counts[0] * addSymbol.length - 1);
        }
    }

    @Override
    public void setRegister(IRegister register) {
        if(this.getNext() != null){
            this.getNext().setRegister(register);
        }
    }
}
