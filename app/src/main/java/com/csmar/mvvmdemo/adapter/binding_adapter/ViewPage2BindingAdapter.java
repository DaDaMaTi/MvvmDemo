package com.csmar.mvvmdemo.adapter.binding_adapter;

import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingMethod;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.csmar.lib.base.util.LogUtil;
import com.csmar.lib.base.view.BottomNavigationView;
import com.csmar.mvvmdemo.MainFragmentStateAdapter;
import com.csmar.mvvmdemo.R;
import com.csmar.mvvmdemo.fragment.IndexFragment;
import com.csmar.mvvmdemo.fragment.MoneyFragment;
import com.csmar.mvvmdemo.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPage2BindingAdapter {

    /**
     * 设置 viewpage2 适配器，与底部按钮联动
     * @param viewPager2 view
     * @param pageLimit 预加载数量
     */
    @BindingAdapter(value = {"initMainViewPage2"}, requireAll = false)
    public static void initMainViewPage2(ViewPager2 viewPager2, int pageLimit) {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new IndexFragment());
        fragmentList.add(new MoneyFragment());
        fragmentList.add(new MyFragment());
        viewPager2.setUserInputEnabled(true);
        viewPager2.setOffscreenPageLimit(pageLimit); // viewpage2 源码中预加载数量为-1，而viewpage 为1
        viewPager2.setAdapter(new MainFragmentStateAdapter((FragmentActivity) viewPager2.getContext(), fragmentList));
        BottomNavigationView bottomNavigationView = viewPager2.getRootView().findViewById(R.id.bottom_navigation_view);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.setSelectedItemId(position);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(position -> {
            viewPager2.setCurrentItem(position);
        });
    }
}
