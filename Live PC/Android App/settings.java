package com.LivePC;

import android.database.*;
import android.util.*;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.os.Bundle;
import android.widget.*;
import android.Manifest;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.os.*;
import android.text.*;
import android.net.Uri;
import android.database.sqlite.*;
import java.util.*;
import android.util.*;
import java.lang.*;
import android.database.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.telephony.SmsManager;
import android.content.Context;
import android.graphics.*;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.*;
import android.app.ActivityManager;
import android.app.ActivityManager.*;
import java.text.SimpleDateFormat;

import java.time.*;


public class settings{
	public int screen_refresh;
	public int scroll_rate;
	public boolean touch_clicks;
	private Activity act;
	SimpleDateFormat mdate=new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
	settings(Activity act)
	{
		this.act=act;
		screen_refresh=1;
		scroll_rate=1;
		touch_clicks=false;
		try{
				SQLiteDatabase db=act.openOrCreateDatabase("Livepc",act.MODE_PRIVATE, null);
				db.execSQL("create table if not exists settings(num int not null primary key,refresh int,scroll int,touch text)");
				db.execSQL("create table if not exists log(num int not null primary key auto_increment,content text,start text,stop text)");
				db.execSQL("insert into settings values(1,"+screen_refresh+","+scroll_rate+",'"+Boolean.toString(touch_clicks)+"')");
		}catch(Exception e)
		{
			//MainActivity.disp("creating"+e.toString());
		}

		try
		{
		    SQLiteDatabase db=act.openOrCreateDatabase("Livepc",act.MODE_PRIVATE, null);
		    Cursor c=db.rawQuery("select refresh,scroll,touch from settings",null);
		    c.moveToFirst();
		    screen_refresh=Integer.valueOf(c.getString(0));
		    scroll_rate=Integer.valueOf(c.getString(1));
		    touch_clicks=Boolean.valueOf(c.getString(2));
		}catch (Exception e)
		{
			//MainActivity.disp("fetching"+e.toString());
		}
	}

	public void update()
	{
		try{
		SQLiteDatabase db=act.openOrCreateDatabase("Livepc",act.MODE_PRIVATE, null);
		db.execSQL("update settings set refresh='"+screen_refresh+"',scroll='"+scroll_rate+"',touch='"+Boolean.toString(touch_clicks)+"' ");
		}catch(Exception e)
		{
			MainActivity.disp(" Updating error : "+e.toString());
		}

	}

	public int logid=0;
	public void log(String content){

		try{
		SQLiteDatabase db=act.openOrCreateDatabase("Livepc",act.MODE_PRIVATE, null);
		db.execSQL("insert into log (content,start) values('start','"+mdate.format(new Date())+"')");
		Cursor c=db.rawQuery("select max(num) from log",null);
		    c.moveToFirst();
		logid=Integer.valueOf(c.getString(0));
		}catch(Exception e)
		{
			//MainActivity.disp(" Updating error : "+e.toString());
		}
	}

	public void updateLog()
	{
	
		try{
		SQLiteDatabase db=act.openOrCreateDatabase("Livepc",act.MODE_PRIVATE, null);
		db.execSQL("update log set stop='"+mdate.format(new Date())+"' where num='"+logid+"'");
		}catch(Exception e)
		{
			//MainActivity.disp(" Updating error : "+e.toString());
		}

	
	}

	public String[] getLog()
	{
		String[] fine=new String[0];
		try
		{
		    SQLiteDatabase db=act.openOrCreateDatabase("Livepc",act.MODE_PRIVATE, null);
		    Cursor c=db.rawQuery("select count(content) from log",null);
		    c.moveToFirst();
		    int num=Integer.valueOf(c.getString(0));
		    fine=new String[num];

		    c=db.rawQuery("select start,content,stop from log order by num desc",null);
		    int counter=0;
		    while(c.moveToNext())
		    {
		    	fine[counter]=c.getString(0)+" - "+c.getString(1)+" - "+c.getString(2);
		    	counter++;
		    }
		    
		}catch (Exception e)
		{
			//MainActivity.disp("fetching"+e.toString());
		}

		return fine;
	}




}