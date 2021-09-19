package cn.yihao.service;

import com.health.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HMR
 * @create 2021/8/20 16:43
 */
public class UserService implements UserDetailsService {
//
//        public static Map<String, User> map = new HashMap<>();
//
//        static {
//            User user1 = new User();
//            user1.setUsername("admin");
//            user1.setPassword("admin");
//            User user2 = new User();
//            user2.setUsername("xiaoming");
//            user2.setPassword("1234");
//            map.put(user1.getUsername(),user1);
//            map.put(user2.getUsername(),user2);
//        }
//
//        @Override
//        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//            System.out.println("username:" + username);
//            User userInDb = map.get(username);//模拟根据用户名
//
//            if(userInDb == null){
////根据用户名没有查询到用户
//                return null;
//            }
////模拟数据库中的密码，后期需要查询数据库
//            String passwordInDb = "{noop}" + userInDb.getPassword();
//            List<GrantedAuthority> list = new ArrayList<>();
////授权，后期需要改为查询数据库动态获得用户拥有的权限和角色
//            list.add(new SimpleGrantedAuthority("add"));
//            list.add(new SimpleGrantedAuthority("delete"));
//            list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//            UserDetails user = new org.springframework.security.core.userdetails.User(username,passwordInDb,list);
//            return user;
//
//        }
        @Autowired
        private BCryptPasswordEncoder passwordEncoder;

        public static Map<String, User> map = new HashMap<>();

        public void initData(){
            User user1 = new User();
            user1.setUsername("admin");
            user1.setPassword(passwordEncoder.encode("admin"));
            User user2 = new User();
            user2.setUsername("xiaoming");
            user2.setPassword(passwordEncoder.encode("1234"));
            map.put(user1.getUsername(),user1);
            map.put(user2.getUsername(),user2);
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            initData();
            System.out.println("username:" + username);
            User userInDb = map.get(username);//模拟根据用户名

            if(userInDb == null){
//根据用户名没有查询到用户
                return null;
            }
//模拟数据库中的密码，后期需要查询数据库
            String passwordInDb =  userInDb.getPassword();
            List<GrantedAuthority> list = new ArrayList<>();
//授权，后期需要改为查询数据库动态获得用户拥有的权限和角色
//            list.add(new SimpleGrantedAuthority("add"));
            list.add(new SimpleGrantedAuthority("delete"));
            list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            UserDetails user = new org.springframework.security.core.userdetails.User(username,passwordInDb,list);
            return user;
        }

}
