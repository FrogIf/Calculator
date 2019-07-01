package frog.calculator.resolve.register;

import frog.calculator.express.IExpression;
import frog.calculator.operate.IOperator;

import java.util.ArrayList;
import java.util.List;

public class Registry {

    private char symbol = 0;

    private IExpression expression;

    private IOperator operator;

    private String completeSymbol = "";

    private List<Registry> table = new ArrayList<>();

    public IExpression getExpression() {
        return expression;
    }

    public IOperator getOperator() {
        return operator;
    }

    public String getCompleteSymbol() {
        return completeSymbol;
    }

    /**
     * 注册表达式
     * @param exp
     * @param expression
     */
    public void registe(String exp, IExpression expression){
        registe(exp.toCharArray(), 0, expression, null, "");
    }

    /**
     * 注册表达式和运算符
     * @param exp
     * @param expression
     * @param operator
     */
    public void registe(String exp, IExpression expression, IOperator operator) {
        registe(exp.toCharArray(), 0, expression, operator, "");
    }

    /**
     * 注册运算器
     * @param exp
     * @param operator
     */
    public void registe(String exp, IOperator operator){
        registe(exp.toCharArray(), 0, null, operator, "");
    }

    private void registe(char[] oprs, int startIndex, IExpression expression, IOperator operator, String upStr){
        char ch = oprs[startIndex];
        if(table.isEmpty()){
            Registry newreg = new Registry();
            newreg.completeSymbol = upStr + ch;
            newreg.symbol = ch;
            if(startIndex == oprs.length - 1){
                newreg.operator = operator;
                newreg.expression = expression;
            }
            table.add(newreg);
        }else{
            int pos = findReg(ch);
            Registry reg = table.get(pos);

            if(startIndex == oprs.length - 1){  // 如果是最后一位
                if(reg.symbol != ch){
                    Registry newreg = new Registry();
                    newreg.completeSymbol = upStr + ch;
                    newreg.symbol = ch;
                    newreg.operator = operator;
                    newreg.expression = expression;
                    insertReg(newreg, pos, reg);
                }else{
                    if(expression != null){
                        if(reg.expression == null){
                            reg.expression = expression;
                        }else{
                            throw new IllegalArgumentException("duplicate define expression.");
                        }
                    }

                    if(operator != null){
                        if(reg.operator == null){
                            reg.operator = operator;
                        }else{
                            throw new IllegalArgumentException("duplicate define operator.");
                        }
                    }
                }
            }else{
                if(reg.symbol != ch){
                    Registry newreg = new Registry();
                    newreg.completeSymbol = upStr + ch;
                    newreg.symbol = ch;
                    insertReg(newreg, pos, reg);
                }
                reg.registe(oprs, startIndex + 1, expression, operator, upStr);
            }
        }

    }

    private void insertReg(Registry reg, int pos, Registry oldReg){
        if(oldReg == null){
            table.add(pos, reg);
        }else{
            if(oldReg.symbol < reg.symbol){
                table.add(pos + 1, reg);
            }else{
                table.add(pos, reg);
            }
        }
    }

    public Registry retrieveRegistryInfo(char[] chars, int startIndex){
        if(chars.length <= startIndex){
            return this;
        }
        char ch = chars[startIndex];
        if(table.isEmpty()){
            return this;
        }else{
            int pos = findReg(ch);
            Registry reg = table.get(pos);
            if(reg.symbol == ch){
                return reg.retrieveRegistryInfo(chars, startIndex + 1);
            }else{
                return this;
            }
        }
    }

    /**
     * 返回目标位置或者接近目标位置的位置
     * @param ch
     * @return
     */
    private int findReg(char ch){
        int low = 0;
        int high = table.size() - 1;

        Registry reg;
        while(low < high){
            int mid = (low + high) / 2;
            reg = table.get(mid);
            if(ch == reg.symbol){
                return mid;
            }else if(ch > reg.symbol){
                low = mid + 1;
            }else{
                high = mid - 1;
            }
        }

        return low;
    }

    @Override
    public String toString() {
        return completeSymbol;
    }
}
