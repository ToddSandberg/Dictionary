package ai.legendary;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
/**
 * Converts 3Grams into a nice HashMap format, the keys are the words.
 * "word-" is the format if searching for something at the start of a 3Gram.
 * "-word" is the format if searching for something at the end of a 3Gram.
 * @author ToddSandberg
 *
 */
public class ThreeGram {
    public static void main(String[] args) {
        HashMap<String, HashMap<String, Integer>> preprocessed = new HashMap<String, HashMap<String, Integer>>();
        DictionaryAccess dict = new DictionaryAccess();
        HashMap<String, Determiner> detdict = dict.getDeterminerDictionary();
        HashMap<String, Preposition> prepdict = dict.getPrepositionDictionary();
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://ngrams.cbndyymb1za5.us-east-1.rds.amazonaws.com/NGrams",
                    "MasterUser", "MasterPassword");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT Word1,Word2,Word3,Count FROM NGrams.3Grams");
            int y = 1;
            while (rs.next()) {
                String word1 = rs.getString("Word1");
                String word2 = rs.getString("Word2");
                String word3 = rs.getString("Word3");
                if (detdict.containsKey(word2) || prepdict.containsKey(word2)) {
                    if (preprocessed.containsKey(word1 + "-")) {
                        HashMap<String, Integer> temp = preprocessed.get(word1+"-");
                        temp.put(word1 + "|" + word2 + "|" + word3,
                                rs.getInt("Count"));
                        System.out.println(word1 + "|" + word2 + "|" + word3);
                        preprocessed.put(word1 + "-", temp);
                    }
                    else {
                        HashMap<String, Integer> temp = new HashMap<String, Integer>();
                        temp.put(word1 + "|" + word2 + "|" + word3,
                                rs.getInt("Count"));
                        preprocessed.put(word1 + "-", temp);
                    }
                    if(preprocessed.containsKey("-"+word3)){
                        HashMap<String, Integer> temp = preprocessed.get("-" + word3);
                        temp.put(word1 + "|" + word2 + "|" + word3,
                                rs.getInt("Count"));
                        System.out.println(word1 + "|" + word2 + "|" + word3);
                        preprocessed.put("-"+word3, temp);
                    }
                    else {
                        HashMap<String, Integer> temp = new HashMap<String, Integer>();
                        temp.put(word1 + "|" + word2 + "|" + word3,
                                rs.getInt("Count"));
                        preprocessed.put("-"+word3, temp);
                    }
                }
            }
            FileOutputStream fout = new FileOutputStream("outputs/preprocessed3gram.ser");
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(preprocessed);
            out.flush();
            out.close();
            fout.close();
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
