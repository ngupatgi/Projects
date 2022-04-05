import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;
import java.net.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.datatransfer.StringSelection;


public class Livepc{
public static Dimension len=Toolkit.getDefaultToolkit().getScreenSize();
public static JFrame main=new JFrame("LivePC");
private static connection con=new connection();

	public static void main(String [] args)
	{
main.setVisible(true);
main.setBounds(0,0,len.width,len.height);
main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

/*
con.urladdress="http://pats.atwebpages.com/LivePC/";
//con.urladdress="http://pegien.000webhostapp.com/";
con.urladdress="http://127.0.0.1/home/LivePC/";
con.urladdress="http://pegien.000webhostapp.com/";


//con.connection_id="6193c970338b8";
//con.myhostid=con.connection_id;
con.username="patrick Test";
con.securitykey="pats";
*/

mode_select();
}

public static Font f(int size,int weight)
{
	return new Font("Arial",(weight==0?Font.PLAIN:Font.BOLD),size);
}

public static void mode_select()
{
	main.getContentPane().removeAll();
final JPanel home=new JPanel();
main.add(home);
home.setSize(len.width,len.height);
home.setLocation(0,0);
home.setBackground(new Color(143, 143, 61));
home.setLayout(null);

JButton btn1=new JButton();
home.add(btn1);
btn1.setSize(len.width/4,len.height/4);
btn1.setLocation(len.width/8,len.height/2-len.height/8);
btn1.setBackground(new Color(0,0,139));
btn1.setText("HOST MODE");
btn1.setForeground(Color.white);
btn1.setFont(f(25,1));

btn1.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent t)
{
con.myrole=role.HOST;
connector();
}});

JButton btn2=new JButton();
home.add(btn2);
btn2.setSize(len.width/4,len.height/4);
btn2.setLocation(len.width-len.width/4-len.width/8,len.height/2-len.height/8);
btn2.setBackground(new Color(255, 0, 0));
btn2.setText("CLIENT MODE");
btn2.setForeground(Color.white);
btn2.setFont(f(25,1));
btn2.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent t)
{
con.myrole=role.CLIENT;
connector();
}});



}

public static void connector()
{
	main.getContentPane().removeAll();
final JPanel connector_pane=new JPanel();
main.add(connector_pane);
connector_pane.setSize(len.width,len.height);
connector_pane.setLocation(0,0);
connector_pane.setBackground(new Color(200,200,200));
connector_pane.setLayout(null);

main.repaint();


JButton h1=new JButton(con.myrole.toString()+" CONNECTION");
h1.setBounds(0,0,len.width/2,30);
h1.setForeground(Color.white);
h1.setBackground(new Color(39,144,255));
h1.setFont(f(18,1));


BasicArrowButton back=new BasicArrowButton(BasicArrowButton.WEST);
back.setBounds(20,20,60,30);
back.setBackground(new Color(20,20,20));
back.setBorder(null);
back.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			mode_select();
		}
	});
//back.setForeground(Color.white);

connector_pane.add(back);


JPanel gath=new JPanel();
connector_pane.add(gath);
//gath.setSize(len.width/2,len.height-150);
//gath.setLocation(len.width/4,70);
//gath.setBackground(new Color(39,144,255));
gath.setBounds(len.width/4,70,len.width/2,len.height-200);
gath.setLayout(new BorderLayout());

JPanel tmp=new JPanel();
tmp.setLayout(new GridLayout(3,1));
tmp.setBackground(new Color(39,144,255));

gath.add(tmp,BorderLayout.NORTH);


tmp.add(h1);


JLabel label = new JLabel("Select mode Of Connection");


JPanel selecta=new JPanel();
//selecta.setBounds(0,31,len.width/2,50);
selecta.setBackground(Color.white);
selecta.setLayout(new GridLayout(1,2));

tmp.add(selecta);

