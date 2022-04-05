import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Game extends JFrame implements ComponentListener{

	public board b;
	piece[][] structure;
	public static String playas;

	Game(String title)
	{
		super(title);
		structure=new piece[8][8];
		b=new board(structure);
		b.user(playas);
		b.setLocation(0,0);
		add(b);
		setVisible(true);
		setBounds(100,100,600,650);
		b.height=getSize().height-50;
		b.width=getSize().width;	
		getContentPane().addComponentListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void componentResized(ComponentEvent ce) {
		b.height=getSize().height-50;
		b.width=getSize().width;
		b.prev=new piece[8][8];

	}
	 public void componentHidden(ComponentEvent ce) {};
  public void componentShown(ComponentEvent ce) {};
  public void componentMoved(ComponentEvent ce) { };

  public void update()
  {
  	//b.repaint();
  }


	public static void main(String[] args)
	{
		playas=args[0];
		Game f=new Game("Chess");
		
		System.out.println(playas);
		f.structure[0][0]=new Rook("white");
		f.structure[0][1]=new Knight("white");
		f.structure[0][2]=new Bishop("white");
		f.structure[0][3]=new Queen("white");
		f.structure[0][4]=new King("white");
		f.structure[0][5]=new Bishop("white");
		f.structure[0][6]=new Knight("white");
		f.structure[0][7]=new Rook("white");

		for(int i=0;i<8;i++)
			f.structure[1][i]=new Pawn("white");
		//f.update();

		f.structure[7][0]=new Rook("black");
		f.structure[7][1]=new Knight("black");
		f.structure[7][2]=new Bishop("black");
		f.structure[7][3]=new Queen("black");
		f.structure[7][4]=new King("black");
		f.structure[7][5]=new Bishop("black");
		f.structure[7][6]=new Knight("black");
		f.structure[7][7]=new Rook("black");

		for(int i=0;i<8;i++)
			f.structure[6][i]=new Pawn("black");

		f.b.circled=new ArrayList<Loc>();
		//f.b.circled.add(new Loc(0,0));
		ActionListener recap=new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				//f.b.toggle();
				
				//f.b.circle();

			}};

		new javax.swing.Timer(5000,recap).start();
		
	}
}
