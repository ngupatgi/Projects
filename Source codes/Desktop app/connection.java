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

import java.util.*;
import java.io.*;
import java.net.*;
import java.lang.*;
import javax.script.*;
import java.security.cert.Certificate;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.awt.*;

import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;


public class connection{



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

public String myhostid=null;

public String username="";

public int receive_frequency=6000;

public int screenshare_frequency=12;

public long last_receive_time=0;

public String client=null;

public screenshot ssprev=null;
public screenshot sscurrect=null;


public String start_s="<mwanzo>";
    public String stop_s="</mwisho>";
public byte [] receiveBuffer=null;

public byte [] outputBuffer=null;

public static ActionListener Onconnect=null;
public static ActionListener onAuth=null;


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



public ServerSocket server=null;
public boolean connect()
{
if(connemode==mode.TCP)
{
	if(myrole==role.HOST)
	{
		
		try{
			System.out.println("My ip is : "+InetAddress.getLocalHost());
		server=new ServerSocket(port);
		Thread wait=new Thread()
		{
			public void run()
			{
				try
				{	
		mysocket=server.accept();
		//System.out.println("cONNECTED ip: "+mysocket.getRemoteSocketAddress());
		client=mysocket.getRemoteSocketAddress().toString();
		power=true;
		Onconnect.actionPerformed(new ActionEvent("connection",0,"client found"));
				}catch(Exception e)
				{
					System.out.println("host "+e);
				}
		}};
		wait.start();
		
	return true;
		}catch(Exception e)
		{
			System.out.println("Error"+e+" : ");
			return false;
		}
	}
	else if(myrole==role.CLIENT)
	{
		try
		{
			mysocket=new Socket(urladdress,port);
			power=true;
			Onconnect.actionPerformed(new ActionEvent("connection",0,"client found"));
	return true;
		}catch(Exception e)
		{
			System.out.println("connection"+e);
			
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

			if(myrole==role.HOST)
			{
				if(myhostid!=null)
				{
					if(surf(urladdress+"prepare.php?myid="+myhostid).equals("Success")){
						connection_id=myhostid;
						power=true;
						Onconnect.actionPerformed(new ActionEvent("connection",0,"client found"));
						return true;
					}
					else{
						System.out.println(surf(urladdress+"prepare.php?myid="+myhostid));
						return false;
					}
				}
				else
				{
				byte[] postdata=(new String("username="+username)).getBytes(StandardCharsets.UTF_8);
				String res=surf(urladdress+"index.php",postdata,netconnection);
				if(res.contains("Success")){
						myhostid=res.split(";")[0];
						connection_id=myhostid;
						power=true;
						Onconnect.actionPerformed(new ActionEvent("connection",0,"client found"));
						return true;
					}
					else
						return false;
				}


				
			}

			else
			{
				byte[] postdata=(new String("username="+username)).getBytes(StandardCharsets.UTF_8);
				if(surf(urladdress+"connect.php?myid="+connection_id,postdata,netconnection).equals("Success")){
					
					power=true;
					Onconnect.actionPerformed(new ActionEvent("connection",0,"client found"));
					return true;
				}
				else{
					System.out.println("ted"+surf(urladdress+"connect.php?myid="+connection_id,postdata,netconnection));
					return false;
				}
				
			}
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


	public long connected_time=0l;

    public void getClientInfo()
    {
        if(connemode==mode.TCP)
            client=mysocket.getRemoteSocketAddress().toString().split(":")[0].split("/")[1];
        else
            client=surf("https://livepc.pegien.co.ke/client.php?request=client&connection_id="+connection_id);

        //System.out.println("I got "+client);
    }






public static String surf(String page,byte[] postdata,HttpURLConnection netconnection)
{
	String res="";

try
{
		URL url=new URL(page);
		netconnection=(HttpURLConnection) url.openConnection();
		netconnection.setDoInput(true);
		netconnection.setDoOutput(true);
		netconnection.setReadTimeout(30000);
        netconnection.setConnectTimeout(30000);

		netconnection.setRequestMethod( "POST" );
		netconnection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 

		//byte[] postdata=(new String("username="+username)).getBytes(StandardCharsets.UTF_8);
		netconnection.setRequestProperty( "Content-Length",postdata.length+"");
		netconnection.setUseCaches(false);
			netconnection.connect();
			netconnection.getOutputStream().write(postdata);
		String bring=new String(netconnection.getInputStream().readAllBytes());
		res=bring;
		System.out.println(bring);
		netconnection.getInputStream().close();
	}catch(Exception e)
	{
		res="error"+e;
	}

return res;
}









public void send_data(byte[] data_to_send)
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
	//out.close();

}catch(Exception hggd)
{
	System.out.println(hggd);
	//javax.swing.JOptionPane.showMessageDialog(null,"Fails : "+hggd);
	//disconnect();
	//connect();
	disconnect();
    Livepc.connector();
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
		netconnection.setRequestProperty( "Content-Length",(outputBuffer.length)+"");
		netconnection.setUseCaches(false);
			netconnection.connect();
		byte[] predata=null;
		OutputStream out=netconnection.getOutputStream();

		String prep;
		prep="\n--"+boundary+"\nContent-Disposition: form-data; name=\"MAX_FILE_SIZE\"\n\n";
		prep+=1000000;
		predata=prep.getBytes();
		out.write(predata);
		prep="\n--"+boundary+"\nContent-Disposition: form-data; name=\"connection_id\"\n";
		prep+="Content-Type: text/plain;\n\n"+connection_id;
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




			
		String bring=new String(netconnection.getInputStream().readAllBytes());
		netconnection.getInputStream().close();
		System.out.println(bring);

		if(bring.contains("success"))
		{
			String size=bring.split("size:")[1];
			outputBuffer=subbytes(Integer.valueOf(size),outputBuffer.length,outputBuffer);
			System.out.println("uploaded bytes\t:\t"+size+";");
		}
				
				
					//System.out.println(bring);





		

		

	}catch(Exception e)
	{
		System.out.print("network error sending"+e);
	}
}
//System.out.print("trying to send");
try 
{
	Thread.sleep(5);
}catch(Exception e)
{

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
	System.out.println(e+"find");
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
public void processbyte(byte [] raw)
{
	   while(reclock)
        {
            try
            {
                Thread.sleep(3);
            }catch (Exception e)
            {

            }
        }
        reclock=true;

	if(receiveBuffer==null)
	{
		receiveBuffer=raw;
	}
	else
	{
		receiveBuffer=concatbyte(receiveBuffer,raw);
	}
	
	while(findbyte(start_s.getBytes(),receiveBuffer)!=-1&&findbyte(stop_s.getBytes(),receiveBuffer)!=-1)
	{
		translate(subbytes(findbyte(start_s.getBytes(),receiveBuffer)+start_s.length(),findbyte(stop_s.getBytes(),receiveBuffer),receiveBuffer));
		receiveBuffer=subbytes(findbyte(stop_s.getBytes(),receiveBuffer)+stop_s.length(),receiveBuffer.length,receiveBuffer);
	}
	reclock=false;
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

	try
	{
mysocket.close();

}catch(Exception e)
{
//disconnect();
}
try
{
server.close();
}catch(Exception e)
{
//disconnect();
}
}

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
			in.read(res);

			processbyte(res);

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
			if(web.length()>2&&!web.contains("html")&&!web.contains("ption"))	
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
							processbyte(kin);
							kin=new byte[avail];
						}
						System.out.println("downloaded\t:\t"+tot+";");
						if(loaded)
							System.out.print(surf(urladdress+"delete.php?file="+me));
					}catch(Exception e)
					{                 
						System.out.println("network error receiving"+e+me);
					}
				}
			}catch(Exception e)
			{
System.out.println(e);
			}
		}




		try
		{
			int sleep_time=5;
			Thread.sleep(sleep_time);
		}catch(Exception dhgd){}
	}



}};
dd.start();











}



