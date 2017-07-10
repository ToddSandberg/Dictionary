package ai.legendary;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

public class access2gram {
    public static HashMap<String, HashMap<String, Integer>> twograms = new HashMap<String, HashMap<String, Integer>>();

    public static void main(String[] args) {
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream("outputs/preprocessed2gram.ser"));
            System.out.println("Loading 2grams");
            twograms = (HashMap<String, HashMap<String, Integer>>) in
                    .readObject();
            System.out.println("Loaded");
            in.close();
            while(true){
            String word = JOptionPane.showInputDialog("insert a word");
            //System.out.println(word + " : " + twograms.get(word));
            //System.out.println(getPercentages(twograms.get(word)));
            String sentence = word + " ";
            for (int x = 0; x < 10; x++) {
                 String otherword = highestPercentageWord(
                        getPercentages(twograms.get(word + "-")));
                word = otherword;
                //System.out.println(word);
                sentence += word + " ";
            }
            System.out.println(sentence);
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, Double> getPercentages(HashMap<String, Integer> temp) {
            HashMap<String, Double> result = new HashMap<String, Double>();
            HashMap<String, Integer> temp2 = temp;
            //System.out.println("temp2: "+temp2.toString());
            HashMap<String, Integer> temp3 =temp;
            double total = 0;
            Iterator<Entry<String, Integer>> it = temp2.entrySet().iterator();
            while (it.hasNext()) {
                Entry<String, Integer> pair = it.next();
                total += pair.getValue();
            }
            //System.out.println("temp3: "+temp3.toString());
            Iterator<Entry<String, Integer>> it2 = temp3.entrySet().iterator();
            while (it2.hasNext()) {
                Entry<String, Integer> pair = it2.next();
                double p = (Integer) pair.getValue();
                double percentage = ((p / total) * 100);
                String s = (String) pair.getKey();
                result.put(s, percentage);
                //it2.remove();
            }
            //System.out.println("percent hashmap: "+result.toString());
            return result; 
    }

    public static String highestPercentageWord(HashMap<String, Double> temp) {
        double highest = 0;
        String higheststring = "";
        String [] random = new String[100];
        Iterator it = temp.entrySet().iterator();
        int index=0;
        try{
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            double dou = ((Double) pair.getValue()).doubleValue(); //intValue();
            int percentage = (int)dou + 1;
            //System.out.print(percentage + " : " + ((String) pair.getKey()).split("\\|")[1] + "   ");
            for(int x=index;x<(index+percentage);x++){
                random[x] = ((String) pair.getKey()).split("\\|")[1];
                //System.out.println(index + " : " + ((String) pair.getKey()).split("\\|")[1]);
            }
            index += percentage;
        }
        //System.out.println("");
        }
        catch(Exception e){
            //e.printStackTrace();
        }
        int randomest = ((int)(Math.random()*100));
        //System.out.println(Arrays.toString(random));
        return random[randomest];
    }

    public static double highestPercentageDouble(
            HashMap<String, Double> temp) {
        if (temp!=null && !temp.isEmpty()) {
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
