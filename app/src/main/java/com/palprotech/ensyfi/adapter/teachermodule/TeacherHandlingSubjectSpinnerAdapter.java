package com.palprotech.ensyfi.adapter.teachermodule;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.palprotech.ensyfi.R;

import java.util.ArrayList;

/**
 * Created by Admin on 23-08-2017.
 */

public class TeacherHandlingSubjectSpinnerAdapter extends ArrayAdapter {

    private static final String TAG = "TeacherHandlingSubject";

    private Activity context;
    private ArrayList<String> values;


    public TeacherHandlingSubjectSpinnerAdapter(Activity context, int resource, ArrayList<String> values) {
        super(context, resource, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        // Log.d(TAG,"getView called");
        if (convertView == null) {
            LayoutInflater inflater = (context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.handling_subject_dropdown_item, parent, false);

            holder = new ViewHolder();
            holder.txtCityName = (TextView) convertView.findViewById(R.id.txt_city_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtCityName.setText(values.get(position));

        // ... Fill in other views ...
        return convertView;
    }

    public class ViewHolder {
        public TextView txtCityName;
    }
}
