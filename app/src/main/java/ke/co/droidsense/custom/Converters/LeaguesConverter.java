package ke.co.droidsense.custom.Converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ke.co.droidsense.custom.models.Items.League;

public class LeaguesConverter {

    //All League Converter...
    @TypeConverter
    public static List<League> fromLeagueString(String value) {
        Type listType = new TypeToken<List<League>>() {
        }.getType();
        return new Gson().fromJson( value, listType );
    }

    @TypeConverter
    public static String fromLeagueList(List<League> list) {
        Gson gson = new Gson();
        String json = gson.toJson( list );
        return json;
    }

    //All LeaguesResult Converter.

}
