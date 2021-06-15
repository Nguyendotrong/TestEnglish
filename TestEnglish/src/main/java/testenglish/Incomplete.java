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
public class Incomplete extends MultipleChoice implements ParagraphQuestion {
    //private int idPara;

    public Incomplete(Category category, Level level, String cont) {
        super(category, level, null);
    }

    public Incomplete() {

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < this.listAnswer.size(); i++) {
            builder.append(String.format("%s. %s\t", (char) (i + 65),
                    this.listAnswer.get(i)));
        }

        return builder.toString();
    }

    @Override
    public void addAnswer(Answer answ) throws Exception {
        if (this.listAnswer.size() < 4) {
            this.listAnswer.add(answ);
        } else {
            throw new Exception("so luong dap an cua loai "
                    + "cau hoi nay toi da la 4");
        }
    }

//    @Override
//    public int getIdPara() {
//        return idPara;
//    }
//
//    @Override
//    public void setIdPara(int id) {
//       this.idPara = id;
//    }
}
