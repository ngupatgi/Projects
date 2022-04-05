import java.io.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class patide
{
  public static JFrame pat=new JFrame("PATRICK`S  IDE");
  public static JPanel home;
  public static Dimension len=Toolkit.getDefaultToolkit().getScreenSize();
  public static JTextPane khal;

public static void main(String [] args)
{
pat.setSize(len.width,len.height);
pat.setLocation(0,0);
pat.setLayout(null);
pat.setVisible(true);
home=new JPanel();
home.setSize(len.width,len.height);
home.setLocation(0,0);
pat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
home.setVisible(true);
home.setBackground(new Color(26, 140, 255));
pat.add(home);
home.setLayout(null);

JPanel conte=new JPanel();
conte.setBackground(new Color(45, 41, 33));
home.add(conte);
conte.setLocation(150,80);
conte.setSize(len.width-150,len.height-110);
conte.setVisible(true);
conte.setLayout(null);

JPanel pati=new JPanel();
JScrollPane sk=new JScrollPane(pati);
sk.setLocation(0,0);
sk.setSize(len.width-150,len.height-110);
conte.add(sk);
pati.setLocation(0,0);
pati.setSize(len.width-150,len.height-110);
pati.setBackground(new Color(45, 41, 33));
pati.setLayout(new GridLayout(1,1));

JPanel whole=new JPanel();
BorderLayout hhy=new BorderLayout();
whole.setLayout(hhy);
hhy.setHgap(20);
pati.add(whole);
whole.setSize(len.width-150,200*12);
whole.setLocation(0,0);
whole.setBackground(new Color(45, 41, 33));



khal=new JTextPane();
whole.add(khal,BorderLayout.CENTER);
khal.setBackground(new Color(45, 41, 33));
khal.setForeground(Color.white);
khal.setFont(new Font("Arial",Font.PLAIN,12));
khal.setCaretColor(Color.blue);



int bee=0;

int rows=khal.getDocument().getDefaultRootElement().getElementCount();
GridLayout gg;
JPanel nms=new JPanel();
whole.add(nms,BorderLayout.WEST);
nms.setBackground(new Color(45, 41, 33));
nms.setForeground(Color.pink);

JLabel hj=new JLabel();

JButton st=new JButton("Button");
st.setLocation(300,10);
st.setSize(100,50);
st.setVisible(true);
home.add(st);
st.addActionListener(new ActionListener()
{
public void actionPerformed(ActionEvent j)
{
String[] pyy=new String[10];
pyy[0]=JOptionPane.showInputDialog(home,"caption");
pyy[1]=JOptionPane.showInputDialog(home,"size");
pyy[2]=JOptionPane.showInputDialog(home,"location");
pyy[3]=JOptionPane.showInputDialog(home,"background colour");
pyy[4]=JOptionPane.showInputDialog(home,"parent");
pyy[5]=JOptionPane.showInputDialog(home,"foreground");
pyy[6]=JOptionPane.showInputDialog(home,"font");

String sn="";
sn="JButton "+pyy[0]+"=new JButton(\""+pyy[0]+"\");\n"+pyy[0]+".setSize("+pyy[1]+");\n";
sn=sn+pyy[0]+".setLocation("+pyy[2]+");\n";
sn=sn+pyy[0]+".setLayout("+"null"+");\n";
sn=sn+pyy[0]+".setForeground("+pyy[5]+");\n";
sn=sn+pyy[0]+".setBackground("+pyy[3]+");\n";
sn=sn+pyy[0]+".setFont(new Font(\"Arial\",Font.BOLD,"+pyy[6]+"));\n";
sn=sn+pyy[4]+".add("+pyy[0]+");\n";
sn=sn+pyy[0]+".addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){\n\n}});\n\n";

khal.setText(sn);

}});

JButton test=new JButton("test");
test.setSize(100,40);
test.setLocation(len.width/2,20);
test.setForeground(Color.black);
test.setBackground(Color.blue);
test.setFont(new Font("Arial",Font.BOLD,23));
home.add(test);
test.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){

}});





JButton open=new JButton("open");
open.setLocation(20,10);
open.setVisible(true);
home.add(open);
open.setSize(100,40);
open.addActionListener(new ActionListener()
{
public void actionPerformed(ActionEvent j)
{

	
try{
	JFileChooser kaki=new JFileChooser();
int ret=kaki.showOpenDialog(null);
	String gta=kaki.getSelectedFile().getPath();
	System.out.println(gta);
File files=new File(gta);
InputStream dd=new FileInputStream(files);
String buff=new String(dd.readAllBytes());
khal.setText(buff);

}catch(Exception fgg)
{

}

}
});











while(1==1)
{
rows=khal.getDocument().getDefaultRootElement().getElementCount();
gg=new GridLayout(rows+50,1);
nms.setLayout(gg);
if(rows>bee){
for(int a=bee+1;a<=rows;a++)
{

	hj=new JLabel(""+a,SwingConstants.CENTER);
	nms.add(hj);
	hj.setForeground(Color.cyan);
	hj.setFont(new Font("Arial",Font.PLAIN,12));

}
bee=rows;
}

if(rows<bee){
	for(int a=bee;a>=rows;a--)
	{
		nms.remove(a-1);
		bee--;
	}

}
try{
Thread.sleep(30);
}catch(Exception ty4){}
}
}}
