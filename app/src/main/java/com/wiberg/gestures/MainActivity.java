package com.wiberg.gestures;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {


    private static final String MY_TAG = "jonahe";

    private TextView mMessage;
    private TextView mLeftIndicator;
    private TextView mRightIndicator;
    private GestureDetectorCompat gestureDetector;

    private Handler uiUpdateHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

/*        uiUpdateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                animateAlfaToZero();
            }
        };*/
        uiUpdateHandler = new Handler();

    }

    private void initialize() {
        mMessage = (TextView) findViewById(R.id.tvMessage);
        mLeftIndicator = (TextView) findViewById(R.id.tvLeftIndicator);
        mRightIndicator = (TextView) findViewById(R.id.tvRightIndicator);
        gestureDetector = new GestureDetectorCompat(this, this);
        gestureDetector.setOnDoubleTapListener(this);
    }

    // override Default implementation

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // first send through gesture detector to see if the event should be handled there
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    // START gesture interface methods

    private void updateMsgText(String txt) {
        mMessage.setText(txt);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        updateMsgText("onSingleTapConfirmed");
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        updateMsgText("onDoubleTap");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        updateMsgText("onDoubleTapEvent");
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        updateMsgText("onDown");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        updateMsgText("onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        updateMsgText("onSingleTapUp");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        updateMsgText("onScroll");
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        updateMsgText("onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // updateMsgText("onFling. " + String.format("vel_X: %f, vel_Y: %f", velocityX, velocityY));
        printDirectionMsg(velocityX, velocityY);
        return true;
    }

    // STOP gesture interface methods

    private void printDirectionMsg(float velX, float velY) {
        boolean up = velY < 0;
        boolean left = velX < 0;

        String xDir = up ? "up" : "down";
        String yDir = left ? "left" : "right";

        String mostly = Math.abs(velX) < Math.abs(velY) ? xDir : yDir;

        updateMsgText("onFling. mostly " + mostly);

        mLeftIndicator.setAlpha(0);
        mRightIndicator.setAlpha(0);
        mLeftIndicator.setVisibility(View.VISIBLE);
        mRightIndicator.setVisibility(View.VISIBLE);

        animateAlphaToOne(left ? mLeftIndicator : mRightIndicator);


        uiUpdateHandler.postDelayed(animateAlfaToZero, 500);



    }

    private Runnable animateAlfaToZero = new Runnable() {
        @Override
        public void run() {
            mLeftIndicator.animate().alpha(0);
            mRightIndicator.animate().alpha(0);
        }
    };

    private void animateAlphaToOne(View v) {
        v.animate().alpha(1);
    }



}
