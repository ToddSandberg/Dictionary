package ai.legendary;

import java.util.ArrayList;
/**
 * This method finds the base word of intended word, then adds suffixes and prefixes to change various features, such as part of speech and meaning. It only returns the words that are in the dictionary. Creates a DictionaryAccess class on initialization, which loads 
 * everything into HashMaps, so try to only initialize it once.
 * @author ToddSandberg
 * @version 8/10/2017
 */
public class Reformatter {
    DictionaryAccess acc = new DictionaryAccess();
    String word = "";
    String pos = "";

    public Reformatter() {

    }
    /**
     * Adds the word to the Reformatter class which allows conversion
     * @param w = the word to be reformatted
     * @param p = the part of speech of w
     */
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
        ArrayList<String> suffs = new ArrayList<String>();
        suffs.add("er");
        suffs.add("ant");
        suffs.add("or");
        return checkSuffixesNouns(suffs);
    }

    /**
     * Converts the word to one who practices
     * 
     * @return new word
     */
    public String oneWhoPractices() {
        ArrayList<String> suffs = new ArrayList<String>();
        suffs.add("ist");
        suffs.add("an");
        suffs.add("eer");
        suffs.add("ster");
        suffs.add("meister");
        return checkSuffixesNouns(suffs);
    }

    /**
     * Converts the word to one who receives
     * 
     * @return new word
     */
    public String oneWhoReceives() {
        ArrayList<String> suffs = new ArrayList<String>();
        suffs.add("ee");
        return checkSuffixesNouns(suffs);
    }

    /**
     * Converts the word to person of
     * 
     * @return new word
     */
    public String personOf() {
        ArrayList<String> suffs = new ArrayList<String>();
        suffs.add("ite");
        suffs.add("ese");
        suffs.add("ish");
        return checkSuffixesNouns(suffs);
    }

    /**
     * Converts the word to result/event/state
     * 
     * @return new word
     */
    public String resultEventState() {
        ArrayList<String> suffs = new ArrayList<String>();
        suffs.add("al");
        suffs.add("ance");
        suffs.add("ence");
        suffs.add("ment");
        suffs.add("ancy");
        suffs.add("ency");
        suffs.add("ure");
        suffs.add("ing");
        suffs.add("tion");
        suffs.add("age");
        return checkSuffixesNouns(suffs);
    }

    /**
     * Converts the word to quality (mostly adjective to noun)
     * 
     * @return new word
     */
    public String quality() {
        ArrayList<String> suffs = new ArrayList<String>();
        suffs.add("ness");
        suffs.add("ity");
        return checkSuffixesNouns(suffs);
    }

    /**
     * Converts the word to collectives/locations/behaviors
     * 
     * @return new word
     */
    public String collectivesLocationsBehaviors() {
        ArrayList<String> suffs = new ArrayList<String>();
        suffs.add("dom");
        suffs.add("ship");
        suffs.add("hood");
        suffs.add("ery");
        suffs.add("age");
        suffs.add("ana");
        suffs.add("ia");
        return checkSuffixesNouns(suffs);
    }

    /**
     * Converts the word to fields of study
     * 
     * @return new word
     */
    public String fieldsOfStudy() {
        ArrayList<String> suffs = new ArrayList<String>();
        suffs.add("ism");
        suffs.add("ology");
        return checkSuffixesNouns(suffs);
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
                if ((curr.startsWith("in") || curr.startsWith("intra"))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("in") || curr.startsWith("intra"))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("in") || curr.startsWith("intra"))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("in") || curr.startsWith("intra"))
                        && !curr.startsWith(word)
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
                if ((curr.startsWith("out") || curr.startsWith("extra"))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("out") || curr.startsWith("extra"))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("out") || curr.startsWith("extra"))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("out") || curr.startsWith("extra"))
                        && !curr.startsWith(word)
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
                if ((curr.startsWith("by") || curr.startsWith("circum")
                        || curr.startsWith("para") || curr.startsWith("peri"))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("by") || curr.startsWith("circum")
                        || curr.startsWith("para") || curr.startsWith("peri"))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("by") || curr.startsWith("circum")
                        || curr.startsWith("para") || curr.startsWith("peri"))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("by") || curr.startsWith("circum")
                        || curr.startsWith("para") || curr.startsWith("peri"))
                        && !curr.startsWith(word)
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
                if ((curr.startsWith("off")) && !curr.startsWith(word)
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
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("off")) && !curr.startsWith(word)
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
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("off")) && !curr.startsWith(word)
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
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("off")) && !curr.startsWith(word)
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
                if ((curr.startsWith("on") || curr.startsWith("epi"))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("on") || curr.startsWith("epi"))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("on") || curr.startsWith("epi"))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("on") || curr.startsWith("epi"))
                        && !curr.startsWith(word)
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
                if ((curr.startsWith("inter") || (curr.startsWith("co")
                        && !curr.startsWith("counter")))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("inter") || (curr.startsWith("co")
                        && !curr.startsWith("counter")))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("inter") || (curr.startsWith("co")
                        && !curr.startsWith("counter")))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("inter") || (curr.startsWith("co")
                        && !curr.startsWith("counter")))
                        && !curr.startsWith(word)
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
                if ((curr.startsWith("in")
                        || (curr.startsWith("un") && !curr.startsWith("under"))
                        || curr.startsWith("non") || curr.startsWith("dis")
                        || curr.startsWith("de")
                        || (curr.startsWith("a") && !curr.startsWith("auto"))
                        || curr.startsWith("an") || curr.startsWith("mis")
                        || curr.startsWith("mal")) && !curr.startsWith(word)
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
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("in")
                        || (curr.startsWith("un") && !curr.startsWith("under"))
                        || curr.startsWith("non") || curr.startsWith("dis")
                        || curr.startsWith("de")
                        || (curr.startsWith("a") && !curr.startsWith("auto"))
                        || curr.startsWith("an") || curr.startsWith("mis")
                        || curr.startsWith("mal")) && !curr.startsWith(word)
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
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("in")
                        || (curr.startsWith("un") && !curr.startsWith("under"))
                        || curr.startsWith("non") || curr.startsWith("dis")
                        || curr.startsWith("de")
                        || (curr.startsWith("a") && !curr.startsWith("auto"))
                        || curr.startsWith("an") || curr.startsWith("mis")
                        || curr.startsWith("mal")) && !curr.startsWith(word)
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
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("in")
                        || (curr.startsWith("un") && !curr.startsWith("under"))
                        || curr.startsWith("non") || curr.startsWith("dis")
                        || curr.startsWith("de")
                        || (curr.startsWith("a") && !curr.startsWith("auto"))
                        || curr.startsWith("an") || curr.startsWith("mis")
                        || curr.startsWith("mal")) && !curr.startsWith(word)
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
                if ((curr.startsWith("up") || curr.startsWith("over")
                        || curr.startsWith("super") || curr.startsWith("supra")
                        || curr.startsWith("sur") || curr.startsWith("meta")
                        || curr.startsWith("arch") || curr.startsWith("ultra"))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("up") || curr.startsWith("over")
                        || curr.startsWith("super") || curr.startsWith("supra")
                        || curr.startsWith("sur") || curr.startsWith("meta")
                        || curr.startsWith("arch") || curr.startsWith("ultra"))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("up") || curr.startsWith("over")
                        || curr.startsWith("super") || curr.startsWith("supra")
                        || curr.startsWith("sur") || curr.startsWith("meta")
                        || curr.startsWith("arch") || curr.startsWith("ultra"))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("up") || curr.startsWith("over")
                        || curr.startsWith("super") || curr.startsWith("supra")
                        || curr.startsWith("sur") || curr.startsWith("meta")
                        || curr.startsWith("arch") || curr.startsWith("ultra"))
                        && !curr.startsWith(word)
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
                if ((curr.startsWith("ante") || curr.startsWith("fore")
                        || curr.startsWith("pre") || curr.startsWith("retro"))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("ante") || curr.startsWith("fore")
                        || curr.startsWith("pre") || curr.startsWith("retro"))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("ante") || curr.startsWith("fore")
                        || curr.startsWith("pre") || curr.startsWith("retro"))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("ante") || curr.startsWith("fore")
                        || curr.startsWith("pre") || curr.startsWith("retro"))
                        && !curr.startsWith(word)
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
                if ((curr.startsWith("sub") || curr.startsWith("down")
                        || curr.startsWith("under")) && !curr.startsWith(word)
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
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("sub") || curr.startsWith("down")
                        || curr.startsWith("under")) && !curr.startsWith(word)
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
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("sub") || curr.startsWith("down")
                        || curr.startsWith("under")) && !curr.startsWith(word)
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
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("sub") || curr.startsWith("down")
                        || curr.startsWith("under")) && !curr.startsWith(word)
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
                if ((curr.startsWith("after") || curr.startsWith("post"))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("after") || curr.startsWith("post"))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("after") || curr.startsWith("post"))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("after") || curr.startsWith("post"))
                        && !curr.startsWith(word)
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
                if ((curr.startsWith("counter") || curr.startsWith("contra"))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("counter") || curr.startsWith("contra"))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("counter") || curr.startsWith("contra"))
                        && !curr.startsWith(word)
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
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("counter") || curr.startsWith("contra"))
                        && !curr.startsWith(word)
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

        return newwords;
    }

    /**
     * Converts the word to have prefix meaning do something again
     * 
     * @return new word
     */
    public String doAgainPrefix() {
        String newwords = "";
        ArrayList<String> nouns = acc.changePOS(word, pos, "noun");
        ArrayList<String> adverbs = acc.changePOS(word, pos, "adverb");
        ArrayList<String> adjectives = acc.changePOS(word, pos, "adjective");
        ArrayList<String> verbs = acc.changePOS(word, pos, "verb");
        for (int x = 0; x < nouns.size(); x++) {
            String curr = nouns.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("re")) && !curr.startsWith(word)
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
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("re")) && !curr.startsWith(word)
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
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("re")) && !curr.startsWith(word)
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
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("re")) && !curr.startsWith(word)
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

        return newwords;
    }

    /**
     * Converts the word to resembling
     * 
     * @return new word
     */
    public String resembling() {
        ArrayList<String> suffs = new ArrayList<String>();
        suffs.add("al");
        suffs.add("esque");
        suffs.add("ic");
        suffs.add("oid");
        suffs.add("like");
        suffs.add("ary");
        suffs.add("ish");
        String s = "";
        ArrayList<String> adjectives = acc.changePOS(word, pos, "adjective");
        for(int x=0;x<adjectives.size();x++){
            if(adjectives.get(x).startsWith("quasi") || adjectives.get(x).startsWith("pseudo")){
                s+=adjectives.get(x) + " or ";
            }
        }
        return checkSuffixesAdjectives(suffs);
    }

    /**
     * Converts the word to thing of
     * 
     * @return new word
     */
    public String thingOf() {
        ArrayList<String> suffs = new ArrayList<String>();
        suffs.add("ese");
        suffs.add("an");
        suffs.add("ic");
        return checkSuffixesAdjectives(suffs);
    }

    /**
     * Converts the word to possessing/full of
     * 
     * @return new word
     */
    public String possessing() {
        ArrayList<String> suffs = new ArrayList<String>();
        suffs.add("ous");
        suffs.add("ful");
        suffs.add("some");
        suffs.add("ive");
        return checkSuffixesAdjectives(suffs);
    }

    /**
     * Converts the word to capable of
     * 
     * @return new word
     */
    public String capableOf() {
        ArrayList<String> suffs = new ArrayList<String>();
        suffs.add("able");
        suffs.add("ible");
        return checkSuffixesAdjectives(suffs);
    }

    /**
     * Converts the word to without
     * 
     * @return new word
     */
    public String without() {
        ArrayList<String> suffs = new ArrayList<String>();
        suffs.add("less");
        return checkSuffixesAdjectives(suffs);
    }
    /**
     * Converts the word into diminutive form
     * @return new words
     */
    public String diminutives(){
        String newwords = "";
        ArrayList<String> nouns = acc.changePOS(word, pos, "noun");
        ArrayList<String> adverbs = acc.changePOS(word, pos, "adverb");
        ArrayList<String> adjectives = acc.changePOS(word, pos, "adjective");
        ArrayList<String> verbs = acc.changePOS(word, pos, "verb");
        for (int x = 0; x < nouns.size(); x++) {
            String curr = nouns.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("hypo") || curr.startsWith("mini") || curr.startsWith("micro") || curr.startsWith("nano") || curr.endsWith("een") || curr.endsWith("ette") || curr.endsWith("ie") || curr.endsWith("kin") || curr.endsWith("let") || curr.endsWith("ling") || curr.endsWith("ish"))
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
        for (int x = 0; x < adverbs.size(); x++) {
            String curr = adverbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("hypo") || curr.startsWith("mini") || curr.startsWith("micro") || curr.startsWith("nano") || curr.endsWith("een") || curr.endsWith("ette") || curr.endsWith("ie") || curr.endsWith("kin") || curr.endsWith("let") || curr.endsWith("ling")||curr.endsWith("ish"))
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
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("hypo") || curr.startsWith("mini") || curr.startsWith("micro") || curr.startsWith("nano") || curr.endsWith("een") || curr.endsWith("ette") || curr.endsWith("ie") || curr.endsWith("kin") || curr.endsWith("let") || curr.endsWith("ling")||curr.endsWith("ish"))
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
        for (int x = 0; x < verbs.size(); x++) {
            String curr = verbs.get(x);
            if (!Character.isUpperCase(curr.charAt(0))) {
                if ((curr.startsWith("hypo") || curr.startsWith("mini") || curr.startsWith("micro") || curr.startsWith("nano") || curr.endsWith("een") || curr.endsWith("ette") || curr.endsWith("ie") || curr.endsWith("kin") || curr.endsWith("let") || curr.endsWith("ling")||curr.endsWith("ish"))
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

        return newwords;
    }
    
    /**
     * Converts to the adverb form of the word
     * @return the adverb form of the word
     */
    public String adverb_ly(){
        String newwords = "";
        ArrayList<String> adverbs = acc.changePOS(word, pos, "adverb");
        for(int x=0;x<adverbs.size();x++){
            if(adverbs.get(x).endsWith("ly")){
                if (newwords.length() > 1) {
                    newwords += " or " + adverbs.get(x);
                }
                else {
                    newwords += adverbs.get(x);
                }
            }
        }
        return newwords;
    }
    
    /**
     * Converts to the verb form of the word
     * @return the verb form of the word
     */
    public String toVerb(){
        String newwords = "";
        ArrayList<String> adverbs = acc.changePOS(word, pos, "verb");
        for(int x=0;x<adverbs.size();x++){
            if(!adverbs.get(x).startsWith("no verbs relating")){
                if (newwords.length() > 1) {
                    newwords += " or " + adverbs.get(x);
                }
                else {
                    newwords += adverbs.get(x);
                }
            }
        }
        return newwords;
    }
    
    /**
     * Checks the given suffixes
     * @param suffs = suffixes to be checked
     * @return string of words found
     */
    public String checkSuffixesAdjectives(ArrayList<String> suffs) {
        String newwords = "";
        ArrayList<String> adjectives = acc.changePOS(word, pos, "adjective");
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            for (int i = 0; i < suffs.size(); i++) {
                /*if (!Character.isUpperCase(curr.charAt(0))) {*/
                    if ((curr.endsWith(suffs.get(i))) && !curr.endsWith(word)
                            && curr.startsWith(word)
                            && !(curr.contains(" ") || curr.contains("-"))) {
                        if (newwords.length() > 1) {
                            newwords += " or " + curr;
                        }
                        else {
                            newwords += curr;
                        }
                    }
                //}
            }
        }
        return newwords;
    }
    /**
     * Checks the given suffixes
     * @param suffs = Suffixes to be checked
     * @return string of words found
     */
    public String checkSuffixesNouns(ArrayList<String> suffs) {
        String newwords = "";
        ArrayList<String> adjectives = acc.changePOS(word, pos, "noun");
        for (int x = 0; x < adjectives.size(); x++) {
            String curr = adjectives.get(x);
            for (int i = 0; i < suffs.size(); i++) {
                /*if (!Character.isUpperCase(curr.charAt(0))) {*/
                    if ((curr.endsWith(suffs.get(i))) && !curr.endsWith(word)
                            && curr.startsWith(word)
                            && !(curr.contains(" ") || curr.contains("-"))) {
                        if (newwords.length() > 1) {
                            newwords += " or " + curr;
                        }
                        else {
                            newwords += curr;
                        }
                    }
                //}
            }
        }
        return newwords;
    }
}
