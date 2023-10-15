package io.github.frogif.calculator.number.impl;

import io.github.frogif.calculator.number.exception.DivideByZeroException;
import io.github.frogif.calculator.number.exception.OutOfRangeException;

/**
 * 正整数工具类 所有 正整数的大数操作, 都将在这里完成 包内可见, 主要是这个类并不符合知道最少原则, 暴露出去没有意义,
 * 主要是为IntegerNumber服务
 */
class PositiveIntegerUtil {

    private PositiveIntegerUtil(){
        // hide constructor
    }

    static final int[] ZERO = new int[1];

    static final int[] ONE = new int[]{1};

    /**
     * 该对象表示的数的实际进制为SCALE
     * 这里选择每9个十进制位作为一个进制位
     */
    static final int SCALE = 1000000000;

    /**
     * int数组中单个元素中所能存的数字的可读长度
     * 当前值是: 9
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
     * 获取一个正整数非0首位位置
     * @param arr 待判断的正整数
     * @return 返回非0首位位置, 如果该数是0, 则也会返回0
     */
    static int highPos(int[] arr){
        int l = arr.length - 1;
        while(arr[l] < 1 && l > 0){ l--; }
        return l;
    }

    /**
     * 正整数相加 <br/>
     * 模拟手算加法
     * @param left  左侧数
     * @param lh left数非0高位位置
     * @param right 右侧数
     * @param rh right数非0高位位置
     * @return 返回加和结果, 高位可能存在冗余
     */
    static int[] add(int[] left, int lh, int[] right, int rh) {
        int[] longer;
        int[] shorter;
        int longLen;
        int shortLen;
        if(lh > rh){
            longer = left;
            longLen = lh + 1;
            shorter = right;
            shortLen = rh + 1;
        }else{
            longer = right;
            longLen = rh + 1;
            shorter = left;
            shortLen = lh + 1;
        }

        int m = 0;

        int[] result = new int[longLen + 1];
        int cursor = 0;

        long carry = 0;
        for(; m < shortLen; m++){
            long mr = shorter[m] + longer[m] + carry;
            carry = mr / SCALE;
            result[cursor++] = (int) (mr % SCALE);
        }

        for(; m < longLen; m++){
            long mr = longer[m] + carry;
            carry = mr / SCALE;
            result[cursor++] = (int) (mr % SCALE);
        }

        if(carry > 0){
            result[m] = (int) carry;
        }

        return result;
    }

    /**
     * 一个加数为单字的正整数加法
     * @param left 正整数
     * @param lh left的非0高位位置
     * @param right 正整数
     * @return 和
     */
    static int[] addOneWord(int[] left, int lh, int right){
        if(right < 0){
            throw new IllegalArgumentException("can't support operate for " + right);
        }
        if(right == 0){ return left; }

        int[] result = new int[lh + 2];
        int carry = right;
        for(int i = 0; i < lh + 1; i++){
            int s = left[i] + carry;
            if(s < SCALE){
                carry = 0;
                result[i] = s;
            }else{
                result[i] = s - SCALE;
                carry = s / SCALE;
            }
        }

        if(carry == 1){
            result[result.length - 1] = 1;
        }
        return result;
    }

    /**
     * 比较两个正整数的大小
     * @param a 待比较数a
     * @param highA 待比较数a的非0高位的位置
     * @param b 待比较数b
     * @param highB 待比较数b的非0高位的位置
     * @return 比较结果, 等于0, 则两数相等; 小于0则, a &lt; b; 大于0, 则a &gt; b
     */
    static int compare(int[] a, int highA, int[] b, int highB){
        if(a == b){ return 0; }

        if(highA == highB){
            // 从高位开始遍历
            for(int i = highA; i > -1; i--){
                if(a[i] != b[i]){
                    return a[i] - b[i];
                }
            }
            return 0;
        }else{
            return highA - highB;
        }
    }

