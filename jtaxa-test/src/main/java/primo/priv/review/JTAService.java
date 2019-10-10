/********************** 版权声明 *************************
 * 文件名: JTAService.java
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class JTAService {
    @Autowired
    private UserMapper userMapper;//操作db_user库
    @Autowired
    private AccountMapper accountMapper;//操作db_account库
    @Transactional
    public void insert() {
        User user = new User();
        user.setName("wangxiaoxiao");
        userMapper.insert(user);

        //    int i = 1 / 0;//模拟异常，spring回滚后，db_user库中user表中也不会插入记录
        Account account = new Account();
        account.setUserId(user.getId());
        account.setMoney(123456789);
        accountMapper.insert(account);

    }
}
