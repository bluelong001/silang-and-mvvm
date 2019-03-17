package me.study.silang.http;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import io.reactivex.observers.DisposableObserver;
import me.study.silang.R;
import me.study.silang.base.bean.Rest;
import me.study.silang.ui.login.LoginActivity;
import retrofit2.HttpException;

public abstract class RetrofitCallback<M> extends DisposableObserver<M> {
    private static final String TAG = "RetrofitCallback";

    public abstract void onSuccess(Object model);

    public abstract void onFailure(String msg);

    public void onInvalid() {
        Toast.makeText(context, "由于你长时间没有操作或者账号失效了，请重新登录！", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public abstract void onFinish();

    public Context context;

    public void bindContext(Context context) {
        this.context = context;
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError: ", e);
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            //httpException.response().errorBody().string()
            int code = httpException.code();
            String msg = httpException.getMessage();
            //LogUtil.d("code=" + code);
            if (code == 504) {
                msg = context.getResources().getString(R.string.error_504);
            }
            if (code == 502) {
                msg = context.getResources().getString(R.string.error_502);
            }
            if (code == 404 || code == 500) {
                msg = context.getResources().getString(R.string.error_404_and_500);
            }
            onFailure(msg);
        } else {
            if (e.getMessage().toLowerCase().contains("failed to connect to")) {
                onFailure(context.getResources().getString(R.string.error_network));
                return;
            }
            onFailure(context.getResources().getString(R.string.error_network));
        }
        onFinish();
    }


    @Override
    public void onNext(M model) {
        if (isRest(model)) {
            Rest r = toRest(model);
            switch (r.getStatus()) {
                case 200:
                    onSuccess(r.getData());
                    break;
                case 201:
                    onFailure(r.getMsg());
                    break;
                case 202:
                    onInvalid();
                    break;
                case 203:
                    onFailure(context.getResources().getString(R.string.error_203));
                    break;
                case 205:
                    onFailure(context.getResources().getString(R.string.error_205));
                    break;
                case 206:
                    onFailure(context.getResources().getString(R.string.error_206));
                    break;
            }
        } else
            onSuccess(model);
    }

    @Override
    public void onComplete() {
        onFinish();
    }

    private boolean isRest(M obj) {
        Class<Rest> clazz = Rest.class;
        if (obj == null) {
            return false;
        }
        Rest retObject = null;
        try {
            retObject = clazz.cast(obj);
        } catch (ClassCastException e) {
            return false;
        }
        return true;
    }

    private Rest toRest(M obj) {
        Class<Rest> clazz = Rest.class;
        return clazz.cast(obj);
    }

}
