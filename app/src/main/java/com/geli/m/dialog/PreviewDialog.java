package com.geli.m.dialog;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import com.geli.m.R;
import com.geli.m.dialog.base.BaseDialogFragment;
import com.geli.m.ui.fragment.PreviewFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steam_l on 2018/3/29.
 */

public class PreviewDialog extends BaseDialogFragment implements View.OnClickListener {
    @BindView(R.id.tv_preview_index)
    TextView tv_index;
    @BindView(R.id.vp_preview_content)
    ViewPager vp_content;
    private List<String> imgUrl;
    private List<PreviewFragment> mFragments = new ArrayList<>();

    private PreviewDialog(List<String> imgUrl) {
        this.imgUrl = imgUrl;
    }

    public static PreviewDialog newInstance(List<String> imgUrl) {
        return new PreviewDialog(imgUrl);
    }

    public void setNewurl(List<String> imgUrl) {
        this.imgUrl = imgUrl;
        vp_content.setCurrentItem(0);
    }

    @Override
    protected int getResId() {
        return R.layout.dialog_preview;
    }

    @Override
    protected void initData() {
        tv_index.setText("1/" + imgUrl.size());

        for (String url : imgUrl) {
            mFragments.add(PreviewFragment.newInstance(url));
        }
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return imgUrl.size();
            }

            @Override
            public long getItemId(int position) {
                return imgUrl.get(position).hashCode();
            }
        };
        vp_content.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {
        vp_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_index.setText((position + 1) + "/" + imgUrl.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected EditText getEt() {
        return null;
    }
}
