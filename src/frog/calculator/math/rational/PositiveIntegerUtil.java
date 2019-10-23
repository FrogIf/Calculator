package frog.calculator.math.rational;

import frog.calculator.util.Arrays;

class PositiveIntegerUtil {

    private static final int[] ZERO = new int[1];

    private static final int[] ONE = new int[]{1};

    /**
     * 该对象表示的数的实际进制为SCALE
     * 这里选择每9个十进制位作为一个进制位
     */
    static final int SCALE = 1000000000;

    /**
     * int数组中单个元素中所能存的数字的可读长度
     */
    static final int SINGLE_ELEMENT_LEN;

    static {
        int i = 1;
        int len = 0;
        while(i < SCALE){
            i *= 10;
            len++;
        }
        SINGLE_ELEMENT_LEN = len;

    }

    /**
     * 无符号相加
     */
    static int[] add(int[] left, int[] right) {
        int[] longer;
        int[] shorter;
        if(left.length > right.length){
            longer = left;
            shorter = right;
        }else{
            longer = right;
            shorter = left;
        }

        int m = 0;

        int[] result = new int[longer.length];
        int cursor = 0;

        long carry = 0;
        for(; m < shorter.length - 1; m++){
            long mr = shorter[m] + longer[m] + carry;
            carry = mr / SCALE;
            result[cursor++] = (int) (mr % SCALE);
        }

        if(shorter[m] >= 0){
            long mr = shorter[m] + (longer[m] > 0 ? longer[m] : 0) + carry;
            carry = mr / SCALE;
            result[cursor++] = (int) (mr % SCALE);
            m++;
        }

        for(; m < longer.length - 1; m++){
            long mr = longer[m] + carry;
            carry = mr / SCALE;
            result[cursor++] = (int) (mr % SCALE);
        }

        if(m < longer.length && longer[m] >= 0){
            long mr = longer[m] + carry;
            carry = mr / SCALE;
            result[cursor] = (int) (mr % SCALE);
            m++;
        }

        if(carry > 0){
            int[] newResult = new int[m + 1];   // 显然, 数组长度最多只会增长1
            Arrays.copy(result, newResult, 0, m);
            newResult[m] = (int) (carry % SCALE);
            result = newResult;
        }

        return result;
    }

    static int compare(int[] a, int[] b) {
        if(a == b){ return 0; }

        int ao = a.length - 1;
        int bo = b.length - 1;
        while(a[ao] < 1 && ao > 0){ ao--; }
        while(b[bo] < 1 && bo > 0){ bo--; }
        if(ao == bo){
            // 从高位开始遍历
            for(int i = ao; i > -1; i--){
                if(a[i] != b[i]){
                    return a[i] - b[i];
                }
            }
            return 0;
        }else{
            return ao - bo;
        }
    }

    /**
     * 无符号相减
     * 输入参数需要保证left一定大于right(因为不会返回负数)
     */
    static int[] subtract(int[] left, int[] right) {
        int ll = left.length - 1;
        int rl = right.length - 1;
        while (left[ll] < 1 && ll > 0) { ll--; }
        while (right[rl] < 1 && rl > 0) { rl--; }
        ll++;
        rl++;

        int[] result = new int[ll];
        int borrow = 0; // 借位

        int m = 0;

        int more = 0;   // 记录result数组中有多少位冗余

        int r;

        int stepRes;    // 记录每一步运算的运算结果
        for(; m < rl; m++){
            stepRes = left[m] - right[m] - borrow;
            if(stepRes < 0){
                r = stepRes + SCALE;
                borrow = 1;
            }else{
                r = stepRes;
                borrow = 0;
            }

            more = r == 0 ? (more + 1) : 0;
            result[m] = r;
        }

        for(; m < ll; m++){
            stepRes = left[m] - borrow;
            if(stepRes < 0){
                r = stepRes + SCALE;
                borrow = 1;
            }else{
                borrow = 0;
                r = stepRes;
            }

            more = r == 0 ? (more + 1) : 0;
            result[m] = r;
        }

        if(more > 0){   // 说明result数组中存在冗余
            if(more == result.length){
                result = ZERO;
            }else{
                int[] newResult = new int[result.length - more];
                Arrays.copy(result, newResult, 0, newResult.length);
                result = newResult;
            }
        }
        return result;
    }

    /**
     * 无符号乘法运算
     */
    static int[] multiply(int[] left, int[] right){
        if(left.length == 1 || right.length == 1){
            if(left.length == 1 && left[0] == 1){
                return right;
            }else if(right.length == 1 && right[0] == 1){
                return left;
            }else if(left.length == right.length && (left[0] == 0 || right[0] == 0)){
                return ZERO;
            }
        }

        return ordinaryMultiply(left, right);
    }

