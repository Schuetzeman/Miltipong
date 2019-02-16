package com.example.naddi.wifip2p2tesi;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.net.wifi.p2p.WifiP2pInfo;
import android.widget.Toast;

import com.example.naddi.wifip2p2tesi.MainActivity;




public class GameView extends View  {

    int amountPlayers;
    int scoreLeft = 0;
    int scoreRight = 0;
    float zwischenfloat;
    public static Circle circle;
    Screen thisScreen;
    Screen[] screen;
    Screen saveScreen;
    Paddle[] paddle;
    Paint paint;
    private static final String TAG ="DEBUGINGER";


    public GameView(Context context) {
        super(context);

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);

        thisScreen = new Screen();
        thisScreen.getOwnHandyTask();
        thisScreen.getOwnHandyDimensions();
        thisScreen.getOwnHandyPosition();
        Log.i(TAG, "Das Objekt Circle ist Erschinen");
        circle = new Circle();

        circle.standardradius = 10;


        circle.radius = circle.standardradius * thisScreen.density;




        String msgcript = MainActivity.cript.encript("Letsegooo"); //Verschlüsselt ie nachricht
        MainActivity.sendReceive.write(msgcript.getBytes()); //senden


        if(thisScreen.HandyTask == 'h'){

        }



        if(thisScreen.HandyTask == 'h'){
            thisScreen.getAmountPlayers();
            screen = new Screen[amountPlayers];
            for(int i = 0; i < amountPlayers; i++) screen[i] = new Screen();

            circle.xpos = 450;
            circle.ypos = 900;
            circle.standardxspeed = 6;
            circle.standardyspeed = 3;
            //circle.radius = 10 * thisScreen.density;


            screen[thisScreen.HandyPosition - 1].width = thisScreen.width;
            screen[thisScreen.HandyPosition - 1].height = thisScreen.height;
            screen[thisScreen.HandyPosition - 1].density = thisScreen.density;
            screen[thisScreen.HandyPosition - 1].HandyPosition = thisScreen.HandyPosition;
            screen[thisScreen.HandyPosition - 1].HandyTask = 'h';

            saveScreen = new Screen();

            for(int i = 0; i < amountPlayers - 1; i++){
                saveScreen.getHandyDimensions();
                saveScreen.getHandyPosition();
                saveScreen.HandyPosition = amountPlayers;

                screen[saveScreen.HandyPosition - 1].width = saveScreen.width;
                screen[saveScreen.HandyPosition - 1].height = saveScreen.height;
                screen[saveScreen.HandyPosition - 1].density = saveScreen.density;
                screen[saveScreen.HandyPosition - 1].HandyPosition = saveScreen.HandyPosition;
                screen[saveScreen.HandyPosition - 1].HandyTask = 'j';
            }
            zwischenfloat = 9999;
            for(int i = 0; i < amountPlayers; i++){
                if(screen[i].height / screen[i].density < zwischenfloat){
                    zwischenfloat = screen[i].height / screen[i].density;
                    //zwischenspeicher = i;
                }
            }
            for(int i = 0; i < amountPlayers; i++){
                screen[i].adjustedHeight = zwischenfloat * screen[i].density;
                screen[i].offset = (screen[i].height - screen[i].adjustedHeight)/2;
            }


            thisScreen.offset = screen[thisScreen.HandyPosition - 1].offset;


        }
        else{
            thisScreen.sendHandyDimensions();
            thisScreen.sendHandyPosition();
        }



        paddle = new Paddle[3];
        paddle[0] = new Paddle();
        paddle[1] = new Paddle();
        paddle[2] = new Paddle();

        if(thisScreen.HandyPosition == 1 && thisScreen.HandyTask == 'j'){
            paddle[0].xdistance = 80 * thisScreen.density;
            paddle[0].length = 100 * thisScreen.density;
            paddle[0].width = 10 * thisScreen.density;
            paddle[0].ypos = thisScreen.height/2;
            paddle[0].adjust = 50 * thisScreen.density;
        }

