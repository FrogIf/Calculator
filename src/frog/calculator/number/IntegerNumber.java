package frog.calculator.number;

public class IntegerNumber {

    private static final byte POSITIVE = 0;

    private static final byte NEGATIVE = 1;

    public static final IntegerNumber ZERO = new IntegerNumber();

    static {
        ZERO.number = "0";
    }

    private byte sign;

    private String number;

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

        this.number = number.substring(this.sign);
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
            result.number = add(left.number, right.number);
            result.sign = left.sign;
        }else{
            int fa = compare(left.number, right.number);
            if(fa == 0){
                return ZERO;
            }else {
                result = new IntegerNumber();
                if(fa < 0){
                    result.number = sub(right.number, left.number);
                    result.sign = NEGATIVE;
                }else{
                    result.number = sub(left.number, right.number);
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
    private int compare(String a, String b){
        int alen = a.length();
        int blen = b.length();

        if(alen == blen){
            for(int i = 0; i < alen; i++){
                char ach = a.charAt(i);
                char bch = b.charAt(i);

                if(ach != bch){
                    return ach - bch;
                }
            }
            return 0;
        }else{
            return alen - blen;
        }
    }

    private String add(String left, String right){
        String large;
        String small;
        if(left.length() > right.length()){
            large = left;
            small = right;
        }else{
            large = right;
            small = left;
        }

        int m = large.length() - 1;
        int n = small.length() - 1;

        StringBuilder sb = new StringBuilder();

        int carry = 0;
        for(; n >= 0; n--, m--){
            int mr = (small.charAt(n) - '0') + (large.charAt(m) - '0') + carry;
            carry = mr / 10;
            sb.append(mr % 10);
        }

        for(; m >= 0; m--){
            int mr = (large.charAt(m) - '0') + carry;
            carry = mr / 10;
            sb.append(mr % 10);
        }

        if(carry > 0) {
            while(carry > 0) {
                sb.append(carry % 10);
                carry = carry / 10;
            }
        }

        return sb.reverse().toString();
    }


    private String sub(String left, String right){
        StringBuilder sb = new StringBuilder();
        int borrow = 0;
        int stepRes;

        int m = left.length() - 1;
        int n = right.length() - 1;

        int z = 0;

        for(; n >= 0; n--, m--){
            stepRes = (left.charAt(m) - '0') - (right.charAt(n) - '0') - borrow;
            int r;
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

        for(; m >= 0; m--){
            stepRes = left.charAt(m) - '0' - borrow;
            int r;
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

        return sb.reverse().toString();
    }

    public IntegerNumber mult(IntegerNumber r){
        return null;
    }

    public IntegerNumber div(IntegerNumber r){
        return null;
    }

    @Override
    public String toString() {
        return this.sign == POSITIVE ? this.number : ("-" + this.number);
    }
}
