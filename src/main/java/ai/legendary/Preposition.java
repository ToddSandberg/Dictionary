package ai.legendary;

import java.io.Serializable;
/**
 * Class for all prepositions and their data
 * @author ToddSandberg
 *
 */
public class Preposition implements PartOfSpeech,Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 7562750722149454315L;
    public String Preposition = "Preposition";
	public Boolean hasAdverbForm;
	/**
     * Percentage of use in the Frequency list, -1 = undefined
     */
    public float howCommon = -1;
    /**
     * The rank in the Frequency list, -1 = undefined
     */
    public long commonRank = -1;
	public Preposition(){
		this("");
	}
	public Preposition(String word) {

	}
	public String toString(){
	    return "Preposition: HasAdverbForm=" + hasAdverbForm + ", howCommon="+howCommon+ ", commonRank="+commonRank;
	}

}
