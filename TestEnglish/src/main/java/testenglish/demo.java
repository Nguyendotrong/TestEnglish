/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testenglish;

import java.util.Scanner;

/**
 *
 * @author Nguyen Do Trong
 */
public class demo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        QuestionManage questionManage = new QuestionManage();

        UserManage userManage = new UserManage();

        System.out.print("=======WELCOME TO TEST ENLISH  QUIZ======\n"
                + "1. Sign in\t2. Sign up\nYour choice:");
        int choice = 0;
        choice = Integer.parseInt(scan.nextLine());

        int rs;
        if (choice == 1) {
            rs = userManage.signIn(scan);
        } else {
            rs = userManage.signUp(scan);
        }
        if (rs == 1) {
            System.out.print("1. Show list user\n2. search user by name\n"
                    + "3. Show Results statistics\n4. Delete user\n5. Show list question by type\n"
                    + "6. Practice\n7. quit\nYour choice:");
            int choiceActivity;
            do {
                choiceActivity = Integer.parseInt(scan.nextLine());
                switch (choiceActivity) {
                    case 1:
                        System.out.println("======LIST USER=====");
                        userManage.showListUser(userManage.getAllUser());
                        System.out.println("If you want to check any function "
                                + "you can choice 1 - 6 and you want to quit "
                                + "you can choice 7");
                        break;
                    case 2:
                        System.out.print("Type user name that you want to search:");
                        String userName = scan.nextLine();
                        if (userManage.search(userName) != null) {
                            System.out.println("======result==\n"
                                    + userManage.search(userName));
                        } else {
                            System.out.println("User name have no exist");
                        }
                        break;
                    case 3:
                        System.out.print("Type month:");
                        int month = Integer.parseInt(scan.nextLine());
                        System.out.print("Type year:");
                        int year = Integer.parseInt(scan.nextLine());
                        userManage.resultsStatistics(month, year, userManage.getUserLogin().getUserID());
                        break;
                    case 4:
                        System.out.println("type user name to delete:");
                        String name = scan.nextLine();
                        UserManage.deleteUser(userManage.search(name).getUserID());
                        break;
                    case 5:
                        System.out.println("there are three type: 1. Multiple choice"
                                + " \t2. Incomplete\t3. Conversation ");
                        System.out.print("Type type:");
                        int type = Integer.parseInt(scan.nextLine());
                        System.out.println("=====List question====");
                        questionManage.getListQuestionByType(type).forEach(q -> {
                            System.out.println(q);
                        });
                        break;
                    case 6:
                        questionManage.practice(scan, userManage.getUserLogin());
                        break;
                }
                if (choice != 7) {
                    System.out.println("Choice");
                } else {
                    System.out.println("Thanks you for testing..");
                }
            } while (choiceActivity != 7);

        }

    }
}
