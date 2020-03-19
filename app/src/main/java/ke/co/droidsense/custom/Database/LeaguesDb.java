package ke.co.droidsense.custom.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ke.co.droidsense.custom.Daos.LeaguesDao;
import ke.co.droidsense.custom.models.Leagues;

@Database(entities = Leagues.class, version = 2)
public abstract class LeaguesDb extends RoomDatabase {
    //Fields...
    private static final String DATABASE = "AllLeaguesDb";
    private static LeaguesDb leaguesDbInstance;

    //Create Getter for DB instance.
    public static LeaguesDb getLeaguesDbInstance(Context context) {
        //Check if Instance is null.
        if (leaguesDbInstance == null) {
            //Create new Instance.
            leaguesDbInstance = Room.databaseBuilder( context, LeaguesDb.class, DATABASE )
                    .fallbackToDestructiveMigrationFrom()
                    .build();
        }
        //Else return Instance.
        return leaguesDbInstance;
    }

    //Create dao access object
    public abstract LeaguesDao leaguesDao();

}
