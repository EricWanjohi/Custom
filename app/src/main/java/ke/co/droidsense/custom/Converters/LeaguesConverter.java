package ke.co.droidsense.custom.Converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ke.co.droidsense.custom.models.League;

public class LeaguesConverter {

    //All Leagues Converter...
    @TypeConverter
    public static List<League> fromLeaguesString(String value) {
        Type listType = new TypeToken<List<League>>() {
        }.getType();
        return new Gson().fromJson( value, listType );
    }

    @TypeConverter
    public static String fromLeaguesList(List<League> list) {
        Gson gson = new Gson();
        String json = gson.toJson( list );
        return json;
    }
}
