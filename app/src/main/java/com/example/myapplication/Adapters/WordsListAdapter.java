package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Data.Word;
import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class WordsListAdapter extends ArrayAdapter<Word> {
    private LayoutInflater inflanter;
    private int layout;
    private List<Word> words;
    private Context context;
    private DatabaseReference userWordsReference;

    public WordsListAdapter(Context context, int resource, List<Word> words, DatabaseReference userWordsReference){
        super(context, resource, words);
        this.words = words;
        this.layout = resource;
        this.inflanter = LayoutInflater.from(context);
        this.context = context;
        this.userWordsReference = userWordsReference;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = inflanter.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Word word = words.get(position);
        viewHolder.titleView.setText(word.getWord());
        viewHolder.subtitleView.setText(word.getTranslations());
        viewHolder.itemMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu itemMenu = new PopupMenu(context, viewHolder.itemMenuBtn);
                itemMenu.inflate(R.menu.word_item_menu);
                itemMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.delete_word:
                                userWordsReference.child(word.getWord()).removeValue();
                                break;
                        }
                        return false;
                    }
                });
                itemMenu.show();
            }
        });

        return convertView;
    }

    private class ViewHolder{
        final ImageButton itemMenuBtn;
        final TextView titleView, subtitleView;

        ViewHolder(View view){
            titleView = (TextView) view.findViewById(R.id.word_title);
            subtitleView = (TextView) view.findViewById(R.id.word_subtitle);
            itemMenuBtn = (ImageButton) view.findViewById(R.id.btn_show_popup_menu);
        }
    }
}
