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
public class Semantic {
    
    ArrayList<symbolTable> classNames = new ArrayList<>();
    int Scope;
   
    
    public Semantic()
    {
        this.Scope=0;
    }
    public boolean Lookup(String ID, int counter, ArrayList<symbolTable> cN)
    {
        for(int i=0;i<=counter;i++)
        {
            if(cN.get(i).className.equals(ID))
            {
                return true;
            }
        }
        return false;
    }
    
    public boolean Lookup(String ID, String ID1, int counter, ArrayList<symbolTable> cN)
    {
        for(int i=0;i<counter;i++)
        {
            if(cN.get(i).className.equals(ID))
            {
            
                if(cN.get(i).Extends.equals(ID1))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        
        
        return false;
    }
    
    public boolean Lookup(String name, String type, ArrayList<classTable> cT)
    {
        for(int i=0;i<cT.size();i++)
        {
            if(name.equals(cT.get(i).name))
            {
                if(type.contains("->"))
                {
                    if(type.equals(cT.get(i).type))
                    {
                        return true;
                    }
                }
                
                else if(!cT.get(i).type.contains("->"))
                {
                    return true; 
                }
                
            }
            
        }
        return false;
    }
    
    public boolean Lookup(String name, String type, ArrayList<classTable> cT, int a) //for local variable warning
    {
        for(int i=0;i<cT.size();i++)
        {
            if(name.equals(cT.get(i).name))
            {
                if(!cT.get(i).type.contains("->"))
                {
                    return true;
                }
            }
            
        }
        return false;
    }
    
    public int createScope()
    {
        Scope=Scope+1;
        return Scope;
    }
    
    public int destroyScope()
    {
        Scope=Scope-1;
        return Scope;
    }
    
    public String LookupComp(String name, ArrayList<classTable> cT, ArrayList<functionTable> fT)
    {
        
        return "null";
    }
    
    
    public String Compatibility(String t1, String t2, String OP)
    {
        if("+".equals(OP))
        {
            if(("Integer".equals(t1) && "char".equals(t2)) ||
                    ("char".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "char".equals(t2)) ||
                    ("Integer".equals(t1) && "Integer".equals(t2)))
            {
                return "Integer";
            }
            
            else if(("Integer".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "decimal".equals(t2)))
            {
                return "decimal";
            }
            
            else if(("Integer".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "decimal".equals(t2)) ||
                    ("String".equals(t1) && "String".equals(t2)))
            {
                return "String";
            }
            
            else
            {
                System.out.println("\nBad Operand types for binary operator \"+\"");
            }
            
            
        }
        
        else if("-".equals(OP))
        {
            if(("Integer".equals(t1) && "char".equals(t2)) ||
                    ("char".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "char".equals(t2)) ||
                    ("Integer".equals(t1) && "Integer".equals(t2)))
            {
                return "Integer";
            }
            
            else if(("Integer".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "decimal".equals(t2)))
            {
                return "decimal";
            }
            
            else /*if(("Integer".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "decimal".equals(t2)) ||
                    ("String".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "String".equals(t2)))*/
            {
                System.out.println("\nBad Operand types for binary Operator \"-\"");
                return "null";
            }
        }
        
        else if("*".equals(OP))
        {
            if(("Integer".equals(t1) && "char".equals(t2)) ||
                    ("char".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "char".equals(t2)) ||
                    ("Integer".equals(t1) && "Integer".equals(t2)))
            {
                return "Integer";
            }
            
            else if(("Integer".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "decimal".equals(t2)))
            {
                return "decimal";
            }
            
            else /*if(("Integer".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "decimal".equals(t2)) ||
                    ("String".equals(t1) && "String".equals(t2)))*/
            {
                System.out.println("\nBad Operand types for binary Operator \"*\"");
                return "null";
            }
        }
        
        else if("/".equals(OP))
        {
            if(("Integer".equals(t1) && "char".equals(t2)) ||
                    ("char".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "char".equals(t2)) ||
                    ("Integer".equals(t1) && "Integer".equals(t2)))
            {
                return "Integer";
            }
            
            else if(("Integer".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "decimal".equals(t2)))
            {
                return "decimal";
            }
            
            else if(("Integer".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "decimal".equals(t2)) ||
                    ("String".equals(t1) && "String".equals(t2)))
            {
                System.out.println("\nBad Operand types for binary Operator \"/\"");
                return "null";
            }
        }
        
        else if("%".equals(OP))
        {
            if(("Integer".equals(t1) && "char".equals(t2)) ||
                    ("char".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "char".equals(t2)) ||
                    ("Integer".equals(t1) && "Integer".equals(t2)))
            {
                return "Integer";
            }
            
            else if(("Integer".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "decimal".equals(t2)))
            {
                return "decimal";
            }
            
            else if(("Integer".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "decimal".equals(t2)) ||
                    ("String".equals(t1) && "String".equals(t2)))
            {
                System.out.println("\nBad Operand types for binary Operator \"%\"");
                return "null";
            }
        }
        
        else if(">>".equals(OP))
        {
            if(("Integer".equals(t1) && "char".equals(t2)) ||
                    ("char".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "char".equals(t2)) ||
                    ("Integer".equals(t1) && "Integer".equals(t2)))
            {
                return "Integer";
            }
            
            else if(("Integer".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "decimal".equals(t2)))
            {
                System.out.println("\nBad Operand types for binary Operator \">>\"");
                return "null";
            }
            
            else if(("Integer".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "decimal".equals(t2)) ||
                    ("String".equals(t1) && "String".equals(t2)))
            {
                System.out.println("\nBad Operand types for binary Operator \">>\"");
                return "null";
            }
        }
        
        else if("<<".equals(OP))
        {
            if(("Integer".equals(t1) && "char".equals(t2)) ||
                    ("char".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "char".equals(t2)) ||
                    ("Integer".equals(t1) && "Integer".equals(t2)))
            {
                return "Integer";
            }
            
            else if(("Integer".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "decimal".equals(t2)))
            {
                System.out.println("\nBad Operand types for binary Operator \"<<\"");
                return "null";
            }
            
            else if(("Integer".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "decimal".equals(t2)) ||
                    ("String".equals(t1) && "String".equals(t2)))
            {
                System.out.println("\nBad Operand types for binary Operator \"<<\"");
                return "null";
            }
        }
        
        else if("<=".equals(OP))
        {
            if(("Integer".equals(t1) && "char".equals(t2)) ||
                    ("char".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "char".equals(t2)) ||
                    ("Integer".equals(t1) && "Integer".equals(t2)) ||
                    ("Integer".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "decimal".equals(t2)))
            {
                return "bool";
            }
            
            else if(("Integer".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "decimal".equals(t2)) ||
                    ("String".equals(t1) && "String".equals(t2)))
            {
                System.out.println("\nBad Operand types for binary Operator \"<=\"");
                return "null";
            }
        }
        
        else if(">=".equals(OP))
        {
            if(("Integer".equals(t1) && "char".equals(t2)) ||
                    ("char".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "char".equals(t2)) ||
                    ("Integer".equals(t1) && "Integer".equals(t2)) ||
                    ("Integer".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "decimal".equals(t2)))
            {
                return "bool";
            }
            
            else if(("Integer".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "decimal".equals(t2)) ||
                    ("String".equals(t1) && "String".equals(t2)))
            {
                System.out.println("\nBad Operand types for binary Operator \">=\"");
                return "null";
            }
        }
        
        else if("<".equals(OP))
        {
            if(("Integer".equals(t1) && "char".equals(t2)) ||
                    ("char".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "char".equals(t2)) ||
                    ("Integer".equals(t1) && "Integer".equals(t2)) ||
                    ("Integer".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "decimal".equals(t2)))
            {
                return "bool";
            }
            
            else if(("Integer".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "decimal".equals(t2)) ||
                    ("String".equals(t1) && "String".equals(t2)))
            {
                System.out.println("\nBad Operand types for binary Operator \"<\"");
                return "null";
            }
        }
        
        else if(">".equals(OP))
        {
            if(("Integer".equals(t1) && "char".equals(t2)) ||
                    ("char".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "char".equals(t2)) ||
                    ("Integer".equals(t1) && "Integer".equals(t2)) ||
                    ("Integer".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "decimal".equals(t2)))
            {
                return "bool";
            }
            
            else if(("Integer".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "decimal".equals(t2)) ||
                    ("String".equals(t1) && "String".equals(t2)))
            {
                System.out.println("\nBad Operand types for binary Operator \">\"");
                return "null";
            }
        }
        
        else if("==".equals(OP))
        {
            if(("Integer".equals(t1) && "char".equals(t2)) ||
                    ("char".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "char".equals(t2)) ||
                    ("Integer".equals(t1) && "Integer".equals(t2)) ||
                    ("Integer".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "decimal".equals(t2)) ||
                    ("String".equals(t1) && "String".equals(t2)) ||
                    ("bool".equals(t1) && "bool".equals(t2)))
            {
                return "bool";
            }
            
            else if(("Integer".equals(t1) && "bool".equals(t2)) ||
                    ("bool".equals(t1) && "Integer".equals(t2)) || 
                    ("decimal".equals(t1) && "bool".equals(t2)) ||
                    ("bool".equals(t1) && "decimal".equals(t2)) ||
                    ("bool".equals(t1) && "char".equals(t2)) ||
                    ("char".equals(t1) && "bool".equals(t2)))
            {
                System.out.println("\nIncomparable types.");
                return "null";
            }
            
            else if(("Integer".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "decimal".equals(t2)) ||
                    ("bool".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "bool".equals(t2)))
            {
                System.out.println("\nBad Operand types for binary Operator \"==\"");
                return "null";
            }
        }
        
        else if("!=".equals(OP))
        {
            if(("Integer".equals(t1) && "char".equals(t2)) ||
                    ("char".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "char".equals(t2)) ||
                    ("Integer".equals(t1) && "Integer".equals(t2)) ||
                    ("Integer".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "decimal".equals(t2)) ||
                    ("String".equals(t1) && "String".equals(t2)) ||
                    ("bool".equals(t1) && "bool".equals(t2)))
            {
                return "bool";
            }
            
            else if(("Integer".equals(t1) && "bool".equals(t2)) ||
                    ("bool".equals(t1) && "Integer".equals(t2)) || 
                    ("decimal".equals(t1) && "bool".equals(t2)) ||
                    ("bool".equals(t1) && "decimal".equals(t2)) ||
                    ("bool".equals(t1) && "char".equals(t2)) ||
                    ("char".equals(t1) && "bool".equals(t2)))
            {
                System.out.println("\nIncomparable types.");
                return "null";
            }
            
            else if(("Integer".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "decimal".equals(t2)) ||
                    ("bool".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "bool".equals(t2)))
            {
                System.out.println("\nBad Operand types for binary Operator \"!=\"");
                return "null";
            }
        }
        
        else if("&".equals(OP))
        {
            if(("Integer".equals(t1) && "char".equals(t2)) ||
                    ("char".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "char".equals(t2)) ||
                    ("Integer".equals(t1) && "Integer".equals(t2)))
            {
                return "Integer";
            }
            
            else if(("bool".equals(t1) && "bool".equals(t2)))
            {
                return "bool";
            }
            
            else if(("Integer".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "decimal".equals(t2)))
            {
                System.out.println("\nBad Operand types for binary Operator \"&\"");
                return "null";
            }
            
            else if(("Integer".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "decimal".equals(t2)) ||
                    ("String".equals(t1) && "String".equals(t2)))
            {
                System.out.println("\nBad Operand types for binary Operator \"&\"");
                return "null";
            }
        }
        
        else if("^".equals(OP))
        {
            if(("Integer".equals(t1) && "char".equals(t2)) ||
                    ("char".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "char".equals(t2)) ||
                    ("Integer".equals(t1) && "Integer".equals(t2)))
            {
                return "Integer";
            }
            
            else if(("bool".equals(t1) && "bool".equals(t2)))
            {
                return "bool";
            }
            
            else if(("Integer".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "decimal".equals(t2)))
            {
                System.out.println("\nBad Operand types for binary Operator \"^\"");
                return "null";
            }
            
            else if(("Integer".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "decimal".equals(t2)) ||
                    ("String".equals(t1) && "String".equals(t2)))
            {
                System.out.println("\nBad Operand types for binary Operator \"^\"");
                return "null";
            }
        }
        
        else if("|".equals(OP))
        {
            if(("Integer".equals(t1) && "char".equals(t2)) ||
                    ("char".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "char".equals(t2)) ||
                    ("Integer".equals(t1) && "Integer".equals(t2)))
            {
                return "Integer";
            }
            
            else if(("bool".equals(t1) && "bool".equals(t2)))
            {
                return "bool";
            }
            
            else if(("Integer".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "decimal".equals(t2)) ||
                    ("decimal".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "decimal".equals(t2)))
            {
                System.out.println("\nBad Operand types for binary Operator \"|\"");
                return "null";
            }
            
            else if(("Integer".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "Integer".equals(t2)) || 
                    ("char".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "char".equals(t2)) ||
                    ("decimal".equals(t1) && "String".equals(t2)) ||
                    ("String".equals(t1) && "decimal".equals(t2)) ||
                    ("String".equals(t1) && "String".equals(t2)))
            {
                System.out.println("\nBad Operand types for binary Operator \"|\"");
                return "null";
            }
        }
        
        else if("&&".equals(OP))
        {
            if("bool".equals(t1) && "bool".equals(t2))
            {
                return "bool";
            }
            
            else
            {
                System.out.println("\nBad Operand types for binary operator \"&&\"");
                return "null";
            }
        }
        
        else if("||".equals(OP))
        {
            if("bool".equals(t1) && "bool".equals(t2))
            {
                return "bool";
            }
            
            else
            {
                System.out.println("\nBad Operand types for binary operator \"||\"");
                return "null";
            }
        }
        
        return "null";
    }
    
    
    
}
