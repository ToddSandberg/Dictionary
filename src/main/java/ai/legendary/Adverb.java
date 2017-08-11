package ai.legendary;

import java.io.Serializable;
/**
 * Class for all Adverbs and their data
 * @author ToddSandberg
 *
 */
public class Adverb implements PartOfSpeech,Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -3581083482214729589L;
    public String Adverb = "Adverb";
    /**
     * 1-Degree or Extent
     * 2-Points of Time
     * 3-Relationships in Time
     * 4-Frequency
     * 5-Purpose
     * 6-Place
     * 7-Manner
     * 8-Other
     */
    public int wordSenseID = -1;
    /**
     * Base ID = 2
     */
	public int advIntensiferID = -1;
	public Boolean isRelativeAdverb=false;
	public Boolean isComparativeAdverb=null;
	public Boolean isSuperlativeAdverb=null;
	public Boolean noCompOrSuperForm=null;
	public Boolean mustUseMoreMost=null;
	/**
	 * Determines what type of intensifier the Adverb is. Ex. "Manner","Time","Place,etc.
	 */
	public String advIntensifier = "--";
	/**
	 * Determines the irregular form of the word. This info is currently from the Specialist Lexicon.
	 */
    public String irregularForm = "--";
    /**
     * Percentage of use in the Frequency list, -1 = undefined
     */
    public float howCommon = -1;
    /**
     * The rank in the Frequency list, -1 = undefined
     */
    public long commonRank = -1;
    /**
     * The information from propBank including wordnet ID, description, form and alias
     */
    public String baseForm = "--";
	public Adverb(){
		this("");
	}
	public Adverb(String s) {
		s=s.toLowerCase();
		/*advIntensiferID*/
		/*Is Relative Adverb*/
		if(s.equals("where") || s.equals("when") ||s.equals("why")){
			isRelativeAdverb = true;
		}
		/*Is Comparative/Superlative Adverb*/
		if(s.endsWith("est")||s.equals("worst")||s.equals("most") || s.equals("least") || s.equals("best")){
			isSuperlativeAdverb = true;
			isComparativeAdverb = false;
		}
		else if(s.endsWith("er") || s.equals("worse")||s.equals("more")||s.equals("less") || s.equals("better")){
			isComparativeAdverb = true;
			isSuperlativeAdverb = false;
		}
	}
	public String toString(){
	   return "Adverb: AdvIntensifierID=" + advIntensiferID + ", IsRelativeAdverb="
                + isRelativeAdverb + ", IsComparativeAdverb=" + isComparativeAdverb
                + ", IsSuperlativeAdverb=" + isSuperlativeAdverb + ", AdvIntensifier="
                + advIntensifier +", NoCompOrSupForm="+noCompOrSuperForm +", MustUseMoreMost="+mustUseMoreMost+ ", IrregularForm="+irregularForm
                + ", howCommon="+howCommon+ ", commonRank="+commonRank + ", baseForm="+baseForm;
	}

}
