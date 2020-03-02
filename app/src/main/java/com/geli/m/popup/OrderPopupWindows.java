package com.geli.m.popup;

import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.geli.m.R;

import static com.geli.m.app.GlobalData.mContext;

/**
 * Created by Administrator on 2018/1/25.
 *
 * 订单对应的 -- 弹窗
 */
public class OrderPopupWindows {
    public static PopupWindow newInstance(String message) {

        View inflate = LayoutInflater.from(mContext).inflate(R.layout.popup_submitorder_toast,
                new LinearLayout(mContext), false);
        TextView tv_message = (TextView) inflate.findViewById(R.id.tv_popup_message);
        tv_message.setText(message);
        inflate.measure(0, 0);

        PopupWindow popupWindow = new PopupWindow(inflate.getMeasuredWidth(), inflate.getMeasuredHeight());
        popupWindow.setContentView(inflate);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        return popupWindow;
    }
}
