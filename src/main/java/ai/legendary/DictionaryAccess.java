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
    public static HashMap<String, Noun> nounDictionary = new HashMap<String, Noun>();
    public static HashMap<String, Verb> verbDictionary = new HashMap<String, Verb>();
    public static HashMap<String, Adjective> adjectiveDictionary = new HashMap<String, Adjective>();
    public static HashMap<String, Adverb> adverbDictionary = new HashMap<String, Adverb>();
    public static HashMap<String, Conjunction> conjunctionDictionary = new HashMap<String, Conjunction>();
    public static HashMap<String, Determiner> determinerDictionary = new HashMap<String, Determiner>();
    public static HashMap<String, Interjection> interjectionDictionary = new HashMap<String, Interjection>();
    public static HashMap<String, Preposition> prepositionDictionary = new HashMap<String, Preposition>();
    public static HashMap<String, Pronoun> pronounDictionary = new HashMap<String, Pronoun>();
    public static HashMap<String, Quantifier> quantifierDictionary = new HashMap<String, Quantifier>();

    public DictionaryAccess() {
        ObjectInputStream in;
        try {
            //load nouns
            in = new ObjectInputStream(
                    new FileInputStream("outputs/nounDictHashMap.txt"));
            System.out.println("Loading noun dictionary...");
            nounDictionary = (HashMap) in.readObject();
            in.close();
            //load verbs
            in = new ObjectInputStream(
                    new FileInputStream("outputs/verbDictHashMap.txt"));
            System.out.println("Loading verbdictionary...");
            verbDictionary = (HashMap) in.readObject();
            in.close();
            //adjectives
            in = new ObjectInputStream(
                    new FileInputStream("outputs/adjDictHashMap.txt"));
            System.out.println("Loading adjective dictionary...");
            adjectiveDictionary = (HashMap) in.readObject();
            in.close();
            //adverbs
            in = new ObjectInputStream(
                    new FileInputStream("outputs/advDictHashMap.txt"));
            System.out.println("Loading adverb dictionary...");
            adverbDictionary = (HashMap) in.readObject();
            in.close();
            //conjunction
            in = new ObjectInputStream(
                    new FileInputStream("outputs/conjDictHashMap.txt"));
            System.out.println("Loading conjunction dictionary...");
            conjunctionDictionary = (HashMap) in.readObject();
            in.close();
            //determiners
            in = new ObjectInputStream(
                    new FileInputStream("outputs/detDictHashMap.txt"));
            System.out.println("Loading determiner dictionary...");
            determinerDictionary = (HashMap) in.readObject();
            in.close();
            //interjection
            in = new ObjectInputStream(
                    new FileInputStream("outputs/interjectionDictHashMap.txt"));
            System.out.println("Loading interjection dictionary...");
            interjectionDictionary = (HashMap) in.readObject();
            in.close();
            //prepositions
            in = new ObjectInputStream(
                    new FileInputStream("outputs/prepDictHashMap.txt"));
            System.out.println("Loading preposition dictionary...");
            prepositionDictionary = (HashMap) in.readObject();
            in.close();
            //pronouns
            in = new ObjectInputStream(
                    new FileInputStream("outputs/pronDictHashMap.txt"));
            System.out.println("Loading pronoun dictionary...");
            pronounDictionary = (HashMap) in.readObject();
            in.close();
            //quantifiers
            in = new ObjectInputStream(
                    new FileInputStream("outputs/quantifierDictHashMap.txt"));
            System.out.println("Loading quantifier dictionary...");
            quantifierDictionary = (HashMap) in.readObject();
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
            return word + " " + getdictionary(pos) + " is not in Dictionary\n";
    }

    public String getWordInfo(String word) {
        String p = "Word: "+word+"\n";
        if (nounDictionary.containsKey(word)){
            p+=nounDictionary.get(word) + "\n";
        }
        if(verbDictionary.containsKey(word)){
            p+=verbDictionary.get(word) + "\n";
        }
        if(adjectiveDictionary.containsKey(word)){
            p+=adjectiveDictionary.get(word) + "\n";
        }
        if(adverbDictionary.containsKey(word)){
            p+=adverbDictionary.get(word)+ "\n";
        }
        if(conjunctionDictionary.containsKey(word)){
            p+=conjunctionDictionary.get(word)+ "\n";
        }
        if(determinerDictionary.containsKey(word)){
            p+=determinerDictionary.get(word)+ "\n";
        }
        if(interjectionDictionary.containsKey(word)){
            p+=interjectionDictionary.get(word)+ "\n";
        }
        if(prepositionDictionary.containsKey(word)){
            p+=prepositionDictionary.get(word)+ "\n";
        }
        if(pronounDictionary.containsKey(word)){
            p+=pronounDictionary.get(word)+ "\n";
        }
        if(quantifierDictionary.containsKey(word)){
            p+=quantifierDictionary.get(word)+ "\n";
        }

            return p;
    }

    public String getMultipleWordInfo(String word) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Annotation document = new Annotation(word);
        pipeline.annotate(document);
        String output = "\n";
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        for(CoreMap sentence: sentences) {
            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
                String w = token.get(TextAnnotation.class);
                String pos = token.get(PartOfSpeechAnnotation.class);
                output += getWordInfo(w,convertnlp(pos)) + "\n";
              }
        }
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

    private static HashMap getdictionary(String pos){
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
