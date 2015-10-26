
import Tagger.SimpleTagger;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Example main class for MyNER
 *
 * @author Natanael Taufik
 */
public class Main {
    public static void main(String[] args) {
        String combinationNumber = "111011011101";
        String input = "data\\corpus\\dummy.txt";
        String output = "data\\output\\dummy.txt";
        String modelLoc = "data\\model\\111011011101.mod";
        String crfLoc = "data\\CRFs\\input.crf";
        String opennlpModelLoc = "opennlp\\model\\en-token.bin";
        String stanfordModelLoc = "stanford-postagger\\indonesianTweetStanfordModel";
        String regDicLoc = "dictionary\\region.dic";
        String stpDicLoc = "dictionary\\stopword.dic";
        String abbDicLoc = "dictionary\\abbreviation.dic";
        
        System.out.println("Starting...");
        
        TokenizerMyner tok = new TokenizerMyner("test", input, opennlpModelLoc);
        
        Features features = new Features(tok.getTokenizedLines(), tok.getMode());
        features.setStanfordModelLoc(stanfordModelLoc);
        features.setRegionDicLoc(regDicLoc);
        features.setStpDicLoc(stpDicLoc);
        features.setAbbDicLoc(abbDicLoc);
        features.buildFeatures();
        
        ArrayList<String> extractedFeatures = features.getFeatures(combinationNumber);
        
        // Write the features into file.
        FileWriter out = null;
        try {
            out = new FileWriter(crfLoc);
            
            for (String word : extractedFeatures) {
                out.write(word + "\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // CRF MALLET
        ArrayList<String> crfResults = new ArrayList();
        SimpleTagger tagger = new SimpleTagger();
        String[] tagArgs = {"--model-file",modelLoc,crfLoc};
        try {
            tagger.arguments(tagArgs, crfResults);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Write the results into file.
        ArrayList<String> results = tok.retractSegment(crfResults);
        FileWriter outResult = null;
        try {
            outResult = new FileWriter(output);
            
            for (String sentence : results) {
                outResult.write(sentence + "\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (outResult != null)
                    outResult.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        System.out.println("Finished...");
    }
    
}
