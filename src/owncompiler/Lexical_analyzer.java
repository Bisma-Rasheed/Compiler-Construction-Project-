/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package owncompiler;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;

/**
 *
 * @author Bismah Rasheed
 */
public class Lexical_analyzer {
    
    String text = "";
    Token token=new Token();    
    ArrayList<Token> tokenlist = new ArrayList<>(); //to save tokens to be used in the next phase
    char[] charArray = {};
    char[] digits={'0','1','2','3','4','5','6','7','8','9'}; //for the matching of character before and after dot
    char[] characters ={'r','t','f','b','n','\\','\'','\"','0','1','2','3','4','5','6','7'};
    
    public Lexical_analyzer() throws IOException
    {
        this.token.lineNumber=0;
        ReadFromTextFile();
    }
    
    
    private void WriteInTextFile() throws IOException //writes in text file after whole source code has been read and token 
            //are generated
    {
            File file = new File("src\\TokenSetFile.txt");
            FileWriter fileWriter = new FileWriter(file);
            //System.out.println(tokenlist);
        try (BufferedWriter writer = new BufferedWriter(fileWriter)) {
            for(int i=0;i<tokenlist.size();i++)
            {
                writer.append("( "+tokenlist.get(i).classPart+" , "+tokenlist.get(i).valuePart+" , "+tokenlist.get(i).lineNumber+" )");
                writer.newLine();
            }
        }
        
    }
    
    public class savey{
        Token token;
        int index;
        
        
        public savey(Token token, int index)
        {
            this.index=index;
            this.token=token;
        }
    }
    
    ArrayList<savey> saveindex=new ArrayList<>();
    ArrayList<Integer> index1=new ArrayList<>();
    
    private void saveindex(Token token, int index)
    {
        int temp=token.lineNumber;
        index1.add(temp);
        saveindex.add(new savey(token,index));
    }
    
    private void retrieveindex()
    {
        for(int i=0;i<saveindex.size();i++)
        {
            saveindex.get(i).token.lineNumber=index1.get(i);
            tokenlist.set(saveindex.get(i).index, saveindex.get(i).token);
        }
    }
    
