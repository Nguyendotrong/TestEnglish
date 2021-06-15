/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testenglish;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nguyen Do Trong
 */
public class MultipleChoice implements Question {
    protected int idQuestion;
    
    protected String content;
    protected List<Answer> listAnswer = new ArrayList<>();
    protected  Answer trueAnswer;
    protected Category category;
    protected Level level;
    protected Answer userchon;
    
    public MultipleChoice(Category cate,Level leve, String cont){
       // this.idQuestion = id;
        this.content = cont;       
        this.category = cate;
        this.level = leve;
    }

    public MultipleChoice() {
       
    }
    
    /**     
     * @param answ
     * add answer into list answer of question
     */
    @Override
    public void addAnswer(Answer answ)  throws Exception {
        
        this.listAnswer.add(answ);
        
    }

    @Override
    public boolean isCorrect(int index) {
        return listAnswer.get(index) == trueAnswer;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s\n", this.content));
        for(int i = 0; i < this.listAnswer.size(); i++ )
            builder.append(String.format("%s. %s\t",(char) (i+65),
                    this.listAnswer.get(i)));
         
        return builder.toString();
    }

    @Override
    public String getContent() {
      return this.content;
    }

    @Override
    public void setContent(String cont) {
        this.content = cont;
    }

    @Override
    public List<Answer> getListAnswer() {
        return this.listAnswer;
    }

    @Override
    public void setListAnswer(List<Answer> list) {
        this.listAnswer  = list;
    }

    @Override
    public Answer getTrueAnswer() {
        return this.trueAnswer;
    }

    @Override
    public void setTrueAnswer(Answer answer) {
        this.trueAnswer = answer;
    }

    @Override
    public void setIdQueston(int x) {
        this.idQuestion = x;
    }

    @Override
    public int getIdQuestion() {
       return this.idQuestion;
    }

    @Override
    public Category getCategory() {
        return this.category;
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public Level getLevel() {
        return this.level;
    }


}
