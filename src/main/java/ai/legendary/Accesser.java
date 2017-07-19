package ai.legendary;

public class Accesser {
    public static void main(String [] args){
        DictionaryAccess acc = new DictionaryAccess();
        /*example usage of getWordInfo*/
        //System.out.println(acc.getWordInfo("play"));
        //System.out.println(acc.getWordInfo("play","verb"));
        /*Example usage of changePOS*/
        //System.out.println(acc.changePOS("got", "verb", "noun").toString());
        //System.out.println(acc.changePOS("forgivable", "adjective", "noun").toString());
        //System.out.println(acc.changePOS("cat", "noun", "adjective").toString());
        /*Unifinished methods - formats light verbs*/
        //System.out.println(acc.changeVerbTense("hope", "Past"));
        //System.out.println(acc.clean("bob casts a vote"));
        /*Example usage*/
        //System.out.println(acc.getMultipleWordInfo("I like big pizza"));
        //System.out.println(acc.getMultipleWordInfo("you are a person"));
        //System.out.println(acc.getMultipleWordInfo("there must be a bath telephone and TV in the room"));
        //System.out.println(acc.getMultipleWordInfo("what is going on ?"));
        //HashMap<Long,String> temp = acc.getMostFrequent();
        //acc.sortAndPrintHashMap(temp);
    }
}
