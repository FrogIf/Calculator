package frog.calculator.express.container;

import frog.calculator.express.AbstractExpression;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.space.*;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.LinkedList;

public class TupleExpression extends AbstractExpression {

    private String separatorSymbol;

    private String closeSymbol;

    private IExpression suspendExpression;

    private Element currentElement;

    private IList<Element> elements;

    private boolean isClose;

    public TupleExpression(String openSymbol, String separatorSymbol, String closeSymbol) {
        super(openSymbol, null);
        if(closeSymbol == null || separatorSymbol == null){
            throw new IllegalArgumentException("symbol is not enough.");
        }
        this.separatorSymbol = separatorSymbol;
        this.closeSymbol = closeSymbol;
        init();
    }

    private void init(){
        currentElement = new Element();
        elements = new LinkedList<>();
        elements.add(currentElement);
    }

    @Override
    public final boolean createBranch(IExpression childExpression) {
        boolean success = false;
        if(!isClose){
            if(this.separatorSymbol.equals(childExpression.symbol())){
                currentElement = new Element();
                elements.add(currentElement);
                success = true;
            }else{
                if(childExpression.order() < this.order()){
                    if(this.suspendExpression == null){
                        this.suspendExpression = childExpression;
                        success = true;
                    }
                }else{
                    if(currentElement.expression == null){
                        currentElement.expression = childExpression;
                        success = true;
                    }else{
                        IExpression eleRoot = currentElement.expression.assembleTree(childExpression);
                        success = eleRoot != null;
                        if(success){
                            currentElement.expression = eleRoot;
                        }
                    }
                }
            }
        }
        return success;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        IExpression root = null;

        if(isClose){
            if(expression.createBranch(this)){
                return expression;
            }
        }else{
            if(this.closeSymbol.equals(expression.symbol())){
                root = reversal();
            }else if(expression.buildFactor() < 0){
                if(expression.createBranch(this)){
                    root = expression;
                }
            }else{
                if(this.createBranch(expression)){
                    root = this;
                }
            }
        }

        return root;
    }

    private IExpression reversal(){
        IExpression root = this;
        isClose = true;
        if(this.suspendExpression != null){
            root = this.suspendExpression;
            this.suspendExpression = null;
            if(!root.createBranch(this)){
                throw new IllegalStateException("the expression can't put into this tree.");
            }
        }
        return root;
    }

    @Override
    public ISpace interpret() {
        Iterator<Element> iterator = this.elements.iterator();
        SpaceBuilder builder = new SpaceBuilder();
        builder.setDimension(1);
        builder.setWidth(0, this.elements.size());
        ISpace space = builder.build();
        int i = 0;
        while(iterator.hasNext()){
            Element next = iterator.next();
            ISpace cSpace = next.expression.interpret();
            IPoint value = cSpace.getPoint(new Coordinate(i));
            space.addPoint(value, new Coordinate(i));
            i++;
        }
        return space;
    }

    @Override
    public boolean isLeaf() {
        return isClose;
    }

    @Override
    public int buildFactor() {
        return -1;
    }

    @Override
    public IExpression clone() {
        TupleExpression clone = (TupleExpression) super.clone();
        clone.init();
        return clone;
    }

    @Override
    public void setExpressionContext(IExpressionContext context) {
        this.context = context;
        Iterator<Element> iterator = elements.iterator();
        while(iterator.hasNext()){
            Element next = iterator.next();
            if(next.expression != null){
                next.expression.setExpressionContext(context);
            }
        }
    }

    @Override
    public boolean hasNextChild() {
        return false;
    }

    @Override
    public IExpression nextChild() {
        return null;
    }

    private static class Element{
        private IExpression expression;
    }
}
