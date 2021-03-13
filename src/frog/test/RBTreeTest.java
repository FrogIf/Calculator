package frog.test;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import frog.calculator.util.IComparator;
import frog.calculator.util.collection.AVLTreeSet;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.ISet;
import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.RBTreeSet;

public class RBTreeTest {

    public static void main(String[] args){
        for(int i = 0; i < 1000000; i++){
            if(!repeatCheck()){
                break;
            }
        }

        // String errorStr = "fthrTnyVuZJZT,etAIfy,r,JBvDViqgESoAph,JibAgUmgyjWcl,pWdNYmOpnHEYu,DhmnYjArImypQbUjZV,ZLyUWjkN,QBjRXhwseZhKzm,OYpvuhUemjrQo,LYqeVCdDs,zdTDzpQA,TjpPYmXRiHKAA,gASBgCfcidiWfxoTLI,IeNovImlA,tAKDofZ,TOmybRsmcRnnoFZChw,lxpxEYjjMAEgdO,AJweYvwX,jbdhEVKDidYiUO,sFooEVlb,cZUvhCdhfUhxd,QxFVHjlEj,IkDxxOZEhtRfkVuWeY,HsQttRMfJuA,ccmtUsHSciYAILnwYs,bzSEQAjDABfsgAQtFjJ,QjrIgAGv,sVSBMkMTNV,BR,s,cDOgsTp,SAeLfc,fKgAiU,tiKyyyjelQvXUoRmhV,uxk,AhLPTFQWCOEDmCUE,P,jOCDOUsyd,oKbAeZfhAcpXW,s,RFcMDHAeL,lkz,pYIX,AzdkXXZl,JSdju,AxksRLl,MXeuAwAjLoPrCoN,Akxow,EEZSxrPTYHpvi,LQ,uASshWRhxhMwgHsMJ,xIGXTDiAdwzJeVYc,ctSn,FIyRrMeQXyFIeRbjuFso,mATuIx,FTPomqTkoTi,RXDMKuhkSG,vGiegbMnDQYi,ADDlEYhmrYb,nn,cBUoAUPTwUWfACxB,ZwSeyd,scrsQXem,YzMmyQzGSfCXLDdgxAzV,OTb,r,fEZqLdAIdtIMnW,hEgVeXYdGedQFmxJs,unjzR,kMdfWO,I,QrYE,OShAMZZpZWARZfxbeXJi,cUkoWoFTA,EPn,etVPXbWCMBFJ,rALeErXDBiBCJq,CUYNAAdlyRfFMo,kLUiolrszhnudh,uiB,HssGWGgdrSQ,OHssZAMkyWtmvbJHAA,v,dXteULhkGIDekfmhTkMH,VDUWZmXNkZKG,lcBKRUPVnGNDOkBc,VfWzgCEOhUCtwjmQeEFH,PBLBlxTiLXjhyJgXuc,r,uzfYNAlXmeG,EAZnLC,cAdTYDRWZGSCQA,mLAA,j,hZlDRtLzuuVo,BrSNqwrjYo,OVAAZDcejWP,OAeSeqhWsUVbvmgy,lAplGHtUAPgRXhJXdcn";
        // String errorStr = "fthrTnyVuZJZT,etAIfy,r,JBvDViqgESoAph,JibAgUmgyjWcl,pWdNYmOpnHEYu,DhmnYjArImypQbUjZV,ZLyUWjkN,QBjRXhwseZhKzm,LYqeVCdDs,zdTDzpQA,TjpPYmXRiHKAA,gASBgCfcidiWfxoTLI,IeNovImlA,tAKDofZ,TOmybRsmcRnnoFZChw,lxpxEYjjMAEgdO,jbdhEVKDidYiUO,ctSn,r";
        // ArrayList<String> origin = new ArrayList<>(errorStr.split(","));
        // IList<Integer> numList = new ArrayList<>();
        // Iterator<String> iterator = origin.iterator();
        // while(iterator.hasNext()){
        //     numList.add(iterator.next().hashCode());
        // }
        // System.out.println(numList.get(numList.size() - 1));
        // checkInteger(numList);
    }

