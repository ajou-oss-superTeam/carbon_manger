package com.oss.carbonadministrator;

import org.junit.jupiter.api.Test;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.python.util.PythonInterpreter;

import java.io.File;


@SpringBootTest
public class JythonTest {
    private static PythonInterpreter intPre;

    @Test
    void jythonOpenTest(){
        intPre = new PythonInterpreter();
        intPre.execfile("src\\test\\java\\com\\oss\\carbonadministrator\\test.py");
        intPre.exec("print(testFunc(5,10))");

        PyFunction pyFunction = (PyFunction) intPre.get("testFunc", PyFunction.class);
        int a = 10;
        int b = 20;
        PyObject pyobj = pyFunction.__call__(new PyInteger(a), new PyInteger(b));
        System.out.println(pyobj.toString());
    }

    @Test
    void pathTest(){
        intPre = new PythonInterpreter();
        intPre.execfile("..\\ML\\test.py");
        intPre.exec("print(testFunc(5,10))");

        PyFunction pyFunction = (PyFunction) intPre.get("testFunc", PyFunction.class);
        int a = 10;
        int b = 20;
        PyObject pyobj = pyFunction.__call__(new PyInteger(a), new PyInteger(b));
        System.out.println(pyobj.toString());
    }

}
