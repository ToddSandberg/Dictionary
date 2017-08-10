package ai.legendary;

import java.io.Serializable;
/**
 * class for all Interjections and their data
 * @author ToddSandberg
 *
 */
public class Interjection implements PartOfSpeech,Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -8206020155454902728L;
    public String Interjection = "Interjection";
    /**
     * 1 Attention Grabbing
     * 2 Silence Request
     * 3 Clarification Request
     * 4 Happy or Excited
     */
	public int interjectionTypeID = -1;
    public float howCommon = -1;
    public long commonRank = -1;
	public Interjection(){
		this("");
	}
	public Interjection(String word) {
		// TODO Auto-generated constructor stub
	}
	public String toString(){
	    return "Interjection: InterjectionTypeID=" + interjectionTypeID + ", howCommon="+howCommon+ ", commonRank="+commonRank;
	}

}
