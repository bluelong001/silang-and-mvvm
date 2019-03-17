package me.study.silang.http;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.*;
import me.study.silang.bean.ServerConfig;
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

    private final String BASE_URL = ServerConfig.DEV.getIp();
    private static Context mContext;
    private RetrofitInterface retrofitInterface;
    private ClearableCookieJar cookieJar =
            new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(mContext));
    private OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder().cookieJar(cookieJar)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS);
    private OkHttpClient okHttpClient = new OkHttpClient();
    private GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
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
    }).registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
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
    }).create());
    //由于该对象会被频繁调用，采用单例模式，下面是一种线程安全模式的单例写法
    private volatile static RetrofitHelper instance;

    public static RetrofitHelper getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            synchronized (RetrofitHelper.class) {
                if (instance == null) {
                    instance = new RetrofitHelper();
                }
            }
        }
        return instance;
    }

    private RetrofitHelper() {
        init();
    }

    private void init() {
        retrofitInterface = createService(RetrofitInterface.class);
    }

    public void reload() {
        init();
    }

    public RetrofitInterface getRetrofitApiService() {
        return retrofitInterface;
    }

    private <S> S createService(Class<S> serviceClass) {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
//        String token = sharedPreferences.getString("silangToken","");
        return createService(serviceClass, null);
    }

    private Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL);

    private <S> S createService(Class<S> serviceClass, final String authToken) {
        if (TextUtils.isEmpty(authToken)) {
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

        Retrofit retrofit = builder
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }
}