    private static int[] ordinaryMultiply(int[] left, int[] right){
        int[] tempResult = new int[]{0};

        int offset = 0;
        for (int rn : right) {
            long carry = 0;
            int[] midResult = new int[left.length + 1 + offset];
            int i = 0;
            for(; i < left.length; i++){
                long res = (long)left[i] * (long)rn + carry;
                carry = res / SCALE;
                midResult[i + offset] = (int) (res % SCALE);
            }
            if(carry > 0){
                midResult[i + offset] = (int) carry;
            }else{
                midResult[i + offset] = -1;
            }
            tempResult = add(tempResult, midResult);
            offset++;
        }

        int redundancy = 0;
        for(int i = tempResult.length - 1; i > 0; i--){
            if(tempResult[i] > 0) { break; }
            redundancy++;
        }

        int[] result = tempResult;
        if(redundancy > 0){
            result = new int[tempResult.length - redundancy];
            Arrays.copy(tempResult, result, 0, result.length);
        }

        return result;
    }

    static int[][] division(int[] dividend, int[] divisor){
        if(divisor.length == 0){
            throw new IllegalArgumentException("divisor is zero.");
        }

        if(dividend.length == 0 || (dividend.length == 1 && dividend[0] == 0)){
            return new int[][]{ZERO, ZERO};
        }

        if(divisor.length == 1) {
            if(divisor[0] == 0){
                throw new IllegalArgumentException("divisor is zero.");
            }
            return divideOneWord(dividend, divisor[0]);
        }

        int cmp = compare(dividend, divisor);
        if(cmp < 0){    // 被除数小于除数
            return new int[][]{ZERO, dividend};
        }else if(cmp == 0){
            return new int[][]{ONE, ZERO};
        }

        return divideKnuth(dividend, divisor);
    }

    /**
     * 返回一个长度为2的二维数组
     * 数组第一个元素是商, 第二个元素时余数
     */
    private static int[][] divideKnuth(int[] dividend, int[] divisor){
        int m = dividend.length - divisor.length;
        int n = divisor.length;
        int[] quotient = new int[m + 1];

        /*
         * 估商法理论基础:
         * 若令 qhat = min(ceil((u0*b-u1)/v1), b - 1), 当 v1 >= ceil(b / 2)时, qhat - 2 <= q <= qhat
         * 因此可以在小于等于2次迭代的情况下, 求出真正的商值
         */

        // D1 规范化
        int d = SCALE / (divisor[divisor.length - 1] + 1);
        int[] u = new int[dividend.length + 1];
        copyAndShift(dividend, d, u);

        int[] v = new int[divisor.length + 1];
        copyAndShift(divisor, d, v);

        for(int j = m; j > -1; j--){ // D2 初始化j
            // D3 计算qhat
            long uH = u[j + n] * (long)SCALE + u[j + n - 1];
            int vH = v[n - 1];
            long qhat = uH / vH;
            long rhat = uH % vH;

            int v2 = v[n - 2];
            int u2 = u[j + n - 2];
            while((qhat == SCALE || qhat * v2 > (SCALE * rhat + u2)) && (rhat < SCALE)){    // assert 这个循环最多执行2次
                qhat--;
                rhat += vH;
            }

            // D4 乘和减
            long borrow = 0;
            for(int i = 0; i < n + 1; i++){
                long a = u[j + i];
                long b = qhat * v[i] + borrow;
                if(a < b){
                    borrow = b / SCALE;
                    a = SCALE * borrow + a;
                    if(a < b){
                        a += SCALE;
                        borrow++;
                    }
                    u[j + i] = (int) (a - b);
                }else{
                    borrow = 0;
                    u[j + i] = (int) (a - b);
                }
            }

            // D6 往回加
            if(borrow > 0){
                qhat--;
                long carry = 0;
                for(int i = 0; i < n + 1; i++){
                    int a = u[j + i];
                    int b = v[i];
                    long sum = carry + a + b;
                    carry = sum / SCALE;
                    u[j + i] = (int) (sum % SCALE);
                }
            }

            quotient[j] = (int) qhat;
        }

        // fix
        int i = quotient.length - 1;
        for(; i > 0; i--){
            if(quotient[i] != 0){
                break;
            }
        }
        if(i < quotient.length - 1){
            int[] nq = new int[i + 1];
            for(; i > -1; i--){
                nq[i] = quotient[i];
            }
            quotient = nq;
        }

        return new int[][]{quotient, divideOneWord(u, d)[0]};
    }

