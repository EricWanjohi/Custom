
package ke.co.droidsense.custom.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import ke.co.droidsense.custom.Converters.LeaguesByCountryConverter;

@Parcel
@Entity(tableName = "countryItem")
@TypeConverters(LeaguesByCountryConverter.class)
public class countryItem {
    @PrimaryKey(autoGenerate = true)
    int id;

    @SerializedName("idLeague")
    @Expose
    String idLeague;
    @SerializedName("idSoccerXML")
    @Expose
    String idSoccerXML;
    @SerializedName("idAPIfootball")
    @Expose
    String idAPIfootball;
    @SerializedName("strSport")
    @Expose
    String strSport;
    @SerializedName("strLeague")
    @Expose
    String strLeague;
    @SerializedName("strLeagueAlternate")
    @Expose
    String strLeagueAlternate;
    @SerializedName("strDivision")
    @Expose
    String strDivision;
    @SerializedName("idCup")
    @Expose
    String idCup;
    @SerializedName("strCurrentSeason")
    @Expose
    String strCurrentSeason;
    @SerializedName("intFormedYear")
    @Expose
    String intFormedYear;
    @SerializedName("dateFirstEvent")
    @Expose
    String dateFirstEvent;
    @SerializedName("strGender")
    @Expose
    String strGender;
    @SerializedName("strCountry")
    @Expose
    String strCountry;
    @SerializedName("strWebsite")
    @Expose
    String strWebsite;
    @SerializedName("strFacebook")
    @Expose
    String strFacebook;
    @SerializedName("strTwitter")
    @Expose
    String strTwitter;
    @SerializedName("strYoutube")
    @Expose
    String strYoutube;
    @SerializedName("strRSS")
    @Expose
    String strRSS;
    @SerializedName("strDescriptionEN")
    @Expose
    String strDescriptionEN;
    @SerializedName("strDescriptionDE")
    @Expose
    String strDescriptionDE;
    @SerializedName("strDescriptionFR")
    @Expose
    String strDescriptionFR;
    @SerializedName("strDescriptionIT")
    @Expose
    String strDescriptionIT;
    @SerializedName("strDescriptionCN")
    @Expose
    String strDescriptionCN;
    @SerializedName("strDescriptionJP")
    @Expose
    String strDescriptionJP;
    @SerializedName("strDescriptionRU")
    @Expose
    String strDescriptionRU;
    @SerializedName("strDescriptionES")
    @Expose
    String strDescriptionES;
    @SerializedName("strDescriptionPT")
    @Expose
    String strDescriptionPT;
    @SerializedName("strDescriptionSE")
    @Expose
    String strDescriptionSE;
    @SerializedName("strDescriptionNL")
    @Expose
    String strDescriptionNL;
    @SerializedName("strDescriptionHU")
    @Expose
    String strDescriptionHU;
    @SerializedName("strDescriptionNO")
    @Expose
    String strDescriptionNO;
    @SerializedName("strDescriptionPL")
    @Expose
    String strDescriptionPL;
    @SerializedName("strDescriptionIL")
    @Expose
    String strDescriptionIL;
    @SerializedName("strFanart1")
    @Expose
    String strFanart1;
    @SerializedName("strFanart2")
    @Expose
    String strFanart2;
    @SerializedName("strFanart3")
    @Expose
    String strFanart3;
    @SerializedName("strFanart4")
    @Expose
    String strFanart4;
    @SerializedName("strBanner")
    @Expose
    String strBanner;
    @SerializedName("strBadge")
    @Expose
    String strBadge;
    @SerializedName("strLogo")
    @Expose
    String strLogo;
    @SerializedName("strPoster")
    @Expose
    String strPoster;
    @SerializedName("strTrophy")
    @Expose
    String strTrophy;
    @SerializedName("strNaming")
    @Expose
    String strNaming;
    @SerializedName("strComplete")
    @Expose
    String strComplete;
    @SerializedName("strLocked")
    @Expose
    String strLocked;

