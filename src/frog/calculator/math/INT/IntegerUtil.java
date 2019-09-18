package frog.calculator.math.INT;

class IntegerUtil {
    /**
     * 大于0, 则a大
     * 小于0, 则b大
     * 等于0, 则一样大
     */
    static int compare(StringBuilder a, StringBuilder b){
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


    /**
     * 无符号字符串相加
     * @param left
     * @param right
     * @return
     */
    static StringBuilder add(StringBuilder left, StringBuilder right){
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


    /**
     * 无符号相减
     * 并且, 调用者保证left一定大于right(因为不会返回负数)
     * @param left
     * @param right
     * @return
     */
    static StringBuilder subtract(StringBuilder left, StringBuilder right){
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

        if(sb.length() == 0){
            sb = IntegerNumber.ZERO_STR;
        }

        return sb;
    }

    /**
     * 无符号相乘
     * @param left
     * @param right
     * @return
     */
    static StringBuilder multiply(StringBuilder left, StringBuilder right){
        if(left == IntegerNumber.ONE_STR){ return right; }
        else if(right == IntegerNumber.ONE_STR) { return left; }
        return ordinaryMultiply(left, right);
    }

    /**
     * 模拟手算乘法
     * @param left
     * @param right
     * @return
     */
    private static StringBuilder ordinaryMultiply(StringBuilder left, StringBuilder right){
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

            result = add(result, sb);
            fill.append("0");
        }

        return result;
    }

    /**
     * 一个数乘以2
     * @param num
     * @return
     */
    private static StringBuilder doubleNum(StringBuilder num){
        int carry = 0;
        StringBuilder result = new StringBuilder();
        for(int i = 0, len = num.length(); i < len; i++){
            int res = (num.charAt(i) - '0') * 2 + carry;
            carry = res / 10;
            result.append(res % 10);
        }
        if(carry > 0){
            while(carry > 0){
                result.append(carry % 10);
                carry /= 10;
            }
        }
        return result;
    }

    /**
     * 除以2
     * @param num
     * @return
     */
    private static StringBuilder floorHalf(StringBuilder num){
        if(num == IntegerNumber.ZERO_STR){return IntegerNumber.ZERO_STR;}

        int borrow = 0;
        int a, b;
        StringBuilder result = new StringBuilder();
        int i = 0;

        for(int border = num.length() - 1; i < border; i++){
            a = num.charAt(i) - '0' - borrow;
            b = num.charAt(i + 1) - '0';
            borrow = b % 2;
            result.append((a + borrow * 10) / 2);
        }

        a = (num.charAt(i) - '0' - borrow) / 2;
        if(a != 0) { result.append(a); }

        return result;
    }

    /**
     * 判断是偶数还是奇数
     * @param num
     * @return
     */
    static boolean isOdd(StringBuilder num){
        return (num.charAt(0) - '0') % 2 == 1;
    }

    /**
     * 求最大公约数
     * @param left
     * @param right
     * @return
     */
    static StringBuilder gcd(StringBuilder left, StringBuilder right){
        StringBuilder a = left;
        StringBuilder b = right;

        StringBuilder d = IntegerNumber.ONE_STR;

        while(a != IntegerNumber.ZERO_STR && b != IntegerNumber.ZERO_STR){
            boolean aIsOdd = isOdd(a);
            boolean bIsOdd = isOdd(b);

            if(aIsOdd && bIsOdd){
                int mark = compare(a, b);
                if(mark > 0){   // a > b
                    a = subtract(a, b);
                }else{
                    b = subtract(b, a);
                }
            }else if(aIsOdd){
                b = floorHalf(b);
            }else if(bIsOdd){
                a = floorHalf(a);
            }else{
                d = doubleNum(d);
                a = floorHalf(a);
                b = floorHalf(b);
            }
        }

        if(a == IntegerNumber.ZERO_STR){ return multiply(d, b); }
        else{ return multiply(d, a); }
    }

    public static void main(String[] args){

    }


}
