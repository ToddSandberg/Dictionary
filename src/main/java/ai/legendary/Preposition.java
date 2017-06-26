package ai.legendary;

import java.io.Serializable;

public class Preposition implements PartOfSpeech,Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 7562750722149454315L;
    public String Preposition = "Preposition";
	public Boolean hasAdverbForm;
    public float howCommon = -1;
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
