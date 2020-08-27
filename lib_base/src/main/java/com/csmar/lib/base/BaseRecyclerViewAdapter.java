package com.csmar.lib.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public abstract class BaseRecyclerViewAdapter<VH extends ViewHolder, T extends ViewDataBinding> extends RecyclerView.Adapter<VH> {

    protected T mBinding;

    /**
     * @return 布局id
     */
    protected abstract int getLayout();

    protected abstract VH initHolder();

    protected abstract void initViewHolder(@NonNull VH holder, int position);

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayout(), parent, false);
        return initHolder();
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        initViewHolder(holder, position);
//        final T item = items.get(position);
//        holder.getBinding().setVariable(BR.item, item);
//        holder.getBinding().executePendingBindings(); // 及时绑定
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
