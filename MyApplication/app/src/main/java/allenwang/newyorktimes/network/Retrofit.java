package allenwang.newyorktimes.network;

import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by allenwang on 2017/2/26.
 */

public class Retrofit {
    private static final String BASE_URL = "https://api.nytimes.com/";

    private static OkHttpClient okHttp;
    private static retrofit2.Retrofit retrofit;
    private static Retrofit instance;

    public static Retrofit getInstance() {
        if (instance == null)
            synchronized (Retrofit.class) {
                if (instance == null)
                    instance = new Retrofit();
            }
        return (instance);
    }

    private Retrofit() {
        okHttp = new OkHttpClient.Builder().build();
        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttp)
                .build();
    }

    public <T> T createService(Class<T> clz) {
        return retrofit.create(clz);
    }


}
