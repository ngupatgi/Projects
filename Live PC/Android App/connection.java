package com.LivePC;

// The main connection class
/*
Contain

enum connection mode internet/lan

enum connection role client/server

security key

port

connection code

create connection

check alive

send file

send data

receive data

receive file

encrypt

decrypt

bytes
translate



formats
file sending









*/


import android.net.wifi.p2p.WifiP2pManager;
import android.os.Environment;

import java.util.*;
import java.io.*;
import java.net.*;
import java.lang.*;

//import java.awt.event.ActionListener;
//import java.awt.event.ActionEvent;



import java.nio.charset.StandardCharsets;



public class connection{


    public String log="";

    public mode connemode=mode.UNKNOWN;

    public role myrole=role.UNSET;

    public String securitykey=null;

    public int port=2310;

    public int file_sharing_port=2311;

    public String urladdress="";

    public byte [] receive;

    public Socket mysocket=null;

    public boolean power=false;

    public HttpURLConnection netconnection;

    public String connection_id;

    public String username="";

    public String client=null;

    public int receive_frequency=6000;

    public int screenshare_frequency=100;

    public long last_receive_time=0;

    public boolean loadss=false;

    public static String start_s="<mwanzo>";
    public static String stop_s="</mwisho>";
    public byte [] receiveBuffer=null;

    public byte [] outputBuffer=null;


    public screenshot ssprev=null;
    public screenshot sscurrect=null;



    //public static java.awt.event.ActionListener Onconnect=null;
//public static ActionListener onAuth=null;



    public byte[] encrypt(byte [] raw)
    {
        encryption e=new encryption(securitykey);
        return e.encrypt(raw);
    }





    public byte[] decrypt(byte [] raw)
    {
        encryption e=new encryption(securitykey);
        return e.decrypt(raw);
    }



    public boolean connect()
    {
        if(connemode==mode.TCP)
        {
            if(myrole==role.HOST)
            {

            }
            else if(myrole==role.CLIENT)
            {
                try
                {
                    mysocket=new Socket(urladdress,port);
                    power=true;
                    return true;
                }catch(Exception e)
                {
                    System.out.println(e);
                    log+=e.toString();

                }

            }
            else
            {
                System.out.println("failed");
                return false;
            }
        }
        else if(connemode==mode.INTERNET)
        {

            power=true;

            return true;

        }
        else
        {
            return false;
        }
        return false;
    }



    public static byte [] midbytes(byte [] start,byte [] end,byte [] full)
    {
        return subbytes(findbyte(start,full)+start.length,findbyte(end,full),full);
    }






















    public synchronized void send_data(byte[] data_to_send)
    {
        if(connemode==mode.INTERNET)
        {

            if(outputBuffer==null)
                outputBuffer=bordered(encrypt(data_to_send));
            else
                outputBuffer=concatbyte(outputBuffer,bordered(encrypt(data_to_send)));

        }
        else if(connemode==mode.TCP)
        {
            try
            {
                OutputStream out=mysocket.getOutputStream();
                out.write(bordered(encrypt(data_to_send)));


            }catch(Exception hggd)
            {
                //System.out.println(hggd);
                log+=(" "+hggd);

                       
            }
        }
    }






