package com.example.finalproject.domain;

public class Question {

    private Long id;
    private String question;
    private Boolean thruth;
    private Long deck;

    public Question(Long id, String question, Boolean thruth, Long deck) {
        this.id = id;
        this.question = question;
        this.thruth = thruth;
        this.deck = deck;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Boolean getThruth() {
        return thruth;
    }

    public void setThruth(Boolean thruth) {
        this.thruth = thruth;
    }

    public Long getDeck() {
        return deck;
    }

    public void setDeck(Long deck) {
        this.deck = deck;
    }

    @Override
    public String toString() {
        return this.getQuestion();
    }
}