package com.csmar.lib.base.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.csmar.lib.base.R;

public class LoadingPage extends FrameLayout {
    View inflate;
    TextView textView;

    public LoadingPage(@NonNull Context context) {
        this(context, null);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    private void initLayout() {
        inflate = inflate(getContext(), R.layout.progress_loading, this);
        textView = inflate.getRootView().findViewById(R.id.tvMessage);
    }

    public void setShowContent(String content) {
        if (textView != null) {
            textView.setText(TextUtils.isEmpty(content) ? "" : content);
        }
    }
}
