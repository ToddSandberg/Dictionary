package ai.legendary;

import java.io.Serializable;

public class Adverb implements PartOfSpeech,Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -3581083482214729589L;
    public String Adverb = "Adverb";
	public int advIntensiferID = -1;
	public Boolean isRelativeAdverb=false,isComparativeAdverb=null,isSuperlativeAdverb=null,noCompOrSuperForm=null,mustUseMoreMost=null;
	public String advIntensifier = "--";
    public String irregularForm = "--";
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
                + advIntensifier +", NoCompOrSupForm="+noCompOrSuperForm +", MustUseMoreMost="+mustUseMoreMost+ ", IrregularForm="+irregularForm;
	}

}
