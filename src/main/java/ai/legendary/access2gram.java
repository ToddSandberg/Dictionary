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
    public static HashMap<String, HashMap<String, Integer>> threegrams = new HashMap<String, HashMap<String, Integer>>();
    public static DictionaryAccess dict = new DictionaryAccess();

    public static void main(String[] args) {
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream("outputs/preprocessed2gram.ser"));
            System.out.println("Loading 2grams");
            twograms = (HashMap<String, HashMap<String, Integer>>) in
                    .readObject();
            System.out.println("Loaded");
            ObjectInputStream in2 = new ObjectInputStream(
                    new FileInputStream("outputs/preprocessed3gram.ser"));
            System.out.println("Loading 3grams");
            threegrams = (HashMap<String, HashMap<String, Integer>>) in2
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
            
            HashMap<String, HashMap<String, Double>> adjnhash= new HashMap<String, HashMap<String, Double>>();
            HashMap<String, HashMap<String, Double>> adjadvhash= new HashMap<String, HashMap<String, Double>>();
            HashMap<String, HashMap<String, Double>> prepnhash= new HashMap<String, HashMap<String, Double>>();
            HashMap<String, HashMap<String, Double>> detnhash= new HashMap<String, HashMap<String, Double>>();
            HashMap<String, HashMap<String, Double>> prepvhash= new HashMap<String, HashMap<String, Double>>();
            HashMap<String, HashMap<String, Double>> nounvhash= new HashMap<String, HashMap<String, Double>>();
            HashMap<String, HashMap<String, Double>> advvhash= new HashMap<String, HashMap<String, Double>>();
            HashMap<String, HashMap<String, Double>> vobjhash= new HashMap<String, HashMap<String, Double>>();

            HashMap<String, Noun> noundict = dict.getNounDictionary();
            HashMap<String, Adjective> adjdict = dict.getAdjectiveDictionary();
            HashMap<String, Adverb> advdict = dict.getAdverbDictionary();
            HashMap<String, Determiner> detdict = dict.getDeterminerDictionary();
            HashMap<String, Preposition> prepdict = dict.getPrepositionDictionary();
            HashMap<String, Verb> verbdict = dict.getVerbDictionary();
            HashMap<String, Pronoun> prondict = dict.getPronounDictionary();
            HashMap<String, Conjunction> conjdict = dict.getConjunctionDictionary();

            Iterator<Entry<String, HashMap<String, Integer>>> it = twograms
                    .entrySet().iterator();
            while (it.hasNext()) {
                Entry<String, HashMap<String, Integer>> pair = it.next();
                String word = pair.getKey(); 
                if (word.charAt(0) == '-') {
                    word = word.substring(1, word.length());
                    HashMap<String, Integer> values = pair.getValue();
                    HashMap<String, Double> percentages = getPercentages(values);
                    Iterator<Entry<String, Integer>> it2 = values.entrySet()
                            .iterator();
                    while (it2.hasNext()) {
                        Entry<String, Integer> pair2 = it2.next();
                        if (noundict.containsKey(word)) {
                            String key = pair2.getKey();
                            String beginword = key.split("\\|")[0];
                            if (adjdict.containsKey(beginword) && !detdict.containsKey(beginword) && !prepdict.containsKey(beginword) && !conjdict.containsKey(beginword) && !prondict.containsKey(beginword)) {
                                if(adjnhash.containsKey(word)){
                                    HashMap<String,Double> temp = adjnhash.get(word);
                                    temp.put(beginword,percentages.get(key));
                                    adjnhash.put(word, temp);
                                }
                                else{
                                    HashMap<String,Double> temp = new HashMap<String,Double>();
                                    temp.put(beginword,percentages.get(key));
                                    adjnhash.put(word, temp);
                                }
                            }
                            else if(detdict.containsKey(beginword)){
                                if(detnhash.containsKey(word)){
                                    HashMap<String,Double> temp = detnhash.get(word);
                                    temp.put(beginword,percentages.get(key));
                                    detnhash.put(word, temp);
                                }
                                else{
                                    HashMap<String,Double> temp = new HashMap<String,Double>();
                                    temp.put(beginword,percentages.get(key));
                                    detnhash.put(word, temp);
                                }
                            }
                            else if(prepdict.containsKey(beginword)){
                                if(prepnhash.containsKey(word)){
                                    HashMap<String,Double> temp = prepnhash.get(word);
                                    temp.put(beginword,percentages.get(key));
                                    prepnhash.put(word, temp);
                                }
                                else{
                                    HashMap<String,Double> temp = new HashMap<String,Double>();
                                    temp.put(beginword,percentages.get(key));
                                    prepnhash.put(word, temp);
                                }
                            }
                            else if(verbdict.containsKey(beginword) && !conjdict.containsKey(beginword) && !prepdict.containsKey(beginword) && !adjdict.containsKey(beginword) && !beginword.equals("is") && !beginword.equals("be") && !beginword.equals("was")){
                                if(vobjhash.containsKey(word)){
                                    HashMap<String,Double> temp = vobjhash.get(word);
                                    temp.put(beginword,percentages.get(key));
                                    vobjhash.put(word, temp);
                                }
                                else{
                                    HashMap<String,Double> temp = new HashMap<String,Double>();
                                    temp.put(beginword,percentages.get(key));
                                    vobjhash.put(word, temp);
                                }
                            }
                        }
                        if(adjdict.containsKey(word)){
                            String key =pair2.getKey();
                            String beginword = key.split("\\|")[0];
                            if (advdict.containsKey(beginword)&& !detdict.containsKey(beginword)&& !prepdict.containsKey(beginword) && !prondict.containsKey(beginword) && !conjdict.containsKey(beginword)) {
                                if(adjadvhash.containsKey(word)){
                                    HashMap<String, Double> temp = adjadvhash.get(word);
                                    temp.put(beginword,percentages.get(key));
                                    adjadvhash.put(word, temp);
                                }
                                else{
                                    HashMap<String, Double> temp = new HashMap<String,Double>();
                                    temp.put(beginword,percentages.get(key));
                                    adjadvhash.put(word, temp);
                                }
                            }
                        }
                        if(verbdict.containsKey(word)){
                            String key = pair2.getKey();
                            String beginword = key.split("\\|")[0];
                            if (prepdict.containsKey(beginword)) {
                                if(prepvhash.containsKey(word)){
                                    HashMap<String,Double> temp = prepvhash.get(word);
                                    temp.put(beginword,percentages.get(key));
                                    prepvhash.put(word, temp);
                                }
                                else{
                                    HashMap<String,Double> temp = new HashMap<String,Double>();
                                    temp.put(beginword,percentages.get(key));
                                    prepvhash.put(word, temp);
                                }
                            }
                            else if (noundict.containsKey(beginword) && !adjdict.containsKey(beginword) && !detdict.containsKey(beginword) && !conjdict.containsKey(beginword) && !verbdict.containsKey(beginword)) {
                                if(nounvhash.containsKey(word)){
                                    HashMap<String,Double> temp = nounvhash.get(word);
                                    temp.put(beginword,percentages.get(key));
                                    nounvhash.put(word, temp);
                                }
                                else{
                                    HashMap<String,Double> temp = new HashMap<String,Double>();
                                    temp.put(beginword,percentages.get(key));
                                    nounvhash.put(word, temp);
                                }
                            }
                            else if(advdict.containsKey(beginword) && !adjdict.containsKey(beginword) && !detdict.containsKey(beginword) && !conjdict.containsKey(beginword)){
                                if(advvhash.containsKey(word)){
                                    HashMap<String,Double> temp = advvhash.get(word);
                                    temp.put(beginword,percentages.get(key));
                                    advvhash.put(word, temp);
                                }
                                else{
                                    HashMap<String,Double> temp = new HashMap<String,Double>();
                                    temp.put(beginword,percentages.get(key));
                                    advvhash.put(word, temp);
                                }
                            }
                        }
                    }
                    if(threegrams.containsKey("-"+word) && noundict.containsKey(word)){
                        HashMap<String,Integer> values2 = threegrams.get("-"+word);
                        HashMap<String,Double> percentages2 = getPercentages(values2);
                        Iterator<Entry<String, Integer>> it3 = values2.entrySet().iterator();
                        while(it3.hasNext()){
                            Entry<String, Integer> pair2 = it3.next();
                            String key = pair2.getKey();
                            String beginword = key.split("\\|")[0];
                            String middleword = key.split("\\|")[1];
                            if(verbdict.containsKey(beginword) && !prepdict.containsKey(beginword) && !conjdict.containsKey(beginword)){
                                if(noundict.containsKey(word)){
                                    if(vobjhash.containsKey(word)){
                                        HashMap<String, Double> temp = vobjhash.get(word);
                                        temp.put(beginword + " " + middleword, percentages2.get(key));
                                        vobjhash.put(word, temp);
                                    }
                                    else{
                                        HashMap<String, Double> temp = new HashMap<String,Double>();
                                        temp.put(beginword + " " + middleword, percentages2.get(key));
                                        vobjhash.put(word, temp);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            printHashMaps(adjnhash,new File("outputs/adjtonoun.txt"));
            printHashMaps(prepnhash,new File("outputs/preptonoun.txt"));
            printHashMaps(adjadvhash,new File("outputs/advtoadj.txt"));
            printHashMaps(detnhash, new File("outputs/dettonoun.txt"));
            printHashMaps(prepvhash,new File("outputs/preptoverb.txt"));
            printHashMaps(advvhash, new File("outputs/advtoverb.txt"));
            printHashMaps(nounvhash, new File("outputs/nountoverb.txt"));
            printHashMaps(vobjhash, new File("outputs/verbtoobject.txt"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void printHashMaps(HashMap<String,HashMap<String,Double>> temp,File file){
        try{
            PrintWriter printer = new PrintWriter(file);
            for (Entry<String, HashMap<String, Double>> entry : temp.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                printer.println(key + " : " + value);
            }
        }
        catch(Exception e){
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
                //System.out.println("total: " + total + " p: "+p + " p/total: "+(p/total) + " p/total * 100: "+((p/total)*100));
                double percentage = ((p / total) * 100);
                
                String s = (String) pair.getKey();
                //System.out.println(s + " : "+percentage);
                result.put(s, percentage);
                // it2.remove();
            }
            // System.out.println("percent hashmap: "+result.toString());
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
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
