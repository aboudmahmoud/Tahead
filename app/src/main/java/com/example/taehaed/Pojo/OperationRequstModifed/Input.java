package com.example.taehaed.Pojo.OperationRequstModifed;

import java.io.Serializable;
import java.util.ArrayList;

public class Input implements Serializable {
    public String name;
    public int type;
    public int required;
    public Object value;
    public int validation;
    public ArrayList<Advanced> advanced;

}
