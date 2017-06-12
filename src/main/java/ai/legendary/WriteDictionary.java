package ai.legendary;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WriteDictionary {
    /**
     * Dictionarys
     */
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

    public static HashMap<String,String> animacy = new HashMap<String,String>();
    static FileOutputStream fout;
    static ObjectOutputStream out;
    static int seconds = 0;
    static Timer time = new Timer();
    static TimerTask ttask = new TimerTask(){
        @Override
        public void run() {
            seconds++;
        }
    };
    public static void main(String[] args) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("outputs/animacyquery.ser"));
            animacy=(HashMap<String,String>)in.readObject();  
            in.close();
            System.out.println("Starting");
            
            //counts seconds until the program finishes
            time.scheduleAtFixedRate(ttask,1000,1000);
            File f = new File("README.md");
            PrintWriter printed = new PrintWriter(f);
            printed.println("# DictionaryMaker");
            printed.close();
            FileWriter fw = new FileWriter(f, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter printer = new PrintWriter(bw);
            printer.println("## Document Implementation");
            // Adds all subordinate conjunctions to the dictionary
            subordinateConjunctions();
            printer.println("- subordinateConjunctions.txt Implemented");
            /*store animacy querys for future runs*/
            fout=new FileOutputStream("outputs/animacyquery.ser");  
            out=new ObjectOutputStream(fout);  
            out.writeObject(animacy);  
            out.flush();  
            out.close();  
            fout.close(); 
            // Adds all specific gender nouns to the dictionary
            nounGender();
            printer.println("- nounGenderList.txt Implemented ");
            /*store animacy querys for future runs*/
            fout=new FileOutputStream("outputs/animacyquery.ser");  
            out=new ObjectOutputStream(fout);  
            out.writeObject(animacy);  
            out.flush();  
            out.close();  
            fout.close(); 
            // Implements MobyWordListWithPOS.txt to the dictionary
            mobyListPOS();
            printer.println("- MobyWordListWithPOS.txt Implemented");
            
            // Implements default-lexion.xml
            defLexicon();
            printer.println("- default-lexicon.xml Implemented");
            // adds count nouns and non count nouns to the dictionary
            countNounOrNot();
            printer.println(
                    "- NounList_CountNounsOnly.txt and NounsList_MassNounsOnly.txt implemented");
            // Implements SPECIALIST LEXICON
            speclexicon();
            printer.println("- LEXICON.txt semiImplemented");
            // Implements Adverb Scales
            adverbIntensifiers();
            printer.println("- AdverbScales-Manual.xlsx semiImplemented");

            printer.println("## Document Output Formats:");
            printer.println(
                    "- csv files for each part of speech with words and their properties");
            printer.println(
                    "- serialized dictionary classes for each part of speech");
            
            //prints dictionary analysis to the read m
            DictionaryAnalyzer temp = new DictionaryAnalyzer(nounDictionary,
                    verbDictionary, adjectiveDictionary, adverbDictionary,
                    conjunctionDictionary, determinerDictionary,
                    interjectionDictionary, prepositionDictionary,
                    pronounDictionary, quantifierDictionary);
            // ToCSV
            toCSV();
            // ToHashMap
            toHashMap("noun");
            toHashMap("adj");
            toHashMap("adv");
            toHashMap("conj");
            toHashMap("det");
            toHashMap("interjection");
            toHashMap("pron");
            toHashMap("quantifier");
            toHashMap("prep");
            toHashMap("verb");
            /*store animacy querys for future runs*/
            fout=new FileOutputStream("outputs/animacyquery.ser");  
            out=new ObjectOutputStream(fout);  
            out.writeObject(animacy);  
            out.flush();  
            out.close();  
            fout.close();  
            
            printer.println("## Current Dictionary Write Time: " +seconds/60 + " minutes and "+seconds%60+" seconds");
            printer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Serializes all dictionarys to hashmap files
     * @param string name of the dictionary to be serialized
     */
    private static void toHashMap(String string) {
        try {
            FileOutputStream fout = new FileOutputStream(
                    "outputs/" + string + "DictHashMap.ser");
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(getdictionary(string));
            out.flush();
            out.close();
            fout.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * Prints all dictionarys to .csv format
     */
    private static void toCSV() {
        try {
            // nounprinter setup
            File nouns = new File("outputs/nouns.csv");
            PrintWriter nounprinter = new PrintWriter(nouns);
            nounprinter.println(
                    "Word,Plurality,Gender,AnAbbreviationFor,AbbreviatedFrom,AnAcronymFor,irregularPluralForm,IsCompound,IsCountable,AcceptsZeroArticle,IsProperName,Compliments,BaseForm,Animacy");
            // adjectiveprinter setup
            File adjectives = new File("outputs/adjectives.csv");
            PrintWriter adjectiveprinter = new PrintWriter(adjectives);
            adjectiveprinter.println(
                    "Word,AdjectiveOrderID,ComparisonType,Quantifier,IsQualitative,IsClassifying,CommonlyPrecededWithAnd,WorksInAttributivePosition,WorksInPredicativePosition,HasDiminutiveSuffix,IsProper,Compliments,MustUseMoreMost,AdjectiveIntensifierID");
            // verbprinter setup
            File verbs = new File("outputs/verbs.csv");
            PrintWriter verbprinter = new PrintWriter(verbs);
            verbprinter.println(
                    "Word,VerbType,Transivity,Tense,Aspect,Person,Phrasal,IsInfinitive");
            // adverbprinter setup
            File adverbs = new File("outputs/adverbs.csv");
            PrintWriter adverbprinter = new PrintWriter(adverbs);
            adverbprinter.println(
                    "Word,AdvIntensifierID,IsRelativeAdverb,IsComperativeAdverb,IsSuperlativeAdverb,AdvIntensifier,NoCompOrSupForm,MustUseMoreMost,IrregularForm");
            // conjunctionprinter setup
            File conjunctions = new File("outputs/conjunctions.csv");
            PrintWriter conjunctionprinter = new PrintWriter(conjunctions);
            conjunctionprinter.println("Word,ConjunctionType");
            // determinerprinter setup
            File determiners = new File("outputs/determiners.csv");
            PrintWriter determinerprinter = new PrintWriter(determiners);
            determinerprinter.println("Word,DeterminerTypeID");
            // Interjectionprinter setup
            File interjections = new File("outputs/interjections.csv");
            PrintWriter interjectionprinter = new PrintWriter(interjections);
            interjectionprinter.println("Word,InterjectionTypeID");
            // prepositionprinter setup
            File prepositions = new File("outputs/prepositions.csv");
            PrintWriter prepositionprinter = new PrintWriter(prepositions);
            prepositionprinter.println("Word,HasAdverbForm");
            // pronounprinter setup
            File pronouns = new File("outputs/pronouns.csv");
            PrintWriter pronounprinter = new PrintWriter(pronouns);
            pronounprinter.println("Word,Plurality,Gender,PronounCase,Type");
            // quantifierprinter setup
            File quantifiers = new File("outputs/quantifiers.csv");
            PrintWriter quantifierprinter = new PrintWriter(quantifiers);
            quantifierprinter.println("Word,QuantifierID");
            
            Iterator nounit = nounDictionary.entrySet().iterator();
            while (nounit.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) nounit.next();
                String w = (String) pair.getKey();
                Noun n = (Noun) pair.getValue();
                if(w.endsWith(",")){
                    w=w.substring(0,w.length()-1);
                }
                nounprinter.println(w + "," + n.plurality + "," + n.gender
                        + "," + n.anAbbreviationFor + "," + n.abbreviatedFrom
                        + "," + n.anAcronymFor + "," + n.irregularPluralForm
                        + "," + n.isCompound + "," + n.isCountable + ","
                        + n.acceptsZeroArticle + "," + n.isProperName + ","
                        + n.compliments + "," + n.baseForm+","+n.animacy);
            }
            Iterator adjit = adjectiveDictionary.entrySet().iterator();
            while (adjit.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) adjit.next();
                String w = (String) pair.getKey();
                Adjective a = (Adjective) pair.getValue();
                if(w.endsWith(",")){
                    w=w.substring(0,w.length()-1);
                }
                adjectiveprinter.println(w + "," + a.adjectiveOrderID + ","
                        + a.comparisonType + "," + a.quantifier + ","
                        + a.isQualitative + "," + a.isClassifying + ","
                        + a.commonlyPrecededWithAnd + ","
                        + a.worksInAttributivePosition + ","
                        + a.worksInPredicativePosition + ","
                        + a.hasDiminutiveSuffix + "," + a.isProper + ","
                        + a.compliments + "," + a.mustUseMoreMost+ ","+a.adjectiveIntensifierID);
            }
            Iterator verbit = verbDictionary.entrySet().iterator();
            while (verbit.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) verbit.next();
                String w = (String) pair.getKey();
                Verb v = (Verb) pair.getValue();
                if(w.endsWith(",")){
                    w=w.substring(0,w.length()-1);
                }
                verbprinter.println(w + "," + v.verbType + "," + v.transivity
                        + "," + v.tense + "," + v.aspect + "," + v.person + ","
                        + v.phrasal + "," + v.isInfinitive);
            }
            Iterator advit = adverbDictionary.entrySet().iterator();
            while (advit.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) advit.next();
                String w = (String) pair.getKey();
                Adverb a = (Adverb) pair.getValue();
                if(w.endsWith(",")){
                    w=w.substring(0,w.length()-1);
                }
                adverbprinter.println(w + "," + a.advIntensiferID + ","
                        + a.isRelativeAdverb + "," + a.isComparativeAdverb
                        + "," + a.isSuperlativeAdverb + "," + a.advIntensifier
                        + "," + a.noCompOrSuperForm + "," + a.mustUseMoreMost
                        + "," + a.irregularForm);
            }
            Iterator conjit = conjunctionDictionary.entrySet().iterator();
            while (conjit.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) conjit.next();
                String w = (String) pair.getKey();
                Conjunction c = (Conjunction) pair.getValue();
                if(w.endsWith(",")){
                    w=w.substring(0,w.length()-1);
                }
                conjunctionprinter.println(w + "," + c.conjunctionType);
            }
            Iterator detit = determinerDictionary.entrySet().iterator();
            while (detit.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) detit.next();
                String w = (String) pair.getKey();
                Determiner d = (Determiner) pair.getValue();
                if(w.endsWith(",")){
                    w=w.substring(0,w.length()-1);
                }
                determinerprinter.println(w + "," + d.determinerTypeID);
            }
            Iterator intit = interjectionDictionary.entrySet().iterator();
            while (intit.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) intit.next();
                String w = (String) pair.getKey();
                Interjection a = (Interjection) pair.getValue();
                if(w.endsWith(",")){
                    w=w.substring(0,w.length()-1);
                }
                interjectionprinter.println(w + "," + a.interjectionTypeID);
            }
            Iterator prepit = prepositionDictionary.entrySet().iterator();
            while (prepit.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) prepit.next();
                String w = (String) pair.getKey();
                Preposition p = (Preposition) pair.getValue();
                if(w.endsWith(",")){
                    w=w.substring(0,w.length()-1);
                }
                prepositionprinter.println(w + "," + p.hasAdverbForm);
            }
            Iterator proit = pronounDictionary.entrySet().iterator();
            while (proit.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) proit.next();
                String w = (String) pair.getKey();
                Pronoun p = (Pronoun) pair.getValue();
                if(w.endsWith(",")){
                    w=w.substring(0,w.length()-1);
                }
                pronounprinter.println(w + "," + p.plurality + "," + p.gender
                        + "," + p.pronounCase + "," + p.type);
            }
            Iterator quantit = quantifierDictionary.entrySet().iterator();
            while (quantit.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) quantit.next();
                String w = (String) pair.getKey();
                Quantifier q = (Quantifier) pair.getValue();
                if(w.endsWith(",")){
                    w=w.substring(0,w.length()-1);
                }
                quantifierprinter.println(w + "," + q.quantifierID);
            }

            nounprinter.close();
            adjectiveprinter.close();
            verbprinter.close();
            adverbprinter.close();
            conjunctionprinter.close();
            determinerprinter.close();
            interjectionprinter.close();
            prepositionprinter.close();
            pronounprinter.close();
            quantifierprinter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * Implements all subordinate conjunctions
     */
    private static void subordinateConjunctions() {
        File f = new File("inputs/subordinateConjunctions.txt");
        try {
            Scanner scan = new Scanner(f);
            while (scan.hasNextLine()) {
                String word = scan.nextLine();
                if (!word.equals("") && !word.equals("\n")) {
                    Conjunction pos = new Conjunction(word + "@");
                    System.out.println(word);
                    merge(word, pos);
                }
            }
            scan.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * implements gender specific nouns
     */
    private static void nounGender() {
        File f = new File("inputs/NounGenderList.txt");
        try {
            boolean masculine = true;
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String d = s.nextLine();
                if (d.equals("Masculine")) {
                    masculine = true;
                }
                else if (d.equals("Feminine")) {
                    masculine = false;
                }
                else {
                    String[] words = d.split(",");
                    for (int i = 0; i < words.length; i++) {
                        String word = words[i];
                        System.out.println(word);
                        if (masculine) {
                            Noun pos = new Noun(words[i] + "#~~");
                            animacy = pos.checkAnimacy(word, animacy);
                            merge(word, pos);
                        }
                        else if (!masculine) {
                            Noun pos = new Noun(words[i] + "@~~");
                            animacy = pos.checkAnimacy(word, animacy);
                            merge(word, pos);
                        }

                    }
                }
            }
            s.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method merges the word parameter with the existing dictionary.
     * 
     * @param w
     *            is the Word to be merged into the dictionary
     * @return if the Word was in the dictionary already or not
     */
    private static void merge(String w, PartOfSpeech part) {
        // Check the part of speech
try{
        if (part instanceof Noun) {
            if (nounDictionary.containsKey(w)) {
                Noun p = (Noun) nounDictionary.get(w);
                if (p.plurality.equals("--")) {
                    p.plurality = ((Noun) part).plurality;
                }
                if (p.gender.equals("--")) {
                    p.gender = ((Noun) part).gender;
                }
                if (p.anAbbreviationFor.equals("--")) {
                    p.anAbbreviationFor = ((Noun) part).anAbbreviationFor;
                }
                if (p.abbreviatedFrom.equals("--")) {
                    p.abbreviatedFrom = ((Noun) part).abbreviatedFrom;
                }
                if (p.anAcronymFor.equals("--")) {
                    p.anAcronymFor = ((Noun) part).anAcronymFor;
                }
                if (p.isCompound == null) {
                    p.isCompound = ((Noun) part).isCompound;
                }
                if (p.isCountable == null) {
                    p.isCountable = ((Noun) part).isCountable;
                }
                if (p.acceptsZeroArticle == null) {
                    p.acceptsZeroArticle = ((Noun) part).acceptsZeroArticle;
                }
                if (p.isProperName == null) {
                    p.isProperName = ((Noun) part).isProperName;
                }
                if (p.compliments.equals("--")) {
                    p.compliments = ((Noun) part).compliments;
                }
                if (p.irregularPluralForm.equals("--")) {
                    p.irregularPluralForm = ((Noun) part).irregularPluralForm;
                }
                if(p.animacy == null){
                    p.animacy = ((Noun) part).animacy;
                }
            }
            else {
                nounDictionary.put(w, (Noun) part);
            }

        }
        else if (part instanceof Adjective) {

            if (adjectiveDictionary.containsKey(w)) {
                Adjective p = (Adjective) adjectiveDictionary.get(w);
                if (p.adjectiveOrderID == -1) {
                    p.adjectiveOrderID = ((Adjective) part).adjectiveOrderID;
                }
                if (p.comparisonType.equals("--")) {
                    p.comparisonType = ((Adjective) part).comparisonType;
                }
                if (p.quantifier.equals("--")) {
                    p.quantifier = ((Adjective) part).quantifier;
                }
                if (p.isQualitative == null) {
                    p.isQualitative = ((Adjective) part).isQualitative;
                }
                if (p.isClassifying == null) {
                    p.isClassifying = ((Adjective) part).isClassifying;
                }
                if (p.commonlyPrecededWithAnd == null) {
                    p.commonlyPrecededWithAnd = ((Adjective) part).commonlyPrecededWithAnd;
                }
                if (p.worksInAttributivePosition == null) {
                    p.worksInAttributivePosition = ((Adjective) part).worksInAttributivePosition;
                }
                if (p.worksInPredicativePosition == null) {
                    p.worksInPredicativePosition = ((Adjective) part).worksInPredicativePosition;
                }
                if (p.hasDiminutiveSuffix == null) {
                    p.hasDiminutiveSuffix = ((Adjective) part).hasDiminutiveSuffix;
                }
                if (p.isProper == null) {
                    p.isProper = ((Adjective) part).isProper;
                }
                if (p.compliments.equals("--")) {
                    p.compliments = ((Adjective) part).compliments;
                }
                if (p.mustUseMoreMost == null) {
                    p.mustUseMoreMost = ((Adjective) part).mustUseMoreMost;
                }
                if(p.adjectiveIntensifierID == -1){
                    p.adjectiveIntensifierID = ((Adjective) part).adjectiveIntensifierID;
                }
            }
            else {
                adjectiveDictionary.put(w, (Adjective) part);
            }
        }
        else if (part instanceof Adverb) {
            if (adverbDictionary.containsKey(w)) {
                Adverb p = (Adverb) adverbDictionary.get(w);
                if (p.advIntensiferID == -1) {
                    p.advIntensiferID = ((Adverb) part).advIntensiferID;
                }
                if (p.advIntensifier.equals("--")) {
                    p.advIntensifier = ((Adverb) part).advIntensifier;
                }
                if (p.isComparativeAdverb == null) {
                    p.isComparativeAdverb = ((Adverb) part).isComparativeAdverb;
                }
                if (p.isSuperlativeAdverb == null) {
                    p.isSuperlativeAdverb = ((Adverb) part).isSuperlativeAdverb;
                }
                if(p.noCompOrSuperForm == null){
                    p.noCompOrSuperForm = ((Adverb)part).noCompOrSuperForm;
                }
                if(p.mustUseMoreMost == null){
                    p.mustUseMoreMost = ((Adverb)part).mustUseMoreMost;
                }
                if(p.irregularForm.equals("--")){
                    p.irregularForm = ((Adverb)part).irregularForm;
                }
            }
            else {
                adverbDictionary.put(w, (Adverb) part);
            }
        }
        else if (part instanceof Conjunction) {
            if (conjunctionDictionary.containsKey(w)) {
                Conjunction p = (Conjunction) conjunctionDictionary.get(w);
                if (p.conjunctionType.equals("--")) {
                    p.conjunctionType = ((Conjunction) part).conjunctionType;
                }
            }
            else {
                conjunctionDictionary.put(w, (Conjunction) part);
            }
        }
        else if (part instanceof Determiner) {
            if (determinerDictionary.containsKey(w)) {
                Determiner p = (Determiner) determinerDictionary.get(w);
                if (p.determinerTypeID == -1) {
                    p.determinerTypeID = ((Determiner) part).determinerTypeID;
                }
            }
            else {
                determinerDictionary.put(w, (Determiner) part);
            }
        }
        else if (part instanceof Interjection) {
            if (interjectionDictionary.containsKey(w)) {
                Interjection p = (Interjection) interjectionDictionary.get(w);
                if (p.interjectionTypeID == -1) {
                    p.interjectionTypeID = ((Interjection) part).interjectionTypeID;
                }
            }
            else {
                interjectionDictionary.put(w, (Interjection) part);
            }
        }
        else if (part instanceof Preposition) {
            if (prepositionDictionary.containsKey(w)) {
                Preposition p = (Preposition) prepositionDictionary.get(w);
                if (p.hasAdverbForm == null) {
                    p.hasAdverbForm = ((Preposition) part).hasAdverbForm;
                }
            }
            else {
                prepositionDictionary.put(w, (Preposition) part);
            }
        }
        else if (part instanceof Pronoun) {
            if (pronounDictionary.containsKey(w)) {
                Pronoun p = (Pronoun) pronounDictionary.get(w);
                if (p.plurality.equals("--")) {
                    p.plurality = ((Pronoun) part).plurality;
                }
                if (p.gender.equals("--")) {
                    p.gender = ((Pronoun) part).gender;
                }
                if (p.pronounCase.equals("--")) {
                    p.pronounCase = ((Pronoun) part).pronounCase;
                }
                if (p.type.equals("--")) {
                    p.type = ((Pronoun) part).type;
                }
            }
            else {
                pronounDictionary.put(w, (Pronoun) part);
            }
        }
        else if (part instanceof Verb) {
            if (verbDictionary.containsKey(w)) {
                Verb p = (Verb) verbDictionary.get(w);
                if (p.verbType.equals("--")) {
                    p.verbType = ((Verb) part).verbType;
                }
                if (p.transivity.equals("--")) {
                    p.transivity = ((Verb) part).transivity;
                }
                if (p.tense.equals("--")) {
                    p.tense = ((Verb) part).tense;
                }
                if (p.aspect.equals("--")) {
                    p.aspect = ((Verb) part).aspect;
                }
                if (p.person.equals("--")) {
                    p.person = ((Verb) part).person;
                }
                if (p.phrasal.equals("--")) {
                    p.phrasal = ((Verb) part).phrasal;
                }
                if (p.isInfinitive != null) {
                    p.isInfinitive = ((Verb) part).isInfinitive;
                }
            }
            else {
                verbDictionary.put(w, (Verb) part);
            }
        }
        else if (part instanceof Quantifier) {
            if (quantifierDictionary.containsKey(w)) {
                Quantifier q = (Quantifier) quantifierDictionary.get(w);
                if (q.quantifierID == -1) {
                    q.quantifierID = ((Quantifier) part).quantifierID;
                }
            }
            else {
                quantifierDictionary.put(w, (Quantifier) part);
            }
        }}
catch(Exception e){
    e.printStackTrace();
}
    }

    /**
     * Implements the MobyList into the dictionary.
     */
    private static void mobyListPOS() {
        try {
            File f = new File("inputs/MobyWordListWithPOS.txt");
            Scanner scan = new Scanner(f);
            int x = 0;
            while (scan.hasNext()) {
                String scanIn = scan.nextLine();
                if (!scanIn.equals("\n") && !scanIn.equals("")) {
                    //seperates the part of speech from the word
                    String[] input = scanIn.split("\\$");
                    if (input.length > 0 && input.length < 3) {
                        String word = input[0];
                        String[] poses = input[1].split("");
                        int i = 0;
                        //goes through each part of speech
                        while (i < poses.length) {
                            String pos = poses[i];
                            System.out.println(word);
                            /* Check what part of speech it is */
                            if (pos.equals("N")) {
                                Noun n = new Noun(word);
                                animacy = n.checkAnimacy(word, animacy);
                                merge(word, n);
                            }
                            else if (pos.equals("p")) {
                                Noun n = new Noun(word + "~~@");
                                animacy = n.checkAnimacy(word, animacy);
                                merge(word, n);
                            }
                            else if (pos.equals("h")) {
                                Noun n = new Noun(word + "~@~");
                                animacy = n.checkAnimacy(word, animacy);
                                merge(word, n);
                            }
                            else if (pos.equals("V")) {
                                merge(word, new Verb(word));
                            }
                            else if (pos.equals("t") && i < poses.length - 1
                                    && poses[i + 1].equals("i")) {
                                merge(word, new Verb(word + "%"));
                                i++;
                            }
                            else if (pos.equals("t")) {
                                merge(word, new Verb(word + "@"));
                            }
                            else if (pos.equals("i")) {
                                merge(word, new Verb(word + "="));
                            }
                            else if (pos.equals("A")) {
                                merge(word, new Adjective(word));
                            }
                            else if (pos.equals("v")) {
                                merge(word, new Adverb(word));
                            }
                            else if (pos.equals("C")) {
                                if (!word.equals("")
                                        || !word.equals("Ophiuchus")
                                                && !word.equals("opuscule")
                                                && !word.equals("vel")
                                                && !word.equals("quoties"))
                                    merge(word, new Conjunction(word));
                            }
                            else if (pos.equals("P")) {
                                merge(word, new Preposition(word));
                            }
                            else if (pos.equals("!")) {
                                merge(word, new Interjection(word));
                            }
                            else if (pos.equals("r")) {
                                if (!word.equals("")
                                        && !word.equals("noblewoman")
                                        && !word.equals("succussion"))
                                    merge(word, new Pronoun(word));
                            }
                            else if (pos.equals("D")) {
                                merge(word, determinerSorter(word));
                            }
                            else if (pos.equals("I")) {
                                merge(word, determinerSorter(word));
                            }
                            i++;
                        }
                        if(x==5000){
                        /*store animacy querys for future runs*/
                        fout=new FileOutputStream("outputs/animacyquery.ser");  
                        out=new ObjectOutputStream(fout);  
                        out.writeObject(animacy);  
                        out.flush();  
                        out.close();  
                        fout.close();
                        x=0;
                        }
                        x++;
                    }
                }
            }
            
            scan.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void defLexicon() {
        File f = new File("inputs/default-lexicon.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                .newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(f);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("word");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                String word = "";
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    /* Get the word */
                    word = eElement.getElementsByTagName("base").item(0)
                            .getTextContent();
                    /* Get the part of speech */
                    String pos = eElement.getElementsByTagName("category")
                            .item(0).getTextContent();
                    System.out.println(pos);
                    if (pos.equals("determiner")) {
                        merge(word, determinerSorter(word));
                    }
                    else if (pos.equals("verb")) {
                        if ((eElement.getElementsByTagName("ditransitive")
                                .getLength() > 0
                                || eElement.getElementsByTagName("transitive")
                                        .getLength() > 0)
                                && eElement
                                        .getElementsByTagName("intransitive")
                                        .getLength() > 0) {
                            merge(word, new Verb(word + "%"));
                        }
                        else if (eElement.getElementsByTagName("ditransitive")
                                .getLength() > 0) {
                            merge(word, new Verb(word + "~"));
                        }
                        else if (eElement.getElementsByTagName("transitive")
                                .getLength() > 0) {
                            merge(word, new Verb(word + "@"));
                        }
                        else if (eElement.getElementsByTagName("intransitive")
                                .getLength() > 0) {
                            merge(word, new Verb(word + "="));
                        }
                        else {
                            merge(word, new Verb(word));
                        }
                    }
                    else if (pos.equals("noun")) {
                        if (eElement.getElementsByTagName("nonCount")
                                .getLength() > 0){
                            Noun n = new Noun(word + "#~");
                            animacy = n.checkAnimacy(word, animacy);
                            merge(word, n);
                        }
                        else{
                            Noun n = new Noun(word);
                            animacy = n.checkAnimacy(word, animacy);
                            merge(word, n);
                        }
                    }
                    else if (pos.equals("adjective")) {
                        String temp = word;
                        if (eElement.getElementsByTagName("classifying")
                                .getLength() > 0) {
                            temp += "@";
                        }
                        else {
                            temp += "#";
                        }
                        if (eElement.getElementsByTagName("predicative")
                                .getLength() > 0) {
                            temp += "@";
                        }
                        else {
                            temp += "#";
                        }
                        if (eElement.getElementsByTagName("qualitative")
                                .getLength() > 0) {
                            temp += "@";
                        }
                        else {
                            temp += "#";
                        }
                        merge(word, new Adjective(temp));
                    }
                    else if (pos.equals("adverb")) {
                        merge(word, new Adverb(word));
                    }
                    else if (pos.equals("conjunction")) {
                        merge(word, new Conjunction(word));
                    }
                    else if (pos.equals("interjection")) {
                        merge(word, new Interjection(word));
                    }
                    else if (pos.equals("preposition")) {
                        merge(word, new Preposition(word));
                    }
                    else if (pos.equals("pronoun")) {
                        merge(word, new Pronoun(word));
                    }
                }
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void countNounOrNot() {

        try {
            File f2 = new File("inputs/NounList_CountNounsOnly.txt");
            Scanner scan2 = new Scanner(f2);

            while (scan2.hasNext()) {
                String word = scan2.nextLine();
                System.out.println(word);
                if (!word.equals("") && !word.equals("\n")) {
                    Noun n = new Noun(word + "@~");
                    animacy = n.checkAnimacy(word, animacy);
                    merge(word, n);
                }
            }
            scan2.close();
            File f = new File("inputs/NounList_MassNounsOnly.txt");
            Scanner scan = new Scanner(f);
            while (scan.hasNext()) {
                String word = scan.nextLine();
                System.out.println(word);
                if (!word.equals("") && !word.equals("\n")) {
                    Noun n = new Noun(word + "#~");
                    animacy = n.checkAnimacy(word,animacy);
                    merge(word, n);
                }
            }
            scan.close();

        }
        catch (Exception e) {
            System.out.println("filenotfound");
        }
    }

    private static void speclexicon() {
        File f = new File("inputs/LEXICON.txt");
        try {
            Scanner scan = new Scanner(f);
            String word = "";
            String wordvariant = "";
            ArrayList<PartOfSpeech> parts = new ArrayList<PartOfSpeech>();
            String pos = "";
            boolean count = false;
            boolean predposition = false;
            boolean attribposition = false;
            boolean qualitative = false;
            boolean classifying = false;
            String plurality = "";
            int wordsseen = 0;
            String irreg = "";
            int adjorderid = -1;
            boolean nocomporsup = false;
            boolean mustUseMoreMost = false;
            ArrayList<String> compliments = new ArrayList<String>();
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                // set word
                if (s.charAt(0) == '{' && s.length()>6) {
                    word = s.substring(6, s.length());
                }
                String[] split = s.split("=");
                // get part of speech
                if (split[0].endsWith("cat") && !split[1].equals("aux")
                        && !split[1].equals("modal")
                        && !split[1].equals("compl")) {
                    pos = split[1];

                    System.out.print(wordsseen);
                    System.out.println(pos);
                    System.out.println(word + " contains:"
                            + getdictionary(pos).containsKey(word));
                }
                // get the spelling variant of the word
                else if(split[0].endsWith("spelling_variant")){
                    wordvariant = split[1];
                }
                else if(pos.length()>0 && !getdictionary(pos).containsKey(word) && getdictionary(pos).containsKey(wordvariant)){
                    word = wordvariant;
                }
                // if dictionary contains the word
                else if (pos.length() > 0
                        && getdictionary(pos).containsKey(word)
                        && !s.equals("}")) {

                    // check for variants tag
                    if (split[0].endsWith("variants")) {
                        if (split[1].equals("uncount")) {
                            count = true;
                        }
                        else if (split[1].equals("plur")) {
                            plurality = "Plural";
                        }
                        else if (split[1].equals("sing")) {
                            plurality = "Singular";
                        }
                        else if (split[1].startsWith("inv")) {
                            nocomporsup = true;
                            if (split[1].endsWith("periph")) {
                                mustUseMoreMost = true;
                            }
                        }
                        else if (split[1].startsWith("irreg")) {
                            irreg = split[1].substring(6,
                                    split[1].length() - 1);
                        }
                    }
                    // check for position tag
                    else if (split[0].endsWith("position")) {
                        if (split[1].equals("pred")) {
                            predposition = true;
                        }
                        else if (split[1].length() > 6
                                && split[1].substring(0, 6).equals("attrib")) {
                            attribposition = true;
                            if (split[1].endsWith("(1)")) {
                                qualitative = true;
                            }
                            else if (split[1].endsWith("(3)")) {
                                classifying = true;
                            }
                            else if (split[1].endsWith("(2)")) {
                                adjorderid = 7;
                            }
                        }
                    }
                    else if (split[0].endsWith("compl")) {
                        compliments.add(split[1]);
                    }

                    else if (split.length > 1 && split[0].endsWith("ditran")) {
                        compliments.add("ditran=" + split[1]);
                    }
                    else if (split.length > 1
                            && split[0].endsWith("cplxtran")) {
                        compliments.add("cplxtran=" + split[1]);
                    }
                    else if (split.length > 1 && split[0].endsWith("tran")) {
                        compliments.add("tran=" + split[1]);
                    }
                }
                else if (s.equals("}")&&pos.length()>0
                        && getdictionary(pos).containsKey(word)) {
                    wordsseen++;
                    // add nouns
                    if (pos.equals("noun")) {
                        String temp = word;
                        if (count) {
                            temp += "@";
                        }
                        else {
                            temp += "#";
                        }
                        if (plurality.equals("Singular")) {
                            temp += "#";
                        }
                        else if (plurality.equals("Plural")) {
                            temp += "@";
                        }
                        else {
                            temp += "~";
                        }
                        Noun n = new Noun(temp);
                        if (compliments.size() > 0) {
                            n.addCompliments(compliments);
                        }
                        if (irreg.length() > 0) {
                            // splits irregular sentence
                            String[] irregs = irreg.split("|");
                            Noun extend = new Noun(irregs[1]);
                            extend.baseForm = word;
                            merge(irregs[1], extend);
                            n.addIrregularPlur(irregs[1]);
                        }
                        parts.add(n);

                    }
                    if (pos.equals("verb")){
                        Verb v = new Verb(word);
                        v.addCompliments(compliments);
                        parts.add(v);
                    }
                    if (pos.equals("adj")) {
                        String temp = word;
                        // attrib position
                        if (attribposition) {
                            temp += "@";
                        }
                        else {
                            temp += "#";
                        }
                        // is classifying
                        if (classifying) {
                            temp += "@";
                        }
                        else {
                            temp += "#";
                        }
                        // predic pos
                        if (predposition) {
                            temp += "@";
                        }
                        else {
                            temp += "#";
                        }
                        // is qualitative
                        if (qualitative) {
                            temp += "@";
                        }
                        else {
                            temp += "#";
                        }
                        Adjective a = new Adjective(temp);
                        if (compliments.size() > 0) {
                            a.addCompliments(compliments);
                        }
                        if (adjorderid > -1) {
                            a.addAdjectiveOrderID(adjorderid);
                        }

                        a.mustUseMoreMost = mustUseMoreMost;
                        parts.add(a);
                    }
                    if (pos.equals("pron"))
                        parts.add(new Pronoun(word));
                    if (pos.equals("adv")) {
                        Adverb a = new Adverb(word);
                        a.noCompOrSuperForm = nocomporsup;
                        a.mustUseMoreMost = mustUseMoreMost;
                        if (irreg.length() > 0) {
                            String[] irregs = irreg.split("|");
                            a.irregularForm = irregs[1] + "|" + irregs[2];
                            Adverb compabs = new Adverb(irregs[1]);
                            compabs.isComparativeAdverb = true;
                            compabs.isSuperlativeAdverb = false;
                            merge(irregs[1], compabs);
                            Adverb supabs = new Adverb(irregs[2]);
                            supabs.isComparativeAdverb = false;
                            supabs.isSuperlativeAdverb = true;
                            merge(irregs[2], supabs);
                        }
                        parts.add(a);
                    }
                    if (pos.equals("prep"))
                        parts.add(new Preposition(word));
                    if (pos.equals("conj"))
                        parts.add(new Conjunction(word));
                    if(pos.equals("det"))
                        parts.add(determinerSorter(word));

                    merge(word, parts.get(0));
                    // reset
                    wordvariant = "";
                    word = "";
                    parts = new ArrayList<PartOfSpeech>();
                    pos = "";
                    count = false;
                    predposition = false;
                    attribposition = false;
                    qualitative = false;
                    classifying = false;
                    plurality = "";
                    irreg = "";
                    adjorderid = -1;
                    nocomporsup = false;
                    mustUseMoreMost = false;
                    compliments = new ArrayList<String>();
                }
                else {
                }
            }
            scan.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void adverbIntensifiers(){
        try {
            Scanner scan = new Scanner(new File("inputs/AdverbScales-Manual.csv"));
            scan.nextLine();
            while(scan.hasNextLine()){
                String s = scan.nextLine();
                String [] split = s.split(",");
                if(split.length>1){
                String word = split[0];
                Adverb adv = new Adverb(word);
                adv.advIntensiferID = (Integer.parseInt(split[2]))+2;
                adv.advIntensifier = split[1];
                merge(word,adv);
                if(word.endsWith("ly")){
                    String wordadj = word.substring(0, word.length()-2);
                    if(adjectiveDictionary.containsKey(wordadj)){
                    Adjective adj = new Adjective(wordadj);
                    adj.adjectiveIntensifierID =(Integer.parseInt(split[2]))+2;
                    merge(wordadj,adj);
                    }
                }
                }
            }
            scan.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static PartOfSpeech determinerSorter(String s) {
        if (s.equals("a") || s.equals("an") || s.equals("the")
                || s.equals("this") || s.equals("these") || s.equals("that")
                || s.equals("those") || s.equals("what")
                || s.equals("whatever") || s.equals("which")
                || s.equals("whichever")) {
            return new Determiner(s);
        }
        else if (s.equals("many") || s.equals("some") || s.equals("divers")
                || s.equals("sundry") || s.equals("much")
                || s.endsWith("several") || s.equals("none") || s.equals("no")
                || s.endsWith("one") || s.endsWith("two")
                || s.endsWith("three") || s.endsWith("four")
                || s.endsWith("five") || s.endsWith("six")
                || s.endsWith("seven") || s.endsWith("eight")
                || s.endsWith("nine") || s.endsWith("zero")
                || s.endsWith("ten") || s.endsWith("eleven")
                || s.endsWith("twelve") || s.endsWith("teen")
                || s.endsWith("billion") || s.endsWith("dozen")
                || (s.endsWith("ty")&&!s.endsWith("plenty")) || s.endsWith("trillion")
                || s.endsWith("thousand") || s.endsWith("quadrillion")
                || s.endsWith("zillion") || s.equals("ane")
                || s.equals("fourscore") || s.endsWith("hundred")
                || s.endsWith("million") || s.endsWith("threescore")) {
            return new Quantifier(s);
        }
        else {
            try {
                FileWriter fw = new FileWriter("outputs/infs.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter printer = new PrintWriter(bw);
                printer.println(s);
                printer.close();
                bw.close();
                fw.close();

            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return new Adjective(s + "~~~");
        }
    }

    public static HashMap getdictionary(String pos) {
        if (pos.equals("noun")) {
            return nounDictionary;
        }
        else if (pos.equals("adj")) {
            return adjectiveDictionary;
        }
        else if (pos.equals("adv")) {
            return adverbDictionary;
        }
        else if (pos.equals("conj")) {
            return conjunctionDictionary;
        }
        else if (pos.equals("det")) {
            return determinerDictionary;
        }
        else if (pos.equals("interjection")) {
            return interjectionDictionary;
        }
        else if (pos.equals("prep")) {
            return prepositionDictionary;
        }
        else if (pos.equals("pron")) {
            return pronounDictionary;
        }
        else if (pos.equals("quantifier")) {
            return quantifierDictionary;
        }
        else if (pos.equals("verb")) {
            return verbDictionary;
        }
        else
            return null;
    }
    

}
