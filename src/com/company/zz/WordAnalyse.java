package com.company.zz;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zz on 2016/3/6.
 */
public class WordAnalyse {
    private static File content = new File("content");
    private static Map<Character,Double> standard = new HashMap<>();
    private static double Ic;
    static {
        for (char item = 'a';item<='z';item++){
            standard.put(item, (double) 0);
        }
        try {
            analyse(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        calcuProportion(standard);
    }
    private static void analyse(File file) throws FileNotFoundException {
        File[] files = file.listFiles();
        for (File f: files) {
            FileInputStream fileInputStream = new FileInputStream(f);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            try {
                analyseChar(bufferedReader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //分析每个字母的个数
    private static void analyseChar(BufferedReader bufferedReader) throws IOException {
        int temp;
        while ((temp = bufferedReader.read())!=-1){
            char aChar = (char) temp;
            if (standard.containsKey(aChar)){
                Double value = standard.get(aChar)+1;
                standard.put(aChar,value);
            }
        }
        bufferedReader.close();
    }
    //分析每个字母的数量
    private static Map<Character,Double> analyseChar(StringBuilder stringBuilder){
        Map<Character,Double> map = new HashMap<>();
        for (char item = 'a';item<='z';item++){
            map.put(item, (double) 0);
        }
        for (int i = 0;i<stringBuilder.length();i++) {
            char c = stringBuilder.charAt(i);
            double value = map.get(c)+1;
            map.put(c,value);
        }
        return map;
    }
    //计算每个字母在文本中的比率
    private static Map<Character,Double> calcuProportion(Map<Character,Double> map){
        Set<Character> characters = map.keySet();
        int allWords = 0;
        for (char  key:characters) {
            allWords += map.get(key);
        }
        for (char key:characters){
            map.put(key,map.get(key)/allWords);
        }
        return map;
    }
    //计算重合指数
    public static double calcuIOC(StringBuilder stringBuilder){
        Map<Character,Double> result = new HashMap<>();
        for (char item = 'a';item<='z';item++){
            result.put(item, (double) 0);
        }
        int length = stringBuilder.length();
        for (int i = 0;i<length;i++){
            char c = stringBuilder.charAt(i);
            double temp = result.get(c);
            result.put(c,temp+1);
        }
        Map<Character, Double> stringFloatMap = calcuProportion(result);
        double IC = 0;
        Set<Character> chars = stringFloatMap.keySet();
        for (char key:chars){
            IC +=  Math.pow(stringFloatMap.get(key),2);
        }
        System.out.println(IC);
        return IC;
    }
    //计算符合度
    private static double calcuFitDegree(Map<Character,Double> map){
        double degree = 0;
        Set<Character> characters = map.keySet();
        for (char c:characters){
            degree += Math.abs(standard.get(c) - map.get(c));
        }
        return degree;
    }
    //计算密钥的一位
    private static char calcuKey(StringBuilder stringBuilder){
        Map<Character,StringBuilder> map = new HashMap<>();
        //用a到z解密
        for (char item = 'a';item<='z';item++){
            StringBuilder freshStringBuilder = new StringBuilder();
            for (int i = 0;i<stringBuilder.length();i++){
                char oldChar = stringBuilder.charAt(i);
                int remainder = oldChar-item;
                char freshChar;
                if (remainder<0){
                    freshChar = (char) ('z'+remainder+1);
                }else {
                    freshChar = (char) (remainder+'a');
                }
                freshStringBuilder.append(freshChar);
            }
            map.put(item,freshStringBuilder);
        }
        Set<Character> keys = map.keySet();
        Map<Character,Double> fitMap = new HashMap<>();
        for (char key:keys){
            Map<Character, Double> characterIntegerMap = analyseChar(map.get(key));
            Map<Character, Double> proportion = calcuProportion(characterIntegerMap);
            double fitDegree = calcuFitDegree(proportion);
            fitMap.put(key,fitDegree);
        }
        double temp = 26;
        char key = ' ';
        for (char c:keys){
            Double fitDegree = fitMap.get(c);
            if (fitDegree<temp){
                temp = fitDegree;
                key = c;
            }
        }
        return key;
    }

    public static Map<Integer,StringBuilder> calcuKey(Map<Integer,StringBuilder[]> map){
        Set<Integer> keyLengths = map.keySet();
        Map<Integer,StringBuilder> keyMap = new HashMap<>();
        for (int keyLength:keyLengths){
            StringBuilder[] stringBuilders = map.get(keyLength);
            StringBuilder key = new StringBuilder();
            for (int i = 0;i<keyLength;i++){
                char c = calcuKey(stringBuilders[i]);
                key.append(c);
            }
            keyMap.put(keyLength,key);
        }
//        System.out.println(keyMap);
        return keyMap;
    }
//  67
//  a b c d e f g h i j k l m n o p q r s t u v w x y z
//  b c d e f g h i j k l m n o p q r s t u v w x y z a
//  c d e f g h i j k l m n o p q r s t u v w x y z a b
//  n o p q r s t u v w x y z a b c d e f g h i j k l m
//  s t u v w x y z a b c d e f g h i j k l m n o p q r

//    thewordthethethethe
//    asdfasdfasdfasdfasdf
//    apb
//    12
//    1 2 3 4 6 12
//    a-z  26String  1
//    2    52        2
}
