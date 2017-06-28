package ai.legendary;

import java.util.ArrayList;
import java.util.HashMap;

public class MorphologyFinder {
    /**
     * letters of the word that is to be broken apart.
     */
    protected ArrayList<Character> letters = new ArrayList<Character>();
    /**
     * ArrayList of suffixes
     */
    protected ArrayList<String> suffs = new ArrayList<String>();
    /**
     * ArrayList of prefixes
     */
    protected ArrayList<String> prefs = new ArrayList<String>();
    /**
     * Arraylist of traits defined by the suffix
     */
    protected ArrayList<String> traits = new ArrayList<String>();
    /**
     * an english dictionary
     */
    protected HashMap english = new HashMap();

    /**
     * Puts all letter of the word into the letters arraylist
     * 
     * @param w
     *            word to be broken apart.
     */

    public MorphologyFinder(String w) {
        w = w.toLowerCase();
        for (int i = 0; i < w.length(); i++) {
            letters.add(w.charAt(i));
        }
    }

    /**
     * load dictionary from a file The default will not provide a backoff check
     */
    public void loadDictionary(HashMap temp) {
        english = temp;
    }

    /**
     * Call's all steps to break apart the word.
     * 
     * @return all info on the word in string format;
     */
    public String breakApart() {
        if (!letters.contains(" ")) {
            stepOne();
            stepTwo();
            stepThree();
            stepFour();
            String s = "Word:";
            for (int x = 0; x < letters.size(); x++) {
                s += letters.get(x);
            }
            for (int i = 0; i < suffs.size(); i++) {
                s += " Suffix " + (i + 1) + ": " + suffs.get(i);
            }
            for (int i = 0; i < prefs.size(); i++) {
                s += " Prefix " + (i + 1) + ": " + prefs.get(i);
            }
            for (int i = 0; i < traits.size(); i++) {
                s += " Trait " + (i + 1) + ": " + traits.get(i);
            }
            return s;
        }
        else {
            return "";
        }
    }

    /**
     * 
     * @return the root of the word
     */
    public String getRoot() {
        String s = "";
        for (int x = 0; x < letters.size(); x++) {
            s += letters.get(x);
        }
        return s;
    }

    /**
     * 
     * @return all suffixes removed from the word to get the root
     */
    public ArrayList<String> getSuffixes() {
        return suffs;
    }

    /**
     * 
     * @return all prefixes removed from the word to get the root
     */
    public ArrayList<String> getPrefixes() {
        return prefs;
    }

    /**
     * 
     * @return all traits saved from removing suffixes and prefixes
     */
    public ArrayList<String> getTraits() {
        return traits;
    }

