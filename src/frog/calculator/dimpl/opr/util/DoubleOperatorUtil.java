package frog.calculator.dimpl.opr.util;

import frog.calculator.express.IExpression;
import frog.calculator.dimpl.DoubleResultExpression;

public class DoubleOperatorUtil {

    public static double resultExpressionToDouble(IExpression exp){
        double d;
        if(exp instanceof DoubleResultExpression){
            d = ((DoubleResultExpression)exp).getDoubleValue();
        }else{
            String leftValue = exp.symbol();
            d = Double.parseDouble(leftValue);
        }
        return d;
    }

    public static IExpression doubleToResultExpression(double value){
        // TODO 这里的operator不应该是null
        DoubleResultExpression doubleResultExpression = new DoubleResultExpression(String.valueOf(value), null);
        doubleResultExpression.setDoubleValue(value);
        return doubleResultExpression;
    }

}
