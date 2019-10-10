/********************** 版权声明 *************************
 * 文件名: TestASMifier.java
 * 包名: priv.primo.asmuse
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/8/21
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo.asmuse;

import jdk.internal.org.objectweb.asm.util.ASMifier;

import java.lang.reflect.Method;

public class TestASMifier {
    public static void main(String[] args) throws Exception {
//        java -classpath asm.jar:asm-util.jar  org.objectweb.asm.util.ASMifier  java.lang.Runnable
                ASMifier.main(new String[]{"priv.primo.asmuse.encode.NopClass"});

//        byte[] dump = FooDump.dump();
////        Class<?> aClass1 = TestASMifier.class.getClassLoader().loadClass("priv.primo.asmuse.Foo");
//        MyClassLoader loader = new MyClassLoader();
//        Class aClass = loader.defineClass("priv.primo.asmuse.Foo", dump);
//        Object o = aClass.newInstance();
//        Method testadd = aClass.getMethod("testadd", new Class[]{});
//        testadd.invoke(o, null);
//        System.out.println(aClass.getName());

    }
}

class MyClassLoader extends ClassLoader {
    public Class defineClass(String name, byte[] b) {
        return defineClass(name, b, 0, b.length);
    }

}