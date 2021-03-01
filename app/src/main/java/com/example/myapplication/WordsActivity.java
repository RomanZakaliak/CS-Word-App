package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.myapplication.Data.Word;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordsActivity extends AppCompatActivity {
    private static String TAG = "WordActivity";
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private FirebaseDatabase database;
    private DatabaseReference userReference;

    private Button saveWord;
    private TextInputLayout word;
    private TextInputLayout translation;
    private TextInputLayout usageExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance(getResources().getString(R.string.realtime_db_reference));
        userReference = database.getReference("users").child(currentUser.getUid().toString()).child("words");

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UpdateWordsList(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        saveWord = findViewById(R.id.save_word);
        saveWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWordToDb();
            }
        });
    }

    private void UpdateWordsList(DataSnapshot snapshot) {
        GenericTypeIndicator<HashMap<String, Word>> type = new GenericTypeIndicator<HashMap<String, Word>>() {};
        HashMap<String, Word> hashMap = (HashMap<String, Word>) snapshot.getValue(type);
        if( hashMap == null){
            return;
        } else{
            ArrayList<Word> wordEtities = new ArrayList<Word>(hashMap.values());

            List<Map<String, String>> data = new ArrayList<Map<String, String>>();

            for (Word currentWord:wordEtities) {
                Map<String, String> currItemData = new HashMap<String, String>(2);
                currItemData.put("title", currentWord.getWord());
                currItemData.put("subtitle", currentWord.getTranslations());
                data.add(currItemData);
            }

            ListView wordsList = findViewById(R.id.words_list);

            SimpleAdapter adapter  = new SimpleAdapter(this, data, R.layout.word_item_layout,
                                                        new String[] {"title", "subtitle"},
                                                        new int[] {R.id.word_title, R.id.word_subtitle});
            wordsList.setAdapter(adapter);

        }
    }

    private void saveWordToDb() {
        word = findViewById(R.id.word);
        translation = findViewById(R.id.translation);
        usageExample = findViewById(R.id.usage_example);

        String wordText = word.getEditText().getText().toString();
        String translationText = translation.getEditText().getText().toString();
        String usageExampleText = usageExample.getEditText().getText().toString();

        if(wordText.isEmpty() || translationText.isEmpty()){
            Toast.makeText(this, "Слово та його переклад є обов'язковими", Toast.LENGTH_SHORT).show();
            return;
        }

        Word wordEntity = new Word(wordText, translationText, usageExampleText);
        try {
            userReference.child(wordText).setValue(wordEntity);
        } catch (Exception e){
            Log.d(TAG, e.getMessage(), e);
            return;
        }
        Toast.makeText(this, "Слово збережене успішно!", Toast.LENGTH_SHORT).show();
    }
}