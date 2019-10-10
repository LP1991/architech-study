/********************** 版权声明 *************************
 * 文件名: AccountMapper.java
 * 包名: priv.primo.primo.priv.review
 * 版权:	杭州华量软件  jtaxatest
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo   创建时间：2019/4/8
 * 文件版本：V1.0
 *
 *******************************************************/
package primo.priv.review;

import org.apache.ibatis.annotations.Insert;

public interface AccountMapper {
    @Insert("INSERT INTO account(user_id,money) VALUES(#{userId},#{money})")
    void insert(Account account);
}