    public void send_buffered()
    {
        Thread dd=new Thread(){
            public void run()
            {
                while(power)
                {
                    if(outputBuffer!=null&&outputBuffer.length>0)
                    {
                        try
                        {
                            URL url=new URL(urladdress+"fileupload.php?direction="+(myrole==role.HOST?"away":"home"));
                            netconnection=(HttpURLConnection) url.openConnection();
                            netconnection.setDoInput(true);
                            netconnection.setDoOutput(true);
                            netconnection.setReadTimeout(10000);
                            netconnection.setConnectTimeout(15000);


                            netconnection.setRequestMethod( "POST" );

                            String boundary="==="+System.currentTimeMillis()+"===";
                            netconnection.setRequestProperty( "Content-Type", "multipart/form-data;boundary="+boundary);
                            //netconnection.setRequestProperty( "Content-Length",(outputBuffer.length+950)+"");
                            netconnection.setUseCaches(false);
                            netconnection.connect();
//byte[] postdata=(new String("username="+username+"pats")).getBytes(StandardCharsets.UTF_8);
                            byte[] predata=null;
                            OutputStream out=netconnection.getOutputStream();
//OutputStream out=new java.io.FileOutputStream("sent.txt");

                            String prep;
/*
prep="--"+boundary+"\nContent-Disposition: form-data; name=\"connection_id\"\n";
prep+="Content-Type: text/plain;\n\n"+connection_id;

predata=prep.getBytes();
out.write(predata);
*/

                            prep="\n--"+boundary+"\nContent-Disposition: form-data; name=\"MAX_FILE_SIZE\"\n\n";
                            prep+=1000000;

//System.out.println(prep);

                            predata=prep.getBytes();
                            out.write(predata);




                            prep="\n--"+boundary+"\nContent-Disposition: form-data; name=\"connection_id\"\n";
                            prep+="Content-Type: text/plain;\n\n"+connection_id;

//System.out.println(prep);

                            predata=prep.getBytes();
                            out.write(predata);





                            prep="\n--"+boundary+"\nContent-Disposition: form-data; name=\"userdata\"; filename=\"contents\"\n";
                            prep+="Content-Type: application/x-object\n\n";
//prep+="Content-Transfer-Encoding: binary; \n";

                            predata=prep.getBytes();
                            out.write(predata);
//System.out.println(prep);

                            out.write(outputBuffer);

                            out.write(("\n--"+boundary).getBytes());

                            out.close();





                            String bring="";
                            try
                            {
                                InputStream in=netconnection.getInputStream();
                                byte [] tot=new byte[0];
                                byte [] all=new byte[in.available()];
                                while(in.read(all)!=-1)
                                {
                                    tot=concatbyte(tot,all);
                                    all=new byte[in.available()];
                                }
                                bring=new String(tot);
                            }catch (Exception e)
                            {

                            }

                            netconnection.getInputStream().close();

                            if(bring.contains("success"))
                            {
                                String size=bring.split("size:")[1];
                                outputBuffer=subbytes(Integer.valueOf(size),outputBuffer.length,outputBuffer);
                                //System.out.println("uploaded bytes\t:\t"+size+";");
                                //MainActivity.disp("uploaded bytes\t:\t"+size+";");
                            }


                            //System.out.println(bring);









                        }catch(Exception e)
                        {
                            System.out.print("network error sending"+e);
                            MainActivity.disp("network error sending"+e);

                        }
                    }


                }


            }};
        dd.start();

    }





    public byte [] bordered(byte [] raw)
    {
        return concatbyte(concatbyte(start_s.getBytes(),raw),stop_s.getBytes());
    }

    public static int findbyte(byte [] criteria,byte [] full)
    {
    int ona=-1;
    try
    {
        for(int i=0;i<full.length;i++)
            for(int j=0;j<criteria.length&&(i+j)<full.length&&full[i+j]==criteria[j];j++)
                if(j==criteria.length-1)
                    return i;
    }catch(Exception e)
    {
        MainActivity.disp(e+"find");
        //JOptionPane.showMessageDialog(null,e+"find");
    }

            

    return ona;
    }

    public static byte [] concatbyte(byte [] before,byte [] after)
    {
        //System.out.println("concat return "+(before.length+after.length)+";");
        byte [] newer=new byte[before.length+after.length];


        for(int i=0;i<before.length;i++)
            newer[i]=before[i];

        for(int j=before.length;j<newer.length;j++)
            newer[j]=after[j-before.length];


        return newer;
    }



public static Boolean reclock=false;
    public synchronized void processbyte(byte [] raw)
    {

            if (receiveBuffer == null) {
                receiveBuffer = raw;
            } else {
                receiveBuffer = concatbyte(receiveBuffer, raw);
            }

            while (findbyte(start_s.getBytes(), receiveBuffer) != -1 && findbyte(stop_s.getBytes(), receiveBuffer) != -1) {

                translate(subbytes(findbyte(start_s.getBytes(), receiveBuffer) + start_s.length(), findbyte(stop_s.getBytes(), receiveBuffer), receiveBuffer));
                int tmp_len=receiveBuffer.length;

                byte [] tmp = subbytes(findbyte(stop_s.getBytes(), receiveBuffer) + stop_s.length(), receiveBuffer.length, receiveBuffer);

                while(receiveBuffer.length!=tmp_len)
                {
                    tmp_len=receiveBuffer.length;
                    tmp = subbytes(findbyte(stop_s.getBytes(), receiveBuffer) + stop_s.length(), receiveBuffer.length, receiveBuffer);
                    MainActivity.disp("got caught");
                }
                receiveBuffer=tmp;
            }

    }


