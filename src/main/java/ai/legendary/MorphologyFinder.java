package ai.legendary;

import java.util.ArrayList;
import java.util.Collections;
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
        // w = w.toLowerCase();
        for (int i = 0; i < w.length(); i++) {
            letters.add(w.charAt(i));
        }
    }

    /**
     * load dictionary from a file The default will not provide a backoff check
     */
    public void loadDictionary(HashMap temp) {
        english.putAll(temp);
    }

    /**
     * Call's all steps to break apart the word.
     * 
     * @return all info on the word in string format;
     */
    public String breakApart() {
        if (!letters.contains(" ") && !letters.isEmpty()
                /*&& !Character.isUpperCase(letters.get(0))*/) {
            //Prefixes
            stepFour();//possibly change 
            //Suffixes 1
            stepOne();
            //Suffixes 2
            stepTwo();
            //Suffixes 3
            stepThree();
            //Prefixes again
            stepFour();//possibly change 
            //Return the results
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
        if (ends(s) && s.length() + 1 < letters.size()) {
            removeX(s.length());
            String p = "";
            for (int i = 0; i < letters.size(); i++) {
                p += letters.get(i);
            }
            //System.out.println("word: "+p + " dictionaryContains?: "+english.containsKey(p));
            boolean removedD = false;
            if (doubleletter(letters.size() - 1) && (ends("vell") || !ends("ll"))
                    && !ends("ss") && !ends("dd")) { // check excellent if remove, discuss sell add
                removeX(1);
                removedD=true;
                p=p.substring(0,p.length()-1);
            }
            if (!english.isEmpty() && !english.containsKey(p)) {
                if(!ends("e")){
                    p += "e";
                }
                //System.out.println("word: "+p + " dictionaryContains?: "+english.containsKey(p));
                if (!english.containsKey(p)) {
                    p = p.substring(0, p.length() - 1);
                    boolean haveI = false;
                    if(ends("i")){
                        removeX(1);
                        p=p.substring(0,p.length()-1);
                        haveI = true;
                    }
                    p += "y";
                    //System.out.println("word: "+p + " dictionaryContains?: "+english.containsKey(p));
                    if (!english.containsKey(p)) {
                        p = p.substring(0, p.length() - 1);
                        if(haveI){
                            letters.add('i');
                        }
                        if(removedD){
                            letters.add(letters.get(letters.size()-1));
                        }
                        for (int i = 0; i < s.length(); i++) {
                            letters.add(letters.size(), s.charAt(i));
                        }
                    }
                    else {
                        letters.add('y');
                        suffs.add(s);
                    }
                }
                else {
                    suffs.add(s);
                    letters.add('e');
                }
            }

            else
                suffs.add(s);
            
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
        if (ends("s") && !ends("osis")) {
            if (ends("sses")) {
                suffs.add("s");
                removeX(3);
                traits.add("plural/ownership");
            }
            else if (ends("shes") && letters.size() > 4) {
                suffs.add("s");
                removeX(2);
                traits.add("plural/ownership");
            }
            else if (ends("xes")) {
                suffs.add("s");
                removeX(2);
                traits.add("plural/ownership");
            }
            else if (ends("ies")) {
                suffs.add("s");
                removeX(3);
                letters.add('y');
                traits.add("plural/ownership");
            }
            else if (ends("ves")) {
                suffs.add("s");
                removeX(3);
                letters.add('f');
                if (cvc()) {
                    letters.add('e');
                }
                traits.add("plural/ownership");
            }
            else if (ends("oes")) {
                suffs.add("s");
                removeX(2);
                traits.add("plural/ownership");
            }
            else if ((letters.get(letters.size() - 2) != 's') && !ends("itis")
                    && !ends("ous")) {
                checkSuff("s");
                traits.add("plural/ownership");
            }
        }

        if (ends("i")) {
            removeX(1);
            letters.add('u');
            letters.add('s');
            String p = "";
            for (int i = 0; i < letters.size(); i++) {
                p += letters.get(i);
            }
            if (!english.isEmpty() && english.containsKey(p)) {
                suffs.add("i");
                traits.add("plural");
            }
            else {
                removeX(2);
                letters.add('i');
            }
        }
        // blasty, flashy -- not body
        if (ends("y") && !ends("ody") && !ends("ify") && !ends("ly")
                && !ends("ency") && !ends("ancy")) {
            checkSuff("y");
        }

        // past tense
        if (ends("eed")) {
                removeX(1);
                String p = "";
                for (int i = 0; i < letters.size(); i++) {
                    p += letters.get(i);
                }
                if (english.containsKey(p)) {
                    suffs.add("ed");
                    traits.add("past tense");
                }
                else {
                    letters.add('d');
                }
            
        }

    }

    /**
     * find middle suffixes such as "ity" and "ly"
     */
    private void stepTwo() {
        // adjective to noun +
        if (ends("ity") && !ends("ivity")) {
            removeX(3);
            suffs.add("ity");
            if (ends("abil")) {
                removeX(2);
                letters.add('l');
                letters.add('e');
            }
        }
        if (ends("ily")) {
            checkSuff("ily");
            //letters.add('y');// peremptorily preliminarily

        }
        if (ends("scly")) {
            removeX(2);
            suffs.add("ly");
            letters.add('l');
            letters.add('e');
        }
        // adjective to adverb + noun to adjective
        if (ends("ly") && !ends("ably")) { // lly detrimentally,
            checkSuff("ly");
        }
        // adjective to noun
        if (ends("ness")) {
            checkSuff("ness");
            /*if (ends("i")) {
                removeX(1);
                letters.add('y');
            }*/
        }
        // noun to adjective + adjective to noun + verb to adjective

        // adjective to noun
        checkSuff("dom");

    }

    /**
     * finds whatever is left in the suffixes
     */
    private void stepThree() {
        if (ends("ism")) {
            checkSuff("ism");
            /*if (cvc()) {
                letters.add('e');
            }
            if (doubleletter(letters.size() - 1)) {
                removeX(1);
            }*/
        }
        if ((ends("ed") /* && vowelinstem("ed") */ && !ends("eed"))
                || (ends("ing") && vowelinstem("ing") && !ends("ling"))
                        && letters.size() > 4) {// king
            // past tense
            if (ends("ed")) {
                checkSuff("ed");
                traits.add("past tense");
            }
            // present tense
            if (ends("ing")) {
                checkSuff("ing");
                traits.add("present tense");
            }
            /*if ((cvc() || ends("it") || ends("ns") || ends("rg") || ends("dg")
                    || ends("uir") || ends("iat") || ends("eas") || ends("ng")
                    || ends("nc") || ends("uat")) && !ends("y")
                    && !ends("lter") && !ends("el") && !ends("ffen")
                    && !ends("en")) // charged & substantiating & requiring &
                                    // filtering, labeled,stiffening
                letters.add('e');
            else if (ends("bl"))
                letters.add('e');
            else if (ends("iz"))
                letters.add('e');
            if (ends("i")) {
                removeX(1);
                letters.add('y');
            }
            if (doubleletter(letters.size() - 1) && cons(letters.size() - 1)
                    && !ends("ll") && !ends("ss")) {// glossing, falling
                removeX(1);
            }
            /*
             * if (ends("y")) { //slaying removeX(1); letters.add('i');
             * letters.add('e'); }
             */

            /*
             * else if (doublec(letters.size()-1)) { { Character ch =
             * letters.get(letters.size()-1); if (!(ch == 'l' || ch == 's' || ch
             * == 'z')) letters.remove(letters.size()-1); } } else if (m() == 1
             * && cvc(k)) setto("e");
             */
        }
        checkSuff("less");
        // feminine
        if (ends("ess") && !ends("ness") && !ends("less") && !ends(" press")) {
            removeX(3);
            suffs.add("ess");
            if (ends("r")) {
                removeX(1);
                letters.add('e');
                letters.add('r');
            }
            if(doubleletter(letters.size()-1)){
                removeX(1);
            }
            else if (ends("nc")) {
                letters.add('e');
            }
            traits.add("feminine");
        }
        // feminine
        if (ends("ette")) {
            checkSuff("ette");
            /*if (cvc()) {
                letters.add('e');
            }
            if (doubleletter(letters.size() - 1)) {
                removeX(1);
            }*/
            traits.add("feminine");
        }
        // diminutive
        if (ends("ish") && letters.size() > 5) {
            checkSuff("ish");
            /*if (doubleletter(letters.size() - 1)) {
                removeX(1);
            }
            if (cvc()) { // gnomish
                letters.add('e');
            }*/
            traits.add("dimunitive");
        }
        // diminutive
        if (ends("ling")) {
            if (ends("eling") || ends("gling") || ends("lling")
                    || ends("ycling")) { // falling
                checkSuff("ing");
                /*if (ends("gl") || ends("cl")) {
                    letters.add('e');
                }*/
            }
            else {
                checkSuff("ling");
                traits.add("dimunitive");
            }

        }
        if (ends("trable")) { // demonstrable
            removeX(4);
            suffs.add("able");
            letters.add('a');
            letters.add('t');
            letters.add('e');
        }
        // verb to adjective + productive
        if (ends("able") && !ends("iable")) {
            checkSuff("able");
            /*if ((cvc() || ends("uid") || ends("ns")) && !ends("y")
                    && !ends("fer")) {
                letters.add('e');
            }*/
        }
        if (ends("ible")) {
            checkSuff("ible");
            /*if (!ends("st"))// digestible
                letters.add('e');*/
        }
        // able + ly
        checkSuff("ably");
        if ((ends("ancy") || ends("ency")) && letters.size() > 6) {
            removeX(1);
            letters.add('e');
            suffs.add("y");
        }
        // action, state, or quality
        if (ends("ance")) {
            removeX(2);
            letters.add('t');
            suffs.add("ance");
        }
        checkSuff("osis");// anamorphosis
        if (ends("ence")) {
            removeX(2);
            letters.add('t');
            suffs.add("ence");
        }
        // state
        checkSuff("ship");
        if (ends("ee") && letters.size() > 5) {
            checkSuff("ee");
            /*if (cvc()) // check if it goes VC at the end
            {
                letters.add('e');
            }*/
        }
        if (ends("dient")) {
            checkSuff("dient");
            //letters.add('y'); // obedient
        }
        if (ends("ent") && !ends("rent") && !ends("sent")) { // current absent
            checkSuff("ent");
            /*if (ends("ng") || ends("rg")) {
                letters.add('e'); // astringe
            }*/
        }
        if (ends("ant") && (letters.size() - 3 > 1) && !ends("gnant")
                && !ends("rant") && !ends("ilitant")) { // militant
            checkSuff("ant");
            checkSuff("t");
            /*if (ends("i")) { // compliant
                removeX(1);
                letters.add('y');
            }
            if (cvc()) {
                letters.add('e');
            }*/
        }
        if (ends("ure") && !ends("cure")) {
            checkSuff("ure");
            /*if (cvc()) {
                letters.add('e');
            }*/
        }
        checkSuff("ilation");
        if (ends("ization")) {
            suffs.add("ation");
            removeX(5);
            letters.add('e');
        }
        if (ends("ize")) {
            checkSuff("ize");
            /*if (ends("at")) { // emblematize
                letters.add('e');
            }
            if (ends("g")) {
                letters.add('y'); // apologize & homologize
            }
            if (ends("not")) {//need to fix possibly
                letters.add('i');
                letters.add('c');
            }*/
            /*
             * if(cvc() && !ends("itic")){ //naturalize & magnetize &
             * reflectorize letters.add('e'); }
             */
            if (ends("igmate")) {
                suffs.add("ate");
                removeX(2);
            }
        }
        if (ends("ation")) {
            checkSuff("ation");
            /*if (cvc()) {
                letters.add('a');
                letters.add('t');
                letters.add('e');
            }
            if (doubleletter(letters.size() - 1)) {
                removeX(1);
            }*/
        }
        if (ends("ustion")) {
            checkSuff("ion");
        }
        if (ends("ition")) {
            checkSuff("ition");
            /*if (cvc() || ends("rv") || ends("rm")) { // servition
                letters.add('e');
            }*/
        }
        if (ends("tion") && !ends("ction") && !ends("stion")
                && !cons(letters.size() - 5)) {
            suffs.add("tion");
            removeX(4);
            if (ends("n")) {
                letters.add('t');
            }
        }
        if (ends("ion") /*
                         * && (!ends("tion") || ends("stion") || ends("ction"))
                         */) {// introspection, digestion
            checkSuff("ion");
            // if add doubleletter think about discussion
            /*if (ends("ns") || ends("is")) {// vision
                letters.add('e');
            }*/
        }

        if (ends("ical") && !ends("critical")) {
            suffs.add("ical");
            removeX(4);
            if (!ends("ph"))// graphic
                letters.add('y'); // biological
        }
        if (ends("al") && !ends("ual") && !ends("ital")) {
            // dismissal
            checkSuff("al");
            /*if ((cvc() || ends("ais") || ends("ous") || ends("rv"))
                    && !ends("ray")) { // appraisal, espousal, observal
                letters.add('e');
            }
            if (doubleletter(letters.size() - 1)) {
                removeX(1);
            }*/
            /*
             * if(english.containsKey(getRoot())){ suffs.add("al"); } else{
             * letters.add('a'); letters.add('l'); }
             */
        }

        if (ends("ivity")) {
            removeX(3);
            suffs.add("ity");
            letters.add('e');
        }
        if (ends("ative")) { // manipulative
            suffs.add("ative");
            removeX(3);
            letters.add('e');
        }
        if (ends("itive")) {
            suffs.add("itive");
            removeX(5);
            letters.add('e');
        }
        if (ends("sive")) { // extorsive extensive
            suffs.add("sive");
            removeX(4);
            letters.add('t');
        }
        if (ends("ive") && letters.size() > 6 && !ends("orgive")) { // thrive
            checkSuff("ive");
            /*if (ends("eat") || cvc()) {
                letters.add('e');
            }*/
        }
        if (ends("icator")) {
            checkSuff("icator");
            //letters.add('y');// scarificator, purificator
        }
        if (ends("ify")) {
            checkSuff("ify");
            /*if (cvc()) {
                letters.add('e');
            }*/
        }
        checkSuff("itor");
        if (ends("or")) {
            checkSuff("or");
            /*if ((cvc() || ends("ns") || ends("uat")) && !ends("dit")) {
                letters.add('e');
            }*/
        }
        if (ends("arate") || ends("urate") || ends("ervate") || ends("uate")
                || ends("alate")) { // declarate assimilate dedicate
            checkSuff("ate");
            //letters.add('e');
        }
        if (ends("ite")) {
            checkSuff("ite");

        }
        if (ends("er") && letters.size() > 4 && !ends("rosper")
                && !ends("infer") && !ends("fter") && !ends("mber")
                && !ends("eter") && !ends("ffer") && !ends("rder")) { // somber/member, meter,order
                                                     // offer
            checkSuff("er");
            /*if (ends("i")) {
                removeX(1);
                letters.add('y');
            }
            else if ((cvc() && !ends("x") && !doubleletter(letters.size() - 3))
                    && !ends("ched")) {
                letters.add('e');
                String s = "";
                for (int i = 0; i < letters.size(); i++) {
                    s += letters.get(i);
                }
                /*
                 * if (!english.containsKey(s)) { removeX(1); }
                 */
            /*}
            else if (doubleletter(letters.size() - 1) && !ends("ss")
                    && (!ends("ll") || ends("full")) && !ends("dd")) {
                removeX(1);
            }
            if (ends("bl") || ends("rs") || ends("u") || ends("mpl")
                    || ends("btl") || ends("uav") || ends("bl")
                    || ends("rg")) {
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
            }*/
            traits.add("comparative");
        }
        else if (ends("est") && letters.size() > 4) {
            checkSuff("est");
            /*if (ends("i")) {
                removeX(1);
                letters.add('y');
            }
            else if ((cvc() && !ends("x") && !doubleletter(letters.size() - 3))
                    && !ends("ched")) {
                letters.add('e');
                String s = "";
                for (int i = 0; i < letters.size(); i++) {
                    s += letters.get(i);
                }
                /*
                 * if (!english.containsKey(s)) { removeX(1); }
                 */
            /*}
            else if (doubleletter(letters.size() - 1) && !ends("ss")
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
            }*/
            traits.add("superlative");
        }
        if (ends("ful")) {
            suffs.add("ful");
            removeX(3);
            /*if (ends("i")) {
                removeX(1);
                letters.add('y');
            }*/
        }
        if (ends("itis")) {
            removeX(4);
            suffs.add("itis");
            if (ends("r")) { // cerebritis
                letters.add('u');
                letters.add('m');
            }
        }
        checkSuff("like");
        checkSuff("ment");
        //checkSuff("geous");
        if (ends("ious") && letters.size() != 5 && !ends("serious")
                && !ends("icious")) {
            checkSuff("ious");
        }
        if (ends("eous")) {
            checkSuff("eous");
        }
        if (ends("rous")) {
            checkSuff("rous");
        }
        if (ends("ous") && !ends("ious")) {
            checkSuff("ous");
            // letters.add('e'); //think about monstrous or
        }
        if (ends("ist") && letters.size() > 4) { // christen
            checkSuff("ist");
            /*if (cvc()) { // gnomist
                letters.add('e');
            }
            if (ends("log")) {
                letters.add('y'); // biologist
            }*/
        }
        if (ends("en") && !ends("men")) {
            checkSuff("en");
        }

        if (ends("ar") && !ends("ear") && !ends("aar") && !ends("oar")
                && !ends("uar")) {

            checkSuff("ar");
            /*if (letters.size() > 2
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
                suffs.add("ar");*/
        }
        if (ends("atic")) {
            checkSuff("atic");
            /*if (doubleletter(letters.size() - 1)) {
                removeX(1);
            }*/
        }
        if (ends("etic")) {
            checkSuff("etic");
            //letters.add('y'); // energetic
        }
        if (ends("otic")) {
            checkSuff("tic");// hypnotic
        }
        if (ends("ic") && !ends("ritic") && !ends("toic")) { // stoic
            checkSuff("ic");

            /*if (ends("ct")) {
                removeX(2);
                letters.add('x');
            }
            else if (ends("tr") || ends("log") || doubleletter(letters.size() - 2)
                    || ends("or")) {
                letters.add('y');
            }
            else if (ends("x")) {
                letters.add('i');
                letters.add('a');
            }
            else if (cvc() && !ends("rab") && !ends("hol")) {
                letters.add('e');
            }*/
        }

        if (ends("ian")) {
            suffs.add("an");
            removeX(3);
            if (ends("r")) { // umbrian
                letters.add('i');
                letters.add('a');
            }
        }
        // an think of ran and ban
        // fuage and pillage and manage and hemorrhage
        if (ends("age") && !ends("anage") && !ends("uage") && !ends("illage")
                && !ends("hage")) {
            checkSuff("age");
        }
        if (ends("ory")) { // consecratory
            checkSuff("ory");
            //letters.add('e');
        }
        if (ends("ium")) { // europium
            checkSuff("ium");
            //letters.add('e');
        }
        if (ends("ery")) { // grocery butlery
            checkSuff("y");
        }

    }

    /**
     * finds first prefixes
     */
    private void stepFour() {
        checkPref("re");
        if (begins("dis") && letters.size() > 4) {
            checkPref("dis");
        }
        checkPref("di");
        checkPref("trans");
        checkPref("acro");
        checkPref("allo");
        checkPref("alter");
        checkPref("ante");
        checkPref("fore");
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
        checkPref("after");
        if (begins("dys")) {
            checkPref("dys");
            traits.add("negative");
        }
        checkPref("epi");
        checkPref("extra");
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
        checkPref("on");
        checkPref("mono");
        checkPref("multi");
        if (begins("non")) {
            checkPref("non");
            traits.add("negative");
        }
        checkPref("out");
        checkPref("octo");
        checkPref("over");
        checkPref("sur");
        checkPref("meta");
        checkPref("arch");
        checkPref("pan");
        checkPref("para");
        checkPref("penta");
        checkPref("peri");
        checkPref("per");
        checkPref("poly");
        checkPref("post");
        checkPref("pre");
        checkPref("retro");
        checkPref("proto");
        checkPref("pro");
        checkPref("pseudo");
        checkPref("quadri");
        checkPref("quasi");
        checkPref("self");
        checkPref("semi");
        checkPref("sub");
        checkPref("super");
        checkPref("supra");
        checkPref("up");
        checkPref("tetra");
        checkPref("tri");
        checkPref("ultra");
        checkPref("under");
        checkPref("by");
        checkPref("circum");
        checkPref("off");
        if (begins("un")) {
            checkPref("un");
            traits.add("negative");
        }
        checkPref("up");
        checkPref("xeno");
        checkPref("cyber");
    }

}
