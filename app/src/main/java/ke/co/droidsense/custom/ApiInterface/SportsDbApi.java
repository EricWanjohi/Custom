package ke.co.droidsense.custom.ApiInterface;

import ke.co.droidsense.custom.models.ResponseModels.LeaguesResult;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SportsDbApi {
    //Create Retrofit Call methods.

    //Get All LeaguesResult.
    @GET("api/v1/json/1/all_leagues.php")
    Call<LeaguesResult> getLeagues();

}
