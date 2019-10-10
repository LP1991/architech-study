/********************** 版权声明 *************************
 * 文件名: EnhanceClass.java
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jdk.internal.org.objectweb.asm.*;
import jdk.internal.org.objectweb.asm.util.TraceClassVisitor;

public class EnhanceClass extends ClassLoader implements Opcodes{

    public static void main(String args[]) throws IOException, IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, InstantiationException{
        ClassReader cr = new ClassReader(Foo_OLD.class.getName());
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
//        ClassVisitor cv =  new MethodChangeClassAdapter(cw);
        ClassVisitor cv =  new TraceClassVisitor(cw, new PrintWriter(System.out));
        cr.accept(cv, Opcodes.V1_8);

        MethodVisitor testadd = cv.visitMethod(ACC_PUBLIC, "testadd", "()V", null, null);
        testadd.visitInsn(RETURN);
        testadd.visitMaxs(0,0);
        testadd.visitEnd();
        //新增加一个方法
        MethodVisitor mw = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "modify", "([Ljava/lang/String;)V", null, null);
        mw.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mw.visitLdcInsn("this is add method print!");
        mw.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
        mw.visitInsn(RETURN);
        // this code uses a maximum of two stack elements and two local
        // variables
        mw.visitMaxs(0, 0);
        mw.visitEnd();

        byte[] code = cw.toByteArray();
//        EnhanceClass loader = new EnhanceClass();
//        Class<?> exampleClass = loader.defineClass(Foo.class.getName(), code, 0, code.length);
//
//        for(Method method : exampleClass.getMethods()){
//            System.out.println(method);
//        }

        System.out.println("***************************");

        // uses the dynamically generated class to print 'Helloworld'
        //調用changeMethodContent，修改方法內容
//        exampleClass.getMethods()[0].invoke(exampleClass.newInstance(), null);

        System.out.println("***************************");
        //調用execute,修改方法名
//        exampleClass.getMethods()[2].invoke(exampleClass.newInstance(), null);
        // gets the bytecode of the Example class, and loads it dynamically

        FileOutputStream fos = new FileOutputStream("D:\\data\\Foo.class");
        fos.write(code);
        fos.close();
    }

}
