package me.study.silang.http;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.*;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class RetrofitHelper {
    <S> S createService(Class<S> serviceClass, String authToken) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.199.225:8080/api/");

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();


        OkHttpClient okHttpClient = okHttpClientBuilder.build();

        if (null!=authToken&&!authToken.equals("")) {
            okHttpClient = okHttpClientBuilder
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();
                            Request.Builder requestBuilder = original.newBuilder()
                                    .header("Authorization", authToken)
                                    .method(original.method(), original.body());

                            Request request = requestBuilder.build();
                            return chain.proceed(request);
                        }
                    }).build();
        } else
            okHttpClient = okHttpClientBuilder.build();

        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                String datetime = json.getAsJsonPrimitive().getAsString().replace("T", " ");
                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                try {
                    return format.parse(datetime);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return new Date();
                }
            }
        }).registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, type, jsonDeserializationContext) -> {
            String datetime = json.getAsJsonPrimitive().getAsString().replace("T", " ");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            try {
                return format.parse(datetime);
            } catch (ParseException e) {
                e.printStackTrace();
                return new Date();
            }
        }).create());

        Retrofit retrofit = builder
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }
}

