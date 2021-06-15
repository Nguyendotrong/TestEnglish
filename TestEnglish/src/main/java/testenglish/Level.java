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
public enum Level {
    basic,
    medium,
    advance;
    public static Level getLevel(int id){
        for(Level i : Level.values()){
            if(i.ordinal() == id - 1)
                return i;
        }
        return null;
    }

}
