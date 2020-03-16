
package ke.co.droidsense.custom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Country {

    @SerializedName("countrys")
    @Expose
    private List<Country_> countrys = null;

    public List<Country_> getCountrys() {
        return countrys;
    }

    public void setCountrys(List<Country_> countrys) {
        this.countrys = countrys;
    }

}
