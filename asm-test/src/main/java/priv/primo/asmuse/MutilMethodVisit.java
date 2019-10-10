/********************** 版权声明 *************************
 * 文件名: MutilMethodVisit.java
 * 包名: priv.primo.asmuse
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/8/22
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo.asmuse;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import static jdk.internal.org.objectweb.asm.Opcodes.ASM5;

public class MutilMethodVisit extends ClassVisitor {

    public MutilMethodVisit(ClassVisitor cv) {
        super(ASM5, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv1, mv2;
        mv1 = cv.visitMethod(access, name, desc, signature, exceptions);
        mv2 = cv.visitMethod(access, "_" + name, desc, signature, exceptions);
        return new MultiMethodAdapter(mv1, mv2);
    }
    static class MultiMethodAdapter extends MethodVisitor{
        MethodVisitor mv1, mv2;

        public MultiMethodAdapter(MethodVisitor mv1,MethodVisitor mv2) {
            super(ASM5);
            this.mv1 = mv1;
            this.mv2 = mv2;
        }
    }
}
