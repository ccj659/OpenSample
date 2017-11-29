package com.ccj.emptyview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * 空布局view,包括loading, empty,error三种状态,用viewStub进行了优化
 * Created by chenchangjun on 17/9/7.
 */

public class CommonEmptyView extends RelativeLayout {


    private ViewStub error, empty, loading;
    private View errorInf, emptyInf, loadingInf;
    OnClickListener listener;
    private String emptyBtnMsg = "";

    private void init(Context context, AttributeSet attrs) {

    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        inflate(getContext(), R.layout.common_empty_view, this);
        error = (ViewStub) findViewById(R.id.error);
        empty = (ViewStub) findViewById(R.id.empty);
        loading = (ViewStub) findViewById(R.id.loading);
        //设置监听
    }


    public void showLoadingState(boolean show) {
        clearAllState();
        setVisibility(VISIBLE);
        showLoadingView(show);
    }

    public void showErrorState() {
        clearAllState();
        setVisibility(VISIBLE);
        showErrorView(true);
    }

    public void showEmptyState() {
        clearAllState();
        setVisibility(VISIBLE);
        showEmptyView(true);

    }

    public void clearAllState() {

        if (empty == null) {
            showEmptyView(false);
        }
        if (error == null) {
            showErrorView(false);
        }
        if (loading == null) {
            showLoadingView(false);
        }

        setVisibility(GONE);

    }


    private void showLoadingView(boolean show) {

        if (loadingInf == null) {
            loadingInf = loading.inflate();
            loading = null;
        }

        if (show) {
            loadingInf.setVisibility(View.VISIBLE);
        } else {
            loadingInf.setVisibility(GONE);
        }
    }

    /**
     * 显示错误页面
     */
    private void showErrorView(boolean show) {
        if (errorInf == null) {
            errorInf = error.inflate();
            Button btn_reload = (Button) errorInf.findViewById(R.id.btn_reload);
            btn_reload.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    reloadClickListener.onReload();
                }
            });
            error = null;
        }

        if (show) {
            errorInf.setVisibility(View.VISIBLE);
        } else {
            errorInf.setVisibility(View.GONE);

        }
    }


    private void showEmptyView(boolean show) {

        if (emptyInf == null) {
            emptyInf = empty.inflate();
            if (emptyBtnClickListener != null) {
                Button btn_click = (Button) emptyInf.findViewById(R.id.btn_click);
                btn_click.setVisibility(VISIBLE);
                btn_click.setText(emptyBtnMsg);
                btn_click.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        emptyBtnClickListener.onEmptyBtnClick(v);
                    }
                });
            }

            empty = null;
        }

        if (show) {
            emptyInf.setVisibility(View.VISIBLE);
        } else {
            emptyInf.setVisibility(GONE);
        }
    }

    /**
     * 空白页btn 监听
     */
    public interface OnEmptyBtnClickListener {
        void onEmptyBtnClick(View v);
    }

    private OnEmptyBtnClickListener emptyBtnClickListener;


    public void setOnEmptyBtnClickListener(String emptyBtnMsg,OnEmptyBtnClickListener emptyBtnClickListener) {
        this.emptyBtnClickListener = emptyBtnClickListener;
        this.emptyBtnMsg=emptyBtnMsg;
    }


    /**
     * 重新加载 监听
     */
    public interface OnReloadClickListener {
        void onReload();
    }

    private OnReloadClickListener reloadClickListener;


    public void setOnReloadClickListener(OnReloadClickListener listener) {
        this.reloadClickListener = listener;
    }


    public CommonEmptyView(Context context) {
        super(context);
        init(context, null);
    }

    public CommonEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public CommonEmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CommonEmptyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);

    }

}