    private static boolean repeatCheck(){
        IList<String> list = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            list.add(randomString());
        }

        return checkString(list);
    }


    private static boolean checkInteger(IList<Integer> list){
        ISet<Integer> rbSet = new RBTreeSet<>(new IComparator<Integer>(){
            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });
        
        ISet<Integer> avlSet = new AVLTreeSet(new IComparator<Integer>(){
            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            } 
        });

        Set<Integer> standardSet = new TreeSet<>(new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            } 
        });
        
        // 重复校验测试
        Iterator<Integer> itr = list.iterator();
        while(itr.hasNext()){
            Integer str = itr.next();
            // if("r".equals(str)){
            //     System.out.println("---");
            // }
            if(114 == str){
                System.out.println("----");
            }
            boolean rbAdd = rbSet.add(str);
            // boolean avlAdd = avlSet.add(str);
            boolean ansAdd = standardSet.add(str);
            if(rbAdd != ansAdd){
                System.out.println("----error----");
                return false;
            }
        }
        return true;
    }

    private static boolean checkString(IList<String> list){
        ISet<String> rbSet = new RBTreeSet<>(new IComparator<String>(){
            @Override
            public int compare(String a, String b) {
                return a.hashCode() - b.hashCode();
            }
        });
        
        ISet<String> avlSet = new AVLTreeSet(new IComparator<String>(){
            @Override
            public int compare(String a, String b) {
                return a.hashCode() - b.hashCode();
            } 
        });

        Set<String> standardSet = new TreeSet<>(new Comparator<String>(){
            @Override
            public int compare(String o1, String o2) {
                return o1.hashCode() - o2.hashCode();
            } 
        });
        
        // 重复校验测试
        Iterator<String> itr = list.iterator();
        int i = 0;
        while(itr.hasNext()){
            String str = itr.next();
            boolean rbAdd = rbSet.add(str);
            // boolean avlAdd = avlSet.add(str);
            boolean ansAdd = standardSet.add(str);
            if(rbAdd != ansAdd){
                System.out.println("----error----");
                Iterator<String> errItr = list.iterator();
                StringBuilder sb = new StringBuilder();
                while(errItr.hasNext()){
                    sb.append(errItr.next()).append(',');
                }
                System.out.println("origin list : [" + sb.toString() + "]");
                System.out.println("error str : [" + str + "], pos : " + i);
                // if(avlAdd == ansAdd){
                //     System.out.println("avl is right");
                // }else{
                //     System.out.println("rb is right");
                // }
                return false;
            }
            i++;
        }
        return true;
    }

    /**
     * 生成随机字符串
     * @return
     */
    private static String randomString(){
        Random r = new Random();
        int len = r.nextInt(20) + 1;
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < len; i++){
            int pos = r.nextInt(52);
            int a = pos / 27 * ('a' - 'A') + 'A' + pos % 26;
            // if(a < 'A' || (a > 'Z' && a < 'a') || a > 'Z'){
            //     System.out.println("------------");
            //     System.out.println("a : " + a + ", pos : "+ pos + "");
            //     System.out.println("-----------");
            // }
            // System.out.println((pos / 27 * ('a' - 'A') + 'A' + pos % 26));
            sb.append((char)a);
        }
        return sb.toString();
    }

    // private static String randomString(){
    //     Random r = new Random();
    //     int len = r.nextInt(20) + 1;
    //     StringBuilder sb = new StringBuilder();

    //     for(int i = 0; i < len; i++){
    //         int pos = r.nextInt(52);
    //         sb.append((char)(pos / 27 * ('a' - 'A') + 'a' + pos % 26));
    //     }
    //     return sb.toString();
    // }
    
}
