package com.smart.dao;

import com.smart.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int getMatchCount(String userName,String password){
        String sql = "Select count(*) from user where user_name = ? and password = ?";
        return jdbcTemplate.queryForObject(sql,new Object[]{userName,password},int.class);
    }

    public User findUserByName(String userName){
        String sql = "Select user_id,user_name,credits from user where user_name = ?";
        User user = new User();
        jdbcTemplate.query(sql,new Object[]{userName},(rch)->{
            user.setUserId(rch.getInt("user_id"));
            user.setUserName(userName);
            user.setCredits(rch.getInt("credits"));
        });
        return user;
    }

    public void updateLoginInfo(User user){
        String sql = "update user set last_visit=?,last_ip=?,credits=? where user_id=?";
        jdbcTemplate.update(sql,new Object[]{user.getLastVisit(),user.getLastIp(),user.getCredits(),user.getUserId()});
    }
}