    public static boolean comparebytes(byte [] byte1,byte [] byte2)
    {
        boolean ans=true;
        if(byte1.length==byte2.length)
        {
            for(int i=0;i<byte1.length&&ans;i++)
                if(byte1[i]!=byte2[i])
                    ans=false;

        }

        else
        {
            ans=false;
        }


        return ans;
    }

    public long connected_time;
    public void getClientInfo()
    {
        if(connemode==mode.TCP)
            client=urladdress;
        else
            client=surf("https://livepc.pegien.co.ke/client.php?request=host&connection_id="+connection_id);

    }




    public static byte [] subbytes(int start,int end,byte [] full)
    {
        int endi=end;
        if(endi>full.length)
            endi=full.length;

        if(endi-start<0)
        {
            return new byte[0];
        }
        byte [] fine=new byte[endi-start];

        for(int i=start;i<end&&i<full.length;i++)
            fine[i-start]=full[i];

        return fine;
    }









    public void disconnect()
    {
        receiveBuffer=null;
        outputBuffer=null;
        foul=false;
        pow=false;
        power=false;
        sscurrect=null;
        ssprev=null;
        reclock=false;

        Thread t=new Thread()
        {
            public void run()
            {
        send_data(("Disconnect").getBytes());
        }};
        t.start();

        try
        {
            mysocket.close();

        }catch(Exception e)
        {

        }
        MainActivity.ms="Connecting ";
    }
public static int wang=0;
    public void receive_data()
    {

        Thread dd=new Thread(){
            public void run()
            {
                while(power)
                {
                    if(connemode==mode.TCP)
                    {
                        try
                        {
                            InputStream in=mysocket.getInputStream();
                            byte[] res=new byte[in.available()];
                            while(in.read(res)!=-1)
                            {
                                processbyte(res);
                                res=new byte[in.available()];
                            }

                        }catch(Exception e)
                        {
                            System.out.println(e);
                        }

                    }else
                    {
                        String web=surf(urladdress+"list.php?myid="+connection_id+"&direction="+(myrole==role.HOST?"home":"away"));
                        //System.out.println("web"+urladdress+"list.php?myid="+connection_id+"&direction="+(myrole==role.HOST?"home":"away"));
                        try
                        {
                            if(web.length()>2&&!web.contains("html")&&!web.contains("ption")) {
                                for(String me:web.split(";"))
                                {
                                    try
                                    {
                                        boolean loaded=false;
                                        URL url=new URL(urladdress+"fragments/"+me);
                                        HttpURLConnection con=(HttpURLConnection) url.openConnection();
                                        con.setRequestProperty("content-type", "binary/data");
                                        con.connect();

                                        InputStream in=con.getInputStream();

                                        int tot=in.available();
                                        byte[] kin=new byte[in.available()];
                                        while(in.read(kin)!=-1)
                                        {
                                            loaded=true;
                                            int avail=in.available();
                                            tot+=avail;
                                            try{
                                            processbyte(kin);
                                            }catch(Exception e)
                                            {
                                                MainActivity.disp(e.toString());
                                            }
                                            kin=new byte[avail];
                                        }
                                        System.out.println("downloaded\t:\t"+tot+";");
                                        if(loaded)
                                            System.out.print(surf(urladdress+"delete.php?file="+me));
                                    }catch(Exception e) {
                                        //System.out.println("network error receiving" + e + me);
                                        MainActivity.disp(("network error receiving" + e + me));
                                    }
                                }
                            }
                        }catch(Exception e)
                        {
                            System.out.println(e);
                            MainActivity.disp(e.toString());
                        }
                    }


                    try
                    {
                        int sleep_time=5;
                        Thread.sleep(sleep_time);
                    }catch(Exception dhgd){}

                    
                }



            }};
        dd.setPriority(Thread.MAX_PRIORITY);
        dd.start();











    }