final JButton modenet=new JButton("<html><center>INTERNET<BR>MODE</html>");
modenet.setBackground(new Color(0,150,0));
//modenet.setBounds(0,31,len.width/4-10,50);
selecta.add(modenet);
modenet.setFont(f(15,1));
modenet.setForeground(Color.white);


final JButton modetcp=new JButton("<html><center>TCP (via LAN or WLAN)<BR>MODE</html>");
modetcp.setBackground(new Color(0,155,0));
//modetcp.setBounds(len.width/4,31,len.width/4-10,50);
selecta.add(modetcp);
modetcp.setFont(f(15,1));
modetcp.setForeground(Color.white);


JPanel gush=new JPanel();
gush.setBackground(new Color(39,144,255));
gush.setLayout(new GridLayout(1,1));

gath.add(gush,BorderLayout.CENTER);



ActionListener listener1=new ActionListener(){
public void actionPerformed(ActionEvent e)
{
	con.connemode=mode.INTERNET;
modenet.setBackground(Color.blue);
modetcp.setBackground(new Color(0,155,0));
gush.removeAll();
gush.add(config(0));
main.repaint();
}
};

ActionListener listener2=new ActionListener(){
public void actionPerformed(ActionEvent e)
{
con.connemode=mode.TCP;
modetcp.setBackground(Color.blue);
modenet.setBackground(new Color(0,155,0));
gush.removeAll();
gush.add(config(1));
main.repaint();
}
};




modenet.addActionListener(listener1);
modetcp.addActionListener(listener2);



go=new JButton("<html><center><br>START CONNECTION<br><br></html>");
go.setForeground(Color.white);
go.setBackground(Color.blue);
go.setFont(f(20,1));

gath.add(go,BorderLayout.SOUTH);




}

