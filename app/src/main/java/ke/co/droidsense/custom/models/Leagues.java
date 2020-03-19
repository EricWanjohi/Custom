
package ke.co.droidsense.custom.models;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "Leagues")
public class Leagues {

    @SerializedName("leagues")
    @Expose
    private List<League> leagues = null;

    public List<League> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }

}
