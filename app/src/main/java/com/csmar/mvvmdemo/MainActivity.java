package com.csmar.mvvmdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csmar.lib.base.Interface.PopDialogInterface;
import com.csmar.lib.base.util.BarUtils;
import com.csmar.lib.base.util.LogUtil;
import com.csmar.lib.base.view.CustomPopWindow;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements View.OnLayoutChangeListener {
    private Button login;
    private TextView tx;
    private boolean isShow; // 虚拟导航栏是否显示
    private CustomPopWindow customPopWindow;

    // 在初始化View的时候通过findViewById查找activity的根布局容器：
    private ViewGroup mRootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.button);
        tx = findViewById(R.id.text);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tx.setVisibility(View.GONE);
                if (customPopWindow == null) {
                    customPopWindow = new CustomPopWindow.Builder(MainActivity.this)
                            .setClickListener(new PopDialogInterface() {
                                @Override
                                public void onClick(int index) {
                                    switch (index) {
                                        case 0 : // 平台退出
                                            break;
                                        case 1 : // 系统退出
                                    }
                                    if (customPopWindow != null) {
                                        customPopWindow.dismiss();
                                    }
                                }
                            })
                            .build();
                }
            }
        });
        // 第一步获取底部导航栏是否存在
        isShow = checkDeviceHasNavigationBar(MainActivity.this);
        mRootLayout = findViewById(android.R.id.content);
        // 第二步 实现（activity implements View.OnLayoutChangeListener）并设置布局监听器
        mRootLayout.addOnLayoutChangeListener(this);
    }

    /**
     * 监听事件回调
     */
    @Override
    public void onLayoutChange(View v, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
        String s = v.getHeight() + "--" + getScreenHeight() + "---" + getScreenStatusBarHeight() + "---" + BarUtils.getNavBarHeight() + "---" + checkNavigationBarShow(MainActivity.this, getWindow()) + "---" + checkDeviceHasNavigationBar(MainActivity.this);
        LogUtil.d("onLayoutChange--",s);
        // 导航栏之前的状态 与监听器里的变化作比较 且弹框不为null
        if(isShow != checkDeviceHasNavigationBar(MainActivity.this)) {
            isShow=!isShow;
            if (customPopWindow != null) {
                // 这边可能需要设置新的高度
                customPopWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
//                customPopWindow.setHeight(getScreenHeight());

            }
        }
    }

    @Override
    protected void onDestroy() {
        // 第三步 页面销毁，需要移除该监听事件
        if (mRootLayout != null) {
            mRootLayout.removeOnLayoutChangeListener(this);
        }
        super.onDestroy();
    }

    /**
     * 获取设备的状态栏高度(px)
     *
     * @return 设备的状态栏高度(px)
     */
    private int getScreenStatusBarHeight() {
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            return getResources().getDimensionPixelSize(resourceId);
        }
        return -1;
    }

    /**
     * 获取当前屏幕高度(px)
     *
     * @return 当前屏幕高度(px)
     */
    private int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 判断虚拟导航是否开启
     * @param context
     * @param window
     * @return
     */
    public static boolean checkNavigationBarShow(Context context, Window window) {
        boolean show;
        Display display = window.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getRealSize(point);

        View decorView = window.getDecorView();
        Configuration conf = context.getResources().getConfiguration();
        if (Configuration.ORIENTATION_LANDSCAPE == conf.orientation) {
            View contentView = decorView.findViewById(android.R.id.content);
            show = (point.x != contentView.getWidth());
        } else {
            Rect rect = new Rect();
            decorView.getWindowVisibleDisplayFrame(rect);
            show = (rect.bottom != point.y);
        }
        return show;
    }

    //获取是否存在NavigationBar
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
        }
        return hasNavigationBar;
    }

}