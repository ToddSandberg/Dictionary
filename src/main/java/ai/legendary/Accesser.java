package ai.legendary;

public class Accesser {
    public static void main(String [] args){
        DictionaryAccess acc = new DictionaryAccess();
        System.out.println(acc.getWordInfo("abandon"));
        //System.out.println(acc.changePOS("disgusting", "adjective", "adjective").toString());
        //System.out.println(acc.changePOS("forgivable", "adjective", "verb").toString());
        //System.out.println(acc.changePOS("cat", "noun", "adjective").toString());
        //System.out.println(acc.getWordInfo("gave"));
        //System.out.println(acc.getWordInfo("hugged"));
        //System.out.println(acc.changeVerbTense("hug", "Past"));
        //System.out.println(acc.clean("bob gave him a hug"));
        //System.out.println(acc.getMultipleWordInfo("I like big pizza"));
        //System.out.println(acc.getMultipleWordInfo("you are a person"));
        //System.out.println(acc.getMultipleWordInfo("there must be a bath telephone and TV in the room"));
        //System.out.println(acc.getMultipleWordInfo("what is going on ?"));
        //HashMap<Long,String> temp = acc.getMostFrequent();
        //acc.sortAndPrintHashMap(temp);
    }
}
