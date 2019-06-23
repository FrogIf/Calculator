package frog.calculator.operate.doubleopr;

import frog.calculator.operate.IOperator;
import frog.calculator.operate.IOperatorPool;
import frog.calculator.operate.doubleopr.discriminator.MidOperatorDiscriminator;
import frog.calculator.operate.doubleopr.discriminator.NumberDiscriminator;
import frog.calculator.operate.doubleopr.discriminator.RightOperatorDiscriminator;

public class DefaultDoubleOperatorPool implements IOperatorPool {

    private IDiscriminator discriminator;

    public DefaultDoubleOperatorPool(){
        NumberDiscriminator numberDiscriminator = new NumberDiscriminator();
        MidOperatorDiscriminator midOperatorDiscriminator = new MidOperatorDiscriminator();
        RightOperatorDiscriminator rightOperatorDiscriminator = new RightOperatorDiscriminator();

        numberDiscriminator.setNext(midOperatorDiscriminator);
        midOperatorDiscriminator.setNext(rightOperatorDiscriminator);

        this.discriminator = numberDiscriminator;
    }

    @Override
    public IOperator getOperator(String symbol) {
        return discriminator.recognize(symbol);
    }
}
