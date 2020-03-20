package ke.co.droidsense.custom.ApiInterface;

import ke.co.droidsense.custom.models.Country;
import ke.co.droidsense.custom.models.ResponseModels.LeaguesResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SportsDbApi {
    //Create Retrofit Call methods.

    //Get All LeaguesResult.
    @GET("api/v1/json/1/all_leagues.php")
    Call<LeaguesResult> getLeagues();

    //Get All Leagues in a Country by country.
    @GET("api/v1/json/1/search_all_leagues.php")
    Call<Country> getLeaguesByCountry(@Query("c") String country);

}
