package com.hsy.cas.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hsy.cas.entity.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author scott
 * @since 2018-12-20
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<String> getRolesByUsername(String username);

    List<String> getRolesByUserId(String username);
}
