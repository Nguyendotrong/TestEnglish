/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testenglish;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;


/**
 *
 * @author Nguyen Do Trong
 */
public class QuestionManage {
    private List<Question> listQuestion = new ArrayList<>();
    /**
     * 
     * @param q 
     */
    public void addQuestion(Question q) {
            this.listQuestion.add(q);
    }
   
    /**
     * 
     * @param userName
     * @param typeId
     * @param levelId
     * @param text 
     */
    public void upLoadText(String userName, int typeId, int levelId, Text text) {
        try {
            
                CallableStatement stm = ConnectMySQl.getConnection()
                        .prepareCall("{call upLoadParagraph(?,?,?)}");
                stm.setString(1, userName);
                stm.setInt(2, typeId);
                stm.setInt(3, levelId);
                ResultSet result = stm.executeQuery();
                result.next();
                text.setTextId(result.getInt("id"));
                text.setContentText(result.getString("content"));
                
            
        } catch (SQLException ex) {
           
                    System.out.println("Question have your level's choice had did? ");
                
                // System.err.println("loi up load doan van" + ex.getMessage());
        }

    }

    /**
     * 
     * @param userName
     * @param amount
     * @param typeId
     * @param levelId
     * @param categoryId up load question of multiple choice
     */
    
    // up load question laoi Multiple choice
    public void upLoadQuestion(String userName, int amount, int typeId, int levelId, int categoryId) {
            try {
                CallableStatement stm = ConnectMySQl.getConnection().prepareCall("{call upLoadQuestion(?,?,?,?,?)}");
                stm.setInt(1, amount);
                stm.setString(2, userName);
                stm.setInt(3, typeId);
                stm.setInt(4, levelId);
                stm.setInt(5, categoryId);
                ResultSet result = stm.executeQuery();

                while (result.next()) {
                    Question q = new MultipleChoice();
                    q.setLevel(testenglish.Level.getLevel(levelId));
                    q.setCategory(Category.getCategory(categoryId));
                    int trueAnswerId = result.getInt("true_answer_id");
                    q.setIdQueston(result.getInt("id"));
                    q.setContent(result.getString("content"));
                    this.upLoadAnswer(q);
                    q.getListAnswer().forEach(ans -> {
                            if (ans.getAnswerId() == trueAnswerId)
                                    q.setTrueAnswer(ans);
                    });
                    this.addQuestion(q);
                }
            } catch (SQLException ex) {
                    System.err.println("Loi load cau hoi" + ex.getMessage());
            }
    }
// up load question loai co doan text
    public void upLoadQuestion(Text text, int typeId, int levelId) {
        try {
            CallableStatement stm = ConnectMySQl.getConnection()
                    .prepareCall("{call upLoadQuestionByParagrapId(?)}");
            stm.setInt(1, text.getTextId());
            ResultSet result = stm.executeQuery();
            while (result.next()) {
                ParagraphQuestion q;
                if (typeId == 2)
                        q = new Incomplete();
                else
                        q = new ConversationQuestion();
                q.setIdQueston(result.getInt("id"));
                q.setContent(result.getString("content"));
                q.setLevel(testenglish.Level.getLevel(levelId));
                q.setCategory(Category.getCategory(result.getInt("category_id")));
                int trueAnswerId = result.getInt("true_answer_id");
                this.upLoadAnswer(q);
                q.getListAnswer().forEach(ans -> {
                        if (ans.getAnswerId() == trueAnswerId)
                                q.setTrueAnswer(ans);
                });
                text.getListQuestion().add(q);
                this.addQuestion(q);

        }
        } catch (SQLException ex) {
                System.err.println("Loi load cau hoi: " + ex.getMessage());
        }
    }

