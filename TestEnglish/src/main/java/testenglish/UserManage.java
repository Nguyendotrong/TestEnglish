/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testenglish;


import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;


/**
 *
 * @author Nguyen Do Trong
 */
public class UserManage {
    Date g = new Date(1592143855);
    private List<User> listUser = new ArrayList<>();
    private User userLogin;
   

    public void addUser(User user) {
            this.listUser.add(user);
    }
    public List<User> getAllUser(){
        try {
            List<User> list = new  ArrayList<>();                 
            Statement pstm =  ConnectMySQl.getConnection().createStatement();
            ResultSet result;            
            result = pstm.executeQuery("select * from users");
            while(result.next()){
                User u = new User();
                u.setName(result.getString("user_name"));
                u.setGender(result.getString("gender"));
                u.setWhereBorn(result.getString("hometown"));
                GregorianCalendar birthday = new GregorianCalendar();
                birthday.setTime(result.getDate("birthday"));
                u.setBornDate(birthday);
                birthday.setTime(result.getDate("register_date"));
                list.add(u);
            }
            
            return list;
        } catch (SQLException ex) {
            System.err.println("loi load danh sach nguoi hoc!" + ex.getMessage());
        }
        return null;
    }

    public User search(String userName){

        try {
            
            Statement stm = ConnectMySQl.getConnection().createStatement();
            ResultSet rs = stm.executeQuery("select * from  users where user_name = '" + userName + "';");
            rs.next();
            User  u  = new User();
            u.setUserID(rs.getInt("id"));
            u.setName(userName);
            GregorianCalendar d = new GregorianCalendar();
            d.setTime(rs.getDate("birthday"));
            u.setBornDate(d);
            u.setGender(rs.getString("gender"));
            u.setWhereBorn("hometown");
            d.setTime(rs.getDate("register_date"));
            u.setBornDate(d);
         
            return u;
                    
        } catch (SQLException ex) {
            System.err.println("no find out userName" + ex.getMessage());
        }
                return null;
    }

    private static void deletePointByIDUser(int userID){
        try {
            CallableStatement stm = ConnectMySQl.getConnection()
                    .prepareCall("{call deletePointByIDUser(?)}");
            stm.setInt(1, userID);
            int rs = stm.executeUpdate();
            
        } catch (SQLException ex) {
            System.err.println("Loi xoa diem nguoi dung!"  + ex.getMessage());
        }
    }
    private  static void deletePracticeByIDUser(int userID){
         try {
            CallableStatement stm = ConnectMySQl.getConnection()
                    .prepareCall("{call deletePracticeByIDUser(?)}");
            stm.setInt(1, userID);
            int rs = stm.executeUpdate();            
        } catch (SQLException ex) {
            System.err.println("Loi xoa cau hoi da lam cua nguoi dung!"
                    + ex.getMessage());
        }
    }
    public static void deleteUser(int userID){
        deletePointByIDUser(userID);
        deletePracticeByIDUser(userID);
        try {
            CallableStatement stm = ConnectMySQl.getConnection()
                    .prepareCall("{call deleteUser(?)}");
            stm.setInt(1, userID);
            int rs = stm.executeUpdate(); 
            System.out.println(rs +" u: "+ userID);
        } catch (SQLException ex) {
            System.err.println("Loi xoa thong tin nguoi dung!"
                    + ex.getMessage());
        }
    }
    
