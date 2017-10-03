package com.mahpgnaohhnim.aco.kassensystem.toa2017;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

/**
 * Created by henry on 04.09.17.
 */

public class MainActivity extends Activity {

    public static final int REQUEST_CODE = 100;
    LinearLayout contentContainer;
    int totalSum;
    ItemLinearLayout adultItem, childItem;
    SelectionDropDownLinearLayout generationDropDown, relationDropdown, originDropDown;
    Button scanBtn, showCSVBtn, submitBtn;
    String barcodeResult;
    CSVFileHandler fileHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //request Fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        fileHandler = new CSVFileHandler(this);
        contentContainer = (LinearLayout) findViewById(R.id.contentContainer);
        adultItem = new ItemLinearLayout(this, "Erwachsener", 6);
        childItem = new ItemLinearLayout(this, "Kind", 3);
        LinearLayout totalSumLL = (LinearLayout) View.inflate(this,R.layout.summary_value_linearlayout,null);
        generationDropDown = new SelectionDropDownLinearLayout(this,"Altersgruppe", R.array.generationList);
        relationDropdown = new SelectionDropDownLinearLayout(this, "Beziehung", R.array.beziehungList);
        originDropDown = new SelectionDropDownLinearLayout(this, "Herkunft", R.array.originList);
        totalSum = 0;

        contentContainer.addView(adultItem);
        contentContainer.addView(childItem);
        contentContainer.addView(generationDropDown);
        contentContainer.addView(relationDropdown);
        contentContainer.addView(originDropDown);
        contentContainer.addView(totalSumLL);

        RelativeLayout root = (RelativeLayout) findViewById(R.id.mainRootView);
        LinearLayout footer = (LinearLayout) View.inflate(this, R.layout.footerbtns_linearlayout,null);
        RelativeLayout.LayoutParams footerParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        footerParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        footerParam.bottomMargin = 2;
        footer.setLayoutParams(footerParam);


        root.addView(footer);

        scanBtn = (Button) findViewById(R.id.scanQRBtn);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        showCSVBtn = (Button) findViewById(R.id.showCSVBtn);
        scanBtn.setOnClickListener(myListener);
        submitBtn.setOnClickListener(myListener);
        showCSVBtn.setOnClickListener(myListener);


    }

    public void updateTotalSum(){
        calcTotalSum();
        TextView totalSumLabel = (TextView) findViewById(R.id.totalSum);
        if(totalSum == 0){
            totalSumLabel.setText("0€");
        }else {
            totalSumLabel.setText(Float.toString(totalSum) + "€");
        }
    }

    private void calcTotalSum(){
        int adultSum = adultItem.sellPrice * adultItem.quantity;
        int childSum = childItem.sellPrice * childItem.quantity;
        totalSum = adultSum + childSum;
    }


    private void resetStats() {
        adultItem.resetStats();
        childItem.resetStats();
        generationDropDown.reset();
        relationDropdown.reset();
        originDropDown.reset();
        updateTotalSum();
    }

    private void onSubmit(){
        if(totalSum != 0.0){
            String entry = getCurrentEntry();
            fileHandler.writeFile(entry);
            Toast.makeText(getApplicationContext(),"Submit", Toast.LENGTH_LONG).show();
            resetStats();
        }
        else{
            Toast.makeText(getApplicationContext(),"Summe ist 0!", Toast.LENGTH_LONG).show();
        }

    }

    private String getCurrentEntry(){
        String currentEntry = "";
            currentEntry += childItem.getCount()+";";
            currentEntry += adultItem.getCount()+";";
            currentEntry += generationDropDown.getVal()+";";
            currentEntry += relationDropdown.getVal()+";";
            currentEntry += originDropDown.getVal()+";";
            currentEntry += totalSum+";";


        return currentEntry;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null){
                Barcode barcode = data.getParcelableExtra("barcode");
                barcodeResult = barcode.rawValue;
                Toast.makeText(getApplicationContext(),barcodeResult, Toast.LENGTH_LONG).show();

            }
        }

    }

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.showCSVBtn:
                    startActivity(new Intent(MainActivity.this, CSVViewerActivity.class));
                    break;

                case R.id.submitBtn:
                    onSubmit();
                    resetStats();
                    break;

                case R.id.scanQRBtn:
                    Intent barCodeScannerIntent = new Intent(MainActivity.this, BarCodeScannerActivity.class);
                    startActivityForResult(barCodeScannerIntent, REQUEST_CODE);
                    break;

                default:
                    break;
            }
        }
    };


}
