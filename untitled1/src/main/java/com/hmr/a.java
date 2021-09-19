package com.hmr;

import java.util.HashMap;

/**
 * @author HMR
 * @create 2021/9/13 14:58
 */
public class a {
    public static void main(String[] args) {
        HashMap map= new HashMap<>(0);
        map.put("张三1","狂徒1");

//       Thread thread1 = new Thread(){
//           @Override
//           public void run() {
//               for (int i = 0; i <1000 ; i++) {
//                   map.put(i,"张三");
//               }
//           }
//       };
//
//        Thread thread2 = new Thread(){
//            @Override
//            public void run() {
//                for (int i = 0; i <1000 ; i++) {
//                    System.out.println(map.get(i));
//                }
//            }
//        };
//        thread1.start();
//        thread2.start();
    }
}
