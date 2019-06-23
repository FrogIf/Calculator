package frog.calculator.express.end;

public class NumberExpression extends EndExpression {

    private final StringBuilder sb = new StringBuilder();

    public NumberExpression(){}

    public NumberExpression(String value) {
        sb.append(value);
    }

    public void assemble(char ch){
        sb.append(ch);
    }

    public String number(){
        return sb.toString();
    }

    @Override
    public int getPriority(){
        return super.getPriority() + 1;
    }

    @Override
    public String toString(){
        return sb.toString();
    }

    public boolean isEmpty(){
        return sb.length() == 0;
    }
}