    public void sync_data()
    {
        send_buffered();
        receive_data();
    }










    public static String surf(String link)
    {
        String res="";
        try
        {
            URL url=new URL(link);
            HttpURLConnection con=(HttpURLConnection) url.openConnection();

            InputStream in=con.getInputStream();
            byte [] tot=new byte[0];
            byte [] all=new byte[in.available()];
            while(in.read(all)!=-1)
            {
                tot=concatbyte(tot,all);
                all=new byte[in.available()];
            }

            res=new String(tot);
        }catch(Exception e)
        {
            res=e+"";
            MainActivity.disp(res=e+"");
        }
        return res;
    }

    public synchronized void translate(byte [] input)
    {
        MainActivity.mysettings.updateLog();
        byte [] inputa=decrypt(input);

        String greeting="Hello";
        String response="Hello too";
        //String


        if(comparebytes(inputa,greeting.getBytes())&&!pow)
        {
            send_data(response.getBytes());
            pow=true;
            //onAuth.actionPerformed(new ActionEvent("connection",0,"client found"));

        }
        else if(comparebytes(inputa,response.getBytes())&&!pow)
        {
            pow=true;
            //onAuth.actionPerformed(new ActionEvent("connection",0,"client found"));
        }
        else
        {
            foul=true;
        }

        String ss="<ss>";
        String sst="</ss>";
        if(findbyte(ss.getBytes(),inputa)!=-1&&findbyte(sst.getBytes(),inputa)!=-1)
        {
            ssprev=sscurrect;
            if(ssprev==null)
                sscurrect=new screenshot(inputa);
            else
                sscurrect=new screenshot(inputa,ssprev.whole);

           // MainActivity.disp("\nReceived "+(inputa.length/1000.0)+"kbs joined "+(sscurrect.whole.length/1000.0)+"kbs");
           // appenda("\nReceived "+(inputa.length/1000.0)+"kbs joined "+(sscurrect.whole.length/1000.0)+"kbs");

            loadss=true;
        }


        String cr="<cursor>";
        String crt="</cursor>";
        if(findbyte(cr.getBytes(),inputa)!=-1&&findbyte(crt.getBytes(),inputa)!=-1)
        {
            String dat=new String(midbytes(cr.getBytes(),crt.getBytes(),inputa));
            try {
                String[] bae = dat.split(",");
                MainActivity.cursorx=Integer.parseInt(bae[0]);
                MainActivity.cursory=Integer.parseInt(bae[1]);
            }catch (Exception e){}
        }

        if(findbyte(("Disconnect").getBytes(),inputa)!=-1)
        {
                        MainActivity.act.setContentView(MainActivity.l);
                        MainActivity.location=1;
                        MainActivity.a=10;
                        try {
                            Thread.sleep(1500);
                            disconnect();
                        }catch (Exception e)
                        {

                        }
                        MainActivity.disp("Disconnected");
        }





        }

   public static byte[] readAll(InputStream in)
   {
       byte [] total=new byte[0];
       try {
           byte[] all = new byte[in.available()];
           while (in.read(all) != -1) {
               total = concatbyte(total, all);
               all = new byte[in.available()];
               MainActivity.disp("reading");
           }
       }catch(Exception e)
       {
           MainActivity.disp(e.toString());
       }
       return total;
   }

    public boolean pow=false;
    public boolean foul=false;
    public static String stg="";

    public static void appenda(String str)
    {
        stg+="\n"+str;
        Thread t=new Thread()
        {
            public void run()
            {
                try
                {
                    File f=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/LivePC/pats.txt");
                    OutputStream out=new FileOutputStream(f);
                    out.write(stg.getBytes());
                    out.close();

                }catch (Exception e)
                {
                MainActivity.disp(e.toString());
                }
        }};
        t.start();

    }




}
