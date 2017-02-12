package com.neko1990.jetbrains.lua;

import com.intellij.lang.Language;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.ParsingTestCase;
import org.antlr.jetbrains.adaptor.xpath.XPath;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by neko1990 on 2/11/17.
 */
public class TestXPath extends ParsingTestCase {
    public TestXPath() {
        super("", "lua", new LuaParserDefinition());
    }

    public void testSingleLocalDef() throws Exception {
        String code = "local x = 1";
        String output = code;
        String xpath = "/file/chunk/stat/localstat";
        checkXPathResults(code, xpath, output);
    }

    public void testMultiLocalDef() throws Exception {
        String code =
                "local x = 1\n" +
                        "local y = {1,2,3}\n";
        String output = code;
        String xpath = "/file/chunk/stat/localstat";
        checkXPathResults(code, xpath, output);
    }

    public void testFuncNames() throws Exception {
        String code = loadFile("test.lua");
        String output =
                "f\n"+
                        "g\n" +
                        "h";
        String xpath = "/file/chunk/stat/functionstat/functionname";
        checkXPathResults(code, xpath, output);
    }

    public void testAllIDs() throws Exception {
        String code = loadFile("test.lua");
        String output =
                "f\n"+
                        "x\n" +
                        "y\n" +
                        "x\n" +
                        "x\n" +
                        "g\n" +
                        "x\n" +
                        "y\n" +
                        "h\n" +
                        "z";
        String xpath = "//NAME";
        checkXPathResults(code, xpath, output);
    }

    public void testAnyLocalDef() throws Exception {
        String code = loadFile("test.lua");
        String output =
                "local y = x\n" +
                        "local z = 9";
        String xpath = "//localstat";
        checkXPathResults(code, xpath, output);
    }

    public void testLocalDefIDs() throws Exception {
        String code = loadFile("test.lua");
        String output =
                "y\n" +
                        "z";
        String xpath = "//localstat/namelist/NAME";
        checkXPathResults(code, xpath, output);
    }

    public void testRuleUnderWildcard() throws Exception {
        String code = loadFile("test.lua");
        String output =
                "local y = x\n" +
                        "x[1] = \"sdflkjsdf\"\n";
        String xpath = "//functionstat/funcbody/chunk/stat/*";
        checkXPathResults(code, xpath, output);
    }

//    public void testCallList() throws Exception {
//        String code = "local y = a.b.c:d(1)";
//        String output = "a.b.c";
//        String xpath = "//primaryexp/!functioncall";
//        checkXPathResults(code, xpath, output);
//    }

//    public void testCallListDeeper() throws Exception {
//        String code = "local y = a.b.c:d(1).e:f()";
//        String output = "a.b.c";
//        String xpath = "//primaryexp/primaryexp";
//        checkXPathResults(code, xpath, output);
//    }

    // S U P P O R T
    protected void checkXPathResults(String code, String xpath, String allNodesText) throws IOException {
        checkXPathResults(LuaLanguage.INSTANCE, code, xpath, allNodesText);
    }

    protected void checkXPathResults(Language language, String code, String xpath, String allNodesText) throws IOException {
        myFile = createPsiFile("a", code);
        ensureParsed(myFile);
        assertEquals(code, myFile.getText());
        Collection<? extends PsiElement> nodes = XPath.findAll(language, myFile, xpath);
        StringBuilder buf = new StringBuilder();
        for (PsiElement t : nodes) {
            buf.append(t.getText());
            buf.append("\n");
        }
        assertEquals(allNodesText.trim(), buf.toString().trim());
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/";
    }

    @Override
    protected boolean skipSpaces() {
        return false;
    }

    @Override
    protected boolean includeRanges() {
        return true;
    }
}
