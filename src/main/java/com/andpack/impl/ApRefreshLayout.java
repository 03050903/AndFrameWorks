package com.andpack.impl;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.andframe.api.multistatus.OnRefreshListener;
import com.andframe.api.multistatus.RefreshLayouter;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.Date;

import static com.andframe.R.*;

/**
 * 使用第三方刷新控件 PullRefreshLayout
 * Created by SCWANG on 2016/10/21.
 */

public class ApRefreshLayout implements RefreshLayouter {

    private MaterialRefreshLayout mRefreshLayout;
    private boolean isRefreshing = false;

    public ApRefreshLayout(Context context) {
        this.mRefreshLayout = new MaterialRefreshLayout(context);
        this.mRefreshLayout.setWaveColor(context.getResources().getColor(color.colorPrimary));
    }

    @Override
    public ViewGroup getLayout() {
        return mRefreshLayout;
    }

    @Override
    public void setContenView(View content) {
        mRefreshLayout.addView(content);
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        isRefreshing = refreshing;
        if (refreshing) {
            mRefreshLayout.autoRefresh();
        } else {
            mRefreshLayout.finishRefresh();
        }
    }

    @Override
    public void setOnRefreshListener(OnRefreshListener listener) {
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                if (listener.onRefresh()) {
                    isRefreshing = true;
                } else {
                    setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void setLastRefreshTime(Date date) {
    }

    @Override
    public boolean isRefreshing() {
        return isRefreshing;
    }
}