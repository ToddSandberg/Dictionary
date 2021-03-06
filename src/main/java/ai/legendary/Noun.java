package ai.legendary;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

//import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.dictionary.Dictionary;
/**
 * Class for all nouns and their data
 * @author ToddSandberg
 *
 */
public class Noun implements PartOfSpeech,Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -8185217038014096172L;
    public String Noun = "Noun";
    /**
     * Determines whether the word is "Plural" or "Singular"
     */
	public String plurality = "--";
	/**
	 * Determines whether the word is "Masculine", "Feminine", or "Neuter"
	 */
	public String gender= "--";
	/**
	 * Determines if the word is an abbreviation, and if so, this contains the words it is an abbreviation for
	 */
	public String anAbbreviationFor="--";
	/**
	 * If the word has an abbreviate form, this field will contain the abbreviated form of the word.
	 */
	public String abbreviatedFrom="--";
	/**
	 * If the word is an acronym, this contains what the acronym stands for.
	 */
	public String anAcronymFor="--";
	/**
	 * Contains the words irregular plural form. This information is taken from the Specialist Lexicon
	 */
	public String irregularPluralForm="--";
	public Boolean isCompound=null;
	public Boolean isCountable=null;
	public Boolean acceptsZeroArticle=null;
	public Boolean isProperName=null;
	/**
	 * Determines if the word is a location
	 */
	public Boolean location=null;
	private transient static boolean l =false;
	/**
	 * List of the compliments to the word. They are separated from the '+' character/
	 */
	public String compliments = "--";
	/**
     * The base form after suffixes and prefixes have been removed from the word.
     */
    public String baseForm= "--";
    /**
     * Contains if the word is "Human", "Animate but not human", or "Inanimate"
     */
    public String animacy = null;
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
    public String propbank = "--";
    /**
     * The frame from frameNet of the word
     */
    public String frame = "--";
    private transient static String c = "";
	public Noun(){
		this("");
	}
	public Noun(String s) {
	    s=s.toLowerCase();
		/*Plurality*/
		if(s.charAt(s.length()-1)=='@'){
			plurality = "Plural";
			s=s.substring(0,s.length()-1);
		}
		else if(s.charAt(s.length()-1)=='#'){
			plurality = "Singular";
			s=s.substring(0,s.length()-1);
		}
		else if(s.charAt(s.length()-1)=='~'){
            s=s.substring(0,s.length()-1);
        }
		/*is countable*/
		if(s.charAt(s.length()-1)=='@'){
            isCountable = true;
            s=s.substring(0,s.length()-1);
        }
		else if(s.charAt(s.length()-1)=='#'){
            isCountable = false;
            s=s.substring(0,s.length()-1);
        }
		else if(s.charAt(s.length()-1)=='~'){
            s=s.substring(0,s.length()-1);
        }
		/*is compound*/
		if(s.split(" ").length>1){
		    isCompound = true;
		}
		else{
		    isCompound = false;
		}
		/*Gender*/
		if(ends("@",s)){
		    s=s.substring(0,s.length()-1);
		    gender = "Feminine";
		}
		else if(ends("#",s)){
		    s=s.substring(0,s.length()-1);
            gender = "Masculine";
		}
		else if((ends("ess",s) && !ends("ness",s) && !ends("less",s) && !ends("access",s)) || ends("ette",s) || ends("trix",s) || (ends("mother",s)|| begins("mother",s)) || (ends("woman",s) || begins("woman",s)) || (ends("female",s) || begins("female",s)) ){
		    
		    gender = "Feminine";
		}
		else if((ends("man",s) || begins("man-",s)|| begins("manhood",s) || begins("manl",s) || begins("man ",s)) || (ends("father",s) || begins("father",s)) || (ends("male",s) || begins("male",s))){
		    
		    gender = "Masculine";
		}
		else{
		    gender = "Neuter";
		}
		/*acceptsZeroArticle*/
        if((isCountable!=null && !isCountable.booleanValue())||(isProperName!=null && isProperName.booleanValue())){
            acceptsZeroArticle = true;
        }
	}
	private static boolean ends(String ender,String word){
        if(word.length()>=ender.length()){
            if(word.substring(word.length()-ender.length(), word.length()).equals(ender)){
                return true;
            }
            else
                return false;
        }
        return false;
    }
	private static boolean begins(String begin,String word){
        if(word.length()>=begin.length()){
            if(word.substring(0,begin.length()).equals(begin)){
                return true;
            }
            else
                return false;
        }
        return false;
    }
	/**
	 * adds to the compliments field
	 * @param comp
	 */
	public void addCompliments(ArrayList<String> comp){
	   for(int x=0;x<comp.size();x++){
	       String s = comp.get(x);
	       if(compliments.equals("--")){
	           compliments = "";
	       }
	       if(compliments.length()>1){
	           compliments+="+";
	       }
	       if(begins("pphr",s)){
	           String[] split = s.split(",");
	           compliments += split[0] +"+";
	           if(split.length>2){
	               compliments+=split[1]+"+"+split[2];
	           }
	           else{
	               compliments+=split[1];
	           }
	       }
	       else{
	           compliments+=s;
	       }

	   }

       System.out.println(compliments);
	}
    public void addIrregularPlur(String s){
        irregularPluralForm = s;
    }
    public HashMap<String, String> checkAnimacy(String s,HashMap<String,String> hm){
        try{
            String query = s;
            if(!hm.containsKey(query)){
                String thing = checker(query);
                hm.put(query,thing);
                animacy = thing;
                this.isProperName = l;
            }
            else{
                animacy = hm.get(query);
            }
            return hm;
        }
        catch(Exception e){
            e.printStackTrace();
            return hm;
        }
    }
	public String toString(){
	    return "Noun: Plurality=" + plurality + ", Gender=" + gender
                + ", AnAbbreviationFor=" + anAbbreviationFor + ", AbbreviatedFrom=" + abbreviatedFrom
                + ", AnAcronymFor=" + anAcronymFor+ ", IrregularPluralForm="+ irregularPluralForm + ", IsCompound=" + isCompound + ", IsCountable="
                + isCountable + ", acceptsZeroArticle=" + acceptsZeroArticle + ", IsProperName="
                + isProperName + ", Compliments=" + compliments + ", BaseForm="+baseForm + ", Animacy=" + animacy + ", location="+location+", howCommon="+howCommon+ ", commonRank="+commonRank + ", propBank=" + propbank + ", frame="+ frame;
	}
	/**
	 * checks animacy of add
	 * @param add
	 * @return
	 */
	public static String checker(String add){
        try {
            //if compound noun then format correctly
            if(add.split(" ").length>1 && !add.split(" ")[0].equals("") && !add.split(" ")[0].equals("")){
                String [] split = add.split(" ");
                add = split[0];
                for(int i=0;i<split.length;i++){
                   add += "_" + split[i];
                }
            }
            // url containing the word to be indexed
            Thread.sleep(100);
            String obj = "http://api.conceptnet.io/c/en/"+add+"?offset=0&limit=999999999&query?node=/c/en";
            // open HttpURLConnection
            HttpURLConnection hp = (HttpURLConnection) new URL(obj)
                    .openConnection();
            // set to request method to get
            // not required since default
            hp.setRequestMethod("GET");
            // get the inputstream in the json format
            hp.setRequestProperty("Accept", "application/json");
            // get inputstream from httpurlconnection
            InputStream is = hp.getInputStream();
            // get text from inputstream using IOUtils
            JSONTokener tokener = new JSONTokener(is);
            // get json object from the json String
            JSONObject json = new JSONObject(tokener);
            // get the edges array from the JSONObject which contains all
            // content
            JSONArray edges = json.getJSONArray("edges");
            boolean animatebutnothuman = false;
            boolean human = false;
            // goes through the edges array
            for (int x = 0; x < edges.length(); x++) {
                // convert the first object of the json array into a jsonobject
                // once it is a jsonobject you can use getString or getJSONArray
                // to continue in getting info
                if (!edges.getJSONObject(x).isNull("surfaceText")) {
                    c = edges.getJSONObject(x).getString("surfaceText");
                    /*if(c.contains("is a synonym of")){
                        int i = c.length()-1;
                        while(c.charAt(i)!='['){
                            i--;
                        }
                        if(!c.substring(i+1, c.length()-2).equals(add)){
                        return checker(c.substring(i+1, c.length()-2));
                        }
                    }*/
                    if(checkString("name")){
                        l = true;
                        human = true;
                    }
                    else if (checkString("animal") || checkString("insect") || checkString("mammal") || checkString("an animal") || checkString("bird") || checkString("fish") || checkString("amphibian")) {
                        animatebutnothuman = true;
                        l = false;
                    }
                    else if (checkString("human") || checkString("person") || checkString("girl") || checkString("boy") || checkString("man") || checkString("woman")) {
                        human = true;
                        l = false;
                    }
                }

            }
            if (animatebutnothuman) {
                return "Animate but not human";
            }
            else if (human) {
                return "Human";
            }
            else if (c.isEmpty()) {
                return "Unknown";
            }
            else {
                return "Inanimate";
            }

        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Waiting for 1 hour");
            try {
                Thread.sleep(3600000);
                return checker(add);
            }
            catch (InterruptedException e1) {
               return null;
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	/**
	 * checks the return of conceptnet
	 * @param in
	 * @return
	 */
    public static boolean checkString(String in) {
        /*if (s.endsWith("is related to [[" + in + "]]")
                || s.endsWith("is a type of [[" + in + "]]")
                || s.endsWith("is a kind of [[" + in + "]]")) {
            return true;
        }*/
        if(c.endsWith(in+"]]")){
            return true;
        }
        else
            return false;
    }
}
