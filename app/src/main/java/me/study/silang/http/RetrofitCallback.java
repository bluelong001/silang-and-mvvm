package me.study.silang.http;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import io.reactivex.observers.DisposableObserver;
import me.study.silang.R;
import me.study.silang.bean.Rest;
import me.study.silang.ui.login.LoginActivity;
import retrofit2.HttpException;

public abstract class RetrofitCallback<M> extends DisposableObserver<M> {
    private static final String TAG = "RetrofitCallback";

    public abstract void onSuccess(Object model);

    public abstract void onFailure(String msg);

    public void onInvalid() {
        Toast.makeText(context, "Session is invalid, please re-login", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void onFinish(){}

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
                msg = "The status of network is bad!";
            }
            if (code == 502) {
                msg = "Server failure!";
            }
            if (code == 404 || code == 500) {
                msg = "Server down!";
            }
            onFailure(msg);
        } else {
            if (e.getMessage().toLowerCase().contains("failed to connect to")) {
                onFailure("It can't connect to network of server!");
                return;
            } else {
                onFailure(e.getMessage());
                return;
            }
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
                    onFailure("Access define");
                    break;
                case 205:
                    onFailure("Auth fail");
                    break;
                case 206:
                    onFailure("No permission");
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
