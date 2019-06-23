package frog.calculator.operate.doubleopr;

import frog.calculator.operate.IOperator;

public abstract class ADiscriminator implements IDiscriminator {

    private ADiscriminator next;

    @Override
    public IOperator recognize(String symbol) {
        IOperator operator = this.retrieve(symbol);
        if(operator == null && this.next != null){
            operator = next.recognize(symbol);
        }
        return operator;
    }

    protected abstract IOperator retrieve(String symbol);

    protected void setNext(ADiscriminator discriminator){
        this.next = discriminator;
    }
}
