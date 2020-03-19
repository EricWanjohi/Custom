
package ke.co.droidsense.custom.models.ResponseModels;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ke.co.droidsense.custom.Converters.LeaguesConverter;
import ke.co.droidsense.custom.models.Items.League;

@TypeConverters(LeaguesConverter.class)
@Entity(tableName = "LeaguesResult")
public class LeaguesResult {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("leagues")
    @Expose
    private List<League> leagues = null;

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
