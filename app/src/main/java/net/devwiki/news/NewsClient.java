package net.devwiki.news;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class NewsClient {

    private static final String BASE_URL = "https://v.juhe.cn";
    private static final String KEY = "2d0adc34ee4b54c6a379555258197455";

    private NewsAPI newsAPI;

    private static class InstanceHolder {
        private static final NewsClient INSTANCE = new NewsClient();
    }

    public static NewsClient getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private NewsClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .addNetworkInterceptor(loggingInterceptor)
                .addInterceptor(new HeaderInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        newsAPI = retrofit.create(NewsAPI.class);
    }

    public Observable<NewsResponse> getNews(String type) {
        return newsAPI.getNews(KEY, type).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
