package com.LivePC;

import android.graphics.drawable.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.SeekBar;
import android.app.Activity;
import android.os.Bundle;
import android.graphics.*;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import  android.widget.ImageView.ScaleType;
import android.widget.PopupWindow;
import android.database.*;
import android.content.Intent;
//import java.sql.Date;


import java.util.Date;

import static android.graphics.Color.rgb;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.text.SimpleDateFormat;



public class MainActivity extends Activity
{
    public static Handler moh=new Handler();
    public static connection con=new connection();
    public static settings mysettings=null;//new settings();
    public static LinearLayout l=null;
    public static LinearLayout sess=null;
    public static DisplayMetrics metrics = new DisplayMetrics();
    public static Activity act;
    public static Button go=null;
    public static int screen_frequency=1;
    private static int times=5;
    SimpleDateFormat mdate=new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act=this;
        mysettings=new settings(this);
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        con.myrole=role.CLIENT;
        go=new Button(this);

        con.connemode=mode.TCP;
        //con.urladdress="192.168.43.245";
        //con.securitykey="pats";
        loadSettings(mysettings);
        //waiting();

        if(con.pow)
            session();
        else
            home();


 
    }

    @Override
    public void onBackPressed()
    {
        if(location==1)
            this.finishAffinity();

        else if(location==2)
        {
            //home();
            act.setContentView(l);
            location=1;
            a=10;
            try {
                con.disconnect();
            }catch (Exception e)
            {

            }
        }
        else if(location==3)
        {
            //home();
            act.setContentView(l);
            location=1;
            a=10;
        }
    }

    private static void loadSettings(settings s)
    {
        screen_frequency=s.screen_refresh;
        times=s.scroll_rate;
    }


    public static int location=0;
    public static void home()
    {
        location=1;
        l=new LinearLayout(act);
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                act.setContentView(l);
                act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
            }
        });


        l.setBackgroundColor(rgb(39,144,255));
        l.setOrientation(LinearLayout.VERTICAL);

        final Handler h=new Handler();
        final TextView t5=new TextView(act);
        t5.setText("LivePC");
        t5.setTextColor(rgb(52, 58, 99));
        t5.setX(0);
        t5.setY(15);
        t5.setGravity(Gravity.CENTER);
        t5.setTextSize(25);





        l.setBackgroundColor(rgb(181,181,181));



        final LinearLayout login=new LinearLayout(act);


        GradientDrawable shape=new GradientDrawable();
        shape.setCornerRadius(30);
        shape.setColor(rgb(255,255,255));
        login.setBackground(shape);



        login.setX(30);
        login.setY(50);






        h.post(new Runnable() {
            @Override
            public void run() {
                l.addView(t5);
                t5.getLayoutParams().width=metrics.widthPixels;
                t5.getLayoutParams().height=85;
            }
        });

        h.post(new Runnable() {
            @Override
            public void run() {
                l.addView(login);
                login.getLayoutParams().width=metrics.widthPixels-60;
                login.getLayoutParams().height=metrics.heightPixels*4/5;
            }
        });

        


        final TextView hd=new TextView(act);
        hd.setText("CLIENT CONNECTION");
        hd.setTextColor(rgb(52,58,164));
        hd.setGravity(Gravity.CENTER);
        hd.setX(0);
        hd.setY(10);
        hd.setTextSize(18);



        final Button b1=new Button(act);
        b1.setText("INTERNET MODE");
        b1.setGravity(Gravity.CENTER);

        b1.setBackgroundColor(rgb(139,139,100));
        b1.setTextColor(Color.WHITE);
        b1.setX(0);
        b1.setY(50);

        final Button b2=new Button(act);
        b2.setText("TCP (LAN or WLAN) MODE");
        b2.setGravity(Gravity.CENTER);

        b2.setBackgroundColor(rgb(139,139,100));
        b2.setTextColor(Color.WHITE);
        //int size=Math.round((metrics.widthPixels - 60)/2);
        //b2.setX(size);
        b2.setY(-50);



        final LinearLayout common=new LinearLayout(act);
        //common.setBackgroundColor(rgb(39,144,255));
        common.setX(0);

        go.setTranslationX(150);
        go.setText("CONNECT");
        go.setTextColor(Color.WHITE);
        go.setBackgroundColor(rgb(10,155,215));
        go.setTextSize(17);
        go.setY(50);

        final Button scan=new Button(act);
        scan.setTranslationX(150);
        scan.setText("Scan Connect");
        scan.setTextColor(Color.WHITE);
        scan.setBackgroundColor(rgb(52, 58, 99));
        scan.setTextSize(17);


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             
               try {

                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

                act.startActivityForResult(intent, 0);

            } catch (Exception e) {

                Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                act.startActivity(marketIntent);

            }


            }
        });


        h.post(new Runnable() {
            @Override
            public void run() {
                login.addView(hd);
                login.addView(b1);
                login.addView(b2);
                login.addView(common);
                login.addView(scan);
                login.addView(go);
                hd.getLayoutParams().width = metrics.widthPixels-60;
                hd.getLayoutParams().height=70;
                b1.getLayoutParams().width=(metrics.widthPixels - 160)/2;
                b1.getLayoutParams().height=100;
                b1.setTranslationX(10);
                b2.getLayoutParams().width=(metrics.widthPixels - 60)/2;
                b2.getLayoutParams().height=100;
                b2.setTranslationX((metrics.widthPixels - 80)/2);

                login.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams jed= (LinearLayout.LayoutParams) common.getLayoutParams();
                jed.width=(metrics.widthPixels - 60);
                jed.height=metrics.heightPixels/2-200;
                common.setOrientation(LinearLayout.VERTICAL);

                go.getLayoutParams().width=metrics.widthPixels-360;
                scan.getLayoutParams().width=metrics.widthPixels-360;


            }
        });





        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                con.connemode=mode.INTERNET;
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        common.removeAllViews();
                        common.addView(config());
                        b2.setBackgroundColor(rgb(139,139,100));
                        b1.setBackgroundColor(rgb(139,139,139));

                    }
                });

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                con.connemode=mode.TCP;
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        common.removeAllViews();
                        common.addView(config());
                        b1.setBackgroundColor(rgb(139,139,100));
                        b2.setBackgroundColor(rgb(139,139,139));
                    }
                });

            }
        });

        moh.post(new Runnable() {
            @Override
            public void run() {

                l.getLayoutParams().width=metrics.widthPixels;
                l.getLayoutParams().height=metrics.heightPixels;
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {           
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                String[] flag=contents.split(":");
                if(flag[0].equals("Livepc"))
                {
                    con.connemode=(mode) mode.valueOf(flag[1]);
                    if(con.connemode==mode.TCP)
                    {
                        con.urladdress=flag[2];
                        con.securitykey=flag[3];
                        waiting();
                    }
                    else if(con.connemode==mode.INTERNET)
                    {
                        con.urladdress="https://livepc.pegien.co.ke/";
                        con.connection_id=flag[2];
                        con.username=flag[3];
                        con.securitykey=flag[4];
                        waiting();
                    }
                }
                
            }
            if(resultCode == RESULT_CANCELED){
                //handle cancel
            }
        }
    }


    public static double thelength=0.0;
    public static LinearLayout config()
    {
        final GradientDrawable mshape = new GradientDrawable();
        // Set the gradient drawable background to transparent
        mshape.setColor(Color.parseColor("#00ffffff"));

        // Set a border for the gradient drawable
        mshape.setStroke(2,rgb(52,58,99));
        mshape.setCornerRadius(10);

        final Handler h=new Handler();

        final LinearLayout mine=new LinearLayout(act);
        final EditText cid=new EditText(act);
        final EditText email=new EditText(act);
        final EditText password=new EditText(act);
        final CheckBox sh=new CheckBox(act);
        final EditText ip=new EditText(act);


        if(con.connemode==mode.INTERNET)
        {

            cid.setHint("ENTER CONNECTION_ID");
            cid.setGravity(Gravity.CENTER);
            cid.setTranslationX(50);
            mine.addView(cid);
            cid.setY(20);
            //cid.setBackgroundColor(rgb(190,190,190));
            cid.setText(con.connection_id);
            cid.setBackground(mshape);


            email.setHint("ENTER EMAIL");
            email.setGravity(Gravity.CENTER);
            email.setY(60);
            email.setTranslationX(50);
            mine.addView(email);
            email.setBackground(mshape);
            email.setText(con.username);


            password.setHint("Enter Security Key");
            password.setGravity(Gravity.CENTER);
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            password.setY(100);
            password.setTranslationX(50);
            mine.addView(password);
            password.setBackground(mshape);
            password.setText(con.securitykey);


            sh.setText("Show SecurityKey");
            sh.setGravity(Gravity.CENTER);
            sh.setTranslationX(100);
            mine.addView(sh);
            sh.setY(110);

            sh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                    {
                        password.setInputType(InputType.TYPE_CLASS_TEXT);
                    }
                    else
                    {
                        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    }
                }
            });



            h.post(new Runnable() {
                @Override
                public void run() {
                    cid.getLayoutParams().width=(metrics.widthPixels - 160);
                    password.getLayoutParams().width=(metrics.widthPixels - 160);
                    email.getLayoutParams().width=(metrics.widthPixels - 160);
                    sh.getLayoutParams().width=(metrics.widthPixels - 260);

                    // cid.getLayoutParams().height=40;
                    // email.getLayoutParams().height=40;
                    // password.getLayoutParams().height=40;

                }
            });
        }
        else if(con.connemode==mode.TCP)
        {

            ip.setHint("Enter Target Ip Address");
            ip.setGravity(Gravity.CENTER);
            ip.setTranslationX(50);
            mine.addView(ip);
            ip.setY(20);
           // ip.setBackgroundColor(rgb(190,190,190));
            ip.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            ip.setBackground(mshape);



            password.setHint("Enter Security Key");
            password.setGravity(Gravity.CENTER);
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            password.setY(80);
            password.setTranslationX(50);
            mine.addView(password);
            //password.setBackgroundColor(rgb(190,190,190));
            password.setText(con.securitykey);
            password.setBackground(mshape);

            sh.setText("Show SecurityKey");
            sh.setGravity(Gravity.CENTER);
            sh.setTranslationX(90);
            mine.addView(sh);
            sh.setY(110);

            sh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                    {
                        password.setInputType(InputType.TYPE_CLASS_TEXT);
                    }
                    else
                    {
                        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    }
                }
            });



            h.post(new Runnable() {
                @Override
                public void run() {
                    ip.getLayoutParams().width=(metrics.widthPixels - 160);
                    password.getLayoutParams().width=(metrics.widthPixels - 160);
                    sh.getLayoutParams().width=(metrics.widthPixels - 260);

                    // ip.getLayoutParams().height=40;
                    // email.getLayoutParams().height=40;
                    // password.getLayoutParams().height=40;

                }
            });
        }








        h.post(new Runnable() {
            @Override
            public void run() {
                mine.setOrientation(LinearLayout.VERTICAL);
                mine.getLayoutParams().width=metrics.widthPixels-60;
                mine.getLayoutParams().height=metrics.heightPixels*2/3;
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                con.securitykey=password.getText().toString();
                boolean check=false;
                if(con.connemode==mode.TCP)
                {
                    con.urladdress=ip.getText().toString();
                    if(con.urladdress.length()>5)
                        check=true;
                }
                else
                {
                    //con.urladdress="http://192.168.43.229/home/LivePC/";
                    con.urladdress="https://livepc.pegien.co.ke/";

                    con.username=email.getText().toString();
                    con.connection_id=cid.getText().toString();
                    if(con.username.length()>1&&con.connection_id.length()>5)
                        check=true;
                }
                if(con.securitykey.length()>1&&check)
                    waiting();
                else
                    Toast.makeText(act,"Some Details Missing", Toast.LENGTH_SHORT).show();
            }
        });

        return mine;
    }
    public static EditText pr=null;
    public static void waiting() {
        location = 2;
        final Handler h = new Handler();
        final LinearLayout rel = new LinearLayout(act);
        //rel.setBackgroundColor();

        pr = new EditText(act);
        pr.setText("Connecting");
        rel.addView(pr);
        pr.setGravity(Gravity.CENTER);
        pr.setTextSize(22);
        pr.setBackgroundColor(Color.RED);
        pr.setTextColor(Color.CYAN);
        pr.setEnabled(false);

        h.post(new Runnable() {
            @Override
            public void run() {
                rel.setOrientation(LinearLayout.VERTICAL);
                act.setContentView(rel);
                rel.getLayoutParams().width = metrics.widthPixels;
                rel.getLayoutParams().height = metrics.heightPixels;

                pr.getLayoutParams().width = metrics.widthPixels - 120;
                pr.getLayoutParams().height = 140;
                pr.setTranslationX(60);
                pr.setTranslationY(metrics.heightPixels / 2 - 60);

            }
        });


        Thread anim = new Thread() {
            public void run() {
                a = 0;
                while (a <= 3) {
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            String mis = ms;
                            for (int i = 0; i < a; i++)
                                mis += ". ";
                            pr.setText(mis);
                        }
                    });

                    try {
                        Thread.sleep(700);
                    } catch (Exception e) {

                    }
                    a++;
                    if (a == 4)
                        a = 0;
                    if(con.pow) {
                        a=10;
                        mysettings.log(" Accessed Computer "+con.client+" Via "+con.connemode.toString());
                        session();
                    }
                    else if(con.foul)
                    {
                        a=10;
                        disp("Security key Incorrect");
                        act.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                act.setContentView(l);
                            }
                        });
                    }
                }
            }
        };
        anim.start();
        Thread com=new Thread()
        {
            public void run() {
                if (con.connect()) {
                    con.sync_data();
                    ms="Checking Security Key ";
                    String greet="Hello";
                    con.send_data(greet.getBytes());
                    disp("sending data");

                } else {
                    disp(""+con.log);
                    con.disconnect();
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            act.setContentView(l);
                        }
                    });
                }
            }};
        com.start();
    }
    //public static Handler moh=null;
    public static String ms="Trying to Connecting ";
    public static int a=0;
    public static int cursorx=0;
    public static int cursory=0;
    public static long lastss=0l;
    //public static Bitmap bitmap=null;
    public static void disp(String text)
    {
        final String str=text;
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(act, str, Toast.LENGTH_SHORT).show();
            }
        });

    }
   
    public static boolean potrait=false;
    @SuppressLint("ClickableViewAccessibility")
    public static void session()
    {

        disp("Access Successful");
        con.getClientInfo();
        sess=new LinearLayout(act);
        sess.setBackgroundColor(rgb(64,64,64));

        act.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    act.setContentView(sess);
                    //act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                    sess.getLayoutParams().width=metrics.widthPixels;
                    sess.getLayoutParams().height=metrics.heightPixels;

                }catch (Exception e)
                {
                    disp(e.toString()+"very top");
                }
            }
        });

        sess.setOrientation(LinearLayout.VERTICAL);
        sess.setBackgroundColor(rgb(64,64,64));

        final ImageView img=new ImageView(act);
        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int touchx=(int) event.getX();
                int touchy=(int) event.getY();
                double snx=(touchx/1.00)/(img.getWidth()/1.00);
                double sny=(touchy/1.00)/(img.getHeight()/1.00);
                
                if(mysettings.touch_clicks){
                    tap(snx,sny);
                    return false;
                }
                else
                    mouse_move(snx,sny);
                return true;
            }
        });



        final TextView tv=new TextView(act);
        // sess.addView(tv);
        tv.setGravity(Gravity.CENTER);

        try{
        //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT) ;//.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //setLayoutParams(params);
       final LinearLayout left=new LinearLayout(act);
       //left.setMaxWidth(100);
       left.setBackgroundColor(rgb(52,58,64));

       //left.setLayoutParams(params);


       final Button lclick=new Button(act);
       lclick.setText("L click");
       left.addView(lclick);
       lclick.setBackgroundColor(rgb(248, 249, 250));
       lclick.setTextColor(rgb(52,58,64));


       final ImageView osk=new ImageView(act);
       osk.setImageResource(R.drawable.keyboard);
      // osk.setBackgroundColor(Color.WHITE);
       osk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        Thread req=new Thread()
        {
            public void run()
            {
                String dat="osk";
                con.send_data(dat.getBytes());
            }
        };
        req.start();
    }
        });

       final int tot=(int)  Math.round((metrics.widthPixels-20));



        act.runOnUiThread(new Runnable() {

            public void run() {
               sess.addView(left);
               left.addView(osk);
                left.setLayoutParams(new LinearLayout.LayoutParams(metrics.widthPixels-20,(int) Math.round((metrics.heightPixels - thelength) / 2 + metrics.heightPixels / 2.0)-20));
                left.setTranslationX(10);
                left.setTranslationY(10);
                left.getLayoutParams().width=metrics.widthPixels-20;
                left.getLayoutParams().height=100;//(int) Math.round((metrics.heightPixels - thelength) / 2 + metrics.heightPixels / 2.0)-20;
                left.setOrientation(LinearLayout.VERTICAL);
                //left.setBackgroundColor(Color.WHITE);

                //ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //lclick.setLayoutParams(params);
                lclick.getLayoutParams().height=100;//left.getHeight();
                lclick.getLayoutParams().width=(int)  Math.round((metrics.widthPixels-20)/3.0);
                //lclick.getLayoutParams().setweight(0.5);


                osk.getLayoutParams().height=100;//left.getHeight();
                osk.getLayoutParams().width=100;
               osk.setTranslationX((float) tot*3/3-200);
               osk.setTranslationY(-100);

            }});




            lclick.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            mouseclick();
            break;
        case MotionEvent.ACTION_UP:
            mouserelease();
            break;
        }
        return false;
    }
});
    }catch (Exception e)
    {
        disp(e.toString()+"Right");
    }

        



        act.runOnUiThread(new Runnable() {

            public void run() {
                try {
                    sess.addView(img);
                    if(potrait) {
                        img.setTranslationX(5);
                        img.setTranslationY(10);
                        img.getLayoutParams().width = metrics.widthPixels - 20;
                        //img.getLayoutParams().height=metrics.heightPixels-100;
                        // img
                    }
                    else
                    {
                        thelength=metrics.heightPixels - 200;
                        img.getLayoutParams().width =(int)thelength;
                        //img.setTranslationY();//(float) ((metrics.widthPixels - 90)/2.0));
                        //img.setTranslationY((float)(metrics.heightPixels-thelength)/2);
                        //float transx=img.getWidth()/-2+10;// (metrics.widthPixels)/-2+10;
                        img.setTranslationX(0);//(float) ((metrics.widthPixels - 90)/-4.0));
                        img.setPivotY(0);
                        img.setPivotX((metrics.widthPixels - 10));
                    }
                   // tv.getLayoutParams().width=metrics.widthPixels;
                    //tv.getLayoutParams().height=50;




                }catch (Exception e)
                {
                    disp(e.toString()+"2 image");
                }
            }
        });

        Thread req=new Thread()
        {
            public void run()
            {
                String ss="screenshot";
                con.send_data(ss.getBytes());
                try
                {
                    Thread.sleep(1000/screen_frequency);
                }catch (Exception e)
                {

                }

            }
        };
        req.start();

        Thread fesh=new Thread()
        {
            public void run()
            {
                while(con.pow)
                {

                    try
                    {
                        Thread.sleep(200/screen_frequency);
                    }catch (Exception e)
                    {

                    }
                    if(con.loadss)
                    {
                        lastss=new Date().getTime();
                        Thread req=new Thread()
                        {
                            public void run()
                            {
                                String ss="screenshot";
                                con.send_data(ss.getBytes());


                            }
                        };
                        req.start();

                        try {
                            final Bitmap bitmap = BitmapFactory.decodeByteArray(con.sscurrect.whole, 0, con.sscurrect.whole.length);
                            final Bitmap tmpBitmap=Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.RGB_565);
                            final Canvas c=new Canvas(tmpBitmap);
                            Paint p=new Paint(Paint.ANTI_ALIAS_FLAG);
                            //c.translate(img.getWidth()/2.0,bitmap.getHeight()/2.0);
                            //c.rotate(90,);
                            c.drawBitmap(bitmap,0,0,p);
                            p.setColor(Color.BLACK);
                            c.drawCircle(cursorx,cursory,10,p);
                            p.setColor(Color.WHITE);
                            c.drawCircle(cursorx,cursory,7,p);
                            p.setColor(Color.TRANSPARENT);
                            c.drawCircle(cursorx,cursory,5,p);
                            //c.translate(img.getWidth()/-2.0,bitmap.getHeight()/-2.0);


                            act.runOnUiThread(new Runnable() {
                                public void run() {
                                    img.setImageBitmap(tmpBitmap);
                                    con.loadss = false;
                                    try{
                                        img.getLayoutParams().width = (int) thelength;
                                        int kim = (int) Math.round(img.getWidth() / (bitmap.getWidth() * 1.00) * bitmap.getHeight());
                                        img.getLayoutParams().height = kim;//(int)Math.round(img.getWidth()/(bitmap.getWidth()*1.00)*bitmap.getHeight());
                                        while (kim > (metrics.widthPixels - 20)) {
                                            thelength--;
                                            // disp("subtracting");

                                            img.getLayoutParams().width = (int) Math.round(thelength);
                                            kim = (int) Math.round(thelength / (bitmap.getWidth() * 1.00) * bitmap.getHeight());
                                            img.getLayoutParams().height = kim;
                                        }


                                        img.setTranslationY((float) (-100+(metrics.heightPixels - thelength) / 2 + metrics.heightPixels / 2));


                                    }catch(Exception e)
                                    {
                                        disp(e.toString());
                                    }
                                    if(!potrait)
                                    {
                                        img.setRotation(90);

                                    }
                                    tv.setText("total " + ((double) (con.sscurrect.whole.length / 1000.0)) + "kbs buffer"+(con.receiveBuffer.length/1000.0)+"kbs");
                                }
                            });
                        }catch (Exception e)
                        {
                            disp(e.toString()+"main image");
                        }
                    }
                    /*
                    else
                    {
                        if((new Date().getTime())-lastss>(5000))
                        {
                            Thread req=new Thread()
                            {
                                public void run()
                                {
                                    String ss="screenshot";
                                    con.send_data(ss.getBytes());
                                }
                            };
                            req.start();
                        }
                    }
                    */

                }
            }
        };
        fesh.start();


