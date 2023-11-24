package com.example;


import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PlatformTransactionManager platformTransactionManager;

    public void findUserTr() {
        // 2、获取默认事务定义
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        // 设置事务传播行为
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // 3、根据事务定义对象设置的属性，获取事务状态
        TransactionStatus status = platformTransactionManager.getTransaction(def);
        try {
            // 4、执行业务代码（这里进行模拟，执行多个数据库操作方法）
            userMapper.selectById(1L);
            userMapper.selectById(2L);
            // 5、事务进行提交
            platformTransactionManager.commit(status);

        } catch(Exception e){
            // 5、事务进行回滚
            platformTransactionManager.rollback(status);
        }
    }

    public List<User> selectList(User user){
        return userMapper.selectList(user);
    }


    public User selectById(Long userId){
        userMapper.selectById(userId);
        return userMapper.selectById(userId);
    }


    public User selectByIdTr(Long userId){

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        // 设置事务传播行为
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // 3、根据事务定义对象设置的属性，获取事务状态
        TransactionStatus status = platformTransactionManager.getTransaction(def);
        User user = null;
        try {
            // 4、执行业务代码（这里进行模拟，执行多个数据库操作方法）
            userMapper.selectById(userId);
            user = userMapper.selectById(userId);
            // 5、事务进行提交
            platformTransactionManager.commit(status);

        } catch(Exception e){
            // 5、事务进行回滚
            platformTransactionManager.rollback(status);
        }

        return user;
    }

    public User selectUserByIdOut(Long userId){
        // 耗时业务
        User user = userMapper.selectById(userId);
        // 事务方法
        selectByIdTr(user.getUserId());
        return user;
    }


//    @Transactional
    public int insert(User user){
        return userMapper.insert(user);
    }
}
