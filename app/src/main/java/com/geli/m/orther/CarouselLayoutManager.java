package com.geli.m.orther;

import android.content.Context;
import android.view.View;

/**
 * An implementation of {@link ViewPagerLayoutManager}
 * which layouts items like carousel
 *
 * 像旋转木马这样的布局项目 (两头小，中间大)
 *
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class CarouselLayoutManager extends ViewPagerLayoutManager {

    /** 项之间的"间隔" */
    private int mItemSpace;
    /** 最小比例 */
    private float mMinScale;
    /** 移动速度 */
    private float mMoveSpeed;

    public CarouselLayoutManager(Context context, int itemSpace) {
        this(new Builder(context, itemSpace));
    }

    public CarouselLayoutManager(Context context, int itemSpace, int orientation) {
        this(new Builder(context, itemSpace).setOrientation(orientation));
    }

    public CarouselLayoutManager(Context context, int itemSpace, int orientation, boolean reverseLayout) {
        this(new Builder(context, itemSpace).setOrientation(orientation).setReverseLayout(reverseLayout));
    }

    public CarouselLayoutManager(Builder builder) {
        this(builder.context, builder.itemSpace, builder.minScale, builder.orientation,
                builder.maxVisibleItemCount, builder.moveSpeed, builder.distanceToBottom,
                builder.reverseLayout);
    }

    private CarouselLayoutManager(Context context, int itemSpace, float minScale, int orientation,
                                  int maxVisibleItemCount, float moveSpeed, int distanceToBottom,
                                  boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        setEnableBringCenterToFront(true);
        setDistanceToBottom(distanceToBottom);
        setMaxVisibleItemCount(maxVisibleItemCount);
        this.mItemSpace = itemSpace;
        this.mMinScale = minScale;
        this.mMoveSpeed = moveSpeed;
    }


    public int getItemSpace() {
        return mItemSpace;
    }

    public float getMinScale() {
        return mMinScale;
    }

    public float getMoveSpeed() {
        return mMoveSpeed;
    }


    /**
     * 设置 -- 项之间的"间隔"
     * @param itemSpace
     */
    public void setItemSpace(int itemSpace) {
        // 检查回收站是否在布局或滚动的中间，如果它是非法的，就会抛出@link非法的异常。
        assertNotInLayoutOrScroll(null);

        if (this.mItemSpace == itemSpace)
            return;

        this.mItemSpace = itemSpace;
        // 从当前附加的回收站视图中删除所有视图。
        // 这将不会回收任何受影响的视图;如果需要，LayoutManager负责这样做。
        removeAllViews();
    }

    /**
     * 设置 -- 最小比例
     * @param minScale
     */
    public void setMinScale(float minScale) {
        // 检查回收站是否在布局或滚动的中间，如果它是非法的，就会抛出@link非法的异常。
        assertNotInLayoutOrScroll(null);

        if (minScale > 1f)                  // 不能超过1
            minScale = 1f;

        if (this.mMinScale == minScale)
            return;

        this.mMinScale = minScale;
        // 调用@code回收器视图在底层回收器视图上的请求布局
        requestLayout();
    }


    /**
     * 设置 -- 移动速度
     * @param moveSpeed
     */
    public void setMoveSpeed(float moveSpeed) {
        // 检查回收站是否在布局或滚动的中间，如果它是非法的，就会抛出@link非法的异常。
        assertNotInLayoutOrScroll(null);

        if (this.mMoveSpeed == moveSpeed)
            return;

        this.mMoveSpeed = moveSpeed;
    }



    @Override
    protected float setInterval() {                 // 设置"项"的距离 ?
        return (mDecoratedMeasurement - mItemSpace);
    }

    @Override
    protected void setItemViewProperty(View itemView, float targetOffset) {
        float scale = calculateScale(targetOffset + mSpaceMain);
        itemView.setScaleX(scale);
        itemView.setScaleY(scale);
    }

    @Override
    protected float getDistanceRatio() {    // 获取"距离比例"
        if (mMoveSpeed == 0)
            return Float.MAX_VALUE;

        return 1 / mMoveSpeed;
    }


    // 因为在api 21下，提升是不支持的，
    // 所以你可以在这里设置你的高度来支持它低于api 21
    // 或者你可以在@link setitemviewproperty（View，float）中进行setElevation
    @Override
    protected float setViewElevation(View itemView, float targetOffset) {
        return itemView.getScaleX() * 5;
    }

    private float calculateScale(float x) {
        float deltaX = Math.abs(x - (mOrientationHelper.getTotalSpace() - mDecoratedMeasurement) / 2f);
        return (mMinScale - 1) * deltaX / (mOrientationHelper.getTotalSpace() / 2f) + 1f;
    }

    public static class Builder {
        private static final float DEFAULT_SPEED = 1f;
        private static final float MIN_SCALE = 0.5f;

        private Context context;
        private int itemSpace;
        private int orientation;
        private float minScale;
        private float moveSpeed;
        private int maxVisibleItemCount;
        private boolean reverseLayout;
        private int distanceToBottom;

        public Builder(Context context, int itemSpace) {
            this.itemSpace = itemSpace;
            this.context = context;
            orientation = HORIZONTAL;
            minScale = MIN_SCALE;
            this.moveSpeed = DEFAULT_SPEED;
            reverseLayout = false;
            maxVisibleItemCount = ViewPagerLayoutManager.DETERMINE_BY_MAX_AND_MIN;
            distanceToBottom = ViewPagerLayoutManager.INVALID_SIZE;
        }

        public Builder setOrientation(int orientation) {
            this.orientation = orientation;
            return this;
        }

        public Builder setMinScale(float minScale) {
            this.minScale = minScale;
            return this;
        }

        public Builder setReverseLayout(boolean reverseLayout) {
            this.reverseLayout = reverseLayout;
            return this;
        }

        public Builder setMoveSpeed(float moveSpeed) {
            this.moveSpeed = moveSpeed;
            return this;
        }

        public Builder setMaxVisibleItemCount(int maxVisibleItemCount) {
            this.maxVisibleItemCount = maxVisibleItemCount;
            return this;
        }

        public Builder setDistanceToBottom(int distanceToBottom) {
            this.distanceToBottom = distanceToBottom;
            return this;
        }

        public CarouselLayoutManager build() {
            return new CarouselLayoutManager(this);
        }
    }
}