    public void upLoadAnswer(Question q) {
        try {
            CallableStatement stm = ConnectMySQl.getConnection()
                    .prepareCall("{call upLoadAnswer(?)}");
            stm.setInt(1, q.getIdQuestion());
            ResultSet result = stm.executeQuery();
            while (result.next()) {
                Answer ans = new Answer();
                ans.setAnswerId(result.getInt("id"));
                ans.setContent(result.getString("content"));
                try {

                        q.addAnswer(ans);
                } catch (Exception ex) {
                        System.out.println("loi them answer.");
                }
            }
        } catch (SQLException ex) {
                System.err.println("loi load dap an" + ex.getMessage());
        }
    }
    public void updatePractice(List<Question> list,User user, int point){
        try {
                Connection conn = ConnectMySQl.getConnection();
                System.out.println(user +"| "+ point);
                conn.setAutoCommit(false);
                Statement stm = conn.createStatement();
                list.forEach(ch -> {
                    String sql = "INSERT INTO practice (user_id, question_id)"
                            + " VALUES (" + user.getUserID() + ","
                                    + ch.getIdQuestion() + ");";
                    try {
                            stm.addBatch(sql);
                    } catch (SQLException e) {
                    }
                });

                long datePractice = new GregorianCalendar().getTimeInMillis();
                String sql = "INSERT INTO point (id_user,point,date_pracrice)"
                        + " VALUES (" + user.getUserID() + "," + point
                                + "," + datePractice + ");";

                stm.addBatch(sql);
                stm.executeBatch();
                conn.commit();
        } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
    }
   /**
    * 
    * @param types
    * @return List user current
    */
    public List<Question>  getListQuestionByType(int types){
        List<Question> list = new ArrayList<>();
        Question q;
        
        try {
            CallableStatement stm = ConnectMySQl.getConnection() 
                    .prepareCall("{call showQuestionByType(?)}");
            stm.setInt(1, types);
            ResultSet rs = stm.executeQuery();
            
            while(rs.next()){
                if(types == 1)
                    q = new MultipleChoice();
                else
                    if(types == 2)
                        q = new Incomplete();
                    else 
                        q =  new ConversationQuestion();
                q.setIdQueston(rs.getInt("id"));
                q.setContent(rs.getString("content"));
                q.setLevel(testenglish.Level.getLevel(rs.getInt("level_id")));
                q.setCategory(Category.getCategory(rs.getInt("category_id")));
                list.add(q);                
            }
            return list;
        } catch (SQLException ex) {
            System.err.println("loi lay cau hoi theo loai" + ex.getMessage());
        }
        return null;
    }
    
    /**
     * 
     * @param scan
     * @param user 
     */
    public void practice(Scanner scan, User user) {
        //chon loai
        System.out.print("there are three type of question: " 
                + "\n1. multiple choice, \t2. incomplete, "
                + "\t3. conversation\nYour choice: ");
        int choiceType = Integer.parseInt(scan.nextLine());
        // chon muc do
        System.out.println("Do you want practice of level:\n" 
                + "1. Basic\t2. Medium\t3. Advance");
        System.out.print("Your choice: ");
        int choiceLevel = Integer.parseInt(scan.nextLine());
        if (choiceType != 1) { // neu kkhong phai multiple choice thi load doan van
            Text text = new Text();
            upLoadText(user.getName(), choiceType, choiceLevel, text);
            upLoadQuestion(text, choiceType, choiceLevel);
            System.out.println("======Read the passage and " 
                    + "answer the questions below.");
            System.out.println(text.getContentText());
        }else {
            //neu la loai multiple choice thi phai nhap so cau
            System.out.print("Type amount question: ");
            int amount = Integer.parseInt(scan.nextLine());
            System.out.println("Do you want practice of category");
            for(Category i: Category.values()) {
                    System.out.print(i.ordinal() + 1 +". " +i +"\t");
            }
            System.out.print("\nYour choice: ");
            int choiceCategory = Integer.parseInt(scan.nextLine());
            upLoadQuestion(user.getName(),amount,choiceType,choiceLevel,
                    choiceCategory);
        }
        
        int point = 0; //bien luu diem
        List<Integer> listChoice = new ArrayList<>(); //tao mang luu dap an nguoi dung chon
        for (int i = 0; i < listQuestion.size(); i++) {
            //xuat tung cau hoi de nguoi dung luyen tap
            Question chnew = listQuestion.get(i);
            System.out.println(i + 1 + ". " + chnew);
            int indexans = -1;

            do {
                System.out.print("type your answer(A,B,C,D...): ");
                String ans = scan.nextLine().trim().toUpperCase();
                //kiem tra nguoi dung co nhap dap an ko
                if (!ans.isEmpty()) {
                    indexans = ans.charAt(0) - 65;

                } else {
                    System.out.println("please type your choice!");
                    continue;
                }
                // kiem tra nguoi dung nhap dap an co nam trong list dap an ko
                if(indexans < 0 || indexans > chnew.getListAnswer()
                        .size() - 1) {
                    System.out.println("your choice no exist! ");
                }

            } while (indexans < 0 || indexans > chnew.getListAnswer()
                    .size() - 1);
            listChoice.add(indexans);
            
            if (chnew.isCorrect(indexans))
                        point++;
        }
        
        //xuat diem tren tong so cau
        System.out.println("\n==Your point: "+point + "/" 
                + listQuestion.size());
        
        //xuat lai dap an dung va dap an nguoi dung chon de kiem tra ket qua
        for (int i = 0; i < listQuestion.size(); i++) {
                Question chnew = listQuestion.get(i);
                System.out.println(i + 1 + ". " + chnew + "\nAnswer true: " 
                        + chnew.getTrueAnswer() + "\nyour choice: "
                        + chnew.getListAnswer().get(listChoice.get(i)));
                System.out.println();
        }
        // cap nhat nhung cau hoi da lam xuong csdl
        updatePractice(listQuestion, user, point);       

    }

