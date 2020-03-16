
package ke.co.droidsense.custom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Player {

    @SerializedName("player")
    @Expose
    private List<Player_> player = null;

    public List<Player_> getPlayer() {
        return player;
    }

    public void setPlayer(List<Player_> player) {
        this.player = player;
    }

}
