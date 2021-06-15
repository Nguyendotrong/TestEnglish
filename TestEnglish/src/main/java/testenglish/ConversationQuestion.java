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
public class ConversationQuestion extends MultipleChoice implements ParagraphQuestion{
  // private int idPara;
    public ConversationQuestion( Category category,Level level,String cont){
      super(category,level,cont);  
    }

    ConversationQuestion() {

    }
    /**
     *
     * @param answ
     * @throws Exception
     */
    @Override
    public void addAnswer(Answer answ) throws Exception {
        if(this.listAnswer.size() < 4 )
            this.listAnswer.add(answ);
        else throw new Exception("So luong phuong an phai bang 4");
    }
    
          
//   @Override
//    public int getIdPara() {
//        return idPara;
//    }
//
//    @Override
//    public void setIdPara(int id) {
//       this.idPara = id;
//    }

    

   
}
