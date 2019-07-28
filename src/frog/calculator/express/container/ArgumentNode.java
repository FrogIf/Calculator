package frog.calculator.express.container;

import frog.calculator.express.IExpression;

class ArgumentNode {
    int len = 0;
    IExpression expression;
    ArgumentNode next;
    boolean isClose;

    ArgumentNode tail = this;

    void reset(){
        tail = this;
        this.isClose = false;
        while(tail.next != null){
            tail.next.isClose = false;
            tail = tail.next;
        }
        tail = this;
    }

    void setTailClose(){
        tail.isClose = true;
    }

    boolean addExpression(IExpression expression){
        boolean result;
        if(tail.isClose){
            if(tail.next == null) tail.next = new ArgumentNode();
            tail = tail.next;
        }
        if(tail.expression == null){
            tail.expression = expression;
            result = true;
            len++;
        }else{
            IExpression tRoot = tail.expression.assembleTree(expression);
            if(tRoot == null){
                result = false;
            }else{
                tail.expression = tRoot;
                result = true;
            }
        }
        return result;
    }

    ArgumentNode copy(){
        ArgumentNode newNode = new ArgumentNode();
        newNode.expression = this.expression == null ? null : this.expression.clone();
        newNode.next = this.next == null ? null : this.next.copy();
        newNode.len = this.len;
        newNode.isClose = this.isClose;
        return newNode;
    }


    IExpression[] getArguments(){
        int len = 0;
        if(this != null){
            len = this.len;
        }
        IExpression[] expressions = new IExpression[len];
        if(len > 0){
            ArgumentNode node = this;
            expressions[0] = node.expression;
            node = node.next;

            int i = 1;
            while(node != null){
                expressions[i] = node.expression;
                node = node.next;
                i++;
            }
        }
        return expressions;
    }

}
