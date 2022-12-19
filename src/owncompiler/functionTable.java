/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package owncompiler;

/**
 *
 * @author Bisma Rasheed
 */
public class functionTable { //for holding variables declared in fucntions/methods in classes
    
    String Name;
    String type;
    int Scope;
    char Assigned;
    
    public functionTable()
    {
        this.Assigned='N';
    }
    
    public functionTable(String N, String T, int Scope, char A)
    {
        this.Name=N;
        this.type=T;
        this.Scope=Scope;
        this.Assigned=A;
    }
    
    public functionTable(functionTable f)
    {
        this.Name=f.Name;
        this.type=f.type;
        this.Assigned=f.Assigned;
        this.Scope=f.Scope;
    }
    
    @Override
    public String toString()
    {
        return "Variable Name : "+this.Name+"\nType : "+this.type+"\nScope : "+this.Scope+"\nAssigned : "+this.Assigned;
    }
    
}
