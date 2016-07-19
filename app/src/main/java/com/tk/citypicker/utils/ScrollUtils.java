package com.tk.citypicker.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by TK on 2016/7/19.
 * recyclerview 滚动工具类
 */
public class ScrollUtils {
    /**
     * 滚动到指定位置 置顶
     *
     * @param recyclerView
     * @param position
     */
    public static final void scrollTo(final RecyclerView recyclerView, final int position) {
        recyclerView.scrollToPosition(position);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                int newP = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if (newP != position) {
                    //辅助滑到顶部
                    int[] viewL = new int[2];
                    int[] parentL = new int[2];
                    recyclerView.getLocationInWindow(parentL);
                    View view = recyclerView.findViewHolderForAdapterPosition(position).itemView;
                    view.getLocationInWindow(viewL);
                    recyclerView.scrollBy(0, viewL[1] - parentL[1]);
                }
            }
        });
    }
}
