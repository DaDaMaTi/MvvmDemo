package com.csmar.lib.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.csmar.lib.base.R;


/**
 * 自定义底部导航栏
 */
public class BottomNavigationView extends LinearLayout implements View.OnClickListener {
    private View viewRoot;
    private LinearLayout indexLin;
    private LinearLayout lcLin;
    private LinearLayout wdLin;
    private ImageView indexImg;
    private TextView indexTxt;
    private ImageView lcImg;
    private TextView lcTxt;
    private ImageView wdImg;
    private TextView wdTxt;

    private OnNavigationItemSelectedListener selectedListener;

    public BottomNavigationView(Context context) {
        this(context, null);
    }

    public BottomNavigationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    private void initLayout() {
        if (getContext() != null) {
            viewRoot = inflate(getContext().getApplicationContext(), R.layout.bottom_navigation_view, this);
            indexLin = viewRoot.findViewById(R.id.index_lin);
            lcLin = viewRoot.findViewById(R.id.lc_lin);
            wdLin = viewRoot.findViewById(R.id.wd_lin);
            indexImg = viewRoot.findViewById(R.id.index_img);
            indexTxt = viewRoot.findViewById(R.id.index_txt);
            lcImg = viewRoot.findViewById(R.id.lc_img);
            lcTxt = viewRoot.findViewById(R.id.lc_txt);
            wdImg = viewRoot.findViewById(R.id.wd_img);
            wdTxt = viewRoot.findViewById(R.id.wd_txt);

            indexLin.setOnClickListener(this);
            lcLin.setOnClickListener(this);
            wdLin.setOnClickListener(this);
        }
    }

    /**
     * 设置 item 点击事件
     * @param listener
     */
    public void setOnNavigationItemSelectedListener(@Nullable OnNavigationItemSelectedListener listener) {
        this.selectedListener = listener;
    }

    /**
     * 设置选中的 item
     *
     * @param position 下标
     */
    public void setSelectedItemId(int position) {
        switch (position) {
            case 0 :
                indexImg.setSelected(true);
                indexTxt.setSelected(true);
                lcImg.setSelected(false);
                lcTxt.setSelected(false);
                wdImg.setSelected(false);
                wdTxt.setSelected(false);
                break;
            case 1 :
                indexImg.setSelected(false);
                indexTxt.setSelected(false);
                lcImg.setSelected(true);
                lcTxt.setSelected(true);
                wdImg.setSelected(false);
                wdTxt.setSelected(false);
                break;
            case 2 :
                indexImg.setSelected(false);
                indexTxt.setSelected(false);
                lcImg.setSelected(false);
                lcTxt.setSelected(false);
                wdImg.setSelected(true);
                wdTxt.setSelected(true);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.index_lin) {
            if (selectedListener != null) {
                selectedListener.onNavigationItemSelected(0);
                setSelectedItemId(0);
            }
        } else if (id == R.id.lc_lin) {
            if (selectedListener != null) {
                selectedListener.onNavigationItemSelected(1);
                setSelectedItemId(1);
            }
        } else if (id == R.id.wd_lin) {
            if (selectedListener != null) {
                selectedListener.onNavigationItemSelected(2);
                setSelectedItemId(2);
            }
        }
    }

    /**
     * item 选择监听器
     */
    public interface OnNavigationItemSelectedListener {
        void onNavigationItemSelected(int position);
    }
}
