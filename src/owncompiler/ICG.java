/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package owncompiler;

/**
 *
 * @author Saad Communication
 */
public class ICG {
    
    int label;
    int temp;
    String classname;
    String funcName;
    String variableName;
    String value;
    String intermediateCode;
    String dummy;
    String[] typeTemp=new String[10];
    int[] tempCount=new int[10];
    
    
    public ICG()
    {
        this.label=0;
        this.temp=0;
        this.classname="";
        this.funcName="";
        this.value="";
        this.intermediateCode="";
    }
}
