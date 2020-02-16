package net.devwiki.news;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPI {

    @GET("/toutiao/index")
    Observable<NewsResponse> getNews(@Query("key") String key,
                                     @Query("type") String type);
}
