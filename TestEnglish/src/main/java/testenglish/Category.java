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
public enum Category {
    verb,
    noun,
    adjective,
    tag_question,
    article;
    public static Category getCategory(int idx){
        for(Category c: Category.values())
            if(c.ordinal() == idx -1)
                return c;
        return null;
    }

}
