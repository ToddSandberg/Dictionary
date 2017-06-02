package ai.legendary;

import java.io.Serializable;

public class Determiner implements PartOfSpeech,Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -5006438904967333669L;
    public String Determiner = "Determiner";
	/**
	 * 1=Indefinite Article
	 * 2=Definite Article
	 * 3=Demonstrative, Near Singular
	 * 4=Demonstrative, Near Plural
	 * 5=Demonstrative, Far Singular
	 * 6=Demonstrative, Far Plural
	 * 7=Interrogative
	 */
	public int determinerTypeID = -1; 
	public Determiner(){
		this("");
	}
	public Determiner(String s) {
		if(s.equals("the")){
			determinerTypeID = 2;
			s=s.substring(0,s.length()-1);
		}
		else if(s.equals("a") || s.equals("an")){
			determinerTypeID = 1;
			s=s.substring(0,s.length()-1);
		}
		else if(s.equals("this")){
		    determinerTypeID = 3;
		}
		else if(s.equals("these")){
		    determinerTypeID = 4;
		}
		else if(s.equals("that")){
		    determinerTypeID = 5;
		}
		else if(s.equals("those")){
		    determinerTypeID = 6;
		}
		else{
		    determinerTypeID = 7;
		}
	}
	public String toString(){
	    return "Determiner: DeterminerTypeID=" + determinerTypeID;
	}
}
