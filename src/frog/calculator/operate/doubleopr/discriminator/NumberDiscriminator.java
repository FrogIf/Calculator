package frog.calculator.operate.doubleopr.discriminator;

import frog.calculator.operate.IOperator;
import frog.calculator.operate.doubleopr.ADiscriminator;
import frog.calculator.operate.doubleopr.oprs.end.NumberDoubleOperator;

public class NumberDiscriminator extends ADiscriminator {

    @Override
    protected IOperator retrieve(String symbol) {
        IOperator operator = null;
        if(this.isNumber(symbol)){
            operator = new NumberDoubleOperator();
        }

        return operator;
    }

    private boolean isNumber(String symbol){
        char first = symbol.charAt(0);
        if(symbol.length() == 1){
            if(first < '0' || first > '9'){
                return false;
            }
        }else{
            if(first != '-' && (first < '0' || first > '9')){
                return false;
            }
        }

        boolean hasDot = false;
        for (int i = 1; i < symbol.length(); i++){
            char ch = symbol.charAt(i);
            if(ch == '.' && !hasDot) {
                hasDot = true;
                continue;
            }
            if(ch < '0' || ch > '9'){
                return false;
            }
        }
        return true;
    }
}
