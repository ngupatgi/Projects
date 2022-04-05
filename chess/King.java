import java.util.*;
public class King extends piece{


	King(String col)
	{
		points=9;
		this.col=col;
		path=(col.equals("white")?"w_king_png_shadow_1024px.png":"b_king_png_shadow_1024px.png");
	}


	public  ArrayList<Loc> moves(piece[][] structure,int i,int j)
	{
		ArrayList<Loc> all=new ArrayList<Loc>();

		int a,b;
		a=i+1;
		b=j+0;

		if(a>=0&&a<=7&&b>=0&&b<=7)
			if(structure[a][b]==null)
				all.add(new Loc(a,b));
			else if(!structure[a][b].col.equals(structure[i][j].col))
				all.add(new Loc(a,b));



		a=i+0;
		b=j+1;

		if(a>=0&&a<=7&&b>=0&&b<=7)
			if(structure[a][b]==null)
				all.add(new Loc(a,b));
			else if(!structure[a][b].col.equals(structure[i][j].col))
				all.add(new Loc(a,b));			


		a=i-1;
		b=j-1;

		if(a>=0&&a<=7&&b>=0&&b<=7)
			if(structure[a][b]==null)
				all.add(new Loc(a,b));
			else if(!structure[a][b].col.equals(structure[i][j].col))
				all.add(new Loc(a,b));



		a=i-0;
		b=j-1;

		if(a>=0&&a<=7&&b>=0&&b<=7)
			if(structure[a][b]==null)
				all.add(new Loc(a,b));
			else if(!structure[a][b].col.equals(structure[i][j].col))
				all.add(new Loc(a,b));	


		a=i-1;
		b=j+0;

		if(a>=0&&a<=7&&b>=0&&b<=7)
			if(structure[a][b]==null)
				all.add(new Loc(a,b));
			else if(!structure[a][b].col.equals(structure[i][j].col))
				all.add(new Loc(a,b));



		a=i+1;
		b=j+1;

		if(a>=0&&a<=7&&b>=0&&b<=7)
			if(structure[a][b]==null)
				all.add(new Loc(a,b));
			else if(!structure[a][b].col.equals(structure[i][j].col))
				all.add(new Loc(a,b));			

		

		a=i-1;
		b=j+1;

		if(a>=0&&a<=7&&b>=0&&b<=7)
			if(structure[a][b]==null)
				all.add(new Loc(a,b));
			else if(!structure[a][b].col.equals(structure[i][j].col))
				all.add(new Loc(a,b));



		a=i+1;
		b=j-1;

		if(a>=0&&a<=7&&b>=0&&b<=7)
			if(structure[a][b]==null)
				all.add(new Loc(a,b));
			else if(!structure[a][b].col.equals(structure[i][j].col))
				all.add(new Loc(a,b));


		return all;
	}
}