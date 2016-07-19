package com.tk.citypicker.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.tk.citypicker.R;
import com.tk.citypicker.adapter.CityAdapter;
import com.tk.citypicker.bean.CityBean;
import com.tk.citypicker.callback.OnRecyclerClickListener;
import com.tk.citypicker.utils.DataUtils;
import com.tk.citypicker.utils.ScrollUtils;
import com.tk.citypicker.view.MyItemDecoration;
import com.tk.citypicker.view.MySortView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.textview)
    TextView textview;
    @Bind(R.id.sortView)
    MySortView sortView;
    private CityAdapter adapter;
    private List<CityBean> mList = new ArrayList<CityBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setHasFixedSize(true);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        //初始化数据源
        mList = DataUtils.getCitybeanList();
        //初始化sortview
        sortView.setTextView(textview);
        sortView.initData(DataUtils.getIndexList(mList));

        recyclerview.addItemDecoration(new MyItemDecoration(this, mList));
        adapter = new CityAdapter(this, mList);

        recyclerview.setAdapter(adapter);
        recyclerview.addOnItemTouchListener(new OnRecyclerClickListener(recyclerview) {
            @Override
            public void onClick(int position) {
                if (TextUtils.isEmpty(mList.get(position).getName())) {
                    return;
                }
                Toast.makeText(getApplicationContext(), mList.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        sortView.setOnTouchingSortListener(new MySortView.OnTouchingSortListener() {
            @Override
            public void onTouch(char c) {
                int p = DataUtils.findPosition(c, mList);
                ScrollUtils.scrollTo(recyclerview, p);
            }
        });


    }
}
