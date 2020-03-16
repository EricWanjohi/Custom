package ke.co.droidsense.custom.ApiInterface;

import java.util.List;

import ke.co.droidsense.custom.models.Leagues;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SportsDbApi {
    //Create Retrofit Call methods.

    //Get All Leagues.
    @GET("all_leagues.php")
    Call<List<Leagues>> getLeagues();

}
