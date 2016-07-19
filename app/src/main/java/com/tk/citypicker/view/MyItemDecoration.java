package com.tk.citypicker.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tk.citypicker.R;
import com.tk.citypicker.adapter.CityAdapter;
import com.tk.citypicker.bean.CityBean;

import java.util.List;

/**
 * Created by TK on 2016/7/18.
 */
public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private Context mContext;
    private List<CityBean> mList;
    //分割线高度
    private int divider;
    private ColorDrawable drawable = new ColorDrawable(0xFFDDDDDD);

    private Paint paint = new Paint();
    //屏幕宽度
    private int screenWidth;
    //字缩进距离
    private int padingLeft;
    //字体高度
    private float textHeight;
    //字体颜色
    private int textColor;
    //标签背景
    private int tipColor;
    //标签高度
    private int tipHeight;
    //标签临时高度
    private float tipTempHeight;

    public MyItemDecoration(Context mContext, List<CityBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        divider = 2;
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setTextSize(mContext.getResources().getDimensionPixelSize(R.dimen.text_size));

        tipColor = mContext.getResources().getColor(R.color.dark);
        textColor = mContext.getResources().getColor(R.color.text_color);
        tipHeight = mContext.getResources().getDimensionPixelOffset(R.dimen.tip_height);
        padingLeft = mContext.getResources().getDimensionPixelOffset(R.dimen.padding_left);
        screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        textHeight = fontMetrics.descent - fontMetrics.ascent;
        tipTempHeight = tipHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int poition = parent.getChildAdapterPosition(view);
        if (poition < parent.getAdapter().getItemCount() - 1) {
            if (parent.getAdapter().getItemViewType(poition) == CityAdapter.ITEM_DATE
                    && parent.getAdapter().getItemViewType(poition + 1) == CityAdapter.ITEM_DATE) {
                outRect.set(0, 0, 0, divider);
            }
        }
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (isOverDraw(parent)) {
            paint.setColor(tipColor);
            c.drawRect(0, 0, screenWidth, tipTempHeight, paint);
            paint.setColor(textColor);
            c.drawText(findIndex(parent), padingLeft, textHeight - (tipHeight - tipTempHeight), paint);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawVertical(c, parent);
    }


    private void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin +
                    Math.round(ViewCompat.getTranslationY(child));
            final int bottom = top + divider;
            drawable.setBounds(left, top, right, bottom);
            drawable.draw(c);
        }
    }

    private boolean isOverDraw(RecyclerView parent) {
        int p = ((LinearLayoutManager) parent.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        if (parent.getAdapter().getItemViewType(p) == CityAdapter.ITEM_DATE) {
            tipTempHeight = tipHeight;
            return true;
        }

        int[] viewL = new int[2];
        int[] parentL = new int[2];
        View view = parent.findViewHolderForAdapterPosition(p).itemView;
        view.getLocationInWindow(viewL);
        parent.getLocationInWindow(parentL);
        if (viewL[1] - parentL[1] > 0) {
            if (viewL[1] - parentL[1] > tipHeight) {
                tipTempHeight = tipHeight;
            } else {
                tipTempHeight = viewL[1] - parentL[1];
            }
            return true;
        }
        return false;
    }

    private String findIndex(RecyclerView parent) {
        int p = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
        return Character.toString(mList.get(p).getFirst());
    }
}
