package com.example.mobile_schorgan;

import com.example.mobile_schorgan.models.Cliente;

import java.util.List;

public class Helper {

    public static int getIndexOf(List<Cliente> list, int id) {
        int pos = 0;

        for(Cliente myObj : list) {
            if(Integer.toString(id).equalsIgnoreCase(Integer.toString(myObj.getId())))
                return pos;
            pos++;
        }

        return -1;
    }
}
