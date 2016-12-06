package top.greendami.wifistate;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static top.greendami.wifistate.WifiState.STATE.LOADING;

/**
 * Connecting and Error State Wi-Fi Animation
 * Created by http://GreendaMi.top on 2016/12/5.
 */

public class WifiState extends View {

    private int activeColor;//前景色
    private int inactiveColor;//背景色

    Paint activePaint;
    Paint inactivePaint;

    int widthSpecSize;
    int heightSpecSize;

    public void setSTATE(STATE STATE) {
        mSTATE = STATE;
        invalidate();
    }

    STATE mSTATE = LOADING;

    int activeAngle = -45;
    int shadowAngle = -45;//阴影当前的开始角度
    int shadow = 20;//阴影较低
    boolean shadowIsDowning = false;//摆针是否在等待
    int step = 2;

    boolean direct = true;
    public WifiState(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs , R.styleable.WifiState);
        activeColor = a.getColor(R.styleable.WifiState_activeColor, Color.BLACK);
        inactiveColor = a.getColor(R.styleable.WifiState_inactiveColor, Color.GRAY);
        a.recycle();

        activePaint = new Paint();
        activePaint.setColor(activeColor);
        activePaint.setAntiAlias(false);

        inactivePaint = new Paint();
        inactivePaint.setColor(inactiveColor);
        inactivePaint.setAntiAlias(false);
        inactivePaint.setStyle(Paint.Style.STROKE);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        //宽度至少是高度的根号2倍
        heightSpecSize = (int)(widthSpecSize / Math.sqrt(2));
        setMeasuredDimension(widthSpecSize, heightSpecSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rectF1 = new RectF(widthSpecSize / 2 - heightSpecSize * 21  / 22 ,
                heightSpecSize / 22 ,
                widthSpecSize / 2 + heightSpecSize * 21 / 22 ,
                2 * heightSpecSize);
        RectF rectF2 = new RectF(widthSpecSize / 2 - heightSpecSize * 15  / 22 ,
                heightSpecSize * 7 / 22 ,
                widthSpecSize / 2 + heightSpecSize * 15 / 22 ,
                heightSpecSize * 19 / 11);
        RectF rectF3 = new RectF(widthSpecSize / 2 - heightSpecSize * 9  / 22 ,
                heightSpecSize * 13 / 22 ,
                widthSpecSize / 2 + heightSpecSize * 9 / 22 ,
                heightSpecSize * 16 / 11);
        Point activeStartPoint;
        switch (mSTATE){
            case LOADING:

                inactivePaint.setStrokeWidth(heightSpecSize  / 11);

                canvas.drawArc(rectF1 , -45 , -90 , false , inactivePaint);
                canvas.drawArc(rectF2 , -45 , -90 , false , inactivePaint);
                canvas.drawArc(rectF3 , -45 , -90 , false , inactivePaint);
                canvas.save();
                activePaint.setStrokeWidth(heightSpecSize  / 11);
                activePaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(widthSpecSize /2 , heightSpecSize * 10 / 11 , heightSpecSize  / 11 ,activePaint);

                activeStartPoint = getActiveStartPoint();

                canvas.drawLine(activeStartPoint.x,activeStartPoint.y,widthSpecSize / 2, heightSpecSize * 12 / 13 , activePaint);

                getShadowAngle();

                activePaint.setStrokeWidth(heightSpecSize  / 11);
                activePaint.setStyle(Paint.Style.STROKE);
                Log.d("WifiState", "shadow:" + shadow);
                if(shadow != 0){
                    canvas.drawArc(rectF1 , shadowAngle , shadow , false , activePaint);
                    canvas.drawArc(rectF2 , shadowAngle , shadow , false , activePaint);
                    canvas.drawArc(rectF3 , shadowAngle , shadow , false , activePaint);
                }
                canvas.restore();
                postInvalidate();
                break;
            case OK:
                activePaint.setStyle(Paint.Style.STROKE);
                inactivePaint.setStrokeWidth(heightSpecSize  / 11);
                activePaint.setStrokeWidth(heightSpecSize  / 11);
                canvas.drawArc(rectF1 , -45 , -90 , false , activePaint);
                canvas.drawArc(rectF2 , -45 , -90 , false , activePaint);
                canvas.drawArc(rectF3 , -45 , -90 , false , activePaint);
                activePaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(widthSpecSize /2 , heightSpecSize * 10 / 11 , heightSpecSize  / 11 ,activePaint);
                canvas.save();
                break;
            case ERROR:


                if(shadowAngle != -90){
                    inactivePaint.setStrokeWidth(heightSpecSize  / 11);

                    canvas.drawArc(rectF1 , -45 , -90 , false , inactivePaint);
                    canvas.drawArc(rectF2 , -45 , -90 , false , inactivePaint);
                    canvas.drawArc(rectF3 , -45 , -90 , false , inactivePaint);
                    canvas.save();
                    activePaint.setStrokeWidth(heightSpecSize  / 11);
                    activePaint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(widthSpecSize /2 , heightSpecSize * 10 / 11 , heightSpecSize  / 11 ,activePaint);

                    activeStartPoint = getActiveStartPoint();

                    canvas.drawLine(activeStartPoint.x,activeStartPoint.y,widthSpecSize / 2, heightSpecSize * 12 / 13 , activePaint);

                    getShadowAngle();

                    activePaint.setStrokeWidth(heightSpecSize  / 11);
                    activePaint.setStyle(Paint.Style.STROKE);
                    Log.d("WifiState", "shadow:" + shadow);
                    if(shadow != 0){
                        canvas.drawArc(rectF1 , shadowAngle , shadow , false , activePaint);
                        canvas.drawArc(rectF2 , shadowAngle , shadow , false , activePaint);
                        canvas.drawArc(rectF3 , shadowAngle , shadow , false , activePaint);
                    }
                    canvas.restore();
                    postInvalidate();
                    break;
                }

                if(shadowAngle == -90 && shadow != 0){
                    inactivePaint.setStrokeWidth(heightSpecSize  / 11);

                    canvas.drawArc(rectF1 , -45 , -90 , false , inactivePaint);
                    canvas.drawArc(rectF2 , -45 , -90 , false , inactivePaint);
                    canvas.drawArc(rectF3 , -45 , -90 , false , inactivePaint);
                    canvas.save();
                    activePaint.setStrokeWidth(heightSpecSize  / 11);
                    activePaint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(widthSpecSize /2 , heightSpecSize * 10 / 11 , heightSpecSize  / 11 ,activePaint);

                    int r = heightSpecSize;
                    int x = (int)(r / Math.sqrt(2)) + (int)(r * Math.cos(Math.toRadians(activeAngle + 90)));
                    int y = r - (int)(r * Math.sin(Math.toRadians(activeAngle + 90)));
                    canvas.drawLine(x , y,widthSpecSize / 2, heightSpecSize * 12 / 13 , activePaint);


                    activePaint.setStrokeWidth(heightSpecSize  / 11);
                    activePaint.setStyle(Paint.Style.STROKE);
                    Log.d("WifiState", "shadow:" + shadow);

                    if(shadow > 0){
                        shadow = shadow - 1;
                    }else{
                        shadow = shadow + 1;
                    }

                    if(shadow != 0){
                        canvas.drawArc(rectF1 , shadowAngle , shadow , false , activePaint);
                        canvas.drawArc(rectF2 , shadowAngle , shadow , false , activePaint);
                        canvas.drawArc(rectF3 , shadowAngle , shadow , false , activePaint);
                    }
                    canvas.restore();
                    postInvalidate();
                    break;
                }


                inactivePaint.setStrokeWidth(heightSpecSize  / 11);
                activePaint.setStyle(Paint.Style.FILL);
                activePaint.setStrokeWidth(heightSpecSize  / 11);
                canvas.drawArc(rectF1 , -45 , -90 , false , inactivePaint);
                canvas.drawArc(rectF2 , -45 , -90 , false , inactivePaint);
                canvas.drawArc(rectF3 , -45 , -90 , false , inactivePaint);
                canvas.drawCircle(widthSpecSize /2 , heightSpecSize * 10 / 11 , heightSpecSize  / 11 ,activePaint);

                activePaint.setStyle(Paint.Style.STROKE);
                activePaint.setStrokeWidth(heightSpecSize*1.2f);

                canvas.drawArc(rectF1 , -83 , -14 , false , activePaint);
                canvas.save();
                break;

        }
    }


