package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.Data.Word;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
        userReference = database.getReference("users").child(currentUser.getUid().toString());

        saveWord = findViewById(R.id.save_word);
        saveWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWordToDb();
            }
        });
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

        Query isWordExists = userReference.child(wordText).equalTo(wordText);
        isWordExists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String currWord = snapshot.child("word").getValue(String.class);
                    Toast.makeText(WordsActivity.this, currWord, Toast.LENGTH_SHORT).show();
                } else{

                    Log.w(TAG, "snap" + snapshot.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "An error occured!");
            }
        });

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