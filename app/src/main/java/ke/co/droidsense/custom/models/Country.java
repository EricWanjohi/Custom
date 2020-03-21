
package ke.co.droidsense.custom.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ke.co.droidsense.custom.Converters.LeaguesByCountryConverter;


@Entity(tableName = "Country")
@TypeConverters(LeaguesByCountryConverter.class)
public class Country {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("countrys")
    @Expose
    private List<countryItem> countryItems = new ArrayList<>();

    //Empty constructor.
    public Country() {
    }

    public List<countryItem> getCountryItems() {
        return countryItems;
    }

    public void setCountryItems(List<countryItem> countryItems) {
        this.countryItems = countryItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