public static JButton go=null;
private static boolean showpassword=false;
public static JButton sh=null;
public static JPanel config(int mode)
{

	GridLayout tg=new GridLayout(1300,2);
	tg.setVgap(50);
	tg.setHgap(20);
	JPanel tmp=new JPanel();
	tmp.setBackground(new Color(39,144,255));
	tmp.setLayout(null);
	tmp.setBounds(0,0,len.width/2,len.height);


if(mode==0)
{


if(con.myrole==role.HOST)
{
			JLabel l1=new JLabel("EMAIL",JLabel.CENTER);
			tmp.add(l1);
			l1.setBounds(len.width/4-300,0,200,40);
			l1.setFont(f(20,1));

			final JTextField f1=new JTextField(con.username);
			tmp.add(f1);
			f1.setBounds(len.width/4-100,0,len.width/4-50,30);

			JLabel l2=new JLabel("Security Key",JLabel.CENTER);
			tmp.add(l2);
			l2.setBounds(len.width/4-300,100,200,40);
			l2.setFont(f(20,1));

			final JTextField txpsw=new JTextField(con.securitykey);
			tmp.add(txpsw);
			txpsw.setBounds(len.width/4-100,100,len.width/4-50,30);
			txpsw.setVisible(false);

			final JPasswordField psw=new JPasswordField(con.securitykey);
			tmp.add(psw);
			psw.setBounds(len.width/4-100,100,len.width/4-50,30);




			sh=new JButton("Show Password");
			sh.setBounds(len.width/4-100,150,200,35);
			tmp.add(sh);

			if(showpassword){
				txpsw.setVisible(true);
				psw.setVisible(false);
				sh.setText("Hide Password");
			}
			else{
				txpsw.setVisible(false);
				psw.setVisible(true);
				sh.setText("Show Password");
			}


			sh.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					if(showpassword)
					{
						showpassword=false;
						txpsw.setVisible(false);
						psw.setVisible(true);
						psw.setText(txpsw.getText());
						psw.setCaretPosition(txpsw.getCaretPosition());
						psw.grabFocus();
						sh.setText("Show Password");


					}
					else
					{
						showpassword=true;
						psw.setVisible(false);
						txpsw.setVisible(true);
						txpsw.setText(psw.getText());
						txpsw.setCaretPosition(psw.getCaretPosition());
						txpsw.grabFocus();
						sh.setText("Hide Password");


					}
				}
			});

	go.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e)
	{
		//con.urladdress="http://pats.atwebpages.com/LivePC/";
		//con.urladdress="http://192.168.43.229/home/LivePC/";
		//con.urladdress="http://127.0.0.1/home/LivePC/";
		//con.urladdress="http://pegien.000webhostapp.com/";
		con.urladdress="http://livepc.pegien.co.ke/";
		con.Onconnect=new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				Thread h=new Thread()
				{
					public void run()
					{
				JOptionPane.showMessageDialog(null,"Connected , Checking Security key ");
			}
			};
			h.start();
			con.sync_data();
			}
		};

			con.onAuth=new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					Thread h=new Thread()
					{
						public void run()
						{
					JOptionPane.showMessageDialog(null,"Access Successful");


				}
				};
				h.start();
				success();
				}
			};
		con.username=f1.getText();
		con.securitykey=(showpassword?txpsw.getText():psw.getText());
		if(con.connect()){
			waiting();

		}
	}
});

}
else if(con.myrole==role.CLIENT)
{
			

			JLabel cid=new JLabel("Connection ID");
			tmp.add(cid);
			cid.setBounds(len.width/4-300,0,200,40);
			cid.setFont(f(20,1));

			final JTextField f6=new JTextField(con.connection_id);
			tmp.add(f6);
			f6.setBounds(len.width/4-100,0,len.width/4-50,30);



			JLabel l1=new JLabel("EMAIL",JLabel.CENTER);
			tmp.add(l1);
			l1.setBounds(len.width/4-300,100,200,40);
			l1.setFont(f(20,1));

			final JTextField f1=new JTextField(con.username);
			tmp.add(f1);
			f1.setBounds(len.width/4-100,100,len.width/4-50,30);

			JLabel l2=new JLabel("Security Key",JLabel.CENTER);
			tmp.add(l2);
			l2.setBounds(len.width/4-300,200,200,40);
			l2.setFont(f(20,1));

			final JTextField txpsw=new JTextField(con.securitykey);
			tmp.add(txpsw);
			txpsw.setBounds(len.width/4-100,200,len.width/4-50,30);
			txpsw.setVisible(false);

			final JPasswordField psw=new JPasswordField(con.securitykey);
			tmp.add(psw);
			psw.setBounds(len.width/4-100,200,len.width/4-50,30);




			sh=new JButton("Show Password");
			sh.setBounds(len.width/4-100,250,200,35);
			tmp.add(sh);

			if(showpassword){
				txpsw.setVisible(true);
				psw.setVisible(false);
				sh.setText("Hide Password");
			}
			else{
				txpsw.setVisible(false);
				psw.setVisible(true);
				sh.setText("Show Password");
			}


			sh.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					if(showpassword)
					{
						showpassword=false;
						txpsw.setVisible(false);
						psw.setVisible(true);
						psw.setText(txpsw.getText());
						psw.setCaretPosition(txpsw.getCaretPosition());
						psw.grabFocus();
						sh.setText("Show Password");


					}
					else
					{
						showpassword=true;
						psw.setVisible(false);
						txpsw.setVisible(true);
						txpsw.setText(psw.getText());
						txpsw.setCaretPosition(psw.getCaretPosition());
						txpsw.grabFocus();
						sh.setText("Hide Password");


					}
				}
			});

	go.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e)
	{
		//con.urladdress="http://pats.atwebpages.com/LivePC/";
		//con.urladdress="http://192.168.43.229/home/LivePC/";
		//con.urladdress="http://127.0.0.1/home/LivePC/";
		//con.urladdress="http://pegien.000webhostapp.com/";
		con.urladdress="http://livepc.pegien.co.ke/";
		con.username=f1.getText();
		con.connection_id=f6.getText();
		con.securitykey=(showpassword?txpsw.getText():psw.getText());
		con.Onconnect=new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				Thread h=new Thread()
				{
					public void run()
					{
				JOptionPane.showMessageDialog(null,"Reached the host "+con.urladdress);
			}
			};
			h.start();
			con.sync_data();
			String me="Hello";
			con.send_data(me.getBytes());
			}
		};

	con.onAuth=new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			Thread h=new Thread()
			{
				public void run()
				{
			JOptionPane.showMessageDialog(null,"Auth Successful");
			
		}
		};
		h.start();
		//con.connected_time=new Date().getTime();
		session();
		}
	};
		if(con.connect()){
			System.out.println("connected");
			

		}
	}
});


}



}
else if(mode==1)
{
	if(con.myrole==role.HOST)
	{

			tmp.add(ips());








			JLabel l2=new JLabel("Security Key",JLabel.CENTER);
			tmp.add(l2);
			l2.setBounds(len.width/4-300,170,200,40);
			l2.setFont(f(20,1));

			final JTextField txpsw=new JTextField(con.securitykey);
			tmp.add(txpsw);
			txpsw.setBounds(len.width/4-100,170,len.width/4-50,30);
			

			final JPasswordField psw=new JPasswordField(con.securitykey);
			tmp.add(psw);
			psw.setBounds(len.width/4-100,170,len.width/4-50,30);

			



			sh=new JButton("Show Password");
			sh.setBounds(len.width/4-100,220,200,35);
			tmp.add(sh);

			if(showpassword){
				txpsw.setVisible(true);
				psw.setVisible(false);
				sh.setText("Hide Password");
			}
			else{
				txpsw.setVisible(false);
				psw.setVisible(true);
				sh.setText("Show Password");
			}

			sh.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					if(showpassword)
					{
						showpassword=false;
						txpsw.setVisible(false);
						psw.setVisible(true);
						psw.setText(txpsw.getText());
						psw.setCaretPosition(txpsw.getCaretPosition());
						psw.grabFocus();
						sh.setText("Show Password");


					}
					else
					{
						showpassword=true;
						psw.setVisible(false);
						txpsw.setVisible(true);
						txpsw.setText(psw.getText());
						txpsw.setCaretPosition(psw.getCaretPosition());
						txpsw.grabFocus();
						sh.setText("Hide Password");


					}
				}
			});

	go.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e)
	{	
		con.Onconnect=new ActionListener()
{
	public void actionPerformed(ActionEvent e)
	{

		Thread h=new Thread()
		{
			public void run()
			{
		con.getClientInfo();
		JOptionPane.showMessageDialog(null,"Connected Authenticating "+con.client);
	}
	};
	h.start();
	con.sync_data();
	}
};

con.onAuth=new ActionListener()
{
	public void actionPerformed(ActionEvent e)
	{
		Thread h=new Thread()
		{
			public void run()
			{
		JOptionPane.showMessageDialog(null,"Auth Successful");
		//con.connected_time=new Date().getTime();
	}
	};
	h.start();
	success();

	}
};
		con.securitykey=(showpassword?txpsw.getText():psw.getText());
		if(con.connect())
			waiting();
	}
});

}
else if(con.myrole==role.CLIENT)
{

			JLabel l1=new JLabel("Enter Host Ip ",JLabel.CENTER);
			tmp.add(l1);
			l1.setBounds(len.width/4-300,0,200,40);
			l1.setFont(f(20,1));

			final JTextField f1=new JTextField();
			tmp.add(f1);
			f1.setBounds(len.width/4-100,0,len.width/4-50,30);

			JLabel l2=new JLabel("Security Key",JLabel.CENTER);
			tmp.add(l2);
			l2.setBounds(len.width/4-300,100,200,40);
			l2.setFont(f(20,1));

			final JTextField txpsw=new JTextField(con.securitykey);
			tmp.add(txpsw);
			txpsw.setBounds(len.width/4-100,100,len.width/4-50,30);
			txpsw.setVisible(false);

			final JPasswordField psw=new JPasswordField(con.securitykey);
			tmp.add(psw);
			psw.setBounds(len.width/4-100,100,len.width/4-50,30);




			sh=new JButton("Show Password");
			sh.setBounds(len.width/4-100,150,200,35);
			tmp.add(sh);

			if(showpassword){
				txpsw.setVisible(true);
				psw.setVisible(false);
				sh.setText("Hide Password");
			}
			else{
				txpsw.setVisible(false);
				psw.setVisible(true);
				sh.setText("Show Password");
			}


			sh.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					if(showpassword)
					{
						showpassword=false;
						txpsw.setVisible(false);
						psw.setVisible(true);
						psw.setText(txpsw.getText());
						psw.setCaretPosition(txpsw.getCaretPosition());
						psw.grabFocus();
						sh.setText("Show Password");


					}
					else
					{
						showpassword=true;
						psw.setVisible(false);
						txpsw.setVisible(true);
						txpsw.setText(psw.getText());
						txpsw.setCaretPosition(psw.getCaretPosition());
						txpsw.grabFocus();
						sh.setText("Hide Password");


					}
				}
			});

	go.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e)
	{
		con.urladdress=f1.getText();
		//con.urladdress="http://192.168.43.229/home/LivePC/";
		con.Onconnect=new ActionListener()
{
	public void actionPerformed(ActionEvent e)
	{

		Thread h=new Thread()
		{
			public void run()
			{
		JOptionPane.showMessageDialog(null,"Reached host on "+con.urladdress);
	}
	};
	h.start();
	con.sync_data();
	String me="Hello";
	con.send_data(me.getBytes());
	}
};

con.onAuth=new ActionListener()
{
	public void actionPerformed(ActionEvent e)
	{
		Thread h=new Thread()
		{
			public void run()
			{
		JOptionPane.showMessageDialog(null,"Access Successful");
		//con.connected_time=new Date().getTime();
	}
	};
	h.start();
	session();
	}
};
		

		con.securitykey=(showpassword?txpsw.getText():psw.getText());
		if(con.connect()){
			


		}
	}
});


}

}





