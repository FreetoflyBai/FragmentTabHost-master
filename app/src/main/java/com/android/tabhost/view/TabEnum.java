package com.android.tabhost.view;

/**
 * Description:
 * Author     : kevin.bai
 * Time       : 2016/12/22 13:47
 * QQ         : 904869397@qq.com
 */

public enum TabEnum {

    TOP(0),BOTTOM(1);

    private int value=0;
    private TabEnum(int value){
        this.value=value;
    }

    public int getValue(){
        return value;
    }
}
