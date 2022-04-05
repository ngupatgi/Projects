import java.awt.*;
import javax.swing.*;
//import ajava.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.awt.datatransfer.StringSelection;


public class pcode{
	public static JFrame pa;
	public static Dimension len;
	public static JPanel zom;
	public static String[] fq=new String[]{"JPanel","JLabel","JButton","JTextField","JPasswordField","JTextArea"};
	public static String[][] patc=new String[][]{{"iden","add","setSize","setLocation","setBackground","setLayout"},{"iden","add","setSize","setLocation","setBackground","setText","setForeground","setFont"},{"iden","add","setSize","setLocation","setBackground","setText","setForeground","setFont"},{"iden","add","setSize","setLocation","setBackground","setForeground","setFont"},{"iden","add","setSize","setLocation","setBackground","setForeground","setFont"},{"iden","add","setSize","setLocation","setBackground","setForeground","setFont"}};
public static JComboBox c1;
public static JTextArea p3;
public static void main(String[] args)
{
pa=new JFrame("p-code");
len=Toolkit.getDefaultToolkit().getScreenSize();
pa.setSize(len);
pa.setVisible(true);
pa.setLocation(0,0);

zom=new JPanel();
zom.setSize(len);
zom.setLocation(0,0);
pa.add(zom);
zom.setBackground(new Color(0, 102, 102));
zom.setLayout(null);
pa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

c1=new JComboBox(fq);
c1.setLocation(50,30);
c1.setSize(250,30);
zom.add(c1);
c1.setSelectedIndex(-1);
//c1.addItem("daf");
c1.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent trwf)
		{
res(c1.getSelectedIndex());


		}
	});



p3=new JTextArea();
p3.setLocation(50,160);
p3.setSize(len.width-100,len.height-260);
zom.add(p3);
p3.setBackground(new Color(0,49,20));
p3.setFont(new Font("Arial",Font.PLAIN,19));
p3.setCaretColor(Color.cyan);
p3.setCaretPosition(0);
p3.grabFocus();
pa.repaint();
p3.setForeground(new Color(39,144,255));





JButton cpy=new JButton("copy");
cpy.setLocation(50,120);
cpy.setSize(100,30);
zom.add(cpy);

cpy.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent ghdfgs)
	{
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(p3.getText()),null);
		Toolkit.getDefaultToolkit().beep();
		p3.grabFocus();
	}
});

cpy.addKeyListener(new KeyAdapter(){
public void keyPressed(KeyEvent d){
Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(p3.getText()),null);
		Toolkit.getDefaultToolkit().beep();

p3.grabFocus();
}});

}


public static JPanel reser;
public static void res(int s)
{
try
{
reser.setVisible(false);

}catch(Exception asggs)
{

}
try
{
zom.remove(reser);

}catch(Exception asggs)
{

}




reser=new JPanel();
reser.setSize(len.width-300,100);
reser.setLocation(300,40);
reser.setLayout(new GridLayout(2,patc[s].length));
zom.add(reser);
pa.repaint();
int f6=-1;
for(String op:patc[s])
{
	f6++;
	final int g3=f6;
	JButton sd=new JButton(op);
	reser.add(sd);
	sd.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent gfst)
		{
			//JOptionPane.showMessageDialog(pa,patc[s][g3]);
			redi f=new redi();
			String dew=f.reda(s,g3);
String twar=JOptionPane.showInputDialog(sd,fq[s]+"       "+patc[s][g3],dew);
try
{
if(twar.length()>0){new rait(s,g3,twar);}
}catch(Exception ysd)
{

}
		}
	});
}


pa.revalidate();

try
{
b10.setVisible(false);
zom.remove(b10);
}catch(Exception ssdsdg)
{

}

b10=new JButton("Generate "+fq[s]);
b10.setSize(230,40);
b10.setLocation(50,70);
zom.add(b10);
b10.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent gdstsak)
{
String sav="";
final redi p=new redi();

sav=sav+fq[s]+" "+p.reda(s,0)+"=new "+fq[s]+"();\n";
sav=sav+p.reda(s,1)+".add("+p.reda(s,0)+");\n";

for(int r=2;r<patc[s].length;r++)
{
sav=sav+p.reda(s,0)+"."+patc[s][r]+"("+p.reda(s,r)+");\n";
}

if(fq[s].equals("JButton"))
{
sav=sav+p.reda(s,0)+".addActionListener(new ActionListener(){\npublic void actionPerformed(ActionEvent t)\n{\n\n}});\n";

}

if(fq[s].equals("JTextField"))
{
sav=sav+p.reda(s,0)+".addKeyListener(new KeyAdapter(){\npublic void keyReleased(KeyEvent d){\nif(d.getKeyCode()==KeyEvent.VK_ENTER)\n{\n}\n\n}});\n";

}



p3.setText(sav);
p3.grabFocus();
int ds=p3.getCaretPosition();
System.out.println(ds);
p3.setCaretPosition(ds-5);

}

});




















}

public static JButton b10;






}
