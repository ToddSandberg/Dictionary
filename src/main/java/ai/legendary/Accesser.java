package ai.legendary;

public class Accesser {
    public static void main(String [] args){
        DictionaryAccess acc = new DictionaryAccess();
        System.out.println(acc.getWordInfo("take"));
        System.out.println(acc.getWordInfo("take", "verb"));
        System.out.println(acc.getMultipleWordInfo("I like big pizza"));
    }
}
