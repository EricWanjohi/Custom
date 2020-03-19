package ke.co.droidsense.custom.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import ke.co.droidsense.custom.Converters.LeaguesConverter;
import ke.co.droidsense.custom.models.ResponseModels.LeaguesResult;

import static androidx.room.OnConflictStrategy.REPLACE;

//Dao to handle fetching data from DB.
@TypeConverters(LeaguesConverter.class)
@Dao
public interface LeaguesDao {

    //Query All LeaguesResult
    @Query("SELECT * FROM LeaguesResult")
    LiveData<LeaguesResult> getAllLeagues();

    @Delete
    void deleteLeagues(LeaguesResult leaguesResult);

    @Insert(onConflict = REPLACE)
    void insertLeagues(LeaguesResult leaguesResult);
}
