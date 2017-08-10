package ai.legendary;

import javax.swing.JOptionPane;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.dictionary.Dictionary;
/**
 * Example uses of the DictionaryAccess class
 * @author ToddSandberg
 *
 */
public class Accesser {
    public static void main(String [] args){
        DictionaryAccess acc = new DictionaryAccess(); //create a DictionaryAccess to access most feature of the program
        
        /*MorphologyFinder mf = new MorphologyFinder("realizably"); //access the morphology finder used in the dictionary
        mf.loadDictionary(acc.getNounDictionary());
        mf.loadDictionary(acc.getAdjectiveDictionary());
        mf.loadDictionary(acc.getAdverbDictionary());
        mf.loadDictionary(acc.getVerbDictionary());
        mf.breakApart();
        System.out.println(mf.getSuffixes());
        System.out.println(mf.getPrefixes());
        System.out.println(mf.getRoot());*/
        
        /*example usage of getWordInfo*/
        /*while(true){
            String word = JOptionPane.showInputDialog("enter a word");
            System.out.println(acc.getWordInfo(word));
        }*/
        //System.out.println(acc.getWordInfo("shatter")); //nonspecific pos
        //System.out.println(acc.getWordInfo("alcoholic","adjective")); //specific pos
        
        /*Example usage of changePOS*/
        //System.out.println(acc.changePOS("gaming", "noun", "verb").toString());
        //System.out.println(acc.changePOS("forgivable", "adjective", "noun").toString());
        //System.out.println(acc.changePOS("cat", "noun", "adjective").toString());
        
        /*formats light verbs (does not work 100%)*/
        //System.out.println(acc.changeVerbTense("walk", "Past"));
        //System.out.println(acc.fromLightVerb("I make the bass drop"));
        //System.out.println(acc.fromLightVerb("I gave him a bath"));
        //System.out.println(acc.fromLightVerb("Sam did a revision of his paper"));
        //System.out.println(acc.fromLightVerb("had a bath"));
        //System.out.println(acc.fromLightVerb("I","had","bath",""));
        //System.out.println(acc.fromLightVerb("I", "made", "drop", "the bass"));
        //System.out.println(acc.toLightVerb("bathed"));
        //System.out.println(acc.toLightVerb("I", "bathed", ""));
        //System.out.println(acc.toLightVerb("Sam", "revised", "the paper")); //this version takes the components
        //System.out.println(acc.toLightVerb("I bathed him"));
        //System.out.println(acc.toLightVerb("Sam revised the paper"));
        //System.out.println(acc.toLightVerb("they closed the door")); //example of when it doesnt work
        
        /*Example usage of getMultipleWordInfo*/
        //System.out.println(acc.getMultipleWordInfo("I like big pizza"));
        //System.out.println(acc.getMultipleWordInfo("you are a person"));
        //System.out.println(acc.getMultipleWordInfo("there must be a bath telephone and TV in the room"));
        //System.out.println(acc.getMultipleWordInfo("what is going on ?"));
        
        /*Testing Reformatter class*/
        /*Reformatter rf = new Reformatter();
        while(true){
            String word = JOptionPane.showInputDialog("enter a word");
            String pos = JOptionPane.showInputDialog("enter a part of speech");
            System.out.println(getAllReformatted(word,rf,pos));
        }*/
        
        /*Prints basewords that start with the given character to a file called baseword.csv in outputs*/
        //acc.getBaseWords('g');
    }
    /**
     * Utility method for getting all possible reformats
     * @param word 
     * @param rf
     * @return
     */
    public static String getAllReformatted(String word, Reformatter rf, String pos){
        String result = "";
        rf.addWord(word,pos);
        /* Noun Forms */
        if(rf.oneWhoDoes().length()>1)
            result += "One who does: "+rf.oneWhoDoes() + "\n";
        if(rf.oneWhoPractices().length()>1)
            result += "One who practices: "+rf.oneWhoPractices() + "\n";
        if(rf.oneWhoRecieves().length()>1)
            result += "One who recieves: "+rf.oneWhoRecieves() + "\n";
        if(rf.personOf().length()>1)
            result += "Person of: "+rf.personOf() + "\n";
        if(rf.resultEventState().length()>1)
            result += "Result/Event/State: "+rf.resultEventState() + "\n";
        if(rf.quality().length()>1)
            result += "Quality: "+rf.quality() + "\n";
        if(rf.collectivesLocationsBehaviors().length()>1)
            result += "Collectives/Locations/Behaviors: "+rf.collectivesLocationsBehaviors() + "\n";
        if(rf.fieldsOfStudy().length()>1)
            result += "Fields of Study: "+rf.fieldsOfStudy() + "\n";
        /* Prefixes */
        if(rf.in_intraPrefix().length()>1)
            result += "In Prefixes: "+rf.in_intraPrefix() + "\n";
        if(rf.out_extraPrefix().length()>1)
            result += "Out Prefixes: "+rf.out_extraPrefix() + "\n";
        if(rf.offPrefix().length()>1)
            result += "Off Prefixes: "+rf.offPrefix() + "\n";
        if(rf.on_epiPrefix().length()>1)
            result += "On Prefixes: "+rf.on_epiPrefix() + "\n";
        if(rf.togetherPrefix().length()>1)
            result += "Together Prefixes: "+rf.togetherPrefix() + "\n";
        if(rf.negativePrefix().length()>1)
            result += "Negatives: "+rf.negativePrefix() + "\n";
        if(rf.abovePrefix().length()>1)
            result += "Above Prefixes: "+rf.abovePrefix() + "\n";
        if(rf.belowPrefix().length()>1)
            result += "Below Prefixes: "+rf.belowPrefix() + "\n";
        if(rf.beforePrefix().length()>1)
            result += "Before Prefixes: "+rf.beforePrefix() + "\n";
        if(rf.after_postPrefix().length()>1)
            result += "After Prefixes: "+rf.after_postPrefix() + "\n";
        if(rf.againstPrefix().length()>1)
            result += "Against Prefixes: "+rf.againstPrefix() + "\n";
        if(rf.doAgainPrefix().length()>1)
            result += "Do Again Prefixes: "+rf.doAgainPrefix() + "\n";
        /* Adjective Forms */
        if(rf.resembling().length()>1)
            result += "Resembling: "+rf.resembling() + "\n";
        if(rf.thingOf().length()>1)
            result += "Thing Of: "+rf.thingOf() + "\n";
        if(rf.possessing().length()>1)
            result += "Possessing/Full Of: "+rf.possessing() + "\n";
        if(rf.capableOf().length()>1)
            result += "Capable Of: "+rf.capableOf() + "\n";
        if(rf.without().length()>1)
            result += "Without: "+rf.without() + "\n";
        /*Diminutive*/
        if(rf.diminutives().length()>1)
            result += "Diminutives: "+rf.diminutives() + "\n";
        /*Adverbs*/
        if(rf.adverb_ly().length()>1)
            result += "Adverbs: "+rf.adverb_ly() + "\n";
        /*Verbs*/
        if(rf.toVerb().length()>1)
            result += "Verbs: "+rf.toVerb() + "\n";
        return result;
    }
}
