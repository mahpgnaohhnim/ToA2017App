package com.mahpgnaohhnim.aco.kassensystem.toa2017;

import android.content.Context;
import android.content.res.Resources;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by henry on 07.09.17.
 */

class SelectionDropDownLinearLayout extends LinearLayout {
    Context context;
    TextView nameLabel;
    Spinner dropDown;

    SelectionDropDownLinearLayout(Context mainContext, String name, int list){
        super(mainContext);
        context = (MainActivity) mainContext;

        LinearLayout.LayoutParams myParam = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT, 1f);
        myParam.setMargins(0,10,0,0);

        nameLabel = new TextView(context);
        dropDown = new Spinner(context);

        String[] listArr = getResources().getStringArray(list);
        ArrayAdapter<String> dropDownAdapter = new ArrayAdapter<String>(mainContext,R.layout.support_simple_spinner_dropdown_item, listArr);
        dropDownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDown.setAdapter(dropDownAdapter);

        nameLabel.setText(name);
        nameLabel.setLayoutParams(myParam);

        dropDown.setLayoutParams(myParam);
        dropDown.setBackgroundColor(getResources().getColor(R.color.white));

        this.addView(nameLabel);
        this.addView(dropDown);
    }

    void reset(){
        dropDown.setSelection(0);
    }
}
