package ke.co.droidsense.custom.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import ke.co.droidsense.custom.Converters.LeaguesConverter;
import ke.co.droidsense.custom.models.Leagues;

import static androidx.room.OnConflictStrategy.REPLACE;

//Dao to handle fetching data from DB.
@TypeConverters(LeaguesConverter.class)
@Dao
public interface LeaguesDao {

    //Query All Leagues
    @Query("SELECT * FROM Leagues")
    LiveData<Leagues> getAllLeagues();

    @Delete
    void deleteLeagues(Leagues leagues);

    @Insert(onConflict = REPLACE)
    void insertLeagues(Leagues leagues);
}
