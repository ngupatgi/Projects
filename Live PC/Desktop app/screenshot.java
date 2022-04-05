import java.awt.*;
import java.util.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;




public class screenshot{
	public byte [] whole=null;

screenshot()
{
	try
{
	Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
	Rectangle rect=new Rectangle(dimension.width,dimension.height);
Robot robot=new Robot();
BufferedImage image=robot.createScreenCapture(rect);

ByteArrayOutputStream out=new ByteArrayOutputStream();
ImageIO.write(image,"jpeg",out);
whole=out.toByteArray();

}catch(Exception tyw)
{
	System.out.println(tyw);
}
System.out.println("sent "+(whole.length/1000.0)+"kbs");
}

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


whole=tmp;


}

screenshot(byte [] alls)
{
    String b4="<ss>";
    String a4="</ss>";
whole=connection.midbytes(b4.getBytes(),a4.getBytes(),alls);
}


public byte[] optimized(byte [] previous_img)
{
int start=0;
int end=whole.length;

boolean found_start=false;
boolean found_end=false;

for(int i=0;i<whole.length&&i<previous_img.length&&!found_start;i++)
{
	if(whole[i]==previous_img[i])
		start++;
	else
		found_start=true;
}

for(int j=1;!found_end&&end>start;j++)
{
	if(whole[whole.length-j]==previous_img[previous_img.length-j])
	{
		end--;
	}
	else
	{
		found_end=true;
	}

}

String before="<ss><pr>"+start+"</pr>";
byte [] tmpbyte=connection.subbytes(start,end,whole);
String after="<pst>"+(previous_img.length-(whole.length-end))+"</pst></ss>";
byte[] optimg=connection.concatbyte(before.getBytes(),tmpbyte);
optimg=connection.concatbyte(optimg,after.getBytes());
System.out.println("optim "+(optimg.length/1000.0)+"kbs");

return optimg;
}



byte [] jcover()
{
	String b4="<ss>";
	String a4="</ss>";
	byte [] tmp=connection.concatbyte(b4.getBytes(),whole);
	tmp=connection.concatbyte(tmp,a4.getBytes());

	return tmp;
}






}