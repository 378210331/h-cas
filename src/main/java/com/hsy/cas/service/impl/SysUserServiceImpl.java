package com.hsy.cas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hsy.cas.entity.SysUser;
import com.hsy.cas.mapper.SysUserMapper;
import com.hsy.cas.service.ISysUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author scott
 * @since 2018-12-20
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public SysUser getUserByName(String username) {
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getUsername, username);
        return this.getOne(lambdaQueryWrapper, false);
    }

    @Override
    public List<String> getRolesByUserId(String userId) {
        return this.getBaseMapper().getRolesByUserId(userId);
    }

    @Override
    public SysUser getUserByPhone(String phone) {
        return this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getPhone, phone),false);
    }


}
