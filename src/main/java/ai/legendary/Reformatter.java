package ai.legendary;

import java.util.ArrayList;

public class Reformatter {
    DictionaryAccess acc = new DictionaryAccess();
    String word = "";
    String pos = "";

    public Reformatter() {

    }

    public void addWord(String w, String p) {
        word = w;
        pos = p;
    }

    /**
     * Converts the word to one who does
     * 
     * @return new word
     */
    public String oneWhoDoes() {
        String newwords = "";
        ArrayList<String> nouns = acc.changePOS(word, pos, "noun");
        for (int x = 0; x < nouns.size(); x++) {
            String curr = nouns.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.endsWith("er") || curr.endsWith("ant")
                        || curr.endsWith("or")) && !curr.equals(word)
                        && !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        /*
         * if(newwords.equals("")){ return "No such word in Dictionary"; }
         */
        return newwords;
    }

    /**
     * Converts the word to one who practices
     * 
     * @return new word
     */
    public String oneWhoPractices() {
        String newwords = "";
        ArrayList<String> nouns = acc.changePOS(word, pos, "noun");
        for (int x = 0; x < nouns.size(); x++) {
            String curr = nouns.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.endsWith("ist") || curr.endsWith("an")
                        || curr.endsWith("eer") || curr.endsWith("ster")
                        || curr.endsWith("meister")) && !curr.equals(word)
                        && !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        /*
         * if(newwords.equals("")){ return "No such word in Dictionary"; }
         */
        return newwords;
    }

    /**
     * Converts the word to one who recieves
     * 
     * @return new word
     */
    public String oneWhoRecieves() {
        String newwords = "";
        ArrayList<String> nouns = acc.changePOS(word, pos, "noun");
        for (int x = 0; x < nouns.size(); x++) {
            String curr = nouns.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.endsWith("ee")) && !curr.equals(word)
                        && !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        /*
         * if(newwords.equals("")){ return "No such word in Dictionary"; }
         */
        return newwords;
    }

    /**
     * Converts the word to person of
     * 
     * @return new word
     */
    public String personOf() {
        String newwords = "";
        ArrayList<String> nouns = acc.changePOS(word, pos, "noun");
        for (int x = 0; x < nouns.size(); x++) {
            String curr = nouns.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.endsWith("ite") || curr.endsWith("i")
                        || curr.endsWith("ese") || curr.endsWith("ish"))
                        && !curr.equals(word)
                        && !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        /*
         * if(newwords.equals("")){ return "No such word in Dictionary"; }
         */
        return newwords;
    }

    /**
     * Converts the word to result/event/state
     * 
     * @return new word
     */
    public String resultEventState() {
        String newwords = "";
        ArrayList<String> nouns = acc.changePOS(word, pos, "noun");
        for (int x = 0; x < nouns.size(); x++) {
            String curr = nouns.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.endsWith("al") || curr.endsWith("ance")
                        || curr.endsWith("ence") || curr.endsWith("ancy")
                        || curr.endsWith("ency") || curr.endsWith("ment")
                        || curr.endsWith("ure") || curr.endsWith("ing")
                        || curr.endsWith("ion") || curr.endsWith("age")
                        || curr.endsWith("ery")) && !curr.equals(word)
                        && !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        /*
         * if(newwords.equals("")){ return "No such word in Dictionary"; }
         */
        return newwords;
    }

    /**
     * Converts the word to quality (mostly adjective to noun)
     * 
     * @return new word
     */
    public String quality() {
        String newwords = "";
        ArrayList<String> nouns = acc.changePOS(word, pos, "noun");
        for (int x = 0; x < nouns.size(); x++) {
            String curr = nouns.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.endsWith("ness") || curr.endsWith("ity"))
                        && !curr.equals(word)
                        && !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        /*
         * if(newwords.equals("")){ return "No such word in Dictionary"; }
         */
        return newwords;
    }

    /**
     * Converts the word to collectives/locations/behaviors
     * 
     * @return new word
     */
    public String collectivesLocationsBehaviors() {
        String newwords = "";
        ArrayList<String> nouns = acc.changePOS(word, pos, "noun");
        for (int x = 0; x < nouns.size(); x++) {
            String curr = nouns.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.endsWith("dom") || curr.endsWith("ship")
                        || curr.endsWith("hood") || curr.endsWith("ery")
                        || curr.endsWith("age") || curr.endsWith("ana")
                        || curr.endsWith("ia")) && !curr.equals(word)
                        && !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        /*
         * if(newwords.equals("")){ return "No such word in Dictionary"; }
         */
        return newwords;
    }

    /**
     * Converts the word to fields of study
     * 
     * @return new word
     */
    public String fieldsOfStudy() {
        String newwords = "";
        ArrayList<String> nouns = acc.changePOS(word, pos, "noun");
        for (int x = 0; x < nouns.size(); x++) {
            String curr = nouns.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.endsWith("ism") || curr.endsWith("ology"))
                        && !curr.equals(word)
                        && !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        /*
         * if(newwords.equals("")){ return "No such word in Dictionary"; }
         */
        return newwords;
    }
}
