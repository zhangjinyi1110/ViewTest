package com.project.viewtest.widget;

import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.viewtest.R;

/**
 * Created by Administrator on 2018/10/30.
 * 弹簧View
 */

public class SpringView extends FrameLayout {

    private Context context;
    private Paint paint;
    private PointF startPoint, currPoint;
    private float radius;
    private float minRadius = 10;
    private float maxRadius = 25;
    private float maxLength = 800;
    private Path path;
    private TextView numText;
    private ImageView imageView;
    //    private boolean flag;
    private boolean off = false;
    private OnOffListener listener;

    public SpringView(Context context) {
        this(context, null);
    }

    public SpringView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpringView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SpringView, defStyleAttr, 0);
        int color = array.getColor(R.styleable.SpringView_springColor, Color.RED);
        maxLength = array.getDimension(R.styleable.SpringView_maxLength, 500);
        maxRadius = array.getDimension(R.styleable.SpringView_maxRadius, 25);
        minRadius = array.getDimension(R.styleable.SpringView_minRadius, 10);
        String text = array.getString(R.styleable.SpringView_numText);
        array.recycle();
        paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        path = new Path();
        numText = new TextView(context);
        numText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        numText.setText(text == null || text.isEmpty() ? "0" : text);
        numText.setBackgroundResource(R.drawable.circle_red_bg);
        numText.setPadding(15, 0, 15, 0);
        numText.setTextColor(Color.WHITE);
        addView(numText);
        setClickable(true);
        setVisibility(INVISIBLE);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
//        if (flag) {
        radius = calculateRadius();
        if (radius >= minRadius && !off) {
            if (radius > maxRadius) {
                radius = maxRadius;
            }
            calculatePath();
            canvas.drawCircle(startPoint.x, startPoint.y, radius, paint);
            canvas.drawCircle(currPoint.x, currPoint.y, radius, paint);
            canvas.drawPath(path, paint);
        } else {
            off = true;
        }
