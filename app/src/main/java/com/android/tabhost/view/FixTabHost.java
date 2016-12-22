package com.android.tabhost.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.tabhost.R;
import com.android.tabhost.utils.Px2dpUtils;

/**
 * Description:
 * Author     : kevin.bai
 * Time       : 2016/12/19 11:26
 * QQ         : 904869397@qq.com
 */

public class FixTabHost extends LinearLayout {

    private FragmentTabHost mTabHost = null;
    private View indicator = null;
    private Context mContext;
    private int mTabWidgetHeight;
    private TabChangeListener mTabChangedListener;
    int[] mTabColor=null;
    int[][] mTabResource=null;
    boolean mTabTop=false;

    public FixTabHost(Context context) {
        super(context);
        this.mContext=context;
        getTabWidgetHeight(context);
    }

    public FixTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        getTabWidgetHeight(context);
    }

    public FixTabHost(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        getTabWidgetHeight(context);
    }

    private void getTabWidgetHeight(Context context){
        int[] attr=new int[]{
                android.R.attr.actionBarSize
        };
        final TypedArray a = context.obtainStyledAttributes(attr);
        mTabWidgetHeight = a.getDimensionPixelSize(0, Px2dpUtils.dip2px(context,50));
        a.recycle();
    }

    /**
     * 初始化
     * @param fragmentManager  Fragment 管理器
     * @param tabEnum  Tab 方向 top or bottom
     * @param tabName  Tab 名称
     * @param tabColor Tab 字体颜色  0表示未选中，1表示选中
     * @param tabResource  top 时为背景资源, bottom 时为drawable icon 资源
     *                     二维数组内部数组 0表示未选中，1表示选中
     * @param fragments  Fragment集合
     */
    public void setup(
            FragmentManager fragmentManager,
            TabEnum tabEnum,
            String[] tabName,
            int[] tabColor,
            int[][] tabResource,
            Class<Fragment>[] fragments){
        initView(mContext,fragmentManager,tabEnum,tabName,tabColor,tabResource,fragments,null);
    }

    /**
     * 初始化
     * @param fragmentManager  Fragment 管理器
     * @param tabEnum  Tab 方向 top or bottom
     * @param tabName  Tab 名称
     * @param tabColor Tab 字体颜色  0表示未选中，1表示选中
     * @param tabResource  top 时为背景资源, bottom 时为drawable icon 资源
     *                     二维数组内部数组 0表示未选中，1表示选中
     * @param fragments  Fragment集合
     * @param bundles    Fragment参数
     */
    public void setup(
            FragmentManager fragmentManager,
            TabEnum tabEnum,
            String[] tabName,
            int[] tabColor,
            int[][] tabResource,
            Class<Fragment>[] fragments,
            Bundle[] bundles){
        initView(mContext,fragmentManager,tabEnum,tabName,tabColor,tabResource,fragments,bundles);
    }


    /**
     * 初始化控件
     * @param context  上下文对象
     * @param fragmentManager  Fragment 管理器
     * @param tabEnum  Tab 方向 top or bottom
     * @param tabName  Tab 名称
     * @param tabColor Tab 字体颜色  0表示未选中，1表示选中
     * @param tabResource  top 时为背景资源, bottom 时为drawable icon 资源
     * @param fragments  Fragment集合
     * @param bundles    Fragment参数
     */
    private void initView(
            Context context,
            FragmentManager fragmentManager,
            TabEnum tabEnum,
            String[] tabName,
            int[] tabColor,
            int[][] tabResource,
            Class<Fragment>[] fragments,
            Bundle[] bundles) {
        View view=View.inflate(context, R.layout.view_tabhost,null);
        mTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        mTabHost.setup(context, fragmentManager, android.R.id.tabcontent);
        mTabHost.getTabWidget().setDividerDrawable(null);
        setTabWidgetGravity(getTabWidgetGravity(tabEnum));
        initTabHost(mTabTop,context,tabName,tabColor,tabResource,fragments,bundles);
        initListener();
        this.addView(view);
    }

    /**
     * 初始化监听事件
     */
    private void initListener(){
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int total = mTabHost.getTabWidget().getChildCount();
                for (int i = 0; i < total; i++) {
                    TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.tabText);
                    tabChanged(mTabTop,tv,i,mTabHost.getCurrentTab() == i?true:false);
                }

                if(mTabChangedListener!=null){
                    mTabChangedListener.onTabChanged(mTabHost.getCurrentTab(),tabId);
                }

            }
        });
    }

    /**
     * TabHost切换监听接口
     * @param tabChangedListener
     */
    public void setOnTabChangedListener(TabChangeListener tabChangedListener){
        this.mTabChangedListener=tabChangedListener;

    }

    /**
     * 初始化TabHost
     * @param tabTop   Tab 方向 top or bottom
     * @param context  上下文对象
     * @param tabName  Tab 名称
     * @param tabColor Tab 字体颜色
     * @param tabResource  top 时为背景资源, bottom 时为drawable icon 资源
     * @param fragments  Fragment集合
     * @param bundles    Fragment参数
     */
    private void initTabHost(
            boolean tabTop,
            Context context,
            String[] tabName,
            int[] tabColor,
            int[][] tabResource,
            Class<Fragment>[] fragments,
            Bundle[] bundles) {
        if(tabName==null||tabName.length==0){
            return;
        }
        if(tabColor==null||tabColor.length<2){
            return;
        }
        if(tabResource==null||tabResource.length==0){
            return;
        }
        if(fragments==null||fragments.length==0){
            return;
        }
        if(tabName.length!=fragments.length&&tabName.length!=tabResource.length){
            throw new IllegalArgumentException("invalid argument");
        }
        setTabColor(tabColor);
        setTabResource(tabResource);

        for (int k=0;k<fragments.length;k++){
            String name = TextUtils.isEmpty(tabName[k])?"TAB"+k:tabName[k];
            int[] resource=tabResource[k];
            indicator = initIndicatorView(tabTop,context,name,tabColor,resource,k);
            mTabHost.addTab(
                    mTabHost.newTabSpec(name).setIndicator(indicator),
                    fragments[k],
                    (bundles==null||bundles.length==0)?null:bundles[k]);

        }

    }


    /**
     * 初始化TabHost Item
     * @param tabTop   Tab 方向 top or bottom
     * @param context  上下文对象
     * @param tabName  Tab 名称
     * @param tabColor Tab 字体颜色
     * @param tabResource  top 时为背景资源, bottom 时为drawable icon 资源
     * @param tabPos  当前位置
     */
    private View initIndicatorView(
            boolean tabTop,
            Context context,
            String tabName,
            int[] tabColor,
            int[] tabResource,
            int tabPos) {
        View v = View.inflate(context,R.layout.view_tabbar_item, null);
        TextView tv = (TextView) v.findViewById(R.id.tabText);
        tv.setText(tabName);
        if(tabTop){
            tv.setBackgroundResource(tabPos==0?tabResource[1]:tabResource[0]);
        }else{
            tv.setCompoundDrawables(null, drawableIcon(tabPos==0?tabResource[1]:tabResource[0]), null, null);
        }
        tv.setTextColor(tabPos==0?
                getResources().getColor(tabColor[1]):getResources().getColor(tabColor[0]));
        return v;
    }

    /**
     * 设置Icon bounds
     * @param resId
     * @return
     */
    private Drawable drawableIcon(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }


    /**
     *
     * @param tabTop
     * @param tv
     * @param position
     * @param active
     */
    private void tabChanged(boolean tabTop, TextView tv, int position, boolean active){
        if(mTabResource==null||mTabResource.length==0){
            return;
        }
        if(mTabColor==null||mTabColor.length<2){
            return;
        }
        int[] tabResource=mTabResource[position];
        if(tabTop){
            tv.setBackgroundResource(active?tabResource[1]:tabResource[0]);
        }else{
            tv.setCompoundDrawables(null, drawableIcon(active?tabResource[1]:tabResource[0]), null, null);
        }
        tv.setTextColor(active?
                getResources().getColor(mTabColor[1]):getResources().getColor(mTabColor[0]));

    }


    /**
     * 设置TabWidget背景
     * @param resId
     */
    public void setTabWidgetBackground(int resId){
        mTabHost.getTabWidget().setBackgroundResource(resId);
    }

    /**
     * 设置TabWidget间距
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setTabWidgetPadding(int left, int top, int right, int bottom){
        mTabHost.getTabWidget().setPadding(left, top, right, bottom);
    }

    /**
     * 设置TabWidget间距
     * @param padding
     */
    public void setTabWidgetPadding(int padding){
        mTabHost.getTabWidget().setPadding(padding, padding, padding, padding);
    }

    /**
     * 设置TabWidget当前位置
     * @param index
     */
    public void setCurrentTab(int index){
        mTabHost.setCurrentTab(index);
    }



    /**
     * 设置Tab字体颜色
     * 数组长度须>=2
     * 0 表示选中颜色
     * 1 表示未选中颜色
     * @param tabColor
     */
    public void setTabColor(int[] tabColor){
        if(tabColor==null||tabColor.length<2){
            return;
        }
        this.mTabColor=tabColor;
    }

    /**
     * 设置Tab资源
     * @param tabResource
     */
    public void setTabResource(int[][] tabResource){
        if(tabResource==null||tabResource.length==0){
            return;
        }
        this.mTabResource=tabResource;
    }


    /**
     * 设置TabWidget位置
     * @param gravity
     * Gravity.TOP  上边
     * Gravity.Bottom 下边
     */
    private void setTabWidgetGravity(int gravity){

        if((gravity!=(0x0002|0x0001)<<4)&&(gravity!=(0x0004|0x0001)<<4)){
            throw new IllegalArgumentException("invalid argument");
        }

        FrameLayout.LayoutParams widget=new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,mTabWidgetHeight);
        FrameLayout.LayoutParams frame=new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
        switch (gravity){
            case (0x0002|0x0001)<<4:
                mTabTop=true;
                widget.gravity=Gravity.TOP;
                frame.topMargin=mTabWidgetHeight;
                mTabHost.getTabWidget().setLayoutParams(widget);
                (mTabHost.getChildAt(1)).setLayoutParams(frame);
                break;
            case (0x0004|0x0001)<<4:
                mTabTop=false;
                widget.gravity=Gravity.BOTTOM;
                frame.bottomMargin=mTabWidgetHeight;
                mTabHost.getTabWidget().setLayoutParams(widget);
                (mTabHost.getChildAt(1)).setLayoutParams(frame);
                break;
        }


    }

    /**
     * /**
     * 获取TabWidget位置
     * @param tabEnum
     * @return
     * Gravity.TOP  上边
     * Gravity.Bottom 下边
     */
    private int getTabWidgetGravity(TabEnum tabEnum){
        switch (tabEnum.getValue()){
            case 0:
                return Gravity.TOP;
            case 1:
                return Gravity.BOTTOM;
        }
        return Gravity.BOTTOM;
    }

    /**
     * 设置TabWidget高度
     * @param tabWidgetHeight
     */
    public void setTabWidgetHeight(int tabWidgetHeight){
        mTabWidgetHeight=tabWidgetHeight;
        setTabWidgetGravity(mTabTop?Gravity.TOP:Gravity.BOTTOM);
    }



}