        if(thisScreen.HandyPosition == amountPlayers && thisScreen.HandyTask == 'j'){
            paddle[0].xdistance = 80 * thisScreen.density;
            paddle[0].length = 100 * thisScreen.density;
            paddle[0].width = 10 * thisScreen.density;
            paddle[0].ypos = thisScreen.height/2;
            paddle[0].adjust = 50 * thisScreen.density;
        }

        if(thisScreen.HandyTask == 'h'){
            paddle[1].xdistance = 80 * thisScreen.density;
            paddle[1].length = 100 * thisScreen.density;
            paddle[1].width = 10 * thisScreen.density;
            paddle[1].ypos = thisScreen.height/2;
            paddle[1].adjust = 50 * thisScreen.density;
            paddle[2].xdistance = 80 * thisScreen.density;
            paddle[2].length = 100 * thisScreen.density;
            paddle[2].width = 10 * thisScreen.density;
            paddle[2].ypos = thisScreen.height/2;
            paddle[2].adjust = 50 * thisScreen.density;
        }

        if(thisScreen.HandyPosition == 1) paddle[0].xpos = paddle[0].xdistance;
        if(thisScreen.HandyPosition == amountPlayers) paddle[0].xpos = thisScreen.width - paddle[0].xdistance;
        if(thisScreen.HandyTask == 'h' /*&& thisScreen.HandyPosition != 1*/) paddle[1].xpos = paddle[1].xdistance;
        if(thisScreen.HandyTask == 'h' /*&& thisScreen.HandyPosition != amountPlayers*/) paddle[2].xpos = screen[amountPlayers - 1].width - paddle[2].xdistance;


