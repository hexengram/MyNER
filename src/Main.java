
import Tagger.SimpleTagger;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Natanael Taufik
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Starting...");
        
        // proses corpus jadi file input crf
        
        ArrayList<String> results = new ArrayList();
        SimpleTagger tagger = new SimpleTagger();
        String[] tagArgs = {"--model-file","data\\model\\111011011101.mod","data\\CRFs\\input.crf"};
        try {
            tagger.arguments(tagArgs, results);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Finished...");
        
        for (String result : results) {
            System.out.println(result);
        }
    }
    
}
