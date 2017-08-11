package ai.legendary;

import java.io.Serializable;
import java.util.HashMap;
/**
 * Saves animacyHashMap to prevent the need for future rewrites.
 * @author ToddSandberg
 *
 */
public class AnimacySave implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 7980511631196559504L;
    public HashMap<String, String> animacy = new HashMap<String, String>();
    public AnimacySave(){
        
    }
}
