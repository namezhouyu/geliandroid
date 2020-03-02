package com.geli.m.coustomview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.utils.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.geli.m.app.GlobalData.mContext;

/**
 * Created by fynn on 16/5/21.
 * 流式布局
 */
public class FluidLayout extends ViewGroup implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private int mGravity = Gravity.TOP;
    public boolean isRadio = true;
    private List<List<View>> mViews = new ArrayList<List<View>>();
    private List<Integer> mLineHeight = new ArrayList<Integer>();
    private LayoutInflater mInflater;
    private float mChildMarginTop;
    private float mChildMarginLeft;
    private boolean mFirstUseMargin;

    public FluidLayout(Context context) {
        this(context, null);
    }

    public FluidLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FluidLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        setGravity(Gravity.CENTER);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FluidLayout);
        try {
            mChildMarginLeft = array.getDimension(R.styleable.FluidLayout_child_marginLeft, 0);
            mChildMarginTop = array.getDimension(R.styleable.FluidLayout_child_marginTop, 0);
            mFirstUseMargin = array.getBoolean(R.styleable.FluidLayout_child_firstMargin, false);
        } finally {
            array.recycle();
        }
    }

    private List<TextView> recyclerTvList = new ArrayList<>();

    /**
     * 使用该方法获得textview,防止new多余的对象
     *
     * @param titles
     * @return
     */
    public List<TextView> getView(List<String> titles) {
        if (recyclerTvList.size() < titles.size()) {
            int addSise = titles.size() - recyclerTvList.size();
            for (int i = 0; i < addSise; i++) {
                CheckBox e = newCb();
                e.setOnCheckedChangeListener(this);
                recyclerTvList.add(e);
            }
        } else {
            for (int i = recyclerTvList.size(); i > titles.size(); i--) {
                recyclerTvList.remove(i - 1);
            }
        }

        for (int i = 0; i < titles.size(); i++) {
            recyclerTvList.get(i).setText(titles.get(i));
        }

        return recyclerTvList;
    }

    private TextView newTv() {
        MarginLayoutParams mp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //         new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mp.leftMargin = Utils.dip2px(getContext(), 5);
        mp.rightMargin = Utils.dip2px(getContext(), 5);
        mp.topMargin = Utils.dip2px(getContext(), 5);
        mp.bottomMargin = Utils.dip2px(getContext(), 5);
        TextView textView = new TextView(mContext);
        textView.setBackgroundColor(Color.WHITE);
        textView.setLayoutParams(mp);
        int padding5 = Utils.dip2px(getContext(), 5);
        int padding2 = Utils.dip2px(getContext(), 2);
        textView.setPadding(padding5, padding2, padding5, padding2);
        return textView;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    int drawableId = -1;

    private CheckBox newCb() {
        MarginLayoutParams mp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mp.leftMargin = (int) mChildMarginLeft;
        mp.topMargin = (int) mChildMarginTop;
        CheckBox checkBox = (CheckBox) mInflater.inflate(R.layout.itemview_overseas_cb, null);
        if (drawableId != -1) {
            checkBox.setBackgroundResource(drawableId);
        }
        checkBox.setLayoutParams(mp);
        return checkBox;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;
        int height = 0;

        int lineWidth = 0;
        int lineHeight = 0;

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if (lineWidth + childWidth > widthSize - getPaddingLeft() - getPaddingRight()) {
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }

            if (i == count - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }
        setMeasuredDimension(
                widthMode == MeasureSpec.EXACTLY ? widthSize : width + getPaddingLeft() + getPaddingRight(),
                heightMode == MeasureSpec.EXACTLY ? heightSize : height + getPaddingTop() + getPaddingBottom()
        );
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mViews.clear();
        mLineHeight.clear();

        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;

        List<View> lineViews = new ArrayList<View>();
        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width - getPaddingLeft() - getPaddingRight()) {
                //超过一行
                mLineHeight.add(lineHeight);//行view高度
                mViews.add(lineViews);//行view集合

                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                lineViews = new ArrayList<View>();
            }
            if (!mFirstUseMargin) {
                if (mViews.size() == 0) {//第一行
                    lp.topMargin = 0;
                } else {
                    lp.topMargin = (int) mChildMarginTop;
                }
                if (lineViews.size() == 0) {//第一个
                    lp.leftMargin = 0;
                } else {
                    lp.leftMargin = (int) mChildMarginLeft;
                }
                child.setLayoutParams(lp);
            }

            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
            lineViews.add(child);
        }
        //记录每行高度，为后面设置子view单独top做准备，比如gravity
        //最后一个view,有可能没超过一行,主动添加
        mLineHeight.add(lineHeight);
        mViews.add(lineViews);

        int left = getPaddingLeft();
        int top = getPaddingTop();

        int lineCount = mViews.size();

        for (int i = 0; i < lineCount; i++) {
            lineViews = mViews.get(i);
            lineHeight = mLineHeight.get(i);
            int lineViewCount = lineViews.size();
            for (int j = 0; j < lineViewCount; j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == GONE) {
                    continue;
                }

                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                //                if (!mFirstUseMargin) {
                //                    if (i == 0) {//第一行
                //                        lp.topMargin = 0;
                //                    }
                //                    if (left == 0) {//第一个
                //                        lp.leftMargin = 0;
                //                    }
                //                }
                int gravity = lp.gravity;
                if (gravity < 0) {
                    gravity = mGravity;
                }

                int tmpTop = top;
                if (gravity == Gravity.CENTER) {
                    tmpTop = top + (lineHeight - lp.topMargin - lp.bottomMargin - child.getMeasuredHeight()) / 2;
                } else if (gravity == Gravity.BOTTOM) {
                    tmpTop = top + (lineHeight - lp.topMargin - lp.bottomMargin - child.getMeasuredHeight());
                }

                int lv = left + lp.leftMargin;
                int tv = tmpTop + lp.topMargin;
                int rv = lv + child.getMeasuredWidth();
                int bv = tv + child.getMeasuredHeight();

                child.layout(lv, tv, rv, bv);
                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }

            left = getPaddingLeft();
            top += lineHeight;
        }
    }

    public void setGravity(int gravity) {
        if (mGravity != gravity) {
            mGravity = gravity;
            requestLayout();
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }


    @Override
    public void onClick(View v) {

    }

    public String getCbTag() {
        String tag = "";
        List<CheckBox> allView = getAllView();
        for (CheckBox childe : allView) {
            if (childe.isChecked()) {
                tag = (String) childe.getTag();
            }
        }
        return tag;
    }

    public Map<Integer, Boolean> cbState = new HashMap<>();

    public void initCbState() {
        int i = 0;
        List<CheckBox> allView = getAllView();
        for (CheckBox childe : allView) {
            childe.setEnabled(cbState.get(new Integer(i)));
            i++;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (mListenerList.size() > 0) {
            if (isRadio) {
                if (isChecked) {
                    List<CheckBox> allView = getAllView();
                    cbState.clear();
                    int i = 0;
                    for (CheckBox childe : allView) {
                        cbState.put(new Integer(i), childe.isEnabled());

                            if (childe != buttonView) {
                                childe.setChecked(false);
                                childe.setEnabled(true);

                            } else {
                                childe.setEnabled(false);
                            }

                        i++;
                    }
                }
            }
            for (CheckBoxClickListener boxClickListener : mListenerList) {
                boxClickListener.onCheckBoxClick(buttonView);
            }
        }
    }

    private List<CheckBoxClickListener> mListenerList = new ArrayList<>();

    public interface CheckBoxClickListener {
        void onCheckBoxClick(View v);
    }

    public void setCheckBoxClickListener(CheckBoxClickListener listener) {
        mListenerList.add(listener);
    }

    public List getAllView() {
        return recyclerTvList;
    }

    public static class LayoutParams extends MarginLayoutParams {
        public int gravity = -1;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            gravity = Gravity.CENTER;
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(LayoutParams source) {
            super(source);
            this.gravity = source.gravity;
        }
    }
}
