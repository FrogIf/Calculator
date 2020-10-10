package frog.calculator.express;

import frog.calculator.execute.IOperator;
import frog.calculator.execute.space.ISpace;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.LinkedList;
import frog.calculator.util.collection.UnmodifiableList;

public class ContainerExpression extends AbstractExpression {

    private IExpression suspendExpression;

    private LinkedList<IExpression> elements = new LinkedList<>();

    private final IPutStrategy putStrategy;

    private boolean isClose;

    public ContainerExpression(String symbol, IPutStrategy putStrategy, IOperator operator) {
        super(symbol, operator);
        if(putStrategy == null){
            throw new IllegalArgumentException("put strategy is null.");
        }
        this.putStrategy = putStrategy;
        elements.add(new GhostExpression());
    }

    @Override
    public final boolean createBranch(IExpression childExpression) {
        boolean success = false;
        if(!isClose){
            if(this.putStrategy.prepareNext(childExpression)){
                this.elements.add(new GhostExpression());
                success = true;
            }else{
                if(childExpression.order() < this.order()){
                    if(this.suspendExpression == null){
                        this.suspendExpression = childExpression;
                        success = true;
                    }
                }else{
                    IExpression last = this.elements.last();    // assert last instanceof GhostExpression
                    success = last.createBranch(childExpression);
                }
            }
        }
        return success;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        IExpression root = null;

        if(isClose){    // 如果容器已经关闭, 容器整体成为一个叶子
            if(expression.createBranch(this)){
                return expression;
            }
        }else{  // 如果容器未关闭
            if(putStrategy.canClose(expression)){   // 检测到是闭合符号, 闭合容器
                root = reversal();
            }else if(expression.buildFactor() <= this.buildFactor()){ // buildFactor 小于等于当前buildFactor, 将当前表达式作为输入表达式的子表达式
                if(expression.createBranch(this)){
                    root = expression;
                }
            }else{  // 进入容器的成为容器的子元素
                if(this.createBranch(expression)){
                    root = this;
                }
            }
        }

        return root;
    }

    /**
     * 反转<br/>
     * 将整个容器作为叶子, 放入suspendExpression树中
     */
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
        return super.interpret();
    }

    @Override
    public IList<IExpression> children() {
        return new UnmodifiableList<>(this.elements);
    }

    @Override
    public boolean isLeaf() {
        return isClose;
    }

    @Override
    public int buildFactor() {
        return MIN_BUILD_FACTOR;
    }

    @Override
    public IExpression newInstance() {
        ContainerExpression containerExpression = new ContainerExpression(this.symbol, putStrategy.newInstance(), this.operator);
        this.copyProperty(containerExpression);
        Iterator<IExpression> itr = this.elements.iterator();
        containerExpression.elements = new LinkedList<>();
        while(itr.hasNext()){
            containerExpression.elements.add(itr.next().newInstance());
        }
        return containerExpression;
    }

    public interface IPutStrategy {
        boolean prepareNext(IExpression input);
        boolean canClose(IExpression input);
        IPutStrategy newInstance();
    }
}
