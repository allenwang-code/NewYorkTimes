package allenwang.newyorktimes.network;

import allenwang.newyorktimes.model.News;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by allenwang on 2017/2/26.
 */

// https://api.nytimes.com/svc/search/v2/articlesearch.json?
// begin_date=20160112
// &sort=oldest
// &fq=news_desk:(%22Education%22%20%22Health%22)
// &api-key=


public interface NewYorkTimes {
    String apiKey = "227c750bb7714fc39ef1559ef1bd8329";

    @GET("svc/search/v2/articlesearch.json?api-key=227c750bb7714fc39ef1559ef1bd8329")
    Call<News> getNews(@Query("page") String page
                       );
}
