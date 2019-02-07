package com.example.naddi.wifip2p2tesi;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;


public class GameView extends View {

    int amountPlayers;
    int zwischenspeicher;
    float zwischenfloat;
    Circle circle;
    Screen thisScreen;
    Screen[] screen;
    Screen saveScreen;
    Paddle[] paddle;
    Paint paint;

    public GameView(Context context) {
        super(context);

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);

        thisScreen = new Screen();
        thisScreen.getOwnHandyDimensions();
        thisScreen.getOwnHandyPosition();
        thisScreen.getOwnHandyTask();

        circle = new Circle();

        if(thisScreen.HandyTask == 'h'){
            thisScreen.getAmountPlayers();
            screen = new Screen[amountPlayers];
            for(int i = 0; i < amountPlayers; i++) screen[i] = new Screen();

            circle.xpos = 450;
            circle.ypos = 900;
            circle.standardxspeed = 6;
            circle.standardyspeed = 3;
            circle.radius = 10 * thisScreen.density;

            if(thisScreen.HandyPosition == 'l') zwischenspeicher = 1;
            if(thisScreen.HandyPosition == 'r') zwischenspeicher = amountPlayers;
            if(thisScreen.HandyPosition != 'l' && thisScreen.HandyPosition != 'r') zwischenspeicher = Character.getNumericValue(thisScreen.HandyPosition);

            screen[zwischenspeicher - 1].width = thisScreen.width;
            screen[zwischenspeicher - 1].height = thisScreen.height;
            screen[zwischenspeicher - 1].density = thisScreen.density;
            screen[zwischenspeicher - 1].HandyPosition = thisScreen.HandyPosition;
            screen[zwischenspeicher - 1].HandyTask = 'h';

            saveScreen = new Screen();

            for(int i = 0; i < amountPlayers - 1; i++){
                saveScreen.getHandyDimensions();
                saveScreen.getHandyPosition();
                saveScreen.HandyPosition = 'r';

                if(saveScreen.HandyPosition == 'l') zwischenspeicher = 1;
                if(saveScreen.HandyPosition == 'r') zwischenspeicher = amountPlayers;
                if(saveScreen.HandyPosition != 'l' && saveScreen.HandyPosition != 'r') zwischenspeicher = Character.getNumericValue(saveScreen.HandyPosition);


                screen[zwischenspeicher - 1].width = saveScreen.width;
                screen[zwischenspeicher - 1].height = saveScreen.height;
                screen[zwischenspeicher - 1].density = saveScreen.density;
                screen[zwischenspeicher - 1].HandyPosition = saveScreen.HandyPosition;
                screen[zwischenspeicher - 1].HandyTask = 'j';
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

            if(thisScreen.HandyPosition == 'l') zwischenspeicher = 1;
            if(thisScreen.HandyPosition == 'r') zwischenspeicher = amountPlayers;
            if(thisScreen.HandyPosition != 'l' && saveScreen.HandyPosition != 'r') zwischenspeicher = Character.getNumericValue(saveScreen.HandyPosition);

            thisScreen.offset = screen[zwischenspeicher - 1].offset;


        }
        else{
            thisScreen.sendHandyDimensions();
            thisScreen.sendHandyPosition();
        }



        paddle = new Paddle[3];
        paddle[0] = new Paddle();
        paddle[1] = new Paddle();
        paddle[2] = new Paddle();

        if(thisScreen.HandyPosition == 'l' && thisScreen.HandyTask == 'j'){
            paddle[0].xdistance = 80 * thisScreen.density;
            paddle[0].length = 100 * thisScreen.density;
            paddle[0].width = 10 * thisScreen.density;
            paddle[0].ypos = thisScreen.height/2;
            paddle[0].adjust = 50 * thisScreen.density;
        }

        if(thisScreen.HandyPosition == 'r' && thisScreen.HandyTask == 'j'){
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

        if(thisScreen.HandyPosition == 'l') paddle[0].xpos = paddle[0].xdistance;
        if(thisScreen.HandyPosition == 'r') paddle[0].xpos = thisScreen.width - paddle[0].xdistance;
        if(thisScreen.HandyTask == 'h' /*&& thisScreen.HandyPosition != 'l'*/) paddle[1].xpos = paddle[1].xdistance;
        if(thisScreen.HandyTask == 'h' /*&& thisScreen.HandyPosition != 'r'*/) paddle[2].xpos = screen[amountPlayers - 1].width - paddle[2].xdistance;


        circle.CurrentHandy = 1;


    }


    class Circle {
        float xpos;
        float ypos;
        float standardxspeed;
        float standardyspeed;
        float xspeed;
        float yspeed;
        float radius;
        int CurrentHandy;

        public void move(){
            circle.xpos += circle.xspeed;
            circle.ypos += circle.yspeed;
        }

        public void getSpecificSpeeds(){
            circle.xspeed = circle.standardxspeed * screen[circle.CurrentHandy - 1].density;
            circle.yspeed = circle.standardyspeed * screen[circle.CurrentHandy - 1].density;

        }

        public void checkHitbox() {
            /*
            if (circle.xpos > screen.width - circle.radius || circle.xpos < circle.radius)
                circle.xspeed *= -1;*/
            if (circle.ypos > screen[CurrentHandy - 1].height - circle.radius - screen[circle.CurrentHandy - 1].offset || circle.ypos < circle.radius + screen[circle.CurrentHandy - 1].offset) circle.standardyspeed *= -1;
            if (screen[CurrentHandy - 1].HandyPosition == 'l'){
                //--------------------------------------
                if(circle.xpos >= screen[CurrentHandy - 1].width - circle.radius) circle.standardxspeed *= -1;
                //--------------------------------------
                if(circle.xpos - circle.radius <= paddle[1].xpos + paddle[1].width && circle.xpos - circle.radius >= paddle[1].xpos - paddle[1].width && circle.ypos >= paddle[1].ypos - paddle[1].length/2 && circle.ypos <= paddle[1].ypos + paddle[1].length/2 && standardxspeed < 0) circle.standardxspeed *= -1;
            }
            if (screen[CurrentHandy - 1].HandyPosition == 'r'){
                //--------------------------------------
                if(circle.xpos <= circle.radius) circle.standardxspeed *= -1;
                //--------------------------------------
                if(circle.xpos + circle.radius >= paddle[2].xpos - paddle[2].width && circle.xpos + circle.radius <= paddle[2].xpos + paddle[2].width && circle.ypos >= paddle[2].ypos - paddle[2].length && circle.ypos <= paddle[2].ypos + paddle[2].length && standardxspeed > 0) circle.standardxspeed *= -1;
            }
        }

        public void sendPos(){

        }

        public void getPos(){

        }
    }

    class Screen {
        float width;
        float height;
        float density;
        float adjustedHeight;
        float offset;
        char HandyPosition;
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

        }

        public void sendHandyDimensions(){

        }

        public void getOwnHandyPosition(){
            HandyPosition = 'l';
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
            HandyTask = 'h';
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

        if(thisScreen.HandyTask == 'h') circle.sendPos(); //bei send pos muss auch CurrentHandy mit geschickt werden
        else circle.getPos();

        if(thisScreen.HandyPosition == 'l') zwischenspeicher = 1;
        if(thisScreen.HandyPosition == 'r') zwischenspeicher = amountPlayers;
        if(thisScreen.HandyPosition != 'l' && thisScreen.HandyPosition != 'r') zwischenspeicher = Character.getNumericValue(thisScreen.HandyPosition);

        if(zwischenspeicher == circle.CurrentHandy)canvas.drawCircle(circle.xpos, circle.ypos, circle.radius, paint);
        if((thisScreen.HandyPosition == 'l' || thisScreen.HandyPosition == 'r') && thisScreen.HandyTask == 'j') canvas.drawRect(paddle[0].xpos - paddle[0].width/2, paddle[0].ypos - paddle[0].length/2,paddle[0].xpos + paddle[0].width/2, paddle[0].ypos + paddle[0].length/2, paint);
        if(thisScreen.HandyPosition == 'l' && thisScreen.HandyTask == 'h') canvas.drawRect(paddle[1].xpos - paddle[1].width/2, paddle[1].ypos - paddle[1].length/2,paddle[1].xpos + paddle[1].width/2, paddle[1].ypos + paddle[1].length/2, paint);
        if(thisScreen.HandyPosition == 'r' && thisScreen.HandyTask == 'h') canvas.drawRect(paddle[2].xpos - paddle[2].width/2, paddle[2].ypos - paddle[2].length/2,paddle[2].xpos + paddle[2].width/2, paddle[2].ypos + paddle[2].length/2, paint);

        if(thisScreen.HandyTask == 'h'){
            if(thisScreen.HandyPosition != 'l') paddle[1].getLeftYPos();
            if(thisScreen.HandyPosition != 'r') paddle[2].getRightYPos();
            circle.checkHitbox();
            circle.getSpecificSpeeds();
            circle.move();
        }
        else {
            if(thisScreen.HandyPosition == 'l' || thisScreen.HandyPosition == 'r') paddle[0].sendYPos();
            circle.CurrentHandy = 0;
        }


        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //circle.xpos = event.getX();
        //circle.ypos = event.getY();
        if((thisScreen.HandyPosition == 'l' || thisScreen.HandyPosition == 'r') && thisScreen.HandyTask == 'j' && event.getY() < paddle[0].ypos + paddle[0].length/2 + paddle[0].adjust && event.getY() > paddle[0].ypos - paddle[0].length/2 - paddle[0].adjust) {
            paddle[0].ypos = event.getY();
            if(paddle[0].ypos < thisScreen.offset + paddle[0].length/2) paddle[0].ypos = thisScreen.offset + paddle[0].length/2;
            if(paddle[0].ypos > thisScreen.height - thisScreen.offset - paddle[0].length/2) paddle[0].ypos = thisScreen.height - thisScreen.offset - paddle[0].length/2;
        }
        if(thisScreen.HandyPosition == 'l' && thisScreen.HandyTask == 'h' && event.getY() < paddle[1].ypos + paddle[1].length/2 + paddle[1].adjust && event.getY() > paddle[1].ypos - paddle[1].length/2 - paddle[1].adjust) {
            paddle[1].ypos = event.getY();
            if(paddle[1].ypos < thisScreen.offset + paddle[1].length/2) paddle[1].ypos = thisScreen.offset + paddle[1].length/2;
            if(paddle[1].ypos > thisScreen.height - thisScreen.offset - paddle[1].length/2) paddle[1].ypos = thisScreen.height - thisScreen.offset - paddle[1].length/2;
        }
        if(thisScreen.HandyPosition == 'r' && thisScreen.HandyTask == 'h' && event.getY() < paddle[2].ypos + paddle[2].length/2 + paddle[2].adjust && event.getY() > paddle[2].ypos - paddle[2].length/2 - paddle[2].adjust) {
            paddle[2].ypos = event.getY();
            if(paddle[2].ypos < thisScreen.offset + paddle[2].length/2) paddle[2].ypos = thisScreen.offset + paddle[2].length/2;
            if(paddle[2].ypos > thisScreen.height - thisScreen.offset - paddle[2].length/2) paddle[2].ypos = thisScreen.height - thisScreen.offset - paddle[2].length/2;
        }
        invalidate();
        return true;
    }
}
