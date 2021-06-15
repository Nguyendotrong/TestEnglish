/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testenglish;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nguyen Do Trong
 */
public class User {
    private int userID;
    private final SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
    private String name;
    private String whereBorn;
    private GregorianCalendar bornDate;
    private String gender;
    private GregorianCalendar registerDate = new GregorianCalendar();
    private int point;
    private List<Question> listQuestion = new ArrayList<>(); 

    public User(String name, String whereBorn, GregorianCalendar birthday,
            String gender){
        this.name = name;
        this.gender = gender;
        this.bornDate = birthday;
        this.whereBorn = whereBorn;               
    }

    public User() {
		// TODO Auto-generated constructor stub
	}

	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("User Name: %s\nHometown: "
                + "%s\nBirthday: %s\nGender: %s\nRegister date: %s\n ", 
                this.name, this.whereBorn,
                this.f.format(this.bornDate.getTime()), this.gender,
                this.f.format(this.registerDate.getTime())));
        return builder.toString();
    }
    
    
    
    public void addQuestion(Question question){
        this.listQuestion.add(question);
        
        
        
        try {
           
            String sql = "select id from user where name = ?";
            PreparedStatement pstm = ConnectMySQl.getConnection().prepareStatement(sql);
            pstm.setString(1,this.name);
            ResultSet rs = pstm.executeQuery();
            rs.next();
            int iduser = rs.getInt(1);
   
            sql = "insert into practice (id_user, id_question) values (?, ?)";
            pstm = ConnectMySQl.getConnection().prepareStatement(sql);
            pstm.setInt(1, iduser);
            pstm.setInt(2, question.getIdQuestion());
            pstm.executeUpdate();
            System.out.println("Add question da lam ");
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	/**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the whereBorn
     */
    public String getWhereBorn() {
        return whereBorn;
    }

    /**
     * @param whereBorn the whereBorn to set
     */
    public void setWhereBorn(String whereBorn) {
        this.whereBorn = whereBorn;
    }

    /**
     * @return the bornDate
     */
    public GregorianCalendar getBornDate() {
        return bornDate;
    }

    /**
     * @param whenBorn the bornDate to set
     */
    public void setBornDate(GregorianCalendar date) {
        this.bornDate = date;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the registerDate
     */
    public GregorianCalendar getRegisterDate() {
        return registerDate;
    }

    /**
     * @param registerDate the registerDate to set
     */
    public void setRegisterDate(GregorianCalendar registerDate) {
        this.registerDate = registerDate;
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
