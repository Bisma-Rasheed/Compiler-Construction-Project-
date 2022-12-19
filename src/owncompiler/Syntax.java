/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package owncompiler;

import java.util.ArrayList;

/**
 *
 * @author Bismah Rasheed
 */


public class Syntax{
    
    String dummy; //to be used in switch statement
    int currentScope; //for holding scopes
    int index;
    ArrayList<Token> tokenlist = new ArrayList<>(); //to save tokens to be used in the next phase
    boolean value;
    Semantic semantic=new Semantic();
    symbolTable sym=new symbolTable(); //for holding names of class
    classTable myClass=new classTable(); //for holding names of class data
    functionTable myFunc=new functionTable(); //for holding data of inside the function
    int classCounter; //to count no. of classes added
    int varfuncCounter; //to count no. of attributes and functions added in classes.
    int variableCounter; //to count no. of variables added in functions of classes
    ArrayList<functionTable> dumFunc=new ArrayList<>(); //to hold temporary data of functionTable type
    typeChecker checkType=new typeChecker(); //to check types returned from expression
    String funcName; //to hold name of functions when they are added and then checking the return type with
    //the return type returned.
    ICG IC=new ICG();
    
    public Syntax(int index, ArrayList<Token> tokenlist)
    {
        this.variableCounter=0;
        this.varfuncCounter=0;
        this.classCounter=0;
        this.index=index;
        this.tokenlist=tokenlist;
        this.value=CLASS();
    }
     
    private boolean CLASS()
    {
        if("AM".equals(tokenlist.get(index).classPart))
        {
            sym.accessModifier=tokenlist.get(index).valuePart;
            index++;
            if(CLASS1())
            {
                return true;
            }
        }
            return false;
    }
    
