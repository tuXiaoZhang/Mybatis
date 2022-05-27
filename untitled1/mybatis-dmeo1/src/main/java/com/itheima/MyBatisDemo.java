package com.itheima;

import com.itheima.pojo.User;
import com.itheima.pojo.UserDto;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class MyBatisDemo {
    public static void main(String[] args) throws IOException {
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactory sqlSessionFactory =
//                new SqlSessionFactoryBuilder().build(inputStream);
//
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//
//        List<User> users = sqlSession.selectList("test.selectAll");
//        sqlSession.close();

        // 查出来UserList 转换成 UserDtoList
        List<User> userList = generate();
//        List<UserDto> userDtoList = new ArrayList<>();
//        Map<Integer, UserDto> map = new HashMap<>();
//        long count = 0;
//
//        UserDto userDto = null;
//        UserDto thisUserDto = null;
//        UserDto max = null;
//        for (int i = 0; i < userList.size(); i++) {
//            User user = userList.get(i);
//            // 剔除男生
//
//
//            if("girl".equals(user.getGender()) && 10 < user.getId()){
//                count ++;
//
//                // user -> userDto
//                userDto = new UserDto();
//                userDto.setId(user.getId());
//                userDto.setUsername(user.getUsername());
//                userDtoList.add(userDto);
//                map.put(userDto.getId(), userDto);
//                thisUserDto = userDto;
//
//
//
//            }
//            if(userDto != null){
//                return;
//            }
//            System.out.println(userDto);
//
//        }
//        userDtoList.size();
        lambda(userList);
    }

    public static void lambda(List<User> userList){
        List<UserDto> userDtoList = userList.stream()
                .filter(user -> user.getId() > 10 && "girl".equals(user.getGender()))
                .skip(2)
//                .max((u1, u2) -> u1.getId()>u2.getId() ? 1:-1)
                .map(user -> {
                    UserDto userDto = new UserDto();
                    userDto.setId(user.getId());
                    userDto.setUsername(user.getUsername());
                    return userDto;
                }).collect(Collectors.toList());   // Map<ID, UserDto>''

        Optional<User> max = userList.stream()
                .filter(user -> user.getId() > 10 && "girl".equals(user.getGender()))
                .max((u1, u2) -> u1.getId() > u2.getId() ? 1 : -1);

        Optional<User> max1 = userList.stream()
                .filter(user -> user.getId() > 10 && "girl".equals(user.getGender()))
                .findAny(); // 元素的任何一个，
        System.out.println(max1.get());

        long max2 = userList.stream()
                .filter(user -> user.getId() > 10 && "girl".equals(user.getGender()))
                .count(); // 元素的任何一个，
        System.out.println(max2);


        List<User> collect = userList.stream()
                .sorted((u1, u2) -> u1.getId() < u2.getId() ? 1 : -1).collect(Collectors.toList());
        System.out.println(collect);


        Map<Integer, UserDto> map  = userList.stream()
                .filter(user -> user.getId() > 10 && "girl".equals(user.getGender()))
                .map(user -> {
                    UserDto userDto = new UserDto();
                    userDto.setId(user.getId());
                    userDto.setUsername(user.getUsername());
                    return userDto;
//                }).collect(Collectors.toList());   // Map<ID, UserDto>
                }).collect(Collectors.toMap(userDto -> userDto.getId(), userDto -> userDto));
        System.out.println(map);
    }

    public static List<User> generate(){
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new User(i, "user" + i, "password" + i, "boy", "i"+i));
        }
        for (int i = 10; i < 20; i++) {
            list.add(new User(i, "user" + i, "password" + i, "girl", "i"+i));
        }

        return list;
    }
}
