package com.linxiao.quickdevframework.sample.netapi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linxiao.framework.fragment.BaseFragment;
import com.linxiao.framework.net.CookieMode;
import com.linxiao.framework.net.RetrofitManager;
import com.linxiao.framework.net.HttpInfoCatchInterceptor;
import com.linxiao.framework.net.HttpInfoCatchListener;
import com.linxiao.framework.net.HttpInfoEntity;
import com.linxiao.framework.rx.SampleSubscriber;
import com.linxiao.framework.toast.ToastAlert;
import com.linxiao.quickdevframework.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class NetTestFragment extends BaseFragment {


    @BindView(R.id.tvResponse)
    TextView tvResponse;
    
    NetTestDataManager mDataManager;

    @Override
    protected void onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setContentView(R.layout.fragment_net_test, container);
        ButterKnife.bind(this, getContentView());
        mDataManager = new NetTestDataManager();
    }

    @OnClick(R.id.btnRequestNet)
    public void onRequestTestClick(View v) {
        requestApi();
    }

    public void requestApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        mDataManager.getTestData()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(@NonNull Disposable disposable) throws Exception {
                progressDialog.setMessage("正在请求");
                progressDialog.show();
            }
        })
        .subscribe(new SampleSubscriber<String>(){
            @Override
            public void onNext(@NonNull String responseBody) {
                String result = "Response:\n " + responseBody;
                tvResponse.setText(result);
            }

            @Override
            public void onComplete() {
                progressDialog.dismiss();
            }
        });
    }

}
