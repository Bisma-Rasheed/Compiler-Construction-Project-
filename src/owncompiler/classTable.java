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
public class classTable { //for containing variables and functions declared within a class
    
    String argType1;
    String name;
    String type;
    String AM;
    char Assigned;
    String category;
    char Static;
    ArrayList<functionTable> functionData;
    String dummy;
    ArrayList<String> argType;
    int argCounter;
    String dumType;
    String dType;
    char[] c=new char[100];
    String string;
    
    public classTable()
    {
        this.argCounter=0;
        this.argType=new ArrayList<>();
        this.dummy="";
        this.Assigned='N';
    }
    
    public classTable(String n, String t, String AM, char A, String c, char S)
    {
        this.dummy="";
        this.name=n;
        this.type=t;
        this.AM=AM;
        this.Assigned=A;
        this.category=c;
        this.Static=S;
        this.functionData=new ArrayList<>();
    }
    
    public boolean Lookup(ArrayList<classTable> cT, int counter, String ID)
    {
        for(int i=0;i<counter;i++)
        {
            if(cT.get(i).name.equals(ID))
            {
                dType=cT.get(i).type.replace("[]", "");
                return true;
            }
        }
        return false;
        
    }
    
    public boolean Lookup(ArrayList<classTable> cT, String name)
    {
        string="";
        for(int i=0;i<cT.size();i++)
        {
            if(name.equals(cT.get(i).name))
            {
                c=cT.get(i).type.toCharArray();
                for(int j=0;j<c.length;j++)
                {
                    if(c[j]=='-' && c[j+1]=='>')
                    {
                        j=j+2;
                        while(!(j>=c.length))
                        {
                            string=string+c[j];
                            j++;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean LookupCT(ArrayList<classTable> cT, String ID)
    {
        for(int i=0;i<cT.size();i++)
        {
            if(cT.get(i).name.equals(ID))
            {
                if(cT.get(i).type.contains("[]"))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean Lookup(symbolTable s, String ID, ArrayList<symbolTable> sT)
    {
        String dummy1=s.Extends;
        if("Object".equals(dummy1))
        {
            return false;
        }
        else
        {
            for(int i=0;i<sT.size();i++)
            {
                    if(dummy1.equals(sT.get(i).className))
                    {
                        for(int j=0;j<sT.get(i).classInfo.size();j++)
                        {
                            if(sT.get(i).classInfo.get(j).name.equals(ID))
                            {
                                return true;
                            }
                        }
                    }
            }
        }
        return false;
    }
    
    public boolean Lookup(String name, int Scope, ArrayList<functionTable> fT)
    {
        while(Scope!=0)
        {
        for(int i=0;i<fT.size();i++)
        {
            if(Scope==fT.get(i).Scope)
            {
                if(name.equals(fT.get(i).Name))
                {
                    dType=fT.get(i).type.replace("[]", "");
                    return true;
                }
            }
        }
        Scope=Scope-1;
        }
        return false;
    }
    
    public boolean Lookup(String name, int Scope, ArrayList<functionTable> fT, int a)
    {
        while(Scope!=0)
        {
        for(int i=0;i<fT.size();i++)
        {
            if(Scope==fT.get(i).Scope)
            {
                if(name.equals(fT.get(i).Name))
                {
                    if(fT.get(i).type.contains("[]"))
                    {
                        return true;
                    }
                }
            }
        }
        Scope=Scope-1;
        }
        return false;
        
    }
    
    public boolean Lookup(String name, int Scope, ArrayList<functionTable> fT, String type)
    {
        while(Scope!=0)
        {
        for(int i=0;i<fT.size();i++)
        {
            if(Scope==fT.get(i).Scope)
            {
                if(name.equals(fT.get(i).Name))
                {
                    if(type.equals(fT.get(i).type))
                    {
                        return true;
                    }
                }
            }
        }
        Scope=Scope-1;
        }
        return false;
    }
    
    public boolean LookupAr(String name,ArrayList<classTable> cT, ArrayList<functionTable> fT)
    {
        for(int i=0;i<fT.size();i++)
        {
            if(name.equals(fT.get(i).Name))
            {
                if(fT.get(i).type.contains("[]"))
                {
                    dType=fT.get(i).type.replace("[]", "");
                    return true;
                }
            }
        }
        
        for(int i=0;i<cT.size();i++)
        {
            if(name.equals(cT.get(i).name))
            {
                if(cT.get(i).type.contains("[]"))
                {
                    dType=cT.get(i).type.replace("[]", "");
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean LookupDot(String name, ArrayList<symbolTable> cN, ArrayList<classTable> cT, ArrayList<functionTable> fT)
    {
        for(int i=0;i<fT.size();i++)
        {
            if(name.equals(fT.get(i).Name))
            {
                for(int j=0;j<cN.size();j++)
                {
                    if(fT.get(i).type.equals(cN.get(j).className))
                    {
                        dType=fT.get(i).type.replace("[]", "");
                        return true;
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
                        dType=cT.get(i).type.replace("[]", "");
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    
    public boolean Lookup(ArrayList<functionTable> fT, ArrayList<classTable> cT, String name)
    {
        for(int i=0;i<fT.size();i++)
        {
            if(name.equals(fT.get(i).Name))
            {
                if(fT.get(i).type.contains("[]"))
                {
                    dType=fT.get(i).type.replace("[]", "");
                    return true;
                }
            }
        }
        
        for(int i=0;i<cT.size();i++)
        {
            if(name.equals(cT.get(i).name))
            {
                if(cT.get(i).type.contains("[]"))
                {
                    dType=cT.get(i).type.replace("[]", "");
                    return true;
                }
            }
        }
        return false;
    }
    public boolean Lookup(ArrayList<functionTable> fT, ArrayList<classTable> cT, String ID, String ID1, ArrayList<symbolTable> sT)
    {
        for(int i=0;i<fT.size();i++)
        {
            if(ID.equals(fT.get(i).Name))
            {
                for(int j=0;j<sT.size();j++)
                {
                    if(fT.get(i).type.equals(sT.get(j).className))
                    {
                        for(int k=0;k<sT.get(j).classInfo.size();k++)
                        {
                            if(ID1.equals(sT.get(j).classInfo.get(k).name))
                            {
                                dType=sT.get(j).classInfo.get(k).type;
                                return true;
                            }
                            
                        }
                    }
                    
                    
                }
                
            }
        }
        
        for(int i=0;i<cT.size();i++)
        {
            if(ID.equals(cT.get(i).name))
            {
                for(int j=0;j<sT.size();j++)
                {
                    if(cT.get(i).type.equals(sT.get(j).className))
                    {
                        for(int k=0;k<sT.get(j).classInfo.size();k++)
                        {
                            if(ID1.equals(sT.get(j).classInfo.get(k).name))
                            {
                                dType=sT.get(j).classInfo.get(k).type;
                                return true;
                            }
                        }
                        
                    }
                    
                }
                
            }
        }
        
        return false;
    }
    
    @Override
    public String toString()
    {
        return "Attribute/Function Name : "+this.name+"\nData Type : "+this.type+"\nAccess Modifier : "+this.AM+
                "\nAssigned : "+this.Assigned+"\nCategory : "+this.category+"\nStatic : "+this.Static;
    }
    
    
}
