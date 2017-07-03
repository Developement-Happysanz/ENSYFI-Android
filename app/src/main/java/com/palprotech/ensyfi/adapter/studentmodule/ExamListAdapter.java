package com.palprotech.ensyfi.adapter.studentmodule;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.palprotech.eduappparentsstudents.R;
import com.palprotech.eduappparentsstudents.app.AppController;
import com.palprotech.eduappparentsstudents.bean.dashboard.Exams;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by Admin on 17-05-2017.
 */

public class ExamListAdapter extends BaseAdapter{

    private static final String TAG = ExamListAdapter.class.getName();
    private final Transformation transformation;
    private Context context;
    private ArrayList<Exams> exams;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices =new ArrayList<Integer>();
    private ImageLoader imageLoader = AppController.getInstance().getUniversalImageLoader();

    public ExamListAdapter(Context context, ArrayList<Exams> exams) {
        this.context = context;
        this.exams = exams;

        transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(0)
                .oval(false)
                .build();
        mSearching = false;
    }


    @Override
    public int getCount() {
        if(mSearching){
            // Log.d("Event List Adapter","Search count"+mValidSearchIndices.size());
            if(!mAnimateSearch){
                mAnimateSearch = true;
            }
            return mValidSearchIndices.size();

        }else{
            // Log.d(TAG,"Normal count size");
            return exams.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if(mSearching){
            return exams.get(mValidSearchIndices.get(position));
        }else {
            return exams.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.exam_list_item, parent, false);

            holder = new ViewHolder();
            holder.txtExamName = (TextView) convertView.findViewById(R.id.txtExamName);
            holder.txtStartDate = (TextView) convertView.findViewById(R.id.txtStartDate);
            holder.txtEndDate = (TextView) convertView.findViewById(R.id.txtEndDate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(mSearching){
            // Log.d("Event List Adapter","actual position"+ position);
            position = mValidSearchIndices.get(position);
            //Log.d("Event List Adapter", "position is"+ position);

        }else{
            Log.d("Event List Adapter","getview pos called"+ position);
        }


        Exams exam = exams.get(position);

        holder.txtExamName.setText(exams.get(position).getExamName());
        holder.txtStartDate.setText(exams.get(position).getFromDate());
        holder.txtEndDate.setText(exams.get(position).getToDate());


        return convertView;
    }

    //helper for parsing the date from string. Expected format "10-28-2015 00:00:00"
    public static String getDate(String dateTime){
        String dateVal = null;
        try {
            if ((dateTime != null) && !dateTime.isEmpty() && dateTime.length() >= 10) {
                dateVal = dateTime.substring(0, 10);
                String month = getMonthName(Integer.parseInt(dateVal.substring(0, 2)));
                String day = dateVal.substring(3, 5);
                String year = dateVal.substring(6,10);
                if( (month != null) && (day != null) && (year != null)){
                    dateVal = month+"  "+ day+ ","+ year+" ";
                }

            }
        }catch (NumberFormatException numE){
            numE.printStackTrace();

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return dateVal;
    }

    public void startSearch(String eventName){
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter","serach for event"+eventName);
        mValidSearchIndices.clear();
        for(int i =0; i< exams.size(); i++){
            String homeWorkTitle = exams.get(i).getExamName();
            if((homeWorkTitle != null) && !(homeWorkTitle.isEmpty())){
                if( homeWorkTitle.toLowerCase().contains(eventName.toLowerCase())){
                    mValidSearchIndices.add(i);
                }

            }

        }
        Log.d("Event List Adapter","notify"+ mValidSearchIndices.size());
        //notifyDataSetChanged();
    }

    public void exitSearch(){
        mSearching = false;
        mValidSearchIndices.clear();
        mAnimateSearch = false;
        // notifyDataSetChanged();
    }

    public void clearSearchFlag(){
        mSearching = false;
    }

    public class ViewHolder {
        public TextView txtExamName,txtEndDate,txtStartDate;
    }

    public boolean ismSearching() {
        return mSearching;
    }

    public int getActualEventPos(int selectedSearchpos){
        if(selectedSearchpos < mValidSearchIndices.size()) {
            return mValidSearchIndices.get(selectedSearchpos);
        }else{
            return 0;
        }
    }

    private static  String getMonthName(int month){
        String monthVal = null;
        switch (month){
            case 1:
                monthVal = "Jan";
                break;
            case 2:
                monthVal = "Feb";
                break;
            case 3:
                monthVal = "Mar";
                break;
            case 4:
                monthVal = "Apr";
                break;
            case 5:
                monthVal = "May";
                break;
            case 6:
                monthVal = "Jun";
                break;
            case 7:
                monthVal = "Jul";
                break;
            case 8:
                monthVal = "Aug";
                break;
            case 9:
                monthVal = "Sep";
                break;
            case 10:
                monthVal = "Oct";
                break;
            case 11:
                monthVal = "Nov";
                break;
            case 12:
                monthVal = "Dec";
                break;
        }

        return  monthVal;
    }
}