return tmp;

}



public static JPanel ips()
{
	JPanel mine=new JPanel();
	mine.setBackground(new Color(39,144,255));
	mine.setBounds(0,0,len.width,150);
	mine.setLayout(null);


try
{
Enumeration<NetworkInterface> net=NetworkInterface.getNetworkInterfaces();
ArrayList<NetworkInterface> liz=Collections.list(net);
GridLayout ll=new GridLayout(liz.size(),2);
//System.out.println(liz.size());
//mine.setLayout(ll);
int a=0;
for(NetworkInterface netint:liz)
{
JLabel tt=new JLabel(netint.getDisplayName()+" ("+netint.getName()+") IP Address ",JLabel
.CENTER);
tt.setBounds(0,a*40,len.width/4,30);

String paz="";
Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
if(inetAddress.toString().length()>5&&inetAddress.toString().contains(".")&&inetAddress.toString().length()<=17)
{
paz=inetAddress.toString().replaceAll("/","");
System.out.println(paz);

JTextField tf=new JTextField(paz);
tf.setBounds(len.width/4,a*40,len.width/4-150,30);
tf.setEditable(false);

final String pao=paz;
JButton bscan=new JButton("QR Code");
bscan.setBounds(len.width/2-150,a*40,100,30);
//inetAddress.toString().length()>5&&!inetAddress.toString().contains("127.0.0.1")&&inetAddress.toString().length()<15)
bscan.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e)
	{
		Thread h=new Thread(){
			public void run()
			{
			new QRcode("Livepc:TCP:"+pao+":"+con.securitykey);
		}};
		h.start();
		
	}
});


mine.add(tt);
mine.add(tf);
mine.add(bscan);
a++;
}
}}
}catch(Exception e)
{
mine.add(new JLabel("No Interface Found",JLabel.CENTER));
}