    /**
     * 正整数相减 <br/>
     * 输入参数需要保证minuend一定大于subtrahend(因为不会返回负数)
     * @param minuend 被减数
     * @param hm 被减数的非0高位位置
     * @param subtrahend 减数
     * @param hs 减数非0高位位置
     * @return 差, 高位可能存在冗余
     */
    static int[] subtract(int[] minuend, int hm, int[] subtrahend, int hs) {
        int mLen = hm + 1;
        int sLen = hs + 1;

        int[] result = new int[mLen];
        int borrow = 0; // 借位

        int m = 0;

        int more = 0;   // 记录result数组中有多少位冗余

        int r;

        int stepRes;    // 记录每一步运算的运算结果
        for(; m < sLen; m++){
            stepRes = minuend[m] - subtrahend[m] - borrow;
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

        for(; m < mLen; m++){
            stepRes = minuend[m] - borrow;
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

        if(more == result.length){
            result = ZERO;
        }

        return result;
    }

    /**
     * 减数为单字的正整数减法
     * @param minuend 被减数
     * @param h minuend非0高位位置
     * @param subtrahend 减数
     * @return 差
     */
    static int[] subtractOneWord(int[] minuend, int h, int subtrahend){
        if(subtrahend < 0){
            throw new IllegalArgumentException("can't support operate for " + subtrahend);
        }
        if(subtrahend == 0){ return minuend; }

        if(h == 0 && minuend[0] < subtrahend){
            throw new IllegalArgumentException("can't subtract.");
        }

        int[] result = new int[h + 1];
        int borrow = subtrahend;
        for(int i = 0; i < result.length; i++){
            if(minuend[i] < borrow){
                result[i] = SCALE + minuend[i] - borrow;
                borrow = 1;
            }else{
                result[i] = minuend[i] - borrow;
                borrow = 0;
            }
        }

        return result;
    }

    /**
     * 正整数乘法
     * @param left 左侧因数
     * @param lh left的非0高位位置
     * @param right 右侧因数
     * @param rh right的非零高位位置
     * @return 积, 高位可能存在冗余
     */
    static int[] multiply(int[] left, int lh, int[] right, int rh){
        if(lh == 0 || rh == 0){
            if(lh == 0 && left[0] == 1){
                return right;
            }else if(rh == 0 && right[0] == 1){
                return left;
            }else if(lh == rh && (left[0] == 0 || right[0] == 0)){
                return ZERO;
            }
        }

        return ordinaryMultiply(left, lh, right, rh);
    }

    /**
     * 平凡正数乘法
     * @param left 左侧因数
     * @param lh left的非0高位位置
     * @param right 右侧因数
     * @param rh right的非零高位位置
     * @return 积, 高位可能存在冗余
     */
    private static int[] ordinaryMultiply(int[] left, int lh, int[] right, int rh){
        int[] result = new int[]{0};

        int offset = 0;
        for(int j = 0; j < rh + 1; j++){
            int rn = right[j];
            long carry = 0;
            int[] midResult = new int[lh + 2 + offset];
            int i = 0;
            for(; i < lh + 1; i++){
                long res = (long)left[i] * (long)rn + carry;
                carry = res / SCALE;
                midResult[i + offset] = (int) (res % SCALE);
            }
            int midHigh;
            if(carry > 0){
                midResult[i + offset] = (int) carry;
                midHigh = midResult.length - 1;
            }else{
                midResult[i + offset] = -1;
                midHigh = midResult.length - 2;
            }
            result = add(result, highPos(result), midResult, midHigh);
            offset++;
        }

        return result;
    }

    /**
     * 正整数除法
     * @param dividend 被除数
     * @param deh dividend非0高位位置
     * @param divisor 除数
     * @param drh divisor非0高位位置
     * @return 商和余数, 数组的第一个元素是商, 第二个元素是余数
     */
    static int[][] divide(int[] dividend, int deh, int[] divisor, int drh){
        if(drh == 0) {
            if(divisor[0] == 0){
                throw new DivideByZeroException();
            }
            return divideOneWord(dividend, deh, divisor[0]);
        }

        int cmp = compare(dividend, deh, divisor, drh);
        if(cmp < 0){    // 被除数小于除数
            return new int[][]{ZERO, dividend};
        }else if(cmp == 0){
            return new int[][]{ONE, ZERO};
        }

        return divideKnuth(dividend, deh, divisor, drh);
    }

    /**
     * Knuth D正整数除法(估商法)
     * @param dividend  被除数
     * @param deh dividend非0高位位置
     * @param divisor 除数
     * @param drh divisor非0高位位置
     * @return 商和余数, 数组的第一个元素是商, 第二个元素是余数
     */
    private static int[][] divideKnuth(int[] dividend, int deh, int[] divisor, int drh){
        int m = deh - drh;
        int n = drh + 1;
        int[] quotient = new int[m + 1];

        /*
         * 估商法理论基础:
         * 若令 qhat = min(ceil((u0*b-u1)/v1), b - 1), 当 v1 >= ceil(b / 2)时, qhat - 2 <= q <= qhat
         * 因此可以在小于等于2次迭代的情况下, 求出真正的商值
         */

        // D1 规范化
        int d = SCALE / (divisor[drh] + 1);
        int[] u = new int[deh + 2];
        copyAndShift(dividend, deh, d, u);

        int[] v = new int[drh + 2];
        copyAndShift(divisor, drh, d, v);

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
                }else{
                    borrow = 0;
                }
                u[j + i] = (int) (a - b);
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

        // 去除高位0
        int i = highPos(quotient);
        if(i < quotient.length - 1){
            int[] nq = new int[i + 1];
            for(; i > -1; i--){
                nq[i] = quotient[i];
            }
            quotient = nq;
        }

        return new int[][]{quotient, divideOneWord(u, highPos(u), d)[0]};
    }

    /**
     * Knuth D算法 整数规范化
     * @param arr 待规范化整数
     * @param h 非0高位位置
     * @param d 规范化因数d
     * @param dest 目标整数容器
     */
    private static void copyAndShift(int[] arr, int h, int d, int[] dest){
        /*
         * 因为d = power(2, shift)
         * 索引 u * d = u << shift
         * 又由于u * d的过程可能产生进位
         * 进位为 u >>> (SCALE_LEAD_ZERO_COUNT - shift)
         */
        long carry = 0;
        int i = 0;
        for(; i < h + 1; i++){
            long r = ((long)arr[i] * d) + carry;
            dest[i] = (int) (r % SCALE);
            carry = r / SCALE;
        }

        if(carry > 0){
            dest[i] = (int) carry;
        }
    }

    /**
     * 除数为单字的正整数除法
     * @param dividend 被除数
     * @param dh dividend非0高位位置
     * @param divisor 除数
     * @return 商和余数, 数组的第一个元素是商, 第二个元素是余数
     */
    private static int[][] divideOneWord(int[] dividend, int dh, int divisor){
        int[] result;
        int len = dh + 1;

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
            if(d < divisor && d != 0){
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

    /**
     * 判断一个元素是否为奇数
     * @param values 待判断整数
     * @return true : 奇数; false : 偶数
     */
    static boolean isEven(int[] values){
        return (values[0] & 1) == 0;
    }

    /**
     * 获取两个正整数的最大公约数
     * 1. a, b都是偶数, 则gcd(a, b) = 2 * gcd(a/2, b/2)
     * 2. a是偶数, b是奇数, 则gcd(a, b) = gcd(a/2, b)
     * 3. a是奇数, b是偶数, 则gcd(a, b) = gcd(a, b/2)
     * 4. 如果a, b都是奇数, 则a - b是偶数, gcd(a, b) = gcd(|a - b|, min(a, b))
     * @param a 数a
     * @param ah a的非0高位位置
     * @param b 数b
     * @param bh b的非0高位位置
     * @return 最大公约数, 如果入参中有一个为0, 则返回另一个
     */
    static int[] gcd(int[] a, int ah, int[] b, int bh){
        if(ah == 0 && a[0] == 0){ return b; }
        if(bh == 0 && b[0] == 0){ return a; }

        int[] k = ONE;
        while(isEven(a) && isEven(b)){
            k = twice(k, highPos(k));
            a = half(a, highPos(a));
            b = half(b, highPos(b));
        }

        while(true){
            while(isEven(a)){
                a = half(a, highPos(a));
            }
            while(isEven(b)){
                b = half(b, highPos(b));
            }

            int mark = compare(a, (ah = highPos(a)), b, (bh = highPos(b)));
            if(mark == 0){
                break;
            }else if(mark > 0){   // a > b
                a = subtract(a, ah, b, bh);
            }else{ 
                b = subtract(b, bh, a, ah);
            }
        }
        
        return multiply(k, highPos(k), b, highPos(b));
    }

    /**
     * 一个数乘以2
     * @param num 待乘以2的数
     * @param h num非0高位位置
     * @return 乘以2之后的结果
     */
    private static int[] twice(int[] num, int h){
        int[] result = new int[h + 2];
        int carry = 0;
        int i = 0;
        for(; i < h + 1; i++){
            int m = (num[i] << 1) + carry;
            result[i] = m % SCALE;
            carry = m / SCALE;
        }
        if(carry > 0){
            result[i] = carry;
        }
        return result;
    }

    /**
     * 除以2
     * @param num 待除以2的数
     * @param h num非0高位位置
     * @return 除以2之后的结果
     */
    private static int[] half(int[] num, int h){
        if(h == 0){
            return new int[]{ num[0] >> 1 };
        }

        int d = num[h];
        int[] result;
        if(d > 1){
            result = new int[h + 1];
        }else{
            result = new int[h];
            d = (SCALE + num[--h]);
        }
        result[h] = d >> 1;
        int rem = d & 1;

        for(int i = h - 1; i > -1; i--){
            d = rem * SCALE + num[i];
            result[i] = d >> 1;
            rem = d & 1;
        }

        return result;
    }

    /**
     * 十进制左移
     * @param num 待左移的数
     * @param highPos num非0高位位置
     * @param bcount 左移位数
     * @return 左移的结果
     */
    public static int[] decLeftShift(int[] num, int highPos, int bcount){
        int len = highPos + 1 + bcount / SINGLE_ELEMENT_LEN;
        int r = bcount % SINGLE_ELEMENT_LEN;
        int[] res;
        if(r > 0){
            len += 1;
            res = new int[len];

            int f = 1;
            while(r > 0){
                f *= 10;
                r--;
            }
            int e = SCALE / f;

            int up = 0;
            int j = len - 1;
            for(int i = highPos; i >= 0; i--, j--){
                res[j] = num[i] / e + up * f;
                up = num[i] % e;
            }
            if(up > 0){
                res[j] = up * f;
            }
        }else{
            res = new int[len];
            int j = len - 1;
            for(int i = highPos; i >= 0; i--, j--){
                res[j] = num[i];
            }
        }

        return res;
    }

    /**
     * 十进制左移
     * @param num 待左移的数
     * @param highPos num非0高位位置
     * @param bcount 左移位数
     * @param bHighPos 左移位数高位
     * @return 左移的结果
     */
    public static int[] decLeftShift(int[] num, int highPos, int[] bcount, int bHighPos){
        if(bHighPos == 0){
            return decLeftShift(num, highPos, bcount[0]);
        }else{
            int[][] divideResult = divideOneWord(bcount, bHighPos, 9);
            int[] q = divideResult[0];
            int[] r = divideResult[1];  // 显然r < SCALE
            int rVal = r[0];
            long qInt = 0;
            if(q.length > 2){   // 超出数组最大容量
                throw new OutOfRangeException();
            }else if(q.length == 2){
                long l = q[1] * 1L * SCALE + q[0];
                if(l > Integer.MAX_VALUE){
                    throw new OutOfRangeException();
                }
                qInt = (int)l;
            }else{
                qInt = q[0];
            }

            num = decLeftShift(num, highPos, rVal);
            highPos = highPos(num);
            long len = qInt + highPos + 1;
            if(len > Integer.MAX_VALUE){
                throw new OutOfRangeException();
            }

            int[] result = new int[(int)len];
            for(int j = highPos, i = (int)len - 1; j >= 0; i--, j--){
                result[i] = num[j];
            }
            
            return result;
        }
    }

    /**
     * 将正整数转为数组形式
     * @param absNum 正整数
     * @return 数值数组
     */
    public static int[] convertToNumberArray(long absNum){
        int[] result;
        if(absNum < PositiveIntegerUtil.SCALE){
            result = new int[1];
            result[0] = (int)absNum;
        }else{
            result = new int[2];
            result[0] = (int)(absNum % PositiveIntegerUtil.SCALE);
            result[1] = (int)(absNum / PositiveIntegerUtil.SCALE);
        }
        return result;
    }

}
