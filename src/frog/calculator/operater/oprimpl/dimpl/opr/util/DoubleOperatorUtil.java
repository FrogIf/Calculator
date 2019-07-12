package frog.calculator.operater.oprimpl.dimpl.opr.util;

import frog.calculator.operater.oprimpl.dimpl.DoubleResultExpression;
import frog.calculator.express.result.AResultExpression;

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
