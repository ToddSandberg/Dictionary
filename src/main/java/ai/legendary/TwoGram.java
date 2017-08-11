package ai.legendary;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
/**
 * Converts 2Grams into a nice HashMap format, the keys are the words.
 * "word-" is the format if searching for something at the start of a 2Gram.
 * "-word" is the format if searching for something at the end of a 2Gram.
 * @author ToddSandberg
 *
 */
public class TwoGram {
    public static void main(String [] args){
        HashMap<String,HashMap<String,Integer>> preprocessed = new HashMap<String,HashMap<String,Integer>>();
        try{
         // pronouns
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream("outputs/pronDictHashMap.ser"));
            System.out.println("Loading pronoun dictionary...");
            HashMap<String, Pronoun>pronounDictionary = (HashMap<String, Pronoun>) in.readObject();
            in.close();
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://ngrams.cbndyymb1za5.us-east-1.rds.amazonaws.com/NGrams",
                    "MasterUser", "MasterPassword");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Word1,Word2,Count FROM NGrams.2Grams");
            int y = 1;
            while(rs.next()){
                String word1 = rs.getString("Word1");
                String word2 = rs.getString("Word2");
                /*if(pronounDictionary.containsKey(word1)){
                    word1 = "PRONOUN";
                }
                if(pronounDictionary.containsKey(word2)){
                    word2 = "PRONOUN";
                }
                if(word1.equals("is") || word1.equals("was") || word1.equals("were") || word1.equals("are")){
                    word1 = "COPULA";
                }
                if(word2.equals("is") || word2.equals("was") || word2.equals("were") || word2.equals("are")){
                    word2 = "COPULA";
                }
                if(word1.equals("has") || word1.equals("have") || word1.equals("had")){
                    word1 = "HAVE/HAD/HAS";
                }
                if(word2.equals("has") || word2.equals("have") || word2.equals("had")){
                    word2 = "HAVE/HAD/HAS";
                }*/
                
                String combined = word1+"|"+word2;
                System.out.println(combined);
                word1 = word1+"-";
                word2 = "-"+word2;
                int score = rs.getInt("Count");
                y++;
                //System.out.println(combined + y);
                if(!preprocessed.containsKey(word1)){
                    HashMap<String, Integer> temp = new HashMap<String,Integer>();
                    temp.put(combined, score);
                    preprocessed.put(word1,temp);
                }
                else{
                    HashMap<String,Integer> temp = preprocessed.get(word1);
                    if(temp.containsKey(combined)){
                            int newscore = temp.get(combined) + score;
                            temp.put(combined, newscore);
                        
                    }
                    else{
                        temp.put(combined, score);
                    }
                    preprocessed.put(word1, temp);
                }
                if(!preprocessed.containsKey(word2)){
                    HashMap<String, Integer> temp = new HashMap<String,Integer>();
                    temp.put(combined, score);
                    preprocessed.put(word2,temp);
                }
                else{
                    HashMap<String,Integer> temp = preprocessed.get(word2);
                    if(temp.containsKey(combined)){
                        int newscore = temp.get(combined) + score;
                        temp.put(combined, newscore);
                    }
                    else{
                        temp.put(combined, score);
                    }
                    preprocessed.put(word2, temp);
                }
            }
            FileOutputStream fout = new FileOutputStream("outputs/preprocessed2gram.ser");
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(preprocessed);
            out.flush();
            out.close();
            fout.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
