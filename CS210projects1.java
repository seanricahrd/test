package hashDictionary;

import java.security.MessageDigest;
import java.util.Arrays;
import java.security.*;
import java.io.*;

public class CS210projects1 {
    public static void main (String[] args) {
    	long startTime = System.currentTimeMillis();
    	int count = -1;
    	int maxhash = -1;
    	int runtimes = 40000;
    	String sentence[] = new String [runtimes];
    	String hash[] = new String [runtimes];
    	String []output = new String[2];
    	String []outputhash = new String[2];
    	
    	CS210projects1 txt = new CS210projects1();
    	Dictionary dictionary1 = new Dictionary();
    	
    	System.out.println("Number of words in the dictionary: "
    			+ dictionary1.getSize() + "\n");
    	System.out.println("Calculating, please wait: "
    			+ "\n");

    	for (int x=0; x<runtimes; x++) {
			String temp = "";
        	String word[] = new String [10];
        	int sentencelength = (int)(Math.random()*6)+2;
        	
    		for(int i=0; i<sentencelength; i++) {
    			word[i] = dictionary1.getWord((int)(Math.random()*(dictionary1.getSize()))).replace('\r' ,',');
    			if (i==0) {
    		    	word[i] = upperCaseFirst(word[i]);
    			}
    			if (i==sentencelength-1) {
    				word[i] = word[i].replace(',' ,'\s');
    			}
    			temp = temp + word[i];
    		}
    		temp = temp + "are wonderful words!";
    		sentence[x] = temp;
    		hash[x] = sha256(sentence[x]);		
    	}
    	
    	for (int x=0; x<runtimes-1; x++) {
    		for (int y=1; y<runtimes; y++) {
    			count = compare(hash[x],hash[y]);
            	if (count >= 19) {
            		txt.writeUsingFileWriter("Same hash value " + count); 
            		txt.writeUsingFileWriter(sentence[x]);
            		txt.writeUsingFileWriter(sentence[y]);
            		txt.writeUsingFileWriter("");
            	}
    	    	if (count>maxhash) {
    				maxhash = count;
    				output[0] = sentence[x];
    				output[1] = sentence[y]; 
    				outputhash[0] = hash[x]; 
    				outputhash[1] = hash[y]; 
    	    	}
    		}
    	}	
    	System.out.println("The two sentences with the closest hash values are: "
    			+ "\n" + output[0] + "\n" + output[1] + "\n");
		System.out.println("Their hash values are: "
				+ "\n" + outputhash[0] + "\n" + outputhash[1] + "\n" + maxhash + "\n");
		long endTime = System.currentTimeMillis();
		System.out.println("Running Time��" + (endTime - startTime)/60000 + " min");	   	
    }
    
    public static String upperCaseFirst(String val) {
        char[] arr = val.toCharArray();
        arr[0] = Character.toUpperCase(arr[0]);
        return new String(arr);
     }
    
    public static int compare (String input1, String input2) {
    	int count = 0;
    	for (int i = 0; i < 64; i++) {
    		if(input1.charAt(i) == input2.charAt(i)) {
    			count++;
    		}
        	if (count==64) {
        		count = 0;
        	}
    	}
    	return count;
    }

    public static String sha256(String input) { 
        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
            byte[] salt = "CS210+".getBytes("UTF-8");
            mDigest.update(salt);
            byte[] data = mDigest.digest(input.getBytes("UTF-8"));
            StringBuffer sb = new StringBuffer();
            for (int i=0;i<data.length;i++){
                sb.append(Integer.toString((data[i]&0xff)+0x100,16).substring(1));
            }
            return sb.toString();
        } catch(Exception e){
            return(e.toString());
        }
    }
    /**
     * Use FileWriter when number of write operations are less
     * @param data
     */
    public void writeUsingFileWriter(String data) {
        File file = new File("D:\\Answer.txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file,true);
            fr.write(data+'\n');
        } catch (IOException e) { 
            e.printStackTrace();
        }finally{
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
