
package ke.co.droidsense.custom.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ke.co.droidsense.custom.Converters.LeaguesByCountryConverter;


@Entity(tableName = "Country")
@TypeConverters(LeaguesByCountryConverter.class)
public class Country {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("countrys")
    @Expose
    private List<Country_> countrys = null;

    //Empty constructor.
    public Country() {
    }

    public List<Country_> getCountrys() {
        return countrys;
    }

    public void setCountrys(List<Country_> countrys) {
        this.countrys = countrys;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
