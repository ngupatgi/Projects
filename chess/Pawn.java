import java.util.*;
public class Pawn extends piece{


	Pawn(String col)
	{
		points=9;
		this.col=col;
		path=(col.equals("white")?"w_pawn_png_shadow_1024px.png":"b_pawn_png_shadow_1024px.png");
	}

	public ArrayList<Loc> moves(piece[][] structure,int i,int j)
	{
		ArrayList<Loc> all=new ArrayList<Loc>();
		if(structure[i][j].col.equals("white"))
		{
			if(structure[i+1][j]==null)
			{
				all.add(new Loc(i+1,j));
				if(structure[i+2][j]==null&&!structure[i][j].moved)
					all.add(new Loc(i+2,j));

			}

			if(j<7&&structure[i+1][j+1]!=null&&!structure[i+1][j+1].col.equals(structure[i][j].col))
				all.add(new Loc(i+1,j+1));
			if(j>0&&structure[i+1][j-1]!=null&&!structure[i+1][j-1].col.equals(structure[i][j].col))
				all.add(new Loc(i+1,j-1));

		}
		else if(structure[i][j].col.equals("black"))
		{
			if(structure[i-1][j]==null)
			{
				all.add(new Loc(i-1,j));
				if(structure[i-2][j]==null&&!structure[i][j].moved)
					all.add(new Loc(i-2,j));

			}

			if(j<7&&structure[i-1][j+1]!=null&&!structure[i-1][j+1].col.equals(structure[i][j].col))
				all.add(new Loc(i-1,j+1));
			if(j>0&&structure[i-1][j-1]!=null&&!structure[i-1][j-1].col.equals(structure[i][j].col))
				all.add(new Loc(i-1,j-1));
		}
		return all;
		
	}
}