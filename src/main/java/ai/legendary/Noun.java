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

public class Noun implements PartOfSpeech,Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -8185217038014096172L;
    public String Noun = "Noun";
	public String plurality = "--",gender= "--",anAbbreviationFor="--", abbreviatedFrom="--",anAcronymFor="--",irregularPluralForm="--";
	public Boolean isCompound=null,isCountable=null,acceptsZeroArticle=null,isProperName=null,location = null;
	private transient static boolean l =false;
	public String compliments = "--";
    public String baseForm= "--" ,animacy = null;
    public transient static String c = "";
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
		/*Is Proper Name?*/
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
                location = l;
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
                + isProperName + ", Compliments=" + compliments + ", BaseForm="+baseForm + ", Animacy=" + animacy;
	}
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
            String obj = "http://api.conceptnet.io/c/en/"+add;
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
                    if (checkString("animal") || checkString("insect") || checkString("mammal") || checkString("an animal") || checkString("bird") || checkString("fish") || checkString("amphibian")) {
                        animatebutnothuman = true;
                    }
                    else if (checkString("human") || checkString("person") || checkString("girl") || checkString("boy") || checkString("man") || checkString("woman")) {
                        human = true;
                    }
                    else if(checkString("room")||checkString("city")||checkString("continent")||checkString("country")||checkString("area")||checkString("state")||checkString("building")||checkString("place")||checkString("street")||checkString("body")||checkString("appendage")||checkString("body part")){
                        l = true;
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
