package com.csmar.mvvmdemo;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.csmar.lib.base.BaseActivity;
import com.csmar.mvvmdemo.databinding.ActivityMainBinding;
import com.csmar.mvvmdemo.fragment.IndexFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding>{

    private List<Fragment> fragmentList;
    MainFragmentStateAdapter mAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewModel() {
//        getActivityViewModel(BR.mode, MainViewMode.class);
    }

    @Override
    protected void initData() {
        mBinding.viewPager2.setUserInputEnabled(true);
        mBinding.viewPager2.setOffscreenPageLimit(2); // viewpage2 源码中预加载数量为-1，而viewpage 为1
        fragmentList = new ArrayList<>();
        fragmentList.add(new IndexFragment());
        fragmentList.add(new IndexFragment());
        fragmentList.add(new IndexFragment());
        mAdapter = new MainFragmentStateAdapter(this, fragmentList);
        mBinding.viewPager2.setAdapter(mAdapter);

        mBinding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                mBinding.bottomNavigationView.setSelectedItemId(position);
            }
        });

        mBinding.bottomNavigationView.setOnNavigationItemSelectedListener(position -> {
            mBinding.viewPager2.setCurrentItem(position);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}