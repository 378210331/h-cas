package com.hsy.cas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hsy.cas.entity.SysUser;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author scott
 * @since 2018-12-20
 */
public interface ISysUserService extends IService<SysUser> {

    public SysUser getUserByName(String username);


    public List<String> getRolesByUserId(String userId);

    public SysUser getUserByPhone(String phone);

}
