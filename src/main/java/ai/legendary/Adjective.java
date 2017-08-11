package ai.legendary;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Class for Adjectives and their properties.
 * @author ToddSandberg
 *
 */
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
     * the higher the number, the more intense
     */
    public int adjectiveIntensifierID = -1;
    /**
     * Determines whether it's "Superlative", "Comparative", or "Absolute"
     */
    public String comparisonType = "--";
    /**
     * Determine if the Adjective indicates singular or plural meaning.
     */
    public String quantifier = "--";
    public Boolean isQualitative = null;
    public Boolean isClassifying = null;
    public Boolean commonlyPrecededWithAnd = null;
    public Boolean worksInAttributivePosition = null;
    public Boolean worksInPredicativePosition = null;
    public Boolean hasDiminutiveSuffix = null;
    public Boolean isProper = null;
    /**
     * Determine whether the adjective uses more or most in its Comparative and Superlative forms
     */
    public Boolean mustUseMoreMost=null;
    /**
     * The compliments to the Adjective
     */
    public String compliments = "";
    /**
     * Percentage of use in the Frequency list, -1 = undefined
     */
    public float howCommon = -1;
    /**
     * The rank in the Frequency list, -1 = undefined
     */
    public long commonRank = -1;
    /**
     * The base form after suffixes and prefixes have been removed from the word.
     */
    public String baseForm = "--";
    /**
     * The information from propBank including wordnet ID, description, form and alias
     */
    public String propbank = "--";
    /**
     * The frame from frameNet of the word
     */
    public String frame = "--";

    public Adjective() {
        this("");
    }
    /**
     * Adjective part of speech with Adjective properties
     * @param s the word that is an adjective
     */
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
    /**
     * Adds compliments to the Adjective.
     * @param comp
     */
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
    /**
     * Adds the AdjectiveOrderID to the Adjective.
     * @param i the adjectiveOrderID
     */
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
                + compliments+ ", MustUseMoreMost="+mustUseMoreMost + ", howCommon="+howCommon + ", commonRank="+commonRank
                + ", baseForm="+baseForm + ", propBank="+propbank + ", frame="+frame;
    }

}
