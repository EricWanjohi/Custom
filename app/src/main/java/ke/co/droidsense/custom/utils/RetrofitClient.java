package ke.co.droidsense.custom.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    //Create Retrofit Instance.
    private static Retrofit retrofit;
    private static String Base_Url = "https://www.thesportsdb.com/";

    //Get Retrofit Instance.
    public static Retrofit getRetrofit() {
        //Check if instance is null.
        if (retrofit == null) {
            //Create new Instance.
            retrofit = new Retrofit.Builder()
                    .baseUrl( Base_Url )
                    .addConverterFactory( GsonConverterFactory.create() )
                    .build();
        }
        //Else return instance.
        return retrofit;
    }
}
