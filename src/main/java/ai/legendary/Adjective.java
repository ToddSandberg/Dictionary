package ai.legendary;

import java.io.Serializable;
import java.util.ArrayList;

public class Adjective implements PartOfSpeech, Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3876784461772787907L;
    public String Adjective = "Adjective";
    /**
     * 1=Predeterminer 2=Determiner 3=Post Determiner 4=Observation
     * 5=SizeOrShape 6=Age 7=Color 8=Material 9=Qualifier
     */
    public int adjectiveOrderID = -1;
    /**
     * base ID = 2
     */
    public int adjectiveIntensifierID = -1;
    public String comparisonType = "--", quantifier = "--";
    public Boolean isQualitative = null, isClassifying = null,
            commonlyPrecededWithAnd = null, worksInAttributivePosition = null,
            worksInPredicativePosition = null, hasDiminutiveSuffix = null,
            isProper = null, mustUseMoreMost=null;
    public String compliments = "";

    public Adjective() {
        this("");
    }

    public Adjective(String s) {
        /* Is Qualitative */
        if (s.charAt(s.length() - 1) == '@') {
            s = s.substring(0, s.length() - 1);
            isQualitative = true;
        }
        else if (s.charAt(s.length() - 1) == '#') {
            s = s.substring(0, s.length() - 1);
            isQualitative = false;
        }
        else if (s.charAt(s.length() - 1) == '~') {
            s = s.substring(0, s.length() - 1);
        }
        /* Works in Predicative */
        if (s.charAt(s.length() - 1) == '@') {
            s = s.substring(0, s.length() - 1);
            worksInPredicativePosition = true;
        }
        else if (s.charAt(s.length() - 1) == '#') {
            s = s.substring(0, s.length() - 1);
            worksInPredicativePosition = false;
        }
        else if (s.charAt(s.length() - 1) == '~') {
            s = s.substring(0, s.length() - 1);
        }
        /* Is classifying */
        if (s.charAt(s.length() - 1) == '@') {
            s = s.substring(0, s.length() - 1);
            isClassifying = true;
        }
        else if (s.charAt(s.length() - 1) == '#') {
            s = s.substring(0, s.length() - 1);
            isClassifying = false;
        }
        else if (s.charAt(s.length() - 1) == '~') {
            s = s.substring(0, s.length() - 1);
        }
        /* Attributive pos */
        if (s.endsWith("@")) {
            s = s.substring(0, s.length() - 1);
            worksInAttributivePosition = true;
        }
        else if (s.endsWith("#")) {
            s = s.substring(0, s.length() - 1);
            worksInAttributivePosition = false;
        }
        else if (s.charAt(s.length() - 1) == '~') {
            s = s.substring(0, s.length() - 1);
        }
        /* Quantifier */
        /* Comparison Type */
        if (s.endsWith("est")) {
            comparisonType = "Superlative";
        }
        else if (s.endsWith("er")) {
            comparisonType = "Comparative";
        }
        else
            comparisonType = "Absolute";
        /* Diminutive Suffix */
        if (s.endsWith("ish") || s.endsWith("ling") || s.endsWith("ette")
                || s.endsWith("kin")) {
            hasDiminutiveSuffix = true;
        }
        else {
            hasDiminutiveSuffix = false;
        }
        /* is proper? */
        if (((int) s.charAt(0)) > 64 && ((int) s.charAt(0)) < 91) {
            isProper = true;
        }
        else {
            isProper = false;
        }
    }

    public void addCompliments(ArrayList<String> comp) {
        for (int x = 0; x < comp.size(); x++) {
            String s = comp.get(x);
            if (compliments.length() > 1) {
                compliments += "+";
            }
            if (s.substring(0, 4).equals("pphr")) {
                String[] split = s.split(",");
                compliments += split[0] + "+";
                if (split.length > 2) {
                    compliments += split[1] + "+" + split[2];
                }
                else {
                    compliments += split[1];
                }
            }
            else {
                compliments += s;
            }

        }
    }
    public void addAdjectiveOrderID(int i){
        adjectiveOrderID = i;
    }
    public String toString(){
        return "Adjective: AdjectiveOrderID=" + adjectiveOrderID + ", ComparisonType="
                + comparisonType + ", Quantifier=" + quantifier + ", isQualitative="
                + isQualitative + ", isClassifying=" + isClassifying + ", CommonlyPrecededWithAnd="
                + commonlyPrecededWithAnd + ", WorksInAttributivePosition="
                + worksInAttributivePosition + ", WorksInPredicativePosition="
                + worksInPredicativePosition + ", HasDiminutiveSuffix="
                + hasDiminutiveSuffix + ", IsProper=" + isProper + ", Compliments="
                + compliments+ ", MustUseMoreMost="+mustUseMoreMost;
    }

}
