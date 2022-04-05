import java.awt.*;
import javax.swing.*;
import java.awt.Color.*;
import java.awt.event.*;
import java.util.ArrayList;

public class board extends Canvas{
	public int width=10;
	public int height=10 ;
	piece[][] structure;
	public Boolean rot;
	static piece[][] prev;
	static int activerow;
	static int activecolumn;
	public Boolean ready=false;
	public ArrayList<Loc> circled=new ArrayList<Loc>();

	char[] column={'A','B','C','D','E','F','G','H'};


	board(piece[][] structure)
	{
		this.structure=structure;
		prev=new piece[8][8];
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				prev[i][j]=structure[i][j];

		ActionListener reload=new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				try{

					Graphics2D g=(Graphics2D) getGraphics();
					for(int k=0;k<8;k++)
						for(int l=0;l<8;l++)
							if(prev[k][l]!=structure[k][l]){
									g.setColor((((7-k)%2==0&&(l)%2==1)||((7-k)%2==1&&(l)%2==0))?new Color(125 ,135 ,150):new Color(232 ,235 ,239));
									g.fillRect(width*(rot?(7-l):(l))/8,height*(rot?k:(7-k))/8,width/8,height/8);
									if(structure[k][l]!=null)
										g.drawImage(structure[k][l].getImage(rot),(rot?(7-l):(l))*width/8,(rot?k:(7-k))*height/8,width/8,height/8,null);
									prev[k][l]=structure[k][l];
							}
							else if(structure[k][l]==null&&prev[k][l]!=null){
								g.setColor((((7-k)%2==0&&(l)%2==1)||((7-k)%2==1&&(l)%2==0))?new Color(125 ,135 ,150):new Color(232 ,235 ,239));
								g.fillRect(width*(rot?(7-l):(l))/8,height*(rot?k:(7-k))/8,width/8,height/8);
								prev[k][l]=null;
								System.out.println("helped");
							}
					
				}catch(Exception ef)
				{

				}
			}

		};
		new Timer(100,reload).start();

		addMouseListener(new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){

                    	int j=e.getX()/(width/8);
                    	int i=e.getY()/(height/8);
                    	if(rot)
                    		j=7-j;
                    	else
                    		i=7-i;
                    	System.out.println("["+i+"]["+j+"]");
                    	//uncircle();
                    	//circled=new ArrayList<Loc>();
                    	if(!ready)
                    	{
	                    	if(structure[i][j]!=null){
	                    		ready=true;
	                    		activerow=i;
	                    		activecolumn=j;
		                    	circled=structure[i][j].moves(structure,i,j);
		                    	circle();
		                    	System.out.println("Moves "+circled.size());
		                    }
		                }
		                else
		                {
		                	System.out.println("check"+circled.size());
		                		for(Loc g:circled)
		                			if(g.row==i&&g.column==j){
		                				//System.out.println("moving");
		                				structure[i][j]=structure[activerow][activecolumn];
		                				structure[i][j].moved=true;
		                				structure[activerow][activecolumn]=null;
		                				ready=false;
		                				uncircle();
		                				break;
		                			}
		                			//else
		                			//	System.out.println("  No "+g.row+","+g.column+"  and "+activerow+","+activecolumn);

		                		
		                			ready=false;
		                			uncircle();
		                			//System.out.println("ngori"+circled.size());
		                }


                    }});


	}

	public void paint(Graphics g)
	{

		//start f drawing the boxes
		/*
		if(rot){
			Graphics2D gr=(Graphics2D) g;
			gr.rotate(Math.toRadians(180),width/2,height/2);
		}
		*/
		
		g.setColor(new Color(232 ,235 ,239));
		g.fillRect(0,0,width,height);

		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				if((i%2==0&&j%2==1)||(i%2==1&&j%2==0)){
					g.setColor(new Color(125 ,135 ,150));
					g.fillRect(width*j/8,height*i/8,width/8,height/8);
				}
		setSize(width,height);

		//drawing the pieces
/*
		for(int a=0;a<8;a++)
			for(int b=0;b<8;b++)
				if(structure[a][b]!=null)
					try{
					g.drawImage(structure[a][b].getImage(),b*width/8,(7-a)*height/8,width/8,height/8,null);
				}catch(Exception e)
				{
					System.out.println("Error "+e);
				}
*/

	}

	public void user(String col)
	{
		rot=col.equals("black");
	}

	public void toggle()
	{
		rot=!rot;
		prev=new piece[8][8];
	}


	public void circle()
	{
		Graphics2D g=(Graphics2D) getGraphics();
		for(Loc i:circled)
		{
			int k=i.row;
			int l=i.column;
			g.setColor(new Color(0,139,0));
			g.fillOval(width*(rot?(7-l):(l))/8+width/32,height*(rot?k:(7-k))/8+height/32,width/20,height/20);
		}


	}

	public void uncircle()
	{
		Graphics2D g=(Graphics2D) getGraphics();
		for(Loc i:circled)
		{
			int k=i.row;
			int l=i.column;
			g.setColor((((7-k)%2==0&&(l)%2==1)||((7-k)%2==1&&(l)%2==0))?new Color(125 ,135 ,150):new Color(232 ,235 ,239));
			
			if(structure[k][l]!=null)
				try{
				g.drawImage(structure[k][l].getImage(rot),(rot?(7-l):(l))*width/8,(rot?k:(7-k))*height/8,width/8,height/8,null);
			}catch(Exception e){}
			else
				g.fillRect(width*(rot?(7-l):(l))/8,height*(rot?k:(7-k))/8,width/8,height/8);
		}
		circled=new ArrayList<Loc>();

	}






}



