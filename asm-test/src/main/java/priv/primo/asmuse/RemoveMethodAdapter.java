/********************** 版权声明 *************************
 * 文件名: RemoveMethodAdapter.java
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

import jdk.internal.org.objectweb.asm.util.CheckClassAdapter;
import jdk.internal.org.objectweb.asm.util.TraceClassVisitor;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class RemoveMethodAdapter extends ClassVisitor {
    private String mName;
    private String mDesc;
    public RemoveMethodAdapter(ClassVisitor cv,String mName,String mDesc) {
        super(ASM5,cv);
        this.mName = mName;
        this.mDesc = mDesc;
    }


    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] strings) {
        if (name.equals(mName) && desc.equals(mDesc)){

//            如果匹配到方法，则置空, Q去掉 init方法
            MethodVisitor compareTo = cv.visitMethod(access, name,
                    desc, signature, strings);
            compareTo.visitEnd();
            return null;
        }
        return super.visitMethod(access,name,desc,signature,strings);
    }

    public static void main(String[] args) throws IOException {
        ClassWriter classWriter = new ClassWriter(0);
        CheckClassAdapter cv = new CheckClassAdapter(classWriter);
        TraceClassVisitor trace = new TraceClassVisitor(cv,new PrintWriter(System.out));
        ClassReader classReader = new ClassReader("priv/primo/asmuse/TestClass");
        RemoveMethodAdapter removeDebugAdapter = new RemoveMethodAdapter(trace,"getName","()Ljava/lang/String;");
        classReader.accept(removeDebugAdapter,0);
        byte[] bytes = classWriter.toByteArray();

        FileOutputStream fos = new FileOutputStream("D:/data/TestClass.class");
        fos.write(bytes);
        fos.close();
    }
}