    public void creatQuestion(Object question) {
        this.listQuestion.add((Question) question);

        try {
            Statement stm = ConnectMySQl.getConnection().createStatement();
            ResultSet result;
            String sql;
            if (question instanceof MultipleChoice) {
                MultipleChoice x = (MultipleChoice) question;
                System.out.println(x.content);
                sql = "select max(question.id) as idMax from question";
                result = stm.executeQuery(sql);
                result.next();
                int idQuestionNew = result.getInt("idMax") + 1;
                stm = ConnectMySQl.getConnection().createStatement();
                sql = "insert into question(content,category_id,level_id," + "type)"
                        + " values(" + "'" + x.getContent()
                                + "'," + x.getCategory().ordinal() + 1 + "," 
                        + x.getLevel().ordinal() + 1 + ",1)";
                if (stm.executeUpdate(sql) == 1) {

                    for (int i = 0; i < x.listAnswer.size(); i++) {
                        sql = "insert into answer(question_id,content) " 
                               + "values(" + idQuestionNew + ",'"
                               + x.getListAnswer().get(i).getContent() + "')";
                        stm = ConnectMySQl.getConnection().createStatement();
                        stm.executeUpdate(sql);
                    }

                    // lay id cua dap dung
                    sql = "select id from answer where content = '" 
                           + x.getTrueAnswer().getContent()
                         + "' and answer.question_id = " + idQuestionNew + ";";
                    stm = ConnectMySQl.getConnection().createStatement();
                    result = stm.executeQuery(sql);
                    result.next();
                    int idAnswerTrue = result.getInt("id");
                    // set cho id cua dap dung cho trueAnswer cuaquestion duoi csdl
                    sql = "UPDATE `test_english`.`question` SET `true_answer_id"
                            + "` = '" + idAnswerTrue
                               + "' WHERE (`id` = '" + idQuestionNew + "');";
                    stm = ConnectMySQl.getConnection().createStatement();
                    stm.executeUpdate(sql);
                }

            }
            System.out.print("add question success");
        } catch (SQLException ex) {
                System.err.println("loi add question:" + ex.getMessage());
        }
    }
	
    /**
     * @return the listQuestion
     */
    public List<Question> getListQuestion() {
            return listQuestion;
    }

    /**
     * @param listQuestion the listQuestion to set
     */
    public void setListQuestion(List<Question> listQuestion) {
            this.listQuestion = listQuestion;
    }

}