    private static int[][] divideOneWord(int[] dividend, int divisor){
        int[] result;

        int j = dividend.length - 1;
        for(; j > 0; j--){
            if(dividend[j] > 0){
                break;
            }
        }
        int len = j + 1;

        long q = dividend[len - 1] / divisor;
        long rem;
        if(q == 0 && len > 1){
            result = new int[len - 1];
            long d = ((long)dividend[len - 1] * SCALE + dividend[len - 2]);
            result[len - 2] = (int) (d / divisor);
            rem = (int) (d % divisor);
        }else{
            result = new int[len];
            result[len - 1] = (int) q;
            rem = dividend[len - 1] % divisor;
        }

        for(int i = result.length - 2; i > -1; i--){
            long d = dividend[i] + rem * SCALE;
            if(d < divisor){
                d = SCALE + d;
                q = d / divisor;
                rem = d % divisor;
                result[i + 1]--;
            }else{
                q = d / divisor;
                rem = d % divisor;
            }
            result[i] = (int) q;
        }

        return new int[][]{result, new int[]{(int) rem}};
    }

    private static void copyAndShift(int[] values, int d, int[] dest){
        /*
         * 因为d = power(2, shift)
         * 索引 u * d = u << shift
         * 又由于u * d的过程可能产生进位
         * 进位为 u >>> (SCALE_LEAD_ZERO_COUNT - shift)
         */
        long carry = 0;
        int i = 0;
        for(; i < values.length; i++){
            long r = ((long)values[i] * d) + carry;
            dest[i] = (int) (r % SCALE);
            carry = r / SCALE;
        }

        if(carry > 0){
            dest[i] = (int) carry;
        }
    }

    static boolean isOdd(int[] values){
        return values[0] % 2 == 1;
    }

    static int[] gcd(int[] a, int[] b){
        int[] d = ONE;

        while(compare(a, ZERO) != 0 && compare(b, ZERO) != 0){
            boolean aIsOdd = isOdd(a);
            boolean bIsOdd = isOdd(b);
            if(aIsOdd && bIsOdd){
                int mark = compare(a, b);
                if(mark > 0){
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

        if(compare(a, ZERO) == 0){ return multiply(d, b); }
        else{ return multiply(d, a); }
    }

    /**
     * 乘以2
     */
    private static int[] doubleNum(int[] num){
        int l = num.length - 1;
        for(; l > 0; l--){
            if(num[l] > 0){ break; }
        }
        int[] result = new int[l + 2];
        int carry = 0;
        int i = 0;
        for(; i < l + 1; i++){
            int m = num[i] << 1 + carry;
            result[i] = m % SCALE;
            carry = m / SCALE;
        }
        if(carry > 0){
            result[i] = carry;
        }else{
            result[i] = -1;
        }
        return result;
    }

    /**
     * 除以2
     */
    private static int[] floorHalf(int[] num){
        int l = num.length - 1;
        for(; l > 0; l--){
            if(num[l] > 0){ break; }
        }

        if(l == 0){
            return new int[]{ num[0] >> 1 };
        }

        int d = num[l];
        int[] result;
        if(d > 1){
            result = new int[l + 1];
        }else{
            result = new int[l];
            d = (SCALE + num[--l]);
        }
        result[l] = d >> 1;
        int rem = d & 1;

        for(int i = l - 1; i > -1; i--){
            d = rem * SCALE + num[i];
            result[i] = d >> 1;
            rem = d & 1;
        }

        return result;
    }

    static int[] decrease(int[] values) {
        if(values.length == 1 && values[0] == 0){
            throw new IllegalArgumentException("can't decrease.");
        }

        int[] result = new int[values.length];
        int borrow = 1;
        for(int i = 0; i < values.length; i++){
            if(values[i] > borrow){
                result[i] = values[i] - borrow;
                borrow = 0;
            }else{
                result[i] = SCALE - borrow;
                borrow = 1;
            }
        }

        if(result[values.length - 1] < 1){
            int[] newResult = new int[values.length - 1];
            Arrays.copy(result, newResult, 0, newResult.length);
            result = newResult;
        }

        return result;
    }

    static int[] increase(int[] values) {
        int[] result = new int[values.length];
        int carry = 1;
        for(int i = 0; i < result.length; i++){
            int s = result[i] + carry;
            if(s == SCALE){
                carry = 1;
                result[i] = 0;
            }else{
                carry = 0;
                result[i] = s;
            }
        }

        if(carry == 1){
            int len = result.length;
            int[] newResult = new int[len + 1];
            Arrays.copy(result, newResult, 0, len);
            newResult[len] = 1;
            result = newResult;
        }
        return result;
    }
}
