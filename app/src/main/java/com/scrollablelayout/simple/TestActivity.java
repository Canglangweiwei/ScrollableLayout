package com.scrollablelayout.simple;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.scrollablelayout.ScrollableLayout;
import com.scrollablelayout.simple.base.BaseActivity;

import butterknife.Bind;


@SuppressWarnings("ALL")
public class TestActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.sl_root)
    ScrollableLayout sl_root;
    @Bind(R.id.vp_scroll)
    ListView listview;

    @Bind(R.id.tv_right)
    TextView tv;

    @Override
    protected int initContentView() {
        return R.layout.activity_test;
    }

    @Override
    protected void initUi() {
        tv.setText("Github");
    }

    @Override
    protected void initDatas() {
        int size = 100;
        String[] stringArray = new String[size];
        for (int i = 0; i < size; ++i) {
            stringArray[i] = "" + i;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringArray);
        listview.setAdapter(adapter);
        sl_root.getHelper().setCurrentScrollableContainer(listview);
    }

    @Override
    protected void initListener() {
        tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://github.com/Canglangweiwei/ScrollableLayout");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
}
