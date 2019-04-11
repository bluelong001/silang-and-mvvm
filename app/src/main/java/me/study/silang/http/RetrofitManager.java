package me.study.silang.http;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.study.silang.bean.Param;
import me.study.silang.bean.Rest;
import me.study.silang.repository.LoginRepository;

public class RetrofitManager<S> {
    private S service;

    public RetrofitManager(Context context, Class<S> clazz) {
        service = (S)create(context, clazz);
    }


    private S create(Context context, Class<S> clazz) {
        RetrofitHelper retrofitHelper = new RetrofitHelper();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String token = sharedPreferences.getString("SiLangToken", "");
        return retrofitHelper.createService(clazz, token);
    }

    public static void login(String username, String password, RetrofitCallback callback) {
        RetrofitHelper retrofitHelper = new RetrofitHelper();
        retrofitHelper.createService(LoginRepository.class, null).login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }
    public static void register(Param param, RetrofitCallback callback) {
        RetrofitHelper retrofitHelper = new RetrofitHelper();
        retrofitHelper.createService(LoginRepository.class, null).register(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }
    private volatile static RetrofitManager instance;

    public static <S> RetrofitManager getInstance(Context context, Class<S> clazz) {
        instance = null;
        synchronized (RetrofitManager.class) {
            instance = new RetrofitManager<S>(context, clazz);
        }
        return instance;
    }

    public S getService() {
        return service;
    }
}
