package com.example.systemdesignproject.Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Report {
    public int id=0;
    public String name;
    public String state;
    public Report(String name,String state){
        this.name=name;
        this.state=state;
    }
    public String testContext(){
        Boolean isBad = false;
        try {
            File badWords = new File("bad.txt");
            Scanner patternScanner = new Scanner(badWords);
            while(patternScanner.hasNextLine()) {
                String pattern = patternScanner.nextLine().toUpperCase();
                String t=state.toUpperCase();
                int i = pattern.length() -1;
                int j = pattern.length() -1;

                while(i<t.length()) {
                    if(t.charAt(i) == pattern.charAt(j)) {
                        if(j == 0) {
                            isBad = true;
                            break;
                        }
                        else {
                            i--;
                            j--;
                        }
                    }else {
                        int l = last(t.charAt(i), pattern);
                        i =i+ pattern.length()-Math.min(j, l+1);
                        j = pattern.length()-1;
                    }
                }
                if(isBad) {
                    break;
                }
            }
            patternScanner.close();

            File file = new File("input.txt");
            File badFile = new File("badInput.txt");

            FileWriter writer = new FileWriter(file);
            FileWriter badWriter = new FileWriter(badFile);
            if(isBad) {
                badWriter.write(state);
                badWriter.close();
            }else {
                writer.write(state);
                writer.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(isBad) {
            id=0;
            return name+"先生/小姐您好 "+"我們偵測你輸入內容不恰當，請重新填選適當內容";
        }else {
            id=1;
            return name+"先生/小姐您好 "+"謝謝您的回饋，會加以改進！！";
        }
    }
    public int last(char c, String P) {
        for(int i = P.length()-1; i>=0; i--) {
            if(P.charAt(i) == c) {
                return P.indexOf(c);
            }
        }

        return 0;
    }
}