mine.revalidate();
mine.repaint();

return mine;
}








public static Border rborder(int radius)
{
	return javax.swing.BorderFactory.createEmptyBorder(0,0,radius,radius);

}




public static void waiting()
{
main.getContentPane().removeAll();
main.repaint();
final JPanel pnew=new JPanel();
pnew.setLayout(null);
main.add(pnew);
pnew.setBounds(0,0,len.width,len.height);

BasicArrowButton back=new BasicArrowButton(BasicArrowButton.WEST);
back.setBounds(20,20,60,30);
back.setBackground(new Color(20,20,20));
back.setBorder(null);
back.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			con.send_data(("Disconnect").getBytes());
			try 
			{
				Thread.sleep(1500);
			con.disconnect();
		}catch(Exception ejk)
		{
		}

			connector();
		
		}
	});
	pnew.add(back);





JPanel hith=new JPanel();
hith.setBounds(len.width/4,200,len.width/2,400);
pnew.add(hith);
hith.setBackground(new Color(0,139,139));
hith.setLayout(null);

JLabel l1=new JLabel("Waiting For Client to Connect",JLabel.CENTER);
l1.setBounds(0,10,len.width/2,30);
l1.setBackground(Color.white);
l1.setFont(f(20,1));
hith.add(l1);

if(con.connemode==mode.TCP)
{
	JPanel ips=ips();
	ips.setBounds(0,60,len.width/2,390);
	ips.setBackground(new Color(0,139,139));
	hith.add(ips);
}
else if(con.connemode==mode.INTERNET)
{
	JLabel ids=new JLabel("Connection Id : ",JLabel.RIGHT);
	ids.setBounds(0,70,len.width/4-50,30);
	ids.setForeground(Color.white);
	ids.setFont(f(25,1));
	hith.add(ids);

	JTextField f3=new JTextField(con.connection_id);
	f3.setBounds(len.width/4,70,200,30);
	f3.setFont(f(25,1));
	hith.add(f3);

	JButton copy=new JButton("copy");
	copy.setBounds(len.width/2-120,70,100,30);
	hith.add(copy);
	copy.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e)
		{
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(con.connection_id),null);
			Toolkit.getDefaultToolkit().beep();
		}
	});


	JButton bscan=new JButton("QR Code");
	bscan.setBounds(len.width/4-100,200,200,30);
	//inetAddress.toString().length()>5&&!inetAddress.toString().contains("127.0.0.1")&&inetAddress.toString().length()<15)
	bscan.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e)
		{
			Thread h=new Thread(){
			public void run()
			{

				new QRcode("Livepc:INTERNET:"+con.connection_id+":"+con.username+":"+con.securitykey);



			}};
			h.start();


		}
	});

	hith.add(bscan);

}


