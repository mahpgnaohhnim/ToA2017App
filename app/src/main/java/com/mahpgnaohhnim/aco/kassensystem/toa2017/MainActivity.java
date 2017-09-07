package com.mahpgnaohhnim.aco.kassensystem.toa2017;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by henry on 04.09.17.
 */

public class MainActivity extends Activity {

    LinearLayout contentContainer;
    Float totalSum;
    ItemLinearLayout adultItem, childItem;
    SelectionDropDownLinearLayout generationDropDown, relationDropdown, originDropDown;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //request Fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        contentContainer = (LinearLayout) findViewById(R.id.contentContainer);
        adultItem = new ItemLinearLayout(this, "Erwachsener", 4.5f);
        childItem = new ItemLinearLayout(this, "Kind", 1.5f);
        LinearLayout totalSum = (LinearLayout) View.inflate(this,R.layout.summary_value_linearlayout,null);
        generationDropDown = new SelectionDropDownLinearLayout(this,"Altersgruppe", R.array.generationList);
        relationDropdown = new SelectionDropDownLinearLayout(this, "Beziehung", R.array.beziehungList);
        originDropDown = new SelectionDropDownLinearLayout(this, "Herkunft", R.array.originList);

        contentContainer.addView(adultItem);
        contentContainer.addView(childItem);
        contentContainer.addView(generationDropDown);
        contentContainer.addView(relationDropdown);
        contentContainer.addView(originDropDown);
        contentContainer.addView(totalSum);

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
        Float adultSum = adultItem.sellPrice * adultItem.quantity;
        Float childSum = childItem.sellPrice * childItem.quantity;
        totalSum = adultSum + childSum;
    }



}
