package sch.frog.test.util;

import java.util.Random;

/**
 * 调试工具类
 */
public class DebugUtil {

    private DebugUtil(){
        // do nothing
    }

    /**
     * 生成随机字符串
     * @return 随机字符串
     */
    public static String randomString(){
        Random r = new Random(12);
        int len = r.nextInt(20) + 1;
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < len; i++){
            int pos = r.nextInt(52);
            int a = pos / 27 * ('a' - 'A') + 'A' + pos % 26;
            sb.append((char)a);
        }
        return sb.toString();
    }
    
}
