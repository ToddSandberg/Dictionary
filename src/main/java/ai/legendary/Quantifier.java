package ai.legendary;

import java.io.Serializable;

public class Quantifier implements PartOfSpeech,Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 7375522589018391130L;
    public String Quantifier = "Quantifier";
    /**
     *  1 = quantifier
     *  2 = cardinal number
     */
    public int quantifierID = -1;
    public Quantifier(String s) {
        if(s.endsWith("one")||s.endsWith("two")||s.endsWith("three")||s.endsWith("four")||s.endsWith("five")||s.endsWith("six")||s.endsWith("seven")||s.endsWith("eight")||s.endsWith("nine")||s.endsWith("zero")||s.endsWith("ten")||s.endsWith("eleven")||s.endsWith("twelve")||s.endsWith("teen")||s.contains("billion")||s.endsWith("dozen")||s.endsWith("ty")||s.endsWith("trillion")||s.endsWith("thousand")||s.endsWith("quadrillion")||s.endsWith("zillion")||s.equals("ane")||s.equals("fourscore")||s.endsWith("hundred")||s.endsWith("million")||s.endsWith("threescore")){
            quantifierID = 2;
        }
        else{
            quantifierID = 1;
        }
    }
    public String toString(){
        return "Quantifier: QuantifierID=" + quantifierID;
    }

}
