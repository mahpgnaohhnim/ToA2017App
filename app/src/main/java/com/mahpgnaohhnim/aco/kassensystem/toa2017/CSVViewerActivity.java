package com.mahpgnaohhnim.aco.kassensystem.toa2017;

/**
 * Created by henry on 02.10.17.
 */

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class CSVViewerActivity extends Activity {

    CSVFileHandler csvHandler;
    ListView csvListView;
    ArrayList<String[]> entryArrList;
    CSVListAdapter adapter;
    int selectedItemIndex;


    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.csv_listview_activity);
        entryArrList = new ArrayList<>();

        csvHandler = new CSVFileHandler(this);
        csvListView = (ListView) findViewById(R.id.csvListView);
        csvListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        csvListView.setSelector(R.color.colorPrimary);

        csvListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItemIndex = position;
                long idtest = id;
            }
        });


        Button backBtn = (Button) findViewById(R.id.backBtn);
        Button deleteBtn = (Button) findViewById(R.id.deleteBtn);
        final Button shareBtn = (Button) findViewById(R.id.shareBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitActivity();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                deleteSelectedItem();
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareFile();
            }
        });

        updateList();
    }

    private void exitActivity(){
        finish();
    }

    private void deleteSelectedItem(){
        if(selectedItemIndex > 0) {
            entryArrList.remove(selectedItemIndex);
            csvHandler.rewriteFile(entryArrList);
            updateList();
        }
    }

    private void updateList(){
        entryArrList = csvHandler.getCSVArrList();
        adapter = new CSVListAdapter(this, R.layout.csv_list_item_linearlayout,entryArrList);
        csvListView.setAdapter(adapter);
    }

    private void shareFile(){
        String fileName = "Eintrag.csv";
        File path = this.getExternalFilesDir(null);
        File file = new File(path, fileName); // external Storage

        if(file.exists()) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("application/pdf");
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+file));
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Sharing File");
            shareIntent.putExtra(Intent.EXTRA_TEXT,"Sharing File");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            this.startActivity(Intent.createChooser(shareIntent,"Share File"));
        }
    }

}