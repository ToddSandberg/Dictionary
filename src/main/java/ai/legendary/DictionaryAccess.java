package ai.legendary;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;
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

    public DictionaryAccess() {
        ObjectInputStream in;
        try {
            //load nouns
            in = new ObjectInputStream(
                    new FileInputStream("outputs/nounDictHashMap.ser"));
            System.out.println("Loading noun dictionary...");
            nounDictionary = (HashMap<String, Noun>) in.readObject();
            in.close();
            //load verbs
            in = new ObjectInputStream(
                    new FileInputStream("outputs/verbDictHashMap.ser"));
            System.out.println("Loading verbdictionary...");
            verbDictionary = (HashMap<String, Verb>) in.readObject();
            in.close();
            //adjectives
            in = new ObjectInputStream(
                    new FileInputStream("outputs/adjDictHashMap.ser"));
            System.out.println("Loading adjective dictionary...");
            adjectiveDictionary = (HashMap<String, Adjective>) in.readObject();
            in.close();
            //adverbs
            in = new ObjectInputStream(
                    new FileInputStream("outputs/advDictHashMap.ser"));
            System.out.println("Loading adverb dictionary...");
            adverbDictionary = (HashMap<String, Adverb>) in.readObject();
            in.close();
            //conjunction
            in = new ObjectInputStream(
                    new FileInputStream("outputs/conjDictHashMap.ser"));
            System.out.println("Loading conjunction dictionary...");
            conjunctionDictionary = (HashMap<String, Conjunction>) in.readObject();
            in.close();
            //determiners
            in = new ObjectInputStream(
                    new FileInputStream("outputs/detDictHashMap.ser"));
            System.out.println("Loading determiner dictionary...");
            determinerDictionary = (HashMap<String, Determiner>) in.readObject();
            in.close();
            //interjection
            in = new ObjectInputStream(
                    new FileInputStream("outputs/interjectionDictHashMap.ser"));
            System.out.println("Loading interjection dictionary...");
            interjectionDictionary = (HashMap<String, Interjection>) in.readObject();
            in.close();
            //prepositions
            in = new ObjectInputStream(
                    new FileInputStream("outputs/prepDictHashMap.ser"));
            System.out.println("Loading preposition dictionary...");
            prepositionDictionary = (HashMap<String, Preposition>) in.readObject();
            in.close();
            //pronouns
            in = new ObjectInputStream(
                    new FileInputStream("outputs/pronDictHashMap.ser"));
            System.out.println("Loading pronoun dictionary...");
            pronounDictionary = (HashMap<String, Pronoun>) in.readObject();
            in.close();
            //quantifiers
            in = new ObjectInputStream(
                    new FileInputStream("outputs/quantifierDictHashMap.ser"));
            System.out.println("Loading quantifier dictionary...");
            quantifierDictionary = (HashMap<String, Quantifier>) in.readObject();
            in.close();
            
            System.out.println("Loaded");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getWordInfo(String word, String pos) {
        
        if(getdictionary(pos).containsKey(word)){
            return "Word: "+word+  "\n"+getdictionary(pos).get(word).toString() + "\n";
        }
        else
            return word + " " + pos + " is not in Dictionary\n";
    }

    public String getWordInfo(String word) {
        String p = "Word: "+word+"\n";
        if (nounDictionary.containsKey(word)){
            p+=nounDictionary.get(word).toString() + "\n";
        }
        if(verbDictionary.containsKey(word)){
            p+=verbDictionary.get(word).toString() + "\n";
        }
        if(adjectiveDictionary.containsKey(word)){
            p+=adjectiveDictionary.get(word).toString() + "\n";
        }
        if(adverbDictionary.containsKey(word)){
            p+=adverbDictionary.get(word).toString()+ "\n";
        }
        if(conjunctionDictionary.containsKey(word)){
            p+=conjunctionDictionary.get(word).toString()+ "\n";
        }
        if(determinerDictionary.containsKey(word)){
            p+=determinerDictionary.get(word).toString()+ "\n";
        }
        if(interjectionDictionary.containsKey(word)){
            p+=interjectionDictionary.get(word).toString()+ "\n";
        }
        if(prepositionDictionary.containsKey(word)){
            p+=prepositionDictionary.get(word).toString()+ "\n";
        }
        if(pronounDictionary.containsKey(word)){
            p+=pronounDictionary.get(word).toString()+ "\n";
        }
        if(quantifierDictionary.containsKey(word)){
            p+=quantifierDictionary.get(word).toString()+ "\n";
        }

            return p;
    }

    public String getMultipleWordInfo(String word) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, parse");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Annotation document = new Annotation(word);
        pipeline.annotate(document);
        String output = "\n";
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        for(CoreMap sentence: sentences) {
            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
                String w = token.get(TextAnnotation.class);
                String pos = token.get(PartOfSpeechAnnotation.class);
                if(convertnlp(pos) != null)
                output += getWordInfo(w,convertnlp(pos)) + "\n";
                else output += "Puncuation";
              }
        }
        sentences.clear();
        return output;
    }
    private String convertnlp(String pos) {
        if(pos.startsWith("NN")){
            return "noun";
        }
        else if(pos.startsWith("JJ") || pos.equals("WDT")){
            return "adjective";
        }
        else if(pos.startsWith("RB") || pos.equals("WRB")){
            return "adverb";
        }
        else if(pos.equals("CC")){
            return "conjunction";
        }
        else if(pos.equals("DT")){
            return "determiner";
        }
        else if(pos.equals("UH")){
            return "interjection";
        }
        else if(pos.equals("IN") || pos.equals("RP") || pos.equals("TO")){
            return "preposition";
        }
        else if(pos.startsWith("PR") || pos.startsWith("WP")){
            return "pronoun";
        }
        else if(pos.equals("CD") || pos.equals("PDT")){
            return "quantifier";
        }
        else if(pos.equals("MD") || pos.startsWith("VB")){
            return "verb";
        }
        else return null;
    }

    private static HashMap<String, ?> getdictionary(String pos){
        if(pos.equals("noun")){
            return nounDictionary;
        }
        else if(pos.equals("adjective")){
            return adjectiveDictionary;
        }
        else if(pos.equals("adverb")){
            return adverbDictionary;
        }
        else if(pos.equals("conjunction")){
            return conjunctionDictionary;
        }
        else if(pos.equals("determiner")){
            return determinerDictionary;
        }
        else if(pos.equals("interjection")){
            return interjectionDictionary;
        }
        else if(pos.equals("preposition")){
            return prepositionDictionary;
        }
        else if(pos.equals("pronoun")){
            return pronounDictionary;
        }
        else if(pos.equals("quantifier")){
            return quantifierDictionary;
        }
        else if(pos.equals("verb")){
            return verbDictionary;
        }
        else return null;
    }
}
