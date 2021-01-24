package frog.calculator.compile.syntax;

import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.compile.semantic.IExecutor;
import frog.calculator.compile.semantic.IResult;

/**
 * abstract node, 提供一些公共的逻辑, 所有的node都应继承这个抽象类, 而不应该继承ISyntaxNode接口
 * 继承该抽象类的node, open状态一旦变为false, 将不会再变为true
 */
public abstract class AbstractSyntaxNode implements ISyntaxNode {

    protected final IExecutor executor;

    protected final String word;

    protected final int priority;

    protected int position = -1;

    protected AbstractSyntaxNode(IExecutor executor, String word, int priority){
        this.executor = executor;
        this.word = word;
        this.priority = priority;
    }

    @Override
    public final String word(){
        return this.word;
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
    public IResult execute(IExecuteContext context){
        return this.executor.execute(this, context);
    }

    @Override
    public String toString(){
        return this.word;
    }
}
