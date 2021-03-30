package com.example.CSWordApp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class ShowWordFragment extends DialogFragment {

    Button closeFragment;

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
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        String word = getArguments().getString("word");
        String translation = getArguments().getString("translation");
        String usageExample = getArguments().getString("usage_example");

        View v = View.inflate( dialog.getContext(), R.layout.fragment_show_word, null);
        dialog.setContentView(v);
        dialog.getWindow().setBackgroundDrawable(null);

        TextView wordDetail = v.findViewById(R.id.word_detail);
        TextView translationDetail = v.findViewById(R.id.translation_detail);
        TextView usageExampleDetail = v.findViewById(R.id.usage_example_detail);

        closeFragment = v.findViewById(R.id.close_details_btn);

        wordDetail.setText(word);
        translationDetail.setText(translation);
        usageExampleDetail.setText(usageExample);

        closeFragment.setOnClickListener((view)->{
            getFragmentManager().beginTransaction().remove(ShowWordFragment.this).commit();
        });

        return dialog;
    }
}