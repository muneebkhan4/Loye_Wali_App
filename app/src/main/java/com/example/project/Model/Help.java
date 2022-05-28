package com.example.project.Model;

public class Help {


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