    private boolean CLASS1()
    {
        if(FINAL())
        {
        if("class".equals(tokenlist.get(index).classPart))
        {
            sym.type=tokenlist.get(index).valuePart;
            index++;
            if("ID".equals(tokenlist.get(index).classPart))
            {
                sym.className=tokenlist.get(index).valuePart;
                IC.classname=tokenlist.get(index).valuePart;
                IC.intermediateCode=IC.intermediateCode+IC.classname+"_";
                index++;
                if(INH())
                {
                    semantic.classNames.add(new symbolTable(sym.className,sym.type,sym.accessModifier,sym.category,sym.Extends));
                    System.out.println("\n"+semantic.classNames.get(classCounter));
                    
                    if("{".equals(tokenlist.get(index).classPart))
                    {
                        index++;
                        if(MAIN())
                        {
                            if(CBODY())
                            {
                                if("}".equals(tokenlist.get(index).classPart))
                                {
                                    index++;
                                    classCounter++;
                                    if(CLASS_DEF())
                                    {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            
            }
            
        }
        }
        
        else if("abstract".equals(tokenlist.get(index).classPart))
        {
            sym.category=tokenlist.get(index).valuePart;
            index++;
            if("class".equals(tokenlist.get(index).classPart))
            {
                sym.type=tokenlist.get(index).valuePart;
                index++;
                if("ID".equals(tokenlist.get(index).classPart))
                {
                    sym.className=tokenlist.get(index).valuePart;
                    IC.classname=tokenlist.get(index).valuePart;
                    index++;
                    if(INH())
                    {
                        semantic.classNames.add(new symbolTable(sym.className,sym.type,sym.accessModifier,sym.category,sym.Extends));
                        System.out.println("\n"+semantic.classNames.get(classCounter));
                        if("{".equals(tokenlist.get(index).classPart))
                        {
                            index++;
                            if(CBODY())
                            {
                                if("}".equals(tokenlist.get(index).classPart))
                                {
                                    index++;
                                    classCounter++;
                                    if(CLASS_DEF())
                                    {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        else if("struct".equals(tokenlist.get(index).classPart))
        {
            sym.type=tokenlist.get(index).valuePart;
            index++;
            if("ID".equals(tokenlist.get(index).classPart))
            {
                sym.className=tokenlist.get(index).valuePart;
                IC.classname=tokenlist.get(index).valuePart;
                index++;
                if("{".equals(tokenlist.get(index).classPart))
                {
                    sym.category="General";
                    sym.Extends="-";
                    semantic.classNames.add(new symbolTable(sym.className,sym.type,sym.accessModifier,sym.category,sym.Extends));
                    System.out.println("\n"+semantic.classNames.get(classCounter));
                    index++;
                    if(STRCT_BODY())
                    {
                        if("}".equals(tokenlist.get(index).classPart))
                        {
                            index++;
                            classCounter++;
                            if(CLASS_DEF())
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        
        return false;
    }
    
    private boolean CLASS_DEF()
    {
        variableCounter=0;
        varfuncCounter=0;
        currentScope=0;
        semantic.Scope=0;
        if("AM".equals(tokenlist.get(index).classPart))
        {
            sym.accessModifier=tokenlist.get(index).valuePart;
            index++;
            if(CLASS_DEF1())
            {
                return true;
            }
        }
        
        else if("$".equals(tokenlist.get(index).classPart))
                {
                    return true;
                }
        return false;
    }
    
    private boolean CLASS_DEF1()
    {
        if(FINAL())
        {
        if("class".equals(tokenlist.get(index).classPart))
        {
            sym.type=tokenlist.get(index).valuePart;
            index++;
            if("ID".equals(tokenlist.get(index).classPart))
            {
                sym.className=tokenlist.get(index).valuePart;
                IC.classname=tokenlist.get(index).valuePart;
                index++;
                if(INH())
                {
                    semantic.classNames.add(new symbolTable(sym.className,sym.type,sym.accessModifier,sym.category,sym.Extends));
                    System.out.println("\n"+semantic.classNames.get(classCounter));
                    if("{".equals(tokenlist.get(index).classPart))
                    {
                        index++;
                        if(CBODY())
                        {
                            if("}".equals(tokenlist.get(index).classPart))
                            {
                                index++;
                                classCounter++;
                                if(CLASS_DEF())
                                {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        }
        
        else if("struct".equals(tokenlist.get(index).classPart))
        {
            sym.type=tokenlist.get(index).valuePart;
            index++;
            if("ID".equals(tokenlist.get(index).classPart))
            {
                sym.className=tokenlist.get(index).valuePart;
                IC.classname=tokenlist.get(index).valuePart;
                index++;
                if("{".equals(tokenlist.get(index).classPart))
                {
                    sym.category="General";
                    sym.Extends="-";
                    semantic.classNames.add(new symbolTable(sym.className,sym.type,sym.accessModifier,sym.category,sym.Extends));
                    System.out.println("\n"+semantic.classNames.get(classCounter));
                    index++;
                    if(STRCT_BODY())
                    {
                        if("}".equals(tokenlist.get(index).classPart))
                        {
                            index++;
                            classCounter++;
                            if(CLASS_DEF())
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        
        else if("abstract".equals(tokenlist.get(index).classPart))
        {
            sym.category=tokenlist.get(index).valuePart;
            index++;
            if("class".equals(tokenlist.get(index).classPart))
            {
                sym.type=tokenlist.get(index).valuePart;
                index++;
                if("ID".equals(tokenlist.get(index).classPart))
                {
                    IC.classname=tokenlist.get(index).valuePart;
                    sym.className=tokenlist.get(index).valuePart;
                    index++;
                    if(INH())
                    {
                        semantic.classNames.add(new symbolTable(sym.className,sym.type,sym.accessModifier,sym.category,sym.Extends));
                        System.out.println("\n"+semantic.classNames.get(classCounter));
                        if("{".equals(tokenlist.get(index).classPart))
                        {
                            index++;
                            if(CBODY())
                            {
                                if("}".equals(tokenlist.get(index).classPart))
                                {
                                    index++;
                                    classCounter++;
                                    if(CLASS_DEF())
                                    {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean FINAL()
    {
        if("final".equals(tokenlist.get(index).classPart))
        {
            sym.category=tokenlist.get(index).valuePart;
            myClass.category=tokenlist.get(index).valuePart;
            index++;
            return true;
        }
        else if("class".equals(tokenlist.get(index).classPart) || ("DT".equals(tokenlist.get(index).classPart)) ||
                ("ID".equals(tokenlist.get(index).classPart)) || ("void".equals(tokenlist.get(index).classPart)))
        {
            sym.category="General";
            myClass.category="General";
            return true;
        }
        return false;
    }
    
    
    private boolean CBODY()
    {
        if("DT".equals(tokenlist.get(index).classPart))
        {
            myClass.type=tokenlist.get(index).valuePart;
            myClass.AM="closed"; //default AM
            index++;
            if(CBODY1())
            {
                if(CBODY())
                {
                    return true;
                }
            }
        }
        
        else if("ID".equals(tokenlist.get(index).classPart))
        {
            boolean check=semantic.Lookup(tokenlist.get(index).valuePart, classCounter, semantic.classNames);
            if(check==true)
            {
                myClass.type=tokenlist.get(index).valuePart;
                myClass.AM="closed"; //default AM
                index++;
                if(CBODY2())
                {
                    if(CBODY())
                    {
                        return true;
                    }
                }
            }
            
            else
            {
                System.out.println("\nSemantic Error : class with name "+tokenlist.get(index).valuePart+" doesn't exist.");
            }
            
           
        }
        
        else if("AM".equals(tokenlist.get(index).classPart))
        {
            myClass.AM=tokenlist.get(index).valuePart;
            index++;
            if(CBODY6())
            {
                if(CBODY())
                {
                    return true;
                }
            }
        }
        else if("}".equals(tokenlist.get(index).classPart))
        {
            return true;
        }
        return false;
    }
    
    private boolean CBODY1()
    {
        if("ID".equals(tokenlist.get(index).classPart))
        {
            if("(".equals(tokenlist.get(index+1).classPart))
            {
                funcName=tokenlist.get(index).valuePart;
                IC.funcName=tokenlist.get(index).valuePart;
                IC.intermediateCode=IC.intermediateCode+"\n"+IC.classname+"_"+IC.funcName;
                myClass.name=tokenlist.get(index).valuePart;
                index++;
                if(CBODY10())
                {
                    return true;
                }
            }
            
            else if(!semantic.Lookup(tokenlist.get(index).valuePart,myClass.type,semantic.classNames.get(classCounter).classInfo))
            {
                myClass.name=tokenlist.get(index).valuePart;
                index++;
                if(CBODY10())
                {
                    return true;
                }
            }
            
            else
            {
                System.out.println("\nSemantic Error : Name \""+tokenlist.get(index).valuePart+""
                        + "\" is already declared in class "+semantic.classNames.get(classCounter).className);
                return false;
            }
            
        }
        
        else if(ADT())
        {
            return true;
        }
        
        return false;
    }
    
    private boolean CBODY10()
    {
        if(INIT())
        {
            if(LIST())
            {
                return true;
            }
        }
        
        else if("(".equals(tokenlist.get(index).classPart))
        {
            myClass.dummy="";
            index++;
            if(ARGS())
            {
                IC.intermediateCode=IC.intermediateCode+"_"+myClass.argType1;
                if(ARGS1())
                {
                    if(")".equals(tokenlist.get(index).classPart))
                    {
                        IC.intermediateCode=IC.intermediateCode+" PROC\n";
                        myClass.type=myClass.dummy+"->"+myClass.type;
                        myClass.Assigned='N';
                        myClass.category="General";
                        myClass.Static='N';
                        
                        if(!semantic.Lookup(myClass.name,myClass.type,semantic.classNames.get(classCounter).classInfo))
                        { 
                        semantic.classNames.get(classCounter).classInfo.add(new classTable(myClass.name,
                                myClass.type,myClass.AM,myClass.Assigned,myClass.category,myClass.Static));
                        System.out.println("\n"+semantic.classNames.get(classCounter).classInfo.get(varfuncCounter));
                        currentScope=semantic.createScope();
                        
                        
                        for(int i=0;i<dumFunc.size();i++)
                        {
                            dumFunc.get(i).Scope=currentScope;
                            semantic.classNames.get(classCounter).classInfo.get(varfuncCounter).functionData.add(new functionTable(
                            dumFunc.get(i)));
                            System.out.println("\n"+dumFunc.get(i));
                            variableCounter++;
                        }
                        dumFunc=new ArrayList<>();
                        
                        varfuncCounter++;
                        myClass=new classTable();
                        myFunc=new functionTable();
                        index++;
                        IC.temp=0;
                        //IC.value="";
                        //IC.dummy="";
                        //IC.funcName="";
                        //IC.variableName="";
                        if(BODY())
                        {
                            return true;
                        }
                        }
                        
                        else
                        {
                            System.out.println("\nFunction with the name \""+myClass.name+"\" and same argument list"
                                        + " already exist.");
                            return false;
                        }
                    }
                }
            }
        }
        
        return false;
    }
    
    private boolean CBODY2()
    {
        if("[".equals(tokenlist.get(index).classPart))
        {
            myClass.type=myClass.type+tokenlist.get(index).valuePart;
            index++;
            if(AOB())
            {
                return true;
            }
        }
        
        else if("ID".equals(tokenlist.get(index).classPart))
        {
            if("(".equals(tokenlist.get(index+1).classPart))
            {
                myClass.name=tokenlist.get(index).valuePart;
                IC.funcName=tokenlist.get(index).valuePart;
                index++;
                if(CBODY9())
                {
                    return true;
                }
            }
            
            else if(!semantic.Lookup(tokenlist.get(index).valuePart,myClass.type,semantic.classNames.get(classCounter).classInfo))
            {
                myClass.name=tokenlist.get(index).valuePart;
                index++;
                if(CBODY9())
                {
                    return true;
                }
            }
            
            else
            {
                System.out.println("\nSemantic Error : Variable \""+tokenlist.get(index).valuePart+""
                        + "\" is already declared in "+semantic.classNames.get(classCounter).type+" semantic.classNames.get(classCounter).className");
                return false;
            }
        }
        
        return false;
    }
    
    private boolean SUPER()
    {
        if("super".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(SUPER1())
            {
                return true;
            }
        }
        
        else if(CONBODY7())
        {
            return true;
        }
        return false;
    }
    
    private boolean SUPER1()
    {
        if("(".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(")".equals(tokenlist.get(index).classPart))
            {
                index++;
                if(";".equals(tokenlist.get(index).classPart))
                {
                    index++;
                    if(CONBODY())
                    {
                        return true;
                    }
                }
            }
        }
        
        else if(".".equals(tokenlist.get(index).classPart))
        {
            index++;
            if("ID".equals(tokenlist.get(index).classPart))
            {
                if(myClass.Lookup(semantic.classNames.get(classCounter),tokenlist.get(index).valuePart, semantic.classNames))
                    {
                    index++;
                    if(CONBODY3())
                    {
                        if(CONBODY())
                        {
                            return true;
                        }
                        
                    }
                    }
                    
                    else
                    {
                        System.out.println("\nUndeclared variable "+tokenlist.get(index).valuePart);
                    }
                
            }
        }
        return false;
    }
    
    private boolean CBODY3()
    {
        if("(".equals(tokenlist.get(index).classPart))
        {
            index++;
            myClass.dummy="";
            if(ARGS())
            {
                if(ARGS1())
                {
                    if(")".equals(tokenlist.get(index).classPart))
                    {
                        myClass.type=myClass.dummy+"->"+myClass.type;
                        myClass.Assigned='N';
                        myClass.category="General";
                        myClass.Static='N';
                        if(!semantic.Lookup(myClass.name,myClass.type,semantic.classNames.get(classCounter).classInfo))
                        {
                        semantic.classNames.get(classCounter).classInfo.add(new classTable(myClass.name,
                                myClass.type,myClass.AM,myClass.Assigned,myClass.category,myClass.Static));
                        System.out.println("\n"+semantic.classNames.get(classCounter).classInfo.get(varfuncCounter));
                        
                        currentScope=semantic.createScope();
                        
                        for(int i=0;i<dumFunc.size();i++)
                        {
                            dumFunc.get(i).Scope=currentScope;
                            semantic.classNames.get(classCounter).classInfo.get(varfuncCounter).functionData.add(new functionTable(
                            dumFunc.get(i)));
                            System.out.println("\n"+dumFunc.get(i));
                            variableCounter++;
                        }
                        dumFunc=new ArrayList<>();
                        varfuncCounter++;
                        myClass=new classTable();
                        myFunc=new functionTable();
                        
                        index++;
                        if("{".equals(tokenlist.get(index).classPart))
                        {
                            index++;
                            if(SUPER())
                            {
                                if("}".equals(tokenlist.get(index).classPart))
                                {
                                    index++;
                                    return true;
                                }
                            }
                        }
                        }
                        
                        else
                        {
                            System.out.println("\nConstructor with the name \""+myClass.name+"\" and same argument list"
                                        + " already exist.");
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean CBODY4()
    {
            if("(".equals(tokenlist.get(index).classPart))
            {
                myClass.dummy="";
                index++;
                if(ARGS())
                {
                   IC.intermediateCode=IC.intermediateCode+"_"+myClass.argType1;
                    if(ARGS1())
                    {
                        IC.intermediateCode="\n"+IC.intermediateCode+" PROC\n";
                        if(")".equals(tokenlist.get(index).classPart))
                        {
                            
                            myClass.type=myClass.dummy+"->"+myClass.type;
                            myClass.Assigned='N';
                            myClass.category="General";
                            myClass.Static='N';
                            
                            if(!semantic.Lookup(myClass.name,myClass.type,semantic.classNames.get(classCounter).classInfo))
                            {
                            semantic.classNames.get(classCounter).classInfo.add(new classTable(myClass.name,
                                myClass.type,myClass.AM,myClass.Assigned,myClass.category,myClass.Static));
                            System.out.println("\n"+semantic.classNames.get(classCounter).classInfo.get(varfuncCounter));
                            currentScope=semantic.createScope();
                            
                            for(int i=0;i<dumFunc.size();i++)
                            {
                                dumFunc.get(i).Scope=currentScope;
                                semantic.classNames.get(classCounter).classInfo.get(varfuncCounter).functionData.add(new functionTable(
                                dumFunc.get(i)));
                                System.out.println("\n"+dumFunc.get(i));
                                variableCounter++;
                            }
                            dumFunc=new ArrayList<>();
                            
                            varfuncCounter++;
                            myClass=new classTable();
                            myFunc=new functionTable();
                            index++;
                            if("{".equals(tokenlist.get(index).classPart))
                            {
                                index++;
                                if(MST())
                                {
                                if("}".equals(tokenlist.get(index).classPart))
                                {
                                    index++;
                                
                                return true;
                                }
                                }
                            }
                            }
                            
                            else
                            {
                                System.out.println("\nFunction with the name \""+myClass.name+"\" and same argument list"
                                        + " already exist.");
                                return false;
                            }
                        }
                    }
                }
            }
    
        return false;
    }
    
    private boolean CBODY9()
    {
            if(OBJ_DEC1())
            {
                
                return true;
            }
        
        else if(CBODY4())
        {
            return true;
        }
        return false;
    }
    
    private boolean CBODY6()
    {
        if("DT".equals(tokenlist.get(index).classPart))
        {
            myClass.type=tokenlist.get(index).valuePart;
            index++;
            if(CBODY1())
            {
                return true;
            }
        }
        
        else if("ID".equals(tokenlist.get(index).classPart))
        {
            boolean check=semantic.Lookup(tokenlist.get(index).valuePart, classCounter, semantic.classNames);
            if(check==true)
            {
                myClass.type=tokenlist.get(index).valuePart;
                index++;
                if(CBODY7())
                {
                    return true;
                }
            }
            
            else if("(".equals(tokenlist.get(index+1).classPart))
            {
                myClass.type="empty";
                myClass.name=tokenlist.get(index).valuePart;
                funcName=tokenlist.get(index).valuePart;
                index++;
                if(CBODY7())
                {
                    return true;
                }
            }
            
            else
            {
                System.out.println("\n1Semantic Error : class with name "+tokenlist.get(index).valuePart+" doesn't exist.");
                return false;
            }
        }
        
        else if(CBODY8())
        {
            return true;
        }
        return false;
        
    }
    
    private boolean CBODY7()
    {
        if("[".equals(tokenlist.get(index).classPart))
        {
            myClass.type=myClass.type+tokenlist.get(index).valuePart;
            index++;
            if(AOB())
            {
                return true;
            }
        }
        
        else if(CBODY3())
        {
            return true;
        }
        
        else if("ID".equals(tokenlist.get(index).classPart))
        {
            if("(".equals(tokenlist.get(index+1).classPart))
            {
                myClass.name=tokenlist.get(index).valuePart;
                funcName=tokenlist.get(index).valuePart;
                IC.funcName=tokenlist.get(index).valuePart;
                IC.intermediateCode=IC.intermediateCode+"\n"+IC.classname+"_"+IC.funcName;
                index++;
                if(CBODY9())
                {
                    return true;
                }
            }
            
            else if(!semantic.Lookup(tokenlist.get(index).valuePart,myClass.type,semantic.classNames.get(classCounter).classInfo))
            {
                myClass.name=tokenlist.get(index).valuePart;
                index++;
                if(CBODY9())
                {
                    return true;
                }
            }
            
            else
            {
                System.out.println("\nSemantic Error : Name \""+tokenlist.get(index).valuePart+""
                        + "\" is already declared in class "+semantic.classNames.get(classCounter).className);
                return false;
            }
        }
        return false;
    }
    
    private boolean CBODY8()
    {
        if(STAT())
        {
            if(FINAL())
            {
            if(RETURN_TYPE())
            {
                if("ID".equals(tokenlist.get(index).classPart))
                {
                    myClass.name=tokenlist.get(index).valuePart;
                    funcName=tokenlist.get(index).valuePart;
                    index++;
                    if("(".equals(tokenlist.get(index).classPart))
                    {
                        myClass.dummy="";
                        index++;
                        if(ARGS())
                        {
                            if(ARGS1())
                            {
                                if(")".equals(tokenlist.get(index).classPart))
                                {
                                    myClass.type=myClass.dummy+"->"+myClass.type;
                                    myClass.Assigned='N';
                                    if(!semantic.Lookup(myClass.name,myClass.type,semantic.classNames.get(classCounter).classInfo))
                                    {
                                    semantic.classNames.get(classCounter).classInfo.add(new classTable(myClass.name,
                                        myClass.type,myClass.AM,myClass.Assigned,myClass.category,myClass.Static));
                                    System.out.println("\n"+semantic.classNames.get(classCounter).classInfo.get(varfuncCounter));
                                    currentScope=semantic.createScope();
                                    
                                    for(int i=0;i<dumFunc.size();i++)
                                    {
                                        dumFunc.get(i).Scope=currentScope;
                                        semantic.classNames.get(classCounter).classInfo.get(varfuncCounter).functionData.add(new functionTable(
                                        dumFunc.get(i)));
                                        System.out.println("\n"+dumFunc.get(i));
                                        variableCounter++;
                                    }
                                    dumFunc=new ArrayList<>();
                                    varfuncCounter++;
                                    myClass=new classTable();
                                    myFunc=new functionTable();
                                    index++; 
                                    if("{".equals(tokenlist.get(index).classPart))
                                    {
                                        index++;
                                        if(MST())
                                        {
                                            if("}".equals(tokenlist.get(index).classPart))
                                            {
                                                index++;
                                                return true;
                                            }
                                        }
                                    }
                                    }
                                    
                                    else
                                    {
                                        System.out.println("\nFunction with the name \""+myClass.name+"\" and same argument list"
                                        + " already exist.");
                                        return false;
                                    }
                                }
                                
                            }
                        }
                    }
                }
            }
            }
        }
        
        else if("abstract".equals(tokenlist.get(index).classPart))
        {
            myClass.category=tokenlist.get(index).valuePart;
            index++;
            if(RETURN_TYPE())
            {
                if("ID".equals(tokenlist.get(index).classPart))
                {
                    myClass.name=tokenlist.get(index).valuePart;
                    index++;
                    if("(".equals(tokenlist.get(index).classPart))
                    {
                        index++;
                        if(")".equals(tokenlist.get(index).classPart))
                        {
                            myClass.type="empty->"+myClass.type;
                            myClass.Assigned='N';
                            myClass.Static='N';
                            semantic.classNames.get(classCounter).classInfo.add(new classTable(myClass.name,
                                myClass.type,myClass.AM,myClass.Assigned,myClass.category,myClass.Static));
                            System.out.println("\n"+semantic.classNames.get(classCounter).classInfo.get(varfuncCounter));
                            varfuncCounter++;
                            myClass=new classTable();
                            index++;
                            if(";".equals(tokenlist.get(index).classPart))
                            {
                                index++;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        
        return false;
    }
    
    private boolean STRCT_BODY()
    {
        if("DT".equals(tokenlist.get(index).classPart))
        {
            myClass.type=tokenlist.get(index).valuePart;
            myClass.AM="closed"; //default AM
            index++;
            if(CBODY1())
            {
                if(STRCT_BODY())
                {
                    return true;
                }
            }
        }
        
        else if("ID".equals(tokenlist.get(index).classPart))
        {
            boolean check=semantic.Lookup(tokenlist.get(index).valuePart, classCounter, semantic.classNames);
            if(check==true)
            {
                myClass.type=tokenlist.get(index).valuePart;
                myClass.AM="closed"; //default AM
                index++;
                if(CBODY2())
                {
                    if(STRCT_BODY())
                    {
                        return true;
                    }
                }
            }
            
            else
            {
                System.out.println("\nSemantic Error : class with name "+tokenlist.get(index).valuePart+" doesn't exist.");
            }
        }
        
        else if("AM".equals(tokenlist.get(index).classPart))
        {
            myClass.AM=tokenlist.get(index).valuePart;
            index++;
            if(STRCT_BODY6())
            {
                if(STRCT_BODY())
                {
                    return true;
                }
            }
        }
        
        else if("}".equals(tokenlist.get(index).classPart))
        {
            return true;
        }
        return false;
    }
    
    
    private boolean STRCT_BODY6()
    {
        if("DT".equals(tokenlist.get(index).classPart))
        {
            myClass.type=tokenlist.get(index).valuePart;
            index++;
            if(CBODY1())
            {
                return true;
            }
        }
        
        else if("ID".equals(tokenlist.get(index).classPart))
        {
            boolean check=semantic.Lookup(tokenlist.get(index).valuePart, classCounter, semantic.classNames);
            if(check==true)
            {
                myClass.type=tokenlist.get(index).valuePart;
                index++;
                if(CBODY7())
                {
                    return true;
                }
            }
            
            else if("(".equals(tokenlist.get(index+1).classPart))
            {
                myClass.type="empty";
                myClass.name=tokenlist.get(index).valuePart;
                index++;
                if(CBODY7())
                {
                    return true;
                }
            }
            
            else
            {
                System.out.println("\nSemantic Error : class with name "+tokenlist.get(index).valuePart+" doesn't exist.");
            }
        }
        
        else if(STRCT_BODY8())
        {
            return true;
        }
        return false;
    }
    
    
    private boolean STRCT_BODY8()
    {
        if(STAT())
        {
            if(FINAL())
            {
            if(RETURN_TYPE())
            {
                if("ID".equals(tokenlist.get(index).classPart))
                {
                    myClass.name=tokenlist.get(index).valuePart;
                    funcName=tokenlist.get(index).valuePart;
                    index++;
                    if("(".equals(tokenlist.get(index).classPart))
                    {
                        index++;
                        myClass.dummy="";
                        if(ARGS())
                        {
                            if(ARGS1())
                            {
                                
                                if(")".equals(tokenlist.get(index).classPart))
                                {
                                    myClass.type=myClass.dummy+"->"+myClass.type;
                                    myClass.Assigned='N';
                                    if(!semantic.Lookup(myClass.name,myClass.type,semantic.classNames.get(classCounter).classInfo))
                                    {
                                    semantic.classNames.get(classCounter).classInfo.add(new classTable(myClass.name,
                                        myClass.type,myClass.AM,myClass.Assigned,myClass.category,myClass.Static));
                                    System.out.println("\n"+semantic.classNames.get(classCounter).classInfo.get(varfuncCounter));
                                    currentScope=semantic.createScope();
                                    
                                    for(int i=0;i<dumFunc.size();i++)
                                    {
                                        dumFunc.get(i).Scope=currentScope;
                                        semantic.classNames.get(classCounter).classInfo.get(varfuncCounter).functionData.add(new functionTable(
                                        dumFunc.get(i)));
                                        System.out.println("\n"+dumFunc.get(i));
                                        variableCounter++;
                                    }
                                    dumFunc=new ArrayList<>();
                                    varfuncCounter++;
                                    myClass=new classTable();
                                    myFunc=new functionTable();
                                    index++; 
                                    if("{".equals(tokenlist.get(index).classPart))
                                    {
                                        index++;
                                        if(MST())
                                        {
                                            if("}".equals(tokenlist.get(index).classPart))
                                            {
                                                index++;
                                                return true;
                                            }
                                        }
                                    }
                                    }
                                    
                                    else
                                    {
                                        System.out.println("\nFunction with the name \""+myClass.name+"\" and same argument list"
                                        + " already exist.");
                                        return false;
                                    }
                                }
                                
                            }
                        }
                    }
                }
            }
            }
        }
        return false;
    }
    
    private boolean STAT()
    {
        if("static".equals(tokenlist.get(index).classPart))
        {
            myClass.Static='Y';
            index++;
            return true;
        }
        
        return false;
    }
    
    private boolean RETURN_TYPE()
    {
        if("DT".equals(tokenlist.get(index).classPart))
        {
            myClass.type=tokenlist.get(index).valuePart;
            index++;
            return true;
        }
        else if("ID".equals(tokenlist.get(index).classPart))
        {
            myClass.type=tokenlist.get(index).valuePart;
            index++;
            return true;
        }
        
        else if("void".equals(tokenlist.get(index).classPart))
        {
            myClass.type=tokenlist.get(index).valuePart;
            index++;
            return true;
        }
        return false;
    }
    
    private boolean MAIN()
    {
        if("AM".equals(tokenlist.get(index).classPart))
        {
            myClass.AM=tokenlist.get(index).valuePart;
            index++;
            if("static".equals(tokenlist.get(index).classPart))
            {
                myClass.Static='Y';
                index++;
                if("void".equals(tokenlist.get(index).classPart))
                {
                    myClass.type="void->void";
                    index++;
                    if("main".equals(tokenlist.get(index).classPart))
                    {
                        myClass.name=tokenlist.get(index).valuePart;
                        IC.funcName=tokenlist.get(index).valuePart;
                        IC.intermediateCode="\n"+IC.intermediateCode+IC.funcName+" PROC";
                        index++;
                        if("(".equals(tokenlist.get(index).classPart))
                        {
                            index++;
                            if(")".equals(tokenlist.get(index).classPart))
                            {
                                myClass.Assigned='N';
                                myClass.category="General";
                                semantic.classNames.get(classCounter).classInfo.add(new classTable(myClass.name,
                                    myClass.type,myClass.AM,myClass.Assigned,myClass.category,myClass.Static));
                                System.out.println("\n"+semantic.classNames.get(classCounter).classInfo.get(varfuncCounter));
                                myClass=new classTable();
                                varfuncCounter++;
                                currentScope=semantic.createScope();
                                index++;
                                if("{".equals(tokenlist.get(index).classPart))
                                {
                                    index++;
                                    if(MST())
                                    {
                                        if("}".equals(tokenlist.get(index).classPart))
                                        {
                                            IC.temp=0;
                                            index++;
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean INH()
    {
        if("extends".equals(tokenlist.get(index).classPart))
        {
            index++;
            if("ID".equals(tokenlist.get(index).classPart))
            {
                boolean check=semantic.Lookup(tokenlist.get(index).valuePart, classCounter, semantic.classNames);
                if(check==true)
                {
                    sym.Extends=tokenlist.get(index).valuePart;
                    index++;
                    return true;
                }
                else
                {
                    System.out.println("\nSemantic Error : class with name "+tokenlist.get(index).valuePart+" doesn't exist.");
                    return false;
                }
                
            }
        }
        else if("{".equals(tokenlist.get(index).classPart))
        {
            sym.Extends="Object";
            return true;
        }
        return false;
    }
    
    private boolean CONBODY()
    {
        if("ID".equals(tokenlist.get(index).classPart))
        {
            if("ID".equals(tokenlist.get(index+1).classPart))
            {
                if(semantic.Lookup(tokenlist.get(index).valuePart, classCounter, semantic.classNames))
                {
                    myFunc.type=tokenlist.get(index).valuePart;
                    index++;
                    if(CONBODY1())
                    {
                        if(CONBODY())
                        {
                            return true;
                        }
                    }
                }
                else
                {
                    System.out.println("\nSemantic Error : Class with name "+tokenlist.get(index).valuePart+" "
                        + "doesn't exist.");
                    return false;
                }
            }
            
            else if("=".equals(tokenlist.get(index+1).classPart) || "IncDec".equals(tokenlist.get(index+1).classPart) ||
                    "AOP".equals(tokenlist.get(index+1).classPart))
            {
                if(myClass.Lookup(tokenlist.get(index).valuePart, currentScope,
                        semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData))
                {
                    IC.variableName=tokenlist.get(index).valuePart;
                    index++;
                    if(CONBODY1())
                    {
                        if(CONBODY())
                        {
                            return true;
                        }
                    }
                }
                
                else if(myClass.Lookup(semantic.classNames.get(classCounter).classInfo, varfuncCounter, tokenlist.get(index).valuePart))
                {
                    index++;
                    if(CONBODY1())
                    {
                        if(CONBODY())
                        {
                            return true;
                        }
                    }
                }
                
                else
                {
                    System.out.println("\nSemantic Error : Undeclared Variable.");
                    return false;
                }
            }
            
            else if(".".equals(tokenlist.get(index+1).classPart) && "ID".equals(tokenlist.get(index+2).classPart))
            {
                if(myClass.Lookup(semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData,
                semantic.classNames.get(classCounter).classInfo, tokenlist.get(index).valuePart, tokenlist.get(index+2).valuePart,
                semantic.classNames))
                {
                    IC.variableName=tokenlist.get(index).valuePart;
                    index++;
                    if(CONBODY1())
                    {
                        if(CONBODY())
                        {
                            return true;
                        }
                    }
                }
                
                else
                {
                    System.out.println("\nSemantic Error : Undeclared "
                            + "Variable "+tokenlist.get(index+2).valuePart+" in the class type object "+tokenlist.get(index).valuePart+" "
                                    + "or either the variable "+tokenlist.get(index).valuePart+" \nis not"
                                            + " of class type or either "+tokenlist.get(index).valuePart+" "
                                                    + "is undeclared.");
                    return false;
                }
            }
            
            else if("[".equals(tokenlist.get(index+1).classPart) && "]".equals(tokenlist.get(index+2).classPart))
            {
                if(semantic.Lookup(tokenlist.get(index).valuePart, classCounter, semantic.classNames))
                {
                    myFunc.type=tokenlist.get(index).valuePart;
                    index++;
                    if(CONBODY1())
                    {
                        if(CONBODY())
                        {
                            return true;
                        }
                    }
                }
                
                else
                {
                    System.out.println("\nSemantic Error : Class with name "+tokenlist.get(index).valuePart+" "
                            + "doesn't exist.");
                    return false;
                }
            }
            
            else if("[".equals(tokenlist.get(index+1).classPart))
            {
                if(myClass.Lookup(semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData,
                        semantic.classNames.get(classCounter).classInfo, tokenlist.get(index).valuePart))
                {
                    IC.variableName=tokenlist.get(index).valuePart;
                    index++;
                    if(CONBODY1())
                    {
                        if(CONBODY())
                        {
                            return true;
                        }
                    }
                }
                
                else
                {
                    System.out.println("\nSemantic Error : The variable "+tokenlist.get(index).valuePart+" "
                            + "is not of the array type or either not declared.");
                    return false;
                }
            }
        }
        
        else if(IF_ELSE())
        {
            if(CONBODY())
            {
                return true;
            }
        }
        
        else if(SWITCH())
        {
            if(CONBODY())
            {
                return true;
            }
        }
        
        else if("DT".equals(tokenlist.get(index).classPart))
        {
            myFunc.type=tokenlist.get(index).valuePart;
            index++;
            if(CONBODY2())
            {
                if(CONBODY())
                {
                    return true;
                }
            }
        }
        
        else if(WHILE_ST())
        {
            if(CONBODY())
            {
                return true;
            }
        }
        
        else if(FOR())
        {
            if(CONBODY())
            {
                return true;
            }
        }
        
        else if(DO_WHILE())
        {
            if(CONBODY())
            {
                return true;
            }
        }
        
        else if(FOR_EACH())
        {
            if(CONBODY())
            {
                return true;
            }
        }
        
        else if("this".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(".".equals(tokenlist.get(index).classPart))
            {
                index++;
                if("ID".equals(tokenlist.get(index).classPart))
                {
                    IC.variableName=""+IC.classname+"."+tokenlist.get(index).valuePart;
                    if(myClass.Lookup(semantic.classNames.get(classCounter).classInfo, varfuncCounter,tokenlist.get(index).valuePart))
                    {
                    index++;
                    if(CONBODY3())
                    {
                        if(CONBODY())
                        {
                            return true;
                        }
                        
                    }
                    }
                    
                    else
                    {
                        System.out.println("class doesn't contain Identifier with name "+tokenlist.get(index).valuePart);
                        return false;
                    }
                }
            }
        }
        
        else if("super".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(".".equals(tokenlist.get(index).classPart))
            {
                index++;
                if("ID".equals(tokenlist.get(index).classPart))
                {
                    if(myClass.Lookup(semantic.classNames.get(classCounter),tokenlist.get(index).valuePart, semantic.classNames))
                    {
                    index++;
                    if(CONBODY3())
                    {
                        if(CONBODY())
                        {
                            return true;
                        }
                        
                    }
                    }
                    
                    else
                    {
                        System.out.println("\nUndeclared variable "+tokenlist.get(index).valuePart);
                    }
                }
            }
        }
        
        else if("}".equals(tokenlist.get(index).classPart))
        {
            return true;
        }
        return false;
    }
    
    
    private boolean CONBODY7()
    {
        if("ID".equals(tokenlist.get(index).classPart))
        {
            if("ID".equals(tokenlist.get(index+1).classPart))
            {
                if(semantic.Lookup(tokenlist.get(index).valuePart, classCounter, semantic.classNames))
                {
                    myFunc.type=tokenlist.get(index).valuePart;
                    index++;
                    if(CONBODY1())
                    {
                        if(CONBODY())
                        {
                            return true;
                        }
                    }
                }
                else
                {
                    System.out.println("\nSemantic Error : Class with name "+tokenlist.get(index).valuePart+" "
                        + "doesn't exist.");
                    return false;
                }
            }
            
            else if("=".equals(tokenlist.get(index+1).classPart) || "IncDec".equals(tokenlist.get(index+1).classPart) ||
                    "AOP".equals(tokenlist.get(index+1).classPart))
            {
                if(myClass.Lookup(tokenlist.get(index).valuePart, currentScope,
                        semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData))
                {
                    IC.variableName=tokenlist.get(index).valuePart;
                    index++;
                    if(CONBODY1())
                    {
                        if(CONBODY())
                        {
                            return true;
                        }
                    }
                }
                
                else if(myClass.Lookup(semantic.classNames.get(classCounter).classInfo, varfuncCounter, tokenlist.get(index).valuePart))
                {
                    index++;
                    if(CONBODY1())
                    {
                        if(CONBODY())
                        {
                            return true;
                        }
                    }
                }
                
                else
                {
                    System.out.println("\nSemantic Error : Undeclared Variable.");
                    return false;
                }
            }
            
            else if(".".equals(tokenlist.get(index+1).classPart) && "ID".equals(tokenlist.get(index+2).classPart))
            {
                if(myClass.Lookup(semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData,
                semantic.classNames.get(classCounter).classInfo, tokenlist.get(index).valuePart, tokenlist.get(index+2).valuePart,
                semantic.classNames))
                {
                    IC.variableName=tokenlist.get(index).valuePart;
                    index++;
                    if(CONBODY1())
                    {
                        if(CONBODY())
                        {
                            return true;
                        }
                    }
                }
                
                else
                {
                    System.out.println("\nSemantic Error : Undeclared "
                            + "Variable "+tokenlist.get(index+2).valuePart+" in the class type object "+tokenlist.get(index).valuePart+" "
                                    + "or either the variable "+tokenlist.get(index).valuePart+" \nis not"
                                            + " of class type or either "+tokenlist.get(index).valuePart+" "
                                                    + "is undeclared.");
                    return false;
                }
            }
            
            else if("[".equals(tokenlist.get(index+1).classPart) && "]".equals(tokenlist.get(index+2).classPart))
            {
                if(semantic.Lookup(tokenlist.get(index).valuePart, classCounter, semantic.classNames))
                {
                    myFunc.type=tokenlist.get(index).valuePart;
                    index++;
                    if(CONBODY1())
                    {
                        if(CONBODY())
                        {
                            return true;
                        }
                    }
                }
                
                else
                {
                    System.out.println("\nSemantic Error : Class with name "+tokenlist.get(index).valuePart+" "
                            + "doesn't exist.");
                    return false;
                }
            }
            
            else if("[".equals(tokenlist.get(index+1).classPart))
            {
                if(myClass.Lookup(semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData,
                        semantic.classNames.get(classCounter).classInfo, tokenlist.get(index).valuePart))
                {
                    IC.variableName=tokenlist.get(index).valuePart;
                    index++;
                    if(CONBODY1())
                    {
                        if(CONBODY())
                        {
                            return true;
                        }
                    }
                }
                
                else
                {
                    System.out.println("\nSemantic Error : The variable "+tokenlist.get(index).valuePart+" "
                            + "is not of the array type or either not declared.");
                    return false;
                }
            }
        }
        
        else if(IF_ELSE())
        {
            if(CONBODY())
            {
                return true;
            }
        }
        
        else if(SWITCH())
        {
            if(CONBODY())
            {
                return true;
            }
        }
        
        else if("DT".equals(tokenlist.get(index).classPart))
        {
            myFunc.type=tokenlist.get(index).valuePart;
            index++;
            if(CONBODY2())
            {
                if(CONBODY())
                {
                    return true;
                }
            }
        }
        
        else if(WHILE_ST())
        {
            if(CONBODY())
            {
                return true;
            }
        }
        
        else if(FOR())
        {
            if(CONBODY())
            {
                return true;
            }
        }
        
        else if(DO_WHILE())
        {
            if(CONBODY())
            {
                return true;
            }
        }
        
        else if(FOR_EACH())
        {
            if(CONBODY())
            {
                return true;
            }
        }
        
        else if("this".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(".".equals(tokenlist.get(index).classPart))
            {
                index++;
                if("ID".equals(tokenlist.get(index).classPart))
                {
                    IC.variableName=""+IC.classname+"."+tokenlist.get(index).valuePart;
                    if(myClass.Lookup(semantic.classNames.get(classCounter).classInfo, varfuncCounter,tokenlist.get(index).valuePart))
                    {
                    index++;
                    if(CONBODY3())
                    {
                        if(CONBODY())
                        {
                            return true;
                        }
                        
                    }
                    }
                    
                    else
                    {
                        System.out.println("class doesn't contain Identifier with name "+tokenlist.get(index).valuePart);
                        return false;
                    }
                }
            }
        }
        
        else if("}".equals(tokenlist.get(index).classPart))
        {
            return true;
        }
        
        return false;
    }
    
    private boolean CONBODY1()
    {
        if("ID".equals(tokenlist.get(index).classPart))
        {
            if(semantic.Lookup(tokenlist.get(index).valuePart, myFunc.type, semantic.classNames.get(classCounter).classInfo, 1))
            {
                myFunc.Name=tokenlist.get(index).valuePart;
                System.out.println("\nWarning : Local Variable "+myFunc.Name+" hides a field");
            }
            if(!myClass.Lookup(tokenlist.get(index).valuePart, currentScope, semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData))
            {
                myFunc.Name=tokenlist.get(index).valuePart;
                index++;
                if(OBJ_DEC1())
                {
                    return true;
                }
            }
            
            else
            {
                System.out.println("\nSemantic Error : Variable \""+tokenlist.get(index).valuePart+""
                                + "\" is already declared in current Scope ");
                return false;
            }
            
        }
        
        else if("[".equals(tokenlist.get(index).classPart))
        {
            myFunc.type=myFunc.type+tokenlist.get(index).valuePart;
            index++;
            if(CONBODY4())
            {
                return true;
            }
        }
        
        
        else if(INIT0())
        {
            if(";".equals(tokenlist.get(index).classPart))
            {
                index++;
                return true;
            }
        }
        
        else if("=".equals(tokenlist.get(index).classPart))
        {
            index++;
            IC.value="";
            if(INIT3())
            {
                if(checkType.T10!=null)
                {
                    if(checkType.T10.equals(myClass.dType))
                    {
                        IC.intermediateCode=IC.intermediateCode+"\n"+IC.variableName+" = "+IC.value;
                        if(";".equals(tokenlist.get(index).classPart))
                        {
                            index++;
                            return true;
                        }
                    }
                    else
                    {
                        System.out.println("\nSemantic Error : "+checkType.T10+" cannot be converted to "
                                + ""+myClass.dType);
                        return false;
                    }
                }
            }
        }
        
        else if(PARA7())
        {
            return true;
        }
        
        return false;
    }
    
    private boolean CONBODY4()
    {
        if(AOB())
        {
            return true;
        }
        
        else if(OE())
        {
            if("Integer".equals(checkType.T10))
            {
                IC.value=IC.value+" * size_of_each_array_member";
                IC.intermediateCode=IC.intermediateCode+"\nt"+(IC.temp+1)+" = "+IC.value;
                IC.value="t"+(IC.temp+1);
                IC.variableName=IC.variableName+"["+IC.value+"]";
                if("]".equals(tokenlist.get(index).classPart))
                {
                    myFunc.type=myFunc.type+tokenlist.get(index).valuePart;
                    index++;
                    if(INIT5())
                    {
                        if(";".equals(tokenlist.get(index).classPart))
                        {
                            index++;
                            return true;
                        }
                    }
                }
            }
            else
            {
                System.out.println("\nSemantic Error : Incompatible Types. Array type can only have Integer "
                        + "value inside [].");
                return false;
            }
        }
        return false;
    }
    
    private boolean CONBODY2()
    {
        if(ADT())
        {
            return true;
        }
        
        else if("ID".equals(tokenlist.get(index).classPart))
        {
            if(semantic.Lookup(tokenlist.get(index).valuePart, myFunc.type, semantic.classNames.get(classCounter).classInfo, 1))
            {
                myFunc.Name=tokenlist.get(index).valuePart;
                System.out.println("\nWarning : Local Variable "+myFunc.Name+" hides a field");
            }
            if(!myClass.Lookup(tokenlist.get(index).valuePart, currentScope, semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData))
            {
                myFunc.Name=tokenlist.get(index).valuePart;
                index++;
                if(INIT())
                {
                    if(LIST())
                    {
                        return true;
                    }
                }   
            }
            
            else
            {
                System.out.println("\nSemantic Error : Variable \""+tokenlist.get(index).valuePart+""
                                + "\" is already declared in current Scope ");
                return false;
            }
        }
        return false;
    }
    
    private boolean CONBODY3()
    {
        if("=".equals(tokenlist.get(index).classPart))
        {
            index++;
            IC.value="";
            if(INIT3())
            {
                if(checkType.T10.equals(myClass.dType))
                {
                    if(";".equals(tokenlist.get(index).classPart))
                    {
                        IC.intermediateCode=IC.intermediateCode+"\n"+IC.variableName+" = "+IC.value;
                        index++;
                        return true;
                    }
                }
                else
                {
                    System.out.println("\nSemantic Error : "+checkType.T10+" cannot be converted to "
                            + ""+myClass.dType);
                    return false;
                }
            }
        }
        
        else if(INIT0())
        {
            if(";".equals(tokenlist.get(index).classPart))
            {
                index++;
                return true;
            }
        }
        
        else if(PARA7())
        {
            return true;
        }
        
        else if("[".equals(tokenlist.get(index).classPart))
        {
            myFunc.type=myFunc.type+tokenlist.get(index).valuePart;
            index++;
            if(OE())
            {
                if("Integer".equals(checkType.T10))
                {
                    IC.value=IC.value+" * size_of_each_array_member";
                IC.intermediateCode=IC.intermediateCode+"\nt"+(IC.temp+1)+" = "+IC.value;
                IC.value="t"+(IC.temp+1);
                IC.variableName=IC.variableName+"["+IC.value+"]";
                if("]".equals(tokenlist.get(index).classPart))
                {
                    myFunc.type=myFunc.type+tokenlist.get(index).valuePart;
                    if(myClass.Lookup(myFunc.Name, currentScope, 
                    semantic.classNames.get(classCounter).classInfo.get(varfuncCounter).functionData, myFunc.type))
                {
                    index++;
                    if(INIT5())
                    {
                        if(";".equals(tokenlist.get(index).classPart))
                        {
                            index++;
                            return true;
                        }
                    }
                }
                
                else if(semantic.Lookup(myFunc.Name,myFunc.type,semantic.classNames.get(classCounter).classInfo))
                {
                    index++;
                    if(INIT5())
                    {
                        if(";".equals(tokenlist.get(index).classPart))
                        {
                            index++;
                            return true;
                        }
                    }
                }
                
                else 
                {
                    System.out.println("The Identifier "+myFunc.Name+" is not of the array type");
                    return false;
                }
                }
            }
                else
                {
                    System.out.println("\nSemantic Error : Incompatible Types. Array type can only have Integer "
                        + "value inside [].");
                    return false;
                }
            }
        }
        return false;
    }
    
    private boolean ARGS()
    {
        myClass.argType1="";
        if("DT".equals(tokenlist.get(index).classPart))
        {
            
            myClass.argType1=tokenlist.get(index).valuePart;
            //IC.intermediateCode=IC.intermediateCode+"_"+tokenlist.get(index).valuePart;
            myClass.dummy=myClass.dummy+tokenlist.get(index).valuePart;
            index++;
            if(ARGS0())
            {
                return true;
            }
        }
        
        else if("ID".equals(tokenlist.get(index).classPart))
        {
            boolean check=semantic.Lookup(tokenlist.get(index).valuePart, classCounter, semantic.classNames);
            if(check==true)
            {
                myClass.argType1=tokenlist.get(index).valuePart;
                //IC.intermediateCode=IC.intermediateCode+"_"+tokenlist.get(index).valuePart;
                myClass.dummy=myClass.dummy+tokenlist.get(index).valuePart;
                index++;
                if(ARGS0())
                {
                    return true;
                } 
            }
            
            else
            {
                System.out.println("Semantic Error : class with name "+tokenlist.get(index).valuePart+" doesn't exist.");
                return false;
            }
            
        }
        
        else if("void".equals(tokenlist.get(index).classPart))
        {
            myClass.argType1=tokenlist.get(index).valuePart;
            //IC.intermediateCode=IC.intermediateCode+"_"+tokenlist.get(index).valuePart;
            myClass.dummy=myClass.dummy+tokenlist.get(index).valuePart;
            index++;
            return true;
        }
        return false;
    }
    
    private boolean ARGS0()
    {
        if("[".equals(tokenlist.get(index).classPart))
        {
            myClass.argType1=myClass.argType1+tokenlist.get(index).valuePart;
            myClass.dummy=myClass.dummy+tokenlist.get(index).valuePart;
            index++;
            if("]".equals(tokenlist.get(index).classPart))
            {
                myClass.argType1=myClass.argType1+tokenlist.get(index).valuePart;
                myClass.dummy=myClass.dummy+tokenlist.get(index).valuePart;
                index++;
                if("ID".equals(tokenlist.get(index).classPart))
                {
                    myFunc.type=myClass.argType1;
                    myFunc.Name=tokenlist.get(index).valuePart;
                    myFunc.Assigned='Y';
                    myFunc.Scope=0;
                    dumFunc.add(myFunc);
                    myFunc=new functionTable();
                    index++;
                    return true;
                }
            }
        }
        
        else if("ID".equals(tokenlist.get(index).classPart))
        {
            myFunc.type=myClass.argType1;
            myFunc.Name=tokenlist.get(index).valuePart;
            myFunc.Assigned='Y';
            myFunc.Scope=0;
            dumFunc.add(myFunc);
            myFunc=new functionTable();
            index++;
            return true;
        }
        
        return false;
    }
    
    private boolean ARGS1()
    {
        if(",".equals(tokenlist.get(index).classPart))
        {
            myClass.dummy=myClass.dummy+tokenlist.get(index).valuePart;
            index++;
            if(ARGS())
            {
                //IC.intermediateCode=IC.intermediateCode+"_"+myClass.argType1;
                if(ARGS1())
                {
                    return true;
                }
            }
        }
        
        else if(")".equals(tokenlist.get(index).classPart))
        {
            return true;
        }
        
        return false;
    }
    
    private boolean ADT()
    {
        if("[".equals(tokenlist.get(index).classPart))
        {
            if(myClass.type!=null)
            {
                myClass.type=myClass.type+tokenlist.get(index).valuePart;
                index++;
                if("]".equals(tokenlist.get(index).classPart))
                {
                    myClass.type=myClass.type+tokenlist.get(index).valuePart;
                    index++;
                    if("ID".equals(tokenlist.get(index).classPart))
                    {
                        if(!semantic.Lookup(tokenlist.get(index).valuePart,myClass.type,semantic.classNames.get(classCounter).classInfo))
                        {
                            myClass.name=tokenlist.get(index).valuePart;
                            index++;
                            if("=".equals(tokenlist.get(index).classPart))
                            {
                                myClass.Assigned='Y';
                                index++;
                                if(ADT1())
                                {   
                                    if(";".equals(tokenlist.get(index).classPart))
                                    {
                                        myClass.Static='N';
                                        myClass.category="General";
                                        semantic.classNames.get(classCounter).classInfo.add(new classTable(myClass.name,myClass.type,myClass.AM,
                                        myClass.Assigned,myClass.category,myClass.Static));
                                        System.out.println("\n"+semantic.classNames.get(classCounter).classInfo.get(varfuncCounter));
                                        varfuncCounter++;
                                        myClass=new classTable();
                                        index++;
                                        return true;
                                    }
                                }   
                            }
                        }
                        
                        else
                        {
                            System.out.println("\nSemantic Error : Variable \""+tokenlist.get(index).valuePart+""
                                + "\" is already declared in class "+semantic.classNames.get(classCounter).className);
                            return false;
                        }
                    }
                }
            }
            
            else if(myFunc.type!=null)
            {
                myFunc.type=myFunc.type+tokenlist.get(index).valuePart;
                index++;
                if("]".equals(tokenlist.get(index).classPart))
                {
                    myFunc.type=myFunc.type+tokenlist.get(index).valuePart;
                    index++;
                    if("ID".equals(tokenlist.get(index).classPart))
                    {
                        if(!myClass.Lookup(tokenlist.get(index).valuePart, currentScope, semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData))
                        {
                            myFunc.Name=tokenlist.get(index).valuePart;  
                            index++;
                            if("=".equals(tokenlist.get(index).classPart))
                            {
                                myFunc.Assigned='Y';
                                index++;
                                if(ADT1())
                                {
                                    if(";".equals(tokenlist.get(index).classPart))
                                    {
                                        semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData.add(new functionTable(
                                        myFunc.Name, myFunc.type, currentScope, myFunc.Assigned));
                                        myFunc.Scope=currentScope;
                                        System.out.println("\n"+myFunc);
                                        variableCounter++;
                                        myFunc=new functionTable();
                                        index++;
                                        return true;
                                    }
                                }
                            }
                        }
                        
                        else
                        {
                            System.out.println("\nSemantic Error : Variable \""+tokenlist.get(index).valuePart+""
                                + "\" is already declared in current Scope ");
                            return false;
                        }
                    }
                }

            }
        }
        return false;
    }
    
    private boolean ADT1()
    {
        if("new".equals(tokenlist.get(index).classPart))
        {
            index++;
            if("DT".equals(tokenlist.get(index).classPart))
            {
                String dummy1=tokenlist.get(index).valuePart;
                index++;
                if("[".equals(tokenlist.get(index).classPart))
                {
                    dummy1=dummy1+tokenlist.get(index).valuePart;
                    index++;
                    if(OE())
                    {
                        if("Integer".equals(checkType.T10))
                        {
                        if("]".equals(tokenlist.get(index).classPart))
                        {
                            dummy1=dummy1+tokenlist.get(index).valuePart;
                            if(myClass.type!=null)
                            {
                                if(dummy1.equals(myClass.type))
                                {
                                    index++;
                                    return true;
                                }
                                else
                                {
                                    System.out.println("Semantic Error : "+myClass.type+" cannot be converted to "+dummy1);
                                    return false;
                                }
                            }
                            
                            else if(myFunc.type!=null)
                            {
                                if(dummy1.equals(myFunc.type))
                                {
                                    index++;
                                    return true;
                                }
                                else
                                {
                                    System.out.println("Semantic Error : "+myFunc.type+" cannot be converted to "+dummy1);
                                    return false;
                                }
                            }
                            
                        }
                    }
                        else
                        {
                            System.out.println("\nSemantic Error : Incompatible Types. Array type can only have Integer "
                                + "value inside [].");
                            return false;
                        }
                    }
                }
            }
        }
        
        else if("{".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(CONST())
            {
                if(CONST2())
                {
                    if("}".equals(tokenlist.get(index).classPart))
                    {
                        index++;
                        return true;
                    }
                }
            }
                
        }
        return false;
    }
    
    private boolean AOB()
    {
            if("]".equals(tokenlist.get(index).classPart))
            {
                if(myClass.type!=null)
                {
                    myClass.type=myClass.type+tokenlist.get(index).valuePart;
                    index++;
                    if("ID".equals(tokenlist.get(index).classPart))
                    {
                        if(!semantic.Lookup(tokenlist.get(index).valuePart,myClass.type,semantic.classNames.get(classCounter).classInfo))
                        {
                            myClass.name=tokenlist.get(index).valuePart;
                            index++;
                            if("=".equals(tokenlist.get(index).classPart))
                            {
                            myClass.Assigned='Y';
                            index++;
                            if(AOB1())
                            {
                                if(";".equals(tokenlist.get(index).classPart))
                                {
                                    myClass.Static='N';
                                    myClass.category="General";
                                    semantic.classNames.get(classCounter).classInfo.add(new classTable
                                        (myClass.name,myClass.type,myClass.AM,
                                        myClass.Assigned,myClass.category,myClass.Static));
                                    System.out.println("\n"+semantic.classNames.get(classCounter).classInfo.get(varfuncCounter));
                                    varfuncCounter++;
                                    myClass=new classTable();
                                    index++;
                                    return true;
                                }
                            }
                        }
                        }
                        
                        else
                        {
                            System.out.println("\nSemantic Error : Variable \""+tokenlist.get(index).valuePart+""
                                + "\" is already declared in class "+semantic.classNames.get(classCounter).className);
                            return false;
                        }
                        
                    }
                }
                
                else if(myFunc.type!=null)
                {
                    myFunc.type=myFunc.type+tokenlist.get(index).valuePart;
                    index++;
                    if("ID".equals(tokenlist.get(index).classPart))
                    {
                        if(!myClass.Lookup(tokenlist.get(index).valuePart, currentScope, semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData))
                        {
                           myFunc.Name=tokenlist.get(index).valuePart;
                        index++;
                        if("=".equals(tokenlist.get(index).classPart))
                        {
                            myFunc.Assigned='Y';
                            index++;
                            if(AOB1())
                            {
                                if(";".equals(tokenlist.get(index).classPart))
                                {   
                                    semantic.classNames.get(classCounter).classInfo.get
                                        (varfuncCounter-1).functionData.add(new functionTable(
                                        myFunc.Name, myFunc.type, currentScope, myFunc.Assigned));
                                    System.out.println("\n"+semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData.get(variableCounter));
                                    variableCounter++;
                                    myFunc=new functionTable();
                                    index++;
                                    return true;
                                }
                            }
                        } 
                        }
                        
                        else
                        {
                            System.out.println("\nSemantic Error : Variable \""+tokenlist.get(index).valuePart+""
                                + "\" is already declared in current Scope ");
                            return false;
                        }
                        
                    }
                }
                
            }
        
        return false;
    }
    
    private boolean AOB1()
    {
        if("new".equals(tokenlist.get(index).classPart))
        {
            index++;
            if("ID".equals(tokenlist.get(index).classPart))
            {
                String dummy1=tokenlist.get(index).valuePart;
                index++;
                if("[".equals(tokenlist.get(index).classPart))
                {
                    dummy1=dummy1+tokenlist.get(index).valuePart;
                    index++;
                    if(OE())
                    {
                        if("Integer".equals(checkType.T10))
                        {
                        if("]".equals(tokenlist.get(index).classPart))
                        {
                            dummy1=dummy1+tokenlist.get(index).valuePart;
                            if(myClass.type!=null)
                            {
                            if(dummy1.equals(myClass.type))
                            {
                                index++;
                                return true;
                            }
                            else
                            {
                                System.out.println("Semantic Error : "+myClass.type+" cannot be converted to "+dummy1);
                                return false;
                            }
                            }
                            
                            else if(myFunc.type!=null)
                            {
                                if(dummy1.equals(myFunc.type))
                            {
                                index++;
                                return true;
                            }
                            else
                            {
                                System.out.println("Semantic Error : "+myFunc.type+" cannot be converted to "+dummy1);
                                return false;
                            }
                            }
                        }
                        }
                        
                        else
                        {
                            System.out.println("\nSemantic Error : Incompatible Types. Array type can only have Integer "
                                + "value inside [].");
                            return false;
                        }
                    }
                }
            }
        }
        
        else if("{".equals(tokenlist.get(index).classPart))
        {
            index++;
            if("}".equals(tokenlist.get(index).classPart))
            {
                index++;
                return true;
            }
        }
        return false;
    }
    
    private boolean CONST2()
    {
        if(",".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(CONST())
            {
                if(CONST2())
                {
                    return true;
                }
            }
        }
        
        else if("}".equals(tokenlist.get(index).classPart))
        {
            return true;
        }
        return false;
    }
    
    private boolean OBJ_DEC1()
    {
        if("=".equals(tokenlist.get(index).classPart))
        {
            if(myClass.type!=null)
            {
                myClass.Assigned='Y';
                index++;
                if("new".equals(tokenlist.get(index).classPart))
                {
                    index++;
                    if("ID".equals(tokenlist.get(index).classPart))
                    {
                        if(tokenlist.get(index).valuePart.equals(myClass.type))// || tokenlist.get(index).valuePart.equals(myFunc.type))
                        {
                            index++;
                            if("(".equals(tokenlist.get(index).classPart))
                            {
                                index++;
                                if(OBJ_DEC2())
                                {
                                    return true;
                                }
                            }
                        }   
                        else if(semantic.Lookup(myClass.type, tokenlist.get(index).valuePart, classCounter, semantic.classNames))
                        {
                            index++;
                            if("(".equals(tokenlist.get(index).classPart))
                            {
                                index++;
                                if(OBJ_DEC2())
                                {
                                    return true;
                                }
                            }   
                        }
                        
                        else
                        {
                            System.out.println("\nSemantic Error : class "+myClass.type+" doesn't extends "+tokenlist.get(index).valuePart);
                            return false;
                        }
                    }
                }
            }
            
            else if(myFunc.type!=null)
            {
                myFunc.Assigned='Y';
                index++;
                if("new".equals(tokenlist.get(index).classPart))
                {
                    index++;
                    if("ID".equals(tokenlist.get(index).classPart))
                    {
                        if(tokenlist.get(index).valuePart.equals(myFunc.type))// || tokenlist.get(index).valuePart.equals(myFunc.type))
                        {
                            index++;
                            if("(".equals(tokenlist.get(index).classPart))
                            {
                                index++;
                                if(OBJ_DEC2())
                                {
                                    return true;
                                }
                            }
                        }   
                        else if(semantic.Lookup(myFunc.type, tokenlist.get(index).valuePart, classCounter, semantic.classNames))
                        {
                            index++;
                            if("(".equals(tokenlist.get(index).classPart))
                            {
                                index++;
                                if(OBJ_DEC2())
                                {
                                    return true;
                                }
                            }   
                        }
                        
                        else
                        {
                            System.out.println("\nSemantic Error : class "+myFunc.type+" doesn't extends "+tokenlist.get(index).valuePart);
                            return false;
                        }
                    }
                }
            }
                   
        }
    
        
        
        else if(";".equals(tokenlist.get(index).classPart))
        {
            if(myClass.type!=null)
            {
            myClass.Assigned='N';
            myClass.category="General";
            myClass.Static='N';
            semantic.classNames.get(classCounter).classInfo.add(new classTable(myClass.name,myClass.type,myClass.AM,
                          myClass.Assigned,myClass.category,myClass.Static));
            System.out.println("\n"+semantic.classNames.get(classCounter).classInfo.get(varfuncCounter));
            varfuncCounter++;
            myClass=new classTable();
            index++;
            return true;
            }
            
            else if(myFunc.type!=null)
            {
                myFunc.Assigned='N';
                myFunc.Scope=currentScope;
                semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData.add(new functionTable(
                    myFunc.Name, myFunc.type, myFunc.Scope, myFunc.Assigned));
                
                System.out.println("\n"+myFunc);
                variableCounter++;
                myFunc=new functionTable();
                index++;
                return true;
            }
        }
        
        return false;
    }
    
    private boolean OBJ_DEC2()
    {
        if(PARA())
        {
            if(PARA2())
            {
            if(";".equals(tokenlist.get(index).classPart))
            {
                if(myClass!=null)
                {
                myClass.category="General";
                myClass.Static='N';
                semantic.classNames.get(classCounter).classInfo.add(new classTable(myClass.name,myClass.type,myClass.AM,
                          myClass.Assigned,myClass.category,myClass.Static));
                System.out.println("\n"+semantic.classNames.get(classCounter).classInfo.get(varfuncCounter));
                varfuncCounter++;
                myClass=new classTable();
                index++;
                return true;
                }
                
                else if(myFunc.type!=null)
                {
                semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData.add(new functionTable(
                    myFunc.Name, myFunc.type, currentScope, myFunc.Assigned));
                System.out.println("\n"+semantic.classNames.get(classCounter).classInfo.get(varfuncCounter).functionData.get(variableCounter));
                variableCounter++;
                myFunc=new functionTable();
                index++;
                return true;
            }
            }
            }
                
        }
        
        else if(")".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(";".equals(tokenlist.get(index).classPart))
            {
                if(myClass.type!=null)
                {
                myClass.category="General";
                myClass.Static='N';
                semantic.classNames.get(classCounter).classInfo.add(new classTable(myClass.name,myClass.type,myClass.AM,
                          myClass.Assigned,myClass.category,myClass.Static));
                System.out.println("\n"+semantic.classNames.get(classCounter).classInfo.get(varfuncCounter));
                varfuncCounter++;
                myClass=new classTable();
                index++;
                return true;
                }
                
                else if(myFunc.type!=null)
                {
                semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData.add(new functionTable(
                    myFunc.Name, myFunc.type, currentScope, myFunc.Assigned));
                myFunc.Scope=currentScope;
                System.out.println("\n"+myFunc);
                variableCounter++;
                myFunc=new functionTable();
                index++;
                return true;
            }
            }
        }
        return false;
    }
    
    private boolean SST()
    {
        if("ID".equals(tokenlist.get(index).classPart))
        {
            if("ID".equals(tokenlist.get(index+1).classPart))
            {
                if(semantic.Lookup(tokenlist.get(index).valuePart, classCounter, semantic.classNames))
                {
                    myFunc.type=tokenlist.get(index).valuePart;
                    index++;
                    if(CONBODY1())
                    {
                        return true;
                    }
                }
                else
                {
                    System.out.println("\nSemantic Error : Class with name "+tokenlist.get(index).valuePart+" "
                        + "doesn't exist.");
                    return false;
                }
            }
            
            else if("=".equals(tokenlist.get(index+1).classPart) || "IncDec".equals(tokenlist.get(index+1).classPart) ||
                    "AOP".equals(tokenlist.get(index+1).classPart))
            {
                if(myClass.Lookup(tokenlist.get(index).valuePart, currentScope,
                        semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData))
                {
                    IC.variableName=tokenlist.get(index).valuePart;
                    index++;
                    if(CONBODY1())
                    {
                        return true;
                    }
                }
                
                else if(myClass.Lookup(semantic.classNames.get(classCounter).classInfo, varfuncCounter, tokenlist.get(index).valuePart))
                {
                    index++;
                    if(CONBODY1())
                    {
                        return true;
                    }
                }
                
                else
                {
                    System.out.println("\nSemantic Error : Undeclared Variable.");
                    return false;
                }
            }
            
            else if(".".equals(tokenlist.get(index+1).classPart) && "ID".equals(tokenlist.get(index+2).classPart))
            {
                if(myClass.Lookup(semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData,
                semantic.classNames.get(classCounter).classInfo, tokenlist.get(index).valuePart, tokenlist.get(index+2).valuePart,
                semantic.classNames))
                {
                    IC.variableName=tokenlist.get(index).valuePart;
                    index++;
                    if(CONBODY1())
                    {
                        return true;
                    }
                }
                
                else
                {
                    System.out.println("\nSemantic Error : Undeclared "
                            + "Variable "+tokenlist.get(index+2).valuePart+" in the class type object "+tokenlist.get(index).valuePart+" "
                                    + "or either the variable "+tokenlist.get(index).valuePart+" \nis not"
                                            + " of class type or either "+tokenlist.get(index).valuePart+" "
                                                    + "is undeclared.");
                    return false;
                }
            }
            
            else if("[".equals(tokenlist.get(index+1).classPart) && "]".equals(tokenlist.get(index+2).classPart))
            {
                if(semantic.Lookup(tokenlist.get(index).valuePart, classCounter, semantic.classNames))
                {
                    myFunc.type=tokenlist.get(index).valuePart;
                    index++;
                    if(CONBODY1())
                    {
                        return true;
                    }
                }
                
                else
                {
                    System.out.println("\nSemantic Error : Class with name "+tokenlist.get(index).valuePart+" "
                            + "doesn't exist.");
                    return false;
                }
            }
            
            else if("[".equals(tokenlist.get(index+1).classPart))
            {
                if(myClass.Lookup(semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData,
                        semantic.classNames.get(classCounter).classInfo, tokenlist.get(index).valuePart))
                {
                    IC.variableName=tokenlist.get(index).valuePart;
                    index++;
                    if(CONBODY1())
                    {
                        return true;
                    }
                }
                
                else
                {
                    System.out.println("\nSemantic Error : The variable "+tokenlist.get(index).valuePart+" "
                            + "is not of the array type or either not declared.");
                    return false;
                }
            }
            
        }   
        
        else if("DT".equals(tokenlist.get(index).classPart))
        {
            myFunc.type=tokenlist.get(index).valuePart;
            index++;
            if(CONBODY2())
            {
                return true;
            }
        }
        
        else if(IF_ELSE())
        {
            return true;
        }
        
        else if(DO_WHILE())
        {
            return true;
        }
        
        else if(SWITCH())
        {
            return true;
        }
        
        else if(WHILE_ST())
        {
            return true;
        }
        
        else if(FOR())
        {
            return true;
        }
        
        else if(FOR_EACH())
        {
            return true;
        }
        
        else if("this".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(".".equals(tokenlist.get(index).classPart))
            {
                index++;
                if("ID".equals(tokenlist.get(index).classPart))
                {
                    IC.variableName=""+IC.classname+"."+tokenlist.get(index).valuePart;
                    if("[".equals(tokenlist.get(index+1).classPart))
                    {
                        if(myClass.LookupAr(tokenlist.get(index).valuePart, semantic.classNames.get(classCounter).classInfo, 
                            semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData))
                        {
                            index++;
                            if(CONBODY3())
                            {
                                return true;  
                            }
                        }
                
                        else
                        {
                            System.out.println("\nSemantic Error : Variable "+tokenlist.get(index).valuePart+" "
                                + "is either not declared or of the array type.");
                            return false;
                        }
                    }
            
                    else if(".".equals(tokenlist.get(index+1).classPart))
                    {
                        if(myClass.LookupDot(tokenlist.get(index).valuePart, semantic.classNames, 
                            semantic.classNames.get(classCounter).classInfo,
                            semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData))
                        {
                            index++;
                            if(CONBODY3())
                            {
                                return true;
                            }
                        }
                
                        else
                        {
                            System.out.println("\nSemantic Error : Object of class type with name "+tokenlist.get(index).valuePart+" "
                            + "doesn't exist.");
                            return false;
                        }
                    }
                    
                    else if(myClass.Lookup(semantic.classNames.get(classCounter).classInfo, varfuncCounter, tokenlist.get(index).valuePart))
                    {
                        index++;
                        if(CONBODY3())
                        {
                            return true;
                        }
                    }
            
                    else
                    {
                         System.out.println("\nSemantic Error : Identifier with name "+tokenlist.get(index).valuePart+" "
                            + "doesn't exist.");
                         return false;
                    }
                }
            }
        }
        
        else if("super".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(".".equals(tokenlist.get(index).classPart))
            {
                index++;
                if("ID".equals(tokenlist.get(index).classPart))
                {
                    if("[".equals(tokenlist.get(index+1).classPart))
            {
                if(myClass.LookupAr(tokenlist.get(index).valuePart, semantic.classNames.get(classCounter).classInfo, 
                        semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData))
                {
                    index++;
                    if(CONBODY3())
                    {
                        return true;  
                    }
                }
                
                else
                {
                    System.out.println("\nSemantic Error : Variable "+tokenlist.get(index).valuePart+" "
                            + "is either not declared or of the array type.");
                    return false;
                }
            }
            
            else if(".".equals(tokenlist.get(index+1).classPart))
            {
                if(myClass.LookupDot(tokenlist.get(index).valuePart, semantic.classNames, 
                        semantic.classNames.get(classCounter).classInfo,
                        semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData))
                {
                    index++;
                    if(CONBODY3())
                    {
                        return true;
                    }
                }
                
                else
                {
                    System.out.println("\nSemantic Error : Object of class type with name "+tokenlist.get(index).valuePart+" "
                            + "doesn't exist.");
                    return false;
                }
            }
            
            else if(myClass.Lookup(semantic.classNames.get(classCounter).classInfo, varfuncCounter, tokenlist.get(index).valuePart))
            {
                index++;
                if(CONBODY3())
                {
                    return true;
                }
            }
            
            else
            {
                     System.out.println("\nSemantic Error : Identifier with name "+tokenlist.get(index).valuePart+" "
                    + "doesn't exist.");
                     return false;
            }
                }
            }
        }
        
        else if("break".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(";".equals(tokenlist.get(index).classPart))
            {
                index++;
                if(!"}".equals(tokenlist.get(index).classPart))
                {
                    System.out.println("\nSemantic Error : Unreachable Statement at "
                            + "Line Number "+tokenlist.get(index).lineNumber+".");
                    return false;
                }
                else
                {
                    return true;
                }
            }
        }
        
        else if("continue".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(";".equals(tokenlist.get(index).classPart))
            {
                index++;
                if(!"}".equals(tokenlist.get(index).classPart))
                {
                    System.out.println("\nSemantic Error : Unreachable Statement at "
                            + "Line Number "+tokenlist.get(index).lineNumber+".");
                    return false;
                }
                else
                {
                    return true;
                }
            }
        }
        
        else if(RETURN())
        {
            return true;
        }
        return false;
    }
    
    private boolean RETURN()
    {
        if("return".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(EXP())
            {
                if(myClass.Lookup(semantic.classNames.get(classCounter).classInfo, funcName)) //we get return type
                    //of the current function to later match it with the type returned.
                {
                    if(myClass.string.equals(checkType.T10))
                    {
                        if("}".equals(tokenlist.get(index).classPart))
                        {
                            return true;
                        }
                        else
                        {
                            System.out.println("\nSemantic Error : Unreachable Statement at Line Number "
                                    + ""+tokenlist.get(index).lineNumber);
                            return false;
                        }
                    }
                    else
                    {
                        System.out.println("\nSemantic Error : return type "+myClass.string+" "
                                + "doesn't matches with "+checkType.T10);
                        if(!"}".equals(tokenlist.get(index).classPart))
                        {
                            System.out.println("\nSemantic Error : Unreachable Statement at Line Number "
                                    + ""+tokenlist.get(index).lineNumber);
                            return false;
                        }
                    }
                }
                return false;
            }
        }
        return false;
    }
    
    private boolean WHILE_ST()
    {
        if("while".equals(tokenlist.get(index).classPart))
        {
            index++;
            if("(".equals(tokenlist.get(index).classPart))
            {
                index++;
                if(COND())
                {
                    if("bool".equals(checkType.T10))
                    {
                        if(")".equals(tokenlist.get(index).classPart))
                        {
                            index++;
                            currentScope=semantic.createScope();
                            if(BODY())
                            {
                                currentScope=semantic.destroyScope();
                                return true;
                            }
                        }
                    }
                    else
                    {
                        System.out.println("\nSemantic Error : Incompatible Types : "+checkType.T10+" "
                                + "cannot be converted to bool.");
                        return false;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean COND()
    {
        if(OE())
        {
            return true;
        }
        return false;
    }
    
    private boolean BODY()
    {
        if(";".equals(tokenlist.get(index).classPart))
        {
            index++;
            return true;
        }
        
        else if("{".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(BODY1())
            {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean BODY1()
    {   
        if(MST())
        {
            if("}".equals(tokenlist.get(index).classPart))
            {
                index++;
                return true;
            }
        }
        return false;
    }
    
    private boolean MST()
    {
        if(SST())
        {
            if(MST())
            {
                return true;
            }
        }
        
        else if("}".equals(tokenlist.get(index).classPart) || ("case".equals(tokenlist.get(index).classPart)) ||
                ("default".equals(tokenlist.get(index).classPart)))
        {
            return true;
        }
        
        return false;
    }
    
    private boolean FOR()
    {
        if("for".equals(tokenlist.get(index).classPart))
        {
            index++;
            currentScope=semantic.createScope();
            if("(".equals(tokenlist.get(index).classPart))
            {
                index++;
                if(F1())
                {
                    if(F2())
                    {
                        if(F3())
                        {
                            if(")".equals(tokenlist.get(index).classPart))
                            {
                                index++;
                                if(BODY())
                                {
                                    currentScope=semantic.destroyScope();
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean F1()
    {
        if(VINIT())
        {
            if(";".equals(tokenlist.get(index).classPart))
            {
                index++;
                return true;
            }
        }
        
        else if(DEC())
        {
            return true;
        }
        
        else if(";".equals(tokenlist.get(index).classPart))
        {
            index++;
            return true;
        }
        return false;
    }
    
    private boolean F2()
    {
        if(COND())
        {
            if(";".equals(tokenlist.get(index).classPart))
            {
                index++;
                return true;
            }
        }
        
        else if(";".equals(tokenlist.get(index).classPart))
        {
            index++;
            return true;
        }
        return false;
    }
    
    private boolean F3()
    {
        if(VINIT())
        {
            return true;
        }
        
        else if(")".equals(tokenlist.get(index).classPart))
        {
            return true;
        }
        
        return false;
    }
    
    private boolean IF_ELSE()
    {
        if("if".equals(tokenlist.get(index).classPart))
        {
            index++;
            if("(".equals(tokenlist.get(index).classPart))
            {
                index++;
                if(COND())
                {
                    if("bool".equals(checkType.T10))
                    {
                    if(")".equals(tokenlist.get(index).classPart))
                    {
                        index++;
                        currentScope=semantic.createScope();
                        if(BODY())
                        {
                            currentScope=semantic.destroyScope();
                            if(ELSE())
                            {
                                return true;
                            }
                        }
                    }
                    }
                    else
                    {
                        System.out.println("\nSemantic Error : Incompatible Types : "+checkType.T10+" "
                                + "cannot be converted to bool.");
                        return false;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean ELSE()
    {
        if("else".equals(tokenlist.get(index).classPart))
        {
            currentScope=semantic.createScope();
            index++;
            if(BODY())
            {
                currentScope=semantic.destroyScope();
                return true;
            }
        }
        
        return true;
    }
    
    private boolean SWITCH()
    {
        if("switch".equals(tokenlist.get(index).classPart))
        {
            index++;
            if("(".equals(tokenlist.get(index).classPart))
            { 
                index++;
                if(OE())
                {    
                    dummy=checkType.T10;
                    if(")".equals(tokenlist.get(index).classPart))
                    {
                        index++;
                        currentScope=semantic.createScope();
                        if("{".equals(tokenlist.get(index).classPart))
                        {
                            index++; 
                            if(SW_BODY())
                            {
                                if("}".equals(tokenlist.get(index).classPart))
                                {
                                    index++;
                                    currentScope=semantic.destroyScope();
                                    return true;
                                }
                            }
                        }
                     }
                }
            }
        }
        
        return false;

    }     
 
    private boolean SW_BODY()
    {
        if(CASE())
        {
            if(SW_BODY())
            {
                return true;
            }
        }
    
        else if(DEFAULT())
        {
            if(SW_BODY())
            {
                return true;
            }
        }
        
        else if("}".equals(tokenlist.get(index).classPart))
        {
            return true;
        }
        
        return false;
    }   
    
    private boolean CASE()
    {
        if("case".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(OE())
            {
                if(dummy.equals(checkType.T10))
                {
                    if(":".equals(tokenlist.get(index).classPart))
                    {
                        index++;
                        if(ADD_CASE())
                        {
                            if(MST())
                            {
                                return true;
                            }
                        }
                    }
                }
                else
                {
                    System.out.println("\nSemantic Error : Incompatible types : "+checkType.T10+" "
                            + "cannot be converted to "+dummy);
                    return false;
                }
            }
        }
        return false;
    }
    
    private boolean DEFAULT()
    {
        if("default".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(":".equals(tokenlist.get(index).classPart))
            {
                index++;
                if(MST())
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean ADD_CASE()
    {
        if("case".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(OE())
            {
                if(dummy.equals(checkType.T10))
                {
                    if(":".equals(tokenlist.get(index).classPart))
                    {
                        index++;
                        if(ADD_CASE())
                        {
                            return true;
                        }
                    }
                }
                else
                {
                    System.out.println("\nSemantic Error : Incompatible types : "+checkType.T10+" "
                            + "cannot be converted to "+dummy);
                    return false;
                }
            }
        }
        return true;
    }


    
    private boolean FOR_EACH()
    {
        if("foreach".equals(tokenlist.get(index).classPart))
        {
            index++;
            currentScope=semantic.createScope();
            if("(".equals(tokenlist.get(index).classPart))
            {
                index++;
                if(FE1())
                {
                    if(")".equals(tokenlist.get(index).classPart))
                    {
                        index++;
                        if("{".equals(tokenlist.get(index).classPart))
                        {
                            index++;
                            if(MST())
                            {
                                if("}".equals(tokenlist.get(index).classPart))
                                {
                                    currentScope=semantic.destroyScope();
                                    index++;
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean FE1()
    {
        if("DT".equals(tokenlist.get(index).classPart))
        {
            myFunc.type=tokenlist.get(index).valuePart;
            index++;
            if(OBJ())
            {
                return true;
            }
        }
        
        else if("ID".equals(tokenlist.get(index).classPart))
        {
            if(semantic.Lookup(tokenlist.get(index).valuePart, classCounter, semantic.classNames))
            {
                myFunc.type=tokenlist.get(index).valuePart;
                index++;
                if(OBJ())
                {
                    return true;
                }
            }
            
            else
            {
                System.out.println("\nSemantic Error : Class with name "+tokenlist.get(index).valuePart+" "
                        + "doesn't exist, Object can't be created.");
                return false;
            }
        }
        return false;
    }
    
    private boolean OBJ()
    {
        if("ID".equals(tokenlist.get(index).classPart))
        {
            if(!myClass.Lookup(tokenlist.get(index).valuePart, currentScope, semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData))
            {
                
                myFunc.Assigned='Y';
                myFunc.Scope=currentScope;
                myFunc.Name=tokenlist.get(index).valuePart;
                semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData.add(new functionTable(
                                myFunc.Name, myFunc.type, currentScope, myFunc.Assigned));
                
                System.out.println("\n"+myFunc);
                index++;
                if(":".equals(tokenlist.get(index).classPart))
                {
                    index++;
                    if("ID".equals(tokenlist.get(index).classPart))
                    {
                        if(myClass.Lookup(tokenlist.get(index).valuePart, currentScope, semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData,1))
                        {
                            index++;
                            return true;
                        }
                        
                        else if(myClass.LookupCT(semantic.classNames.get(classCounter).classInfo, tokenlist.get(index).valuePart))
                        {
                            index++;
                            return true;
                        }
                        else
                        {
                            System.out.println("\nSemantic Error : Variable "+tokenlist.get(index).valuePart+" "
                                    + "is either not declared or of the array type.");
                        }
                    }
                }
            }
            else
            {
                System.out.println("\nSemantic Error : Variable "+tokenlist.get(index).valuePart+" "
                        + "is already declared in the local scope.");
                return false;
            }
        }
        return false;
    }
    
    private boolean DEC()
    {
        if("DT".equals(tokenlist.get(index).classPart))
        {
            myFunc.type=tokenlist.get(index).valuePart;
            index++;
            if("ID".equals(tokenlist.get(index).classPart))
            {
                    if(!myClass.Lookup(tokenlist.get(index).valuePart, currentScope, semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData))
                    {
                        myFunc.Name=tokenlist.get(index).valuePart;
                        index++;
                        if(INIT())
                        {
                            if(LIST())
                            {
                                return true;
                            }
                        }   
                    }
            
                    else
                    {
                        System.out.println("\nSemantic Error : Variable \""+tokenlist.get(index).valuePart+""
                                + "\" is already declared in current Scope ");
                        return false;
                    }
            }
            
        }
        return false;
    }
    
    private boolean LIST()
    {
        if(";".equals(tokenlist.get(index).classPart))
        {
            if(myClass.type!=null)
            {
                myClass.category="General";
                myClass.Static='N';
                semantic.classNames.get(classCounter).classInfo.add(new classTable(myClass.name,myClass.type,
                myClass.AM,myClass.Assigned,myClass.category,myClass.Static));
                System.out.println("\n"+semantic.classNames.get(classCounter).classInfo.get(varfuncCounter));
                varfuncCounter++;
                myClass=new classTable();
                index++;
                return true;
            }
            
            else if(myFunc.type!=null)
            {
                semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData.add(new functionTable(
                                myFunc.Name, myFunc.type, currentScope, myFunc.Assigned));
                
                myFunc.Scope=currentScope;
                System.out.println("\n"+myFunc);
                variableCounter++;
                myFunc=new functionTable();
                index++;
                return true;
            }
        }
        
        else if(",".equals(tokenlist.get(index).classPart))
        {
            if(myClass.type!=null)
            {
                myClass.category="General";
            semantic.classNames.get(classCounter).classInfo.add(new classTable(myClass.name,myClass.type,
            myClass.AM,myClass.Assigned,myClass.category,myClass.Static));
            System.out.println("\n"+semantic.classNames.get(classCounter).classInfo.get(varfuncCounter));
            myClass.Assigned='N';
            varfuncCounter++;
            index++;
            if("ID".equals(tokenlist.get(index).classPart))
            {
                if(!myClass.Lookup(semantic.classNames.get(classCounter).classInfo, varfuncCounter, tokenlist.get(index).valuePart))
                {
                myClass.name=tokenlist.get(index).valuePart;
                index++;
                if(INIT())
                {
                    if(LIST())
                    {
                        return true;
                    }
                }
                }
                
                else if(!semantic.Lookup(tokenlist.get(index).valuePart,myClass.type,semantic.classNames.get(classCounter).classInfo))
                {
                    myClass.name=tokenlist.get(index).valuePart;
                    index++;
                    if(INIT())
                    {
                        if(LIST())
                        {
                            return true;
                        }
                    }
                }
                
                else
                {
                    System.out.println("\nSemantic Error : Variable \""+tokenlist.get(index).valuePart+""
                        + "\" is already declared in class "+semantic.classNames.get(classCounter).className);
                    return false;
                }
            }
            }
            
            else if(myFunc.type!=null)
            {
                semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData.add(new functionTable(
                                myFunc.Name, myFunc.type, currentScope, myFunc.Assigned));
                System.out.println("\n"+semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData.get(variableCounter));
                myFunc.Assigned='N';
                variableCounter++;
                index++;
                if("ID".equals(tokenlist.get(index).classPart))
                {
                    if(!myClass.Lookup(tokenlist.get(index).valuePart, currentScope, semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData))
                    {
                    myFunc.Name=tokenlist.get(index).valuePart;
                    index++;
                    if(INIT())
                    {
                        if(LIST())
                        {
                            return true;
                        }
                    }
                    }
                    
                    else
                    {
                        System.out.println("\nSemantic Error : Variable \""+tokenlist.get(index).valuePart+""
                                + "\" is already declared in current Scope ");
                        return false;
                    } 
                }
            }
        }
        return false;
    }
    
    
    private boolean INIT()
    {
        if("=".equals(tokenlist.get(index).classPart))
        {
            if(myClass.type!=null)
            {
                myClass.Assigned='Y';
                index++;
                if(INIT1())
                {
                    return true;
                }
            }
            else if(myFunc.type!=null)
            {
                myFunc.Assigned='Y';
                index++;
                if(INIT1())
                {
                    IC.intermediateCode=IC.intermediateCode+"\n"+myFunc.Name+" = "+IC.value;
                    return true;
                }
            }
        }
        
        else if(";".equals(tokenlist.get(index).classPart) || (",".equals(tokenlist.get(index).classPart)))
        {
            return true;   
        }
        return false;
    }
    
    
    private boolean INIT1()
    {
        if(OE())
        {
            if(myClass.type!=null)
            {
            if(myClass.type.equals(checkType.T10))
            {
            if(INIT())
            {
                return true;
            }
            }
            else
            {
                System.out.println("\nSemantic Error : "+checkType.T10+" cannot be converted to "+myClass.type);
                return false;
            }
            }
            
            else if(myFunc.type!=null)
            {
            if(myFunc.type.equals(checkType.T10))
            {
            if(INIT())
            {
                return true;
            }
            }
            else
            {
                System.out.println("\nSemantic Error : "+checkType.T10+" cannot be converted to "+myFunc.type);
                return false;
            }
            }
            
            else if(INIT())
            {
                return true;
            }
        }
        return false;
    }
    
    
   private boolean INIT10()
    {   
        if("=".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(INIT3())
            {
                if(checkType.T10!=null)
                {
                    if(checkType.T10.equals(myClass.dType))
                    {
                        IC.intermediateCode=IC.intermediateCode+"\n"+IC.variableName+" = "+IC.value;
                        return true;
                    }
                    else
                    {
                        System.out.println("\nSemantic Error : "+checkType.T10+" cannot be converted to "
                                + ""+myClass.dType);
                        return false;
                    }
                }
            }
        }
        
        else if(";".equals(tokenlist.get(index).classPart) ||")".equals(tokenlist.get(index).classPart))
        {
            return true;
        }
        
        return false;
    }
    
    
    private boolean VINIT()
    {
        if("ID".equals(tokenlist.get(index).classPart))
        {
            
            if("=".equals(tokenlist.get(index+1).classPart) || "IncDec".equals(tokenlist.get(index+1).classPart) ||
                    "AOP".equals(tokenlist.get(index+1).classPart))
            {
                if(myClass.Lookup(tokenlist.get(index).valuePart, currentScope,
                        semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData))
                {
                    index++;
                    if(VINIT1())
                    {
                        return true;
                    }
                }
                
                else if(myClass.Lookup(semantic.classNames.get(classCounter).classInfo, varfuncCounter, tokenlist.get(index).valuePart))
                {
                    index++;
                    if(VINIT1())
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                
                else
                {
                    System.out.println("\nSemantic Error : Undeclared Variable.");
                    return false;
                }
            }
            
            else if(".".equals(tokenlist.get(index+1).classPart) && "ID".equals(tokenlist.get(index+2).classPart))
            {
                if(myClass.Lookup(semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData,
                semantic.classNames.get(classCounter).classInfo, tokenlist.get(index).valuePart, tokenlist.get(index+2).valuePart,
                semantic.classNames))
                {
                    index++;
                    if(VINIT1())
                    {
                        return true;
                    }
                }
                
                else
                {
                    System.out.println("\nSemantic Error : Undeclared "
                            + "Variable "+tokenlist.get(index+2).valuePart+" in the class type object "+tokenlist.get(index).valuePart+" "
                                    + "or either the variable "+tokenlist.get(index).valuePart+" \nis not"
                                            + " of class type or either "+tokenlist.get(index).valuePart+" "
                                                    + "is undeclared.");
                    return false;
                }
            }
            
            else if("[".equals(tokenlist.get(index+1).classPart))
            {
                if(myClass.Lookup(semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData,
                        semantic.classNames.get(classCounter).classInfo, tokenlist.get(index).valuePart))
                {
                    index++;
                    if(VINIT1())
                    {
                        return true;
                    }
                }
                
                else
                {
                    System.out.println("\nSemantic Error : The variable "+tokenlist.get(index).valuePart+" "
                            + "is not of the array type or either not declared.");
                    return false;
                }
            }
        }
        
        return false;
    }
    
    private boolean VINIT1()
    {
        if(INIT0())
        {
            return true;
        }
        
        else if("[".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(OE())
            {
                if("Integer".equals(checkType.T10))
                {
                    IC.value=IC.value+" * size_of_each_array_member";
                IC.intermediateCode=IC.intermediateCode+"\nt"+(IC.temp+1)+" = "+IC.value;
                IC.value="t"+(IC.temp+1);
                IC.variableName=IC.variableName+"["+IC.value+"]";
                    if("]".equals(tokenlist.get(index).classPart))
                    {
                        index++;
                        if(INIT5())
                        {
                            return true;
                        }
                    }
                }
                else
                {
                    System.out.println("\nSemantic Error : Incompatible Types. Array type can only have Integer "
                        + "value inside [].");
                    return false;
                }
            }
        }
        
        else if("=".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(INIT3())
            {
                if(checkType.T10!=null)
                {
                    if(checkType.T10.equals(myClass.dType))
                    {
                        IC.intermediateCode=IC.intermediateCode+"\n"+IC.variableName+" = "+IC.value;
                        return true;
                    }
                    else
                    {
                        System.out.println("\nSemantic Error : "+checkType.T10+" cannot be converted to "
                                + ""+myClass.dType);
                        return false;
                    }
                }
                
            }
        }
        
        return false;
        
    }
    
    
    private boolean INIT0()
    {
        if(INIT2())
        {
            return true;
        }
        
        else if(".".equals(tokenlist.get(index).classPart))
        {
            index++;
            if("ID".equals(tokenlist.get(index).classPart))
            { 
                IC.variableName=IC.variableName+"."+tokenlist.get(index).valuePart;
                index++;
                if(INIT6())
                {
                    return true;
                }
            }
        }
        
        else if("IncDec".equals(tokenlist.get(index).classPart))
        {
            IC.intermediateCode=IC.intermediateCode+"\n"+IC.variableName+" = "+IC.variableName+" + 1";
            index++;
            return true;
        }
        
        return false;
    }
    
    private boolean INIT2()
    {   
        if("AOP".equals(tokenlist.get(index).classPart))
        {
            String dum=tokenlist.get(index).valuePart.replace("=", "");
            index++;
            if(INIT3())
            {
                if(checkType.T10.equals(myClass.dType))
                {
                    IC.intermediateCode=IC.intermediateCode+"\n"+IC.variableName+" = "+IC.variableName+" "+dum+" "+IC.value;
                    return true;
                }
                else
                {
                    System.out.println("\nSemantic Error : "+checkType.T10+" cannot be converted to "
                            + ""+myClass.dType);
                    return false;
                }
            }
        }
        return false;
    }
    
    private boolean OBJ_DEC3()
    {
        if("new".equals(tokenlist.get(index).classPart))
        {
            index++;
            if("ID".equals(tokenlist.get(index).classPart))
            {
                if(tokenlist.get(index).valuePart.equals(myClass.type))
                    {
                    index++;
                    if("(".equals(tokenlist.get(index).classPart))
                    {
                        index++;
                        if(OBJ_DEC4())
                        {
                            return true;
                        }
                    }
                    }
                    else if(semantic.Lookup(myClass.type, tokenlist.get(index).valuePart, classCounter, semantic.classNames))
                    {
                        index++;
                        if("(".equals(tokenlist.get(index).classPart))
                        {
                            index++;
                            if(OBJ_DEC4())
                            {
                                return true;
                            }
                        }
                    }
                    
                    else
                    {
                        System.out.println("\nSemantic Error : class "+myClass.type+" doesn't extends "+tokenlist.get(index).valuePart);
                        return false;
                    }
            }
        }
        return false;
    }
    
    private boolean OBJ_DEC4()
    {
        if(PARA())
        {
            if(PARA2())
            {
                return true;
            }
        }
        
        else if(")".equals(tokenlist.get(index).classPart))
        {
            index++;
            return true;
        }
        return false;
    }
    
    
    private boolean INIT3()
    {
        if(OE())
        {
            if(checkType.T10.equals(myClass.dType))
            {
                if(INIT10())
                {
                    return true;
                }
            }
            else
            {
                System.out.println("\nSemantic Error : "+checkType.T10+" cannot be converted to "
                        + ""+myClass.dType);
                return false;
            }
        }
        
        else if(OBJ_DEC3())
        {
            return true;
        }
        
        else if("this".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(".".equals(tokenlist.get(index).classPart))
            {
                index++;
                if("ID".equals(tokenlist.get(index).classPart))
                {
                    if("[".equals(tokenlist.get(index+1).classPart))
                    {
                        if(myClass.LookupAr(tokenlist.get(index).valuePart, semantic.classNames.get(classCounter).classInfo, 
                            semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData))
                        {
                            index++;
                            if(FR1())
                            {
                                return true;
                            }
                        }
                
                         else
                        {
                            System.out.println("\nSemantic Error : Variable "+tokenlist.get(index).valuePart+" "
                                + "is either not declared or of the array type.");
                            return false;
                        }
                    }
            
                    else if(".".equals(tokenlist.get(index+1).classPart))
                    {
                        if(myClass.LookupDot(tokenlist.get(index).valuePart, semantic.classNames, 
                            semantic.classNames.get(classCounter).classInfo,
                            semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData))
                        {
                            index++;
                            if(FR1())
                            {
                                return true;
                            }
                        }
                
                        else
                        {
                            System.out.println("\nSemantic Error : Object of class type with name "+tokenlist.get(index).valuePart+" "
                                + "doesn't exist.");
                            return false;
                        }
                    }
            
                    else if(myClass.Lookup(tokenlist.get(index).valuePart, currentScope, 
                            semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData))
                    {
                        index++;
                        if(FR1())
                        {
                            return true;
                        }
                    }
            
                    else if(myClass.Lookup(semantic.classNames.get(classCounter).classInfo, varfuncCounter, tokenlist.get(index).valuePart))
                    {
                        index++;
                        if(FR1())
                        {
                            return true;
                        }
                    }
            
                    else
                    {
                        System.out.println("\nSemantic Error : Identifier with name "+tokenlist.get(index).valuePart+" "
                            + "doesn't exist.");
                    }
                }
            }
        }
        
        
        else if("super".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(".".equals(tokenlist.get(index).classPart))
            {
                index++;
                if("ID".equals(tokenlist.get(index).classPart))
                {
                        if("[".equals(tokenlist.get(index+1).classPart))
            {
                if(myClass.LookupAr(tokenlist.get(index).valuePart, semantic.classNames.get(classCounter).classInfo, 
                        semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData))
                {
                    index++;
                    if(FR1())
                    {
                        return true;
                    }
                }
                
                else
                {
                    System.out.println("\nSemantic Error : Variable "+tokenlist.get(index).valuePart+" "
                            + "is either not declared or of the array type.");
                    return false;
                }
            }
            
            else if(".".equals(tokenlist.get(index+1).classPart))
            {
                if(myClass.LookupDot(tokenlist.get(index).valuePart, semantic.classNames, 
                        semantic.classNames.get(classCounter).classInfo,
                        semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData))
                {
                    index++;
                    if(FR1())
                    {
                        return true;
                    }
                }
                
                else
                {
                    System.out.println("\nSemantic Error : Object of class type with name "+tokenlist.get(index).valuePart+" "
                            + "doesn't exist.");
                    return false;
                }
            }
            
            else if(myClass.Lookup(tokenlist.get(index).valuePart, currentScope, 
                    semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData))
            {
                index++;
                if(FR1())
                {
                    return true;
                }
            }
            
            else if(myClass.Lookup(semantic.classNames.get(classCounter).classInfo, varfuncCounter, tokenlist.get(index).valuePart))
            {
                index++;
                if(FR1())
                {
                    return true;
                }
            }
            
            else
            {
                System.out.println("\nSemantic Error : Identifier with name "+tokenlist.get(index).valuePart+" "
                        + "doesn't exist.");
                return false;
            }
                }
            }
        }
        
        return false;
    }
    
    private boolean INIT6()
    {
        if(INIT5())
        {
            return true;
        }
        else if(INIT4())
        {
            return true;
        }
        
        return false;
    }
    
    private boolean INIT4()
    {
            if("[".equals(tokenlist.get(index).classPart))
            {
            index++;
            if(OE())
            {
                if("Integer".equals(checkType.T10))
                {
                if("]".equals(tokenlist.get(index).classPart))
                {
                    index++;
                    if(INIT5())
                    {
                        return true;
                    }
                }
                }
                else
                {
                    System.out.println("\nSemantic Error : Incompatible Types. Array type can only have Integer "
                        + "value inside [].");
                    return false;
                }
            }
        }
        
        return false;
    }
    
    private boolean INIT5()
    {
        if(INIT2())
        {
            return true;
        }
        
        else if(".".equals(tokenlist.get(index).classPart))
        {
            index++;
            if("ID".equals(tokenlist.get(index).classPart))
            {
                IC.variableName=IC.variableName+"."+tokenlist.get(index).valuePart;
                index++;
                if(INIT5())
                {
                    return true;
                }
            }
        }
        
        else if("=".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(INIT3())
            {
                if(checkType.T10.equals(myClass.dType))
                {
                    IC.intermediateCode=IC.intermediateCode+"\n"+IC.variableName+" = "+IC.value;
                    return true;
                }
                else
                {
                    System.out.println("\nSemantic Error : "+checkType.T10+" cannot be converted "
                            + ""+myClass.dType);
                    return false;
                }
            }
        }
        
        else if("IncDec".equals(tokenlist.get(index).classPart))
        {
            IC.intermediateCode=IC.intermediateCode+"\n"+IC.variableName+" = "+IC.variableName+" + 1";
            index++;
            return true;
        }
        
        return false;
    }
    
    private boolean CONST()
    {
        if("PM".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(CONST3())
            {
                return true;
            }
        }
        else if(CONST1())
        {
            return true;
        }
        return false;
    }
    
    private boolean CONST1()
    {
        if("Integer".equals(tokenlist.get(index).classPart) || ("char".equals(tokenlist.get(index).classPart))||
                ("String".equals(tokenlist.get(index).classPart)) || ("decimal".equals(tokenlist.get(index).classPart)) ||
                ("bool".equals(tokenlist.get(index).classPart)) || ("bool".equals(tokenlist.get(index).classPart)))
        {
            myClass.dummy=tokenlist.get(index).classPart;
            IC.value=tokenlist.get(index).valuePart;
            index++;
            return true;
        }
        return false;
    }
    
    private boolean CONST3()
    {
        if("Integer".equals(tokenlist.get(index).classPart) || 
                ("decimal".equals(tokenlist.get(index).classPart)))
                {
                    myClass.dummy=tokenlist.get(index).classPart;
                    IC.value=tokenlist.get(index).valuePart;
                    index++;
                    return true;
                }
        return false;
    }
    
    private boolean DO_WHILE()
    {
        if("do".equals(tokenlist.get(index).classPart))
        {
            index++;
            currentScope=semantic.createScope();
            if(BODY())
            {
                currentScope=semantic.destroyScope();
                if("while".equals(tokenlist.get(index).classPart))
                {
                    index++;
                    if("(".equals(tokenlist.get(index).classPart))
                    {
                        index++;
                        if(COND())
                        {
                            if(")".equals(tokenlist.get(index).classPart))
                            {
                                index++;
                                if(";".equals(tokenlist.get(index).classPart))
                                {
                                    index++;
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean EXP()
    {
        if(OE())
        {
            if(";".equals(tokenlist.get(index).classPart))
            {
                index++;
                return true;
            }
        }
        return false;
    }
    
    private boolean OE()
    {
        //IC.temp=0;
        if(AE())
        {
            checkType.T10=checkType.T9;
            if(OE1())
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean OE1()
    {
        if("OR".equals(tokenlist.get(index).classPart))
        {
            IC.temp++;
            checkType.Op10=tokenlist.get(index).valuePart;
            IC.typeTemp[9]="\nt"+IC.temp+" = "+IC.value+checkType.Op10;
            IC.tempCount[9]=IC.temp;
            if(AE())
            {
                IC.typeTemp[9]=IC.typeTemp[9]+IC.value;
                IC.intermediateCode=IC.intermediateCode+IC.typeTemp[9];
                IC.value="t"+IC.tempCount[9];
                checkType.T10=semantic.Compatibility(checkType.T10, checkType.T9, checkType.Op10);
                if("null".equals(checkType.T10))
                {
                    System.out.println("\nSemantic Error : Type Mismatch.");
                    return false;
                }
                if(OE1())
                {
                    return true;
                }
            }
        }
        
        else if(";".equals(tokenlist.get(index).classPart) || (")".equals(tokenlist.get(index).classPart)) ||
                ("]".equals(tokenlist.get(index).classPart)) || (":".equals(tokenlist.get(index).classPart)) ||
                ("}".equals(tokenlist.get(index).classPart)))
        {
            checkType.T9=checkType.T10;
            return true;
        }
        return true;
    }
    
    private boolean AE()
    {
        if(BOE())
        {
            checkType.T9=checkType.T8;
            if(AE1())
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean AE1()
    {
        if("AND".equals(tokenlist.get(index).classPart))
        {
            IC.temp++;
            checkType.Op9=tokenlist.get(index).valuePart;
            IC.typeTemp[8]="\nt"+IC.temp+" = "+IC.value+checkType.Op9;
            IC.tempCount[8]=IC.temp;
            index++;
            if(BOE())
            {
                IC.typeTemp[8]=IC.typeTemp[8]+IC.value;
                IC.intermediateCode=IC.intermediateCode+IC.typeTemp[8];
                IC.value="t"+IC.tempCount[8];
                checkType.T9=semantic.Compatibility(checkType.T9, checkType.T8, checkType.Op9);
                if("null".equals(checkType.T9))
                {
                    System.out.println("\nSemantic Error : Type Mismatch.");
                    return false;
                }
                if(AE1())
                {
                    return true;
                }
            }
        }
        
        else if("OR".equals(tokenlist.get(index).classPart))
        {
            checkType.T8=checkType.T9;
            return true;
        }
        return true;
    }
    
    private boolean BOE()
    {
        if(BXE())
        {
            checkType.T8=checkType.T7;
            if(BOE1())
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean BOE1()
    {
        if("|".equals(tokenlist.get(index).classPart))
        {
            IC.temp++;
            checkType.Op8=tokenlist.get(index).valuePart;
            IC.typeTemp[7]="\nt"+IC.temp+" = "+IC.value+checkType.Op8;
            IC.tempCount[7]=IC.temp;
            index++;
            if(BXE())
            {
                IC.typeTemp[7]=IC.typeTemp[7]+IC.value;
                IC.intermediateCode=IC.intermediateCode+IC.typeTemp[7];
                IC.value="t"+IC.tempCount[7];
                checkType.T8=semantic.Compatibility(checkType.T8, checkType.T7, checkType.Op8);
                if("null".equals(checkType.T8))
                {
                    System.out.println("\nSemantic Error : Type Mismatch.");
                    return false;
                }
                if(BOE1())
                {
                    return true;
                }
            }
        }
        else if("AND".equals(tokenlist.get(index).classPart))
        {
            checkType.T7=checkType.T8;
            return true;
        }
        return true;
    }
    
    private boolean BXE()
    {
        if(BAE())
        {
            checkType.T7=checkType.T6;
            if(BXE1())
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean BXE1()
    {
        if("^".equals(tokenlist.get(index).classPart))
        {
            IC.temp++;
            checkType.Op7=tokenlist.get(index).valuePart;
            IC.typeTemp[6]="\nt"+IC.temp+" = "+IC.value+checkType.Op7;
            IC.tempCount[6]=IC.temp;
            index++;
            if(BAE())
            {
                IC.typeTemp[6]=IC.typeTemp[6]+IC.value;
                IC.intermediateCode=IC.intermediateCode+IC.typeTemp[6];
                IC.value="t"+IC.tempCount[6];
                checkType.T7=semantic.Compatibility(checkType.T7, checkType.T6, checkType.Op7);
                if("null".equals(checkType.T7))
                {
                    System.out.println("\nSemantic Error : Type Mismatch.");
                    return false;
                }
                if(BXE1())
                {
                    return true;
                }
            }
        }
        else if("|".equals(tokenlist.get(index).classPart))
        {
            checkType.T6=checkType.T7;
            return true;
        }
        return true;
    }
    
    private boolean BAE()
    {
        if(REE())
        {
            checkType.T6=checkType.T5;
            if(BAE1())
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean BAE1()
    {
        if("&".equals(tokenlist.get(index).classPart))
        {
            IC.temp++;
            checkType.Op6=tokenlist.get(index).valuePart;
            IC.typeTemp[5]="\nt"+IC.temp+" = "+IC.value+checkType.Op6;
            IC.tempCount[5]=IC.temp;
            index++;
            if(REE())
            {
                IC.typeTemp[5]=IC.typeTemp[5]+IC.value;
                IC.intermediateCode=IC.intermediateCode+IC.typeTemp[5];
                IC.value="t"+IC.tempCount[5];
                checkType.T6=semantic.Compatibility(checkType.T6, checkType.T5, checkType.Op6);
                if("null".equals(checkType.T6))
                {
                    System.out.println("\nSemantic Error : Type Mismatch.");
                    return false;
                }
                if(BAE1())
                {
                    return true;
                }
            }
        }
        else if("^".equals(tokenlist.get(index).classPart))
        {
            checkType.T5=checkType.T6;
            return true;
        }
        return true;
    }
    
    private boolean REE()
    {
        if(RE())
        {
            checkType.T5=checkType.T4;
            if(REE1())
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean REE1()
    {
        if("==".equals(tokenlist.get(index).classPart))
        {
            IC.temp++;
            checkType.Op5=tokenlist.get(index).valuePart;
            IC.typeTemp[4]="\nt"+IC.temp+" = "+IC.value+checkType.Op5;
            IC.tempCount[4]=IC.temp;
            index++;
            if(RE())
            {
                IC.typeTemp[4]=IC.typeTemp[4]+IC.value;
                IC.intermediateCode=IC.intermediateCode+IC.typeTemp[4];
                IC.value="t"+IC.tempCount[4];
                checkType.T5=semantic.Compatibility(checkType.T5, checkType.T4, checkType.Op5);
                if("null".equals(checkType.T5))
                {
                    System.out.println("\nSemantic Error : Type Mismatch.");
                    return false;
                }
                if(REE1())
                {
                    return true;
                }
            }
        }
        
        else if("!=".equals(tokenlist.get(index).classPart))
        {
            IC.temp++;
            checkType.Op5=tokenlist.get(index).valuePart;
            IC.typeTemp[4]="\nt"+IC.temp+" = "+IC.value+checkType.Op5;
            IC.tempCount[4]=IC.temp;
            index++;
            if(RE())
            {
                IC.typeTemp[4]=IC.typeTemp[4]+IC.value;
                IC.intermediateCode=IC.intermediateCode+IC.typeTemp[4];
                IC.value="t"+IC.tempCount[4];
                checkType.T5=semantic.Compatibility(checkType.T5, checkType.T4, checkType.Op5);
                if("null".equals(checkType.T5))
                {
                    System.out.println("\nSemantic Error : Type Mismatch.");
                    return false;
                }
                if(REE1())
                {
                    return true;
                }
            }
        }
        else if("&".equals(tokenlist.get(index).classPart))
        {
            checkType.T4=checkType.T5;
            return true;
        }
        return true;
    }
    
    private boolean RE()
    {
        if(SHFT_OP())
        {
            checkType.T4=checkType.T3;
            if(RE1())
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean RE1()
    {
        if("RO".equals(tokenlist.get(index).classPart))
        {
            IC.temp++;
            checkType.Op4=tokenlist.get(index).valuePart;
            IC.typeTemp[3]="\nt"+IC.temp+" = "+IC.value+checkType.Op4;
            IC.tempCount[3]=IC.temp;
            index++;
            if(SHFT_OP())
            {
                IC.typeTemp[3]=IC.typeTemp[3]+IC.value;
                IC.intermediateCode=IC.intermediateCode+IC.typeTemp[3];
                IC.value="t"+IC.tempCount[3];
                checkType.T4=semantic.Compatibility(checkType.T4, checkType.T3, checkType.Op4);
                if("null".equals(checkType.T4))
                {
                    System.out.println("\nSemantic Error : Type Mismatch.");
                    return false;
                }
                if(RE1())
                {
                    return true;
                }
            }
        }
        else if("==".equals(tokenlist.get(index).classPart) || ("!=".equals(tokenlist.get(index).classPart)))
        {
            checkType.T3=checkType.T4;
            return true;
        }
        return true;
    }
    
    private boolean SHFT_OP()
    {
        if(E())
        {
            checkType.T3=checkType.T2;
            if(SHFT_OP1())
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean SHFT_OP1()
    {
        if("ShOP".equals(tokenlist.get(index).classPart))
        {
            IC.temp++;
            checkType.Op3=tokenlist.get(index).valuePart;
            IC.typeTemp[2]="\nt"+IC.temp+" = "+IC.value+checkType.Op3;
            IC.tempCount[2]=IC.temp;
            index++;
            if(E())
            {
                IC.typeTemp[2]=IC.typeTemp[2]+IC.value;
                IC.intermediateCode=IC.intermediateCode+IC.typeTemp[2];
                IC.value="t"+IC.tempCount[2];
                checkType.T3=semantic.Compatibility(checkType.T3, checkType.T2, checkType.Op3);
                if("null".equals(checkType.T3))
                {
                    System.out.println("\nSemantic Error : Type Mismatch.");
                    return false;
                }
                if(SHFT_OP1())
                {
                    return true;
                }
            }
        }
        else if("RO".equals(tokenlist.get(index).classPart))
        {
            checkType.T2=checkType.T3;
            return true;
        }
        return true;
    }
    
    private boolean E()
    {
        if(T())
        {
            checkType.T2=checkType.T1;
            //IC.temp++;
            //IC.intermediateCode=IC.intermediateCode+"\nt"+IC.temp+" = "+IC.value;
            if(E1())
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean E1()
    {
        if("PM".equals(tokenlist.get(index).classPart))
        {
            IC.temp++;
            checkType.Op2=tokenlist.get(index).valuePart;
            //IC.intermediateCode=IC.intermediateCode+"\nt"+IC.temp+" = "+IC.value+checkType.Op2;
            IC.typeTemp[1]="\nt"+IC.temp+" = "+IC.value+checkType.Op2;
            IC.tempCount[1]=IC.temp;
            index++;
            if(T())
            {
                //IC.intermediateCode=IC.intermediateCode+IC.value;
                IC.typeTemp[1]=IC.typeTemp[1]+IC.value;
                IC.intermediateCode=IC.intermediateCode+IC.typeTemp[1];
                IC.value="t"+IC.tempCount[1];
                //System.out.println("trueIC"+IC.intermediateCode+" "+tokenlist.get(index).lineNumber);
                checkType.T2=semantic.Compatibility(checkType.T2, checkType.T1, checkType.Op2);
                if("null".equals(checkType.T2))
                {
                    System.out.println("\nSemantic Error : Type Mismatch.");
                    return false;
                }
                if(E1())
                {
                    return true;
                }
            }
        }
        
        else if("ShOP".equals(tokenlist.get(index).classPart))
        {
            checkType.T1=checkType.T2;
            return true;
        }
        return true;
    }
    
    private boolean T()
    {
        if(D())
        {
            checkType.T1=checkType.T;
            if(T1())
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean T1()
    {
        if("MDM".equals(tokenlist.get(index).classPart))
        {
            IC.temp++;
            checkType.Op1=tokenlist.get(index).valuePart;
            IC.typeTemp[0]="\nt"+IC.temp+" = "+IC.value+checkType.Op1;
            IC.tempCount[0]=IC.temp;
            index++;
            if(D())
            {
                IC.typeTemp[0]=IC.typeTemp[0]+IC.value;
                IC.intermediateCode=IC.intermediateCode+IC.typeTemp[0];
                IC.value="t"+IC.tempCount[0];
                checkType.T1=semantic.Compatibility(checkType.T1, checkType.T, checkType.Op1);
                if("null".equals(checkType.T1))
                {
                    System.out.println("\nSemantic Error : Type Mismatch.");
                    return false;
                }
                if(T1())
                {
                    return true;
                }
            }
        }
        else if("PM".equals(tokenlist.get(index).classPart))
        {
            checkType.T=checkType.T1;
            return true;
        }
        return true;
    }
    
    private boolean D()
    {
        if(FRN())
        {
            return true;
        }
        return false;
    }
    
    private boolean FRN()
    {
        if("!".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(FR())
            {
                return true;
            }
        }
        else if(FR())
        {
            return true;
        }
        return false;
    }
    
    private boolean FR()
    {
        if("ID".equals(tokenlist.get(index).classPart))
        {
            if("[".equals(tokenlist.get(index+1).classPart))
            {
                checkType.T=checkType.LookupAr(tokenlist.get(index).valuePart, semantic.classNames.get(classCounter).classInfo, 
                        semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData);
                IC.value=tokenlist.get(index).valuePart;
                IC.variableName=tokenlist.get(index).valuePart;
                if(!"null".equals(checkType.T))
                {
                    index++;
                    if(FR1())
                    {
                        return true;
                    }
                }
                
                else
                {
                    System.out.println("\nSemantic Error : Variable "+tokenlist.get(index).valuePart+" "
                            + "is either not declared or of the array type.");
                    return false;
                }
            }
            
            else if(".".equals(tokenlist.get(index+1).classPart))
            {
                checkType.T=checkType.LookupDot(tokenlist.get(index).valuePart, semantic.classNames, 
                        semantic.classNames.get(classCounter).classInfo,
                        semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData);
                IC.value=tokenlist.get(index).valuePart;
                IC.variableName=tokenlist.get(index).valuePart;
                if(!"null".equals(checkType.T))
                {
                    index++;
                    if(FR1())
                    {
                        return true;
                    }
                }
                
                else
                {
                    System.out.println("\nSemantic Error : Object of class type with name "+tokenlist.get(index).valuePart+" "
                            + "doesn't exist.");
                    return false;
                }
            }
            
            checkType.T=checkType.Lookup(tokenlist.get(index).valuePart, currentScope, 
                    semantic.classNames.get(classCounter).classInfo.get(varfuncCounter-1).functionData);
            IC.value=tokenlist.get(index).valuePart;
            IC.variableName=tokenlist.get(index).valuePart;
            if(!"null".equals(checkType.T))
            {
                index++;
                if(FR1())
                {
                    return true;
                }
            }
            
            checkType.T=checkType.Lookup(semantic.classNames.get(classCounter).classInfo, varfuncCounter, tokenlist.get(index).valuePart);
            IC.value=tokenlist.get(index).valuePart;
            IC.variableName=tokenlist.get(index).valuePart;
            if(!"null".equals(checkType.T))
            {
                index++;
                if(FR1())
                {
                    return true;
                }
            }
            
            else
            {
                System.out.println("\nSemantic Error : Identifier with name "+tokenlist.get(index).valuePart+" "
                        + "doesn't exist.");
                return false;
            }
            
        }
        
        else if(CONST())
        {
            checkType.T=myClass.dummy;
            return true;
        }
        
        else if("(".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(OE())
            {
                checkType.T=checkType.T10;
                if(")".equals(tokenlist.get(index).classPart))
                {
                    index++;
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean FR1()
    {
        if("[".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(OE())
            {
                if("Integer".equals(checkType.T10))
                {
                    IC.temp++;
                    IC.value=IC.value+" * size_of_each_array_member";
                    IC.intermediateCode=IC.intermediateCode+"\nt"+(IC.temp)+" = "+IC.value;
                    IC.value=IC.variableName+"[t"+IC.temp+"]";
                if("]".equals(tokenlist.get(index).classPart))
                {
                    index++;
                    if(FR2())
                    {
                        return true;
                    }
                }
                }
                else
                {
                    System.out.println("\nSemantic Error : Incompatible Types. Array type can only have Integer "
                        + "value inside [].");
                    return false;
                }
            }
        }
        
        else if("(".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(PARA0())
            {
                if(PARA3())
                {
                    return true;
                }
            }
        }
        
        else if(".".equals(tokenlist.get(index).classPart))
        {
            index++;
            if("ID".equals(tokenlist.get(index).classPart))
            {
                index++;
                if(FR1())
                {
                    return true;
                }
            }
            return false;
        }
        
        else if("IncDec".equals(tokenlist.get(index).classPart))
        {
            index++;
            return true;
        }
        
        else if("MDM".equals(tokenlist.get(index).classPart))
        {
            return true;
        }
        return true;
    }
    
    private boolean FR2()
    {
        if(".".equals(tokenlist.get(index).classPart))
        {
            index++;
            if("ID".equals(tokenlist.get(index).classPart))
            {
                index++;
                if(FR1())
                {
                    return true;
                }
            }
            return false;
        }
        
        else if("MDM".equals(tokenlist.get(index).classPart))
        {
            return true;
        }
        
        return true;
    }
    
    private boolean PARA0()
    {
        if(PARA())
        {
            if(PARA2())
            {
            return true;
            }
        }
        else if(")".equals(tokenlist.get(index).classPart))
        {
            index++;
            return true;
        }
        return false;
    }
    
    private boolean PARA()
    {
        if(OE())
        {
            return true;
        }
        return false;
    }
    
    private boolean PARA2()
    {
        if(")".equals(tokenlist.get(index).classPart))
        {
            index++;
            return true;
        }
        
        else if(",".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(PARA())
            {
                if(PARA2())
                {
                return true;
                }
            }
        }
       
        return false;
    }
    
    private boolean PARA3()
    {
        if(".".equals(tokenlist.get(index).classPart))
        {
            index++;
            if("ID".equals(tokenlist.get(index).classPart))
            {
                index++;
                if(FR1())
                {
                    return true;
                }
            }
        }
        
        else if("[".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(OE())
            {
                if("]".equals(tokenlist.get(index).classPart))
                {
                    index++;
                    if(FR2())
                    {
                        return true;
                    }
                }
            }
        }
        
        else if(";".equals(tokenlist.get(index).classPart))// || ("IncDec".equals(tokenlist.get(index).classPart)))
        {
            return true;
        }
        return false;
    }
    
    private boolean PARA7()
    {
        if("(".equals(tokenlist.get(index).classPart))
        {
            index++;
            if(PARA0())
            {
                if(PARA3())
                {
                    if(";".equals(tokenlist.get(index).classPart))
                    {
                        index++;
                        return true;
                    }
                }
            }
        }
        return false;
    }
}