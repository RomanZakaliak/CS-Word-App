package com.example.myapplication.Data;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Word {
    private String word;
    private String translations;
    private String usageExamples = null;

    public  Word(){}
    public Word(String word, String translate){
        this.word = word;
        this.translations = translate;
    }

    public Word(String word, String translate, String usageExample){
        this.word = word;
        this.translations = translate;
        this.usageExamples = usageExample;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setTranslations(String translations) {
        this.translations = translations;
    }

    public void setUsageExamples(String usageExamples) {
        this.usageExamples = usageExamples;
    }

    public String getWord() {
        return word;
    }

    public String getTranslations() {
        return translations;
    }

    public String getUsageExamples() {
        return usageExamples;
    }
}
