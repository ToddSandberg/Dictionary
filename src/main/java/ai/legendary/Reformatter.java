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
                        || curr.endsWith("or")) && !curr.endsWith(word)
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
                        || curr.endsWith("meister")) && !curr.endsWith(word)
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
                if ((curr.endsWith("ee")) && !curr.endsWith(word)
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
                        && !curr.endsWith(word)
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
                        || curr.endsWith("ery")) && !curr.endsWith(word)
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
                        && !curr.endsWith(word)
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
                        || curr.endsWith("ia")) && !curr.endsWith(word)
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
                        && !curr.endsWith(word)
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
     * Converts the word to have prefix meaning in
     * 
     * @return new word
     */
    public String in_intraPrefix() {
        String newwords = "";
        ArrayList<String> nouns = acc.changePOS(word, pos, "noun");
        ArrayList<String> adverbs = acc.changePOS(word, pos, "adverb");
        ArrayList<String> adjectives = acc.changePOS(word, pos, "adjective");
        ArrayList<String> verbs = acc.changePOS(word, pos, "verb");
        for (int x = 0; x < nouns.size(); x++) {
            String curr = nouns.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("in") || curr.startsWith("intra")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("in") || curr.startsWith("intra")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("in") || curr.startsWith("intra")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("in") || curr.startsWith("intra")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        
        return newwords;
    }
    
    /**
     * Converts the word to have prefix meaning out
     * 
     * @return new word
     */
    public String out_extraPrefix() {
        String newwords = "";
        ArrayList<String> nouns = acc.changePOS(word, pos, "noun");
        ArrayList<String> adverbs = acc.changePOS(word, pos, "adverb");
        ArrayList<String> adjectives = acc.changePOS(word, pos, "adjective");
        ArrayList<String> verbs = acc.changePOS(word, pos, "verb");
        for (int x = 0; x < nouns.size(); x++) {
            String curr = nouns.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("out") || curr.startsWith("extra")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("out") || curr.startsWith("extra")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("out") || curr.startsWith("extra")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("out") || curr.startsWith("extra")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        
        return newwords;
    }
    
    /**
     * Converts the word to have prefix meaning around
     * 
     * @return new word
     */
    public String aroundPrefix() {
        String newwords = "";
        ArrayList<String> nouns = acc.changePOS(word, pos, "noun");
        ArrayList<String> adverbs = acc.changePOS(word, pos, "adverb");
        ArrayList<String> adjectives = acc.changePOS(word, pos, "adjective");
        ArrayList<String> verbs = acc.changePOS(word, pos, "verb");
        for (int x = 0; x < nouns.size(); x++) {
            String curr = nouns.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("by") || curr.startsWith("circum") || curr.startsWith("para") || curr.startsWith("peri")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("by") || curr.startsWith("circum") || curr.startsWith("para") || curr.startsWith("peri")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("by") || curr.startsWith("circum") || curr.startsWith("para") || curr.startsWith("peri")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("by") || curr.startsWith("circum") || curr.startsWith("para") || curr.startsWith("peri")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        
        return newwords;
    }
    
    /**
     * Converts the word to have prefix meaning off
     * 
     * @return new word
     */
    public String offPrefix() {
        String newwords = "";
        ArrayList<String> nouns = acc.changePOS(word, pos, "noun");
        ArrayList<String> adverbs = acc.changePOS(word, pos, "adverb");
        ArrayList<String> adjectives = acc.changePOS(word, pos, "adjective");
        ArrayList<String> verbs = acc.changePOS(word, pos, "verb");
        for (int x = 0; x < nouns.size(); x++) {
            String curr = nouns.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("off")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("off")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("off")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("off")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        
        return newwords;
    }
    
    /**
     * Converts the word to have prefix meaning on
     * 
     * @return new word
     */
    public String on_epiPrefix() {
        String newwords = "";
        ArrayList<String> nouns = acc.changePOS(word, pos, "noun");
        ArrayList<String> adverbs = acc.changePOS(word, pos, "adverb");
        ArrayList<String> adjectives = acc.changePOS(word, pos, "adjective");
        ArrayList<String> verbs = acc.changePOS(word, pos, "verb");
        for (int x = 0; x < nouns.size(); x++) {
            String curr = nouns.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("on") || curr.startsWith("epi")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("on") || curr.startsWith("epi")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("on") || curr.startsWith("epi")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("on") || curr.startsWith("epi")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        
        return newwords;
    }
    
    /**
     * Converts the word to have prefix meaning together
     * 
     * @return new word
     */
    public String togetherPrefix() {
        String newwords = "";
        ArrayList<String> nouns = acc.changePOS(word, pos, "noun");
        ArrayList<String> adverbs = acc.changePOS(word, pos, "adverb");
        ArrayList<String> adjectives = acc.changePOS(word, pos, "adjective");
        ArrayList<String> verbs = acc.changePOS(word, pos, "verb");
        for (int x = 0; x < nouns.size(); x++) {
            String curr = nouns.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("inter") || (curr.startsWith("co") && !curr.startsWith("counter"))) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("inter") || (curr.startsWith("co")&& !curr.startsWith("counter"))) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("inter") || (curr.startsWith("co")&& !curr.startsWith("counter"))) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("inter") || (curr.startsWith("co")&& !curr.startsWith("counter"))) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        
        return newwords;
    }
    
    /**
     * Converts the word to have prefix meaning negative
     * 
     * @return new word
     */
    public String negativePrefix() {
        String newwords = "";
        ArrayList<String> nouns = acc.changePOS(word, pos, "noun");
        ArrayList<String> adverbs = acc.changePOS(word, pos, "adverb");
        ArrayList<String> adjectives = acc.changePOS(word, pos, "adjective");
        ArrayList<String> verbs = acc.changePOS(word, pos, "verb");
        for (int x = 0; x < nouns.size(); x++) {
            String curr = nouns.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("in") || curr.startsWith("un") || curr.startsWith("non") || curr.startsWith("dis") || curr.startsWith("de") || curr.startsWith("a") || curr.startsWith("an") || curr.startsWith("mis") || curr.startsWith("mal")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("in") || curr.startsWith("un") || curr.startsWith("non") || curr.startsWith("dis") || curr.startsWith("de") || curr.startsWith("a") || curr.startsWith("an") || curr.startsWith("mis") || curr.startsWith("mal")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("in") || curr.startsWith("un") || curr.startsWith("non") || curr.startsWith("dis") || curr.startsWith("de") || curr.startsWith("a") || curr.startsWith("an") || curr.startsWith("mis") || curr.startsWith("mal")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("in") || curr.startsWith("un") || curr.startsWith("non") || curr.startsWith("dis") || curr.startsWith("de") || curr.startsWith("a") || curr.startsWith("an") || curr.startsWith("mis") || curr.startsWith("mal")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        
        return newwords;
    }
    
    /**
     * Converts the word to have prefix meaning above
     * 
     * @return new word
     */
    public String abovePrefix() {
        String newwords = "";
        ArrayList<String> nouns = acc.changePOS(word, pos, "noun");
        ArrayList<String> adverbs = acc.changePOS(word, pos, "adverb");
        ArrayList<String> adjectives = acc.changePOS(word, pos, "adjective");
        ArrayList<String> verbs = acc.changePOS(word, pos, "verb");
        for (int x = 0; x < nouns.size(); x++) {
            String curr = nouns.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("up") || curr.startsWith("over") || curr.startsWith("super") || curr.startsWith("supra") || curr.startsWith("sur") || curr.startsWith("meta") || curr.startsWith("arch") || curr.startsWith("ultra")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("up") || curr.startsWith("over") || curr.startsWith("super") || curr.startsWith("supra") || curr.startsWith("sur") || curr.startsWith("meta") || curr.startsWith("arch") || curr.startsWith("ultra")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("up") || curr.startsWith("over") || curr.startsWith("super") || curr.startsWith("supra") || curr.startsWith("sur") || curr.startsWith("meta") || curr.startsWith("arch") || curr.startsWith("ultra")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("up") || curr.startsWith("over") || curr.startsWith("super") || curr.startsWith("supra") || curr.startsWith("sur") || curr.startsWith("meta") || curr.startsWith("arch") || curr.startsWith("ultra")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        
        return newwords;
    }
    
    /**
     * Converts the word to have prefix meaning before
     * 
     * @return new word
     */
    public String beforePrefix() {
        String newwords = "";
        ArrayList<String> nouns = acc.changePOS(word, pos, "noun");
        ArrayList<String> adverbs = acc.changePOS(word, pos, "adverb");
        ArrayList<String> adjectives = acc.changePOS(word, pos, "adjective");
        ArrayList<String> verbs = acc.changePOS(word, pos, "verb");
        for (int x = 0; x < nouns.size(); x++) {
            String curr = nouns.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("ante") || curr.startsWith("fore") || curr.startsWith("pre") || curr.startsWith("retro")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("ante") || curr.startsWith("fore") || curr.startsWith("pre") || curr.startsWith("retro")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("ante") || curr.startsWith("fore") || curr.startsWith("pre") || curr.startsWith("retro")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("ante") || curr.startsWith("fore") || curr.startsWith("pre") || curr.startsWith("retro")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        
        return newwords;
    }
    
    /**
     * Converts the word to have prefix meaning below
     * 
     * @return new word
     */
    public String belowPrefix() {
        String newwords = "";
        ArrayList<String> nouns = acc.changePOS(word, pos, "noun");
        ArrayList<String> adverbs = acc.changePOS(word, pos, "adverb");
        ArrayList<String> adjectives = acc.changePOS(word, pos, "adjective");
        ArrayList<String> verbs = acc.changePOS(word, pos, "verb");
        for (int x = 0; x < nouns.size(); x++) {
            String curr = nouns.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("sub") || curr.startsWith("down") || curr.startsWith("under")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("sub") || curr.startsWith("down") || curr.startsWith("under")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("sub") || curr.startsWith("down") || curr.startsWith("under")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("sub") || curr.startsWith("down") || curr.startsWith("under")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        
        return newwords;
    }
    
    /**
     * Converts the word to have prefix meaning after
     * 
     * @return new word
     */
    public String after_postPrefix() {
        String newwords = "";
        ArrayList<String> nouns = acc.changePOS(word, pos, "noun");
        ArrayList<String> adverbs = acc.changePOS(word, pos, "adverb");
        ArrayList<String> adjectives = acc.changePOS(word, pos, "adjective");
        ArrayList<String> verbs = acc.changePOS(word, pos, "verb");
        for (int x = 0; x < nouns.size(); x++) {
            String curr = nouns.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("after") || curr.startsWith("post")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("after") || curr.startsWith("post")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("after") || curr.startsWith("post")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("after") || curr.startsWith("post")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        
        return newwords;
    }
    
    /**
     * Converts the word to have prefix meaning against
     * 
     * @return new word
     */
    public String againstPrefix() {
        String newwords = "";
        ArrayList<String> nouns = acc.changePOS(word, pos, "noun");
        ArrayList<String> adverbs = acc.changePOS(word, pos, "adverb");
        ArrayList<String> adjectives = acc.changePOS(word, pos, "adjective");
        ArrayList<String> verbs = acc.changePOS(word, pos, "verb");
        for (int x = 0; x < nouns.size(); x++) {
            String curr = nouns.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("counter") || curr.startsWith("contra")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("counter") || curr.startsWith("contra")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("counter") || curr.startsWith("contra")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("counter") || curr.startsWith("contra")) && !curr.startsWith(word)&& !(curr.contains(" ") || curr.contains("-"))) {
                    if (newwords.length() > 1) {
                        newwords += " or " + curr;
                    }
                    else {
                        newwords += curr;
                    }
                }
            }
        }
        
        return newwords;
    }
}
