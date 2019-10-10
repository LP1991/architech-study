/********************** 版权声明 *************************
 * 文件名: GenerateClass.java
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

import org.objectweb.asm.ClassWriter;

import java.io.FileOutputStream;
import java.io.IOException;

import static org.objectweb.asm.Opcodes.*;

public class GenerateClass {
    public static void main(String[] args) throws IOException {
        ClassWriter classWriter = new ClassWriter(0);
        classWriter.visit(V1_8, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,
                "pkg/Comparable", null, "java/lang/Object", new String[]{"pkg/Mesurable"});
        classWriter.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS",
                "I", null, new Integer(-1)).visitEnd();
        classWriter.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL",
                "I", null, new Integer(0)).visitEnd();
        classWriter.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER",
                "I", null, new Integer(1)).visitEnd();

        classWriter.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo",
                "(Ljava/lang/Object;)I", null, new String[]{"java/lang/RunningTimeException", "java/lang/NullPointException"}).visitEnd();
        classWriter.visitEnd();
        byte[] bytes = classWriter.toByteArray();

        FileOutputStream fos = new FileOutputStream("D:\\data\\Mesurable.class");
        fos.write(bytes);
        fos.close();
/**
 * Class c = myClassLoader.defineClass("pkg.Comparable", b);
 * concreate a instrance class which generating by class writer
 */
    }
}

class StubClassLoader extends ClassLoader {
    @Override
    protected Class findClass(String name) throws ClassNotFoundException {
        if (name.endsWith("_Stub")) {
            ClassWriter cw = new ClassWriter(0);
//            ...
            byte[] b = cw.toByteArray();
            return defineClass(name, b, 0, b.length);
        } return super.findClass(name);
    }
}

/**
 * another way
 */