    public void downLoadUser(User user) {

        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Connection conn = ConnectMySQl.getConnection();
            String sql = "insert into users (user_name,hometown,birthday,"
                    + "gender,register_date) values (?,?,?,?,?);";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, user.getName());
            pstm.setString(2, user.getWhereBorn());
            pstm.setString(3, f.format(user.getBornDate().getTime()));
            pstm.setString(4, user.getGender());
            pstm.setString(5, f.format(user.getRegisterDate().getTime()));
            pstm.executeUpdate();
        } catch (SQLException ex) {
                System.out.println("Error downLoad user: " + ex.getMessage());                
        }
    }
    public List<Integer> resultsStatistics(int month, int year,int userID){
        try {
            List<Integer> listPoint = new ArrayList<>();
            String sql =  "{call thongke(?,?,?)}";
            CallableStatement stm = ConnectMySQl.getConnection().prepareCall(sql);
            GregorianCalendar firstdate = new GregorianCalendar(year, month-1, 1); 
            GregorianCalendar lastdate = new GregorianCalendar(year, month-1, 
                    firstdate.getActualMaximum(firstdate.DAY_OF_MONTH)); 
            stm.setLong(1,firstdate.getTimeInMillis());
            stm.setLong(2,lastdate.getTimeInMillis());
            stm.setInt(3, userID);
            ResultSet rs = stm.executeQuery();
            //System.out.println(firstdate.getTimeInMillis() + " " +lastdate.getTimeInMillis() +"mto"+firstdate.getTime());
            while(rs.next()){
               listPoint.add(rs.getInt("point"));
            }
            System.out.println("Number of practice sessions: "+ listPoint.size());
            int avg = 0;
            
            for(int i  =  0;  i < listPoint.size(); i++){
                 System.out.println("the point of the " + i + 1 +" practice: " + listPoint.get(i));
                 avg += listPoint.get(i);
            }
            System.out.printf("point average: %.2f\n", avg*1.0/listPoint.size());
            return listPoint;
        } catch (SQLException ex) {
            System.err.println("Loi thong ke!"+ ex.getMessage());
        }
        return null;
    }
    public int signIn(Scanner scanner) {
        System.out.println("=====Sign in");
        User us = new User();
        while (true) {

            System.out.print("User Name: ");
            String username = scanner.nextLine();
            // System.out.print("Pass: ");
            // String pass = scanner.nextLine();
            String sql = "{call getUser(?,?)}";
            try {
                CallableStatement stm = ConnectMySQl.getConnection().prepareCall(sql);
                stm.setString(1, username);
                stm.setString(2, "pass");
                ResultSet result = stm.executeQuery();
                result.next();
                us.setUserID(result.getInt("id"));
                us.setName(result.getString("user_name"));
                
                GregorianCalendar birthday = new GregorianCalendar();
                birthday.setTime(result.getDate("birthday"));
                us.setBornDate(birthday);

                us.setGender(result.getString("gender"));
                GregorianCalendar register = new GregorianCalendar();
                register.setTime(result.getDate("register_date"));
                us.setRegisterDate(register);
                us.setWhereBorn(result.getString("hometown"));

                System.out.println("Login Success");
                sql = "{call getQuestionDID(?)}";
                stm = ConnectMySQl.getConnection().prepareCall(sql);
                stm.setInt(1, us.getUserID());
                result = stm.executeQuery();
                while (result.next()) {
                    Question temp = new MultipleChoice();
                    temp.setIdQueston(result.getInt("question_id"));
                    us.getListQuestion().add(temp);
                }
                listUser.add(us);
                userLogin = us;
                break;
            } catch (SQLException e) {
                System.out.println("Login fail " +e.getMessage());
            }
        }
        return 1;
    }
    
    

    public int signUp(Scanner scanner) {        
        System.out.println("======Register=======");
        System.out.print("type userName: ");
        String username;
        // kiem tra username da ton tai hay chua, neu ton tai phai nhap ten khac
        do{
            try {
            username = scanner.nextLine();
            CallableStatement pstm = ConnectMySQl.getConnection()
                    .prepareCall("{call countUser(?,?)}");            
            pstm.setString(2, username);
            pstm.registerOutParameter(1, Types.INTEGER);
            pstm.execute();
            if(pstm.getInt(1) == 1)
                System.out.println("username already exists!"
                        + " please type other name: ");
            else
                break;
            } catch (SQLException ex) {
                System.err.println("loi kiem tra tai khoan da ton tai "
                         + ex.getMessage());
            }
        }while(true);

        // tao nhieu dinh dang ngay de nguoi dung co the nhap tuy chon
        GregorianCalendar birthday = new GregorianCalendar();
        String patternDate[] = { "dd MM yyyy","E, dd MMM yyyy HH:mm:ss z",
            "dd/MM/yyyy", "dd-M-yyyy hh:mm:ss", "dd MMMM yyyy zzzz",
            "yyyy-MM-dd", "yyyy/MM/dd", "yyyy MM dd" };
        while (true) {
            System.out.print("Birthday: ");
            String datetemp = scanner.nextLine();
            Date date = null;
            for (String i : patternDate) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat(i);
                    date =  format.parse(datetemp);
                    break;
                } catch (ParseException e) {
                }
            }
            if (date != null) {
                birthday.setTime(date);
                break;
            }else 
                System.out.println("this type of format date  have no exist!");

        }

        System.out.print("Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Address: ");
        String hometown = scanner.nextLine();

        User userNew = new User(username, hometown, birthday, gender);
        downLoadUser(userNew);
        listUser.add(userNew);
        userLogin = userNew;
        System.out.println("Register Success");
        return 1;        
    }    
    /**
     * 
     * @param list 
     */
     public static void showListUser(List<User> list){
        list.forEach(q -> {
            System.out.println(q);
        });
    }

    /**
     * @return the listUser
     */
    public List<User> getListUser() {
        return listUser;
    }

    /**
     * @param listUser the listUser to set
     */
    public void setListUser(List<User> listUser) {
            this.listUser = listUser;
    }
     public User getUserLogin() {
            return userLogin;
    }

    public void setUserLogin(User userLogin) {
            this.userLogin = userLogin;
    }

}
