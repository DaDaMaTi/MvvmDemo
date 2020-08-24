package com.csmar.lib.base.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;

import com.csmar.lib.base.Interface.PopDialogInterface;
import com.csmar.lib.base.R;

/**
 * 三个底部按钮通用 popwindow 弹框, 采用构造者模式
 *
 * @author by wsd
 * @since 2020-08-04
 */
public class CustomPopWindow extends PopupWindow {

    protected CustomPopWindow(Context context) {
        this(context, null);
    }

    protected CustomPopWindow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    protected CustomPopWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void show(View view) {
        this.showAtLocation(view, Gravity.CENTER, 80, 0);
    }

    public static class Builder{
        private CustomPopWindow popWindow;
        private Context mContext;
        private View mView; // 视图
        private Button topConfirm; // 确认
        private Button midConfirm; // 确认
        private Button cancel; // 取消

        private PopDialogInterface mPopListener;

        public Builder(@NonNull Context context) {
            this.mContext = context.getApplicationContext();
            this.popWindow = new CustomPopWindow(mContext);
            setContentView();
        }

        public Builder(@NonNull Context context, int layoutId) {
            this.mContext = context.getApplicationContext();
            this.popWindow = new CustomPopWindow(mContext);
            setContentView(layoutId);
        }

        /**
         * 默认布局
         *
         * @return
         */
        private Builder setContentView(){
            mView = LayoutInflater.from(mContext).inflate(R.layout.popwindow_exit_login, null);
            topConfirm = mView.findViewById(R.id.top_button);
            midConfirm = mView.findViewById(R.id.mid_button);
            cancel = mView.findViewById(R.id.cancel_button);
            cancel.setBottom(80);
            topConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mPopListener != null) {
                        mPopListener.onClick(0);
                    }
                }
            });
            midConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mPopListener != null) {
                        mPopListener.onClick(1);
                    }
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mPopListener != null) {
                        mPopListener.onClick(2);
                    }
                }
            });
            return this;
        }

        private Builder setContentView(int layoutId){
            mView = LayoutInflater.from(mContext).inflate(layoutId, null);
            return this;
        }

        /**
         * 设置顶部按钮文本， 默认为平台退出
         * @param msg 文本
         * @return
         */
        public Builder setMessageTop(String msg){
            if (topConfirm != null) {
                topConfirm.setText(msg);
            }
            return this;
        }

        /**
         * 设置中间按钮文本， 默认为系统退出
         * @param msg 文本
         * @return
         */
        public Builder setMessageMid(String msg){
            if (midConfirm != null) {
                midConfirm.setText(msg);
            }
            return this;
        }

        /**
         * 设置底部按钮文本， 默认为取消
         * @param msg 文本
         * @return
         */
        public Builder setMessageBot(String msg){
            if (cancel != null) {
                cancel.setText(msg);
            }
            return this;
        }

        public Builder setClickListener(PopDialogInterface popDialogInterface) {
            this.mPopListener = popDialogInterface;
            return this;
        }

        public CustomPopWindow build() {
            popWindow.setContentView(mView);
            // 设置弹出窗体的宽和高
            popWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            popWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            popWindow.setFocusable(true);// 使其聚集
            popWindow.setOutsideTouchable(true);
            //实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            //实例化一个ColorDrawable颜色为全透明！！！为了和window底色融合，设置#00ffffff
//            ColorDrawable dw = new ColorDrawable(mContext.getResources().getColor(R.color.transparent));
            popWindow.setBackgroundDrawable(dw);
            popWindow.setAnimationStyle(R.style.custome_popWinAnim);
            return popWindow;
        }
    }
}
