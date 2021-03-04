package com.example.myapplication.Adapters;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Data.Word;
import com.example.myapplication.R;
import com.example.myapplication.ShowWordFragment;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class WordsListAdapter extends ArrayAdapter<Word> {
    private LayoutInflater inflater;
    private final int layout;
    private final List<Word> words;
    private final Context context;
    private final DatabaseReference userWordsReference;

    public WordsListAdapter(Context context, int resource, List<Word> words, DatabaseReference userWordsReference){
        super(context, resource, words);
        this.words = words;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.userWordsReference = userWordsReference;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Word word = words.get(position);
        viewHolder.titleView.setText(word.getWord());
        viewHolder.subtitleView.setText(word.getTranslations());
        viewHolder.itemMenuBtn.setOnClickListener(v -> {
            PopupMenu itemMenu = new PopupMenu(context, viewHolder.itemMenuBtn);
            itemMenu.inflate(R.menu.word_item_menu);
            itemMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.delete_word:
                            AlertDialog.Builder a_builder = new AlertDialog.Builder(context);
                            a_builder.setMessage("Ти дійсно бажаєш видалити це слово?")
                                    .setCancelable(false)
                                    .setPositiveButton("Так", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            userWordsReference.child(word.getWord()).removeValue();
                                            WordsListAdapter.this.remove(word);
                                            WordsListAdapter.this.notifyDataSetChanged();
                                        }
                                    })
                                    .setNegativeButton("Ні", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert = a_builder.create();
                            alert.setTitle("Видалення слова");
                            alert.show();
                            break;
                        case R.id.show_word_details:
                            FragmentTransaction ft = ((AppCompatActivity)context).getFragmentManager().beginTransaction();
                            Fragment previous = ((AppCompatActivity)context).getFragmentManager().findFragmentByTag("details");

                            if(previous != null){
                                ft.remove(previous);
                            }
                            ShowWordFragment showWord = ShowWordFragment.newInstance(word.getWord(),
                                    word.getTranslations(), word.getUsageExamples());
                            showWord.show(ft, "details");
                            break;
                    }
                    return false;
                }
            });
            itemMenu.show();
        });

        return convertView;
    }

    private static class ViewHolder{
        final ImageButton itemMenuBtn;
        final TextView titleView, subtitleView;

        ViewHolder(View view){
            titleView = (TextView) view.findViewById(R.id.word_title);
            subtitleView = (TextView) view.findViewById(R.id.word_subtitle);
            itemMenuBtn = (ImageButton) view.findViewById(R.id.btn_show_popup_menu);
        }
    }
}
