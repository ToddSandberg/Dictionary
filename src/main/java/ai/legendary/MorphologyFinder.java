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
        if(!w.contains(" ")){
        w = w.toLowerCase();
        for (int i = 0; i < w.length(); i++) {
            letters.add(w.charAt(i));
        }
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
        if (!letters.contains(" ") && !letters.isEmpty()) {
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
        if (s.length() < letters.size()) { //if delete the +1 look at -ist
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
        if (ends(s) && s.length()+1<letters.size()) {
            suffs.add(s);
            removeX(s.length());
            if(doubleletter(letters.size()-1)){ //check excellent if remove
                removeX(1);
            }
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
            else if(ends("shes") && letters.size()>4){
                suffs.add("s");
                removeX(2);
                traits.add("plural/ownership");
            }
            else if(ends("xes")){
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
            else if(ends("ves")){
                suffs.add("s");
                removeX(3);
                letters.add('f');
                if(cvc()){
                    letters.add('e');
                }
                traits.add("plural/ownership");
            }
            else if ((letters.get(letters.size() - 2) != 's') && !ends("itis")
                    && !ends("ous")) {
                suffs.add("s");
                removeX(1);
                traits.add("plural/ownership");
            }
        }
        
        if(ends("i")){
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
            else{
                removeX(2);
                letters.add('i');
            }
        }
      //blasty, flashy -- not body
        if (ends("y") && !ends("ody") && !ends("ify") && !ends("ly")) {
            removeX(1);
            if(cvc() || doubleletter(letters.size()-1) || !cons(letters.size()-1)){
                letters.add('y');
            }
            else
                suffs.add("y");
            
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
        if(ends("ily")){
            checkSuff("ily");
            letters.add('y');//peremptorily preliminarily
            
        }
        if(ends("scly")){
            checkSuff("ly");
            letters.add('l');
            letters.add('e');
        }
        // adjective to adverb + noun to adjective
        if (ends("ly") && !ends("ably")) { //lly detrimentally, 
            checkSuff("ly");
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
        
        // adjective to noun
        checkSuff("dom");

    }

    /**
     * finds whatever is left in the suffixes
     */
    private void stepThree() {
        if ((ends("ed") /*&& vowelinstem("ed")*/ && !ends("eed"))
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
                traits.add("present tense");
            }
            if ((cvc() || ends("it") || ends("ns") || ends("rg") || ends("dg") || ends("uir") || ends("iat") || ends("eas")) && !ends("y") && !ends("lter") && !ends("el")) //charged & substantiating & requiring & filtering, labeled 
                letters.add('e');
            else if (ends("bl"))
                letters.add('e');
            else if (ends("iz"))
                letters.add('e');
            if (ends("i")) {
                removeX(1);
                letters.add('y');
            }
            if (doubleletter(letters.size() - 1) && cons(letters.size()-1) && !ends("ll") && !ends("ss")) {//glossing, falling
                removeX(1);
            }
            /*if (ends("y")) { //slaying
                removeX(1);
                letters.add('i');
                letters.add('e');
            }*/
            

            /*
             * else if (doublec(letters.size()-1)) { { Character ch =
             * letters.get(letters.size()-1); if (!(ch == 'l' || ch == 's' || ch
             * == 'z')) letters.remove(letters.size()-1); } } else if (m() == 1
             * && cvc(k)) setto("e");
             */
        }
        checkSuff("less");
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
            if(ends("eling") || ends("gling") || ends("lling") || ends("ycling")){ //falling
                checkSuff("ing");
                if(ends("gl") || ends("cl")){
                    letters.add('e');
                }
            }
            else{
                checkSuff("ling");
                traits.add("dimunitive");
            }
            
        }
        // verb to adjective + productive
        if (ends("able") && !ends("iable")) {
            checkSuff("able");
            if((cvc() || ends("uid") || ends("ns")) && !ends("y") && !ends("fer")){
                letters.add('e');
            }
        }
        if (ends("ible")) {
            checkSuff("ible");
            letters.add('e');
        }
        // able + ly
        checkSuff("ably");
        // action, state, or quality
        checkSuff("ance");
        checkSuff("osis");//anamorphosis
        if(ends("ence")){
            checkSuff("ence");
            if(doubleletter(letters.size()-1)){ //excellence
                removeX(1);
            }
        }
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
        if(ends("dient")){
            checkSuff("dient");
            letters.add('y'); //obedient
        }
        if(ends("ent") && !ends("rent")){ //current
            checkSuff("ent");
            if(ends("ng")){
                letters.add('e'); //astringe
            }
        }
        if (ends("ant") && (letters.size() - 3 > 1) && !ends("gnant") && !ends("rant") && !ends("ilitant")) { //militant
               checkSuff("ant");
        }
        checkSuff("ilation");
        if (ends("ization")) {
            suffs.add("ation");
            removeX(5);
            letters.add('e');
        }
        if(ends("ize")){
            checkSuff("ize");
            if(ends("at")){ // emblematize
                letters.add('e');
            }
            if(ends("g")){
                letters.add('y'); //apologize & homologize
            }
            if(ends("not")){
                letters.add('i');
                letters.add('c');
            }
            /*if(cvc() && !ends("itic")){ //naturalize & magnetize & reflectorize
                letters.add('e');
            }*/
            if(ends("igmate")){
                suffs.add("ate");
                removeX(2);
            }
        }
        if (ends("ation")) {
            suffs.add("ation");
            removeX(5);
            if(cvc()){
                letters.add('a');
                letters.add('t');
                letters.add('e');
            }
            if (doubleletter(letters.size()-1)) {
                removeX(1);
            }
        }
        if(ends("ustion")){
            checkSuff("ion");
        }
        if (ends("tion") && !ends("ction") && !ends("stion")) {
            suffs.add("tion");
            removeX(4);
            if(ends("n")){
                letters.add('t');
            }
        }
        if(ends("ion") && (!ends("tion") || ends("stion"))){
            checkSuff("ion");
            if(ends("ns")){
                letters.add('e');
            }
        }
        if(ends("ical") && !ends("critical")){
            suffs.add("ical");
            removeX(4);
           
            letters.add('y'); //biological
        }
        if (ends("al")) {
            removeX(2);
            if(english.containsKey(getRoot())){
                suffs.add("al");
            }
            else{
                letters.add('a');
                letters.add('l');
            }
        }

        if (ends("ivity")) {
            removeX(3);
            suffs.add("ity");
            letters.add('e');
        }
        if(ends("ative")){ //manipulative
            suffs.add("ative");
            removeX(3);
            letters.add('e');
        }
        if(ends("itive")){
            suffs.add("itive");
            removeX(5);
            letters.add('e');
        }
        if(ends("sive")){ //extorsive extensive
            checkSuff("sive");
            letters.add('t');
        }
        if (ends("ive") && letters.size()>6 && !ends("orgive")) { //thrive
            checkSuff("ive");
            if (ends("eat") || cvc()) {
                letters.add('e');
            }
        }
        if(ends("or")){
            checkSuff("or");
            if(cvc()){
                letters.add('e');
            }
        }
        if (ends("er") && letters.size()>4 && !ends("rosper") && !ends("infer") && !ends("fter")) {
            suffs.add("er");
            removeX(2);
            if (ends("i")) {
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
                /*if (!english.containsKey(s)) {
                    removeX(1);
                }*/
            }
            else if (doubleletter(letters.size()-1) && !ends("ss")
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
        else if (ends("est") && letters.size()>4) {
            checkSuff("est");
            if (ends("i")) {
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
                /*if (!english.containsKey(s)) {
                    removeX(1);
                }*/
            }
            else if (doubleletter(letters.size()-1) && !ends("ss")
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
                removeX(1);
                letters.add('y');
            }
        }
        checkSuff("itis");
        
        checkSuff("like");
        checkSuff("ment");
        checkSuff("geous");
        if (ends("ious") && letters.size() != 5 && !ends("serious")
                && !ends("icious")) {
            suffs.add("ious");
            removeX(4);
            letters.add('y'); // mystery
            //letters.add('e'); // carious
        }
        if(ends("eous")){
            checkSuff("eous");
        }
        if(ends("rous")){
            checkSuff("rous");
        }
        if (ends("ous") && !ends("ious")) {
            suffs.add("ous");
            removeX(3);
            //letters.add('e'); //think about monstrous or
        }
        if(ends("ist") && letters.size()>4){ //christen
            checkSuff("ist");
            if(ends("log")){
                letters.add('y'); //biologist
            }
        }
        if (ends("en")) {
            suffs.add("en");
            removeX(2);
            if(cvc()){
                letters.add('e');
            }
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
        if(ends("etic")){
            checkSuff("etic");
            letters.add('y'); // energetic
        }
        if(ends("otic")){
            checkSuff("tic");//hypnotic
        }
        if (ends("ic") && !ends("ritic")) {
            suffs.add("ic");
            removeX(2);
            
            if (ends("ct")) {
                removeX(2);
                letters.add('x');
            }
            if (ends("tr") || ends("log") || doubleletter(letters.size()-2) || ends("or")) {
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
        
        
        if(ends("ian")){
            suffs.add("an");
            removeX(3);
        }
        //an think of ran and ban
        //fuage and pillage and manage and hemorrhage
        if(ends("age") && !ends("anage") && !ends("uage") && !ends("illage") && !ends("hage")){
            checkSuff("age"); 
        }
        if(ends("ory")){ //consecratory
            checkSuff("y");
            checkSuff("or");
            letters.add('e');
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
