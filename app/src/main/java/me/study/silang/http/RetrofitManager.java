package me.study.silang.http;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.study.silang.bean.Rest;
import me.study.silang.repository.LoginRepository;

public class RetrofitManager<S> {
    private S service;
    private RetrofitManager() {
    }
    private void init(Context context, Class<S> clazz) {
        service = create(context, clazz);
    }


    private S create(Context context, Class<S> clazz) {
        RetrofitHelper retrofitHelper = new RetrofitHelper();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String token = sharedPreferences.getString("SiLangToken", "");
        return retrofitHelper.createService(clazz, token);
    }

    public static void login(String username, String password,RetrofitCallback callback) {
        RetrofitHelper retrofitHelper = new RetrofitHelper();
        retrofitHelper.createService(LoginRepository.class, null).login(username,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    private volatile static RetrofitManager instance;

    public static <S> RetrofitManager getInstance(Context context, Class<S> clazz) {
        if (instance == null) synchronized (RetrofitManager.class) {
            if (instance == null) {
                instance = new RetrofitManager();
                instance.init(context,clazz);
            }
        }
        return instance;
    }

    public static RetrofitManager setInstance(Context context, Class clazz) {
        instance.init(context,clazz);
        return instance;
    }

    public S getService() {
        return service;
    }
}