main.repaint();



}


public static String cursor()
{
	int x=(int) MouseInfo.getPointerInfo().getLocation().getX();
	int y=(int) MouseInfo.getPointerInfo().getLocation().getY();
	String res="<cursor>"+x+","+y+"</cursor>";
	return res;
}

public static void mouse_move(Double x,double y)
{
	try
	{
		Robot r=new Robot();
		Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
		double movx=d.width*x;
		double movy=d.height*y;
		r.mouseMove((int)Math.round(movx),(int)Math.round(movy));
	}catch(Exception e)
	{

	}
}
public static JPanel sess=null;
public static void session()
{

sess=new JPanel();
main.getContentPane().removeAll();
main.add(sess);
main.repaint();
sess.setLayout(new GridLayout(1,1));
sess.setBounds(10,20,len.width-20,len.height-40);




    		final Canvas c=new Canvas();
    		c.setBackground(Color.cyan);
    		c.setBounds(10,20,len.width-20,len.height-40);

    		sess.add(c);

   Thread req=new Thread()
    {
        public void run()
        {
     while(true)
     {
    String ss="screenshot";
    con.send_data(ss.getBytes());
    try
    {
        Thread.sleep(60000/screenshare_frequency);
    }catch (Exception e)
    {

    }
}
    
        }
    };
    req.start();


    con.onScreenShot=new ActionListener()
    {
    	public void actionPerformed(ActionEvent e)
    	{
    		try
    		{

    		InputStream tmpin=new ByteArrayInputStream(sscurrect.whole);
    		final BufferedImage img=ImageIO.read(tmpin);
    		Graphics2D ga=((Graphics2D) img.getGraphics());
    		ga.drawOval(cursorx-4,cursory-4,8,8);
    		ga.setColor(new Color(0,0,0));
    		ga.drawOval(cursorx-5,cursory-5,10,10);
    		if(img==null)	
    			System.out.println("here is null");
    		

    		try
    		{
    		Graphics2D g=(Graphics2D) c.getGraphics();

    		//Graphics2D g=//=(Graphics2D) sess.createGraphics();//sess.getGraphics();
    		//g.clearRect(0,0,c.getSize().width,c.getSize().height);
    		//System.out.println("here");
    		//g.fillOval(cursorx,cursory,10,10);

    		g.drawImage(img,0,0,null);
    		//System.out.println("error here");
    		//g.drawString((sscurrect.whole.length/1000.0)+" kbs",50,50);
    		//System.out.println((sscurrect.whole.length/1000.0)+" kbs");
    		//c.setFillBackgroundColor(Color.WHITE);
    		//g.drawArc(cursorx, cursory, 10, 10, 0,(int) Math.round(2*Math.PI));
    		//c.fillArc();
    		//g.draw
    		//g.fillOval(cursorx,cursory,10,10);
    		//sess.paint(g);

    		}catch(Exception epf)
    		{
    			System.out.println("here"+epf);
    			JOptionPane.showMessageDialog(null,"error "+epf);
    		}

    		

    		System.out.println("here");
    		sess.setBackground(Color.red);
    		
    		//main.repaint();
    		
    	}catch(Exception egfgfgfg)
    	{
    		System.out.println(egfgfgfg);
    	}

    		
    	}
    };






}