//        }
        super.dispatchDraw(canvas);
    }

    private float calculateRadius() {
        double a = (currPoint.x - startPoint.x) * (currPoint.x - startPoint.x);
        double b = (currPoint.y - startPoint.y) * (currPoint.y - startPoint.y);
        double c = Math.sqrt(a + b);
        return (float) (maxRadius - c * maxRadius / maxLength);
    }

    private void calculatePath() {
        PointF p0 = new PointF(startPoint.x + (currPoint.x - startPoint.x) / 2, startPoint.y + (currPoint.y - startPoint.y) / 2);
        double a = Math.atan((currPoint.y - startPoint.y) / (currPoint.x - startPoint.x));
        float sin = (float) Math.sin(a) * radius;
        float cos = (float) Math.cos(a) * radius;
        PointF p1 = new PointF(startPoint.x + sin, startPoint.y - cos);
        PointF p2 = new PointF(currPoint.x + sin, currPoint.y - cos);
        PointF p3 = new PointF(currPoint.x - sin, currPoint.y + cos);
        PointF p4 = new PointF(startPoint.x - sin, startPoint.y + cos);
        path.reset();
        path.moveTo(p1.x, p1.y);
        path.quadTo(p0.x, p0.y, p2.x, p2.y);
        path.lineTo(p3.x, p3.y);
        path.quadTo(p0.x, p0.y, p4.x, p4.y);
        path.lineTo(p1.x, p1.y);
    }

    public void actionDown(MotionEvent event) {
        off = false;
        startPoint = new PointF(event.getX(), event.getY());
        currPoint = new PointF(event.getX(), event.getY());
        numText.setX(startPoint.x - numText.getWidth() / 2);
        numText.setY(startPoint.y - numText.getHeight() / 2);
//        int[] lt = new int[2];
//        numText.getLocationOnScreen(lt);
//        int l = lt[0];
//        int r = l + numText.getWidth();
//        int t = lt[1];
//        int b = t + numText.getHeight();
//        float x = event.getRawX();
//        float y = event.getRawY();
//        this.flag = l <= x && x <= r && t <= y && y <= b;
        setVisibility(VISIBLE);
    }

    public void actionMove(MotionEvent event) {
        currPoint.set(event.getX(), event.getY());
        numText.setX(event.getX() - numText.getWidth() / 2);
        numText.setY(event.getY() - numText.getHeight() / 2);
        invalidate();
    }

    public void actionUp() {
//        if (radius < minRadius) {
//            showAnim();
//            return;
//        }
//        recover();
        showAnim();
    }

    private void recover() {
        currPoint.set(startPoint.x, startPoint.y);
        numText.setX(startPoint.x - numText.getWidth() / 2);
        numText.setY(startPoint.y - numText.getHeight() / 2);
//        flag = false;
        invalidate();
        setVisibility(INVISIBLE);
    }

    private void showAnim() {
        if (off) {
            final int[] images = new int[]{R.drawable.id_1, R.drawable.id_2, R.drawable.id_3, R.drawable.id_4, R.drawable.id_5};
            int scale = getScale(30);
            if (imageView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(scale, scale));
                imageView.setX(numText.getX() + numText.getWidth() / 2 - scale / 2);
                imageView.setY(numText.getY() + numText.getHeight() / 2 - scale / 2);
                addView(imageView);
            } else {
                imageView.setX(numText.getX() + numText.getWidth() / 2 - scale / 2);
                imageView.setY(numText.getY() + numText.getHeight() / 2 - scale / 2);
            }
            imageView.setVisibility(VISIBLE);
            numText.setVisibility(INVISIBLE);
            ValueAnimator animator = ValueAnimator.ofInt(5);
            animator.setDuration(500);
            animator.setEvaluator(new TypeEvaluator<Integer>() {
                @Override
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return (int) (fraction * endValue);
                }
            });
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int curr = (int) animation.getAnimatedValue();
                    if (curr != images.length)
                        imageView.setImageResource(images[curr]);
                    else {
                        imageView.setVisibility(INVISIBLE);
                        numText.setVisibility(VISIBLE);
                    }
                }
            });
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (listener != null) {
                        listener.onOff();
                    }
                    recover();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.start();
        } else {
            ValueAnimator animator = ValueAnimator.ofObject(new SpringEvaluator(), (Object[]) getPointF());
            animator.setDuration(500);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    PointF point = (PointF) animation.getAnimatedValue();
                    currPoint.set(point.x, point.y);
                    numText.setX(point.x - numText.getWidth() / 2);
                    numText.setY(point.y - numText.getHeight() / 2);
                    invalidate();
                }
            });
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
//                    Log.i("spring", "onAnimationStart: " + System.currentTimeMillis());
                }

                @Override
                public void onAnimationEnd(Animator animation) {
//                    Log.i("spring", "onAnimationEnd: " + System.currentTimeMillis());
                    if (listener != null) {
                        listener.onOn();
                    }
                    recover();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.start();
        }
    }

    private PointF[] getPointF() {
        PointF[] pointFS = new PointF[6];
        pointFS[0] = new PointF(numText.getX() + numText.getWidth() / 2, numText.getY() + numText.getHeight() / 2);
        float a = pointFS[0].x - startPoint.x;
        float b = pointFS[0].y - startPoint.y;
        pointFS[1] = new PointF(startPoint.x, startPoint.y);
        pointFS[2] = new PointF((float) (startPoint.x - a * 0.1), (float) (startPoint.y - b * 0.1));
        pointFS[3] = new PointF(startPoint.x, startPoint.y);
        pointFS[4] = new PointF((float) (startPoint.x - a * (-0.1)), (float) (startPoint.y - b * (-0.1)));
        pointFS[5] = new PointF(startPoint.x, startPoint.y);
//        pointFS[6] = new PointF((float) (startPoint.x - a * 0.1), (float) (startPoint.y - b * 0.1));
//        pointFS[7] = new PointF(startPoint.x, startPoint.y);
//        pointFS[8] = new PointF((float) (startPoint.x - a * (-0.1)), (float) (startPoint.y - b * (-0.1)));
//        pointFS[9] = new PointF(startPoint.x, startPoint.y);
        return pointFS;
    }

    private class SpringEvaluator implements TypeEvaluator<PointF> {

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            float a = startValue.x - endValue.x;
            float b = startValue.y - endValue.y;
            float x = startValue.x - fraction * a;
            float y = startValue.y - fraction * b;
            Log.i("spring", "evaluate: " + fraction);
            return new PointF(x, y);
        }
    }

    private int getScale(int value) {
        return (int) (context.getResources().getDisplayMetrics().density * value);
    }

//    @SuppressLint("ClickableViewAccessibility")
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Log.i("spring", "onTouchEvent: spring");
//        currPoint.set(event.getX(), event.getY());
//        if (event.getAction() == MotionEvent.ACTION_UP) {
//            currPoint.set(startPoint.x, startPoint.y);
//            numText.setX(startPoint.x - numText.getWidth() / 2);
//            numText.setY(startPoint.y - numText.getHeight() / 2);
//            flag = false;
//            invalidate();
//        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            int[] lt = new int[2];
//            numText.getLocationOnScreen(lt);
//            int l = lt[0];
//            int r = l + numText.getWidth();
//            int t = lt[1];
//            int b = t + numText.getHeight();
//            float x = event.getRawX();
//            float y = event.getRawY();
//            this.flag = l <= x && x <= r && t <= y && y <= b;
//        }
//        if (flag) {
//            numText.setX(event.getX() - numText.getWidth() / 2);
//            numText.setY(event.getY() - numText.getHeight() / 2);
//            invalidate();
//        }
//        return true;
//    }

    @SuppressLint("SetTextI18n")
    public void setText(int num) {
        if (num <= 0) {
            setVisibility(INVISIBLE);
        } else {
            if (num >= 100)
                numText.setText("99+");
            else
                numText.setText(String.valueOf(num));
            setVisibility(VISIBLE);
        }
    }

    @SuppressLint("SetTextI18n")
    public void setText(String num) {
        if (Integer.valueOf(num) <= 0) {
            setVisibility(INVISIBLE);
        } else {
            if (Integer.valueOf(num) >= 100)
                numText.setText("99+");
            else
                numText.setText(num);
            setVisibility(VISIBLE);
        }
    }

    public void setListener(OnOffListener listener) {
        this.listener = listener;
    }

    public interface OnOffListener {
        void onOff();

        void onOn();
    }

}