try {
    final LinearLayout right = new LinearLayout(act.getApplicationContext());
    // sess.addView(right);
    //right.setLayoutParams(params);

    final int tot=(int)  Math.round((metrics.widthPixels-20));
    final Button rclick = new Button(act);
    rclick.setText("R click");
    right.addView(rclick);
    rclick.setBackgroundColor(rgb(248, 249, 250));
    rclick.setTextColor(rgb(52,58,64));
    //right.setTColor(rgb(39,144,255));

    final ImageView settings=new ImageView(act);
    settings.setImageResource(R.drawable.settings);
   // settings.setTextColor(Color.WHITE);
    //settings.setBackgroundColor(rgb(39,144,255));
    //settings.setOnClickListener(new View.OnClickListener())
    settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupWindow popUp = new PopupWindow(act);
                final LinearLayout lsettings=new LinearLayout(act);

                GradientDrawable shape=new GradientDrawable();
                shape.setCornerRadius(30);
                shape.setColor(rgb(248, 249, 250));
                lsettings.setBackground(shape);
                lsettings.setOrientation(LinearLayout.VERTICAL);


                final TextView hid=new TextView(act);
                hid.setText("Settings");
                hid.setGravity(Gravity.CENTER);
                hid.setTextSize(20);

                final Button cancel=new Button(act);
                cancel.setText("cancel");
                cancel.setBackgroundColor(Color.RED);
                cancel.setTextColor(Color.WHITE);
                cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popUp.dismiss();


                }});


                final CheckBox c=new CheckBox(act);
                c.setText("Touch Performs Click");
                c.setGravity(Gravity.CENTER);
                c.setChecked(mysettings.touch_clicks);

                final TextView lfres=new TextView(act);
                lfres.setText("Screen Refresh rate per Second "+screen_frequency); 
                lfres.setGravity(Gravity.CENTER);

                final SeekBar fres=new SeekBar(act);
                fres.setProgress((int)Math.round(Math.sqrt(screen_frequency-1.00)));
                
                SeekBar.OnSeekBarChangeListener abc = new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    //Executed when progress is changed
                    //System.out.println(progress);
                    int progres=1+(progress*progress);

                    lfres.setText("Screen Refresh rate per Second "+progres);

                }
                };
                fres.setOnSeekBarChangeListener(abc);


                final TextView lscroller=new TextView(act);
                lscroller.setText("Mouse Scroll Sensitivity "+times); 
                lscroller.setGravity(Gravity.CENTER);

                final SeekBar scroller=new SeekBar(act);
                scroller.setProgress(times);//(int)Math.round(Math.sqrt(screen_frequency-1.00)));
                
                SeekBar.OnSeekBarChangeListener abd = new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    //Executed when progress is changed
                    //System.out.println(progress);
                    int progres=progress;//1+(progress*progress);

                    lscroller.setText("Mouse Scroll Sensitivity "+progres);

                }
                };
                scroller.setOnSeekBarChangeListener(abd);


                final Button b=new Button(act);
                b.setText("Save Changes");
                b.setBackgroundColor(rgb(52,58,64));
                b.setTextColor(Color.WHITE);
                b.setTextSize(18);
                b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mysettings.scroll_rate=scroller.getProgress();
                    mysettings.screen_refresh=1+(int)Math.round(Math.pow(fres.getProgress(),2));
                    mysettings.touch_clicks=c.isChecked();
                    mysettings.update();
                    loadSettings(mysettings);
                    disp("saved");
                    popUp.dismiss();


                }});


                final TextView show=new TextView(act);
                show.setText("You are Now Controlling Computer "+con.client);
                show.setTextColor(Color.BLUE);
                show.setGravity(Gravity.CENTER);

                final Button disconnect=new Button(act);
                disconnect.setText("Disconnect");
                disconnect.setBackgroundColor(Color.RED);
                disconnect.setTextColor(Color.WHITE);
                disconnect.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v)

                    {
                         act.setContentView(l);
                        location=1;
                        a=10;
                        try {
                            con.disconnect();
                        }catch (Exception e)
                        {

                        }
                        disp("Disconnected");
                        popUp.dismiss();
                    }
                });







                act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lsettings.setLayoutParams(new LinearLayout.LayoutParams(0,0));
                    lsettings.getLayoutParams().width=metrics.widthPixels-100;
                    //lsettings.setTranslationX(50);
                    lsettings.getLayoutParams().height=metrics.heightPixels-200;
                    //lsettings.setTranslationY(100);
                    popUp.setContentView(lsettings);
                    popUp.showAtLocation(lsettings, Gravity.CENTER, 10, 10);
                    popUp.update(0, 0,metrics.widthPixels-100,metrics.heightPixels-200);

                    lsettings.addView(hid);
                    lsettings.addView(cancel);
                    lsettings.addView(c);
                    lsettings.addView(lfres);
                    lsettings.addView(fres);
                    lsettings.addView(lscroller);
                    lsettings.addView(scroller);
                    lsettings.addView(b);
                    lsettings.addView(show);
                    lsettings.addView(disconnect);

                    int tot=metrics.widthPixels-100;
                    hid.setTranslationY(50);
                    hid.getLayoutParams().width=tot*2/3-50;
                    hid.getLayoutParams().height=100;

                    cancel.getLayoutParams().width=tot/3-50;
                    cancel.getLayoutParams().height=100;
                    cancel.setTranslationX(tot*2/3);
                    cancel.setTranslationY(-50);


                    c.getLayoutParams().width=tot*2/3;
                    c.getLayoutParams().height=50;
                    c.setTranslationX(tot/6);
                    c.setTranslationY(30);

                    lfres.getLayoutParams().width=tot;
                    lfres.getLayoutParams().height=50;
                    //cancel.setTranslationX(tot*2/3);
                    lfres.setTranslationY(170);


                    fres.getLayoutParams().width=tot-50;
                    fres.getLayoutParams().height=100;
                    //cancel.setTranslationX(tot*2/3);
                    fres.setTranslationY(170);


                    lscroller.getLayoutParams().width=tot;
                    lscroller.getLayoutParams().height=50;
                    //cancel.setTranslationX(tot*2/3);
                    lscroller.setTranslationY(200);


                    scroller.getLayoutParams().width=tot-50;
                    scroller.getLayoutParams().height=100;
                    //cancel.setTranslationX(tot*2/3);
                    scroller.setTranslationY(200);

                   
                    b.getLayoutParams().width=tot/2;
                    b.getLayoutParams().height=150;
                    b.setTranslationX(tot/4);
                    b.setTranslationY(400);


                    show.getLayoutParams().width=tot;
                    show.getLayoutParams().height=150;
                    show.setTranslationX(0);
                    show.setTranslationY(500);



                    disconnect.getLayoutParams().width=tot/2;
                    disconnect.getLayoutParams().height=150;
                    disconnect.setTranslationX(tot/4);
                    disconnect.setTranslationY(500);





                }});

                



                 


            }
        });

   


    final ImageView scroll=new ImageView(act);
    scroll.setImageResource(R.drawable.scroll);
    scroll.setBackgroundColor(rgb(0,0,139));
    //scroll.setScaleType(ScaleType.CENTER_INSIDE);
    scroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                double touchx=event.getX();

                if(event.getAction()==MotionEvent.ACTION_DOWN)
                    start_scroll=touchx;
                //int times=5;

                int num=(int)Math.round((start_scroll-touchx)/scroll.getWidth()*times);
                final String fine="<scroll>"+num+"</scroll>";
                Thread h=new Thread()
                {
                    public void run()
                    {
                        try{
                            con.send_data(fine.getBytes());
                        }catch(Exception e)
                        {

                        }

                    }
                };
                h.start();

                
                    start_scroll+=num;
                return true;
            }


        });

    act.runOnUiThread(new Runnable() {

        public void run() {
            sess.addView(right);
            right.addView(settings);
           right.addView(scroll);
            
            right.setLayoutParams(new LinearLayout.LayoutParams(metrics.widthPixels - 20, (int) Math.round((metrics.heightPixels - thelength) / 2 + metrics.heightPixels / 2.0) - 20));
            right.getLayoutParams().width=metrics.widthPixels-20;
            right.setTranslationX(10);
            right.setTranslationY((float) Math.round(thelength/2.0)-100);
            right.getLayoutParams().height=100;//(int) Math.round((metrics.heightPixels - thelength) / 2 + metrics.heightPixels / 2.0)-20;
            right.setOrientation(LinearLayout.VERTICAL);
            //LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //rclick.setLayoutParams(params);
            rclick.getLayoutParams().height = 100;
            rclick.getLayoutParams().width =(int) (metrics.widthPixels-20)/3;

            
            settings.setTranslationX((float) tot*3/3-150);
            settings.setTranslationY(-100);
            settings.getLayoutParams().height=100;//left.getHeight();
            settings.getLayoutParams().width=100;

            scroll.setTranslationX((float) tot*1/3+50);
            scroll.setTranslationY(-200);
            scroll.getLayoutParams().height=100;//left.getHeight();
            scroll.getLayoutParams().width=tot/3*2-250;

        }
    });



    


    rclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mouseright();
            }
        });


}catch (Exception e)
{
    disp(e.toString()+"Right");
}


    



    }
   
    private static double start_scroll=0.0;
    public static void mouse_move(double x,double y)
    {
        final String sn="<mousemove>"+x+","+y+"</mousemove>";
        Thread h=new Thread() {
            public void run()
            {
                con.send_data(sn.getBytes());
            }};
        h.start();
    }

    public static void mouseclick()
    {
        final String dat="mousedown";
        Thread h=new Thread() {
            public void run()
            {
                con.send_data(dat.getBytes());
            }};
        h.start();

    }

    public static void tap(double x,double y)
    {
        final String sn="<tap>"+x+","+y+"</tap>";
        Thread h=new Thread() {
            public void run()
            {
                con.send_data(sn.getBytes());
            }};
        h.start();
    }

    public static void mouserelease()
    {
        final String dat="mouseup";
            Thread h=new Thread() {
                public void run()
                {
                    con.send_data(dat.getBytes());
                }};
            h.start();

    }

    public static void mouseright()
    {
        final String dat="mouseright";
        Thread h=new Thread() {
            public void run()
            {
                con.send_data(dat.getBytes());
            }};
        h.start();

    }

    public static void scanConnect()
    {
        disp("Sijamalizia");
    }



}

