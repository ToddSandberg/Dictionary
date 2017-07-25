package ai.legendary;

public class Accesser {
    public static void main(String [] args){
        DictionaryAccess acc = new DictionaryAccess();
        /*MorphologyFinder mf = new MorphologyFinder("revision");
        mf.breakApart();
        System.out.println(mf.getSuffixes());
        System.out.println(mf.getPrefixes());
        System.out.println(mf.getRoot());*/
        /*example usage of getWordInfo*/
        System.out.println(acc.getWordInfo("Junior"));
        //System.out.println(acc.getWordInfo("play","verb"));
        /*Example usage of changePOS*/
        //System.out.println(acc.changePOS("revive", "verb", "noun").toString());
        //System.out.println(acc.changePOS("forgivable", "adjective", "noun").toString());
        //System.out.println(acc.changePOS("cat", "noun", "adjective").toString());
        /*Unfinished methods - formats light verbs*/
        //System.out.println(acc.getWordInfo("bath"));
        //System.out.println(acc.changeVerbTense("walk", "Past"));
        /*System.out.println(acc.clean("I make the bass drop"));
        System.out.println(acc.clean("I made the door close"));
        System.out.println(acc.clean("we did a test of it"));
        System.out.println(acc.clean("I gave him a bath"));
        System.out.println(acc.clean("Sam did a revision of his paper"));
        System.out.println(acc.clean("Jim made a gesture to the door"));*/
        /*Example usage of getMultipleWordInfo*/
        //System.out.println(acc.getMultipleWordInfo("I like big pizza"));
        //System.out.println(acc.getMultipleWordInfo("you are a person"));
        //System.out.println(acc.getMultipleWordInfo("there must be a bath telephone and TV in the room"));
        //System.out.println(acc.getMultipleWordInfo("what is going on ?"));
    }
}