    //Empty constructor.
    public countryItem() {
    }

    public String getIdLeague() {
        return idLeague;
    }

    public void setIdLeague(String idLeague) {
        this.idLeague = idLeague;
    }

    public String getIdSoccerXML() {
        return idSoccerXML;
    }

    public void setIdSoccerXML(String idSoccerXML) {
        this.idSoccerXML = idSoccerXML;
    }

    public String getIdAPIfootball() {
        return idAPIfootball;
    }

    public void setIdAPIfootball(String idAPIfootball) {
        this.idAPIfootball = idAPIfootball;
    }

    public String getStrSport() {
        return strSport;
    }

    public void setStrSport(String strSport) {
        this.strSport = strSport;
    }

    public String getStrLeague() {
        return strLeague;
    }

    public void setStrLeague(String strLeague) {
        this.strLeague = strLeague;
    }

    public String getStrLeagueAlternate() {
        return strLeagueAlternate;
    }

    public void setStrLeagueAlternate(String strLeagueAlternate) {
        this.strLeagueAlternate = strLeagueAlternate;
    }

    public String getStrDivision() {
        return strDivision;
    }

    public void setStrDivision(String strDivision) {
        this.strDivision = strDivision;
    }

    public String getIdCup() {
        return idCup;
    }

    public void setIdCup(String idCup) {
        this.idCup = idCup;
    }

    public String getStrCurrentSeason() {
        return strCurrentSeason;
    }

    public void setStrCurrentSeason(String strCurrentSeason) {
        this.strCurrentSeason = strCurrentSeason;
    }

    public String getIntFormedYear() {
        return intFormedYear;
    }

    public void setIntFormedYear(String intFormedYear) {
        this.intFormedYear = intFormedYear;
    }

    public String getDateFirstEvent() {
        return dateFirstEvent;
    }

    public void setDateFirstEvent(String dateFirstEvent) {
        this.dateFirstEvent = dateFirstEvent;
    }

    public String getStrGender() {
        return strGender;
    }

    public void setStrGender(String strGender) {
        this.strGender = strGender;
    }

    public String getStrCountry() {
        return strCountry;
    }

    public void setStrCountry(String strCountry) {
        this.strCountry = strCountry;
    }

    public String getStrWebsite() {
        return strWebsite;
    }

    public void setStrWebsite(String strWebsite) {
        this.strWebsite = strWebsite;
    }

    public String getStrFacebook() {
        return strFacebook;
    }

    public void setStrFacebook(String strFacebook) {
        this.strFacebook = strFacebook;
    }

    public String getStrTwitter() {
        return strTwitter;
    }

    public void setStrTwitter(String strTwitter) {
        this.strTwitter = strTwitter;
    }

    public String getStrYoutube() {
        return strYoutube;
    }

    public void setStrYoutube(String strYoutube) {
        this.strYoutube = strYoutube;
    }

    public String getStrRSS() {
        return strRSS;
    }

    public void setStrRSS(String strRSS) {
        this.strRSS = strRSS;
    }

    public String getStrDescriptionEN() {
        return strDescriptionEN;
    }

    public void setStrDescriptionEN(String strDescriptionEN) {
        this.strDescriptionEN = strDescriptionEN;
    }

    public String getStrDescriptionDE() {
        return strDescriptionDE;
    }

    public void setStrDescriptionDE(String strDescriptionDE) {
        this.strDescriptionDE = strDescriptionDE;
    }

    public String getStrDescriptionFR() {
        return strDescriptionFR;
    }

    public void setStrDescriptionFR(String strDescriptionFR) {
        this.strDescriptionFR = strDescriptionFR;
    }

    public String getStrDescriptionIT() {
        return strDescriptionIT;
    }

    public void setStrDescriptionIT(String strDescriptionIT) {
        this.strDescriptionIT = strDescriptionIT;
    }

    public String getStrDescriptionCN() {
        return strDescriptionCN;
    }

