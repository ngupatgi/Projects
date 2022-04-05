package com.LivePC;


import java.util.*;
import java.io.*;
import android.graphics.BitmapFactory;





public class screenshot{


    public byte [] whole=null;


    screenshot(byte [] optimized,byte [] previous)
    {
        String s1,s2;
        s1="<pr>";
        s2="</pr>";
        String start=new String(connection.midbytes(s1.getBytes(),s2.getBytes(),optimized));
        s1="<pst>";
        s2="</pst>";
        String stop=new String(connection.midbytes(s1.getBytes(),s2.getBytes(),optimized));
        s1="</pr>";
        s2="<pst>";
        byte [] mybytes=connection.midbytes(s1.getBytes(),s2.getBytes(),optimized);

        byte [] tmp=connection.concatbyte(connection.subbytes(0,Integer.valueOf(start),previous),mybytes);

        tmp=connection.concatbyte(tmp,connection.subbytes(Integer.valueOf(stop),previous.length,previous));

        if(connection.findbyte(connection.start_s.getBytes(),tmp)>-1||connection.findbyte(connection.stop_s.getBytes(),tmp)>-1)
            MainActivity.disp("Gotten Them ");


        whole=tmp;


    }



screenshot(byte [] alls)
{
    String b4="<ss>";
    String a4="</ss>";
whole=connection.midbytes(b4.getBytes(),a4.getBytes(),alls);
}







}
