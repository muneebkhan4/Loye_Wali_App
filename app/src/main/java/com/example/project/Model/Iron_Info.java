package com.example.project.Model;

import java.util.HashMap;

public interface Iron_Info {
    String getLength();
    String getQuantity();
    String getName();
    void setQuantity(String quantity);

    public static int[] convertToArray(Iron_Info iron_Info) {

        int quantity = Help.Integer(iron_Info.getQuantity());
        int length = Help.Integer(iron_Info.getLength());
        int[] x = new int[quantity];
        for (int i = 0; i < quantity; i++) {
            x[i] = length;
        }
        return x;
    }

    // if null: false otherwise True
    public static HashMap<String,Iron_Info> cutStockRod(Iron_Info stockRod, Iron_Info orderRod) {

        int[] S = Iron_Info.convertToArray(stockRod);
        int[] O = Iron_Info.convertToArray(orderRod);

        int i = 0;
        for (int j = 0; j < O.length; j++) {

            if (S.length == i)
                return null;
            if (S[i] >= O[j]) {
                S[i] = S[i] - O[j];
                if (S[i] < O[j])
                    i++;
            } else
                return null;
        }
        return Iron_Info.convert(S);

    }

    //converts array Of Rod Lengths to List<Rod_Info>
    public static HashMap<String,Iron_Info> convert(int[] rods) {
        HashMap<String,Iron_Info> list = new HashMap<>();

        if (rods.length == 0)
            return null;
        int rodLength = rods[0];
        int count = 1;
        for (int i = 1; i < rods.length; i++) {
            if (rodLength == rods[i]) {
                count++;
            } else {
                String length = Integer.toString(rodLength);
                String quantity = Integer.toString(count);
                if (rodLength != 0) {
                    list.put(length,new Rod_Info(length, quantity));
                }
                rodLength = rods[i];
                count = 1;
            }
        }
        String length = Integer.toString(rodLength);
        String quantity = Integer.toString(count);
        if(rodLength!=0)
        {
            list.put(length,new Rod_Info(length, quantity));
        }
        return list;
    }
}
