/********************** 版权声明 *************************
 * 文件名: ClassPrinter.java
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

import org.objectweb.asm.*;

import java.io.IOException;
import java.io.InputStream;

import  org.objectweb.asm.Opcodes;

public class ClassPrinter extends ClassVisitor {
    public ClassPrinter() {
//        super(api);
        super(Opcodes.ASM7);
    }

    public ClassPrinter(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
//        super.visit(version, access, name, signature, superName, interfaces);
        System.out.println("signature:"+ signature + " : "+ name + " extends " + superName + " {");
    }

    @Override
    public void visitSource(String source, String debug) {
        super.visitSource(source, debug);
    }

    @Override
    public ModuleVisitor visitModule(String name, int access, String version) {
        return super.visitModule(name, access, version);
    }

    @Override
    public void visitNestHost(String nestHost) {
        super.visitNestHost(nestHost);
    }

    @Override
    public void visitOuterClass(String owner, String name, String descriptor) {
        super.visitOuterClass(owner, name, descriptor);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        return super.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
    }

    @Override
    public void visitAttribute(Attribute attribute) {
        super.visitAttribute(attribute);
    }

    @Override
    public void visitNestMember(String nestMember) {
        super.visitNestMember(nestMember);
    }

    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        super.visitInnerClass(name, outerName, innerName, access);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
//        return super.visitField(access, name, descriptor, signature, value);
        System.out.println("signature:"+ signature + " : "+ " " + descriptor + " " + name);
        return null;

    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
//        return super.visitMethod(access, name, descriptor, signature, exceptions);

        System.out.println("signature:"+ signature + " : " + name + descriptor);
        return null;
    }

    @Override
    public void visitEnd() {
//        super.visitEnd();
        System.out.println("}");
    }

    public static void main(String[] args) throws IOException {
        ClassPrinter cp = new ClassPrinter();
/*
Note that there are several ways to construct a ClassReader instance.
The class that must be read can be speciﬁed by name, as above, or by value, as a byte array or as an InputStream.
 An input stream to read the content of a class can be obtained with the ClassLoader’s getResourceAsStream method with:
 */
//        ClassReader cr = new ClassReader("java.lang.Runnable");
//        cr.accept(cp,0);
//          2. InputStream
        InputStream resourceAsStream =
                ClassPrinter.class.getClassLoader().getResourceAsStream("priv/primo/asmuse/TestClass".replace(".", "/") + ".class");
        ClassReader cr = new ClassReader(resourceAsStream);
        cr.accept(cp,0);

    }
}
