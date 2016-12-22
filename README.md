# FragmentTabHost-master
FragmentTabHost-master is a library of FragmentTabHost

![image](https://github.com/FreetoflyBai/FragmentTabHost/blob/master/screenshots/1.png)
![image](https://github.com/FreetoflyBai/FragmentTabHost/blob/master/screenshots/1.png)

##Instructions

##TOP:
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

##Bottom:
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
                new Class[]{MainFragment.class,MainFragment.class,MainFragment.class,MainFragment.class,MainFragment.class});
        mTabHost.setTabWidgetBackground(R.color.tab_widget_background);
