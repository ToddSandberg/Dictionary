package ai.legendary;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class DictionaryAccess {
    /*
     * dictionarys seperated into their parts of speech
     */
    public static HashMap<String, Noun> nounDictionary;
    public static HashMap<String, Verb> verbDictionary;
    public static HashMap<String, Adjective> adjectiveDictionary;
    public static HashMap<String, Adverb> adverbDictionary;
    public static HashMap<String, Conjunction> conjunctionDictionary;
    public static HashMap<String, Determiner> determinerDictionary;
    public static HashMap<String, Interjection> interjectionDictionary;
    public static HashMap<String, Preposition> prepositionDictionary;
    public static HashMap<String, Pronoun> pronounDictionary;
    public static HashMap<String, Quantifier> quantifierDictionary;
    public static HashMap<String, String> roots;
    /*
     * Loads the Dictionarys into hashmaps for easy access
     */
    public DictionaryAccess() {
        ObjectInputStream in;
        try {
            // load nouns
            in = new ObjectInputStream(
                    new FileInputStream("outputs/nounDictHashMap.ser"));
            System.out.println("Loading noun dictionary...");
            nounDictionary = (HashMap<String, Noun>) in.readObject();
            in.close();
            // load verbs
            in = new ObjectInputStream(
                    new FileInputStream("outputs/verbDictHashMap.ser"));
            System.out.println("Loading verbdictionary...");
            verbDictionary = (HashMap<String, Verb>) in.readObject();
            in.close();
            // adjectives
            in = new ObjectInputStream(
                    new FileInputStream("outputs/adjDictHashMap.ser"));
            System.out.println("Loading adjective dictionary...");
            adjectiveDictionary = (HashMap<String, Adjective>) in.readObject();
            in.close();
            // adverbs
            in = new ObjectInputStream(
                    new FileInputStream("outputs/advDictHashMap.ser"));
            System.out.println("Loading adverb dictionary...");
            adverbDictionary = (HashMap<String, Adverb>) in.readObject();
            in.close();
            // conjunction
            in = new ObjectInputStream(
                    new FileInputStream("outputs/conjDictHashMap.ser"));
            System.out.println("Loading conjunction dictionary...");
            conjunctionDictionary = (HashMap<String, Conjunction>) in
                    .readObject();
            in.close();
            // determiners
            in = new ObjectInputStream(
                    new FileInputStream("outputs/detDictHashMap.ser"));
            System.out.println("Loading determiner dictionary...");
            determinerDictionary = (HashMap<String, Determiner>) in
                    .readObject();
            in.close();
            // interjection
            in = new ObjectInputStream(new FileInputStream(
                    "outputs/interjectionDictHashMap.ser"));
            System.out.println("Loading interjection dictionary...");
            interjectionDictionary = (HashMap<String, Interjection>) in
                    .readObject();
            in.close();
            // prepositions
            in = new ObjectInputStream(
                    new FileInputStream("outputs/prepDictHashMap.ser"));
            System.out.println("Loading preposition dictionary...");
            prepositionDictionary = (HashMap<String, Preposition>) in
                    .readObject();
            in.close();
            // pronouns
            in = new ObjectInputStream(
                    new FileInputStream("outputs/pronDictHashMap.ser"));
            System.out.println("Loading pronoun dictionary...");
            pronounDictionary = (HashMap<String, Pronoun>) in.readObject();
            in.close();
            // quantifiers
            in = new ObjectInputStream(
                    new FileInputStream("outputs/quantifierDictHashMap.ser"));
            System.out.println("Loading quantifier dictionary...");
            quantifierDictionary = (HashMap<String, Quantifier>) in
                    .readObject();
            in.close();
            //roots
            in = new ObjectInputStream(
                    new FileInputStream("outputs/roots.ser"));
            System.out.println("Loading roots...");
            quantifierDictionary = (HashMap<String, Quantifier>) in
                    .readObject();
            in.close();

            System.out.println("Loaded");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * gets the info on a singular word with a specific part of speech
     * @param word is the word to get info on
     * @param pos the part of speech of the word
     * @return the info contained in the dictionary for the given word
     */
    public String getWordInfo(String word, String pos) {

        if (getdictionary(pos).containsKey(word)) {
            return "Word: " + word + "\n"
                    + getdictionary(pos).get(word).toString() + "\n";
        }
        else
            return word + " " + pos + " is not in Dictionary\n";
    }
    /**
     * gets the info on a singular word with any part of speech
     * @param word is the word to get info on
     * @return the info contained in the dictionary for the given word
     */
    public String getWordInfo(String word) {
        String p = "Word: " + word + "\n";
        if (nounDictionary.containsKey(word)) {
            p += nounDictionary.get(word).toString() + "\n";
        }
        if (verbDictionary.containsKey(word)) {
            p += verbDictionary.get(word).toString() + "\n";
        }
        if (adjectiveDictionary.containsKey(word)) {
            p += adjectiveDictionary.get(word).toString() + "\n";
        }
        if (adverbDictionary.containsKey(word)) {
            p += adverbDictionary.get(word).toString() + "\n";
        }
        if (conjunctionDictionary.containsKey(word)) {
            p += conjunctionDictionary.get(word).toString() + "\n";
        }
        if (determinerDictionary.containsKey(word)) {
            p += determinerDictionary.get(word).toString() + "\n";
        }
        if (interjectionDictionary.containsKey(word)) {
            p += interjectionDictionary.get(word).toString() + "\n";
        }
        if (prepositionDictionary.containsKey(word)) {
            p += prepositionDictionary.get(word).toString() + "\n";
        }
        if (pronounDictionary.containsKey(word)) {
            p += pronounDictionary.get(word).toString() + "\n";
        }
        if (quantifierDictionary.containsKey(word)) {
            p += quantifierDictionary.get(word).toString() + "\n";
        }

        return p;
    }
    /**
     * gets the info on multiple words with part of speech given by CoreNLP
     * @param word is the sentence to be given
     * @return the info on each word in the sentence
     */
    public String getMultipleWordInfo(String word) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, parse");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Annotation document = new Annotation(word);
        pipeline.annotate(document);
        String output = "\n";
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
                String w = token.get(TextAnnotation.class);
                String pos = token.get(PartOfSpeechAnnotation.class);
                if (convertnlp(pos) != null)
                    output += getWordInfo(w, convertnlp(pos)) + "\n";
                else
                    output += "Puncuation";
            }
        }
        sentences.clear();
        return output;
    }
    /**
     * converts the corenlp return to a readable part of speech for the program
     * @param pos is what coreNLP returned
     * @return the formatted part of speech for the program
     */
    private String convertnlp(String pos) {
        if (pos.startsWith("NN")) {
            return "noun";
        }
        else if (pos.startsWith("JJ") || pos.equals("WDT")) {
            return "adjective";
        }
        else if (pos.startsWith("RB") || pos.equals("WRB")) {
            return "adverb";
        }
        else if (pos.equals("CC")) {
            return "conjunction";
        }
        else if (pos.equals("DT")) {
            return "determiner";
        }
        else if (pos.equals("UH")) {
            return "interjection";
        }
        else if (pos.equals("IN") || pos.equals("RP") || pos.equals("TO")) {
            return "preposition";
        }
        else if (pos.startsWith("PR") || pos.startsWith("WP")) {
            return "pronoun";
        }
        else if (pos.equals("CD") || pos.equals("PDT")) {
            return "quantifier";
        }
        else if (pos.equals("MD") || pos.startsWith("VB")) {
            return "verb";
        }
        else
            return null;
    }
    /**
     * gets the dictionary based on a part of speech string
     * @param pos the part of speech of the intended dictionary
     * @return the dictionary with the intended part of speech
     */
    private static HashMap<String, ?> getdictionary(String pos) {
        pos = pos.toLowerCase();
        if (pos.equals("noun") || pos.equals("nn")) {
            return nounDictionary;
        }
        else if (pos.equals("adjective") || pos.equals("adj")
                || pos.equals("jj")) {
            return adjectiveDictionary;
        }
        else if (pos.equals("adverb") || pos.equals("adv")) {
            return adverbDictionary;
        }
        else if (pos.equals("conjunction") || pos.equals("conj")) {
            return conjunctionDictionary;
        }
        else if (pos.equals("determiner") || pos.equals("det")) {
            return determinerDictionary;
        }
        else if (pos.equals("interjection") || pos.equals("int")) {
            return interjectionDictionary;
        }
        else if (pos.equals("preposition") || pos.equals("prep")
                || pos.equals("pp")) {
            return prepositionDictionary;
        }
        else if (pos.equals("pronoun") || pos.equals("pron")) {
            return pronounDictionary;
        }
        else if (pos.equals("quantifier") || pos.equals("quant")) {
            return quantifierDictionary;
        }
        else if (pos.equals("verb") || pos.equals("vb")) {
            return verbDictionary;
        }
        else
            return null;
    }
    /**
     * changes the word from one part of speech to another
     * @param word1 original word
     * @param pos1 original part of speech
     * @param pos2 final part of speech
     * @return a list of words with pos2 and the same base word as word1
     */
    public String changePOS(String word1, String pos1, String pos2){
        pos1 = pos1.toLowerCase();
        pos2 = pos2.toLowerCase();
        if(getdictionary(pos1).containsKey(word1)){
            String results = "";
            String base = "";
            if(pos1.equals("noun")){
                base = ((Noun)getdictionary(pos1).get(word1)).baseForm;
            }
            else if(pos1.equals("verb")){
                base = ((Verb)getdictionary(pos1).get(word1)).baseForm;
            }
            else if(pos1.equals("adverb")){
                base = ((Adverb)getdictionary(pos1).get(word1)).baseForm;
            }
            else if(pos1.equals("adjective")){
                base = ((Adjective)getdictionary(pos1).get(word1)).baseForm;
            }
            if(pos2.equals("noun")){
                if(getdictionary(pos2).containsKey(base)){
                    results+=base;
                }
                HashMap<String,Noun> temp = (HashMap<String, Noun>) getdictionary(pos2);
                Iterator it = temp.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    if(((Noun)pair.getValue()).baseForm.equals(base) && !base.equals("--")){
                        results+= ", " + pair.getKey();
                    }
                    it.remove(); // avoids a ConcurrentModificationException
                }
            }
            else if(pos2.equals("verb")){
                if(getdictionary(pos2).containsKey(base)){
                    results+=base;
                }
                HashMap<String,Noun> temp = (HashMap<String, Noun>) getdictionary(pos2);
                Iterator it = temp.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    if(((Verb)pair.getValue()).baseForm.equals(base)&& !base.equals("--")){
                        results+= ", " +pair.getKey();
                    }
                    it.remove(); // avoids a ConcurrentModificationException
                }
            }
            else if(pos2.equals("adverb")){
                if(getdictionary(pos2).containsKey(base)){
                    results+=base;
                }
                HashMap<String,Noun> temp = (HashMap<String, Noun>) getdictionary(pos2);
                Iterator it = temp.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    if(((Adverb)pair.getValue()).baseForm.equals(base)&& !base.equals("--")){
                        results+= ", " + pair.getKey();
                    }
                    it.remove(); // avoids a ConcurrentModificationException
                }
            }
            else if(pos2.equals("adjective")){
                if(getdictionary(pos2).containsKey(base)){
                    results+=base;
                }
                HashMap<String,Noun> temp = (HashMap<String, Noun>) getdictionary(pos2);
                Iterator it = temp.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    if(((Adjective)pair.getValue()).baseForm.equals(base)&& !base.equals("--")){
                        results+= ", " + pair.getKey();
                    }
                    it.remove(); // avoids a ConcurrentModificationException
                }
            }
            return results;
        }
        else{
            return word1 + ":" + pos1 + " is not in the dictionary";
        }
    }
    
    public String changePOSw(String word1,String pos1, String pos2){
        pos1 = pos1.toLowerCase();
        pos2 = pos2.toLowerCase();
        if(getdictionary(pos1).containsKey(word1)){
            String results = "";
            String base = "";
            if(pos1.equals("noun")){
                base = ((Noun)getdictionary(pos1).get(word1)).baseForm;
            }
            else if(pos1.equals("verb")){
                base = ((Verb)getdictionary(pos1).get(word1)).baseForm;
            }
            else if(pos1.equals("adverb")){
                base = ((Adverb)getdictionary(pos1).get(word1)).baseForm;
            }
            else if(pos1.equals("adjective")){
                base = ((Adjective)getdictionary(pos1).get(word1)).baseForm;
            }
            if(roots.containsKey(base+":"+pos2)){
                results = roots.get(base+":"+pos2);
            }
            return results;
        }
        else{
            return word1 + ":" + pos1 + " is not in the dictionary";
        }
    }
}
