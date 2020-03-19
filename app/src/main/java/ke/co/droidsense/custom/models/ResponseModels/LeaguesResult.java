
package ke.co.droidsense.custom.models.ResponseModels;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

import ke.co.droidsense.custom.Converters.LeaguesConverter;
import ke.co.droidsense.custom.models.Items.League;

@Parcel
@TypeConverters(LeaguesConverter.class)
@Entity(tableName = "LeaguesResult")
public class LeaguesResult {

    @PrimaryKey(autoGenerate = true)
    int id;

    @SerializedName("leagues")
    @Expose
    List<League> leagues = null;

    //Empty constructor.
    public LeaguesResult() {
    }

    public List<League> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
