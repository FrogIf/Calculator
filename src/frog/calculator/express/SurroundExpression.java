package frog.calculator.express;

import frog.calculator.math.BaseNumber;
import frog.calculator.exec.space.Coordinate;
import frog.calculator.exec.space.FixedAlignSpaceBuilder;
import frog.calculator.exec.space.ISpace;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.ITraveller;
import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.LinkedList;

public class SurroundExpression extends AbstractExpression {

    private String separatorSymbol;

    private String closeSymbol;

    private IExpression suspendExpression;

    private Element currentElement;

    private IList<Element> elements;

    private boolean isClose;

    public SurroundExpression(String openSymbol, String separatorSymbol, String closeSymbol) {
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
        traveller = null;
        role = ROLE_UNDEFINE;
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
    public ISpace<BaseNumber> interpret() {
        if(role == ROLE_ARG_LIST){
            throw new IllegalStateException("the expression has output as argument list.");
        }
        FixedAlignSpaceBuilder<BaseNumber> builder = new FixedAlignSpaceBuilder<>();
        builder.setDimension(1);
        builder.setWidth(0, this.elements.size());
        ISpace<BaseNumber> space = builder.build();
        Iterator<Element> iterator = this.elements.iterator();
        int i = 0;
        while(iterator.hasNext()){
            Element next = iterator.next();
            ISpace<BaseNumber> cSpace = next.expression.interpret();
            BaseNumber value = cSpace.get(new Coordinate(i));
            space.add(value, new Coordinate(i));
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
        return ExpressionConstant.MIN_BUILD_FACTOR;
    }

    @Override
    public IExpression clone() {
        SurroundExpression clone = (SurroundExpression) super.clone();
        clone.init();
        return clone;
    }

    private ITraveller<Element> traveller = null;

    private static final int ROLE_SPACE = 2;

    private static final int ROLE_ARG_LIST = 0;

    private static final int ROLE_UNDEFINE = 1;

    private int role = ROLE_UNDEFINE;  // 标记一个surround expression对象是作为参数列表输出还是space输出, 1 : 未确定, 0 : 参数列表, 2 : space

    @Override
    public boolean hasNextChild() {
        if(role == ROLE_SPACE){
            throw new IllegalStateException("this expression has output as a space.");
        }
        if(traveller == null){
            traveller = elements.iterator();
        }
        return traveller.hasNext();
    }

    @Override
    public IExpression nextChild() {
        if(role == ROLE_SPACE){
            throw new IllegalStateException("this expression has output as a space.");
        }
        if(traveller == null){
            traveller = elements.iterator();
        }
        return traveller.next().expression;
    }

    private static class Element{
        private IExpression expression;

        @Override
        public String toString() {
            return expression.symbol();
        }
    }
}
