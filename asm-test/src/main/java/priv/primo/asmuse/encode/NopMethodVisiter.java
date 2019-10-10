/********************** 版权声明 *************************
 * 文件名: NopMethodVisiter.java
 * 包名: priv.primo.asmuse.encode
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/8/21
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo.asmuse.encode;

import jdk.internal.org.objectweb.asm.*;

import java.io.FileOutputStream;
import java.io.IOException;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class NopMethodVisiter extends ClassVisitor {
    public NopMethodVisiter(ClassVisitor cv) {
        super(ASM5,cv);
    }

    @Override
    public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
//        f = true, 则此方法为抽象方法
        boolean f = (i&ACC_ABSTRACT) == 1;
//        如果方法不是系统init方法，也不是抽象方法，则重写方法
        if (!(s.equals("<init>"))){
            Type returnType = Type.getReturnType(s1);
            MethodVisitor mv = cv.visitMethod(i, s, s1, s2, strings);
            mv.visitCode();
            switch (returnType.getSort()){
                case Type.VOID:
                    mv.visitInsn(RETURN);
                    mv.visitMaxs(0, 1);
                    break;
                case Type.BOOLEAN:
                case Type.CHAR:
                case Type.BYTE:
                case Type.SHORT:
                case Type.INT:
                    mv.visitInsn(ICONST_0);
                    mv.visitInsn(IRETURN);
                    mv.visitMaxs(1, 1);
                    break;
                case Type.FLOAT:
                    mv.visitInsn(FCONST_0);
                    mv.visitInsn(FRETURN);
                    mv.visitMaxs(1, 1);
                    break;
                case Type.LONG:
                    mv.visitInsn(LCONST_0);
                    mv.visitInsn(LRETURN);
                    mv.visitMaxs(2, 1);
                    break;
                case Type.DOUBLE:
                    mv.visitInsn(DCONST_0);
                    mv.visitInsn(DRETURN);
                    mv.visitMaxs(2, 1);
                    break;
                case Type.ARRAY:
                case Type.OBJECT:
                case Type.METHOD:
                    mv.visitInsn(ACONST_NULL);
                    mv.visitInsn(ARETURN);
                    mv.visitMaxs(1, 1);
                    break;
                default:
                    mv.visitInsn(ACONST_NULL);
                    mv.visitInsn(ARETURN);
                    mv.visitMaxs(1, 1);
            }
            mv.visitEnd();
            return null;
        }
        return super.visitMethod(i,s,s1,s2,strings);
    }

    public static void main(String[] args) throws IOException {

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        NopMethodVisiter cv = new NopMethodVisiter(classWriter);
//      制定class 类型
        ClassReader classReader = new ClassReader("priv.primo.asmuse.encode.NopInterface".replace(".","/"));
        classReader.accept(cv,0);
        byte[] bytes = classWriter.toByteArray();
//      输出class 路径
        FileOutputStream fos = new FileOutputStream("D:/data/NopInterface.class");
        fos.write(bytes);
        fos.close();
    }
}
