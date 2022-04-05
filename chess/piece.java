import java.util.*;
import javax.swing.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.*;

public class piece{
	public String path;
	public String owner;
	public int points;
	public String col;
	public Boolean moved=false;
	


	public  BufferedImage getImage() throws IOException
	{
		return ImageIO.read(new File("picts/"+path));
	}

	public BufferedImage rotated() throws IOException{
		BufferedImage imageToRotate=getImage();
        int widthOfImage = imageToRotate.getWidth();
        int heightOfImage = imageToRotate.getHeight();
        int typeOfImage = imageToRotate.getType();

        BufferedImage newImageFromBuffer = new BufferedImage(widthOfImage, heightOfImage, typeOfImage);

        Graphics2D graphics2D = newImageFromBuffer.createGraphics();

        graphics2D.rotate(Math.toRadians(180), widthOfImage / 2, heightOfImage / 2);
        graphics2D.drawImage(imageToRotate, null, 0, 0);

        return newImageFromBuffer;
    }

    public  BufferedImage getImage(Boolean rot) throws IOException
	{
		if(rot&&col.equals("white")||(!rot&&col.equals("black")))
			return rotated();
		else 
			return getImage();

	}

	public ArrayList<Loc> moves(piece[][] structure,int i,int j)
	{
		return new ArrayList<Loc> ();
	}
	

}