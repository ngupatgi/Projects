import java.util.*;

public class encryption{

private String key;

encryption(String key)
{
	this.key=key;
}

public byte[] encrypt(byte[] input)
{
	byte[] fine=new byte[input.length];
	for(int i=0;i<input.length;i+=key.length())
	{
		byte[] tmp=scatter(connection.subbytes(i,i+key.length(),input));
		for(int j=0;j<tmp.length;j++)
			fine[i+j]=tmp[j];
	}
return fine;
}


public byte[] decrypt(byte[] input)
{
	byte[] fine=new byte[input.length];
	for(int i=0;i<input.length;i+=key.length())
	{
		byte[] tmp=assemble(connection.subbytes(i,i+key.length(),input));
		for(int j=0;j<tmp.length;j++)
			fine[i+j]=tmp[j];
	}
return fine;
}


private byte[] scatter(byte[] raw)
{
node[] its=new node[raw.length];
for(int i=0;i<raw.length;i++)
{
	its[i]=new node();
	its[i].index=key.charAt(i);
	its[i].value=raw[i];
	its[i].pos=i;
}

sort(its);
return bytesfrom(its);
}

private byte[] assemble(byte[] raw)
{
node[] its=new node[raw.length];
for(int i=0;i<raw.length;i++)
{
	its[i]=new node();
	its[i].index=key.charAt(i);
}
sort(its);
for(int i=0;i<raw.length;i++)
	its[i].value=raw[i];

byte[] ans=new byte[raw.length];

ArrayList<node> arr=new ArrayList<node>(Arrays.asList(its));

for(int i=0;i<raw.length;i++)
{
	Boolean found=false;
	for(int j=0;j<arr.size()&&!found;j++)
		if(arr.get(j).index==key.charAt(i))
		{
			ans[i]=arr.get(j).value;
			arr.remove(j);
			arr.trimToSize();
			found=true;
		}
}

return ans;
}


public static byte[] bytesfrom(node [] items)
{
	byte[] ans=new byte[items.length];
	for(int i=0;i<items.length;i++)
		ans[i]=items[i].value;
	return ans;
}

private static void sort(node[] items)
{
	for(int i=0;i<items.length-1;i++){
		for(int j=i+1;j<items.length;j++)
			if(items[j].index<items[i].index)
				swap(i,j,items);
			else if(items[j].index==items[i].index&&items[j].pos<items[i].pos)
				swap(i,j,items);
	}
	

}

private static void swap(int x,int y,node[] items)
{
	node tmp=items[x];
	items[x]=items[y];
	items[y]=tmp;
}





}


class node{
	char index;
	byte value;
	int pos=0;
}