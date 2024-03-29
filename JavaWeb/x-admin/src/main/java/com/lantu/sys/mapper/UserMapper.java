package com.lantu.sys.mapper;

import com.lantu.sys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author woowoo
 * @since 2023-03-02
 */
public interface UserMapper extends BaseMapper<User> {
    public List<String> getRoleNameByUserId(Integer userId);
}
