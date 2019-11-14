package frog.calculator;

import frog.calculator.connect.ICalculatorSession;
import frog.calculator.exception.BuildException;
import frog.calculator.exec.space.Coordinate;
import frog.calculator.exec.space.IRange;
import frog.calculator.exec.space.ISpace;
import frog.calculator.express.IExpression;
import frog.calculator.math.BaseNumber;

public class Calculator {

    // 计算管理器
    private ICalculatorManager calculatorManager;

    public Calculator(ICalculatorConfigure calculatorConfigure) {
        if (calculatorConfigure == null) {
            throw new IllegalArgumentException("configure is null.");
        }
        this.calculatorManager = calculatorConfigure.getComponentFactory().createCalculatorManager(calculatorConfigure);
    }

    /**
     * 执行计算
     * @param expression 待计算的表达式
     * @param session    会话
     * @return 解析结果
     */
    public String calculate(String expression, ICalculatorSession session) throws BuildException {
        // 构造解析树
        IExpression expTree = this.calculatorManager.createExpressionBuilder(session).build(expression);

        ISpace<BaseNumber> result = expTree.interpret(); // 执行计算

        IRange range = result.getRange();
        int[] widths = range.maxWidths();
        if(widths.length == 1 && widths[0] == 1){
            return result.get(new Coordinate(0)).toString();
        }else{
            StringBuilder sb = new StringBuilder("[");
            int[] coordinateArr = new int[range.dimension()];
            for(int i = coordinateArr.length - 1; i >= 0; i--){
                int w = widths[i];
                for(int j = 0; j < w; j++){
                    coordinateArr[i] = j;
                    BaseNumber number = result.get(new Coordinate(coordinateArr));
                    if(j > 0){
                        sb.append(',');
                    }
                    sb.append(number == null ? "null" : number.toString());
                }
                if(i > 0){ sb.append(';'); }
            }
            sb.append("]");
            return sb.toString();
        }
    }

    public ICalculatorSession getSession(){
        return this.calculatorManager.createCalculatorSession();
    }

}
