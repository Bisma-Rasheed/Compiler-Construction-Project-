/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package owncompiler;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Bismah Rasheed
 */
public class OwnCompiler {

    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws IOException {
        
        Lexical_analyzer lex = new Lexical_analyzer();
        /*Syntax syntax=new Syntax(0,lex.tokenlist);
        if(syntax.value==true && "$".equals(syntax.tokenlist.get(syntax.index).classPart))
        {
            System.out.println("\nSyntax is Valid");
            File file = new File("src\\IntermediateCodeFile.txt");
            FileWriter fileWriter = new FileWriter(file);
        try (BufferedWriter writer = new BufferedWriter(fileWriter)) {
            
                writer.append(syntax.IC.intermediateCode);
        }
        }
        else
        {
            System.out.println("\nInvalid Syntax at Line: "+syntax.tokenlist.get(syntax.index).lineNumber+" at index: "+(syntax.index+1));
        }*/
       
    }
}


