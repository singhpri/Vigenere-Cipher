import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        
        String newSlice = "";
        for(int i=whichSlice; i<message.length(); i+=totalSlices){
            newSlice += message.charAt(i);
        }
        return newSlice;
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        /*FileResource resource = new FileResource();
        String 
        encrypted = resource.asString();
        System.out.println(encrypted);*/
        int[] key = new int[klength];

        for(int i=0; i<klength;i++){
            String newString = sliceString(encrypted, i, klength);
            CaesarCracker cc = new CaesarCracker();
            int j = cc.getKey(newString);

            key[i] = j;

        }
       
        /*for(int i=0; i<klength; i++){
        System.out.println("The key is " + key[i]);
    }*/
    return key;
}
        

    public void breakVigenere () {
        FileResource fr = new FileResource();
        String message = fr.asString();
        FileResource resource = new FileResource();
        HashSet<String> dict = new HashSet<String>();
        dict = readDictionary(resource);
        String text = breakForLanguage(message,dict);
        //System.out.println(text);
        //System.out.print(message);*/
        
        //int[] key = tryKeyLength(message,57,'e');
        //for(int i=0; i<57; i++){
        //System.out.println("The key is " + key[i]);
   // }
        /*int[] key = new int[4];
        key[0] = 3;
        key[1] = 20; 
        key[2] = 10;
        key[3] = 4;*/

        /*VigenereCipher vc = new VigenereCipher(key);
        String decrypted = vc.decrypt(message);
        System.out.println(decrypted);*/
    }
    
    public HashSet<String>readDictionary(FileResource fr){
        HashSet<String> words = new HashSet<String>();
        for(String text:fr.lines()){
            text = text.toLowerCase();
            if(!words.contains(text)){
            words.add(text);
        }
        
        }
        
    return words;
    }
    
    public int countWords(String message, HashSet<String> dictionary){
        String[] text = message.split("\\W");
        int count = 0;
        for(String word:text){
            word = word.toLowerCase();
            for(String s:dictionary){
                if(word.equals(s)){
                    count++;
                }
            }
        }
        return count;
    }
    
    public String breakForLanguage(String encrypted,HashSet<String> dictionary){
    int max = 0;
    String s = ""; 
    int n = 0;
    
        for(int i=1; i<=100; i++){
        int klength = i; 
        int[] key = tryKeyLength(encrypted, klength,'a');
        VigenereCipher vc = new VigenereCipher(key);
        String decrypted = vc.decrypt(encrypted);
        //System.out.println(decrypted);
        int num = countWords(decrypted, dictionary);
        if(num > max){
            max = num;
            s = decrypted;
            n = i;
        }
        
    }
    System.out.println("KeyLength is " + n);
        /*for(int j=0;j<n;j++){
            System.out.print(key[j]);
        }*/
    System.out.println("The number of words matching are "+max);
    for(int i = 0; i < 50; i++){
        System.out.print(s.charAt(i));
    }
    return s;
    }
    
}
