/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testenglish;

import java.util.List;

/**
 *
 * @author Nguyen Do Trong
 */
public interface Question {

   public void setIdQueston(int x);
   public int getIdQuestion();
   
   public  void addAnswer(Answer answ) throws Exception;



    public boolean isCorrect(int index);
           
  
 
    public String getContent() ;

    /**
     * @param cont the cont to set
     */
    public void setContent(String cont);
    
    /**
     * @return the listAnswer
     */
    public List<Answer> getListAnswer();

    /**
     * @param list the list to set
     */
    public void setListAnswer(List<Answer> list);

    /**
     * @return the trueAnswer
     */
    public Answer getTrueAnswer();

    /**
     * @param answer the answer to set
     */
    public void setTrueAnswer(Answer answer);
    public Category getCategory();
    public void setCategory(Category category);
    public void  setLevel(Level level);
    public Level getLevel();
}
