package frog.calculator.compile.syntax;

import frog.calculator.compile.semantic.IExecutor;
import frog.calculator.value.IValue;

/**
 * abstract node, 提供一些公共的逻辑, 所有的node都应继承这个抽象类, 而不应该继承ISyntaxNode接口
 * 继承该抽象类的node, open状态一旦变为false, 将不会再变为true
 */
public abstract class AbstractSyntaxNode implements ISyntaxNode {

    protected final IExecutor executor;

    protected final String literal;

    protected ISyntaxTreeContext context = null;

    protected final int priority;

    protected int position = -1;

    protected AbstractSyntaxNode(IExecutor executor, String literal, int priority){
        this.executor = executor;
        this.literal = literal;
        this.priority = priority;
    }

    @Override
    public final String literal(){
        return this.literal;
    }

    @Override
    public final int priority(){
        return this.priority;
    }

    @Override
    public int position(){
        return this.position;
    }

    @Override
    public final ISyntaxNode associate(ISyntaxNode input){
        boolean open = this.isOpen();
        ISyntaxNode parent = this;
        ISyntaxNode child = input;
        if(input.isOpen() != open){    // 两个open状态不相同
            if(!open){  // this不是open
                parent = input;
                child = this;
            }
        }else if(open){    // 两个node都处于open状态
            int p = this.priority() - input.priority();
            /**
             * 1. 优先级相等, input位置靠后
             * 2. input优先级小
             */
            if((p == 0 && this.position() < input.position()) || p > 0){
                parent = input;
                child = this;
            }
        }else{  // 两个node都是close
            // 使失败
            parent = null;
            child = null;
        }

        ISyntaxNode root =  null;
        if(parent != null && parent.branchOff(child)){
            root = parent;
            if(!parent.isOpen()){
                IOpenStatusChangeListener listener = this.getOpenStatusChangeListener();
                root = listener != null ? listener.onChange(root) : root;
            }
        }
        return root;
    }

    /**
     * 当开闭状态改变时
     * @param selfNode 改变的节点自身
     * @return 新的语法树根节点
     */
    protected IOpenStatusChangeListener getOpenStatusChangeListener(){
        // 默认情况下, 不监听状态改变
        return null;
    }

    @Override
    public IValue execute(){
        return this.executor.execute(this, context);
    }
}
