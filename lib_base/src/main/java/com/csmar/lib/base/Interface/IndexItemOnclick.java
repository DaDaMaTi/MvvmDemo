package com.csmar.lib.base.Interface;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 首页 item 点击回调接口
 */
public interface IndexItemOnclick {

    /**
     * 首页 item 点击事件
     *
     * @param viewHolder
     */
    void onItemClick(RecyclerView.ViewHolder viewHolder);
}
