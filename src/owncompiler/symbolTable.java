/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package owncompiler;

import java.util.ArrayList;

/**
 *
 * @author Bisma Rasheed
 */
public class symbolTable { //for holding information of classes declared in the whole program i.e class name, type,AM etc given below
    
    String className;
    String type;
    String accessModifier;
    String category;
    String Extends;
    ArrayList<classTable> classInfo;
    
    public symbolTable()
    {
        
    }
    public symbolTable(String cN, String t, String AM, String c, String E)
    {
        this.className=cN;
        this.type=t;
        this.accessModifier=AM;
        this.category=c;
        this.Extends=E;
        this.classInfo=new ArrayList<>();
    }
    
    @Override
    public String toString() {
        return "Class Name : "+this.className +"\nType : "+this.type+"\nAccess Modifier : "+this.accessModifier+
                "\nCategory : "+this.category+"\nExtends : "+this.Extends;
    }
    
}
