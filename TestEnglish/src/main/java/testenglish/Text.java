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
public class Text {
    private int textId;
    private String contentText;
    private List<ParagraphQuestion>  listQuestion = new ArrayList<>();
    
    public Text(){
        
    }
    
    public Text( String content, List<ParagraphQuestion>  list){   
        this.listQuestion = list;
        this.contentText = content;
    }    

    /**
     * @return the contentText
     */
    public String getContentText() {
        return contentText;
    }

    /**
     * @param contentText the contentText to set
     */
    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    /**
     * @return the listQuestion
     */
    public List<ParagraphQuestion> getListQuestion() {
        return listQuestion;
    }

    /**
     * @param listQuestion the listQuestion to set
     */
    public void setListQuestion(List<ParagraphQuestion> listQuestion) {
        this.listQuestion = listQuestion;
    }

    /**
     * @return the textId
     */
    public int getTextId() {
        return textId;
    }

    /**
     * @param textId the textId to set
     */
    public void setTextId(int textId) {
        this.textId = textId;
    }
}
