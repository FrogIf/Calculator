package frog.calculator.math;

/**
 * 正整数基本运算
 */
class PositiveIntegerUtil {
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

        sb.delete(sb.length() - z, sb.length());

        if(sb.length() == 0){
            sb = IntegerNumber.ZERO_STR;
        }

        return sb;
    }

    /**
     * 无符号相乘
     */
    static StringBuilder multiply(StringBuilder left, StringBuilder right){
        if(left == IntegerNumber.ONE_STR){ return right; }
        else if(right == IntegerNumber.ONE_STR) { return left; }
        return ordinaryMultiply(left, right);
    }

    /**
     * 模拟手算乘法
     */
    private static StringBuilder ordinaryMultiply(StringBuilder left, StringBuilder right){
        int rl = right.length();

        StringBuilder result = new StringBuilder("0");
        StringBuilder fill = new StringBuilder();

        StringBuilder[] pr = new StringBuilder[9];
        pr[0] = left;
        for(int i = 0; i < rl; i++){
            int r = right.charAt(i) - '0';
            if(r == 0) { continue; }
            if(pr[r - 1] == null){
                pr[r - 1] = simpleMultiply(left, r);
            }
        }

        for(int i = 0; i < rl; i++){
            int r = right.charAt(i) - '1';
            if(r < 0) {
                fill.append("0");
                continue;
            }

            StringBuilder sb = new StringBuilder(fill);
            sb.append(pr[r]);

            result = add(result, sb);
            fill.append("0");
        }

        return result;
    }

    /*
     * 一个大数与另一个10以内(不包括10)的数相乘
     */
    private static StringBuilder simpleMultiply(StringBuilder num, int m){
        int carry = 0;
        StringBuilder result = new StringBuilder();
        for(int i = 0, len = num.length(); i < len; i++){
            int res = (num.charAt(i) - '0') * m + carry;
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
     * 除法
     * 向下取整
     */
    static StringBuilder division(StringBuilder dividend, StringBuilder divisor){
        // 预计算
        StringBuilder[] stepMulti = new StringBuilder[9];    // x1, x2, x3, x4, x5, x6, x7, x8, x9
        stepMulti[0] = new StringBuilder(divisor);
        for(int i = 2; i < 10; i++){
            stepMulti[i - 1] = simpleMultiply(divisor, i);
        }

        // 补0
        int fl = dividend.length() - divisor.length();
        StringBuilder fill = new StringBuilder(fl);
        for(int i = 0; i < fl; i++){
            fill.append('0');
        }
        for (StringBuilder c : stepMulti) {
            c.insert(0, fill);
        }

        // 计算
        StringBuilder quotient = new StringBuilder();
        int n = dividend.length() - divisor.length();   // 商的位数
        boolean unZero = false;
        for(; n > -1; n--){
            int q = 0;
            StringBuilder trueSub = null;
            for (int i = 8; i >= 0; i--){   // 估商
                StringBuilder subtrahend = stepMulti[i];
                if(q == 0){
                    int r = compare(dividend, subtrahend);
                    if(r >= 0){
                        q = i + 1;
                        trueSub = subtrahend;
                        continue;
                    }
                }
                subtrahend.deleteCharAt(0);
            }

            if(trueSub != null){
                dividend = subtract(dividend, trueSub);
                unZero = unZero || q > 0;
                trueSub.deleteCharAt(0);
            }

            if(unZero) { quotient.append(q); }
        }

        return quotient.reverse();
    }

    /**
     * 除以2
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
     */
    private static boolean isOdd(StringBuilder num){
        return (num.charAt(0) - '0') % 2 == 1;
    }

    /**
     * 求最大公约数
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
                d = simpleMultiply(d, 2);
                a = floorHalf(a);
                b = floorHalf(b);
            }
        }

        if(a == IntegerNumber.ZERO_STR){ return multiply(d, b); }
        else{ return multiply(d, a); }
    }


}
