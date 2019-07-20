package frog.calculator.analyzer;

import frog.calculator.express.IExpression;

public class AnalyzeResult {

    private String expression;  // 结果表达式

    private IExpression[] declareExpression;    // 声明表达式, 包括变量, 函数定义等

    private boolean runnable = true;

}
