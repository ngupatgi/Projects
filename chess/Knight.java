import java.util.*;
public class Knight extends piece{


	Knight(String col)
	{
		points=9;
		this.col=col;
		path=(col.equals("white")?"w_knight_png_shadow_1024px.png":"b_knight_png_shadow_1024px.png");
	}

	public ArrayList<Loc> moves(piece[][] structure,int i,int j)
	{
		ArrayList<Loc> all=new ArrayList<Loc>();
		int a,b;
		a=i+1;
		b=j+2;

		if(a>=0&&a<=7&&b>=0&&b<=7)
			if(structure[a][b]==null)
				all.add(new Loc(a,b));
			else if(!structure[a][b].col.equals(structure[i][j].col))
				all.add(new Loc(a,b));



		a=i+2;
		b=j+1;

		if(a>=0&&a<=7&&b>=0&&b<=7)
			if(structure[a][b]==null)
				all.add(new Loc(a,b));
			else if(!structure[a][b].col.equals(structure[i][j].col))
				all.add(new Loc(a,b));			


		a=i-1;
		b=j-2;

		if(a>=0&&a<=7&&b>=0&&b<=7)
			if(structure[a][b]==null)
				all.add(new Loc(a,b));
			else if(!structure[a][b].col.equals(structure[i][j].col))
				all.add(new Loc(a,b));



		a=i-2;
		b=j-1;

		if(a>=0&&a<=7&&b>=0&&b<=7)
			if(structure[a][b]==null)
				all.add(new Loc(a,b));
			else if(!structure[a][b].col.equals(structure[i][j].col))
				all.add(new Loc(a,b));	


		a=i-1;
		b=j+2;

		if(a>=0&&a<=7&&b>=0&&b<=7)
			if(structure[a][b]==null)
				all.add(new Loc(a,b));
			else if(!structure[a][b].col.equals(structure[i][j].col))
				all.add(new Loc(a,b));



		a=i+2;
		b=j-1;

		if(a>=0&&a<=7&&b>=0&&b<=7)
			if(structure[a][b]==null)
				all.add(new Loc(a,b));
			else if(!structure[a][b].col.equals(structure[i][j].col))
				all.add(new Loc(a,b));			


		a=i+1;
		b=j-2;

		if(a>=0&&a<=7&&b>=0&&b<=7)
			if(structure[a][b]==null)
				all.add(new Loc(a,b));
			else if(!structure[a][b].col.equals(structure[i][j].col))
				all.add(new Loc(a,b));



		a=i-2;
		b=j+1;

		if(a>=0&&a<=7&&b>=0&&b<=7)
			if(structure[a][b]==null)
				all.add(new Loc(a,b));
			else if(!structure[a][b].col.equals(structure[i][j].col))
				all.add(new Loc(a,b));	




		return all;
	}
}