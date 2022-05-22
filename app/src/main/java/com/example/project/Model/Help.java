package com.example.project.Model;

public class Help {

    public static boolean greaterThan0(String str)
    {
        if(Integer.parseInt(str)>0)
        {
            return true;
        }
        return false;
    }

    public static int Integer(String str)
    {
        return Integer.parseInt(str);
    }

    public static String subtract(String operand1,String operand2)
    {
        return Integer.toString(Integer.parseInt(operand1)-Integer.parseInt(operand2));
    }

    public static String add(String operand1,String operand2)
    {
        return Integer.toString(Integer.parseInt(operand1)+Integer.parseInt(operand2));
    }
}
