package com.cafe.view.easyrecyclerview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cafe on 2017/5/1.
 */

public class DataUtil {

    public static List getListData(int listSize) {
        ArrayList<DataType> list = new ArrayList<>();

        for (int i = 0; i < listSize; i++) {

            list.add(new DataType(i, i + 1, i + 2));
        }
        return list;
    }

    /**
     * @param listSize 数据总量
     * @param startNum 起始数据
     * @param up       顺序是否向上（1、2、3。。。）
     * @return
     */

    public static List getListData(int listSize, int startNum, boolean up) {
        ArrayList<DataType> list = new ArrayList<>();



        for (int i = startNum; i < listSize; ) {

            list.add(new DataType(i, i + 1, i + 2));
            i = up ? i + 1 : i - 1;
        }
        return list;
    }

    public static DataType getOneData(int startNum, boolean up) {
        int i = up ? startNum + 1 : startNum - 1;
        return new DataType(i, i + 1, i + 2);
    }

}
