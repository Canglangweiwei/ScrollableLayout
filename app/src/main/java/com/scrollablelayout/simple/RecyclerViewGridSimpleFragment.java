package com.scrollablelayout.simple;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scrollablelayout.simple.adapter.PhotoAdapter;
import com.scrollablelayout.simple.base.BaseFragment;
import com.scrollablelayout.simple.bean.RecyclerBean;
import com.scrollablelayout.simple.util.DataUtil;

import java.util.List;

import butterknife.Bind;

public class RecyclerViewGridSimpleFragment extends BaseFragment {

    @Bind(R.id.v_scroll)
    RecyclerView recyclerView;

    public static RecyclerViewGridSimpleFragment newInstance() {
        return new RecyclerViewGridSimpleFragment();
    }

    @Override
    public int initContentView() {
        return R.layout.fragment_common_recycler;
    }

    @Override
    protected void initUi() {
        initRecyclerView();
    }

    @Override
    protected void initDatas() {
        List<RecyclerBean> data = DataUtil.createItemList();
        PhotoAdapter recyclerAdapter = new PhotoAdapter();
        recyclerAdapter.setPhotos(data);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    protected void initListener() {

    }

    public void initRecyclerView() {
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    @Override
    public View getScrollableView() {
        return recyclerView;
    }

    @Override
    public void pullToRefresh() {

    }

    @Override
    public void refreshComplete() {

    }
}
