package com.company.zz;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Vingenire {
    private static char[] CHARS = new char[26];
    static {
        int i = 0;
        for (char itme = 'a';itme<='z'&&i<26;itme++,i++){
            CHARS[i] = itme;
        }
    }
    public static void main(String[] args) throws FileNotFoundException {
        Vingenire vingenire = new Vingenire();
//        Map<String, Float> analyse = WordAnalyse.analyse();
        Scanner scanner = new Scanner(System.in);
        int next = scanner.nextInt();
        char[] cipher = vingenire.gen(next);
        System.out.println(cipher);
        FileUtil fileUtil = new FileUtil();
        StringBuilder fileString = fileUtil.getFileString();
        vingenire.enc(cipher,fileString);
        vingenire.dec("0111.txt");
    }
    private char[] gen(int n){
        char[] cipher = new char[n];
        for (int i = 0;i<n;i++) {
            int random = (int) (Math.random() * 26);
            cipher[i] = CHARS[random];
        }
        return cipher;
    }
    //加密并且存储到文件中
    public void enc(char[] cipher, StringBuilder fileString) throws FileNotFoundException {
        StringBuilder encStringBuilder = new StringBuilder();
        int mLength = fileString.length();
        int cLength = cipher.length;
        int a = 'a';
        int z = 'z';
        int[] keyArray = new int[cLength];

        for (int i = 0;i<cLength ;i++){
            char temp = cipher[i];
            keyArray[i] = temp-a;
        }
        for (int i = 0;i<mLength;i++){
            char oldChar = fileString.charAt(i);
            char freshChar;
            int index = i%cLength;
            int remainder = oldChar+keyArray[index]-z;
            if(remainder>0){
                freshChar  = (char) (a + remainder - 1);
            }else {
                freshChar  = (char) (oldChar+keyArray[index]);
            }
            encStringBuilder.append(freshChar);
            FileUtil.writeToFile(encStringBuilder,"0111.txt");
        }

    }
    //解密并且存到文件中
    public void dec(String filename){
        Map<Integer,ArrayList> map = new HashMap<>();
        File file = new File(filename);
        StringBuilder stringBuilder = FileUtil.readToStringBuilder(file);
        for (int i = 0;i<stringBuilder.length()-2;i++){
            String subString = stringBuilder.substring(i,i+3);
            Pattern p = Pattern.compile(subString);
            Matcher matcher = p.matcher(stringBuilder);
            ArrayList arrayList = new ArrayList();
            while (matcher.find()){
                int start = matcher.start();
                arrayList.add(start);
            }
            map.put(i,arrayList);
            System.out.println(i+": "+ arrayList);
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入选择的数组：");
        int m = scanner.nextInt();
        ArrayList arrayList = map.get(m);
        int gcd = getGCD(arrayList);
        System.out.println(gcd);
        ArrayList<Integer> keyArray = getKeyLength(gcd);
        Map<Integer,StringBuilder[]> subStringMap = new HashMap<>();
        for (int key:keyArray){
            StringBuilder[] subStringArray = new StringBuilder[key];
            for (int i = 0;i<key;i++){
                StringBuilder subStringBuilder = new StringBuilder();
                int  j = i;
                while (j<stringBuilder.length()){
                    char c = stringBuilder.charAt(j);
                    subStringBuilder.append(c);
                    j += key;
                }
                subStringArray[i] = subStringBuilder;
            }
            subStringMap.put(key,subStringArray);
        }
        Map<Integer, StringBuilder> integerStringBuilderMap = WordAnalyse.calcuKey(subStringMap);
        System.out.println(integerStringBuilderMap);
    }
    //求最大公约数
    private int getGCD(ArrayList arrayList){
        int a = (int) arrayList.get(0);
        int b = (int) arrayList.get(1);
        int c = (int) arrayList.get(2);
        int m = b-a;
        int n = c-b;
        while (n%m!=0){
            int temp = n%m;
            n = m;
            m = temp;
        }
        return m;
    }
    //求密钥的可能长度
    private ArrayList<Integer> getKeyLength(int gcd){
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 1;i<=gcd;i++){
            if (gcd%i==0){
                arrayList.add(i);
            }
        }
        System.out.println(arrayList);
        return arrayList;
    }

}
