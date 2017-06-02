package ai.legendary;

import java.io.Serializable;

public class Pronoun implements PartOfSpeech,Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3166547793615971528L;
    public String Pronoun = "Pronoun";
	public String plurality="--",gender="--",pronounCase="--",type="--";
	public Pronoun(){
		this("");
	}
	public Pronoun(String word) {
	    word = word.toLowerCase();
	    if(word.length()>=4 && word.substring(word.length()-4,word.length()).equals("self")){
	        plurality = "Singular";
	        pronounCase = "Reflexive";
	        gender = "Neuter";
	        type = "Personal";
	        word = word.substring(0,word.length()-4);
	    }
	    else if(word.length()>=5 && word.endsWith("selves")){
            plurality = "Plural";
            pronounCase = "Reflexive";
            gender = "Neuter";
            type = "Personal";
            word = word.substring(0,word.length()-5);
        }
	    else if(word.length()>=5 && word.endsWith("soever")){
            word = word.substring(0,word.length()-5);
        }
	    else if(word.length()>=5 && word.endsWith("soe'er")){
            word = word.substring(0,word.length()-5);
        }
	    else if(word.length()>=4 && word.substring(word.length()-4,word.length()).equals("ever")){
            word = word.substring(0,word.length()-4);
	    }
	    else if(word.length()>=4 && word.substring(word.length()-4,word.length()).equals("e'er")){
            word = word.substring(0,word.length()-4);
        }
	    else if(word.endsWith("from") || word.endsWith("into") || word.endsWith("with")){
            word = word.substring(0, word.length()-4);
        }
	    else if(word.endsWith("in") || word.endsWith("of") || word.endsWith("to") || word.endsWith("on") || word.endsWith("by")){
	        word = word.substring(0, word.length()-2);
	    }
	    
	    if(word.equals("who") || word.equals("what") || word.equals("whom") || word.equals("which") || word.equals("whose") || word.equals("where") || word.equals("when")|| word.equals("whats") || word.equals("whence") || word.equals("whether") || word.equals("whoms") || word.equals("wheres") || word.equals("wherewithal") || word.equals("whichs") || word.equals("whoses") || word.equals("whos")||word.equals("whoso")){
	        type = "Interrogative/Relative";
	        plurality = "Singular/Plural";
	        gender = "Neuter";
	        pronounCase = "N/A";
	    }
	    else if(word.equals("that") || word.equals("there")){
	        type = "Relative";
	        plurality = "Singular";
	        gender = "Neuter";
	        pronounCase = "N/A";
	    }
	    else if(word.equals("thir")){
	        type = "Relative";
	        plurality = "Plural";
	        gender = "Neuter";
	        pronounCase = "N/A";
	    }
	    else if(word.equals("quibus")){
            type = "Relative";
            plurality = "Plural";
            gender = "Neuter";
            pronounCase = "N/A";
        }
	    else if(word.equals("my") || word.equals("its") || word.equals("mine")){
	        if(pronounCase.equals("--"))
	        pronounCase = "Possesive";
	        plurality = "Singular";
	        gender = "Neuter";
	        type = "Personal";
	    }
	    else if(word.equals("our") ||word.equals("their")  || word.equals("ours") || word.equals("theirs") || word.equals("our'n")){
	        gender = "neuter";
	        if(pronounCase.equals("--"))
	        pronounCase = "Possesive";
	        plurality = "Plural";
	        type = "Personal";
	    }
	    else if(word.equals("his") || word.equals("his'n")){
	        if(pronounCase.equals("--"))
	        pronounCase="Possesive";
	        plurality = "Singular/Plural";
	        gender = "Masculine";
	        type = "Personal";
	    }
	    else if(word.equals("her")){
	        if(pronounCase.equals("--"))
	        pronounCase="Possesive/Accusative";
	        plurality="Singular";
	        gender = "Feminine";
	        type = "Personal";
	    }
	    else if(word.equals("hers") || word.equals("her'n")){
	        if(pronounCase.equals("--"))
	        pronounCase="Possesive";
	        plurality = "Plural";
	        gender = "Feminine";
	        type = "Personal";
	    }
	    else if(word.equals("your") || word.equals("yours") || word.equals("thy") || word.equals("your'n")){
	        if(pronounCase.equals("--"))
	        pronounCase = "Possesive";
	        plurality = "Singular/Plural";
	        gender = "Neuter";
	        type = "Personal";
	    }
	    else if(word.equals("he") || word.equals("hic")){
	        pronounCase = "Nominative";
	        plurality = "Singular";
	        gender = "Masculine";
	        type = "Personal";
	    }
	    else if(word.equals("she") || word.equals("haec") || word.equals("hoo")){
	        pronounCase = "Nominative";
	        plurality = "Singular";
	        gender = "Feminine";
	        type = "Personal";
	    }
	    else if(word.equals("i") || word.equals("hoc")){
	        pronounCase = "Nominative";
	        plurality = "Singular";
	        gender = "Neuter";
	        type = "Personal";
	    }
	    else if(word.equals("it") || word.equals("same") || word.equals("idem")){
	        pronounCase = "Nominative/Accusative";
            plurality = "Singular";
            gender = "Neuter";
            type = "Expletive";
	    }
	    else if(word.equals("we") || word.equals("they") || word.equals("allyou") || word.equals("you-all") || word.equals("thae") || word.equals("thems")){
            pronounCase = "Nominative";
            plurality = "Plural";
            gender = "Neuter";
            type = "Personal";
        }
	    else if(word.equals("you") || word.equals("ye") || word.equals("ya") || word.equals("thou") || word.equals("youse") || word.equals("yous")){
            pronounCase = "Nominative/Accusative";
            plurality = "Singular/Plural";
            gender = "Neuter";
            type = "Personal";
        }
	    else if(word.equals("another") || word.equals("anybody") || word.equals("anyone") ||word.equals("anything") ||word.equals("each") ||word.equals("either") ||word.equals("enough") ||word.equals("everybody") ||word.equals("everyone") ||word.equals("everything") ||word.equals("less") ||word.equals("little") ||word.equals("much") ||word.equals("neither") ||word.equals("nobody") ||word.equals("no-one") ||word.equals("nothing") ||word.equals("one") ||word.equals("other") ||word.equals("somebody") ||word.equals("someone") ||word.equals("something") || word.equals("aught") || word.equals("ane") || word.equals("every one") || word.equals("owt") || word.equals("tother") || word.equals("un") || word.equals("ilka") || word.equals("lot") || word.equals("otherwise") || word.equals("so")){
	        plurality = "Singular";
	        gender = "Neuter";
	        type = "Indefinite";
	        pronounCase = "N/A";
	    }
	    else if(word.equals("both") || word.equals("few") || word.equals("fewer") || word.equals("many") ||word.equals("others") ||word.equals("several") || word.equals("baith") || word.equals("couple") || word.equals("plenty")){
	        plurality = "Plural";
            gender = "Neuter";
            type = "Indefinite";
            pronounCase = "N/A";
	    }
	    else if(word.equals("all") ||word.equals("any") ||word.equals("more") ||word.equals("most") ||word.equals("none") ||word.equals("some") ||word.equals("such") || word.equals("nane") || word.equals("sundry")){
	        plurality = "Singular/Plural";
            gender = "Neuter";
            type = "Indefinite";
            pronounCase = "N/A";
	    }
	    else if(word.equals("a")){
	        plurality = "Singular/Plural";
	        gender = "Neuter";
	        type = "Personal";
	        pronounCase = "N/A";
	    }
	    else if(word.equals("each other") || word.equals("one another")){
	        type = "Reciprocal";
	        gender = "Neuter";
	        pronounCase = "N/A";
	        plurality = "Singular";
	    }
	    else if(word.equals("me")){
	        type = "Personal";
	        gender = "Neuter";
	        pronounCase = "Accusative";
	        plurality = "Singular";
	    }
	    else if(word.equals("yours truly")){
	        type = "Personal";
	        gender = "Neuter";
	        pronounCase = "Nominative/Accusative";
	        plurality = "Singular";
	    }
	    else if(word.equals("him")){
	        type = "Personal";
	        gender = "Masculine";
	        pronounCase = "Accusative";
	        plurality = "Singular";
	    }
	    else if(word.equals("us") || word.equals("them") || word.equals("thee")){
	        type = "Personal";
	        gender = "Neuter";
	        pronounCase = "Accusative";
	        plurality = "Plural";
	    }
	}
	public String toString(){
	    return "Pronoun: Plurality=" + plurality + ", Gender=" + gender
        + ", PronounCase=" + pronounCase + ", Type=" + type;
	}

}
