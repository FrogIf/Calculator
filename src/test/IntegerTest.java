package test;

import java.util.Random;

public class IntegerTest {

    public static void main(String[] args){
    }

    public static String randomBigNumber(){
        Random r = new Random();

        int pos = (int) (r.nextDouble() * 30) + 1;

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < pos; i++){
            sb.append(r.nextInt(10));
        }
        return sb.toString();
    }

    public static int gcd(int a, int b){
        if(a == 0){ return b; }
        if(b == 0){ return a; }
        if(a % 2 == 0 && b % 2 == 0){
            return 2 * gcd(a >> 1, b >> 1);
        }else if(a % 2 == 0){
            return gcd(a >> 1, b);
        }else if(b % 2 == 0){
            return gcd(a, b >> 1);
        }else{
            return gcd(Math.abs(a - b), Math.min(a, b));
        }
    }



}
