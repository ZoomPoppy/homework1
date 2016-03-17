package com.company.zz;

import java.io.*;

/**
 * Created by zz on 2016/3/7.
 */
public class FileUtil {
    public StringBuilder getFileString(){
        File file = new File("011.txt");
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            int  temp  = 0;
            while((temp = bufferedReader.read())!=-1){
                char aChar = (char) temp;

                if ((aChar>='a'&&aChar<='z')||(aChar>='A'&&aChar<='Z')){
                     stringBuilder.append(String.valueOf(aChar).toLowerCase());
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder;
    }
    public static void writeToFile(StringBuilder stringBuilder,String fileName){
        File file = new File(fileName);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static StringBuilder readToStringBuilder(File file){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            int  temp  = 0;
            while((temp = bufferedReader.read())!=-1){
                char aChar = (char) temp;
                stringBuilder.append(aChar);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder;

    }

}
