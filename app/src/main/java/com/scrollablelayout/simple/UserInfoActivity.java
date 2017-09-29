package com.scrollablelayout.simple;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scrollablelayout.ScrollableLayout;
import com.scrollablelayout.simple.base.BaseActivity;
import com.scrollablelayout.simple.base.BaseFragment;
import com.scrollablelayout.simple.widget.CustomPtrHeader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


@SuppressWarnings("ALL")
public class UserInfoActivity extends BaseActivity
        implements ViewPager.OnPageChangeListener,
        PtrHandler,
        View.OnClickListener {

    @Bind(R.id.pfl_root)
    PtrClassicFrameLayout pfl_root;
    @Bind(R.id.sl_root)
    ScrollableLayout sl_root;
    @Bind(R.id.vp_scroll)
    ViewPager vp_scroll;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_right)
    TextView tv_right;
    @Bind(R.id.iv_spit)
    ImageView iv_spit;
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_signature)
    TextView tv_signature;
    @Bind(R.id.iv_avatar)
    ImageView iv_avatar;
    @Bind(R.id.ly_page1)
    RelativeLayout ly_page1;
    @Bind(R.id.tv_page1)
    TextView tv_page1;
    @Bind(R.id.ly_page2)
    RelativeLayout ly_page2;
    @Bind(R.id.tv_page2)
    TextView tv_page2;

    private float titleMaxScrollHeight;
    private float hearderMaxHeight;
    private float avatarTop;
    private float maxScrollHeight;
    private final List<BaseFragment> fragmentList = new ArrayList<>();

    @Override
    protected int initContentView() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected void initUi() {
        initView();
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initListener() {
        tv_right.setOnClickListener(this);
        tv_signature.setOnClickListener(this);
        ly_page1.setOnClickListener(this);
        ly_page2.setOnClickListener(this);
    }

    private void initView() {
        iv_spit.setVisibility(View.GONE);
        tv_title.setTranslationY(-1000);
        sl_root.setOnScrollListener(new ScrollableLayout.OnScrollListener() {

            @Override
            public void onScroll(int translationY, int maxY) {
                translationY = -translationY;
                if (titleMaxScrollHeight == 0) {
                    titleMaxScrollHeight = ((View) tv_title.getParent()).getBottom() - tv_title.getTop();
                    maxScrollHeight = hearderMaxHeight + titleMaxScrollHeight;
                }
                if (hearderMaxHeight == 0) {
                    hearderMaxHeight = tv_name.getTop();
                    maxScrollHeight = hearderMaxHeight + titleMaxScrollHeight;
                }
                if (avatarTop == 0) {
                    avatarTop = iv_avatar.getTop();
                }

                int alpha = 0;
                int baseAlpha = 60;
                if (0 > avatarTop + translationY) {
                    alpha = Math.min(255, (int) (Math.abs(avatarTop + translationY) * (255 - baseAlpha) / (hearderMaxHeight - avatarTop) + baseAlpha));
                    iv_spit.setVisibility(View.VISIBLE);
                } else {
                    iv_spit.setVisibility(View.GONE);
                }

                iv_spit.getBackground().setAlpha(alpha);
                tv_title.setTranslationY(Math.max(0, maxScrollHeight + translationY));
            }
        });

        CustomPtrHeader header = new CustomPtrHeader(this, 1);
        pfl_root.setEnabledNextPtrAtOnce(true);
        pfl_root.setLastUpdateTimeRelateObject(this);
        pfl_root.setPtrHandler(this);
        pfl_root.setKeepHeaderWhenRefresh(true);
        pfl_root.setHeaderView(header);
        pfl_root.addPtrUIHandler(header);

        CommonFragementPagerAdapter commonFragementPagerAdapter = new CommonFragementPagerAdapter(getSupportFragmentManager());
        fragmentList.add(RecyclerViewSimpleFragment.newInstance());
        fragmentList.add(RecyclerViewGridSimpleFragment.newInstance());
        vp_scroll.setAdapter(commonFragementPagerAdapter);
        vp_scroll.addOnPageChangeListener(this);
        sl_root.getHelper().setCurrentScrollableContainer(fragmentList.get(0));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        sl_root.getHelper().setCurrentScrollableContainer(fragmentList.get(position));
        if (position == 0) {
            ly_page1.setBackgroundResource(R.drawable.rectangle_left_select);
            tv_page1.setTextColor(Color.parseColor("#ffffff"));
            ly_page2.setBackgroundResource(R.drawable.rectangle_right);
            tv_page2.setTextColor(Color.parseColor("#435356"));
        } else {
            ly_page1.setBackgroundResource(R.drawable.rectangle_left);
            tv_page1.setTextColor(Color.parseColor("#435356"));
            ly_page2.setBackgroundResource(R.drawable.rectangle_right_select);
            tv_page2.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        if (vp_scroll.getCurrentItem() == 0 && sl_root.isCanPullToRefresh()) {
            return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
        }
        return false;
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        if (fragmentList.size() > vp_scroll.getCurrentItem()) {
            fragmentList.get(vp_scroll.getCurrentItem()).pullToRefresh();
        }
    }

    public void refreshComplete() {
        if (pfl_root != null) {
            pfl_root.refreshComplete();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_signature:
                Uri uri = Uri.parse(tv_signature.getText().toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.tv_right:
                startActivity(new Intent(UserInfoActivity.this, TestActivity.class));
                break;
            case R.id.ly_page1:
                vp_scroll.setCurrentItem(0);
                break;
            case R.id.ly_page2:
                vp_scroll.setCurrentItem(1);
                break;
        }
    }

    public class CommonFragementPagerAdapter extends FragmentPagerAdapter {

        public CommonFragementPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return getCount() > position ? fragmentList.get(position) : null;
        }

        @Override
        public int getCount() {
            return fragmentList == null ? 0 : fragmentList.size();
        }
    }

    @Override
    public void onBackPressed() {
        AppApplication.get(this).finishAllActivity();
        super.onBackPressed();
    }
}
