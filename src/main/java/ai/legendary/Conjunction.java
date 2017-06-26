package ai.legendary;

import java.io.Serializable;

public class Conjunction implements PartOfSpeech,Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 5399780922764479253L;
    public String Conjunction = "Conjunction";
	public String conjunctionType = "--";
    public float howCommon = -1;
    public long commonRank = -1;
	public Conjunction(){
	}
	public Conjunction(String s){
	    if(s.equals("and")||s.equals("but")||s.equals("for")||s.equals("nor")||s.equals("or")||s.equals("so")||s.equals("yet")||s.equals("and/or")||s.equals("an")||s.equals("therefore")||s.equals("ergo")||s.equals("et")||s.equals("fer")||s.equals("hence")||s.equals("however")||s.equals("howe'er")||s.equals("howsoever")||s.equals("nevertheless")||s.equals("nonetheless")||s.equals("moreover") ||s.equals("than")){
	        conjunctionType = "Coordinating";
	    }
	    else if(s.equals("both")||s.equals("neither")|| s.equals("not only") || s.equals("only")){
	        conjunctionType = "Preconjunction";
	    }
	    else if(s.equals("either") ||s.equals("not") || s.equals("whether")|| s.equals("rather")||s.equals("as many") || s.equals("no sooner") || s.equals("whither") ||s.equals("whithersoever")){//ADD THE REST OF THE CORRELATIVE CONJUNCTIONS
	        conjunctionType = "Correlative Conjunction";
	    }
	    else if(s.endsWith("@")){
	        conjunctionType = "Subordinate Conjunction";
	    }
	    else{
	        conjunctionType = "Subordinate Conjunction";
	    }
	}
	public String toString(){
	    return "Conjunction: ConjunctionType=" + conjunctionType + ", howCommon="+howCommon+ ", commonRank="+commonRank;
	}
}
