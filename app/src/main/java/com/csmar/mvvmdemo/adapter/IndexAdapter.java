package com.csmar.mvvmdemo.adapter;

import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.csmar.lib.base.BaseRecyclerViewAdapter;
import com.csmar.lib.base.util.LogUtil;
import com.csmar.mvvmdemo.R;
import com.csmar.mvvmdemo.databinding.ItemIndexBinding;


/**
 * 首页列表数据适配器
 */
public class IndexAdapter extends BaseRecyclerViewAdapter<IndexAdapter.IndexViewHolder, ItemIndexBinding> {
    private final static String TAG = "IndexAdapter_";

    private SparseArray<String> sparseArray;

    public void setData(SparseArray sparseArray) {
        this.sparseArray = sparseArray;
        notifyDataSetChanged();
    }

    @Override
    protected int getLayout() {
        return R.layout.item_index;
    }

    @Override
    protected IndexViewHolder initHolder() {
        mBinding.setVariable(BR.sparse, sparseArray);
        return new IndexViewHolder(mBinding.getRoot());
    }

    @Override
    protected void initViewHolder(@NonNull IndexViewHolder holder, int position) {
        mBinding.setVariable(BR.index, position);
    }

    @Override
    public int getItemCount() {
        return (sparseArray != null) ? sparseArray.size() : 0;
    }

    @BindingAdapter({"imageUrl", "error"})
    public static void loadImage(ImageView imageView, String url,  Drawable errorDrawable) {
        url = url.split(",")[0];
        LogUtil.e(TAG, "url---" + url);
        Glide.with(imageView.getContext())
                .load(Integer.parseInt(url))
                .error(errorDrawable)
                .into(imageView);
    }

    @BindingAdapter({"content"})
    public static void loadContent(TextView textView, String content) {
        content = content.split(",")[1];
        LogUtil.e(TAG, "content---" + content);
        textView.setText(content);
    }

    class IndexViewHolder extends RecyclerView.ViewHolder{

        public IndexViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
