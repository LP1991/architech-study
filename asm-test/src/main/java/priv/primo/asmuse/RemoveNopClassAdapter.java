/********************** 版权声明 *************************
 * 文件名: RemoveNopClassAdapter.java
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

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.util.CheckClassAdapter;
import jdk.internal.org.objectweb.asm.util.TraceClassVisitor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class RemoveNopClassAdapter extends ClassVisitor {
    public RemoveNopClassAdapter( ClassVisitor cv) {
        super(ASM5,cv);
    }

    @Override
    public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
        MethodVisitor methodVisitor = cv.visitMethod(i, s, s1, s2, strings);
        if (methodVisitor != null){
            methodVisitor = new RemoveNopAdapter(methodVisitor);
        }
        return methodVisitor;
    }

    public static void main(String[] args) throws IOException {
        ClassWriter classWriter = new ClassWriter(0);
        RemoveNopClassAdapter cv = new RemoveNopClassAdapter(classWriter);
        TraceClassVisitor trace = new TraceClassVisitor(cv,new PrintWriter(System.out));
        ClassReader classReader = new ClassReader("priv/primo/asmuse/TestClass");
        classReader.accept(trace,0);
        byte[] bytes = classWriter.toByteArray();

        FileOutputStream fos = new FileOutputStream("D:/data/TestClass.class");
        fos.write(bytes);
        fos.close();
    }
}

class RemoveNopAdapter extends MethodVisitor{

    public RemoveNopAdapter(MethodVisitor mv) {
        super(ASM5,mv);
    }

    @Override
    public void visitInsn(int i) {
        if ( i != NOP){
            mv.visitInsn(i);
        }
    }
}