    /**
     * Check if the letters array ends in indicated string
     * 
     * @param s
     *            does letters end in this string
     * @return if letters ends in String
     */
    private boolean ends(String s) {
        if (s.length() < letters.size()) {
            for (int i = 1; i <= s.length(); i++) {
                if (letters.get(letters.size() - i) != s
                        .charAt(s.length() - i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Check if the word begins with indicated string
     */
    private boolean begins(String s) {
        if (s.length() < letters.size()) {
            for (int i = 0; i < s.length(); i++) {
                if (letters.get(i) != s.charAt(i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * remove i elements from the front of letters arraylist
     * 
     * @param i
     */
    private void removeF(int i) {
        if (i < letters.size()) {
            for (int x = 0; x < i; x++) {
                letters.remove(0);
            }
        }
    }

    /**
     * Check if the given letter is a consonant
     * 
     * @param i
     *            given index of arraylist
     * @return if consonant or not
     */
    private final boolean cons(int i) {
        switch (letters.get(i)) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
                return false;
            case 'y':
                return (i == 0) ? true : !cons(i - 1);
            default:
                return true;
        }
    }

    /**
     * finds the consonant vowel order
     * 
     * @param s
     * @return
     */
    private final int m(String s) {
        int n = 0;
        int i = 0;
        int j = letters.size() - s.length();
        while (true) {
            if (i > j)
                return n;
            if (!cons(i))
                break;
            i++;
        }
        i++;
        while (true) {
            while (true) {
                if (i > j)
                    return n;
                if (cons(i))
                    break;
                i++;
            }
            i++;
            n++;
            while (true) {
                if (i > j)
                    return n;
                if (!cons(i))
                    break;
                i++;
            }
            i++;
        }
    }

    /**
     * check if vowel is in stem
     * 
     * @return
     */
    private final boolean vowelinstem(String s) {
        int i;
        int j = letters.size() - s.length();
        for (i = 0; i <= j; i++)
            if (!cons(i))
                return true;
        return false;
    }

    /**
     * check if ends in a double consonant
     * 
     * @param j
     *            index of last letter
     * @return
     */
    private final boolean doubleletter(int j) {
        if (j < 1)
            return false;
        return letters.get(j) == letters.get(j - 1);
    }

    /*
     * check if the ending is consonant vowel consonant
     */

    private final boolean cvc() {
        if (letters.size() > 2)
            return cons(letters.size() - 1) && !cons(letters.size() - 2)
                    && (cons(letters.size() - 3)
                            || letters.get(letters.size() - 3) == 'y');
        else
            return false;
    }

    /**
     * removes x elements from the arraylist
     * 
     * @param x
     */
    private void removeX(int x) {
        for (int i = 1; i <= x; i++) {
            letters.remove(letters.size() - 1);
        }
    }

    /**
     * check if suffix is at the end of the string
     * 
     * @param s
     */
    private void checkSuff(String s) {
        if (ends(s)) {
            suffs.add(s);
            removeX(s.length());

        }

    }

    /**
     * check if the prefix is there
     * 
     * @param s
     */
    private void checkPref(String s) {
        if (begins(s) && letters.size() > s.length() + 2) {
            removeF(s.length());
            String p = "";
            for (int i = 0; i < letters.size(); i++) {
                p += letters.get(i);
            }
            if (!english.isEmpty() && !english.containsKey(p)) {
                for (int i = s.length() - 1; i >= 0; i--) {
                    letters.add(0, s.charAt(i));
                }
            }
            else
                prefs.add(s);
        }
    }

    /**
     * find the last suffixes such as "es" or "eed"
     */
    private void stepOne() {
        // plural/ownership
        if (ends("s")) {
            if (ends("sses")) {
                suffs.add("es");
                removeX(3);
                traits.add("plural");
            }
            else if (ends("ies")) {
                suffs.add("es");
                removeX(3);
                letters.add('y');
                traits.add("plural/ownership");
            }
            else if ((letters.get(letters.size() - 2) != 's') && !ends("itis")
                    && !ends("ous")) {
                suffs.add("s");
                removeX(1);
                traits.add("plural/ownership");
            }
        }
        // past tense
        if (ends("eed")) {
            if (m("eed") > 0) {
                removeX(1);
                suffs.add("ed");
                traits.add("past tense");
            }
        }

    }

    /**
     * find middle suffixes such as "ity" and "ly"
     */
    private void stepTwo() {
        // adjective to noun +
        if (ends("ity") && !ends("ivity")) {
            suffs.add("ity");
            removeX(3);
            if (ends("abil")) {
                removeX(2);
                letters.add('l');
                letters.add('e');
            }
        }
        // adjective to adverb + noun to adjective
        if (ends("ly") && !ends("ably")) {
            suffs.add("ly");
            removeX(2);
        }
        // adjective to noun
        if (ends("ness")) {
            suffs.add("ness");
            removeX(4);
            if (ends("i")) {
                removeX(1);
                letters.add('y');
            }
        }
        // noun to adjective + adjective to noun + verb to adjective
        if (ends("y") /* && ends("" + letters.get(letters.size() - 2)) */) {
            suffs.add("y");
            removeX(2);
        }
        // adjective to noun
        checkSuff("dom");

    }

    /**
     * finds whatever is left in the suffixes
     */
    private void stepThree() {
        if ((ends("ed") && vowelinstem("ed") && !ends("eed"))
                || (ends("ing") && vowelinstem("ing") && !ends("ling"))) {
            // past tense
            if (ends("ed")) {
                suffs.add("ed");
                removeX(2);
                traits.add("past tense");
            }
            // present tense
            if (ends("ing")) {
                suffs.add("ing");
                removeX(3);
                if (doubleletter(letters.size() - 1)) {
                    removeX(1);
                }
                traits.add("present tense");
            }
            if (ends("at") || cvc() || ends("it"))
                letters.add('e');
            else if (ends("bl"))
                letters.add('e');
            else if (ends("iz"))
                letters.add('e');
            if (ends("y")) {
                removeX(1);
                letters.add('i');
                letters.add('e');
            }
            if (ends("i")) {
                removeX(1);
                letters.add('y');
            }

            /*
             * else if (doublec(letters.size()-1)) { { Character ch =
             * letters.get(letters.size()-1); if (!(ch == 'l' || ch == 's' || ch
             * == 'z')) letters.remove(letters.size()-1); } } else if (m() == 1
             * && cvc(k)) setto("e");
             */
        }
        // feminine
        if (ends("ess")) {
            suffs.add("ess");
            removeX(3);
            if (ends("r")) {
                removeX(1);
                letters.add('e');
                letters.add('r');
            }
            else if (ends("nc")) {
                letters.add('e');
            }
            traits.add("feminine");
        }
        // feminine
        if (ends("ette")) {
            removeX(4);
            suffs.add("ette");
            if (cvc()) {
                letters.add('e');
            }
            if (doubleletter(letters.size() - 1)) {
                removeX(1);
            }
            traits.add("feminine");
        }
        // diminutive
        if (ends("ish") && letters.size() > 5) {
            removeX(3);
            if (doubleletter(letters.size() - 1)) {
                removeX(1);
            }
            suffs.add("ish");
            traits.add("dimunitive");
        }
        // diminutive
        if (ends("ling")) {
            checkSuff("ling");
            traits.add("dimunitive");
        }
        // verb to adjective + productive
        if (ends("able")) {
            checkSuff("able");
            if(cvc()){
                letters.add('e');
            }
        }
        // able + ly
        checkSuff("ably");
        // action, state, or quality
        checkSuff("ance");
        // state or quality
        if (ends("ancy") && letters.size() > 6) {
            removeX(4);
            suffs.add("ancy");
        }
        // state
        checkSuff("ship");
        if (ends("ee") && letters.size() > 5) {
            suffs.add("ee");
            removeX(2);
            if (cvc()) // check if it goes VC at the end
            {
                letters.add('e');
            }
        }
        if (ends("ant") && (letters.size() - 3 > 1) && !ends("gnant")) {
            suffs.add("ant");
            removeX(3);
        }
        checkSuff("ilation");
        if (ends("ization")) {
            suffs.add("ation");
            removeX(5);
            letters.add('e');
        }
        if (ends("ation")) {
            suffs.add("ation");
            removeX(3);
            letters.add('e');
            if (ends("" + letters.get(letters.size() - 2))) {
                removeX(1);
            }
        }
        if (ends("tion") && !ends("ction")) {
            suffs.add("tion");
            removeX(4);
        }
        if(ends("ion") && !ends("tion")){
            checkSuff("ion");
        }
        if (ends("al") && !ends("inal") && !ends("ial") && !ends("yal") && !ends("adical")) {
            suffs.add("al");
            removeX(2);
            if (cvc()) {
                letters.add('e');
            }
        }

        if (ends("ivity")) {
            removeX(3);
            suffs.add("ivity");
            letters.add('e');
        }
        if (ends("ive")) {
            checkSuff("ive");
            if (ends("eat") || cvc()) {
                letters.add('e');
            }
        }
        checkSuff("or");
        if (ends("er")) {
            suffs.add("er");
            removeX(2);
            if (ends("i")) {
                removeX(1);
                letters.add('y');
            }
            else if ((cvc() && !doubleletter(letters.size() - 3))
                    && !ends("ched")) {
                letters.add('e');
                String s = "";
                for (int i = 0; i < letters.size(); i++) {
                    s += letters.get(i);
                }
                if (!english.containsKey(s)) {
                    removeX(1);
                }
            }
            else if (ends("" + letters.get(letters.size() - 2)) && !ends("ss")
                    && (!ends("ll") || ends("full")) && !ends("dd")) {
                removeX(1);
            }
            if (ends("bl") || ends("rs") || ends("u") || ends("mpl")
                    || ends("btl") || ends("uav") || ends("bl")) {
                letters.add('e');
            }
            if (ends("uy")) {
                removeX(1);
                letters.add('e');
                letters.add('y');
            }
            if (ends("ooy")) {
                removeX(3);
                letters.add('o');
                letters.add('o');
                letters.add('e');
                letters.add('y');
            }
            traits.add("comparative");
        }
        else if (ends("est")) {
            suffs.add("est");
            removeX(3);
            if (ends("i")) {
                removeX(1);
                letters.add('y');
            }
            else if ((cvc() && !doubleletter(letters.size() - 3))
                    && !ends("ched")) {
                letters.add('e');
                String s = "";
                for (int i = 0; i < letters.size(); i++) {
                    s += letters.get(i);
                }
                if (!english.containsKey(s)) {
                    removeX(1);
                }
            }
            else if (ends("" + letters.get(letters.size() - 2)) && !ends("ss")
                    && (!ends("ll") || ends("full")) && !ends("dd")) {
                removeX(1);
            }
            if (ends("bl") || ends("rs") || ends("u") || ends("mpl")
                    || ends("btl") || ends("uav") || ends("bl")) {
                letters.add('e');
            }
            if (ends("uy")) {
                removeX(1);
                letters.add('e');
                letters.add('y');
            }
            if (ends("ooy")) {
                removeX(3);
                letters.add('o');
                letters.add('o');
                letters.add('e');
                letters.add('y');
            }
            traits.add("superlative");
        }
        if (ends("ful")) {
            suffs.add("ful");
            removeX(3);
            if (ends("i")) {
                letters.add('y');
            }
        }
        checkSuff("itis");
        checkSuff("less");
        checkSuff("like");
        checkSuff("ment");
        checkSuff("geous");
        if (ends("ious") && letters.size() != 5 && !ends("rious")
                && !ends("icious")) {
            suffs.add("ious");
            removeX(3);
            letters.add('e'); // carious
        }
        if (ends("ous") && !ends("ious")) {
            suffs.add("ous");
            removeX(3);
        }
        if (ends("en")) {
            suffs.add("en");
            removeX(1);
        }
        if (ends("ism")) {
            checkSuff("ism");
            if (doubleletter(letters.size() - 1)) {
                removeX(1);
            }
        }
        if (ends("ar") && !ends("ear") && !ends("aar") && !ends("oar")
                && !ends("uar")) {

            removeX(2);
            if (letters.size() > 2
                    && letters.get(letters.size() - 2) == letters
                            .get(letters.size() - 1)) {
                removeX(1);
            }
            else if (ends("i")) {
                letters.add('e');
            }
            String s = "";
            for (int i = 0; i < letters.size(); i++) {
                s += letters.get(i);
            }
            if (!english.containsKey(s)) {
                letters.add('a');
                letters.add('r');
            }
            else
                suffs.add("ar");
        }
        if(ends("atic")){
            checkSuff("atic");
            if(doubleletter(letters.size()-1)){
                removeX(1);
            }
        }
        if (ends("ic")) {
            suffs.add("ic");
            removeX(2);
            
            if (ends("ct")) {
                removeX(2);
                letters.add('x');
            }
            if (ends("tr") || ends("log") || doubleletter(letters.size()-2)) {
                letters.add('y');
            }
            if(ends("x")){
                letters.add('i');
                letters.add('a');
            }
            if (cvc() && !ends("rab")) {
                letters.add('e');
            }
        }
        checkSuff("ist");
    }

    /**
     * finds first prefixes
     */
    private void stepFour() {
        if (begins("dis") && letters.size() > 4) {
            checkPref("dis");
        }
        checkPref("di");
        checkPref("trans");
        checkPref("acro");
        checkPref("allo");
        checkPref("alter");
        checkPref("ante");
        if (begins("anti")) {
            checkPref("anti");
            traits.add("negative");
        }
        checkPref("an");
        checkPref("as");
        if (begins("a")) {
            checkPref("a");
            traits.add("negative");
        }
        checkPref("auto");
        checkPref("bi");
        checkPref("contra");
        if (begins("counter")) {
            checkPref("counter");
            traits.add("negative");
        }
        checkPref("co");
        if (begins("de")) {
            checkPref("de");
            traits.add("negative");
        }
        checkPref("down");
        if (begins("dys")) {
            checkPref("dys");
            traits.add("negative");
        }
        checkPref("epi");
        if (begins("extra") && !ends("extra")) {
            prefs.add("extra");
            removeF(5);
        }
        checkPref("hemi");
        checkPref("hexa");
        if (begins("hyper") && !ends("hyper")) {
            prefs.add("hyper");
            removeF(5);
        }
        checkPref("hypo");
        checkPref("il");
        if (begins("im")) {
            checkPref("im");
            traits.add("negative");
        }
        checkPref("infra");
        checkPref("inter");
        checkPref("intra");
        if (begins("in")) {
            checkPref("in");
            traits.add("negative");
        }
        checkPref("ir");
        checkPref("macro");
        checkPref("mal");
        checkPref("maxi");
        checkPref("meso");
        checkPref("micro");
        checkPref("mid");
        checkPref("mini");
        checkPref("mis");
        checkPref("mono");
        checkPref("multi");
        if (begins("non")) {
            checkPref("non");
            traits.add("negative");
        }
        checkPref("octo");
        checkPref("over");
        checkPref("pan");
        checkPref("para");
        checkPref("penta");
        checkPref("peri");
        checkPref("per");
        checkPref("poly");
        checkPref("post");
        checkPref("pre");
        checkPref("proto");
        checkPref("pro");
        checkPref("pseudo");
        checkPref("quadri");
        checkPref("quasi");
        checkPref("re");
        checkPref("self");
        checkPref("semi");
        checkPref("sub");
        checkPref("super");
        checkPref("supra");
        checkPref("tetra");
        checkPref("tri");
        checkPref("ultra");
        checkPref("under");
        if (begins("un")) {
            checkPref("un");
            traits.add("negative");
        }
        checkPref("up");
        checkPref("xeno");
        checkPref("cyber");
    }

}
