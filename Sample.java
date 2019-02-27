import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Sample{
    public static void main(String[] args){
        String tempStr = "2432sadfhsio^^&%$vhuoer8(***qwytr";
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]+");
        Matcher matcher = pattern.matcher(tempStr);

        if (matcher.find()){
            String currentUserName = matcher.group(0);
            System.out.println(currentUserName+" enter");
        }
        String test = "123456";
        System.out.println(test.substring(4));
    }
}