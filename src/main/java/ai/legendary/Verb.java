package ai.legendary;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Verb implements PartOfSpeech ,Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -7980913079391599406L;
    public String Verb = "Verb";
    public String verbType = "--", transivity = "--", tense = "--",
            aspect = "--", person = "--", phrasal = "--";
    public Boolean isInfinitive;
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
                phrasal = "NotSeperable";
            }
            else if(p.length == 2 && check3gram(p) && !check2gram(p)){
                phrasal = "MustBeSeperated";
            }
            else if(p.length == 2 && check3gram(p)){
                phrasal = "Seperable";
            }
            else if(s.equals("drag out of") || s.equals("grow out of")){
                phrasal = "Seperable";
            }
            else{
                phrasal = "NotSeperable";
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
            person = "N/A";
        }
        else if(s.equals("is") || s.equals("'s") || s.equals("isn't")||s.equals("does")||s.equals("doesn't")||s.equals("has")||s.equals("hasn't")){
            verbType = "Auxilary";
            tense = "Present";
            person = "Third";
            isInfinitive = false;
        }
        else if(s.equals("are") || s.equals("'re") || s.equals("aren't")||s.equals("do")||s.equals("don't")||s.equals("have")||s.equals("haven't")||s.equals("'ve")){
            verbType = "Auxilary";
            tense = "Present";
            person = "First/Second/Third";
            isInfinitive = false;
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
        }
        else if(s.equals("having")){
            verbType = "Auxilary";
            tense = "Present";
            person = "N/A";
            isInfinitive = false;
        }
        else{
            person = "N/A";
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
    }

    private boolean check3gram(String [] s) {
        try{
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://ngrams.cbndyymb1za5.us-east-1.rds.amazonaws.com/NGrams",
                "MasterUser", "MasterPassword");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT Word1,word3 FROM NGrams.3Grams"+
        " where word1 = '"+s[0]+"' and word3 = '"+s[1]+"'");
        if(rs.next()){
            return true;
        }
        else
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    private boolean check2gram(String[] s){
        try{
            
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://ngrams.cbndyymb1za5.us-east-1.rds.amazonaws.com/NGrams",
                    "MasterUser", "MasterPassword");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Word1,word2 FROM NGrams.2Grams"+
            " where word1 = '"+s[0]+"' and word2 = '"+s[1]+"'");
            if(rs.next()){
                return true;
            }
            else
                return false;
            }
            catch(Exception e){
                e.printStackTrace();
                return false;
            }
    }
    public void addCompliments(ArrayList<String> comp){
        for(int x=0;x<comp.size();x++){
            String s = comp.get(x);
            if(transivity.equals("--")){
                transivity = "";
            }
            if(transivity.length()>1){
                transivity+="+";
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
                + ", Person=" + person + ", Phrasal=" + phrasal + ", isInfinitive="
                + isInfinitive;
    }
}
