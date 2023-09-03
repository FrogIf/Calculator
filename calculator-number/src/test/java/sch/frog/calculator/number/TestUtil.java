package sch.frog.calculator.number;

import java.util.Random;

public class TestUtil {

    private static final Random r = new Random(12);

    private TestUtil(){
        // do nothing
    }

    /**
     * 生成随机字符串
     * @return 随机字符串
     */
    public static String randomString(){
        int len = r.nextInt(20) + 1;
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < len; i++){
            int pos = r.nextInt(52);
            int a = pos / 27 * ('a' - 'A') + 'A' + pos % 26;
            sb.append((char)a);
        }
        return sb.toString();
    }

    /**
     * 生成随机整数
     */
    public static String randomInteger(){
        return (r.nextInt(2) == 0 ? "" : "-") + randomIntegerPositive();
    }

    /**
     * 生成随机正整数
     */
    public static String randomIntegerPositive(){
        StringBuilder sb = new StringBuilder();
        int count = r.nextInt(50) + 1;
        if(count == 1){
            sb.append(String.valueOf(r.nextInt(10)));
        }else{
            sb.append(String.valueOf(r.nextInt(9) + 1));
        }
        for(int i = 1; i < count; i++){
            sb.append(String.valueOf(r.nextInt(10)));
        }

        return sb.toString();
    }

    /**
     * 生成随机有理数
     */
    public static String randomDecimal(){
        StringBuilder sb = new StringBuilder(randomIntegerPositive());

        int afterDot = r.nextInt(50);
        if(afterDot > 0){
            sb.append('.');
            for(int i = 0; i < afterDot; i++){
                sb.append(String.valueOf(r.nextInt(10)));
            }
        }

        return (r.nextInt(2) == 0 ? "" : "-") + sb.toString();
    }
}
