import java.io.*;


public class rait{
public static String[] fq=new String[]{"JPanel","JLabel","JButton","JTextField","JPasswordField","JTextArea"};
	public static String[][] patc=new String[][]{{"iden","add","setSize","setLocation","setBackground","setLayout"},{"iden","add","setSize","setLocation","setBackground","setText","setForeground","setFont"},{"iden","add","setSize","setLocation","setBackground","setText","setForeground","setFont"},{"iden","add","setSize","setLocation","setBackground","setForeground","setFont"},{"iden","add","setSize","setLocation","setBackground","setForeground","setFont"},{"iden","add","setSize","setLocation","setBackground","setForeground","setFont"}};

public static String[] saka;

public rait(int a,int b,String c)
{
saka=new String[patc[a].length]; 

try
{
InputStream in=new FileInputStream(fq[a]+".pat");
String s1=new String(in.readAllBytes());
String[] s2=s1.split("~");
for(int y=0;y<s2.length&&y<patc[a].length;y++)
{
	saka[y]=s2[y];
}

in.close();
}catch(Exception gghsd)
{

}


saka[b]=c;

try
{
OutputStream out=new FileOutputStream(fq[a]+".pat");
String oda="";
for(String t:saka)
{
	oda=oda+t+"~";
}

out.write(oda.getBytes());
out.close();





}catch(Exception sdygghsd)
{

}










}




}