package com.geli.m.coustomview;

import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

/**
 * Created by Steam_l on 2017/12/20.
 *
 * "简易列表的(easyrecyclerview)"分隔线
 */
public class MyDividerDecoration extends DividerDecoration {

    private ColorDrawable mColorDrawable;
    private int mHeight;
    private boolean mDrawLastItem = true;
    private boolean mDrawHeaderFooter = false;
    private int mPaddingLeft;
    private int mPaddingRight;

    public MyDividerDecoration(int color, int height) {
        super(color, height);
        this.mColorDrawable = new ColorDrawable(color);
        this.mHeight = height;
    }

    public MyDividerDecoration(int color, int height, int paddingLeft, int paddingRight) {
        super(color, height, paddingLeft, paddingRight);
        this.mColorDrawable = new ColorDrawable(color);
        this.mHeight = height;
        this.mPaddingLeft = paddingLeft;
        this.mPaddingRight = paddingRight;
    }

    public void setDrawLastItem(boolean mDrawLastItem) {
        this.mDrawLastItem = mDrawLastItem;
    }

    public void setDrawHeaderFooter(boolean mDrawHeaderFooter) {
        this.mDrawHeaderFooter = mDrawHeaderFooter;
    }

    boolean verticalLineShow = true;
    boolean horizontalLineShow = true;

    public void setVerticalLineShow(boolean isShow) {
        verticalLineShow = isShow;
    }

    public void setHorizontalLineShow(boolean isShow) {
        horizontalLineShow = isShow;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        if (parent.getAdapter() == null) {
            return;
        }

        int orientation = 0;
        int headerCount = 0, footerCount = 0, dataCount;

        if (parent.getAdapter() instanceof RecyclerArrayAdapter) {
            headerCount = ((RecyclerArrayAdapter) parent.getAdapter()).getHeaderCount();
            footerCount = ((RecyclerArrayAdapter) parent.getAdapter()).getFooterCount();
            dataCount = ((RecyclerArrayAdapter) parent.getAdapter()).getCount();
        } else {
            dataCount = parent.getAdapter().getItemCount();
        }
        int dataStartPosition = headerCount;
        int dataEndPosition = headerCount + dataCount;

        int di = 1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
        } else if (layoutManager instanceof GridLayoutManager) {
            di = ((GridLayoutManager) layoutManager).getSpanCount();
            orientation = ((GridLayoutManager) layoutManager).getOrientation();
        } else if (layoutManager instanceof LinearLayoutManager) {
            orientation = ((LinearLayoutManager) layoutManager).getOrientation();
        }
        int start, end;
        int childCount;
        if (horizontalLineShow) {
            start = parent.getPaddingLeft() + mPaddingLeft;
            end = parent.getWidth() - parent.getPaddingRight() - mPaddingRight;
            childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                int position = parent.getChildAdapterPosition(child);

                if (position >= dataStartPosition && position < dataEndPosition - di    // 数据项除了最后一项
                        || (position == dataEndPosition - 1 && mDrawLastItem)           // 数据项最后一项
                        || (!(position >= dataStartPosition && position < dataEndPosition) && mDrawHeaderFooter)    // header&footer且可绘制
                        ) {
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    int top = child.getBottom() + params.bottomMargin;
                    int bottom = top + mHeight;
                    mColorDrawable.setBounds(start, top, end, bottom);
                    mColorDrawable.draw(c);
                }
            }
        }
        //
        if (verticalLineShow) {
            start = parent.getPaddingTop() + mPaddingLeft;
            end = parent.getHeight() - parent.getPaddingBottom() - mPaddingRight;
            childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                int position = parent.getChildAdapterPosition(child);

                if (position >= dataStartPosition && position < dataEndPosition - di// 数据项除了最后一项
                        || (position == dataEndPosition - 1 && mDrawLastItem)       // 数据项最后一项
                        || (!(position >= dataStartPosition && position < dataEndPosition) && mDrawHeaderFooter)    // header&footer且可绘制
                        ) {

                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    int left = child.getRight() + params.rightMargin;
                    int right = left + mHeight;
                    mColorDrawable.setBounds(left, start, right, end);
                    mColorDrawable.draw(c);
                }
            }
        }
    }
}
