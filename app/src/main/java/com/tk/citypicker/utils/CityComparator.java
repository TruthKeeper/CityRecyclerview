package com.tk.citypicker.utils;

import com.tk.citypicker.bean.CityBean;

import java.util.Comparator;

/**
 * Created by TK on 2016/7/18.
 * 排序
 */
public class CityComparator implements Comparator<CityBean> {
    @Override
    public int compare(CityBean lhs, CityBean rhs) {

        char f1 = lhs.getFirst();
        char f2 = rhs.getFirst();
        // 判断是否为#
        if (isExtra(f1) && isExtra(f2)) {
            return 0;
        }
        if ((!isExtra(f1)) && isExtra(f2)) {
            return -1;
        }
        if (isExtra(f1) && (!isExtra(f2))) {
            return 1;
        }
        return Character.toString(f1).compareTo(Character.toString(f2));
    }

    private boolean isExtra(char c) {
        return c == '#';
    }
}