/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jms.chat.privpub;
/**
 *
 * @author Raj Kumar Rb
 */
public class RandomTest {
     public static void main(String[] args) {
         String[] name=new String[]{"RajKumar","Ramesh","Shiva","Sita","Rubina","Parvati","Dharmesh","Nara","Jackie","Barak"};
         for(int i=0;i<10;i++){
            double dno =Math.random()*10;
            System.out.println("dno==>"+dno);
            System.out.println("Math.floor()==>"+Math.floor(dno));
            System.out.println("Math.ceil()==>"+Math.ceil(dno));
            System.out.println("Math.round()==>"+Math.round(dno));
         }        
//         int no=(int)Math.floor();
//         System.out.println(no);
//         System.out.println(name[no]);
     }
}