public static int cursorx=0;
public static int cursory=0;
public static screenshot sscurrect;
public static screenshot ssprev;


public static int screenshare_frequency=60;




public static void success()
{

	main.getContentPane().removeAll();
main.repaint();
final JPanel pnew=new JPanel();
pnew.setLayout(null);
main.add(pnew);
pnew.setBounds(0,0,len.width,len.height);

BasicArrowButton back=new BasicArrowButton(BasicArrowButton.WEST);
back.setBounds(20,20,60,30);
back.setBackground(new Color(20,20,20));
back.setBorder(null);
back.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			con.disconnect();
			connector();
		}
	});
	pnew.add(back);





JPanel hith=new JPanel();
hith.setBounds(len.width/4,200,len.width/2,400);
pnew.add(hith);
hith.setBackground(new Color(0,139,139));
hith.setLayout(null);

final JLabel l1=new JLabel(con.client+" Is now controlling your Computer",JLabel.CENTER);
l1.setBounds(0,100,len.width/2,60);
l1.setBackground(Color.white);
l1.setFont(f(20,1));
l1.setForeground(new Color(139,0,0));
hith.add(l1);
//con.connected_time=new Date().getTime();

tm=new JLabel("Ellapsed",JLabel.CENTER);
tm.setBounds(0,300,len.width/2,60);
tm.setBackground(Color.white);
tm.setFont(f(20,1));
tm.setForeground(new Color(139,0,0));
hith.add(tm);

Toolkit.getDefaultToolkit().beep();
main.repaint();

Thread h=new Thread()
{
public void run()
{
	while(con.pow)
	{
	int ellapsed=(int)(new Date().getTime()-con.connected_time)/1000;
	int hrs=ellapsed/3600;
	int s=ellapsed%60;
	int min=ellapsed/60;

	tm.setText(hrs+" H \t "+min+" M \t "+s+" s");
	con.getClientInfo();
	l1.setText(con.client+" Is now controlling your Computer");
	try
	{
		Thread.sleep(500);
	}catch(Exception e)
	{

	}

	}
}
};
h.start();

}


public static JLabel tm;


}
