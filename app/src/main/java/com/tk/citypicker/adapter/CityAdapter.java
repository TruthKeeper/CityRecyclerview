package com.tk.citypicker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tk.citypicker.R;
import com.tk.citypicker.bean.CityBean;

import java.util.List;

/**
 * Created by TK on 2016/7/18.
 */
public class CityAdapter extends RecyclerView.Adapter {
    public static final int ITEM_TIP = 222222;
    public static final int ITEM_DATE = 333333;
    private Context mContext;
    private List<CityBean> mList;
    private LayoutInflater mInflater;

    public CityAdapter(Context mContext, List<CityBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mInflater = LayoutInflater.from(mContext);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TIP) {
            return new TipHolder(mInflater.inflate(R.layout.list_item_tip, parent, false));
        } else if (viewType == ITEM_DATE) {
            return new ItemHolder(mInflater.inflate(R.layout.list_item_city, parent, false));
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_TIP) {
            ((TextView) holder.itemView).setText(Character.toString(mList.get(position).getFirst()));
        } else {
            ((TextView) holder.itemView).setText(mList.get(position).getName());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (TextUtils.isEmpty(mList.get(position).getName())) {
            return ITEM_TIP;
        }
        return ITEM_DATE;
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class ItemHolder extends RecyclerView.ViewHolder {
        public ItemHolder(View itemView) {
            super(itemView);
        }
    }

    public static class TipHolder extends RecyclerView.ViewHolder {
        public TipHolder(View itemView) {
            super(itemView);
        }
    }
}
