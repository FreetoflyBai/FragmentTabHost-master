package com.android.tabhost;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.tabhost.utils.Px2dpUtils;
import com.android.tabhost.view.FixTabHost;
import com.android.tabhost.view.TabEnum;

public class MainActivity extends AppCompatActivity {

    FixTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabHost = (FixTabHost) findViewById(R.id.fth);
        mTabHost.setup(
                getSupportFragmentManager(),
                TabEnum.TOP,
                new String[]{"TAB1","TAB2","TAB3"},
                new int[]{R.color.orange,R.color.white},
                new int[][]{
                        new int[]{R.drawable.top_left_n,R.drawable.top_left_p},
                        new int[]{R.drawable.top_middle_n,R.drawable.top_middle_p},
                        new int[]{R.drawable.top_right_n,R.drawable.top_right_p}},
                new Class[]{MainFragment.class,MainFragment.class,MainFragment.class});
        mTabHost.setTabWidgetBackground(R.color.tab_widget_background);
        mTabHost.setTabWidgetPadding(Px2dpUtils.dip2px(this,10));


        mTabHost = (FixTabHost) findViewById(R.id.fth);
        mTabHost.setup(
                getSupportFragmentManager(),
                TabEnum.BOTTOM,
                new String[]{"TAB1","TAB2","TAB3","TAB4","TAB5"},
                new int[]{R.color.black,R.color.orange},
                new int[][]{
                        new int[]{R.drawable.tabbar_letter,R.drawable.tabbar_letter_select},
                        new int[]{R.drawable.tabbar_newest,R.drawable.tabbar_newest_select},
                        new int[]{R.drawable.tabbar_pair,R.drawable.tabbar_pair_select},
                        new int[]{R.drawable.tabbar_mostfire,R.drawable.tabbar_mostfire_select},
                        new int[]{R.drawable.tabbar_me,R.drawable.tabbar_me_select}},
                new Class[]{MainFragment.class,MainFragment.class,MainFragment.class,
                        MainFragment.class,MainFragment.class});
        mTabHost.setTabWidgetBackground(R.color.tab_widget_background);

    }
}
