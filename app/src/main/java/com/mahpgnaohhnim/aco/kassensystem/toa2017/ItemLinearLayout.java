package com.mahpgnaohhnim.aco.kassensystem.toa2017;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by henry on 06.09.17.
 */
public class ItemLinearLayout extends LinearLayout {
    String itemName;
    TextView nameLabel, quantityLabel;
    int quantity;
    Float sellPrice;
    final Button subBtn, addBtn;
    MainActivity mainActivity;
    LinearLayout rightContainer;


    public ItemLinearLayout(final Context context, String name, final Float price){
        super(context);
        mainActivity = (MainActivity) context;

        LinearLayout.LayoutParams myLayoutParam = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);

        this.setLayoutParams(myLayoutParam);

        this.itemName = name;
        this.sellPrice = price;
        this.quantity = 0;

        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,1f);
        LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f);

        rightContainer = new LinearLayout(context);
        rightContainer.setLayoutParams(containerParams);
        rightContainer.setOrientation(HORIZONTAL);
        nameLabel = new TextView(context);
        nameLabel.setLayoutParams(childParams);
        nameLabel.setText(this.itemName);
        nameLabel.setGravity(Gravity.CENTER_VERTICAL| Gravity.LEFT);

        quantityLabel = new TextView(context);
        addBtn = new Button(context);
        subBtn = new Button(context);

        quantityLabel.setLayoutParams(childParams);
        quantityLabel.setTypeface(null, Typeface.BOLD);
        quantityLabel.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        quantityLabel.setText("0");

        addBtn.setLayoutParams(childParams);
        addBtn.setText("+");
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                quantity += 1;
                updateLabels();
            }
        });

        subBtn.setLayoutParams(childParams);
        subBtn.setText("-");
        subBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(quantity>0) {
                    quantity -= 1;
                    updateLabels();
                }
            }
        });

        this.addView(nameLabel);
        this.addView(rightContainer);

        rightContainer.addView(subBtn);
        rightContainer.addView(quantityLabel);
        rightContainer.addView(addBtn);
    }

    private void updateLabels(){
        quantityLabel.setText(Integer.toString(this.quantity));
        mainActivity.updateTotalSum();
    }

    public void resetStats(){
        this.quantity = 0;
        updateLabels();
    }

    public String getCount(){
        return Integer.toString(quantity);
    }

}