    private void getShadowAngle() {
        //direct = true ,  +
        //direct = false , -
        if(!shadowIsDowning){
            if(direct){
                // to  left
                shadow = 20;
                //start
                if(shadowAngle + shadow >= - 45){
                    shadow = - 45 - shadowAngle;
                }
                //end
                if(shadowAngle  <=  - 134){
                    shadowIsDowning = true;
                }
            }else{
                // to  right
                shadow = -20;
                //start
                if(shadowAngle + shadow <=  - 135){
                    shadow = - 135 - shadowAngle;
                }
                //end
                if(shadowAngle  >= - 46){
                    shadowIsDowning = true;
                }
            }
        }else{
            if(direct){
                // to  left
                shadow = shadow - 1;

            }else{
                // to  right
                shadow = shadow + 1;
            }
            if(shadow == 0){
                shadowIsDowning = false;
            }
        }


    }

    private Point getActiveStartPoint() {

        if(!shadowIsDowning){
            //改变角度增加和减少的数值可以摆动调整速度
            //direct = true ,to left
            //direct = false ,to right

            if(direct){
                activeAngle = activeAngle + step;
                shadowAngle = shadowAngle - step;

                if(activeAngle >= 25 ){
                    step = 2;
                }
                if(activeAngle >= 35 ){
                    step = 1;
                }

                if(activeAngle >= 45 ){
                    direct = false;
                    step = 3;
                }
            }else{
                activeAngle = activeAngle - step;
                shadowAngle = shadowAngle + step;
                if(activeAngle <= -25 ){
                    step = 2;
                }
                if(activeAngle <= -35 ){
                    step = 1;
                }

                if(activeAngle <= -45 ){
                    direct = true;
                    step = 3;
                }
            }
        }


        int r = heightSpecSize;
        int x = (int)(r / Math.sqrt(2)) + (int)(r * Math.cos(Math.toRadians(activeAngle + 90)));
        int y = r - (int)(r * Math.sin(Math.toRadians(activeAngle + 90)));
        return new Point(x , y);
    }



    public enum STATE {
        LOADING, OK, ERROR
    }
}
