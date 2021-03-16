package com.example.CSWordApp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.CSWordApp.R;

import java.util.List;
import java.util.Map;

public class RulesAdapter extends RecyclerView.Adapter<RulesAdapter.ViewHolder> {
    private Map<String,String> data;
    private LayoutInflater layoutInflater;

    public RulesAdapter(Context context, Map<String,String> data){
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public RulesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.fragment_rule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RulesAdapter.ViewHolder holder, int position) {
        String key = data.keySet().toArray()[position].toString();
        String content = data.get(key);

        holder.topTextView.setText(key);
        holder.contentTextView.setText(content);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView topTextView;
        TextView contentTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            topTextView = itemView.findViewById(R.id.rule_top);
            contentTextView = itemView.findViewById(R.id.rule_content);
        }
    }
}
