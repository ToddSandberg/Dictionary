package ai.legendary;

public class Accesser {
    public static void main(String [] args){
        DictionaryAccess acc = new DictionaryAccess();
        System.out.println(acc.getWordInfo("take"));
        System.out.println(acc.getWordInfo("booked", "verb"));

        System.out.println(acc.getMultipleWordInfo("I like big pizza"));
        System.out.println(acc.getMultipleWordInfo("you are a person"));
        System.out.println(acc.getMultipleWordInfo("there must be a bath telephone and TV in the room"));
        System.out.println(acc.getMultipleWordInfo("what is going on ?"));
    }
}
