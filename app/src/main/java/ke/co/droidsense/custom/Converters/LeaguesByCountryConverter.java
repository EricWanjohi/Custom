package ke.co.droidsense.custom.Converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ke.co.droidsense.custom.models.Country;
import ke.co.droidsense.custom.models.countryItem;

public class LeaguesByCountryConverter {

    //Country list converter.
    //All League Converter...
    @TypeConverter
    public static List<countryItem> fromCountry_String(String value) {
        Type listType = new TypeToken<List<countryItem>>() {
        }.getType();
        return new Gson().fromJson( value, listType );
    }

    @TypeConverter
    public static String fromCountry_List(List<countryItem> list) {
        Gson gson = new Gson();
        String json = gson.toJson( list );
        return json;
    }

    @TypeConverter
    public static Country fromCountryString(String value) {
        Type listType = new TypeToken<Country>() {
        }.getType();
        return new Gson().fromJson( value, listType );
    }

    @TypeConverter
    public static String fromCountryList(Country list) {
        Gson gson = new Gson();
        String json = gson.toJson( list );
        return json;
    }
}
