/********************** 版权声明 *************************
 * 文件名: AtomikosSpringMybatisExample.java
 * 包名: priv.primo.primo.priv.review
 * 版权:	杭州华量软件  jtaxatest
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/4/8
 * 文件版本：V1.0
 *
 *******************************************************/
package primo.priv.review;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AtomikosSpringMybatisExample {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-atomikos.xml");
        JTAService jtaService = context.getBean("jtaService", JTAService.class);
        jtaService.insert();
    }
}
