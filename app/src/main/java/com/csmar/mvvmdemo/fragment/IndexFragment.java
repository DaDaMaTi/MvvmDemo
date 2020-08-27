package com.csmar.mvvmdemo.fragment;

import android.content.Context;
import android.util.SparseArray;

import androidx.annotation.NonNull;

import com.csmar.lib.base.BaseFragment;
import com.csmar.lib.base.ToolBarViewMode;
import com.csmar.mvvmdemo.BR;
import com.csmar.mvvmdemo.R;
import com.csmar.mvvmdemo.adapter.IndexAdapter;
import com.csmar.mvvmdemo.databinding.FragmentIndexBinding;
import com.csmar.mvvmdemo.viewmode.IndexViewModel;

/**
 * 首页
 */
public class IndexFragment extends BaseFragment<FragmentIndexBinding> {

    private SparseArray<String> sparseArray = new SparseArray(4);

    private IndexViewModel mViewMode;

    IndexAdapter mAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        TAG = TAG + "IndexFragment--";
        super.onAttach(context);
    }

    @Override
    protected void initViewModel() {
        mToolBarViewMode = getFragmentViewModel(BR.toolbar, ToolBarViewMode.class);
        mViewMode = getFragmentViewModel(BR.vm, IndexViewModel.class);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_index;
    }

    @Override
    protected void initData() {
        mToolBarViewMode.isShowBack.set(false);
        mToolBarViewMode.mTitle.set("首页");
        // 便民生活数据
        sparseArray.put(0, R.mipmap.icon_cz + "," + getResources().getString(R.string.recharge));
        sparseArray.put(1, R.mipmap.icon_cash_out + "," + getResources().getString(R.string.cash_out));
        sparseArray.put(2, R.mipmap.icon_transfer_accounts + "," + getResources().getString(R.string.transfer_accounts));
        sparseArray.put(3, R.mipmap.icon_phone_replenush + "," + getResources().getString(R.string.cellular_phone_replenish));
        sparseArray.put(4, R.mipmap.icon_credit_center + "," + getResources().getString(R.string.credit_center));
        sparseArray.put(5, R.mipmap.icon_credit_card_pay + "," + getResources().getString(R.string.credit_card_payment));
        // 财富管理数据
        sparseArray.put(6, R.mipmap.icon_zhuanzhauan + "," + getResources().getString(R.string.zuanzuan));
        sparseArray.put(7, R.mipmap.icon_borrow + "," + getResources().getString(R.string.borrow));
        sparseArray.put(8, R.mipmap.icon_dqlc + "," + getResources().getString(R.string.lc_dqlc));
//        mViewMode.mData.set(sparseArray);
        mBinding.setAdapter(new IndexAdapter());
        mBinding.getAdapter().setData(sparseArray);
//        mAdapter = new IndexAdapter(mActivity);
//        mBinding.recycle.setAdapter(mAdapter);
//        mAdapter.setData(sparseArray);
    }
}
