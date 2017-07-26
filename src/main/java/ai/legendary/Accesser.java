package ai.legendary;

import javax.swing.JOptionPane;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.dictionary.Dictionary;

public class Accesser {
    public static void main(String [] args){
        /*try {
            Dictionary dictionary = Dictionary.getDefaultResourceInstance();
            System.out.print(dictionary
            .lookupIndexWord(POS.NOUN,
                    "discuss")
            .getLemma());
        }
        catch (JWNLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        DictionaryAccess acc = new DictionaryAccess();
        /*MorphologyFinder mf = new MorphologyFinder("discussion");
        mf.breakApart();
        System.out.println(mf.getSuffixes());
        System.out.println(mf.getPrefixes());
        System.out.println(mf.getRoot());*/
        /*example usage of getWordInfo*/
        //System.out.println(acc.getWordInfo("befalling"));
        //System.out.println(acc.getWordInfo("alcoholic","adjective"));
        /*Example usage of changePOS*/
        //System.out.println(acc.changePOS("alcohol", "noun", "noun").toString());
        //System.out.println(acc.changePOS("forgivable", "adjective", "noun").toString());
        //System.out.println(acc.changePOS("cat", "noun", "adjective").toString());
        /*Unfinished methods - formats light verbs*/
        //System.out.println(acc.getWordInfo("bath"));
        //System.out.println(acc.changeVerbTense("walk", "Past"));
        //System.out.println(acc.clean("I make the bass drop"));
        //System.out.println(acc.clean("I made the door close"));
        //System.out.println(acc.clean("we did a test of it"));
        //System.out.println(acc.clean("I gave him a bath"));
        //System.out.println(acc.clean("Sam did a revision of his paper"));
        //System.out.println(acc.clean("Jim made a gesture to the door"));
        /*Example usage of getMultipleWordInfo*/
        //System.out.println(acc.getMultipleWordInfo("I like big pizza"));
        //System.out.println(acc.getMultipleWordInfo("you are a person"));
        //System.out.println(acc.getMultipleWordInfo("there must be a bath telephone and TV in the room"));
        //System.out.println(acc.getMultipleWordInfo("what is going on ?"));
        /*Testing Reformatter class*/
        /*Reformatter rf = new Reformatter();
        rf.addWord("warn","verb");
        System.out.println("One who does: "+rf.oneWhoDoes());
        System.out.println("One who practices: "+rf.oneWhoPractices());
        System.out.println("One who recieves: "+rf.oneWhoRecieves());
        System.out.println("Person of: "+rf.personOf());
        System.out.println("Result/Event/State: "+rf.resultEventState());
        System.out.println("Quality: "+rf.quality());
        System.out.println("Collectives/Locations/Behaviors: "+rf.collectivesLocationsBehaviors());
        System.out.println("Fields of Study: "+rf.fieldsOfStudy());*/
        acc.getBaseWords('g');
    }
}
