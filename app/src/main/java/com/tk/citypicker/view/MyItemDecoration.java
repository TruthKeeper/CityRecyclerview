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
    private int divider;
    private ColorDrawable drawable = new ColorDrawable(0xFFDDDDDD);
    private Paint paint = new Paint();
    private int tipHeight;
    private int screenWidth;
    private int padingLeft;
    private float offsetY;
    private int textColor;
    private int darkColor;
    private List<CityBean> mList;

    public MyItemDecoration(Context context, List<CityBean> mList) {
        this.mList = mList;
        divider = 2;
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.text_size));
        darkColor = context.getResources().getColor(R.color.dark);
        textColor = context.getResources().getColor(R.color.text_color);
        tipHeight = context.getResources().getDimensionPixelOffset(R.dimen.tip_height);
        padingLeft = context.getResources().getDimensionPixelOffset(R.dimen.padding_left);
        screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        offsetY = fontMetrics.descent - fontMetrics.ascent;
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
            paint.setColor(darkColor);
            c.drawRect(0, 0, screenWidth, tipHeight, paint);
            paint.setColor(textColor);
            c.drawText(findIndex(parent), padingLeft, offsetY, paint);
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
            return true;
        }
        return false;
    }

    private String findIndex(RecyclerView parent) {
        int p = ((LinearLayoutManager) parent.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        return Character.toString(mList.get(p).getFirst());
    }
}
