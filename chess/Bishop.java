import java.util.*;
public class Bishop extends piece{


	Bishop(String col)
	{
		points=9;
		this.col=col;
		path=(col.equals("white")?"w_bishop_png_shadow_1024px.png":"b_bishop_png_shadow_1024px.png");
	}

	public ArrayList<Loc> moves(piece[][] structure,int i,int j)
	{
		ArrayList<Loc> al=new ArrayList<Loc>();

		int a,b;
		a=i;
		b=j;
		while(a<7&&b<7)
		{
			a++;
			b++;
			if(structure[a][b]==null)
				al.add(new Loc(a,b));
			else
			{
				if(!structure[a][b].col.equals(structure[i][j].col))
				al.add(new Loc(a,b));
				break;
			}
		}

		a=i;
		b=j;
		while(a<7&&b>0)
		{
			a++;
			b--;
			if(structure[a][b]==null)
				al.add(new Loc(a,b));
			else
			{
				if(!structure[a][b].col.equals(structure[i][j].col))
				al.add(new Loc(a,b));
				break;
			}
		}

		a=i;
		b=j;

		while(a>0&&b>0)
		{
			a--;
			b--;
			if(structure[a][b]==null)
				al.add(new Loc(a,b));
			else
			{
				if(!structure[a][b].col.equals(structure[i][j].col))
				al.add(new Loc(a,b));
				break;
			}
		}

		a=i;
		b=j;

		while(a>0&&b<7)
		{
			a--;
			b++;
			if(structure[a][b]==null)
				al.add(new Loc(a,b));
			else
			{
				if(!structure[a][b].col.equals(structure[i][j].col))
				al.add(new Loc(a,b));
				break;
			}
		}



		return al;
	}
}