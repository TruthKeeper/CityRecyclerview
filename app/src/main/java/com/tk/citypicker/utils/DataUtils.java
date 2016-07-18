package com.tk.citypicker.utils;

import com.tk.citypicker.bean.CityBean;
import com.tk.citypicker.constants.CityData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by TK on 2016/7/18.
 */
public class DataUtils {
    /**
     * 返回城市列表集合
     *
     * @return 额外添加索引项目，name=null
     */
    public static final List<CityBean> getCitybeanList() {
        List<CityBean> list = new ArrayList<CityBean>();
        CityBean bean;
        for (int i = 0; i < CityData.CITY.length; i++) {
            bean = new CityBean();
            bean.setFirst(CharacterParser.convert(CityData.CITY[i]));
            bean.setName(CityData.CITY[i]);
            bean.setId(i);
            list.add(bean);
        }
        Collections.sort(list, new CityComparator());
        return initTipList(list);
    }

    private static final List<CityBean> initTipList(List<CityBean> list) {
        List<CityBean> newList = new ArrayList<CityBean>();
        newList.addAll(list);
        CityBean addBean;
        char oldC = newList.get(0).getFirst();
        int off = 1;
        addBean = new CityBean();
        addBean.setFirst(oldC);
        newList.add(0, addBean);
        for (int i = 0; i < list.size(); i++) {
            char newC = list.get(i).getFirst();
            if (newC != oldC) {
                addBean = new CityBean();
                addBean.setFirst(newC);
                newList.add(i + off, addBean);
                oldC = newC;
                off++;
            }
        }
        return newList;
    }

    /**
     * 找到索引的位置
     *
     * @param c
     * @param list
     * @return
     */
    public static final int findPosition(char c, List<CityBean> list) {
        int l = list.size();
        for (int i = 0; i < l; i++) {
            if (list.get(i).getFirst() == c) {
                return i;
            }
        }
        return 0;
    }
}
