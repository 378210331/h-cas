package com.hsy.cas.service;

import com.hsy.cas.entity.CasService;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 单点登录服务端信息
 * @author： husiyi
 * @date：   2021-11-09
 * @version： V1.0
 */
public interface ICasServiceService extends IService<CasService> {

    public List<CasService> listAllByOrder();

    public  CasService isMatch(String service);

}