        circle.CurrentHandy = 1;


    }


    class Circle {
        float xpos;
        float ypos;
        float standardxspeed;
        float standardyspeed;
        float xspeed;
        float yspeed;
        float standardradius;
        float radius;
        int CurrentHandy;

        public void move(){
            circle.xpos += circle.xspeed;
            circle.ypos += circle.yspeed;
        }

        public void getSpecificValues(){
            circle.xspeed = circle.standardxspeed * screen[circle.CurrentHandy - 1].density;
            circle.yspeed = circle.standardyspeed * screen[circle.CurrentHandy - 1].density;
            radius = standardradius * screen[CurrentHandy - 1].density;
        }

        public void checkHitbox() {
           /*
            if (xpos > screen.width - radius || xpos < radius)
                xspeed *= -1;*/
            if (ypos > screen[CurrentHandy - 1].height - radius - screen[CurrentHandy - 1].offset || ypos < radius + screen[CurrentHandy - 1].offset) standardyspeed *= -1;

            if(xpos >= screen[CurrentHandy - 1].width && standardxspeed > 0 && CurrentHandy != amountPlayers) {
                CurrentHandy++;
                xpos = 0;
            }

            if(xpos >= screen[CurrentHandy - 1].width + circle.radius && standardxspeed > 0 && CurrentHandy == amountPlayers){
                scoreLeft++;
                xpos = 450;
                ypos = 900;
                standardxspeed = 6;
                standardyspeed = 3;
            }


            if(xpos < 0 && standardxspeed < 0 && CurrentHandy != 1){
                CurrentHandy--;
                xpos = screen[CurrentHandy].width;
            }

            if(xpos < - circle.radius && standardxspeed < 0 && CurrentHandy == 1){
                scoreRight++;
                xpos = 450;
                ypos = 900;
                standardxspeed = 6;
                standardyspeed = 3;
            }

            if (screen[CurrentHandy - 1].HandyPosition == 1){
                //--------------------------------------
                if(xpos >= screen[CurrentHandy - 1].width - radius && standardxspeed > 0) standardxspeed *= -1;
                //--------------------------------------
                if(xpos - radius <= paddle[1].xpos + paddle[1].width && xpos - radius >= paddle[1].xpos - paddle[1].width && ypos >= paddle[1].ypos - paddle[1].length/2 && ypos <= paddle[1].ypos + paddle[1].length/2 && standardxspeed < 0) standardxspeed *= -1;
            }
            if (screen[CurrentHandy - 1].HandyPosition == amountPlayers){
                //--------------------------------------
                if(xpos <= radius) standardxspeed *= -1;
                //--------------------------------------
                if(xpos + radius >= paddle[2].xpos - paddle[2].width && xpos + radius <= paddle[2].xpos + paddle[2].width && ypos >= paddle[2].ypos - paddle[2].length && ypos <= paddle[2].ypos + paddle[2].length && standardxspeed > 0) standardxspeed *= -1;
            }
        }

        public void sendPos(){

            //Send X-Pos
            String msg = "B_Xpos";
            msg=msg+String.valueOf(xpos);
            Log.i(TAG, "Es wird gesendet: "+msg);
            String msgcript = MainActivity.cript.encript(msg); //Verschlüsselt ie nachricht
            MainActivity.sendReceive.write(msgcript.getBytes()); //senden

            //Send Y-Pos
            msg = "B_Ypos";
            msg=msg+String.valueOf(xpos);
            Log.i(TAG, "Es wird gesendet: "+msg);
            msgcript = MainActivity.cript.encript(msg); //Verschlüsselt ie nachricht
            MainActivity.sendReceive.write(msgcript.getBytes()); //senden


            //Send CurrentHandy

            //Send Radius
        }

        public void getPosX(float wert){
            circle.xpos = wert;
            Log.i(TAG, "Es wurde empfangen: "+String.valueOf(wert));
        }

        public void getPosY(float wert){
            circle.ypos = wert;
            Log.i(TAG, "Es wurde empfangen: "+String.valueOf(wert));
        }
    }


    class Screen {
        float width;
        float height;
        float density;
        float adjustedHeight;
        float offset;
        int HandyPosition;
        char HandyTask;

        public void getHandyPosition(){

        }

        public void getHandyDimensions(){
            //_----------------------------------
            width = Resources.getSystem().getDisplayMetrics().widthPixels;
            height = Resources.getSystem().getDisplayMetrics().heightPixels;
            //height = 900;
            density =  getResources().getDisplayMetrics().density;
            //---------------------------
        }

        public void sendHandyPosition(){
            if(thisScreen.HandyTask == 'h') thisScreen.HandyPosition = 1;
            if(thisScreen.HandyTask == 'j') thisScreen.HandyPosition = amountPlayers;
        }

        public void sendHandyDimensions(){

        }

        public void getOwnHandyPosition(){
            HandyPosition = 1;
        }

        public void getOwnHandyDimensions(){
            width = Resources.getSystem().getDisplayMetrics().widthPixels;
            height = Resources.getSystem().getDisplayMetrics().heightPixels;
            density =  getResources().getDisplayMetrics().density;

            /*
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            height = displayMetrics.heightPixels;
            width = displayMetrics.widthPixels;
            */

            //width = 1070;
            //height = 2100;
        }

        public void getOwnHandyTask(){
            if(MainActivity.IsHost) HandyTask = 'h';
            else HandyTask = 'j';
        }

        public void getAmountPlayers(){
            amountPlayers = 2;
        }

    }

    class Paddle{
        float xdistance;
        float xpos;
        float ypos;
        float length;
        float width;
        float adjust;

        public void sendYPos(){

        }

        public void getLeftYPos(){

        }

        public void getRightYPos(){

        }
    }


    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(Color.BLACK);

        canvas.drawRect(0, 0, thisScreen.width, thisScreen.offset, paint);
        canvas.drawRect(0, thisScreen.height - thisScreen.offset, thisScreen.width, thisScreen.height, paint);

        //if(thisScreen.HandyTask == 'h') circle.sendPos(); //bei send pos muss auch CurrentHandy mit geschickt werden


        //muss wieder eingefuegt werden:!!!!
        //if(thisScreen.HandyPosition == circle.CurrentHandy)canvas.drawCircle(circle.xpos, circle.ypos, circle.radius, paint);
        //----------------------------------------
        canvas.drawCircle(circle.xpos, circle.ypos, circle.radius, paint);
        //----------------------------------------
        if((thisScreen.HandyPosition == 1 || thisScreen.HandyPosition == amountPlayers) && thisScreen.HandyTask == 'j') canvas.drawRect(paddle[0].xpos - paddle[0].width/2, paddle[0].ypos - paddle[0].length/2,paddle[0].xpos + paddle[0].width/2, paddle[0].ypos + paddle[0].length/2, paint);
        if(thisScreen.HandyPosition == 1 && thisScreen.HandyTask == 'h') canvas.drawRect(paddle[1].xpos - paddle[1].width/2, paddle[1].ypos - paddle[1].length/2,paddle[1].xpos + paddle[1].width/2, paddle[1].ypos + paddle[1].length/2, paint);
        if(thisScreen.HandyPosition == amountPlayers && thisScreen.HandyTask == 'h') canvas.drawRect(paddle[2].xpos - paddle[2].width/2, paddle[2].ypos - paddle[2].length/2,paddle[2].xpos + paddle[2].width/2, paddle[2].ypos + paddle[2].length/2, paint);

        if(thisScreen.HandyTask == 'h'){
            if(thisScreen.HandyPosition != 1) paddle[1].getLeftYPos();
            if(thisScreen.HandyPosition != amountPlayers) paddle[2].getRightYPos();
            circle.checkHitbox();
            circle.getSpecificValues();
            circle.move();
        }
        else {
            if(thisScreen.HandyPosition == 1 || thisScreen.HandyPosition == amountPlayers) paddle[0].sendYPos();
            //circle.CurrentHandy = 0;
        }


        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //circle.xpos = event.getX();
        //circle.ypos = event.getY();
        if((thisScreen.HandyPosition == 1 || thisScreen.HandyPosition == amountPlayers) && thisScreen.HandyTask == 'j' && event.getY() < paddle[0].ypos + paddle[0].length/2 + paddle[0].adjust && event.getY() > paddle[0].ypos - paddle[0].length/2 - paddle[0].adjust) {
            paddle[0].ypos = event.getY();
            if(paddle[0].ypos < thisScreen.offset + paddle[0].length/2) paddle[0].ypos = thisScreen.offset + paddle[0].length/2;
            if(paddle[0].ypos > thisScreen.height - thisScreen.offset - paddle[0].length/2) paddle[0].ypos = thisScreen.height - thisScreen.offset - paddle[0].length/2;
        }
        if(thisScreen.HandyPosition == 1 && thisScreen.HandyTask == 'h' && event.getY() < paddle[1].ypos + paddle[1].length/2 + paddle[1].adjust && event.getY() > paddle[1].ypos - paddle[1].length/2 - paddle[1].adjust) {
            paddle[1].ypos = event.getY();
            if(paddle[1].ypos < thisScreen.offset + paddle[1].length/2) paddle[1].ypos = thisScreen.offset + paddle[1].length/2;
            if(paddle[1].ypos > thisScreen.height - thisScreen.offset - paddle[1].length/2) paddle[1].ypos = thisScreen.height - thisScreen.offset - paddle[1].length/2;
        }
        if(thisScreen.HandyPosition == amountPlayers && thisScreen.HandyTask == 'h' && event.getY() < paddle[2].ypos + paddle[2].length/2 + paddle[2].adjust && event.getY() > paddle[2].ypos - paddle[2].length/2 - paddle[2].adjust) {
            paddle[2].ypos = event.getY();
            if(paddle[2].ypos < thisScreen.offset + paddle[2].length/2) paddle[2].ypos = thisScreen.offset + paddle[2].length/2;
            if(paddle[2].ypos > thisScreen.height - thisScreen.offset - paddle[2].length/2) paddle[2].ypos = thisScreen.height - thisScreen.offset - paddle[2].length/2;
        }
        invalidate();
        return true;
    }
}
