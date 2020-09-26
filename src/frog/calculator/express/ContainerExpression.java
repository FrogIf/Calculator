package frog.calculator.express;

import frog.calculator.execute.IOperator;
import frog.calculator.execute.space.ISpace;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.LinkedList;
import frog.calculator.util.collection.UnmodifiableList;

public class ContainerExpression extends AbstractExpression {

    private final String separatorSymbol;

    private final String closeSymbol;

    private IExpression suspendExpression;

    private LinkedList<IExpression> elements = new LinkedList<>();

    private boolean isClose;

    public ContainerExpression(String openSymbol, String separatorSymbol, String closeSymbol, IOperator operator) {
        super(openSymbol, operator);
        if(closeSymbol == null){
            throw new IllegalArgumentException("symbol is not enough.");
        }
        this.separatorSymbol = separatorSymbol;
        this.closeSymbol = closeSymbol;
        elements.add(new GhostExpression());
    }

    @Override
    public final boolean createBranch(IExpression childExpression) {
        boolean success = false;
        if(!isClose){
            if(this.separatorSymbol != null && this.separatorSymbol.equals(childExpression.symbol())){
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
            if(this.closeSymbol.equals(expression.symbol())){   // 检测到是闭合符号, 闭合容器
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
        if(role == ROLE_ARG_LIST){
            throw new IllegalStateException("the expression has output as argument list.");
        }
        return super.interpret();
    }

    @Override
    public IList<IExpression> children() {
        if(role == ROLE_SPACE){
            throw new IllegalStateException("this expression has output as a space.");
        }
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
        ContainerExpression containerExpression = new ContainerExpression(this.symbol, this.separatorSymbol, this.closeSymbol, this.operator);
        this.copyProperty(containerExpression);
        Iterator<IExpression> itr = this.elements.iterator();
        containerExpression.elements = new LinkedList<>();
        while(itr.hasNext()){
            containerExpression.elements.add(itr.next().newInstance());
        }
        return containerExpression;
    }

    private static final int ROLE_SPACE = 2;

    private static final int ROLE_ARG_LIST = 0;

    private static final int ROLE_UNDEFINE = 1;

    private int role = ROLE_UNDEFINE;  // 标记一个surround expression对象是作为参数列表输出还是space输出, 1 : 未确定, 0 : 参数列表, 2 : space

}
