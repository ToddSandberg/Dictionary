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
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.mit.jverbnet.data.FrameType;
import edu.mit.jverbnet.data.IFrame;
import edu.mit.jverbnet.data.IMember;
import edu.mit.jverbnet.data.IVerbClass;
import edu.mit.jverbnet.data.semantics.IPredicateDesc;
import edu.mit.jverbnet.data.semantics.ISemanticDesc;
import edu.mit.jverbnet.data.syntax.ISyntaxArgDesc;
import edu.mit.jverbnet.data.syntax.ISyntaxDesc;
import edu.mit.jverbnet.index.IVerbIndex;
import edu.mit.jverbnet.index.VerbIndex;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.dictionary.Dictionary;

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
    public static HashMap<String, Noun> firstNameDictionary = new HashMap<String,Noun>();
    public static HashMap<String, Noun> lastNameDictionary = new HashMap<String,Noun>();
    public static HashMap<String, Noun> properPlaceDictionary = new HashMap<String,Noun>();
    /**
     * Saves progress in animacy from concept net
     */
    static AnimacySave animacy = new AnimacySave();
    public static HashMap<String,String> roots = new HashMap<String,String>();
    static FileOutputStream fout;
    static ObjectOutputStream out;
    // determines the runtime
    static int seconds = 0;
    static Timer time = new Timer();
    static TimerTask ttask = new TimerTask() {
        @Override
        public void run() {
            seconds++;
        }
    };
    // wordnet dictionary
    static Dictionary dictionary;

    public static void main(String[] args) {
        try {
            dictionary = Dictionary.getDefaultResourceInstance();
            try {
                ObjectInputStream in = new ObjectInputStream(
                        new FileInputStream("outputs/animacyquery.ser"));
                animacy = (AnimacySave) in.readObject();
                in.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Starting");
            // counts seconds until the program finishes
            time.scheduleAtFixedRate(ttask, 1000, 1000);
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
            /* store animacy querys for future runs */
            fout = new FileOutputStream("outputs/animacyquery.ser");
            out = new ObjectOutputStream(fout);
            out.writeObject(animacy);
            out.flush();
            out.close();
            fout.close();
            // Adds all specific gender nouns to the dictionary
            nounGender();
            printer.println("- nounGenderList.txt Implemented ");
            /* store animacy querys for future runs */
            fout = new FileOutputStream("outputs/animacyquery.ser");
            out = new ObjectOutputStream(fout);
            out.writeObject(animacy);
            out.flush();
            out.close();
            fout.close();
            // Implements First Names and Last Names and Proper Places
            properNames();
            printer.println("- First_Names.ser and Last_Names.ser and Proper_Places.ser Implemented");
            // Implements MobyWordListWithPOS.txt to the dictionary
            mobyListPOS();
            printer.println("- MobyWordListWithPOS.txt Implemented");
            // Implements default-lexion.xml
            defLexicon();
            printer.println("- default-lexicon.xml Implemented");
            // adds count nouns and non count nouns to the dictionary
            countNounOrNot();
            printer.println(
                    "- NounList_CountNounsOnly.txt and NounsList_MassNounsOnly.txt Implemented");
            // Implements Adverb Scales
            adverbIntensifiers();
            printer.println("- AdverbScales-Manual.csv Implemented");
            // Implements locations
            locations();
            printer.println("- locations.txt implemented");
            //implement verbnet
            verbnet();
            printer.println("- verbnet implemented");
            //implements ADJADV.txt from nombank
            adjadv();
            printer.println("- ADJADV.txt from nombank implemented");
            //Implements propBank
            propBank();
            printer.println("- propBank implemented");
            //implements shapes
            shapes();
            printer.println("- shapes.csv implemented");
            //implements framenet
            framenet();
            printer.println("- fn16lexunits.ttl implemented");
            // implements colors
            colors();
            printer.println("- colors.txt implemented");
            // Implements SPECIALIST LEXICON
            speclexicon();
            printer.println("- LEXICON.txt implemented");
            // implements which words are common
            frequency();
            printer.println("- Word_frequency_list.txt implemented");
            
            printer.println("## Document Output Formats:");
            printer.println(
                    "- tsv files for each part of speech with words and their properties");
            printer.println(
                    "- serialized dictionary HashMap's for each part of speech");
            printer.println("## Accessing the Dictionary");
            printer.println("- The WriteDictionary class takes all of the inputs and writes them to .tsv files and hashmaps.");
            printer.println("- The DictionaryAccess class provides access to many features of the dictionary. "
                    + "There are getter methods for each partOfSpeech dictionary ex. getNounDictionary. "
                    + "getWordInfo(word) allows for a term look up and returns all part of speech's for the word, while getWordInfo(word,pos) returns the info on a specific part of speech. "
                    + "getMultipleWordInfo(sentence) uses coreNLP to lookup words based on the part of speech in the sentence. "
                    + "DictionaryAccess also has a changePOS method which converts a word from one part of speech to another. ");
            printer.println("- The Accessor class is an example usage of the DictionaryAccess class.");
            printer.println("- This repository also contains access to Most Common Lists based on 2grams and 3grams, which are written in the access2gram class.");
            //print out roots HashMap
            try {
                FileOutputStream fout = new FileOutputStream(
                        "outputs/roots.ser");
                ObjectOutputStream out = new ObjectOutputStream(fout);
                out.writeObject(roots);
                out.flush();
                out.close();
                fout.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            // ToTSV
            toTSV();
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
            toHashMap("firstName");
            toHashMap("lastName");
            toHashMap("properPlace");
            /* store animacy querys for future runs */
            fout = new FileOutputStream("outputs/animacyquery.ser");
            out = new ObjectOutputStream(fout);
            out.writeObject(animacy);
            out.flush();
            out.close();
            fout.close();
            // prints dictionary analysis to the read m
            DictionaryAnalyzer temp = new DictionaryAnalyzer(nounDictionary,
                    verbDictionary, adjectiveDictionary, adverbDictionary,
                    conjunctionDictionary, determinerDictionary,
                    interjectionDictionary, prepositionDictionary,
                    pronounDictionary, quantifierDictionary, firstNameDictionary, lastNameDictionary,properPlaceDictionary);

            printer.println("## Current Dictionary Write Time: " + seconds / 60
                    + " minutes and " + seconds % 60 + " seconds");
            printer.close();
            ttask.cancel();
            time.cancel();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void properNames() {
        try{
            HashMap<String, String> firstnames = new HashMap<String,String>();
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream("inputs/First_Names.ser"));
            firstnames = (HashMap<String, String>) in.readObject();
            in.close();
            
            Iterator it = firstnames.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry pair = (Entry) it.next();
                String name = (String) pair.getKey();
                String actualname = name.substring(0,1)+name.substring(1,name.length()).toLowerCase();
                Noun n = new Noun(actualname);
                System.out.println(actualname);
                n.isProperName = true;
                String prop = (String) pair.getValue();
                if(prop.split("\\.")[1].equals("MALE")){
                    n.gender = "Masculine";
                }
                else if(prop.split("\\.")[1].equals("FEMALE")){
                    n.gender = "Feminine";
                }
                n.animacy = "Human";
                n.location = false;
                mergeFirstName(actualname,n);
            }
            
            HashMap<String, String> lastnames = new HashMap<String,String>();
            ObjectInputStream in2 = new ObjectInputStream(
                    new FileInputStream("inputs/Last_Names.ser"));
            lastnames = (HashMap<String, String>) in2.readObject();
            in2.close();
            
            Iterator it2 = lastnames.entrySet().iterator();
            while(it2.hasNext()){
                Map.Entry pair = (Entry) it2.next();
                String name = (String) pair.getKey();
                String actualname = name.substring(0,1)+name.substring(1,name.length()).toLowerCase();
                Noun n = new Noun(actualname);
                System.out.println(actualname);
                n.isProperName = true;
                n.animacy = "Human";
                n.location = false;
                mergeLastName(actualname,n);
            }
            
            HashMap<String, String> properplaces = new HashMap<String,String>();
            ObjectInputStream in3 = new ObjectInputStream(
                    new FileInputStream("inputs/Proper_Places.ser"));
            properplaces = (HashMap<String, String>) in3.readObject();
            in3.close();
            
            Iterator it3 = properplaces.entrySet().iterator();
            while(it3.hasNext()){
                Map.Entry pair = (Entry) it3.next();
                String name = (String) pair.getKey();
                String actualname = name.substring(0,1)+name.substring(1,name.length()).toLowerCase();
                Noun n = new Noun(actualname);
                System.out.println(actualname);
                n.isProperName = true;
                n.location = true;
                n.animacy = "Inanimate";
                n.gender = "Neuter";
                mergeProperPlace(actualname,n);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void mergeProperPlace(String w, Noun part) {
        if (properPlaceDictionary.containsKey(w)) {
            Noun p = (Noun) properPlaceDictionary.get(w);
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
            if (p.animacy == null) {
                p.animacy = ((Noun) part).animacy;
            }
            if (p.location == null) {
                p.location = ((Noun) part).location;
            }
            if (p.commonRank == -1) {
                p.commonRank = ((Noun) part).commonRank;
            }
            if (p.howCommon == -1) {
                p.howCommon = ((Noun) part).howCommon;
            }
            if(!((Noun) part).baseForm.equals("--")){
                p.baseForm = ((Noun) part).baseForm;
            }
            if(!((Noun) part).propbank.equals("--")){
                if(p.propbank.equals("--")){
                    p.propbank = ((Noun) part).propbank + "|";
                }else{
                    p.propbank += ((Noun) part).propbank + "|";
                }
            }
            if(!((Noun) part).frame.equals("--")){
                if(p.frame.equals("--")){
                    p.frame = ((Noun) part).frame + "|";
                }else{
                    p.frame += ((Noun) part).frame + "|";
                }
            }
            properPlaceDictionary.put(w,p);
        }
        else {
            properPlaceDictionary.put(w, (Noun) part);
        }
    }

    private static void mergeLastName(String w, Noun part) {
        if (lastNameDictionary.containsKey(w)) {
            Noun p = (Noun) lastNameDictionary.get(w);
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
            if (p.animacy == null) {
                p.animacy = ((Noun) part).animacy;
            }
            if (p.location == null) {
                p.location = ((Noun) part).location;
            }
            if (p.commonRank == -1) {
                p.commonRank = ((Noun) part).commonRank;
            }
            if (p.howCommon == -1) {
                p.howCommon = ((Noun) part).howCommon;
            }
            if(!((Noun) part).baseForm.equals("--")){
                p.baseForm = ((Noun) part).baseForm;
            }
            if(!((Noun) part).propbank.equals("--")){
                if(p.propbank.equals("--")){
                    p.propbank = ((Noun) part).propbank + "|";
                }else{
                    p.propbank += ((Noun) part).propbank + "|";
                }
            }
            if(!((Noun) part).frame.equals("--")){
                if(p.frame.equals("--")){
                    p.frame = ((Noun) part).frame + "|";
                }else{
                    p.frame += ((Noun) part).frame + "|";
                }
            }
            lastNameDictionary.put(w,p);
        }
        else {
            lastNameDictionary.put(w, (Noun) part);
        }
    }

    private static void mergeFirstName(String w, Noun part) {
        if (firstNameDictionary.containsKey(w)) {
            Noun p = (Noun) firstNameDictionary.get(w);
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
            if (p.animacy == null) {
                p.animacy = ((Noun) part).animacy;
            }
            if (p.location == null) {
                p.location = ((Noun) part).location;
            }
            if (p.commonRank == -1) {
                p.commonRank = ((Noun) part).commonRank;
            }
            if (p.howCommon == -1) {
                p.howCommon = ((Noun) part).howCommon;
            }
            if(!((Noun) part).baseForm.equals("--")){
                p.baseForm = ((Noun) part).baseForm;
            }
            if(!((Noun) part).propbank.equals("--")){
                if(p.propbank.equals("--")){
                    p.propbank = ((Noun) part).propbank + "|";
                }else{
                    p.propbank += ((Noun) part).propbank + "|";
                }
            }
            if(!((Noun) part).frame.equals("--")){
                if(p.frame.equals("--")){
                    p.frame = ((Noun) part).frame + "|";
                }else{
                    p.frame += ((Noun) part).frame + "|";
                }
            }
            firstNameDictionary.put(w,p);
        }
        else {
            firstNameDictionary.put(w, (Noun) part);
        }
    }

    /**
     * Serializes all dictionarys to hashmap files
     * 
     * @param string
     *            name of the dictionary to be serialized
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
     * Prints all dictionarys to .tsv format
     */
    private static void toTSV() {
        try {
            // nounprinter setup
            File nouns = new File("outputs/nouns.tsv");
            PrintWriter nounprinter = new PrintWriter(nouns);
            nounprinter.println(
                    "Word\tPlurality\tGender\tAnAbbreviationFor\tAbbreviatedFrom\tAnAcronymFor\tirregularPluralForm\tIsCompound\tIsCountable\tAcceptsZeroArticle\tIsProperName\tCompliments\tBaseForm\tAnimacy\tlocation\thowCommon\tcommonRank\tpropBank\tframe");
            // adjectiveprinter setup
            File adjectives = new File("outputs/adjectives.tsv");
            PrintWriter adjectiveprinter = new PrintWriter(adjectives);
            adjectiveprinter.println(
                    "Word\tAdjectiveOrderID\tComparisonType\tQuantifier\tIsQualitative\tIsClassifying\tCommonlyPrecededWithAnd\tWorksInAttributivePosition\tWorksInPredicativePosition\tHasDiminutiveSuffix\tIsProper\tCompliments\tMustUseMoreMost\tAdjectiveIntensifierID\thowCommon\tcommonRank\tbaseForm\tlight\tpropBank\tframe");
            // verbprinter setup
            File verbs = new File("outputs/verbs.tsv");
            PrintWriter verbprinter = new PrintWriter(verbs);
            verbprinter.println(
                    "Word\tVerbType\tTransivity\tTense\tAspect\tPerson\tPhrasal\tIsInfinitive\thowCommon\tcommonRank\tbaseForm\tverbNet\twordNetID\tpropBank\tframe");
            // adverbprinter setup
            File adverbs = new File("outputs/adverbs.tsv");
            PrintWriter adverbprinter = new PrintWriter(adverbs);
            adverbprinter.println(
                    "Word\tAdvIntensifierID\tIsRelativeAdverb\tIsComperativeAdverb\tIsSuperlativeAdverb\tAdvIntensifier\tNoCompOrSupForm\tMustUseMoreMost\tIrregularForm\thowCommon\tcommonRank\tbaseForm\twordSenseID");
            // conjunctionprinter setup
            File conjunctions = new File("outputs/conjunctions.tsv");
            PrintWriter conjunctionprinter = new PrintWriter(conjunctions);
            conjunctionprinter
                    .println("Word\tConjunctionType\thowCommon\tcommonRank");
            // determinerprinter setup
            File determiners = new File("outputs/determiners.tsv");
            PrintWriter determinerprinter = new PrintWriter(determiners);
            determinerprinter
                    .println("Word\tDeterminerTypeID\thowCommon\tcommonRank");
            // Interjectionprinter setup
            File interjections = new File("outputs/interjections.tsv");
            PrintWriter interjectionprinter = new PrintWriter(interjections);
            interjectionprinter.println(
                    "Word\tInterjectionTypeID\thowCommon\tcommonRank");
            // prepositionprinter setup
            File prepositions = new File("outputs/prepositions.tsv");
            PrintWriter prepositionprinter = new PrintWriter(prepositions);
            prepositionprinter
                    .println("Word\tHasAdverbForm\thowCommon\tcommonRank");
            // pronounprinter setup
            File pronouns = new File("outputs/pronouns.tsv");
            PrintWriter pronounprinter = new PrintWriter(pronouns);
            pronounprinter.println(
                    "Word\tPlurality\tGender\tPronounCase\tType\thowCommon\tcommonRank");
            // quantifierprinter setup
            File quantifiers = new File("outputs/quantifiers.tsv");
            PrintWriter quantifierprinter = new PrintWriter(quantifiers);
            quantifierprinter
                    .println("Word\tQuantifierID\thowCommon\tcommonRank");
            // firstnameprinter setup
            File firstnames = new File("outputs/firstNames.tsv");
            PrintWriter firstnameprinter = new PrintWriter(firstnames);
            firstnameprinter
                    .println("Word\tPlurality\tGender\tAnAbbreviationFor\tAbbreviatedFrom\tAnAcronymFor\tirregularPluralForm\tIsCompound\tIsCountable\tAcceptsZeroArticle\tIsProperName\tCompliments\tBaseForm\tAnimacy\tlocation\thowCommon\tcommonRank\tpropBank\tframe");
            // lastnameprinter setup
            File lastnames = new File("outputs/lastNames.tsv");
            PrintWriter lastnameprinter = new PrintWriter(lastnames);
            lastnameprinter
                    .println("Word\tPlurality\tGender\tAnAbbreviationFor\tAbbreviatedFrom\tAnAcronymFor\tirregularPluralForm\tIsCompound\tIsCountable\tAcceptsZeroArticle\tIsProperName\tCompliments\tBaseForm\tAnimacy\tlocation\thowCommon\tcommonRank\tpropBank\tframe");
            // properplaceprinter setup
            File properplaces = new File("outputs/properPlaces.tsv");
            PrintWriter properplaceprinter = new PrintWriter(properplaces);
            properplaceprinter
                    .println("Word\tPlurality\tGender\tAnAbbreviationFor\tAbbreviatedFrom\tAnAcronymFor\tirregularPluralForm\tIsCompound\tIsCountable\tAcceptsZeroArticle\tIsProperName\tCompliments\tBaseForm\tAnimacy\tlocation\thowCommon\tcommonRank\tpropBank\tframe");

            Iterator nounit = nounDictionary.entrySet().iterator();
            while (nounit.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) nounit.next();
                String w = (String) pair.getKey();
                Noun n = (Noun) pair.getValue();
                if (w.endsWith(",")) {
                    w = w.substring(0, w.length() - 1);
                }
                nounprinter.println(w + "\t" + n.plurality + "\t" + n.gender
                        + "\t" + n.anAbbreviationFor + "\t" + n.abbreviatedFrom
                        + "\t" + n.anAcronymFor + "\t" + n.irregularPluralForm
                        + "\t" + n.isCompound + "\t" + n.isCountable + "\t"
                        + n.acceptsZeroArticle + "\t" + n.isProperName + "\t"
                        + n.compliments + "\t" + n.baseForm + "\t" + n.animacy
                        + "\t" + n.location + "\t" + n.howCommon + "\t"
                        + n.commonRank + "\t" + n.frame);
            }
            Iterator firstnameit = firstNameDictionary.entrySet().iterator();
            while (firstnameit.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) firstnameit.next();
                String w = (String) pair.getKey();
                Noun n = (Noun) pair.getValue();
                if (w.endsWith(",")) {
                    w = w.substring(0, w.length() - 1);
                }
                firstnameprinter.println(w + "\t" + n.plurality + "\t" + n.gender
                        + "\t" + n.anAbbreviationFor + "\t" + n.abbreviatedFrom
                        + "\t" + n.anAcronymFor + "\t" + n.irregularPluralForm
                        + "\t" + n.isCompound + "\t" + n.isCountable + "\t"
                        + n.acceptsZeroArticle + "\t" + n.isProperName + "\t"
                        + n.compliments + "\t" + n.baseForm + "\t" + n.animacy
                        + "\t" + n.location + "\t" + n.howCommon + "\t"
                        + n.commonRank + "\t" + n.frame);
            }
            Iterator lastnameit = lastNameDictionary.entrySet().iterator();
            while (lastnameit.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) lastnameit.next();
                String w = (String) pair.getKey();
                Noun n = (Noun) pair.getValue();
                if (w.endsWith(",")) {
                    w = w.substring(0, w.length() - 1);
                }
                lastnameprinter.println(w + "\t" + n.plurality + "\t" + n.gender
                        + "\t" + n.anAbbreviationFor + "\t" + n.abbreviatedFrom
                        + "\t" + n.anAcronymFor + "\t" + n.irregularPluralForm
                        + "\t" + n.isCompound + "\t" + n.isCountable + "\t"
                        + n.acceptsZeroArticle + "\t" + n.isProperName + "\t"
                        + n.compliments + "\t" + n.baseForm + "\t" + n.animacy
                        + "\t" + n.location + "\t" + n.howCommon + "\t"
                        + n.commonRank + "\t" + n.frame);
            }
            Iterator properplaceit = properPlaceDictionary.entrySet().iterator();
            while (properplaceit.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) properplaceit.next();
                String w = (String) pair.getKey();
                Noun n = (Noun) pair.getValue();
                if (w.endsWith(",")) {
                    w = w.substring(0, w.length() - 1);
                }
                properplaceprinter.println(w + "\t" + n.plurality + "\t" + n.gender
                        + "\t" + n.anAbbreviationFor + "\t" + n.abbreviatedFrom
                        + "\t" + n.anAcronymFor + "\t" + n.irregularPluralForm
                        + "\t" + n.isCompound + "\t" + n.isCountable + "\t"
                        + n.acceptsZeroArticle + "\t" + n.isProperName + "\t"
                        + n.compliments + "\t" + n.baseForm + "\t" + n.animacy
                        + "\t" + n.location + "\t" + n.howCommon + "\t"
                        + n.commonRank + "\t" + n.frame);
            }
            Iterator adjit = adjectiveDictionary.entrySet().iterator();
            while (adjit.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) adjit.next();
                String w = (String) pair.getKey();
                Adjective a = (Adjective) pair.getValue();
                if (w.endsWith(",")) {
                    w = w.substring(0, w.length() - 1);
                }
                adjectiveprinter.println(w + "\t" + a.adjectiveOrderID + "\t"
                        + a.comparisonType + "\t" + a.quantifier + "\t"
                        + a.isQualitative + "\t" + a.isClassifying + "\t"
                        + a.commonlyPrecededWithAnd + "\t"
                        + a.worksInAttributivePosition + "\t"
                        + a.worksInPredicativePosition + "\t"
                        + a.hasDiminutiveSuffix + "\t" + a.isProper + "\t"
                        + a.compliments + "\t" + a.mustUseMoreMost + "\t"
                        + a.adjectiveIntensifierID + "\t" + a.howCommon + "\t"
                        + a.commonRank + "\t" + a.baseForm + "\t" + a.frame);
            }
            Iterator verbit = verbDictionary.entrySet().iterator();
            while (verbit.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) verbit.next();
                String w = (String) pair.getKey();
                Verb v = (Verb) pair.getValue();
                if (w.endsWith(",")) {
                    w = w.substring(0, w.length() - 1);
                }
                verbprinter.println(w + "\t" + v.verbType + "\t" + v.transivity
                        + "\t" + v.tense + "\t" + v.aspect + "\t" + v.person
                        + "\t" + v.phrasal + "\t" + v.isInfinitive + "\t"
                        + v.howCommon + "\t" + v.commonRank + "\t"
                        + v.baseForm + "\t" + v.light + "\t" + v.verbnet + "\t" + v.wordNetID +"\t"+v.propbank +"\t"+v.frame);
            }
            Iterator advit = adverbDictionary.entrySet().iterator();
            while (advit.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) advit.next();
                String w = (String) pair.getKey();
                Adverb a = (Adverb) pair.getValue();
                if (w.endsWith(",")) {
                    w = w.substring(0, w.length() - 1);
                }
                adverbprinter.println(w + "\t" + a.advIntensiferID + "\t"
                        + a.isRelativeAdverb + "\t" + a.isComparativeAdverb
                        + "\t" + a.isSuperlativeAdverb + "\t"
                        + a.advIntensifier + "\t" + a.noCompOrSuperForm + "\t"
                        + a.mustUseMoreMost + "\t" + a.irregularForm + "\t"
                        + a.howCommon + "\t" + a.commonRank + "\t"
                        + a.baseForm + "\t" + a.wordSenseID);
            }
            Iterator conjit = conjunctionDictionary.entrySet().iterator();
            while (conjit.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) conjit.next();
                String w = (String) pair.getKey();
                Conjunction c = (Conjunction) pair.getValue();
                if (w.endsWith(",")) {
                    w = w.substring(0, w.length() - 1);
                }
                conjunctionprinter.println(w + "\t" + c.conjunctionType + "\t"
                        + c.howCommon + "\t" + c.commonRank);
            }
            Iterator detit = determinerDictionary.entrySet().iterator();
            while (detit.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) detit.next();
                String w = (String) pair.getKey();
                Determiner d = (Determiner) pair.getValue();
                if (w.endsWith(",")) {
                    w = w.substring(0, w.length() - 1);
                }
                determinerprinter.println(w + "\t" + d.determinerTypeID + "\t"
                        + d.howCommon + "\t" + d.commonRank);
            }
            Iterator intit = interjectionDictionary.entrySet().iterator();
            while (intit.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) intit.next();
                String w = (String) pair.getKey();
                Interjection a = (Interjection) pair.getValue();
                if (w.endsWith(",")) {
                    w = w.substring(0, w.length() - 1);
                }
                interjectionprinter.println(w + "\t" + a.interjectionTypeID
                        + "\t" + a.howCommon + "\t" + a.commonRank);
            }
            Iterator prepit = prepositionDictionary.entrySet().iterator();
            while (prepit.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) prepit.next();
                String w = (String) pair.getKey();
                Preposition p = (Preposition) pair.getValue();
                if (w.endsWith(",")) {
                    w = w.substring(0, w.length() - 1);
                }
                prepositionprinter.println(w + "\t" + p.hasAdverbForm + "\t"
                        + p.howCommon + "\t" + p.commonRank);
            }
            Iterator proit = pronounDictionary.entrySet().iterator();
            while (proit.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) proit.next();
                String w = (String) pair.getKey();
                Pronoun p = (Pronoun) pair.getValue();
                if (w.endsWith(",")) {
                    w = w.substring(0, w.length() - 1);
                }
                pronounprinter.println(w + "\t" + p.plurality + "\t" + p.gender
                        + " " + p.pronounCase + "\t" + p.type + "\t"
                        + p.howCommon + "\t" + p.commonRank);
            }
            Iterator quantit = quantifierDictionary.entrySet().iterator();
            while (quantit.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) quantit.next();
                String w = (String) pair.getKey();
                Quantifier q = (Quantifier) pair.getValue();
                if (w.endsWith(",")) {
                    w = w.substring(0, w.length() - 1);
                }
                quantifierprinter.println(w + "\t" + q.quantifierID + "\t"
                        + q.howCommon + "\t" + q.commonRank);
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
                            Noun n = new Noun(words[i] + "#~~");
                            try {
                                MorphologyFinder m = new MorphologyFinder(
                                        word);
                                m.loadDictionary(nounDictionary);
                                m.breakApart();
                                try {
                                    n.baseForm = dictionary.lookupIndexWord(
                                            POS.NOUN, m.getRoot()).getLemma();
                                }
                                catch (Exception e) {
                                    n.baseForm = m.getRoot();
                                }
                                if (m.getTraits().contains("plural")) {
                                    n.plurality = "Plural";
                                }
                                if(!roots.containsKey(n.baseForm+":noun"))
                                    roots.put(n.baseForm+":noun", word);
                                else
                                {
                                    String e = roots.get(n.baseForm+":noun");
                                    roots.put(n.baseForm+":noun", e +"|"+ word);
                                }
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            animacy.animacy = n.checkAnimacy(word,
                                    animacy.animacy);
                            merge(word, n);
                        }
                        else if (!masculine) {
                            Noun n = new Noun(words[i] + "@~~");
                            try {
                                MorphologyFinder m = new MorphologyFinder(
                                        word);
                                m.loadDictionary(nounDictionary);
                                m.breakApart();
                                try {
                                    n.baseForm = dictionary.lookupIndexWord(
                                            POS.NOUN, m.getRoot()).getLemma();
                                }
                                catch (Exception e) {
                                    n.baseForm = m.getRoot();
                                }
                                if (m.getTraits().contains("plural")) {
                                    n.plurality = "Plural";
                                    Noun temp = new Noun(n.baseForm);
                                    temp.plurality = "Singular";
                                    merge(n.baseForm,temp);
                                }
                                if(!roots.containsKey(n.baseForm+":noun"))
                                    roots.put(n.baseForm+":noun", word);
                                else
                                {
                                    String e = roots.get(n.baseForm+":noun");
                                    roots.put(n.baseForm+":noun", e +"|"+ word);
                                }
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            animacy.animacy = n.checkAnimacy(word,
                                    animacy.animacy);
                            merge(word, n);
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
        try {
            if (part instanceof Noun) {
                if(firstNameDictionary.containsKey(w)||lastNameDictionary.containsKey(w)||properPlaceDictionary.containsKey(w)){
                if(firstNameDictionary.containsKey(w)){
                    mergeFirstName(w,(Noun) part);
                }
                if(lastNameDictionary.containsKey(w)){
                    mergeLastName(w,(Noun) part);
                }
                if(properPlaceDictionary.containsKey(w)){
                    mergeProperPlace(w,(Noun) part);
                }
                }
                else if (nounDictionary.containsKey(w)) {
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
                    p.isProperName = false;
                    if (p.compliments.equals("--")) {
                        p.compliments = ((Noun) part).compliments;
                    }
                    if (p.irregularPluralForm.equals("--")) {
                        p.irregularPluralForm = ((Noun) part).irregularPluralForm;
                    }
                    if (p.animacy == null) {
                        p.animacy = ((Noun) part).animacy;
                    }
                    if (p.location == null) {
                        p.location = ((Noun) part).location;
                    }
                    if (p.commonRank == -1) {
                        p.commonRank = ((Noun) part).commonRank;
                    }
                    if (p.howCommon == -1) {
                        p.howCommon = ((Noun) part).howCommon;
                    }
                    if(!((Noun) part).baseForm.equals("--")){
                        p.baseForm = ((Noun) part).baseForm;
                    }
                    if(!((Noun) part).propbank.equals("--")){
                        if(p.propbank.equals("--")){
                            p.propbank = ((Noun) part).propbank + "|";
                        }else{
                            p.propbank += ((Noun) part).propbank + "|";
                        }
                    }
                    if(!((Noun) part).frame.equals("--")){
                        if(p.frame.equals("--")){
                            p.frame = ((Noun) part).frame + "|";
                        }else{
                            p.frame += ((Noun) part).frame + "|";
                        }
                    }
                    nounDictionary.put(w,p);
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
                    if (p.adjectiveIntensifierID == -1) {
                        p.adjectiveIntensifierID = ((Adjective) part).adjectiveIntensifierID;
                    }
                    if (p.commonRank == -1) {
                        p.commonRank = ((Adjective) part).commonRank;
                    }
                    if (p.howCommon == -1) {
                        p.howCommon = ((Adjective) part).howCommon;
                    }
                    if(!((Adjective) part).baseForm.equals("--")){
                        p.baseForm = ((Adjective) part).baseForm;
                    }
                    if(!((Adjective) part).propbank.equals("--")){
                        if(p.propbank.equals("--")){
                            p.propbank = ((Adjective) part).propbank + "|";
                        }
                        else{
                            p.propbank += ((Adjective) part).propbank + "|";
                        }
                    }
                    if(!((Adjective) part).frame.equals("--")){
                        if(p.frame.equals("--")){
                            p.frame = ((Adjective) part).frame +"|";
                        }
                        else{
                            p.frame += ((Adjective) part).frame + "|";
                        }
                    }
                    adjectiveDictionary.put(w, p);
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
                    if (p.noCompOrSuperForm == null) {
                        p.noCompOrSuperForm = ((Adverb) part).noCompOrSuperForm;
                    }
                    if (p.mustUseMoreMost == null) {
                        p.mustUseMoreMost = ((Adverb) part).mustUseMoreMost;
                    }
                    if (p.irregularForm.equals("--")) {
                        p.irregularForm = ((Adverb) part).irregularForm;
                    }
                    if (p.commonRank == -1) {
                        p.commonRank = ((Adverb) part).commonRank;
                    }
                    if (p.howCommon == -1) {
                        p.howCommon = ((Adverb) part).howCommon;
                    }
                    if(p.wordSenseID == -1){
                        p.wordSenseID = ((Adverb) part).wordSenseID;
                    }
                    if(!((Adverb) part).baseForm.equals("--")){
                        p.baseForm = ((Adverb) part).baseForm;
                    }
                    
                    adverbDictionary.put(w, p);
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
                    if (p.commonRank == -1) {
                        p.commonRank = ((Conjunction) part).commonRank;
                    }
                    if (p.howCommon == -1) {
                        p.howCommon = ((Conjunction) part).howCommon;
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
                    if (p.commonRank == -1) {
                        p.commonRank = ((Determiner) part).commonRank;
                    }
                    if (p.howCommon == -1) {
                        p.howCommon = ((Determiner) part).howCommon;
                    }
                }
                else {
                    determinerDictionary.put(w, (Determiner) part);
                }
            }
            else if (part instanceof Interjection) {
                if (interjectionDictionary.containsKey(w)) {
                    Interjection p = (Interjection) interjectionDictionary
                            .get(w);
                    if (p.interjectionTypeID == -1) {
                        p.interjectionTypeID = ((Interjection) part).interjectionTypeID;
                    }
                    if (p.commonRank == -1) {
                        p.commonRank = ((Interjection) part).commonRank;
                    }
                    if (p.howCommon == -1) {
                        p.howCommon = ((Interjection) part).howCommon;
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
                    if (p.commonRank == -1) {
                        p.commonRank = ((Preposition) part).commonRank;
                    }
                    if (p.howCommon == -1) {
                        p.howCommon = ((Preposition) part).howCommon;
                    }
                    prepositionDictionary.put(w, p);
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
                    if (p.commonRank == -1) {
                        p.commonRank = ((Pronoun) part).commonRank;
                    }
                    if (p.howCommon == -1) {
                        p.howCommon = ((Pronoun) part).howCommon;
                    }
                    pronounDictionary.put(w, p);
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
                    if(!((Verb) part).transivity.equals("--")){
                        if(p.transivity.equals("--"))
                            p.transivity = ((Verb) part).transivity +"|";
                        else
                            p.transivity += ((Verb) part).transivity+"|";
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
                    if (p.commonRank == -1) {
                        p.commonRank = ((Verb) part).commonRank;
                    }
                    if (p.howCommon == -1) {
                        p.howCommon = ((Verb) part).howCommon;
                    }
                    if(!((Verb) part).baseForm.equals("--")){
                        p.baseForm = ((Verb) part).baseForm;
                    }
                    if(!((Verb) part).wordNetID.equals("--")){
                        if(p.wordNetID.equals("--"))
                            p.wordNetID = ((Verb) part).wordNetID + "|";
                        else if(!p.wordNetID.contains(((Verb) part).wordNetID))
                            p.wordNetID += ((Verb) part).wordNetID + "|";
                    }
                    if(!((Verb) part).verbnet.equals("--")){
                        if(p.verbnet.equals("--"))
                            p.verbnet = ((Verb) part).verbnet + "|";
                        else
                            p.verbnet += ((Verb) part).verbnet + "|";
                    }
                    if(!((Verb) part).propbank.equals("--")){
                        if(p.propbank.equals("--"))
                            p.propbank = ((Verb) part).propbank + "|";
                        else
                            p.propbank += ((Verb) part).propbank + "|";
                    }
                    if(!((Verb) part).frame.equals("--")){
                        if(p.frame.equals("--"))
                            p.frame = ((Verb) part).frame + "|";
                        else
                            p.frame += ((Verb) part).frame + "|";
                    }
                        verbDictionary.put(w, p);
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
                    if (q.commonRank == -1) {
                        q.commonRank = ((Quantifier) part).commonRank;
                    }
                    if (q.howCommon == -1) {
                        q.howCommon = ((Quantifier) part).howCommon;
                    }
                    quantifierDictionary.put(w, q);
                }
                else {
                    quantifierDictionary.put(w, (Quantifier) part);
                }
            }
        }
        catch (Exception e) {
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
                //System.out.println(scanIn);
                if (!scanIn.equals("\n") && !scanIn.equals("")) {
                    // seperates the part of speech from the word
                    String[] input = scanIn.split("\\$");
                    if (input.length > 0 && input.length < 3) {
                        String word = input[0];
                        String[] poses = input[1].split("");
                        int i = 0;
                        // goes through each part of speech
                        while (i < poses.length) {
                            String pos = poses[i];
                            System.out.println(word);
                            /* Check what part of speech it is */
                            if (pos.equals("N")) {
                                Noun n = new Noun(word);
                                animacy.animacy = n.checkAnimacy(word,
                                        animacy.animacy);
                                try {
                                    MorphologyFinder m = new MorphologyFinder(
                                            word);
                                    m.loadDictionary(nounDictionary);
                                    m.breakApart();
                                    try {
                                        n.baseForm = dictionary
                                                .lookupIndexWord(POS.NOUN,
                                                        m.getRoot())
                                                .getLemma();
                                    }
                                    catch (Exception e) {
                                        n.baseForm = m.getRoot();
                                    }
                                    if (m.getTraits().contains("plural")) {
                                        n.plurality = "Plural";
                                        if(nounDictionary.containsKey(n.baseForm)){
                                        Noun temp = new Noun(n.baseForm);
                                        temp.plurality = "Singular";
                                        merge(n.baseForm,temp);
                                        }
                                    }
                                    if(!roots.containsKey(n.baseForm+":noun"))
                                        roots.put(n.baseForm+":noun", word);
                                    else{
                                        String e = roots.get(n.baseForm+":noun");
                                        roots.put(n.baseForm+":noun", e +"|"+ word);
                                    }
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }

                                merge(word, n);
                            }
                            else if (pos.equals("p")) {
                                Noun n = new Noun(word);
                                n.plurality = "Plural";
                                animacy.animacy = n.checkAnimacy(word,
                                        animacy.animacy);
                                try {
                                    MorphologyFinder m = new MorphologyFinder(
                                            word);
                                    m.loadDictionary(nounDictionary);
                                    m.breakApart();
                                    try {
                                        n.baseForm = dictionary
                                                .lookupIndexWord(POS.NOUN,
                                                        m.getRoot())
                                                .getLemma();
                                    }
                                    catch (Exception e) {
                                        n.baseForm = m.getRoot();
                                    }
                                    if (m.getTraits().contains("plural")) {
                                        n.plurality = "Plural";
                                        if(nounDictionary.containsKey(n.baseForm)){
                                        Noun temp = new Noun(n.baseForm);
                                        temp.plurality = "Singular";
                                        merge(n.baseForm,temp);
                                        }
                                    }
                                    if(!roots.containsKey(n.baseForm+":noun"))
                                        roots.put(n.baseForm+":noun", word);
                                    else{
                                        String e = roots.get(n.baseForm+":noun");
                                        roots.put(n.baseForm+":noun", e + "|"+word);
                                    }
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                                merge(word, n);
                            }
                            else if (pos.equals("h")) {
                                Noun n = new Noun(word);
                                n.isCountable = true;
                                animacy.animacy = n.checkAnimacy(word,
                                        animacy.animacy);
                                try {
                                    MorphologyFinder m = new MorphologyFinder(
                                            word);
                                    m.loadDictionary(nounDictionary);
                                    m.breakApart();
                                    try {
                                        n.baseForm = dictionary
                                                .lookupIndexWord(POS.NOUN,
                                                        m.getRoot())
                                                .getLemma();
                                    }
                                    catch (Exception e) {
                                        n.baseForm = m.getRoot();
                                    }
                                    if (m.getTraits().contains("plural")) {
                                        n.plurality = "Plural";
                                        if(nounDictionary.containsKey(n.baseForm)){
                                        Noun temp = new Noun(n.baseForm);
                                        temp.plurality = "Singular";
                                        merge(n.baseForm,temp);
                                        }
                                    }
                                    if(!roots.containsKey(n.baseForm+":noun"))
                                        roots.put(n.baseForm+":noun", word);
                                    else{
                                        String e = roots.get(n.baseForm+":noun");
                                        roots.put(n.baseForm+":noun",  e+"|"+ word);
                                    }
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                                merge(word, n);
                            }
                            else if (pos.equals("V")) {
                                Verb v = new Verb(word);
                                try {
                                    MorphologyFinder m = new MorphologyFinder(
                                            word);
                                    m.loadDictionary(verbDictionary);
                                    m.breakApart();
                                    try {
                                        v.baseForm = dictionary
                                                .lookupIndexWord(POS.VERB,
                                                        m.getRoot())
                                                .getLemma();
                                    }
                                    catch (Exception e) {
                                        v.baseForm = m.getRoot();
                                    }
                                    if (m.getTraits().contains("past tense")) {
                                        v.tense = "Past";
                                    }
                                    else if (m.getTraits()
                                            .contains("present tense")) {
                                        v.tense = "Present";
                                    }
                                    if(!roots.containsKey(v.baseForm+":verb"))
                                        roots.put(v.baseForm+":verb", word);
                                    else{
                                        String e = roots.get(v.baseForm+":verb");
                                        roots.put(v.baseForm+":verb", e +"|"+ word);
                                    }
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                                merge(word, v);
                            }
                            else if (pos.equals("t") && i < poses.length - 1
                                    && poses[i + 1].equals("i")) {
                                Verb v = new Verb(word);
                                v.transivity = "Ambitransitive";
                                try {
                                    MorphologyFinder m = new MorphologyFinder(
                                            word);
                                    m.loadDictionary(verbDictionary);
                                    m.breakApart();
                                    try {
                                        v.baseForm = dictionary
                                                .lookupIndexWord(POS.VERB,
                                                        m.getRoot())
                                                .getLemma();
                                    }
                                    catch (Exception e) {
                                        v.baseForm = m.getRoot();
                                    }
                                    if (m.getTraits().contains("past tense")) {
                                        v.tense = "Past";
                                    }
                                    else if (m.getTraits()
                                            .contains("present tense")) {
                                        v.tense = "Present";
                                    }
                                    if(!roots.containsKey(v.baseForm+":verb"))
                                        roots.put(v.baseForm+":verb", word);
                                    else{
                                        String e = roots.get(v.baseForm+":verb");
                                        roots.put(v.baseForm+":verb", e +"|"+ word);
                                    }
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                                merge(word, v);
                                i++;
                            }
                            else if (pos.equals("t")) {
                                Verb v = new Verb(word);
                                v.transivity = "transitive";
                                try {
                                    MorphologyFinder m = new MorphologyFinder(
                                            word);
                                    m.loadDictionary(verbDictionary);
                                    m.breakApart();
                                    try {
                                        v.baseForm = dictionary
                                                .lookupIndexWord(POS.VERB,
                                                        m.getRoot())
                                                .getLemma();
                                    }
                                    catch (Exception e) {
                                        v.baseForm = m.getRoot();
                                    }
                                    if (m.getTraits().contains("past tense")) {
                                        v.tense = "Past";
                                    }
                                    else if (m.getTraits()
                                            .contains("present tense")) {
                                        v.tense = "Present";
                                    }
                                    if(!roots.containsKey(v.baseForm+":verb"))
                                        roots.put(v.baseForm+":verb", word);
                                    else{
                                        String e = roots.get(v.baseForm+":verb");
                                        roots.put(v.baseForm+":verb", e +"|"+ word);
                                    }
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                                merge(word, v);
                            }
                            else if (pos.equals("i")) {
                                Verb v = new Verb(word);
                                v.transivity = "intransitive";
                                try {
                                    MorphologyFinder m = new MorphologyFinder(
                                            word);
                                    m.loadDictionary(verbDictionary);
                                    m.breakApart();
                                    try {
                                        v.baseForm = dictionary
                                                .lookupIndexWord(POS.VERB,
                                                        m.getRoot())
                                                .getLemma();
                                    }
                                    catch (Exception e) {
                                        v.baseForm = m.getRoot();
                                    }
                                    if (m.getTraits().contains("past tense")) {
                                        v.tense = "Past";
                                    }
                                    else if (m.getTraits()
                                            .contains("present tense")) {
                                        v.tense = "Present";
                                    }
                                    if(!roots.containsKey(v.baseForm+":verb"))
                                        roots.put(v.baseForm+":verb", word);
                                    else{
                                        String e = roots.get(v.baseForm+":verb");
                                        roots.put(v.baseForm+":verb", e +"|"+ word);
                                    }
                                }
                                catch (Exception e) {
                                     e.printStackTrace();
                                }
                                merge(word, v);
                            }
                            else if (pos.equals("A")) {
                                Adjective a = new Adjective(word);
                                try {
                                    MorphologyFinder m = new MorphologyFinder(
                                            word);
                                    m.loadDictionary(adjectiveDictionary);
                                    m.breakApart();
                                    try {
                                        a.baseForm = dictionary
                                                .lookupIndexWord(POS.ADJECTIVE,
                                                        m.getRoot())
                                                .getLemma();
                                    }
                                    catch (Exception e) {
                                        a.baseForm = m.getRoot();
                                    }
                                    if(!roots.containsKey(a.baseForm+":adjective"))
                                        roots.put(a.baseForm+":adjective", word);
                                    else{
                                        String e = roots.get(a.baseForm+":adjective");
                                        roots.put(a.baseForm+":adjective", e +"|"+ word);
                                    }
                                }
                                catch (Exception e) {
                                     e.printStackTrace();
                                }
                                merge(word, a);
                            }
                            else if (pos.equals("v")) {
                                Adverb a = new Adverb(word);
                                try {
                                    MorphologyFinder m = new MorphologyFinder(
                                            word);
                                    m.loadDictionary(adverbDictionary);
                                    m.breakApart();
                                    try {
                                        a.baseForm = dictionary
                                                .lookupIndexWord(POS.ADVERB,
                                                        m.getRoot())
                                                .getLemma();
                                    }
                                    catch (Exception e) {
                                        a.baseForm = m.getRoot();
                                    }
                                    if(!roots.containsKey(a.baseForm+":adverb"))
                                        roots.put(a.baseForm+":adverb", word);
                                    else{
                                        String e = roots.get(a.baseForm+":adverb");
                                        roots.put(a.baseForm+":adverb", e +"|"+ word);
                                    }
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                                merge(word, a);
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
                        if (x == 5000) {
                            /* store animacy querys for future runs */
                            fout = new FileOutputStream(
                                    "outputs/animacyquery.ser");
                            out = new ObjectOutputStream(fout);
                            out.writeObject(animacy);
                            out.flush();
                            out.close();
                            fout.close();
                            x = 0;
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
                            Verb v = new Verb(word);
                            v.transivity = "Ambitransitive";
                            try {
                                MorphologyFinder m = new MorphologyFinder(
                                        word);
                                m.loadDictionary(verbDictionary);
                                m.breakApart();
                                try {
                                    v.baseForm = dictionary.lookupIndexWord(
                                            POS.VERB, m.getRoot()).getLemma();
                                }
                                catch (Exception e) {
                                    v.baseForm = m.getRoot();
                                }
                                if (m.getTraits().contains("past tense")) {
                                    v.tense = "Past";
                                }
                                else if (m.getTraits()
                                        .contains("present tense")) {
                                    v.tense = "Present";
                                }
                                if(!roots.containsKey(v.baseForm+":verb"))
                                    roots.put(v.baseForm+":verb", word);
                                else{
                                    String e = roots.get(v.baseForm+":verb");
                                    roots.put(v.baseForm+":verb", e +"|"+ word);
                                }
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            merge(word, v);
                        }
                        else if (eElement.getElementsByTagName("ditransitive")
                                .getLength() > 0) {
                            Verb v = new Verb(word);
                            v.transivity = "Ditransitive";
                            try {
                                MorphologyFinder m = new MorphologyFinder(
                                        word);
                                m.loadDictionary(verbDictionary);
                                m.breakApart();
                                try {
                                    v.baseForm = dictionary.lookupIndexWord(
                                            POS.VERB, m.getRoot()).getLemma();
                                }
                                catch (Exception e) {
                                    v.baseForm = m.getRoot();
                                }
                                if (m.getTraits().contains("past tense")) {
                                    v.tense = "Past";
                                }
                                else if (m.getTraits()
                                        .contains("present tense")) {
                                    v.tense = "Present";
                                }
                                if(!roots.containsKey(v.baseForm+":verb"))
                                    roots.put(v.baseForm+":verb", word);
                                else{
                                    String e = roots.get(v.baseForm+":verb");
                                    roots.put(v.baseForm+":verb", e +"|"+ word);
                                }
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            merge(word, v);
                        }
                        else if (eElement.getElementsByTagName("transitive")
                                .getLength() > 0) {
                            Verb v = new Verb(word);
                            v.transivity = "Transitive";
                            try {
                                MorphologyFinder m = new MorphologyFinder(
                                        word);
                                m.loadDictionary(verbDictionary);
                                m.breakApart();
                                try {
                                    v.baseForm = dictionary.lookupIndexWord(
                                            POS.VERB, m.getRoot()).getLemma();
                                }
                                catch (Exception e) {
                                    v.baseForm = m.getRoot();
                                }
                                if (m.getTraits().contains("past tense")) {
                                    v.tense = "Past";
                                }
                                else if (m.getTraits()
                                        .contains("present tense")) {
                                    v.tense = "Present";
                                }
                                if(!roots.containsKey(v.baseForm+":verb"))
                                    roots.put(v.baseForm+":verb", word);
                                else{
                                    String e = roots.get(v.baseForm+":verb");
                                    roots.put(v.baseForm+":verb", e +"|"+ word);
                                }
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            merge(word, v);
                        }
                        else if (eElement.getElementsByTagName("intransitive")
                                .getLength() > 0) {
                            Verb v = new Verb(word);
                            v.transivity = "Intransitive";
                            try {
                                MorphologyFinder m = new MorphologyFinder(
                                        word);
                                m.loadDictionary(verbDictionary);
                                m.breakApart();
                                try {
                                    v.baseForm = dictionary.lookupIndexWord(
                                            POS.VERB, m.getRoot()).getLemma();
                                }
                                catch (Exception e) {
                                    v.baseForm = m.getRoot();
                                }
                                if (m.getTraits().contains("past tense")) {
                                    v.tense = "Past";
                                }
                                else if (m.getTraits()
                                        .contains("present tense")) {
                                    v.tense = "Present";
                                }
                                if(!roots.containsKey(v.baseForm+":verb"))
                                    roots.put(v.baseForm+":verb", word);
                                else{
                                    String e = roots.get(v.baseForm+":verb");
                                    roots.put(v.baseForm+":verb", e +"|"+ word);
                                }
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            merge(word, v);
                        }
                        else {
                            Verb v = new Verb(word);
                            try {
                                MorphologyFinder m = new MorphologyFinder(
                                        word);
                                m.loadDictionary(verbDictionary);
                                m.breakApart();
                                try {
                                    v.baseForm = dictionary.lookupIndexWord(
                                            POS.VERB, m.getRoot()).getLemma();
                                }
                                catch (Exception e) {
                                    v.baseForm = m.getRoot();
                                }
                                if (m.getTraits().contains("past tense")) {
                                    v.tense = "Past";
                                }
                                else if (m.getTraits()
                                        .contains("present tense")) {
                                    v.tense = "Present";
                                }
                                if(!roots.containsKey(v.baseForm+":verb"))
                                    roots.put(v.baseForm+":verb", word);
                                else{
                                    String e = roots.get(v.baseForm+":verb");
                                    roots.put(v.baseForm+":verb", e +"|"+ word);
                                }
                            }
                            catch (Exception e) {
                               e.printStackTrace();
                            }
                            merge(word, v);
                        }
                    }
                    else if (pos.equals("noun")) {
                        if (eElement.getElementsByTagName("nonCount")
                                .getLength() > 0) {
                            Noun n = new Noun(word);
                            n.isCountable = true;
                            try {
                                MorphologyFinder m = new MorphologyFinder(
                                        word);
                                m.loadDictionary(nounDictionary);
                                m.breakApart();
                                try {
                                    n.baseForm = dictionary.lookupIndexWord(
                                            POS.NOUN, m.getRoot()).getLemma();
                                }
                                catch (Exception e) {
                                    n.baseForm = m.getRoot();
                                }
                                if (m.getTraits().contains("plural")) {
                                    n.plurality = "Plural";
                                    if(nounDictionary.containsKey(n.baseForm)){
                                    Noun temp = new Noun(n.baseForm);
                                    temp.plurality = "Singular";
                                    merge(n.baseForm,temp);
                                    }
                                }
                                if(!roots.containsKey(n.baseForm+":noun"))
                                    roots.put(n.baseForm+":noun", word);
                                else
                                {
                                    String e = roots.get(n.baseForm+":noun");
                                    roots.put(n.baseForm+":noun", e +"|"+ word);
                                }
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            animacy.animacy = n.checkAnimacy(word,
                                    animacy.animacy);
                            merge(word, n);
                        }
                        else {
                            Noun n = new Noun(word);
                            try {
                                MorphologyFinder m = new MorphologyFinder(
                                        word);
                                m.loadDictionary(nounDictionary);
                                m.breakApart();
                                try {
                                    n.baseForm = dictionary.lookupIndexWord(
                                            POS.NOUN, m.getRoot()).getLemma();
                                }
                                catch (Exception e) {
                                    n.baseForm = m.getRoot();
                                }
                                if (m.getTraits().contains("plural")) {
                                    n.plurality = "Plural";
                                    if(nounDictionary.containsKey(n.baseForm)){
                                    Noun temp = new Noun(n.baseForm);
                                    temp.plurality = "Singular";
                                    merge(n.baseForm,temp);
                                    }
                                }
                                if(!roots.containsKey(n.baseForm+":noun"))
                                    roots.put(n.baseForm+":noun", word);
                                else{
                                    String e = roots.get(n.baseForm+":noun");
                                    roots.put(n.baseForm+":noun", e +"|"+ word);
                                }
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            animacy.animacy = n.checkAnimacy(word,
                                    animacy.animacy);
                            merge(word, n);
                        }
                    }
                    else if (pos.equals("adjective")) {
                        Adjective a = new Adjective(word);
                        if (eElement.getElementsByTagName("classifying")
                                .getLength() > 0) {
                            a.isClassifying = true;
                        }
                        else {
                            a.isClassifying = false;
                        }
                        if (eElement.getElementsByTagName("predicative")
                                .getLength() > 0) {
                            a.worksInPredicativePosition = true;
                        }
                        else {
                            a.worksInPredicativePosition = false;
                        }
                        if (eElement.getElementsByTagName("qualitative")
                                .getLength() > 0) {
                            a.isQualitative = true;
                        }
                        else {
                            a.isQualitative = false;
                        }
                        
                        try {
                            MorphologyFinder m = new MorphologyFinder(word);
                            m.loadDictionary(adjectiveDictionary);
                            m.breakApart();
                            try {
                                a.baseForm = dictionary.lookupIndexWord(
                                        POS.ADJECTIVE, m.getRoot()).getLemma();
                            }
                            catch (Exception e) {
                                a.baseForm = m.getRoot();
                            }
                            if(!roots.containsKey(a.baseForm+":adjective"))
                                roots.put(a.baseForm+":adjective", word);
                            else{
                                String e = roots.get(a.baseForm+":adjective");
                                roots.put(a.baseForm+":adjective", e +"|"+ word);
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        merge(word, a);
                    }
                    else if (pos.equals("adverb")) {
                        Adverb a = new Adverb(word);
                        try {
                            MorphologyFinder m = new MorphologyFinder(word);
                            m.loadDictionary(adverbDictionary);
                            m.breakApart();
                            try {
                                a.baseForm = dictionary.lookupIndexWord(
                                        POS.ADVERB, m.getRoot()).getLemma();
                            }
                            catch (Exception e) {
                                a.baseForm = m.getRoot();
                            }
                            if(!roots.containsKey(a.baseForm+":adverb"))
                                roots.put(a.baseForm+":adverb", word);
                            else{
                                String e = roots.get(a.baseForm+":adverb");
                                roots.put(a.baseForm+":adverb", e +"|"+ word);
                            }
                        }
                        catch (Exception e) {
                           e.printStackTrace();
                        }
                        merge(word, a);
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
                    Noun n = new Noun(word);
                    n.isCountable = true;
                    try {
                        MorphologyFinder m = new MorphologyFinder(word);
                        m.loadDictionary(nounDictionary);
                        m.breakApart();
                        try {
                            n.baseForm = dictionary
                                    .lookupIndexWord(POS.NOUN, m.getRoot())
                                    .getLemma();
                        }
                        catch (Exception e) {
                            n.baseForm = m.getRoot();
                        }
                        if (m.getTraits().contains("plural")) {
                            n.plurality = "plural";
                            if(nounDictionary.containsKey(n.baseForm)){
                            Noun temp = new Noun(n.baseForm);
                            temp.plurality = "Singular";
                            merge(n.baseForm,temp);
                            }
                        }
                        if(!roots.containsKey(n.baseForm+":noun"))
                            roots.put(n.baseForm+":noun", word);
                        else{
                            String e = roots.get(n.baseForm+":noun");
                            roots.put(n.baseForm+":noun", e + "|"+word);
                        }
                    }
                    catch (Exception e) {
                         e.printStackTrace();
                    }
                    animacy.animacy = n.checkAnimacy(word, animacy.animacy);
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
                    Noun n = new Noun(word);
                    n.isCountable = false;
                    try {
                        MorphologyFinder m = new MorphologyFinder(word);
                        m.loadDictionary(nounDictionary);
                        m.breakApart();
                        try {
                            n.baseForm = dictionary
                                    .lookupIndexWord(POS.NOUN, m.getRoot())
                                    .getLemma();
                        }
                        catch (Exception e) {
                            n.baseForm = m.getRoot();
                        }
                        if (m.getTraits().contains("plural")) {
                            n.plurality = "plural";
                        }
                        if(!roots.containsKey(n.baseForm+":noun")){
                            roots.put(n.baseForm+":noun", word);
                            if(nounDictionary.containsKey(n.baseForm)){
                            Noun temp = new Noun(n.baseForm);
                            temp.plurality = "Singular";
                            merge(n.baseForm,temp);
                            }
                        }
                        else{
                            String e = roots.get(n.baseForm+":noun");
                            roots.put(n.baseForm+":noun", e + "|"+word);
                        }
                    }
                    catch (Exception e) {
                         e.printStackTrace();
                    }
                    animacy.animacy = n.checkAnimacy(word, animacy.animacy);
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
                if (s.charAt(0) == '{' && s.length() > 6) {
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
                else if (split[0].endsWith("spelling_variant")) {
                    wordvariant = split[1];
                }
                else if (pos.length() > 0
                        && !getdictionary(pos).containsKey(word)
                        && getdictionary(pos).containsKey(wordvariant)) {
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
                else if (s.equals("}") && pos.length() > 0
                        && getdictionary(pos).containsKey(word)) {
                    wordsseen++;
                    // add nouns
                    if (pos.equals("noun")) {
                        Noun n = new Noun(word);
                        if (count) {
                            n.isCountable = true;
                        }
                        else {
                            n.isCountable = false;
                        }
                        if (plurality.equals("Singular")) {
                            n.plurality = "Singular";
                        }
                        else if (plurality.equals("Plural")) {
                            n.plurality = "Plural";
                        }
                        
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
                    if (pos.equals("verb")) {
                        Verb v = new Verb(word);
                        v.addCompliments(compliments);
                        parts.add(v);
                    }
                    if (pos.equals("adj")) {
                        Adjective a = new Adjective(word);
                        // attrib position
                        if (attribposition) {
                            a.worksInAttributivePosition = true;
                        }
                        else {
                            a.worksInAttributivePosition = false;
                        }
                        // is classifying
                        if (classifying) {
                            a.isClassifying = true;
                        }
                        else {
                            a.isClassifying = false;
                        }
                        // predic pos
                        if (predposition) {
                            a.worksInPredicativePosition = true;
                        }
                        else {
                            a.worksInPredicativePosition = false;
                        }
                        // is qualitative
                        if (qualitative) {
                            a.isQualitative = true;
                        }
                        else {
                            a.isQualitative = false;
                        }
                        
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
                    if (pos.equals("det"))
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
                else if(s.equals("}") && pos.length() > 0
                        && !getdictionary(pos).containsKey(word)){
                    /*try { FileWriter fw = new FileWriter("outputs/NATD.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw); PrintWriter printer =
                    new PrintWriter(bw); printer.println("Specialist Lexicon: " + word); printer.close();
                    bw.close(); fw.close();
                     
                     } 
                    catch (IOException e) { e.printStackTrace(); }*/
                }
            }
            scan.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void adverbIntensifiers() {
        try {
            Scanner scan = new Scanner(
                    new File("inputs/AdverbScales-Manual.csv"));
            scan.nextLine();
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                String[] split = s.split(",");
                if (split.length > 1) {
                    String word = split[0];
                    Adverb adv = new Adverb(word);
                    adv.advIntensiferID = (Integer.parseInt(split[2])) + 2;
                    adv.advIntensifier = split[1];
                    merge(word, adv);
                    if (word.endsWith("ly")) {
                        String wordadj = word.substring(0, word.length() - 2);
                            Adjective adj = new Adjective(wordadj);
                            adj.adjectiveIntensifierID = (Integer
                                    .parseInt(split[2])) + 2;
                            merge(wordadj, adj);
                    }
                }
            }
            scan.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void locations() {
        try {
            Scanner s = new Scanner(new File("inputs/locations.txt"));
            while (s.hasNext()) {
                String stuff = s.next();
                Noun n = new Noun(stuff);
                n.location = true;
                n.animacy = "Inanimate";
                try {
                    MorphologyFinder m = new MorphologyFinder(stuff);
                    m.loadDictionary(nounDictionary);
                    m.breakApart();
                    try {
                        n.baseForm = dictionary
                                .lookupIndexWord(POS.NOUN, m.getRoot())
                                .getLemma();
                    }
                    catch (Exception e) {
                        n.baseForm = m.getRoot();
                    }
                    if (m.getTraits().contains("plural")) {
                        n.plurality = "plural";
                    }
                    if(!roots.containsKey(n.baseForm+":noun")){
                        roots.put(n.baseForm+":noun", stuff);
                        if(nounDictionary.containsKey(n.baseForm)){
                        Noun temp = new Noun(n.baseForm);
                        temp.plurality = "Singular";
                        merge(n.baseForm,temp);
                        }
                    }
                    else{
                        String e = roots.get(n.baseForm+":noun");
                        roots.put(n.baseForm+":noun", e + "|"+stuff);
                    }
                }
                catch (Exception e) {
                     e.printStackTrace();
                }
                merge(stuff, n);
            }
            s.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void frequency() {
        try {
            Scanner s = new Scanner(
                    new File("inputs/Word_frequency_list.txt"));

            while (s.hasNextLine()) {
                String stuff = s.nextLine();
                // System.out.println(stuff);
                String[] split = stuff.split(",");
                String word = split[0].substring(1, split[0].length());
                Float c = Float
                        .parseFloat(split[1].substring(1, split[1].length()));
                long rank = Long.parseLong(
                        split[2].substring(1, split[2].length() - 1));
                System.out
                        .println(word + ":" + c + ":" + rank);
                if (nounDictionary.containsKey(word)) {
                    Noun n = new Noun(word);
                    n.howCommon = c;
                    n.commonRank = rank;
                    try {
                        MorphologyFinder m = new MorphologyFinder(
                                word);
                        m.loadDictionary(nounDictionary);
                        m.breakApart();
                        try {
                            n.baseForm = dictionary
                                    .lookupIndexWord(POS.NOUN,
                                            m.getRoot())
                                    .getLemma();
                        }
                        catch (Exception e) {
                            n.baseForm = m.getRoot();
                        }
                        if (m.getTraits().contains("plural")) {
                            n.plurality = "Plural";
                            if(nounDictionary.containsKey(n.baseForm)){
                            Noun temp = new Noun(n.baseForm);
                            temp.plurality = "Singular";
                            merge(n.baseForm,temp);
                            }
                        }
                        if(!roots.containsKey(n.baseForm+":noun"))
                            roots.put(n.baseForm+":noun", word);
                        else{
                            String e = roots.get(n.baseForm+":noun");
                            roots.put(n.baseForm+":noun", e +"|"+ word);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    merge(word, n);
                }
                if (verbDictionary.containsKey(word)) {
                    Verb v = new Verb(word);
                    v.howCommon = c;
                    v.commonRank = rank;
                    try {
                        MorphologyFinder m = new MorphologyFinder(
                                word);
                        m.loadDictionary(verbDictionary);
                        m.breakApart();
                        try {
                            v.baseForm = dictionary.lookupIndexWord(
                                    POS.VERB, m.getRoot()).getLemma();
                        }
                        catch (Exception e) {
                            v.baseForm = m.getRoot();
                        }
                        if (m.getTraits().contains("past tense")) {
                            v.tense = "Past";
                        }
                        else if (m.getTraits()
                                .contains("present tense")) {
                            v.tense = "Present";
                        }
                        if(!roots.containsKey(v.baseForm+":verb"))
                            roots.put(v.baseForm+":verb", word);
                        else{
                            String e = roots.get(v.baseForm+":verb");
                            roots.put(v.baseForm+":verb", e +"|"+ word);
                        }
                    }
                    catch (Exception e) {
                       e.printStackTrace();
                    }
                    merge(word, v);
                }
                if (adjectiveDictionary.containsKey(word)) {
                    Adjective a = new Adjective(word);
                    a.howCommon = c;
                    a.commonRank = rank;
                    try {
                        MorphologyFinder m = new MorphologyFinder(
                                word);
                        m.loadDictionary(adjectiveDictionary);
                        m.breakApart();
                        try {
                            a.baseForm = dictionary
                                    .lookupIndexWord(POS.ADJECTIVE,
                                            m.getRoot())
                                    .getLemma();
                        }
                        catch (Exception e) {
                            a.baseForm = m.getRoot();
                        }
                        if(!roots.containsKey(a.baseForm+":adjective"))
                            roots.put(a.baseForm+":adjective", word);
                        else{
                            String e = roots.get(a.baseForm+":adjective");
                            roots.put(a.baseForm+":adjective", e +"|"+ word);
                        }
                    }
                    catch (Exception e) {
                         e.printStackTrace();
                    }
                    merge(word, a);
                }
                if (adverbDictionary.containsKey(word)) {
                    Adverb a = new Adverb(word);
                    a.howCommon = c;
                    a.commonRank = rank;
                    try {
                        MorphologyFinder m = new MorphologyFinder(
                                word);
                        m.loadDictionary(adverbDictionary);
                        m.breakApart();
                        try {
                            a.baseForm = dictionary
                                    .lookupIndexWord(POS.ADVERB,
                                            m.getRoot())
                                    .getLemma();
                        }
                        catch (Exception e) {
                            a.baseForm = m.getRoot();
                        }
                        if(!roots.containsKey(a.baseForm+":adverb"))
                            roots.put(a.baseForm+":adverb", word);
                        else{
                            String e = roots.get(a.baseForm+":adverb");
                            roots.put(a.baseForm+":adverb", e +"|"+ word);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    merge(word, a);
                }
                if (conjunctionDictionary.containsKey(word)) {
                    Conjunction n = new Conjunction(word);
                    n.howCommon = c;
                    n.commonRank = rank;
                    merge(word, n);
                }
                if (determinerDictionary.containsKey(word)) {
                    Determiner n = new Determiner(word);
                    n.howCommon = c;
                    n.commonRank = rank;
                    merge(word, n);
                }
                if (interjectionDictionary.containsKey(word)) {
                    Interjection n = new Interjection(word);
                    n.howCommon = c;
                    n.commonRank = rank;
                    merge(word, n);
                }
                if (prepositionDictionary.containsKey(word)) {
                    Preposition n = new Preposition(word);
                    n.howCommon = c;
                    n.commonRank = rank;
                    merge(word, n);
                }
                if (pronounDictionary.containsKey(word)) {
                    Pronoun n = new Pronoun(word);
                    n.howCommon = c;
                    n.commonRank = rank;
                    merge(word, n);
                }
                if (quantifierDictionary.containsKey(word)) {
                    Quantifier n = new Quantifier(word);
                    n.howCommon = c;
                    n.commonRank = rank;
                    merge(word, n);
                }
                if(!quantifierDictionary.containsKey(word) && !pronounDictionary.containsKey(word) && !prepositionDictionary.containsKey(word) && !interjectionDictionary.containsKey(word) && !determinerDictionary.containsKey(word) && !conjunctionDictionary.containsKey(word) && !adverbDictionary.containsKey(word) && !adjectiveDictionary.containsKey(word) && !verbDictionary.containsKey(word) && !nounDictionary.containsKey(word)){
                    /*try { 
                        FileWriter fw = new FileWriter("outputs/NATD.txt", true);
                        BufferedWriter bw = new BufferedWriter(fw); 
                        PrintWriter printer = new PrintWriter(bw); 
                        printer.println("Word Frequency: " + word); 
                        printer.close();
                        bw.close();
                        fw.close();
                     } 
                    catch (IOException e) { e.printStackTrace(); }*/
                }
            }
            s.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void colors(){
        try{
            Scanner scan = new Scanner(new File("inputs/colors.txt"));
            while(scan.hasNextLine()){
                String [] split = scan.nextLine().split("\t");
                String word = split[0];
                word = word.toLowerCase();
                /*for(int x = 0;x<split.length-1;x++){
                    word += split[x];
                }*/
                
                if(adjectiveDictionary.containsKey(word)){
                    System.out.println(word);
                    Adjective a = new Adjective(word);
                    a.adjectiveOrderID=7;
                    merge(word,a);
                }
                else{
                    /*try { FileWriter fw = new FileWriter("outputs/NATD.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw); PrintWriter printer =
                    new PrintWriter(bw); printer.println("Colors: " + word); printer.close();
                    bw.close(); fw.close();
                     
                     } 
                    catch (IOException e) { e.printStackTrace(); }*/
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void verbnet(){
        try{
            String pathToVerbnet = "inputs/verbnet";
            URL url = new URL("file", null, pathToVerbnet);
            //constructs index and opens it
            IVerbIndex index = new VerbIndex(url);
            index.open();
            // look up a verb class and print out some info
            Iterator it = index.iterator();
            while(it.hasNext()){
            IVerbClass verb = (IVerbClass) it.next();
            String ID = verb.getID();
            System.out.println(ID);
            String IDtype = verbnetType(ID);
            String format = "\"Type\":\""+IDtype+"\"{\n";
            List l = verb.getFrames();
            for(int x=0;x<l.size();x++){
                IFrame frame = (IFrame) l.get(x);
                FrameType type = frame.getPrimaryType();
                ISemanticDesc semantics = frame.getSemantics();
                List<IPredicateDesc> predicates = semantics.getPredicates();
                String semantic = predicates.toString();
                ISyntaxDesc syntaxes = frame.getSyntax();
                List<ISyntaxArgDesc> pre = syntaxes.getPreVerbDescriptors();
                String presyntax = "";
                for(int i=0;i<pre.size();i++){
                    presyntax += pre.get(i).getValue()+" ";
                }
                List<ISyntaxArgDesc> post = syntaxes.getPostVerbDescriptors();
                String postsyntax = "";
                for(int i=0;i<post.size();i++){
                    postsyntax += post.get(i).getValue()+" ";
                }
                format+="\"frame id\":\""+type.getID()+"\":{\n";
                format+="\"semantics\":\""+semantic+"\",";
                format+="\"presyntax\":\""+presyntax+"\",";
                format+="\"postsyntax\":\""+postsyntax+"\",";
                format+="\n},\n";
            }
            format+="}\n";
            List members = verb.getMembers();
            for(int x=0;x<members.size();x++){
                IMember member = (IMember) members.get(x);
                String word = fixSpaces(member.getName());
                System.out.println(word);
                Verb v = new Verb(word);
                v.wordNetID = member.getGroupings().toString();
                v.verbnet = format;
                try {
                    MorphologyFinder m = new MorphologyFinder(
                            word);
                    m.loadDictionary(verbDictionary);
                    m.breakApart();
                    try {
                        v.baseForm = dictionary.lookupIndexWord(
                                POS.VERB, m.getRoot()).getLemma();
                    }
                    catch (Exception e) {
                        v.baseForm = m.getRoot();
                    }
                    if (m.getTraits().contains("past tense")) {
                        v.tense = "Past";
                    }
                    else if (m.getTraits()
                            .contains("present tense")) {
                        v.tense = "Present";
                    }
                    if(!roots.containsKey(v.baseForm+":verb"))
                        roots.put(v.baseForm+":verb", word);
                    else{
                        String e = roots.get(v.baseForm+":verb");
                        roots.put(v.baseForm+":verb", e +"|"+ word);
                    }
                }
                catch (Exception e) {
                   e.printStackTrace();
                }
                    merge(word,v);
            }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void adjadv(){
        try{
            Scanner s = new Scanner(new File("inputs/ADJADV.txt"));
            String adj = "";
            String adv = "";
            Boolean gradable = null;
            Boolean manner = null;
            while(s.hasNextLine()){
                try{
                String line = s.nextLine();
                System.out.println(line);
                String []split = line.split(":");
                if(split[1].startsWith("ORTH")){
                    if(!adj.equals("") && adjectiveDictionary.containsKey(adj)){
                        Adjective a = new Adjective(adj);
                        if(gradable != null){
                            a.isQualitative = true;
                            a.isClassifying = false;
                        }
                        if(manner != null){
                            a.adjectiveOrderID = 4;
                        }
                        System.out.println(adj);
                        merge(adj,a);
                        adj = "";
                        gradable = null;
                    }
                    else if(!adj.equals("") && !adjectiveDictionary.containsKey(adj)){
                        /*try { FileWriter fw = new FileWriter("outputs/NATD.txt", true);
                        BufferedWriter bw = new BufferedWriter(fw); PrintWriter printer =
                        new PrintWriter(bw); printer.println("ADJADV: " + adj); printer.close();
                        bw.close(); fw.close();
                         
                         } 
                        catch (IOException e) { e.printStackTrace(); }*/
                    }
                    if(!adv.equals("") && adverbDictionary.containsKey(adv)){
                        Adverb a = new Adverb(adv);
                        if(manner != null){
                            a.wordSenseID = 7;
                        }
                        merge(adv,a);
                        adv = "";
                    }
                    else if(!adv.equals("") && !adverbDictionary.containsKey(adv)){
                            /*try { FileWriter fw = new FileWriter("outputs/NATD.txt", true);
                            BufferedWriter bw = new BufferedWriter(fw); PrintWriter printer =
                            new PrintWriter(bw); printer.println("ADJADV: " + adv); printer.close();
                            bw.close(); fw.close();
                             
                             } 
                            catch (IOException e) { e.printStackTrace(); }*/
                    }
                    adj = split[1].substring(6, split[1].length()-1);
                    if(adj.endsWith("\"")){
                        adj = adj.substring(0, adj.length()-1);
                    }
                }
                else if(split[1].startsWith("ADV")){
                    adv = split[1].substring(5, split[1].length()-1);
                    if(adv.endsWith("\"")){
                        adv = adv.substring(0, adv.length()-1);
                    }
                }
                else if(split[1].startsWith("FEATURES")){
                    while(!line.contains("))")){
                        if(line.contains("GRADABLE")){
                            gradable = true;
                        }
                        if(line.contains("MANNER")){
                            manner = true;
                        }
                        line = s.nextLine();
                    }
                    if(line.contains("GRADABLE")){
                        gradable = true;
                    }
                    if(gradable == null){
                        gradable = false;
                    }
                    if(line.contains("MANNER")){
                        manner = true;
                    }
                }
            }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            s.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void propBank(){
        File path = new File("inputs/frames");
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                try {
                    DocumentBuilderFactory factory = DocumentBuilderFactory
                            .newInstance();
                    factory.setValidating(true);
                    factory.setIgnoringElementContentWhitespace(true);
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    File frame = files[i];
                    Document doc = builder.parse(frame);
                    NodeList roleset = doc.getElementsByTagName("roleset");
                    NodeList predicate = doc.getElementsByTagName("predicate");
                    /*if (predicate.item(0).getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) predicate.item(0);
                        System.out.println(eElement.getAttribute("lemma")+":");
                    }*/
                    for(int x=0;x<roleset.getLength();x++){
                        String word = "";
                        String propbank = "";
                        String pos = "";
                        if (roleset.item(x).getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) roleset.item(x);
                            word = eElement.getAttribute("id").split("\\.")[0];
                            propbank += "id:" + eElement.getAttribute("id")+"{";
                        }
                        word = fixSpaces(word);
                        System.out.println(word);
                        NodeList rolesetchildren = roleset.item(x).getChildNodes();
                        NodeList roles = null;
                        NodeList alias = null;
                        for(int c=0;c<rolesetchildren.getLength();c++){
                            if(rolesetchildren.item(c).getNodeName().equals("roles")){
                                roles = rolesetchildren.item(c).getChildNodes();
                            }
                            if(rolesetchildren.item(c).getNodeName().equals("aliases")){
                                alias = rolesetchildren.item(c).getChildNodes();
                            }
                        }
                        for(int c=0;c<roles.getLength();c++){
                            if (roles.item(c).getNodeType() == Node.ELEMENT_NODE) {
                                Element eElement = (Element) roles.item(c);
                                if(eElement.getNodeName().equals("role")){
                                propbank+=" descr:" + eElement.getAttribute("descr") + ",";
                                if(eElement.hasChildNodes()){
                                    NodeList l = (NodeList) eElement.getChildNodes();
                                    for(int z=0;z<l.getLength();z++){
                                        Element e = (Element) l.item(z);
                                        propbank+="{ vnrole:{ vncld:" + e.getAttribute("vncls")+ ", vntheta:" + e.getAttribute("vntheta") + "}},";
                                    }
                                }
                                propbank+=" f:" + eElement.getAttribute("f") + ",";
                                propbank+=" n:" + eElement.getAttribute("n") + ",";
                                }
                            }
                        }
                        for(int c=0;c<alias.getLength();c++){
                            propbank+=" alias:" + alias.item(c).getTextContent()+"}";
                            if (alias.item(c).getNodeType() == Node.ELEMENT_NODE) {
                                Element eElement = (Element) alias.item(c);
                                pos = eElement.getAttribute("pos");
                                
                                if(pos.equals("v") && verbDictionary.containsKey(word)){
                                    Verb v = new Verb(word);
                                    v.propbank = propbank;
                                    merge(word, v);
                                }
                                else if(pos.equals("j") && adjectiveDictionary.containsKey(word)){
                                    Adjective v = new Adjective(word);
                                    v.propbank = propbank;
                                    merge(word, v);
                                }
                                else if(pos.equals("n") && nounDictionary.containsKey(word)){
                                    Noun v = new Noun(word);
                                    v.propbank = propbank;
                                    merge(word, v);
                                }
                                else{
                                    /*try { FileWriter fw = new FileWriter("outputs/NATD.txt", true);
                                    BufferedWriter bw = new BufferedWriter(fw); PrintWriter printer =
                                    new PrintWriter(bw); printer.println("PropBank: " + word + " : " + pos); printer.close();
                                    bw.close(); fw.close();
                                     
                                     } 
                                    catch (IOException e) { e.printStackTrace(); }*/
                                }
                            }
                        }
                        
                    }
                    
                    
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void shapes(){
        try{
        Scanner scan = new Scanner(new File("inputs/Shapes.csv"));
        while(scan.hasNextLine()){
            String s = scan.nextLine().split(",")[0];
            Adjective a = new Adjective(s);
            a.adjectiveOrderID = 5;
            merge(s,a);
        }
        scan.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void framenet(){
        try{
        Scanner scan = new Scanner(new File("inputs/fn16lexunits.ttl"));
        while(scan.hasNextLine()){
            String s = scan.nextLine();
            if(s.startsWith("flu:")){
                String [] split = s.split(" ");
                String word = split[0].split(":")[1];
                String frame = split[5].split(":")[1];
                String pos = s.substring(s.length()-4, s.length()-3);
                word = word.split("\\.")[0];
                word = fixSpaces(word);
                frame = fixSpaces(frame);
                System.out.println(word + ":" + frame + ":" + pos);
                if(pos.equals("v")){
                    if(verbDictionary.containsKey(word)){
                        Verb v= new Verb(word);
                        v.frame = frame;
                        merge(word,v);
                    }
                    else{
                        /*try { FileWriter fw = new FileWriter("outputs/NATD.txt", true);
                        BufferedWriter bw = new BufferedWriter(fw); PrintWriter printer =
                        new PrintWriter(bw); printer.println("FrameNet: " + word + " : " + pos); printer.close();
                        bw.close(); fw.close();
                         
                         } 
                        catch (IOException e) { e.printStackTrace(); }*/
                    }
                }
                else if(pos.equals("n")){
                    if(nounDictionary.containsKey(word)){
                        Noun n = new Noun(word);
                        n.frame = frame;
                        merge(word,n);
                    }
                    else{
                        /*try { FileWriter fw = new FileWriter("outputs/NATD.txt", true);
                        BufferedWriter bw = new BufferedWriter(fw); PrintWriter printer =
                        new PrintWriter(bw); printer.println("FrameNet: " + word+ " : " + pos); printer.close();
                        bw.close(); fw.close();
                         
                         } 
                        catch (IOException e) { e.printStackTrace(); }*/
                    }
                }
                else if(pos.equals("a")){
                    if(adjectiveDictionary.containsKey(word)){
                        Adjective a = new Adjective(word);
                        a.frame = frame;
                    }
                    else{
                        /*try { FileWriter fw = new FileWriter("outputs/NATD.txt", true);
                        BufferedWriter bw = new BufferedWriter(fw); PrintWriter printer =
                        new PrintWriter(bw); printer.println("FrameNet: " + word+ " : " + pos); printer.close();
                        bw.close(); fw.close();
                         
                         } 
                        catch (IOException e) { e.printStackTrace(); }*/
                    }
                }
                else{
                    /*try { FileWriter fw = new FileWriter("outputs/NATD.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw); PrintWriter printer =
                    new PrintWriter(bw); printer.println("FrameNet: " + word+ " : " + pos); printer.close();
                    bw.close(); fw.close();
                     
                     } 
                    catch (IOException e) { e.printStackTrace(); }*/
                }
            }
        }
        }
        catch(Exception e){
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
                || (s.endsWith("ty") && !s.endsWith("plenty"))
                || s.endsWith("trillion") || s.endsWith("thousand")
                || s.endsWith("quadrillion") || s.endsWith("zillion")
                || s.equals("ane") || s.equals("fourscore")
                || s.endsWith("hundred") || s.endsWith("million")
                || s.endsWith("threescore")) {
            return new Quantifier(s);
        }
        else {
            /*
             * try { FileWriter fw = new FileWriter("outputs/infs.txt", true);
             * BufferedWriter bw = new BufferedWriter(fw); PrintWriter printer =
             * new PrintWriter(bw); printer.println(s); printer.close();
             * bw.close(); fw.close();
             * 
             * } catch (IOException e) { e.printStackTrace(); }
             */
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
        else if(pos.equals("firstName")){
            return firstNameDictionary;
        }
        else if(pos.equals("lastName")){
            return lastNameDictionary;
        }
        else if(pos.equals("properPlace")){
            return properPlaceDictionary;
        }
        else
            return null;
    }
    
    public static String fixSpaces(String s){
        String temp = "";
        for(int i=0;i<s.length();i++){
            if(s.charAt(i) == '_'){
                temp += " ";
            }
            else{
                temp += s.charAt(i);
            }
        }
        return temp;
    }
    public static String verbnetType(String s){
        String type = "";
        String[]split = s.split("-");
        String[] num = split[1].split("\\.");
        if(num[0].equals("9")){
            type = "putting";
        }
        else if(num[0].equals("10")){
            type = "removing";
        }
        else if(num[0].equals("11")){
            type = "sending/carrying";
        }
        else if(num[0].equals("12")){
            type = "push/pull";
        }
        else if(num[0].equals("13")){
            type = "change of possesion";
        }
        else if(num[0].equals("14")){
            type = "learn";
        }
        else if(num[0].equals("15")){
            type = "hold/keep";
        }
        else if(num[0].equals("16")){
            type = "concealment";
        }
        else if(num[0].equals("17")){
            type = "throwing";
        }
        else if(num[0].equals("18")){
            type = "contact by impact";
        }
        else if(num[0].equals("19")){
            type = "poke";
        }
        else if(num[0].equals("20")){
            type = "touch";
        }
        else if(num[0].equals("21")){
            type = "cutting";
        }
        else if(num[0].equals("22")){
            type = "combining/attaching";
        }
        else if(num[0].equals("23")){
            type = "seperating/disassembling";
        }
        else if(num[0].equals("24")){
            type = "coloring";
        }
        else if(num[0].equals("25")){
            type = "image creation";
        }
        else if(num[0].equals("26")){
            type = "creation/transformation";
        }
        else if(num[0].equals("27")){
            type = "engender";
        }
        else if(num[0].equals("28")){
            type = "calve";
        }
        else if(num[0].equals("29")){
            type = "w/ predicative compliments";
        }
        else if(num[0].equals("30")){
            type = "perception";
        }
        else if(num[0].equals("31")){
            type = "psychological state";
        }
        else if(num[0].equals("32")){
            type = "desire";
        }
        else if(num[0].equals("33")){
            type = "judgement";
        }
        else if(num[0].equals("34")){
            type = "assessment";
        }
        else if(num[0].equals("35")){
            type = "searching";
        }
        else if(num[0].equals("36")){
            type = "social interaction";
        }
        else if(num[0].equals("37")){
            type = "communication";
        }
        else if(num[0].equals("38")){
            type = "sounds made by animals";
        }
        else if(num[0].equals("39")){
            type = "ingesting";
        }
        else if(num[0].equals("40")){
            type = "involving the body";
        }
        else if(num[0].equals("41")){
            type = "grooming and bodily care";
        }
        else if(num[0].equals("42")){
            type = "killing";
        }
        else if(num[0].equals("43")){
            type = "emission";
        }
        else if(num[0].equals("44")){
            type = "destroy";
        }
        else if(num[0].equals("45")){
            type = "change of state";
        }
        else if(num[0].equals("46")){
            type = "lodge";
        }
        else if(num[0].equals("47")){
            type = "existence";
        }
        else if(num[0].equals("48")){
            type = "appearance/disappearance/occurrence";
        }
        else if(num[0].equals("49")){
            type = "body-internal motion";
        }
        else if(num[0].equals("50")){
            type = "assumin a position";
        }
        else if(num[0].equals("51")){
            type = "motion";
        }
        else if(num[0].equals("52")){
            type = "avoid";
        }
        else if(num[0].equals("53")){
            type = "lingering/rushing";
        }
        else if(num[0].equals("54")){
            type = "measure";
        }
        else if(num[0].equals("55")){
            type = "aspectual";
        }
        else if(num[0].equals("56")){
            type = "weekend";
        }
        else if(num[0].equals("57")){
            type = "weather";
        }
        else if(num[0].equals("58")){
            type = "urging/begging";
        }
        else if(num[0].equals("59")){
            type = "force";
        }
        else if(num[0].equals("60")){
            type = "order";
        }
        else if(num[0].equals("61")){
            type = "try";
        }
        else if(num[0].equals("62")){
            type = "wish";
        }
        else if(num[0].equals("63")){
            type = "enforce";
        }
        else if(num[0].equals("64")){
            type = "allow";
        }
        else if(num[0].equals("65")){
            type = "admit";
        }
        else if(num[0].equals("66")){
            type = "consume";
        }
        else if(num[0].equals("67")){
            type = "forbid";
        }
        else if(num[0].equals("68")){
            type = "pay";
        }
        else if(num[0].equals("69")){
            type = "refrain";
        }
        else if(num[0].equals("70")){
            type = "rely";
        }
        else if(num[0].equals("71")){
            type = "conspire";
        }
        else if(num[0].equals("72")){
            type = "help";
        }
        else if(num[0].equals("73")){
            type = "cooperate";
        }
        else if(num[0].equals("74")){
            type = "succeed";
        }
        else if(num[0].equals("75")){
            type = "neglect";
        }
        else if(num[0].equals("76")){
            type = "limit";
        }
        else if(num[0].equals("77")){
            type = "approve";
        }
        else if(num[0].equals("78")){
            type = "indicate";
        }
        else if(num[0].equals("79")){
            type = "dedicate";
        }
        else if(num[0].equals("80")){
            type = "free";
        }
        else if(num[0].equals("81")){
            type = "suspect";
        }
        else if(num[0].equals("82")){
            type = "withdraw";
        }
        else if(num[0].equals("83")){
            type = "cope";
        }
        else if(num[0].equals("84")){
            type = "discover";
        }
        else if(num[0].equals("85")){
            type = "defend";
        }
        else if(num[0].equals("86")){
            type = "correlating/relating";
        }
        else if(num[0].equals("87")){
            type = "focusing/comprehending";
        }
        else if(num[0].equals("88")){
            type = "caring/empathizing";
        }
        else if(num[0].equals("89")){
            type = "settle";
        }
        else if(num[0].equals("90")){
            type = "exceed";
        }
        else if(num[0].equals("91")){
            type = "matter";
        }
        else if(num[0].equals("92")){
            type = "confine";
        }
        else if(num[0].equals("93")){
            type = "adopt";
        }
        else if(num[0].equals("94")){
            type = "risk";
        }
        else if(num[0].equals("95")){
            type = "acquiesce";
        }
        else if(num[0].equals("96")){
            type = "addict";
        }
        else if(num[0].equals("97")){
            type = "basing/deducing";
        }
        else if(num[0].equals("98")){
            type = "confront";
        }
        else if(num[0].equals("99")){
            type = "ensure";
        }
        else if(num[0].equals("100")){
            type = "own";
        }
        else if(num[0].equals("101")){
            type = "patent";
        }
        else if(num[0].equals("102")){
            type = "promote";
        }
        else if(num[0].equals("103")){
            type = "require";
        }
        else if(num[0].equals("104")){
            type = "spending time";
        }
        else if(num[0].equals("105")){
            type = "use";
        }
        else if(num[0].equals("106")){
            type = "void";
        }
        else if(num[0].equals("107")){
            type = "involve";
        }
        else if(num[0].equals("108")){
            type = "multiply";
        }
        else if(num[0].equals("109")){
            type = "seem";
        }
        return type;
    }
}
