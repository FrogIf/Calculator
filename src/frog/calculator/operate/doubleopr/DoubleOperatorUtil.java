package frog.calculator.operate.doubleopr;

import frog.calculator.express.result.ResultExpression;

public class DoubleOperatorUtil {

    public static double resultExpressionToDouble(ResultExpression exp){
        double d;
        if(exp instanceof DoubleResultExpression){
            d = ((DoubleResultExpression)exp).getDoubleValue();
        }else{
            String leftValue = exp.resultValue();
            d = Double.parseDouble(leftValue);
        }
        return d;
    }

    public static ResultExpression doubleToResultExpression(double value){
        DoubleResultExpression doubleResultExpression = new DoubleResultExpression();
        doubleResultExpression.setDoubleValue(value);
        return doubleResultExpression;
    }

}
