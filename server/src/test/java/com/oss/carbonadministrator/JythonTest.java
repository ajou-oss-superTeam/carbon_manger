package com.oss.carbonadministrator;

import org.junit.jupiter.api.Test;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
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

    @Test
    void ossTest(){
        intPre = new PythonInterpreter();
        intPre.execfile("..\\ML\\ocr_electronic.py");

        PyFunction pyFunction = (PyFunction) intPre.get("parse", PyFunction.class);
        File img_path = new File("..\\ML\\test\\receipt1.jpg");
        File output_path = new File("..\\ML\\test\\test.txt");
        PyObject pyobj = pyFunction.__call__(new PyString(img_path.getAbsolutePath()), new PyString(output_path.getAbsolutePath()));
        System.out.println(pyobj.toString());
    }

    @Test
    void ossTest2(){
        intPre = new PythonInterpreter();
        intPre.execfile("..\\ML\\ocr_electronic.py");

        String img_path = (new File("..\\ML\\test\\receipt1.jpg")).getAbsolutePath();
        String output_path = (new File("..\\ML\\test\\test.txt")).getAbsolutePath();




    }

}
