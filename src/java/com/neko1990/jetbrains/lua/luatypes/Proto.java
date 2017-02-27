package com.neko1990.jetbrains.lua.luatypes;

/**
 * Created by neko1990 on 2/28/17.
 */
public class Proto {
    public String[] constants;        // k
    public Proto[]  nestedFunctions;  // p
    public String[] localVars;        // locvars
    public String[] upValues;         // upvalues
    public boolean  isVararg;         // is_vararg
    public int      numParams;        // numparams

    public Proto(){
    }
//    public Proto(LuaFunctionDef func) {
//    }
}
