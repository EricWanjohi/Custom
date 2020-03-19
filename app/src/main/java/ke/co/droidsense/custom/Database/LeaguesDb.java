package ke.co.droidsense.custom.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import ke.co.droidsense.custom.Converters.LeaguesConverter;
import ke.co.droidsense.custom.Daos.LeaguesDao;
import ke.co.droidsense.custom.models.Items.League;
import ke.co.droidsense.custom.models.ResponseModels.LeaguesResult;

@TypeConverters(LeaguesConverter.class)
@Database(entities = {LeaguesResult.class, League.class}, version = 5, exportSchema = false)
public abstract class LeaguesDb extends RoomDatabase {
    //Fields...
    private static final String DATABASE = "AllLeaguesDb";
    private static LeaguesDb leaguesDbInstance;

    //Migrations.
    static final Migration MIGRATION_1_2 = new Migration( 1, 2 ) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

    static final Migration MIGRATION_2_3 = new Migration( 2, 3 ) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };


    static final Migration MIGRATION_3_4 = new Migration( 3, 4 ) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

    static final Migration MIGRATION_4_5 = new Migration( 4, 5 ) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

    //Create Getter for DB instance.
    public static LeaguesDb getLeaguesDbInstance(Context context) {
        //Check if Instance is null.
        if (leaguesDbInstance == null) {
            //Create new Instance.
            leaguesDbInstance = Room.databaseBuilder( context, LeaguesDb.class, DATABASE )
                    .fallbackToDestructiveMigration()
                    .build();
        }
        //Else return Instance.
        return leaguesDbInstance;
    }

    //Create dao access object
    public abstract LeaguesDao leaguesDao();

}
