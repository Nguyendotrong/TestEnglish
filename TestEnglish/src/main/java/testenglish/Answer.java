/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testenglish;

/**
 *
 * @author Nguyen Do Trong
 */
public class Answer {
    private String content;
    private int AnswerId;
    
    public Answer(){
        
    }
    
    public Answer(String cont){
        this.content = cont;
    }

    @Override
    public String toString() {
        return this.content;
    }
    
    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the AnswerId
     */
    public int getAnswerId() {
        return AnswerId;
    }

    /**
     * @param AnswerId the AnswerId to set
     */
    public void setAnswerId(int AnswerId) {
        this.AnswerId = AnswerId;
    }
}
