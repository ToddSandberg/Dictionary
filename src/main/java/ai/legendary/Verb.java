package ai.legendary;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
/**
 * Class for all verbs and their data
 * @author ToddSandberg
 *
 */
public class Verb implements PartOfSpeech ,Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -7980913079391599406L;
    public String Verb = "Verb";
    /**
     * Contains whether the word is "Auxiliary" or "Modal"
     */
    public String verbType = "--";
    /**
     * Determines the transivity of the word, as well as it's context sometimes. These are separate by the '|' character.
     */
    public String transivity = "--";
    /**
     * Contains whether the word is "Present" or "Past" tense
     */
    public String tense = "--";
    public String aspect = "--";
    /**
     * Contains whether the word is "First","Second" or "Third" person or a combination of these seperated by the / character
     */
    public String person = "--";
    /**
     * Determines whether the word if a phrasal. If it isnt, it gets a "No". If it is, it gets either "Separable" if it can be seperated but does not have to be, a "MustBeSeparated" if the word must be separated, and a "NotSeparable" if it cannot be separated.
     */
    public String phrasal = "--";
    /**
     * Defines 1 or 2 single words that are synonyms for the word if it is a phrasal verb.
     */
    public String phrasalParaphrase="--";
    public Boolean isInfinitive;
    /**
     * Percentage of use in the Frequency list, -1 = undefined
     */
    public float howCommon = -1;
    /**
     * The rank in the Frequency list, -1 = undefined
     */
    public long commonRank = -1;
    /**
     * The base form after suffixes and prefixes have been removed from the word.
     */
    public String baseForm = "--";
    /**
     * Determines if the verb is a light verb or not
     */
    public boolean light = false;
    /**
     * Lot's of information from verbnet
     */
    public String verbnet = "--";
    public String wordNetID = "--";
    /**
     * The information from propBank including wordnet ID, description, form and alias
     */
    public String propbank = "--";
    /**
     * The frame from frameNet of the word
     */
    public String frame = "--";
    /**
     * base ID = 2;
     * higher the number, higher the intensity, -1 = undefined
     */
    public int verbIntensifierID = -1;
    public Verb() {
        this("");
    }

    public Verb(String s) {
        /* transivity */
        if (s.charAt(s.length() - 1) == '%') {
            transivity = "Ambitransitive";
            s = s.substring(0, s.length() - 1);
        }
        else if (s.charAt(s.length() - 1) == '~') {
            transivity = "Ditransitive";
            s = s.substring(0, s.length() - 1);
        }
        else if (s.charAt(s.length() - 1) == '@') {
            transivity = "Transitive";
            s = s.substring(0, s.length() - 1);
        }
        else if (s.charAt(s.length() - 1) == '=') {
            transivity = "Intransitive";
            s = s.substring(0, s.length() - 1);
        }
        else
            transivity = "--";
        /*phrasal*/
        if (s.split(" ").length > 1) {
            String[] p = s.split(" ");
            if (p.length == 2 && !check3gram(p)) {
                phrasal = "NotSeparable";
            }
            else if(p.length == 2 && check3gram(p) && !check2gram(p)){
                phrasal = "MustBeSeparated";
            }
            else if(p.length == 2 && check3gram(p)){
                phrasal = "Separable";
            }
            else if(s.equals("drag out of") || s.equals("grow out of")){
                phrasal = "Separable";
            }
            else{
                phrasal = "NotSaperable";
            }

        }
        else {
            phrasal = "No";
        }
        /*Tense, isinfitive, and type*/
        if (s.endsWith("ed")) {
            tense = "Past";
        }
        if(s.equals("be") || s.equals("have")){
            isInfinitive = true;
            verbType = "Auxilary";
            tense = "Present";
            person = "First";
            light = true;
        }
        else if(s.equals("is") || s.equals("'s") || s.equals("isn't")||s.equals("does")||s.equals("doesn't")){
            verbType = "Auxilary";
            tense = "Present";
            person = "Third";
            isInfinitive = false;
        }
        else if(s.equals("has")||s.equals("hasn't")){
            verbType = "Auxilary";
            tense = "Present";
            person = "Third";
            isInfinitive = false;
            light = true;
        }
        else if(s.equals("are") || s.equals("'re") || s.equals("aren't")||s.equals("do")||s.equals("don't")){
            verbType = "Auxilary";
            tense = "Present";
            person = "First/Second/Third";
            isInfinitive = false;
        }
        else if(s.equals("haven't")||s.equals("'ve")){
            verbType = "Auxilary";
            tense = "Present";
            person = "First/Second/Third";
            isInfinitive = false;
            light = true;
        }
        else if(s.equals("am") || s.equals("'m")){
            verbType = "Auxilary";
            tense = "Present";
            person = "First";
            isInfinitive = false;
        }
        else if(s.equals("was") || s.equals("wasn't")){
            verbType = "Auxilary";
            tense = "Past";
            person = "First/Third";
            isInfinitive = false;
        }
        else if(s.equals("were") || s.equals("weren't")){
            verbType = "Auxilary";
            tense = "Past";
            person = "First/Second/Third";
            isInfinitive = false;
        }
        else if(s.equals("did") || s.equals("didn't")||s.equals("had")||s.equals("hadn't")||s.equals("'d")){
            verbType = "Auxilary";
            tense = "Past";
            person = "N/A";
            isInfinitive = false;
            light = true;
        }
        else if(s.equals("having") || s.equals("taking")){
            verbType = "Auxilary";
            tense = "Present";
            person = "N/A";
            isInfinitive = false;
            light = true;
        }

        if(s.equals("can't") || s.equals("cannot") || s.equals("mayn't") || s.equals("mustn't")||s.equals("oughtn't")||s.equals("shan't")||s.equals("can") || s.equals("dare") || s.equals("may") || s.equals("must") || s.equals("ought")||s.equals("shall")||s.equals("will")||s.equals("won't")||s.equals("'ll")||s.equals("need")||s.equals("needn't")){
            verbType = "Modal";
            tense = "Present";
            isInfinitive = false;
        }
        else if(s.equals("could") || s.equals("couldn't") || s.equals("daren't") || s.equals("might") || s.equals("mightn't")||s.equals("should")||s.equals("shouldn't")||s.equals("would")||s.equals("wouldn't")||s.equals("'d")){
            verbType = "Modal";
            tense = "Past";
            isInfinitive = false;
        }
        if(s.equals("do") || s.equals("give") || s.equals("have") || s.equals("make") || s.equals("take") || s.equals("cast") ||s.equals("get")){
            light = true;
            tense = "Present";
            person = "First";
        }
        if(s.equals("does") || s.equals("gives") || s.equals("has") || s.equals("makes") || s.equals("takes") || s.equals("casts")){
            light = true;
            tense = "Present";
            person = "Third";
        }
        if(s.equals("did") || s.equals("gave") || s.equals("had") || s.equals("made") || s.equals("took") || s.equals("casted")|| s.equals("got")|| s.equals("let")){
            light = true;
            tense = "Past";
        }
        
    }
    /**
     * Checks if a phrasal verb can be separated by looking if there ever exists something in the middle of the two words.
     * @param s array of the words in the phrasal
     * @return if separable
     */
    private boolean check3gram(String [] s) {
        try{
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://ngrams.cbndyymb1za5.us-east-1.rds.amazonaws.com/NGrams",
                "MasterUser", "MasterPassword");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT Word1,word3 FROM NGrams.3Grams"+
        " where word1 = '"+s[0]+"' and word3 = '"+s[1]+"'");
        if(rs.next()){
            conn.close();
            return true;
        }
        else
            conn.close();
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Checks if the phrasal verb is separable or not by using 2grams
     * @param s is the array of words in the phrasal 
     * @return if the word is separable
     */
    private boolean check2gram(String[] s){
        try{
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://ngrams.cbndyymb1za5.us-east-1.rds.amazonaws.com/NGrams",
                    "MasterUser", "MasterPassword");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Word1,word2 FROM NGrams.2Grams"+
            " where word1 = '"+s[0]+"' and word2 = '"+s[1]+"'");
            if(rs.next()){
                conn.close();
                return true;
            }
            else
                conn.close();
                return false;
            }
            catch(Exception e){
                e.printStackTrace();
                return false;
            }
    }
    /**
     * Adds transivity to the word separated by the '|' character.
     * @param array of examples of the transivity
     */
    public void addCompliments(ArrayList<String> comp){
        for(int x=0;x<comp.size();x++){
            String s = comp.get(x);
            if(transivity.equals("--")){
                transivity = "";
            }
            if(transivity.length()>1){
                transivity+="|";
            }
            if(s.contains("pphr")){
                String[] split = s.split(",");
                transivity += split[0] +"+";
                if(split.length>2){
                    transivity+=split[1]+"+"+split[2];
                }
                else{
                    transivity+=split[1];
                }
            }
            else{
                transivity+=s;
            }

        }
    }
    public String toString(){
        return "Verb: VerbType="+verbType + ", Transivity="
                + transivity + ", Tense=" + tense + ", Aspect=" + aspect
                + ", Person=" + person + ", Phrasal=" + phrasal + ", PhrasalParaphrase=" + phrasalParaphrase+ ", isInfinitive="
                + isInfinitive + ", howCommon="+howCommon+ ", commonRank="+commonRank 
                +", baseForm="+baseForm + ", light="+light + ", verbnet="+verbnet + ", wordNetID="+wordNetID+", propBank="+propbank+", frame="+frame + ", Itensifier ID="+verbIntensifierID;
    }
}
