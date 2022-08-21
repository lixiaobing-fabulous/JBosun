package com.lxb.expr.parse.parse;

import com.lxb.models.FuncType;
import lombok.Data;

import java.util.List;

@Data
public class Func {
    public List<FuncType> args;
    public FuncType       returnType;
    public Tags           tags;
    public Object         f;
    public boolean        vArgs;
    public int            vArgsPos;
    public boolean        vArgsOmit;
    public boolean        mapFunc;
    public boolean        prefixEnabled;
    public boolean        prefixKey;
    public boolean        variantReturn;
    public Check          check;
}
