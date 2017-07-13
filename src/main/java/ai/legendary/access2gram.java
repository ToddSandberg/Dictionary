package ai.legendary;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

public class access2gram {
    public static HashMap<String, HashMap<String, Integer>> twograms = new HashMap<String, HashMap<String, Integer>>();
    public static DictionaryAccess dict = new DictionaryAccess();

    public static void main(String[] args) {
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream("outputs/preprocessed2gram.ser"));
            System.out.println("Loading 2grams");
            twograms = (HashMap<String, HashMap<String, Integer>>) in
                    .readObject();
            System.out.println("Loaded");
            in.close();
            /*
             * while(true){ String word =
             * JOptionPane.showInputDialog("insert a word"); String sentence =
             * word + " "; for (int x = 0; x < 10; x++) { String otherword =
             * highestPercentageWord( getPercentages(twograms.get(word + "-")));
             * word = otherword; sentence += word + " "; }
             * System.out.println(sentence); }
             */
            writeMostCommonLists(twograms);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeMostCommonLists(
            HashMap<String, HashMap<String, Integer>> twograms) {
        try {
            PrintWriter adjnprinter = new PrintWriter(new File("outputs/adjtonoun.csv"));
            adjnprinter.println("Adjective,Noun,2GramCount");
            PrintWriter adjadvprinter = new PrintWriter(new File("outputs/advtoadj.csv"));
            adjadvprinter.println("Adverb,Adjective,2GramCount");
            PrintWriter prepnprinter = new PrintWriter(new File("outputs/preptonoun.csv"));
            prepnprinter.println("Preposition,Noun,2GramCount");
            PrintWriter detnprinter = new PrintWriter(new File("outputs/dettonoun.csv"));
            detnprinter.println("Determiner,Noun,2GramCount");
            PrintWriter prepvprinter = new PrintWriter(new File("outputs/preptoverb.csv"));
            prepvprinter.println("Preposition,Verb,2GramCount");
            PrintWriter nounvprinter = new PrintWriter(new File("outputs/nountoverb.csv"));
            nounvprinter.println("Noun,Verb,2GramCount");
            PrintWriter advvprinter = new PrintWriter(new File("outputs/adverbtoverb.csv"));
            advvprinter.println("Adverb,Verb,2GramCount");
            

            HashMap<String, Noun> noundict = dict.getNounDictionary();
            HashMap<String, Adjective> adjdict = dict.getAdjectiveDictionary();
            HashMap<String, Adverb> advdict = dict.getAdverbDictionary();
            HashMap<String, Determiner> detdict = dict.getDeterminerDictionary();
            HashMap<String, Preposition> prepdict = dict.getPrepositionDictionary();
            HashMap<String, Verb> verbdict = dict.getVerbDictionary();

            Iterator<Entry<String, HashMap<String, Integer>>> it = twograms
                    .entrySet().iterator();
            while (it.hasNext()) {
                Entry<String, HashMap<String, Integer>> pair = it.next();
                String word = pair.getKey(); 
                if (word.charAt(0) == '-') {
                    word = word.substring(1, word.length());
                    HashMap<String, Integer> values = pair.getValue();
                    Iterator<Entry<String, Integer>> it2 = values.entrySet()
                            .iterator();
                    boolean adjn = false;
                    boolean detn = false;
                    boolean prepn = false;
                    boolean advadv = false;
                    boolean prepv = false;
                    boolean nounv = false;
                    boolean advv = false;
                    while (it2.hasNext()) {
                        Entry<String, Integer> pair2 = it2.next();
                        if (noundict.containsKey(word)) {
                            String beginword = pair2.getKey().split("\\|")[0];
                            if (adjn == false
                                    && adjdict.containsKey(beginword) && !detdict.containsKey(beginword) && !prepdict.containsKey(beginword)) {
                                adjnprinter.println(beginword + "," + word + ","
                                        + pair2.getValue());
                                adjn = true;
                            }
                            else if(detn == false && detdict.containsKey(beginword)){
                                detnprinter.println(beginword + "," + word + "," + pair2.getValue());
                                detn = true;
                            }
                            else if(prepn == false && prepdict.containsKey(beginword)){
                                prepnprinter.println(beginword + "," + word + "," + pair2.getValue());
                                prepn = true;;
                            }
                        }
                        if(adjdict.containsKey(word)){
                            String beginword = pair2.getKey().split("\\|")[0];
                            if (advadv == false
                                    && advdict.containsKey(beginword)&& !detdict.containsKey(beginword)&& !prepdict.containsKey(beginword)) {
                                adjadvprinter.println(beginword + "," + word + ","
                                        + pair2.getValue());
                                advadv = true;
                            }
                        }
                        if(verbdict.containsKey(word)){
                            String beginword = pair2.getKey().split("\\|")[0];
                            if (prepv== false
                                    && prepdict.containsKey(beginword)) {
                                prepvprinter.println(beginword + "," + word + ","
                                        + pair2.getValue());
                                prepv = true;
                            }
                            else if (nounv== false
                                    && noundict.containsKey(beginword) && !adjdict.containsKey(beginword) && !detdict.containsKey(beginword)) {
                                nounvprinter.println(beginword + "," + word + ","
                                        + pair2.getValue());
                                nounv = true;
                            }
                            else if(advv==false&& advdict.containsKey(beginword) && !adjdict.containsKey(beginword) && !detdict.containsKey(beginword)){
                                advvprinter.println(beginword + "," + word + "," + pair2.getValue());
                                advv = true;
                            }
                        }
                    }
                }
            }
            adjnprinter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, Double> getPercentages(
            HashMap<String, Integer> temp) {
        HashMap<String, Double> result = new HashMap<String, Double>();
        HashMap<String, Integer> temp2 = temp;
        // System.out.println("temp2: "+temp2.toString());
        HashMap<String, Integer> temp3 = temp;
        double total = 0;
        try {
            Iterator<Entry<String, Integer>> it = temp2.entrySet().iterator();
            while (it.hasNext()) {
                Entry<String, Integer> pair = it.next();
                total += pair.getValue();
            }
            // System.out.println("temp3: "+temp3.toString());
            Iterator<Entry<String, Integer>> it2 = temp3.entrySet().iterator();
            while (it2.hasNext()) {
                Entry<String, Integer> pair = it2.next();
                double p = (Integer) pair.getValue();
                double percentage = ((p / total) * 100);
                String s = (String) pair.getKey();
                result.put(s, percentage);
                // it2.remove();
            }
            // System.out.println("percent hashmap: "+result.toString());
            return result;
        }
        catch (Exception e) {

        }
        return result;
    }

    public static String highestPercentageWord(HashMap<String, Double> temp) {
        double highest = 0;
        String higheststring = "";
        String[] random = new String[100];
        Iterator it = temp.entrySet().iterator();
        int index = 0;
        try {
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                double dou = ((Double) pair.getValue()).doubleValue(); // intValue();
                int percentage = (int) dou + 1;
                // System.out.print(percentage + " : " + ((String)
                // pair.getKey()).split("\\|")[1] + " ");
                for (int x = index; x < (index + percentage); x++) {
                    random[x] = ((String) pair.getKey()).split("\\|")[1];
                    // System.out.println(index + " : " + ((String)
                    // pair.getKey()).split("\\|")[1]);
                }
                index += percentage;
            }
            // System.out.println("");
        }
        catch (Exception e) {
            // e.printStackTrace();
        }
        int randomest = ((int) (Math.random() * 100));
        System.out.println(Arrays.toString(random));
        return random[randomest];
    }

    public static double highestPercentageDouble(
            HashMap<String, Double> temp) {
        if (temp != null && !temp.isEmpty()) {
            double highest = 0;
            String higheststring = "";
            Iterator it = temp.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                double d = (Double) pair.getValue();
                if (d > highest) {
                    highest = d;
                    higheststring = ((String) pair.getKey()).split("\\|")[1];
                }
            }
            return highest;
        }
        else {
            return 1;
        }
    }
    
}