    private void ReadFromTextFile() throws IOException
    {
        boolean flag=false;
        int linecounter=1;
        try {
            File file = new File("src\\SourceCodeFile.txt");
            try (FileReader fileReader = new FileReader(file)) 
            {
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) 
                {
                    charArray = line.toCharArray(); //saves the first line from the file in a char array 
                    if(flag==true)
                    {
                        saveindex(token,tokenlist.size()-1);
                    }
                    token.lineNumber=linecounter;
                    wordSeperator(); //the first line is sent into this func to get worked on it
                    linecounter++; //line counter increases after a line has been made and worked on.
                    flag=true;
                }
                fileReader.close();
            }
           
            } 
            catch (IOException e) 
            {
                System.out.println(e);
            }
            retrieveindex();
            Token token1=new Token("$","$",token.lineNumber);
            tokenlist.add(token1);
            WriteInTextFile();
    }
    
    
    
    public void wordSeperator() throws IOException
    {
        
        String word="";
       
        for(int i=0;i<charArray.length;i++) //for loop to read whole char array sent from the buffered reader from Readfile func
        {
            
            if(charArray[i]!='\'' && charArray[i]!='\"' && charArray[i]!=' ' && charArray[i]!= ';' && charArray[i]!= '(' && charArray[i]!= ')' && charArray[i]!= '{' &&
            charArray[i]!= '}' && charArray[i]!='[' && charArray[i]!= ']' && charArray[i]!= '~' && charArray[i]!= '`' &&
                    charArray[i]!='@' && charArray[i]!='#'
            && charArray[i]!= '^' && charArray[i]!= '&' && charArray[i]!= '\\' && charArray[i]!= ':' && charArray[i]!= ','
            && charArray[i]!= '<' && charArray[i]!= '!' && charArray[i]!= '+' && charArray[i]!= '-' && charArray[i]!= '>'
            && charArray[i]!= '/' && charArray[i]!= '*' && charArray[i]!= '%' && charArray[i]!= '=' && charArray[i]!='.'
                    && charArray[i]!= '|' ) //if cond to check for alphabets and digits
            {
                word=word+charArray[i]; //if its an alphabet or digit stores it in word and continues as these are not word breakers
                if(i==charArray.length-1)
                {
                    Tokenization(word,token.lineNumber); //if we are at the end of char array and the last char is an alpha
                    //or digit then we make its token otherwise continue
                }
            }
           
            
            else if(charArray[i]=='|') //for | first we check for when word is empty then if the word is not empty
            {
                if("".equals(word))
                {
                if((i+1)<charArray.length && charArray[i+1]=='|') //if char array contains a consecutive | stores it along with the first one
                {
                    word=word+charArray[i];
                    word=word+charArray[i+1];
                    Tokenization(word,token.lineNumber);
                    word="";
                    i++;
                }
                else
                {
                    word=word+charArray[i];
                    Tokenization(word,token.lineNumber);
                    word="";
                }
                }
                else if(word.contains("\"")) //a check for if word contains " then its a part of a string so stores it with the word
                {
                    word=word+charArray[i];
                }
                else //if the word is not empty then first makes token of the already stored word and then decrements i so
                    //that the same index from the char array will be read in the next iteration
                {
                   Tokenization(word,token.lineNumber);
                   word="";
                   i--;
                }
            }
            
            else if(charArray[i]=='.')
            {
                if(word.contains(".")) //for checking if there is a dot already in the word then first make token for the word
                {
                    Tokenization(word,token.lineNumber);
                    word="";
                    i--;
                    continue;
                }
                for(int k=0;k<digits.length;k++)  //for checking if the next character after dot is a digit or not
                {
                    if("".equals(word) && digits[k]==charArray[i+1])
                    {
                        word=word+charArray[i];
                    }
                    else
                    {
                        if(k==digits.length-1 && "".equals(word)) //if next character is not a digit then make seperate token for dot
                        {
                        word=word + charArray[i];
                        Tokenization(word,token.lineNumber);
                        word="";
                        break;
                        }
                    }
                }
                    if(!"".equals(word) && word.contains("\""))
                    {
                        word=word+charArray[i];
                    }
                    else if(!"".equals(word) && (word.contains("a") || word.contains("A") || word.contains("b") || word.contains("B") ||
                            word.contains("c") || word.contains("C") || word.contains("d") || word.contains("D") || word.contains("e") || word.contains("E") ||
                            word.contains("f") || word.contains("F") || word.contains("g") || word.contains("G") || word.contains("h") || word.contains("H") ||
                            word.contains("i") || word.contains("I") || word.contains("j") || word.contains("J") || word.contains("k") ||
                            word.contains("K") || word.contains("l") || word.contains("L") || word.contains("m") || word.contains("M") || word.contains("n") ||
                            word.contains("N") || word.contains("o") || word.contains("O") || word.contains("p") || word.contains("P") || word.contains("q") ||
                            word.contains("Q") || word.contains("r") || word.contains("R") || word.contains("s") || word.contains("S") ||
                            word.contains("t") || word.contains("T") || word.contains("u") || word.contains("U") || word.contains("v") ||
                            word.contains("V") || word.contains("w") || word.contains("W") || word.contains("x") || word.contains("X") ||
                            word.contains("y") || word.contains("Y") || word.contains("z") || word.contains("Z")))
                    {
                        Tokenization(word,token.lineNumber);
                        word="";
                        i--;
                    }
                    else
                    {
                        for(int s=0;s<digits.length;s++) //for a dot having all previous digits and a digit after dot
                        {
                            if(charArray[i+1]==digits[s])
                            {
                                if(!"".equals(word) && !word.contains(".") )
                                {
                                    word=word+charArray[i];
                                    break;
                               
                                }
                            }
                            else if(s==digits.length-1 && !"".equals(word))
                            {
                                if(!".".equals(word))
                                {
                                Tokenization(word,token.lineNumber);
                                word="";
                                i--;
                                }
                            }
                        }
                    }
            }
                
            else if(charArray[i]=='\\') //backslash
            {
                if("".equals(word)) //for when the word is empty make a seperate token
                {
                    word=word+charArray[i];
                    Tokenization(word,token.lineNumber);
                    word="";
                }
                else if(!"".equals(word)) //when the word is not empty then check whether it contains " if yes save \ along with the word
                {
                    if(word.contains("\""))
                    {
                        word=word+charArray[i];
                    }
                    else //otherwise first make token for the word and then decrement the counter
                    {
                        Tokenization(word,token.lineNumber);
                        word="";
                        i--;
                    }
                }
            }
            
            else if(charArray[i]=='\"') 
            {
                if("".equals(word)) //when the word is empty then its the first " stores it and continues
                {
                    word=word+charArray[i];
                }
                else if(!"".equals(word)) //if word is not empty then checks if it already contains "
                {
                    if(word.contains("\""))
                    {
                        String s=Arrays.toString(charArray);
                        if(s.contains("\\\\") || s.contains("\\\\\\\\")) //checks if the recent " is preceded by a \ if yes then its part of the 
                            //string so stores it and continues otherwise the recent " is the ending " stores it and make token
                            //of the word
                        {
                            word=word+charArray[i];
                            Tokenization(word,token.lineNumber);
                            word="";
                        }
                        else if(charArray[i-1]=='\\')
                        {
                            word=word+charArray[i];
                        }
                        else
                        {
                            word=word+charArray[i];
                            Tokenization(word,token.lineNumber);
                            word="";
                        }
                    }
                    else
                    {
                        //word=word+charArray[i];
                        Tokenization(word,token.lineNumber);
                        word="";
                        i--;
                    }
                }
            }
            
            else if(charArray[i]=='\'') //it checks on basis of length 
            {
                if("".equals(word))
                {
                    if((i+1)<charArray.length && charArray[i+1]!='\\') //if a \ is there then stores 4 characters together including starting and ending
                        //' otherwise stores 3 characters together in a word.
                    {
                        word=word+charArray[i];
                        if((i+1)<charArray.length)
                        {
                            word=word+charArray[i+1];
                        }
                        if((i+2)<charArray.length)
                        {
                            word=word+charArray[i+2];
                        }
                        Tokenization(word,token.lineNumber);
                        word="";
                        i=i+2;
                    }
                    else if((i+1)<charArray.length && charArray[i+1]=='\\')
                    {
                        word=word+charArray[i];
                        word=word+charArray[i+1];
                        if((i+2)<charArray.length)
                        {
                            word=word+charArray[i+2];
                        }
                        if((i+3)<charArray.length)
                        {
                            word=word+charArray[i+3];
                        }
                        Tokenization(word,token.lineNumber);
                        word="";
                        i=i+3;
                    }
                    else
                    {
                        Tokenization(Character.toString(charArray[i]),token.lineNumber);
                        i--;
                    }
                }
                else if(word.contains("\""))
                {
                    word=word+charArray[i];
                }
                else if(!"".equals(word)) //if word is not empty then stores ' along with the word as ' is not a word breaker
                {
                    //word=word+charArray[i];
                    Tokenization(word,token.lineNumber);
                    word="";
                    i--;
                }
            }
            
            else if(charArray[i]=='=') //if == and the word is empty then stores them together otherwise make seperate token for =
            {
                if("".equals(word))
                {
                if((i+1)<charArray.length && charArray[i+1]=='=')
                {
                    word=word+charArray[i];
                    word=word+charArray[i+1];
                    Tokenization(word,token.lineNumber);
                    word="";
                    i++;
                }
                else
                {
                    word=word+charArray[i];
                    Tokenization(word,token.lineNumber);
                    word="";
                }
                }
                else if(word.contains("\"")) //if word contains " then stores = in the word and continues
                {
                    word=word+charArray[i];
                }
                else //otherwise first makes token for the previous word and then again iterates for '
                {
                    Tokenization(word,token.lineNumber);
                    word="";
                    i--;
                }
            }
            
            
            else if(charArray[i]==':') //same logic for all coming punxtuators as for =
            {
                if("".equals(word))
                {
                if((i+1)<charArray.length && charArray[i+1]==':')
                {
                    word=word+charArray[i];
                    word=word+charArray[i+1];
                    Tokenization(word,token.lineNumber);
                    word="";
                    i++;
                }
                else
                {
                    word=word+charArray[i];
                    Tokenization(word,token.lineNumber);
                    word="";
                }
                }
                else if(word.contains("\""))
                {
                    word=word+charArray[i];
                }
                else
                {
                   Tokenization(word,token.lineNumber);
                   word="";
                   i--;
                }
            }
            
            
            else if(charArray[i]=='&')
            {
                if("".equals(word))
                {
                if((i+1)<charArray.length && charArray[i+1]=='&')
                {
                    word=word+charArray[i];
                    word=word+charArray[i+1];
                    Tokenization(word,token.lineNumber);
                    word="";
                    i++;
                }
                else
                {
                    word=word+charArray[i];
                    Tokenization(word,token.lineNumber);
                    word="";
                }
                }
                else if(word.contains("\""))
                {
                    word=word+charArray[i];
                }
                else
                {
                   Tokenization(word,token.lineNumber);
                   word="";
                   i--;
                }
            }
            
            else if(charArray[i]=='!')
            {
                if("".equals(word))
                {
                if((i+1)<charArray.length && charArray[i+1]=='=')
                {
                    word=word+charArray[i];
                    word=word+charArray[i+1];
                    Tokenization(word,token.lineNumber);
                    word="";
                    i++;
                
                }
                else
                {
                    word=word+charArray[i];
                    Tokenization(word,token.lineNumber);
                    word="";
                }
                }
                else if(word.contains("\""))
                {
                    word=word+charArray[i];
                }
                else
                {
                   Tokenization(word,token.lineNumber);
                   word="";
                   i--;
                }
            }
            
            else if(charArray[i]=='+')
            {
                if("".equals(word))
                {
                if((i+1)<charArray.length && (charArray[i+1]=='=' || charArray[i+1]=='+'))
                {
                    word=word+charArray[i];
                    word=word+charArray[i+1];
                    Tokenization(word,token.lineNumber);
                    word="";
                    i++;
                }
                else if(((i+1)<charArray.length && charArray[i+1]=='+'))
                {
                    word=word+charArray[i];
                    word=word+charArray[i+1];
                    Tokenization(word,token.lineNumber);
                    word="";
                    i++;
                }
                else
                {
                    word=word+charArray[i];
                    Tokenization(word,token.lineNumber);
                    word="";
                }
                }
                else if(word.contains("\""))
                {
                    word=word+charArray[i];
                }
               else if(word.contains("."))
                {
                    if(charArray[i-1]=='e' || charArray[i-1]=='E')
                    {
                        for(int m=0;m<digits.length;m++)
                        {
                            if(charArray[i+1]==digits[m])
                            {
                                word=word+charArray[i];
                                break;
                            }
                            else if(m==digits.length-1)
                            {
                                Tokenization(word,token.lineNumber);
                                word="";
                                i--;
                            }
                        }
                    }
                    else
                    {
                       Tokenization(word,token.lineNumber);
                       word="";
                       i--; 
                    }
                            
                }
                else
                {
                   Tokenization(word,token.lineNumber);
                   word="";
                   i--;
                }
            }
            
            else if(charArray[i]=='-')
            {
                if("".equals(word))
                {
                if((i+1)<charArray.length && (charArray[i+1]=='=' || charArray[i+1]=='-'))
                {
                    word=word+charArray[i];
                    word=word+charArray[i+1];
                    Tokenization(word,token.lineNumber);
                    word="";
                    i++;
                }
                else
                {
                    word=word+charArray[i];
                    Tokenization(word,token.lineNumber);
                    word="";
                }
                }
                else if(word.contains("."))
                {
                    if(charArray[i-1]=='e' || charArray[i-1]=='E')
                    {
                        for(int m=0;m<digits.length;m++)
                        {
                            if(charArray[i+1]==digits[m])
                            {
                                word=word+charArray[i];
                                break;
                            }
                            else if(m==digits.length-1)
                            {
                                Tokenization(word,token.lineNumber);
                                word="";
                                i--;
                            }
                        }
                    }
                    else
                    {
                       Tokenization(word,token.lineNumber);
                       word="";
                       i--; 
                    }
                            
                }
                else if(word.contains("\""))
                {
                    word=word+charArray[i];
                }
                else
                {
                   Tokenization(word,token.lineNumber);
                   word="";
                   i--;
                }
            }
            
            else if(charArray[i]=='>')
            {
                if("".equals(word))
                {
                if((i+1)<charArray.length && (charArray[i+1]=='=' || charArray[i+1]=='>'))
                {
                    word=word+charArray[i];
                    word=word+charArray[i+1];
                    Tokenization(word,token.lineNumber);
                    word="";
                    i++;
                }
                else
                {
                    word=word+charArray[i];
                    Tokenization(word,token.lineNumber);
                    word="";
                }
                }
                else if(word.contains("\""))
                {
                    word=word+charArray[i];
                }
                else
                {
                   Tokenization(word,token.lineNumber);
                   word="";
                   i--;
                }
            }
            
            else if(charArray[i]=='/')  //ashar yeh dekhlena single line comment k liye
            {
                if("".equals(word))
                {
                if((i+1)<charArray.length && charArray[i+1]=='=')
                {
                    word=word+charArray[i];
                    word=word+charArray[i+1];
                    Tokenization(word,token.lineNumber);
                    word="";
                    i++;
                }
                else if((i+1)<charArray.length && charArray[i+1]=='/') //for Single Line Comment
                {
                    word=word+charArray[i];
                    word=word+charArray[i+1];
                    for(int k=i+2;k <charArray.length;k++)
                    {
                        word=word+charArray[k];
                    }
                    System.out.println("Line Number "+token.lineNumber+" has a comment, No Token for comment.");
                    break;
                }
                else
                {
                    word=word+charArray[i];
                    Tokenization(word,token.lineNumber);
                    word="";
                }
                }
                else if(word.contains("\""))
                {
                    word=word+charArray[i];
                }
                else
                {
                   Tokenization(word,token.lineNumber);
                   word="";
                   i--;
                }
            }
            
            
            
            else if(charArray[i]=='<')
            {
                if("".equals(word))
                {
                if((i+1)<charArray.length && (charArray[i+1]=='<' || charArray[i+1]=='='))
                {
                    word=word+charArray[i];
                    word=word+charArray[i+1];
                    Tokenization(word,token.lineNumber);
                    word="";
                    i++;
                }
                else
                {
                    word=word+charArray[i];
                    Tokenization(word,token.lineNumber);
                    word="";
                }
                }
                else if(word.contains("\""))
                {
                    word=word+charArray[i];
                }
                else
                {
                   Tokenization(word,token.lineNumber);
                   word="";
                   i--;
                }
            }
            
            else if(charArray[i]=='*')
            {
                if("".equals(word))
                {
                if((i+1)<charArray.length && charArray[i+1]=='=')
                {
                    word=word+charArray[i];
                    word=word+charArray[i+1];
                    Tokenization(word,token.lineNumber);
                    word="";
                    i++;
                }
                else
                {
                    word=word+charArray[i];
                    Tokenization(word,token.lineNumber);
                    word="";
                }
                }
                else if(word.contains("\""))
                {
                    word=word+charArray[i];
                }
                else
                {
                   Tokenization(word,token.lineNumber);
                   word="";
                   i--;
                }
            }
            
            else if(charArray[i]=='%')
            {
                if("".equals(word))
                {
                if((i+1)<charArray.length && charArray[i+1]=='=')
                {
                    word=word+charArray[i];
                    word=word+charArray[i+1];
                    Tokenization(word,token.lineNumber);
                    word="";
                    i++;
                }
                else
                {
                    word=word+charArray[i];
                    Tokenization(word,token.lineNumber);
                    word="";
                }
                }
                else if(word.contains("\""))
                {
                    word=word+charArray[i];
                }
                else
                {
                   Tokenization(word,token.lineNumber);
                   word="";
                   i--;
                }
            }
            
            
            else
            {
                if(word.contains("\""))
                {
                    word=word+charArray[i];
                    continue;
                }
                else if(word!="")
                {
                    Tokenization(word,token.lineNumber);
                    word="";
                }
                
                //word="";
                if(charArray[i]== ';' || charArray[i]== '(' || charArray[i]== ')' || charArray[i]== '{' ||
                charArray[i]== '}' || charArray[i]=='[' || charArray[i]== ']' || charArray[i]== '?' || charArray[i]== '~' || charArray[i]== '`' 
                || charArray[i]== '^' || charArray[i]== ',' || charArray[i]== '`' || charArray[i]== '@' || 
                        charArray[i]== '#')
                    //else if char array[i] is one of these then makes seperate tokens for them
                {
                    word=word+charArray[i];
                    Tokenization(word,token.lineNumber);
                    word="";
                    
                }
            }
            
           
        }
    }
    public void Tokenization(String value,int lineNumber) throws IOException
    {   
        int kwCheck=0;
        Info Check=new Info();
        char[] d=value.toCharArray();
        String dataType=CheckDataTypes(value);
            if("ID".equals(dataType))  //for Identifier and keywords, if not a keyword then it will be an identifier
            {
                for(int i=0;i<Check.KeywordsList.size();i++)
                {
                    if(value.equals(Check.KeywordsList.get(i).word))
                    {
                        token = new Token(Check.KeywordsList.get(i).className,value,lineNumber);
                        tokenlist.add(token);
                        kwCheck++;
                        break;
                    }
                }
                if(kwCheck==0)
                {
                    token = new Token("ID",value,lineNumber);
                    tokenlist.add(token);
                }
            }
            else if("Integer".equals(dataType))
            {
                token = new Token("Integer",value,lineNumber);
                tokenlist.add(token);
            }
            
            else if("Float".equals(dataType))
            {
                token = new Token("decimal",value,lineNumber);
                tokenlist.add(token);
            }
            
            else if("Character".equals(dataType))
            {
                char[] c = value.toCharArray();
                String temp="";
                for(int i=1;i<c.length-1;i++)
                {
                    temp = temp+c[i];
                }
                token = new Token("char",temp,lineNumber);
                tokenlist.add(token);
            }
            
            else if("String".equals(dataType))
            {
                char[] c = value.toCharArray();
                String temp="";
                for(int i=1;i<c.length-1;i++)
                {
                    temp = temp+c[i];
                }
                token = new Token("String",temp,lineNumber);
                tokenlist.add(token);
            }
                
            else if(value.contains("\\"))
            {
                if(d[0]=='\'' && d[d.length-1]=='\'')
                {
                    for(int i=0;i<characters.length;i++)
                    {
                    if(d[2]==characters[i])
                    {
                        char[] c = value.toCharArray();
                        String temp="";
                        for(int k=1;k<c.length-1;k++)
                        {
                           temp = temp+c[k];
                        }
                        token=new Token("Character",temp,lineNumber);
                        tokenlist.add(token);
                        break;
                    }
                    else if(i==characters.length-1)
                    {
                        token = new Token("Invalid Word",value,lineNumber);
                        tokenlist.add(token);
                    }
                    }
                }
            }
            else if(value.length() <= 2)
            {
                int a=0;
                for(int i=0;i<Check.OperatorsList.size();i++)
                {
                if(value.equals(Check.OperatorsList.get(i).word))
                {
                    token = new Token(Check.OperatorsList.get(i).className,value,lineNumber);
                    tokenlist.add(token);
                    a++;
                    break;
                }
                }
                
                for(int i=0;i<Check.PunctuatorsList.size();i++)
                {
                    if(value.equals(Check.PunctuatorsList.get(i).word))
                    {
                    token = new Token(Check.PunctuatorsList.get(i).className,value,lineNumber);
                    tokenlist.add(token);
                    a++;
                    break;
                    }
                }
                if(a==0)
                {
                if("Invalid".equals(dataType))
                {
                    token = new Token("Invalid Word",value,lineNumber);
                    tokenlist.add(token);
                }
                }
            }
            
            else if("Invalid".equals(dataType))
            {
                token = new Token("Invalid Word",value,lineNumber);
                tokenlist.add(token);
            }
        }
    
    private String CheckDataTypes(String s)
    {   
        testregex[] test = new testregex[5]; //  
        test[0] = new testregex("ID","([$A-Z$]|[$a-z$])([$A-Z$]|[$a-z$]|[$0-9$]|_|$)*|_([$A-Z$]|[$a-z$]|[$0-9$]|_)+|$([$A-Z$]|[$a-z$]|[$0-9$]|_)+");
        test[1] = new testregex("Integer","(([+]|[-])?)([0-9])+");
        test[2] = new testregex("Float","(([+]|[-])?)([0-9]*)[.]([0-9]+)(([E]|[e])(([+]|[-])?)([0-9])+)?");
        test[3] = new testregex("Character","\'([\\]([rtfbn\'\"\\01234567]))|([A-Z])|([a-z])|([0-9])|([~!;:=-@#$%^&*()_[]+-<>?,.{}|/]]])\'");
        test[4] = new testregex("String","\".*\"");
        
        Pattern RE;
        Matcher m;
        
        for (int i = 0; i < 5; i++) 
        {
            RE = Pattern.compile(test[i].RE);
            m = RE.matcher(s);
            if(m.matches())
            {
                return test[i].key;
            }
        }
        return "Invalid";
    }
}
