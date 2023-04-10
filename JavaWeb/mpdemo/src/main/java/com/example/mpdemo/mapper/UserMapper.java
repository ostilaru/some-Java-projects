package com.example.mpdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mpdemo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User>{

//    @Select("select * from TbStudent")
//    public List<User> find();
//
//    @Insert("insert into TbStudent values(#{stuid},#{stuname},#{stusex},#{stubirth},#{stuaddr},#{photo})")
//    public int insert(User user);

}
