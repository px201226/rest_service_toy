package com.example.restapi.user;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int userCount = 3 ;
    static {
        users.add(new User(1,"홍길동", LocalDateTime.now(),"pass1","701010-1001234"));
        users.add(new User(2,"박명수", LocalDateTime.now(),"pass2","801010-1001234"));
        users.add(new User(3,"유재석", LocalDateTime.now(),"pass3","901010-1001234"));
    }

    public List<User> findAll(){
        return users;
    }

    public User findById(Integer id){
        for(User user : users){
            if(id.equals(user.getId())) return user;
        }
        return null;
    }

    public User save(User user){
        if(user.getId() == null){
            user.setId(++userCount);
        }
        users.add(user);
        return user;
    }

    public User deleteById(Integer id){
        Iterator<User> iterator = users.iterator();

        while(iterator.hasNext()){
            User user = iterator.next();
            if(user.getId().equals(id)) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }

    public User modifyById(Integer id, User user){
        User modifyUser = findById(id);
        modifyUser.setName(user.getName());
        modifyUser.setJoinDate(user.getJoinDate());
        return modifyUser;
    }
}
