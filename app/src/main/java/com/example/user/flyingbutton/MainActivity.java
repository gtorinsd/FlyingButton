package com.example.user.flyingbutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private final int mDelta = 5;

    private final int mStartX = new Random().nextInt(800) + mDelta;
    private final int mStartY = new Random().nextInt(600) + mDelta;
    int mDx = 0;
    int mDy = 0;

    private int mMaxX, mMaxY;
    private View mImage;
    private Button mBtnStartStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImage = findViewById(R.id.button);
        mBtnStartStop = (Button) findViewById(R.id.button2);

        InitFlyingObject();
    }

    private void InitFlyingObject() {
        mImage.setX(mStartX);
        mImage.setY(mStartY);
        InitBeginnerDirection();

        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Run();
                    }
                });
            }
        };

        final boolean[] StopTimer = {false};
        final Timer mTimer = new Timer();
        mBtnStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!StopTimer[0]) {
                    StopTimer[0] = !StopTimer[0];
                    mMaxX = getWindow().getDecorView().getWidth();
                    mMaxY = getWindow().getDecorView().getHeight();
                    mTimer.schedule(timerTask, 0, 10);
                }
                else {
                    mTimer.cancel();
                }
            }
        });
    }

    private void InitBeginnerDirection() {

        mDx = mDelta * -1;
        mDy = mDelta * -1;


        int direction = new Random().nextInt(8);
        switch (direction) {
            case 1: {
                mDx = mDelta;
                mDy = mDelta * -1;
                break;
            }
            case 2: {
                mDx = mDelta;
                mDy = 0;
                break;
            }
            case 3: {
                mDx = mDelta;
                mDy = mDelta;
                break;
            }
            case 4: {
                mDx = 0;
                mDy = mDelta;
                break;
            }
            case 5: {
                mDx = mDelta * -1;
                mDy = mDelta;
                break;
            }
            case 6: {
                mDx = mDelta * -1;
                mDy = 0;
                break;
            }
            case 7: {
                mDx = mDelta * -1;
                mDy = mDelta * -1;
                break;
            }
            default: {
                // 0
                mDx = 0;
                mDy = mDelta * -1;
                break;
            }
        }
    }

    private void CalcNextButtonPosition(View button) {
        float oldX = button.getX();
        float oldY = button.getY();

        float newX = oldX;
        float newY = oldY;

        if ((oldX <= mDelta * 3) || (oldX + mImage.getWidth() >= mMaxX)) {
            mDx *=-1;
        }

        if ((oldY - mImage.getHeight() <= mDelta ) || (oldY + mImage.getHeight() * 3.5 >= mMaxY)) {
            mDy *=-1;
        }

        newX += mDx;
        newY += mDy;

        button.setX(newX);
        button.setY(newY);

        Log.d("=====", "Buttin.Left = " + button.getX());
        Log.d("=====", "Buttin.Top = " + button.getY());
    }

    private void Run() {
        CalcNextButtonPosition(mImage);
    }
}