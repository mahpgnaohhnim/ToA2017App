package com.mahpgnaohhnim.aco.kassensystem.toa2017;

/**
 * Created by henry on 01.10.17.
 */

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.lang.String;


class CSVFileHandler {

    String header ="Kind;" +
            "Erwachsener;"+
            "Altersgruppe;"+
            "Beziehung;"+
            "Herkunft;"+
            "GesamtPreis;"+
            "Datum;\n";

    private Context context;


    String fileName = "Eintrag.csv";



    public CSVFileHandler(Context context){
        this.context = context;
    }

    public void writeFile(String input){
        String content = input;
        String timeStamp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        content += timeStamp+";\n";

        try {

            File path = context.getExternalFilesDir(null);
            File file = new File(path, fileName); // external Storage
            FileWriter out = new FileWriter(file, true);
            if(file.length() == 0){
                out.append(header);
            }
            out.append(content);
            out.flush();
            out.close();
        }catch (IOException e){
            Log.e("Exception", "File write failed: " + e.toString());
        }


    }

    public ArrayList<String> getCSVArrList(){
        ArrayList<String> arrList = new ArrayList<>();
        File path = context.getExternalFilesDir(null);
        File file = new File(path, fileName);
        try{

            BufferedReader inputStream = new BufferedReader(new FileReader(file));
            String readData;
            while((readData = inputStream.readLine()) != null){
                //readData = readData.replace(";", "|");
                arrList.add(readData);
            }

        }catch (IOException e){
            Log.e("Exception", "File read failed: " + e.toString());
        }

        return arrList;
    }


    public void rewriteFile(ArrayList<String> list){
        String content = "";
        /*for(String listItem :list){
            content += listItem.replace("|",";")+"\n";
        }*/

        try {
            File path = context.getExternalFilesDir(null);
            File file = new File(path, fileName); // external Storage
            FileWriter out = new FileWriter(file,false);
            out.append(content);
            out.flush();
            out.close();
        }catch (IOException e){
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }

    public void deleteLastLine(){
        try{
            File path = context.getExternalFilesDir(null);
            File file = new File(path, fileName);
            RandomAccessFile f = new RandomAccessFile(file, "rw");
            byte b;
            long length = f.length() - 1;
            do {
                length -= 1;
                f.seek(length);
                b = f.readByte();
            } while(b != 10);
            f.setLength(length+1);
            f.close();
        }catch (IOException e){
            //Exception
        }

    }




}
