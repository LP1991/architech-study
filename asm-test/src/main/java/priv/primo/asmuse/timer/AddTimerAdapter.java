/********************** 版权声明 *************************
 * 文件名: AddTimerAdapter.java
 * 包名: priv.primo.asmuse.timer
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/8/22
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo.asmuse.timer;

import jdk.internal.org.objectweb.asm.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static jdk.internal.org.objectweb.asm.ClassReader.SKIP_FRAMES;
import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class AddTimerAdapter extends ClassVisitor {
    private String owner;
    private boolean isInterface;

    public AddTimerAdapter(ClassVisitor cv) {
        super(ASM5, cv);
    }

    @Override
    public void visit(int i, int i1, String s, String s1, String s2, String[] strings) {
        super.visit(i, i1, s, s1, s2, strings);
        owner = s;
        isInterface = (i1 & ACC_INTERFACE) != 0;
    }

    @Override
    public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
        MethodVisitor mv = super.visitMethod(i, s, s1, s2, strings);
        if (!isInterface && mv != null && !s.equals("<init>")) {
            mv = new AddTimerMethodAdapter(mv);
        }
        return mv;
    }

    @Override
    public void visitEnd() {
        if (!isInterface) {
            FieldVisitor fv = cv.visitField(ACC_PUBLIC + ACC_STATIC, "timer", Type.LONG_TYPE.getDescriptor(), null, null);
            if (fv != null) {
                fv.visitEnd();
            }
        }
        super.visitEnd();
    }

    class AddTimerMethodAdapter extends MethodVisitor {
        public AddTimerMethodAdapter(MethodVisitor mv) {
            super(ASM5, mv);
        }

        @Override
        public void visitCode() {
            mv.visitCode();
            mv.visitFieldInsn(GETSTATIC, owner, "timer", "J");
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J");
            mv.visitInsn(LSUB);
            mv.visitFieldInsn(PUTSTATIC, owner, "timer", "J");
        }

        @Override
        public void visitInsn(int opcode) {
            if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
                mv.visitFieldInsn(GETSTATIC, owner, "timer", "J");
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J");
                mv.visitInsn(LADD);
                mv.visitFieldInsn(PUTSTATIC, owner, "timer", "J");
                mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitFieldInsn(GETSTATIC, owner, "timer", "J");
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(J)V", false);
//                mv.visitInsn(RETURN);
            }
            mv.visitInsn(opcode);
        }

        @Override
        public void visitMaxs(int maxStack, int maxLocals) {
            mv.visitMaxs(maxStack + 4, maxLocals);
        }
    }
    static class MyClassLoader extends ClassLoader {
        public Class defineClass(String name, byte[] b) {
            return defineClass(name, b, 0, b.length);
        }

    }
    public static void main(String[] args) throws Exception {
        ClassWriter cw = new ClassWriter(0);
        AddTimerAdapter addTimerAdapter = new AddTimerAdapter(cw);
        ClassReader cr = new ClassReader("priv.primo.asmuse.timer.C" );
        cr.accept(addTimerAdapter,0);

        byte[] bytes = cw.toByteArray();

        MyClassLoader myClassLoader = new MyClassLoader();
        Class aClass = myClassLoader.defineClass("priv.primo.asmuse.timer.C", bytes);
        Object o = aClass.newInstance();
        Method m = aClass.getMethod("m", new Class[]{});
        m.invoke(o);
//        一个类的限定，有类的全路径和类的加载器一起决定
//        Field timer = aClass.getField("timer");
//        System.out.println(timer.get(o));
    }
}
