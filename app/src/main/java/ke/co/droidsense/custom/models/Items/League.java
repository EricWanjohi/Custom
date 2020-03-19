
package ke.co.droidsense.custom.models.Items;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import ke.co.droidsense.custom.Converters.LeaguesConverter;

@Parcel
@TypeConverters(LeaguesConverter.class)
@Entity(tableName = "League")
public class League {

    @PrimaryKey(autoGenerate = true)
    int id;
    @SerializedName("idLeague")
    @Expose
    String idLeague;
    @SerializedName("strLeague")
    @Expose
    String strLeague;
    @SerializedName("strSport")
    @Expose
    String strSport;
    @SerializedName("strLeagueAlternate")
    @Expose
    String strLeagueAlternate;

    //Empty Constructor.
    public League() {
    }

    public String getIdLeague() {
        return idLeague;
    }

    public void setIdLeague(String idLeague) {
        this.idLeague = idLeague;
    }

    public String getStrLeague() {
        return strLeague;
    }

    public void setStrLeague(String strLeague) {
        this.strLeague = strLeague;
    }

    public String getStrSport() {
        return strSport;
    }

    public void setStrSport(String strSport) {
        this.strSport = strSport;
    }

    public String getStrLeagueAlternate() {
        return strLeagueAlternate;
    }

    public void setStrLeagueAlternate(String strLeagueAlternate) {
        this.strLeagueAlternate = strLeagueAlternate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
