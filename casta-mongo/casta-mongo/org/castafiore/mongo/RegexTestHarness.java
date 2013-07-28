package org.castafiore.mongo;

import java.io.Console;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegexTestHarness {

    public static void main(String[] args){

       

            Pattern pattern = Pattern.compile(".*/root/users/.*");

            Matcher matcher =  pattern.matcher("ssdfs/roosdt/users/dsdf");

           
            if (matcher.matches()) {
               System.out.printf( "I found the text\"%s\" starting at index %d and ending at index %d.%n",  matcher.group(),  matcher.start(), matcher.end());
                
            }
            
        
    }
}
