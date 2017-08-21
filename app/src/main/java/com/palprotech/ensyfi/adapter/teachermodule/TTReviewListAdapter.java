package com.palprotech.ensyfi.adapter.teachermodule;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

//import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.teacher.viewlist.TTReview;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Admin on 20-07-2017.
 */

public class TTReviewListAdapter extends BaseAdapter {

//    private final Transformation transformation;
    private Context context;
    private ArrayList<TTReview> ttReviews;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();

    Comparator<TTReview> myComparator = new Comparator<TTReview>() {
        public int compare(TTReview obj1, TTReview obj2) {
            return obj1.getTimeDate().compareTo(obj2.getTimeDate());
        }
    };

    public TTReviewListAdapter(Context context, ArrayList<TTReview> ttReviews) {
        this.context = context;
        this.ttReviews = ttReviews;
        Collections.sort(ttReviews, Collections.reverseOrder(myComparator));
//        transformation = new RoundedTransformationBuilder()
//                .cornerRadiusDp(0)
//                .oval(false)
//                .build();
        mSearching = false;
    }

    @Override
    public int getCount() {
        if (mSearching) {
            if (!mAnimateSearch) {
                mAnimateSearch = true;
            }
            return mValidSearchIndices.size();
        } else {
            return ttReviews.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return ttReviews.get(mValidSearchIndices.get(position));
        } else {
            return ttReviews.get(position);
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
            convertView = inflater.inflate(R.layout.ttreview_list_item, parent, false);

            holder = new TTReviewListAdapter.ViewHolder();
            holder.txtClassName = (TextView) convertView.findViewById(R.id.txtClassName);
            holder.txtSubjectName = (TextView) convertView.findViewById(R.id.txtSubjectName);
            holder.txtDay = (TextView) convertView.findViewById(R.id.txtDay);
            holder.txtTimeDate = (TextView) convertView.findViewById(R.id.txtTimeDate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mSearching) {
            position = mValidSearchIndices.get(position);
        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }
        holder.txtClassName.setText(ttReviews.get(position).getClassName() + "-" + ttReviews.get(position).getSectionName());
        holder.txtSubjectName.setText(ttReviews.get(position).getSubjectName());
        holder.txtDay.setText(ttReviews.get(position).getDay());
        holder.txtTimeDate.setText(ttReviews.get(position).getTimeDate());
        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < ttReviews.size(); i++) {
            String onDutyTitle = ttReviews.get(i).getComments();
            if ((onDutyTitle != null) && !(onDutyTitle.isEmpty())) {
                if (onDutyTitle.toLowerCase().contains(eventName.toLowerCase())) {
                    mValidSearchIndices.add(i);
                }
            }
        }
        Log.d("Event List Adapter", "notify" + mValidSearchIndices.size());
    }

    public void exitSearch() {
        mSearching = false;
        mValidSearchIndices.clear();
        mAnimateSearch = false;
    }

    public void clearSearchFlag() {
        mSearching = false;
    }

    public class ViewHolder {
        public TextView txtClassName, txtSubjectName, txtDay, txtTimeDate;
    }

    public boolean ismSearching() {
        return mSearching;
    }

    public int getActualEventPos(int selectedSearchpos) {
        if (selectedSearchpos < mValidSearchIndices.size()) {
            return mValidSearchIndices.get(selectedSearchpos);
        } else {
            return 0;
        }
    }
}
