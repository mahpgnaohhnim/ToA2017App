package com.mahpgnaohhnim.aco.kassensystem.toa2017;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by henry on 04.10.17.
 */

class CSVListAdapter extends ArrayAdapter<String[]> {

    int groupid;
    List<String[]> items;
    Context context;

    CSVListAdapter(Context context, int vg, List<String[]> items){
        super(context, vg,items);
        this.context = context;
        groupid = vg;
        this.items = items;
    }

    static class ViewHolder{
        TextView txtAdult;
        TextView txtChild;
        TextView txtGeneration;
        TextView txtRelation;
        TextView txtOrigin;
        TextView txtPlz;
        TextView txtSum;
        TextView txtBarcode;
        TextView txtDate;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if(rowView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.txtAdult = (TextView) rowView.findViewById(R.id.csvItemTxtAdult);
            viewHolder.txtChild = (TextView) rowView.findViewById(R.id.csvItemTxtChild);
            viewHolder.txtGeneration = (TextView) rowView.findViewById(R.id.csvItemTxtGeneration);
            viewHolder.txtRelation = (TextView) rowView.findViewById(R.id.csvItemTxtRelation);
            viewHolder.txtOrigin = (TextView) rowView.findViewById(R.id.csvItemTxtOrigin);
            viewHolder.txtPlz = (TextView) rowView.findViewById(R.id.csvItemTxtPlz);
            viewHolder.txtSum = (TextView) rowView.findViewById(R.id.csvItemTxtSum);
            viewHolder.txtBarcode = (TextView) rowView.findViewById(R.id.csvItemTxtBarcode);
            viewHolder.txtDate = (TextView) rowView.findViewById(R.id.csvItemTxtDate);
            rowView.setTag(viewHolder);
        }

        // Fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        String[] row=items.get(position);
        holder.txtAdult.setText(row[0]);
        holder.txtChild.setText(row[1]);
        holder.txtGeneration.setText(row[2]);
        holder.txtRelation.setText(row[3]);
        holder.txtOrigin.setText(row[4]);
        holder.txtPlz.setText(row[5]);
        holder.txtBarcode.setText(row[6]);
        holder.txtSum.setText(row[7]);
        holder.txtDate.setText(row[8]);

        return rowView;
    }
}
