package frog.calculator.math.rational;

class PositiveIntegerUtilArr {

    private static final int[] ZERO = new int[1];

    private static final int[] ONE = new int[]{1};

    /**
     * 该对象表示的数的实际进制为SCALE
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
//            int[] newResult = new int[result.length + 1];   // 显然, 数组长度最多只会增长1
            for(int i = 0; i < m; i++){
                newResult[i] = result[i];
            }
            newResult[m] = (int) (carry % SCALE);
            result = newResult;
        }

        return result;
    }

    static int compare(int[] a, int[] b) {
        if(a.length == b.length){
            // 从高位开始遍历
            for(int i = a.length - 1; i > -1; i--){
                if(a[i] != b[i]){
                    return a[i] - b[i];
                }
            }
            return 0;
        }else{
            return a.length - b.length;
        }
    }

    /**
     * 无符号相减
     * 输入参数需要保证left一定大于right(因为不会返回负数)
     */
    static int[] subtract(int[] left, int[] right) {
        int[] result = new int[left.length];
        int borrow = 0; // 借位

        int m = 0;

        int more = 0;   // 记录result数组中有多少位冗余

        int r;

        int stepRes;    // 记录每一步运算的运算结果
        for(; m < right.length; m++){
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

        for(; m < left.length; m++){
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
                for(int i = 0; i < newResult.length; i++){
                    newResult[i] = result[i];
                }
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
            tempResult = add(tempResult, simpleMultiply(left, rn, offset++));
        }

        int redundancy = 0;
        for(int i = tempResult.length - 1; i > 0; i--){
            if(tempResult[i] > 0) { break; }
            redundancy++;
        }

        int[] result = tempResult;
        if(redundancy > 0){
            result = new int[tempResult.length - redundancy];
            for(int i = 0; i < result.length; i++){
                result[i] = tempResult[i];
            }
        }

        return result;
    }

    private static int[] simpleMultiply(int[] values, int num, int offset){
        long carry = 0;
        int[] result = new int[values.length + 1 + offset];
        int i = 0;
        for(; i < values.length; i++){
            long res = (long)values[i] * (long)num + carry;
            carry = res / SCALE;
            result[i + offset] = (int) (res % SCALE);
        }
        if(carry > 0){
            result[i + offset] = (int) carry;
        }else{
            result[i + offset] = -1;
        }
        return result;
    }

    private static final int TRY_QUOTIENT_START = SCALE / 5;

    static int[] division(int[] left, int[] right){
        if(left.length < right.length){ return ZERO; }

        int n = left.length - right.length; // 商首位预估位置
        int[] tempResult = new int[n + 1];
        for(; n > -1; n--){

            int[] dividend = left;

            int l = dividend.length - 1;
            int r = right.length - 1;
            int divisorHead = right[r--];

            long dividendNum = dividend[l];
            long quotient = dividendNum / divisorHead;
            if(quotient == 0){
                dividendNum = dividendNum * SCALE + dividend[--l];
                quotient = dividendNum / divisorHead;
            }

            // 验证这个值是否可以
            long remainder = dividendNum % divisorHead;
            for(l--; l > -1; l--, r--){
                if((remainder * SCALE + left[l]) < right[r]){
                    // 说明该商数不对
                    break;
                }
            }



        }


        if(tempResult == null || tempResult.length == 0){
            return ZERO;
        }else{
            // 需要翻转数组
            return null;
        }
    }

    static int[] simpleDivision(int[] dividend, int divisor){

        return null;
    }

    static boolean isOdd(int[] values){
        return values[0] % 2 == 1;
    }
}
