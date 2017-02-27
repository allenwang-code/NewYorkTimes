package allenwang.newyorktimes.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import allenwang.newyorktimes.Constant;
import allenwang.newyorktimes.R;
import allenwang.newyorktimes.model.Doc;

/**
 * Created by allenwang on 2017/2/26.
 */

public class NewsAdapter  extends
        RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    // Store a member variable for the contacts
    private List<Doc> mDocs;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public NewsAdapter(Context context, List<Doc> docs) {
        mDocs = docs;
        mContext = context;
    }

    public void updateData(List<Doc> docs){
        mDocs = docs;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        Doc doc = mDocs.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.title;
        textView.setText(doc.getLeadParagraph());
        ImageView imageView = holder.imageView;
        if (doc.getMultimedia().size() > 0) {
            String surfix = doc.getMultimedia().get(0).getUrl();
            Picasso.with(getContext()).load(Constant.IMG_URL + surfix).into(imageView);
        } else {
            imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_launcher));
        }
    }

    @Override
    public int getItemCount() {
        return mDocs.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView title;
        public ImageView imageView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.textView4);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}