    public void setStrDescriptionCN(String strDescriptionCN) {
        this.strDescriptionCN = strDescriptionCN;
    }

    public String getStrDescriptionJP() {
        return strDescriptionJP;
    }

    public void setStrDescriptionJP(String strDescriptionJP) {
        this.strDescriptionJP = strDescriptionJP;
    }

    public String getStrDescriptionRU() {
        return strDescriptionRU;
    }

    public void setStrDescriptionRU(String strDescriptionRU) {
        this.strDescriptionRU = strDescriptionRU;
    }

    public String getStrDescriptionES() {
        return strDescriptionES;
    }

    public void setStrDescriptionES(String strDescriptionES) {
        this.strDescriptionES = strDescriptionES;
    }

    public String getStrDescriptionPT() {
        return strDescriptionPT;
    }

    public void setStrDescriptionPT(String strDescriptionPT) {
        this.strDescriptionPT = strDescriptionPT;
    }

    public String getStrDescriptionSE() {
        return strDescriptionSE;
    }

    public void setStrDescriptionSE(String strDescriptionSE) {
        this.strDescriptionSE = strDescriptionSE;
    }

    public String getStrDescriptionNL() {
        return strDescriptionNL;
    }

    public void setStrDescriptionNL(String strDescriptionNL) {
        this.strDescriptionNL = strDescriptionNL;
    }

    public String getStrDescriptionHU() {
        return strDescriptionHU;
    }

    public void setStrDescriptionHU(String strDescriptionHU) {
        this.strDescriptionHU = strDescriptionHU;
    }

    public String getStrDescriptionNO() {
        return strDescriptionNO;
    }

    public void setStrDescriptionNO(String strDescriptionNO) {
        this.strDescriptionNO = strDescriptionNO;
    }

    public String getStrDescriptionPL() {
        return strDescriptionPL;
    }

    public void setStrDescriptionPL(String strDescriptionPL) {
        this.strDescriptionPL = strDescriptionPL;
    }

    public String getStrDescriptionIL() {
        return strDescriptionIL;
    }

    public void setStrDescriptionIL(String strDescriptionIL) {
        this.strDescriptionIL = strDescriptionIL;
    }

    public String getStrFanart1() {
        return strFanart1;
    }

    public void setStrFanart1(String strFanart1) {
        this.strFanart1 = strFanart1;
    }

    public String getStrFanart2() {
        return strFanart2;
    }

    public void setStrFanart2(String strFanart2) {
        this.strFanart2 = strFanart2;
    }

    public String getStrFanart3() {
        return strFanart3;
    }

    public void setStrFanart3(String strFanart3) {
        this.strFanart3 = strFanart3;
    }

    public String getStrFanart4() {
        return strFanart4;
    }

    public void setStrFanart4(String strFanart4) {
        this.strFanart4 = strFanart4;
    }

    public String getStrBanner() {
        return strBanner;
    }

    public void setStrBanner(String strBanner) {
        this.strBanner = strBanner;
    }

    public String getStrBadge() {
        return strBadge;
    }

    public void setStrBadge(String strBadge) {
        this.strBadge = strBadge;
    }

    public String getStrLogo() {
        return strLogo;
    }

    public void setStrLogo(String strLogo) {
        this.strLogo = strLogo;
    }

    public String getStrPoster() {
        return strPoster;
    }

    public void setStrPoster(String strPoster) {
        this.strPoster = strPoster;
    }

    public String getStrTrophy() {
        return strTrophy;
    }

    public void setStrTrophy(String strTrophy) {
        this.strTrophy = strTrophy;
    }

    public String getStrNaming() {
        return strNaming;
    }

    public void setStrNaming(String strNaming) {
        this.strNaming = strNaming;
    }

    public String getStrComplete() {
        return strComplete;
    }

    public void setStrComplete(String strComplete) {
        this.strComplete = strComplete;
    }

    public String getStrLocked() {
        return strLocked;
    }

    public void setStrLocked(String strLocked) {
        this.strLocked = strLocked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