public void sync_data()
{
	send_buffered();
	receive_data();
	scare();
}

public void scare()
{
	Thread s=new Thread()
	{
	public void run()
	{
	try
	{
		Thread.sleep(10000);
	}catch(Exception e)
	{

	}
	String dat="Hello";
	if(!pow&&!foul)
		send_data(dat.getBytes());
}};
s.start();
}








public static String surf(String link)
{
	String res="";
	try
	{
		URL url=new URL(link);
		HttpURLConnection con=(HttpURLConnection) url.openConnection();

		InputStream in=con.getInputStream();
		res=new String(in.readAllBytes());
	}catch(Exception e)
	{
		res=e+"";
	}
	return res;
}

public void translate(byte [] input)
{
	byte [] inputa=decrypt(input);
//System.out.println(new String(inputa.length+" "));
System.out.println(new String(inputa)+" ");
	String greeting="Hello";
	String response="Hello too";


	//String


	if(comparebytes(inputa,greeting.getBytes())&&!pow)
	{
		send_data(response.getBytes());
		pow=true;
		connected_time=new Date().getTime();
		onAuth.actionPerformed(new ActionEvent("connection",0,"client found"));

	}
	else if(comparebytes(inputa,response.getBytes())&&!pow)
	{
		pow=true;
		connected_time=new Date().getTime();
		onAuth.actionPerformed(new ActionEvent("connection",0,"client found"));
	}
	else
	{
		foul=true;
	}
	
	String ssr="screenshot";
	if(pow&&comparebytes(inputa,ssr.getBytes()))
	{
		send_data(Livepc.cursor().getBytes());
		sscurrect=new screenshot();
		if(ssprev==null)
		{
			send_data(sscurrect.jcover());
		}
		else
		{
			send_data(sscurrect.optimized(ssprev.whole));
		}
		
		//gua=new scree nshot(sscurrect.optimized(ssprev.whole),ssprev.whole);

		//System.out.println("would_get "+((gua.whole.length)/1000.0)+"kbs");


		ssprev=sscurrect;


	}


	String mv="<mousemove>";
	String mvt="</mousemove>";
    if(findbyte(mv.getBytes(),inputa)!=-1&&findbyte(mvt.getBytes(),inputa)!=-1)
        {
        	String mvc=new String(midbytes(mv.getBytes(),mvt.getBytes(),inputa));
        	String[] cods=mvc.split(",");
        	Livepc.mouse_move(Double.valueOf(cods[0]),Double.valueOf(cods[1]));
        }
          

		String cr="<cursor>";
        String crt="</cursor>";
        if(findbyte(cr.getBytes(),inputa)!=-1&&findbyte(crt.getBytes(),inputa)!=-1)
        {
            String dat=new String(midbytes(cr.getBytes(),crt.getBytes(),inputa));
            try {
                String[] bae = dat.split(",");
                Livepc.cursorx=Integer.parseInt(bae[0]);
                Livepc.cursory=Integer.parseInt(bae[1]);
            }catch (Exception e){}
        }


        String ss="<ss>";
        String sst="</ss>";
        if(findbyte(ss.getBytes(),inputa)!=-1&&findbyte(sst.getBytes(),inputa)!=-1)
        {
            Livepc.ssprev=Livepc.sscurrect;
            if(Livepc.ssprev==null)
                Livepc.sscurrect=new screenshot(inputa);
            else
                Livepc.sscurrect=new screenshot(inputa,Livepc.ssprev.whole);

        onScreenShot.actionPerformed(new ActionEvent("connection",0,"Screenshot found"));  


        }

        String res="mousedown";
        String res2="mouseup";
        String res3="mouseright";
        if(findbyte(res.getBytes(),inputa)!=-1)
        {
        	try
        	{
        		Robot r=new Robot();
        		r.mousePress(InputEvent.BUTTON1_DOWN_MASK);

        	}catch (Exception e) {
        		
        	}
        }

        if(findbyte(res2.getBytes(),inputa)!=-1)
        {
        	try
        	{
        		Robot r=new Robot();
        		r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        	}catch (Exception e) {
        		
        	}
        }

        if(findbyte(res3.getBytes(),inputa)!=-1)
        {
        	try
        	{
        		Robot r=new Robot();
        		r.mousePress(MouseEvent.BUTTON3_DOWN_MASK);
        		r.mouseRelease(MouseEvent.BUTTON3_DOWN_MASK);

        	}catch (Exception e) {
        		
        	}
        }


        String osk="osk";
        if(findbyte(osk.getBytes(),inputa)!=-1)
        {
        	
        		Thread h=new Thread()
        		{
        			public void run(){
        			try
        			{
		        		String comm="";
		        		if(isWindows())
		        			comm="osk";
		        		else
		        			comm="florence";



		        		Runtime r=Runtime.getRuntime();
		        		System.out.println("\n\n"+new String(r.exec(comm).getInputStream().readAllBytes())+"\n\n");
		        		}catch (Exception e) {
        				System.out.println("\n\n\n"+e+"error\n\n\n");
        				}
		        	}
		        };
		        h.start();


        	
        }




        String cr7="<scroll>";
        String cr8="</scroll>";
        if(findbyte(cr7.getBytes(),inputa)!=-1&&findbyte(cr8.getBytes(),inputa)!=-1)
        {
            String dat=new String(midbytes(cr7.getBytes(),cr8.getBytes(),inputa));
            try {
            	Robot r=new Robot();
            	r.mouseWheel(Integer.valueOf(dat));
                
            }catch (Exception e){}
        }

    String mtap="<tap>";
	String mvtap="</tap>";
    if(findbyte(mtap.getBytes(),inputa)!=-1&&findbyte(mvtap.getBytes(),inputa)!=-1)
        {
        	String mvc=new String(midbytes(mtap.getBytes(),mvtap.getBytes(),inputa));
        	String[] cods=mvc.split(",");
        	//Livepc.mouse_move(Double.valueOf(cods[0]),Double.valueOf(cods[1]));
        	try 
        	{
        		Robot r=new Robot();
        		Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
				double movx=d.width*Double.valueOf(cods[0]);
				double movy=d.height*Double.valueOf(cods[1]);
				r.mouseMove((int)Math.round(movx),(int)Math.round(movy));
				r.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
        		r.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);

        	}catch(Exception e)
        	{

        	}
        }


      if(findbyte(("Disconnect").getBytes(),inputa)!=-1)
        {
        	disconnect();
        	Livepc.connector();

        }
        





}

public boolean pow=false;
public boolean foul=true;
public ActionListener onScreenShot=null;
public static screenshot gua=null;
public static screenshot prevgua=null;

private static String OS = null;
   public static String getOsName()
   {
      if(OS == null) { OS = System.getProperty("os.name"); }
      return OS;
   }

public static boolean isWindows()
   {
      return getOsName().startsWith("Windows");
   }

}