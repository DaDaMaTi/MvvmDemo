package com.csmar.lib.base.util;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

public class MyClickableSpan extends ClickableSpan {

    private int mColor = 0; // 字体颜色
    private Context mContext;
    private int index; // 区别点击的内容是哪一个

    private Intent intent;

    public MyClickableSpan(Context context, int mColor, int index) {
        this.mContext = context;
        this.mColor = mColor;
        this.index = index;
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        if (mColor == 0) {
            super.updateDrawState(ds);
        } else {
            ds.setColor(mColor);//设置颜色
            ds.setUnderlineText(false);//去掉下划线
        }
    }

    @Override
    public void onClick(@NonNull View view) {
        if (FastClickUtil.isFastClick()) {
            return;
        }
        intent = new Intent();
        intent.setClassName(mContext,BaseApplication.getContext().getPackageName() + ".NoticesActivity");
        switch (index) {
            case 0 : // 跳转隐私
                intent.putExtra("type", 0);
                break;
            case 1 : // 跳转协议
                intent.putExtra("type", 1);
                break;
        }
        mContext.startActivity(intent);
    }
}
