package ke.co.droidsense.custom.ApiInterface;

import ke.co.droidsense.custom.models.Leagues;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SportsDbApi {
    //Create Retrofit Call methods.

    //Get All Leagues.
    @GET("all_leagues.php")
    Call<Leagues> getLeagues();

}
