package frog.calculator.number;

public class IntegerNumber {

    private static final byte POSITIVE = 0;

    private static final byte NEGATIVE = 1;

    public static final IntegerNumber ZERO = new IntegerNumber();

    static {
        ZERO.number = new StringBuilder("0");
    }

    private byte sign;

    private StringBuilder number;

    private String literal;

    private IntegerNumber(){ }

    public IntegerNumber(String number){
        number = fixNumber(number);

        this.sign = number.charAt(0) != '-' ? POSITIVE : NEGATIVE;

        int len = number.length();
        for(int i = this.sign; i < len; i++){
            char ch = number.charAt(i);
            if(ch < '0' || ch > '9'){
                throw new IllegalArgumentException("it's not a integer : " + number);
            }
        }

        this.literal = number.substring(this.sign);
        this.number = new StringBuilder(this.literal).reverse();
    }

    private String fixNumber(String number){
        if(number == null){
            throw new IllegalArgumentException("input number is blank.");
        }else{
            number = number.trim();
            if(number.equals("") || number.equals("-")){
                throw new IllegalArgumentException("input number is blank.");
            }
        }
        return number;
    }

    private IntegerNumber add(IntegerNumber left, IntegerNumber right, byte operator){
        IntegerNumber result;

        if((left.sign ^ right.sign) == operator){
            result = new IntegerNumber();
            result.number = unsignedAdd(left.number, right.number);
            result.sign = left.sign;
        }else{
            int fa = unsignedCompare(left.number, right.number);
            if(fa == 0){
                return ZERO;
            }else {
                result = new IntegerNumber();
                if(fa < 0){
                    result.number = unsignedSubtract(right.number, left.number);
                    result.sign = NEGATIVE;
                }else{
                    result.number = unsignedSubtract(left.number, right.number);
                }
            }
            result.sign = (byte) (left.sign ^ result.sign);
        }

        return result;
    }

    public IntegerNumber add(IntegerNumber r){
        return add(this, r, POSITIVE);
    }

    public IntegerNumber sub(IntegerNumber r){
        return add(this, r, NEGATIVE);
    }

    /**
     * 大于0, 则a大
     * 小于0, 则b大
     * 等于0, 则一样大
     */
    private int unsignedCompare(StringBuilder a, StringBuilder b){
        int al = a.length();
        int bl = b.length();

        if(al == bl){
            for(int i = al - 1; i > -1; i--){
                char ach = a.charAt(i);
                char bch = b.charAt(i);

                if(ach != bch){
                    return ach - bch;
                }
            }
            return 0;
        }else{
            return al - bl;
        }
    }

    public IntegerNumber mult(IntegerNumber r){
        StringBuilder resultSb = unsignedMultiply(this.number, r.number);
        IntegerNumber result = new IntegerNumber();
        result.number = resultSb;
        result.sign = (byte) (1 & (this.sign ^ r.sign));
        return result;
    }

    public IntegerNumber div(IntegerNumber r){
        return null;
    }

    @Override
    public String toString() {
        if(this.literal == null){
            StringBuilder temp = new StringBuilder(this.number);
            if(this.sign == NEGATIVE){
                temp.append('-');
            }
            this.literal = temp.reverse().toString();
        }
        return this.literal;
    }


    //====================================================大数运算================================================

    /*
     * 无符号相加
     */
    private static StringBuilder unsignedAdd(StringBuilder left, StringBuilder right){
        StringBuilder large;
        StringBuilder small;
        if(left.length() > right.length()){
            large = left;
            small = right;
        }else{
            large = right;
            small = left;
        }

        int m = 0;
        int n = 0;

        StringBuilder result = new StringBuilder();

        int carry = 0;
        int len;
        for(len = small.length(); n < len; n++, m++){
            int mr = (small.charAt(n) - '0') + (large.charAt(m) - '0') + carry;
            carry = mr / 10;
            result.append(mr % 10);
        }

        for(len = large.length(); m < len; m++){
            int mr = (large.charAt(m) - '0') + carry;
            carry = mr / 10;
            result.append(mr % 10);
        }

        if(carry > 0) {
            while(carry > 0) {
                result.append(carry % 10);
                carry = carry / 10;
            }
        }

        return result;
    }


    /*
     * 无符号相减
     * 并且, 调用者保证left一定大于right
     */
    private static StringBuilder unsignedSubtract(StringBuilder left, StringBuilder right){
        StringBuilder sb = new StringBuilder();
        int borrow = 0;
        int stepRes;

        int m = 0;
        int n = 0;

        int z = 0;

        int r;
        int len;

        for(len = right.length(); n < len; n++, m++){
            stepRes = (left.charAt(m) - '0') - (right.charAt(n) - '0') - borrow;
            if(stepRes < 0){
                r = (20 + stepRes) % 10;
                borrow = 1;
            }else{
                r = stepRes;
                borrow = 0;
            }
            z = r == 0 ? (z + 1) : 0;
            sb.append(r);
        }

        for(len = left.length(); m < len; m++){
            stepRes = left.charAt(m) - '0' - borrow;
            if(stepRes < 0){
                borrow = 1;
                r = (20 + stepRes) % 10;
            }else{
                borrow = 0;
                r = stepRes;
            }
            z = r == 0 ? (z + 1) : 0;
            sb.append(r);
        }

        sb = sb.delete(sb.length() - z, sb.length());

        return sb;
    }

    private static StringBuilder unsignedMultiply(StringBuilder left, StringBuilder right){
        return ordinaryUnsignedMultiply(left, right);
    }

    private static StringBuilder ordinaryUnsignedMultiply(StringBuilder left, StringBuilder right){
        int ll = left.length();
        int rl = right.length();

        StringBuilder result = new StringBuilder("0");
        StringBuilder fill = new StringBuilder();

        for(int i = 0; i < rl; i++){
            int r = right.charAt(i) - '0';
            int carry = 0;
            StringBuilder sb = new StringBuilder(fill);
            for(int j = 0; j < ll; j++){
                int l = left.charAt(j) - '0';
                int res = r * l + carry;
                carry = res / 10;
                sb.append(res % 10);
            }

            if(carry > 0){
                while(carry != 0){
                    sb.append(carry % 10);
                    carry /= 10;
                }
            }

            result = unsignedAdd(result, sb);
            fill.append("0");
        }

        return result;
    }

    public static void main(String[] args){
        StringBuilder left = new StringBuilder("482348894757138957893275893245793285789345").reverse();
        StringBuilder right = new StringBuilder("8394058028490135483459032845").reverse();

        StringBuilder result = ordinaryUnsignedMultiply(left, right);
        result.reverse();
        System.out.println(result.toString());
        System.out.println("4048864612569505688543326935186834630098918683606710424086416106036525".equals(result.toString()));
    }

}
