
package ke.co.droidsense.custom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Sports {

    @SerializedName("sports")
    @Expose
    private List<Sport> sports = null;

    public List<Sport> getSports() {
        return sports;
    }

    public void setSports(List<Sport> sports) {
        this.sports = sports;
    }

}
