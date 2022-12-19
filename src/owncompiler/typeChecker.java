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
public class typeChecker {
    
    String T;
    String T1;
    String T2;
    String T3;
    String T4;
    String T5;
    String T6;
    String T7;
    String T8;
    String T9;
    String T10;
    
    String Op1;
    String Op2;
    String Op3;
    String Op4;
    String Op5;
    String Op6;
    String Op7;
    String Op8;
    String Op9;
    String Op10;
    
    public typeChecker()
    {
        
    }
    
    public String LookupAr(String name,ArrayList<classTable> cT, ArrayList<functionTable> fT)
    {
        for(int i=0;i<fT.size();i++)
        {
            if(name.equals(fT.get(i).Name))
            {
                if(fT.get(i).type.contains("[]"))
                {
                    return fT.get(i).type;
                }
            }
        }
        
        for(int i=0;i<cT.size();i++)
        {
            if(name.equals(cT.get(i).name))
            {
                if(cT.get(i).type.contains("[]"))
                {
                    return fT.get(i).type;
                }
            }
        }
        return "null";
    }
    
    
    public String LookupDot(String name, ArrayList<symbolTable> cN, ArrayList<classTable> cT, ArrayList<functionTable> fT)
    {
        for(int i=0;i<fT.size();i++)
        {
            if(name.equals(fT.get(i).Name))
            {
                for(int j=0;j<cN.size();j++)
                {
                    if(fT.get(i).type.equals(cN.get(j).className))
                    {
                        return fT.get(i).type;
                    }
                }
            }
        }
        
        for(int i=0;i<cT.size();i++)
        {
            if(name.equals(cT.get(i).name))
            {
                for(int j=0;j<cN.size();j++)
                {
                    if(cT.get(i).type.equals(cN.get(j).className))
                    {
                        return cT.get(i).type;
                    }
                }
            }
        }
        
        return "null";
    }
    
    public String Lookup(ArrayList<classTable> cT, int counter, String ID)
    {
        for(int i=0;i<counter;i++)
        {
            if(cT.get(i).name.equals(ID))
            {
                return cT.get(i).type;
            }
        }
        return "null";
        
    }
    
    public String Lookup(String name, int Scope, ArrayList<functionTable> fT)
    {
        while(Scope!=0)
        {
        for(int i=0;i<fT.size();i++)
        {
            if(Scope==fT.get(i).Scope)
            {
                if(name.equals(fT.get(i).Name))
                {
                    return fT.get(i).type;
                }
            }
        }
        Scope=Scope-1;
        }
        return "null";
    }
}
