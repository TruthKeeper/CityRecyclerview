package com.tk.citypicker.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.tk.citypicker.utils.DensityUtil;


/**
 * Created by TK on 2016/7/18.
 */
public class MySortView extends View {
    public static final char[] CHAR = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z', '#'};
    private static final int NORMAL_COLOR = 0xFFFF6262;
    private static final int PRESS_COLOR = 0xFF1E85D4;
    private static final int PRESS_BG = 0x15000000;
    private static final int TEXT_SIZE = 10;
    private OnTouchingSortListener onTouchingSortListener;
    //记录选中的值
    private int select = -1;
    private Paint paint = new Paint();
    private TextView mTextView;
    private int mWidth;
    private int mHeight;
    private int[] windowLocation = new int[2];

    public MySortView(Context context) {
        super(context);
        init();
    }

    public MySortView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setBackgroundColor(Color.TRANSPARENT);
        paint.setAntiAlias(true);
        paint.setDither(true);
        //加粗
        paint.setFakeBoldText(true);
        paint.setTextSize(DensityUtil.dp2px(getContext(), TEXT_SIZE));
        //横向居中
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int l = CHAR.length;
        //平均
        int singleHeight = mHeight / l;
        for (int i = 0; i < l; i++) {
            paint.setColor(i == select ? PRESS_COLOR : NORMAL_COLOR);
            canvas.drawText(Character.toString(CHAR[i]), mWidth >> 1, (i + 1) * singleHeight, paint);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                setBackgroundColor(PRESS_BG);

                int newS = calculPosition(event.getRawY());
                if (newS != select) {
                    select = newS;
                    invalidate();
                    if (onTouchingSortListener != null) {
                        onTouchingSortListener.onTouch(CHAR[select]);
                    }
                }
                if (mTextView != null) {
                    mTextView.setVisibility(View.VISIBLE);
                    mTextView.setText(Character.toString(CHAR[select]));
                }
                break;
            case MotionEvent.ACTION_UP:
                setBackgroundColor(Color.TRANSPARENT);
                select = -1;//
                invalidate();
                if (mTextView != null) {
                    mTextView.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 根据y坐标计算position
     *
     * @param y
     * @return
     */
    private int calculPosition(float y) {
        if (y < windowLocation[1]) {
            return 0;
        }
        if (y > windowLocation[1] + mHeight) {
            return CHAR.length - 1;
        }
        float single = mHeight / CHAR.length;
        if ((y - windowLocation[1]) % single != 0) {
            return (int) ((y - windowLocation[1]) / single);
        }
        return (int) ((y - windowLocation[1]) / single) - 1;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        getLocationInWindow(windowLocation);

    }

    /**
     * 设置外部textview
     *
     * @param mTextView
     */
    public void setTextView(TextView mTextView) {
        this.mTextView = mTextView;
    }

    public int getSelect() {
        return select;
    }

    public void setOnTouchingSortListener(OnTouchingSortListener onTouchingSortListener) {
        this.onTouchingSortListener = onTouchingSortListener;
    }

    public interface OnTouchingSortListener {
        public void onTouch(char c);
    }

}
