package frog.calculator.dimpl.operate.util;

import frog.calculator.express.result.AResultExpression;
import frog.calculator.dimpl.operate.DoubleResultExpression;

public class DoubleOperatorUtil {

    public static double resultExpressionToDouble(AResultExpression exp){
        double d;
        if(exp instanceof DoubleResultExpression){
            d = ((DoubleResultExpression)exp).getDoubleValue();
        }else{
            String leftValue = exp.resultValue();
            d = Double.parseDouble(leftValue);
        }
        return d;
    }

    public static AResultExpression doubleToResultExpression(double value){
        DoubleResultExpression doubleResultExpression = new DoubleResultExpression();
        doubleResultExpression.setDoubleValue(value);
        return doubleResultExpression;
    }

}
