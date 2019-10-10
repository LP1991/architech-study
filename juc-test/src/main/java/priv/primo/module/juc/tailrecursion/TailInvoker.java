/********************** 版权声明 *************************
 * 文件名: TailInvoker.java
 * 包名: priv.primo.module.juc.tailrecursion
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/8/20
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo.module.juc.tailrecursion;

public class TailInvoker {
    public static void main(String[] args) {

        TailRecursion<Integer> result = factorialTailRecursion(1, 10_000_000);
        System.out.println(result.invoke());
//
//        System.out.println(factorialTailRecursion(1, 10_000_000));
    }

//    public static int factorialTailRecursion(final int factorial, final int number) {
//        if (number == 1) return factorial;
//        else return factorialTailRecursion(factorial * number, number - 1);
//    }

    public static TailRecursion<Integer> factorialTailRecursion(final int factorial, final int number) {
        if (number == 1)
            return TailInvoker.done(factorial);
        else
            return () -> {
                return factorialTailRecursion(factorial + number, number - 1);
        };
//            return () -> factorialTailRecursion(factorial + number, number - 1);
    }

    public static <T> TailRecursion<T> call(final TailRecursion<T> nextFrame) {
        return nextFrame;
    }

    public static <T> TailRecursion<T> done(T value) {
        return new TailRecursion<T>() {
            @Override
            public TailRecursion<T> apply() {
                System.out.println("递归已经结束,非法调用apply方法");
                throw new Error("递归已经结束,非法调用apply方法");
            }

            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public T get() {
                return value;
            }
        };
    }
}
