package com.example.myapplication;

import android.app.DialogFragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class ShowWordFragment extends DialogFragment {
    String word;
    String translation;
    String usageExample;

    public static ShowWordFragment newInstance(String word, String translation, String usageExample) {
        ShowWordFragment f = new ShowWordFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("word", word);
        args.putString("translation", translation);
        args.putString("usage_example", usageExample);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        word = getArguments().getString("word");
        translation = getArguments().getString("translation");
        usageExample = getArguments().getString("usage_example");
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_show_word, container, false);
        TextView wordDetail = (TextView)v.findViewById(R.id.word_detail);
        TextView translationDetail = (TextView)v.findViewById(R.id.translation_detail);
        TextView usageExampleDetail = (TextView)v.findViewById(R.id.usage_example_detail);

        wordDetail.setText(word);
        translationDetail.setText(translation);
        usageExampleDetail.setText(usageExample);
        return v;
    }
}