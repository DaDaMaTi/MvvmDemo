package com.csmar.mvvmdemo;

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;


public class MainFragmentStateAdapter extends FragmentStateAdapter {

    private List<Fragment> fragmentList;
    private SparseArray<Long> idArray = new SparseArray(3);

    public MainFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragmentList) {
        super(fragmentActivity);
//        idArray.clear();
//        for (int i = 0; i < fragmentList.size(); i++) {
//            idArray.put(i, (long) i);
//        }
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList == null ? null : fragmentList.get(position);
    }

//    @Override
//    public long getItemId(int position) {
//        return idArray.get(position);
//    }

    @Override
    public int getItemCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }

//    @Override
//    public boolean containsItem(long itemId) {
//        return idArray.get((int)itemId) == itemId;
//    }
}
