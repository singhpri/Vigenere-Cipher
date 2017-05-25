import java.util.*;
import edu.duke.*;

public class VigenereBreaker2 {
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
        CaesarCracker cc = new CaesarCracker(mostCommon);
        for(int i=0; i<klength;i++){
            String newString = sliceString(encrypted, i, klength);
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

        HashMap<String,HashSet<String>> map = new HashMap<String, HashSet<String>>();
        FileResource resource1 = new FileResource();
        HashSet<String> danish = new HashSet<String>();
        danish = readDictionary(resource1);
        map.put("Danish", danish);
        FileResource resource2 = new FileResource();
        HashSet<String> dutch = new HashSet<String>();
        dutch = readDictionary(resource2);
        map.put("Dutch", dutch);        
        FileResource resource3 = new FileResource();
        HashSet<String> english = new HashSet<String>();
        english = readDictionary(resource3);
        map.put("English", english);
        FileResource resource4 = new FileResource();
        HashSet<String> french = new HashSet<String>();
        french = readDictionary(resource4);
        map.put("French", french);
        FileResource resource5 = new FileResource();
        HashSet<String> german = new HashSet<String>();
        german = readDictionary(resource5);
        map.put("German", german);
        /*FileResource resource6 = new FileResource();
        HashSet<String> italian = new HashSet<String>();
        italian = readDictionary(resource6);
        map.put("Italian", italian);
        FileResource resource7 = new FileResource();
        HashSet<String> portugese = new HashSet<String>();
        portugese = readDictionary(resource7);
        map.put("Portugese", portugese);
        FileResource resource8 = new FileResource();
        HashSet<String> spanish = new HashSet<String>();
        spanish = readDictionary(resource8);
        map.put("Spanish", spanish);*/
        
        
        breakForAllLanguages(message,map);
        //String text = breakForLanguage(message,dict);
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
    char mostCommon = mostCommonCharIn(dictionary);

        for(int i=1; i<=100; i++){
        int klength = i;   
        int[]key = tryKeyLength(encrypted, klength, mostCommon);
        VigenereCipher vc = new VigenereCipher(key);
        String decrypted = vc.decrypt(encrypted);
        int num = countWords(decrypted, dictionary);
        if(num > max){
            max = num;
            s = decrypted;
            n = i;
        }
        
    }
    int[] key = tryKeyLength(encrypted,n,mostCommon);
    System.out.println("KeyLength is " + n + "and the keys are ");
        for(int j=0;j<n;j++){
            System.out.print(key[j]);
        }
    return s;
    
}
    
    public char mostCommonCharIn(HashSet<String> dictionary){
        HashMap<Character, Integer> freq = new HashMap<Character, Integer>();
        
        for(String s:dictionary){
            for(int i=0;i<s.length();i++){
            if(!freq.containsKey(s.charAt(i))){
                freq.put(s.charAt(i),1);
            }
            else{
                int n = freq.get(s.charAt(i));
                freq.put(s.charAt(i),n+1);
            }
        }
        }
        int max = 0;
        char ch = '\0';
        for(char c:freq.keySet()){
            if(freq.get(c) > max){
                max = freq.get(c);
                ch = c;
            }
        }
        return ch;
        
    }
    
    /*public void test(){
        FileResource fr = new FileResource();
        HashSet<String> words = readDictionary(fr);
        char ch = mostCommonCharIn(words);
        System.out.println(ch);
               
    }*/
    public void breakForAllLanguages(String encrypted, 
        HashMap<String,HashSet<String>> language){
        char mostCommon = '\0';
        int max = 0;
        String langCrypted ="";
        String content ="";
       
        for(String lang:language.keySet()){
            HashSet<String> words = language.get(lang);
            System.out.println("The language is " + lang);
            String str = breakForLanguage(encrypted, words);            
            int n = countWords(str,words);

            System.out.println("The number of words matching are "+ n);
            if(n>max){
                max =n;
                langCrypted=lang;
                content = str;
            }
        }
        System.out.println();
        System.out.println("The crypted language is " + langCrypted);
        System.out.println("The number of words matching are " + max);
        for(int i=0;i<50;i++){
            System.out.print(content.charAt(i));
        }

    }
    
}
