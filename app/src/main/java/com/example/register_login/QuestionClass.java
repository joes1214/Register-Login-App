package com.example.register_login;

public class QuestionClass {
    private int answerID;
    private int correctAnswer;
    private int questionAID;
    private int questionBID;
    private int questionCID;
    private int questionDID;
    private int imgID;

    private boolean qIsTF;
    /*
     * The correct answer will either be 0, 1, 2, 3
     * */
    public QuestionClass(int answerID, int correctAnswer, int questionAID, int questionBID, int questionCID,
        int questionDID, boolean qisTF, int imgID){

        this.answerID = answerID;
        this.correctAnswer = correctAnswer;
        this.questionAID = questionAID;
        this.questionBID = questionBID;
        this.questionCID = questionCID;
        this.questionDID = questionDID;
        this.qIsTF = qisTF;
        this.imgID = imgID;
    }

    public int getAnswerID() {
        return answerID;
    }

    public int isCorrectAnswer() {
        return correctAnswer;
    }

    public int getQuestionAID() {
        return questionAID;
    }

    public int getQuestionBID() {
        return questionBID;
    }

    public int getQuestionCID() {
        return questionCID;
    }

    public int getQuestionDID() {
        return questionDID;
    }

    public boolean isqIsTF() {
        return qIsTF;
    }

    public int getImgID(){
        return imgID;
    }

}
