/********************** 版权声明 *************************
 * 文件名: RemoveDebugAdapter.java
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

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.FileOutputStream;
import java.io.IOException;

import org.objectweb.asm.Opcodes;

public class RemoveDebugAdapter extends ClassVisitor {
    public RemoveDebugAdapter(ClassVisitor cv) {
        super(Opcodes.ASM7,cv);
    }

    @Override
    public void visitSource(String s, String s1) {
        super.visitSource(s, s1);
    }

    @Override
    public void visitOuterClass(String s, String s1, String s2) {
        super.visitOuterClass(s, s1, s2);
    }

    @Override
    public void visitInnerClass(String s, String s1, String s2, int i) {
        super.visitInnerClass(s, s1, s2, i);
    }

    public static void main(String[] args) throws IOException {
        ClassWriter classWriter = new ClassWriter(0);
        ClassReader classReader = new ClassReader("priv/primo/asmuse/TestClass");
        RemoveDebugAdapter removeDebugAdapter = new RemoveDebugAdapter(classWriter);
        classReader.accept(removeDebugAdapter,0);
        byte[] bytes = classWriter.toByteArray();

        FileOutputStream fos = new FileOutputStream("D:/data/TestClass.class");
        fos.write(bytes);
        fos.close();
    }
}
