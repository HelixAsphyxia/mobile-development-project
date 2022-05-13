package com.example.finalproject.choosers;

public abstract class QuestionChooser {
    protected int size;

    public QuestionChooser(int size) {
        this.size = size;
    }

    public abstract int nextQuestion();

}
