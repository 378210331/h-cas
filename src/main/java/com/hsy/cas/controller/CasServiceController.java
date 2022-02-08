package com.hsy.cas.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hsy.cas.constant.RedisPrefixConstant;
import com.hsy.cas.entity.CasService;
import com.hsy.cas.model.Result;
import com.hsy.cas.service.ICasServiceService;
import com.hsy.cas.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

/**
 * @Description 单点登录服务端信息
 * @author husiyi
 * @since   2021-11-09
 * @version V3.2
 */
@Controller
@Api(tags={"单点登录服务端信息"})
@Slf4j
public class CasServiceController {

	@Autowired
	private ICasServiceService casServiceService;

	@Autowired
	RedisUtil redisUtil;

	@ApiOperation(value = "分页列表查询单点登录服务端信息",tags = {"单点登录服务端信息"})
	@GetMapping(value = "/casServices/index")
	public ModelAndView index(){
		return  new ModelAndView("service/CasServiceList");
	}

	 /**
	  * 列表查询
	  * @param casService 过滤条件
	  * @return 查询列表
	  */
    @ApiOperation(value = "列表查询单点登录服务端信息",tags = {"单点登录服务端信息"})
    @GetMapping(value = "/casServices")
	@ResponseBody
    public Result<List<CasService>> queryList(CasService casService) {
        //构建查询
		LambdaQueryWrapper<CasService> queryWrapper = new LambdaQueryWrapper<CasService>();
		if(StringUtils.isNotBlank(casService.getId())){
			queryWrapper.eq(CasService::getId,casService.getId());
		}
		if(StringUtils.isNotBlank(casService.getName())){
			queryWrapper.like(CasService::getName,casService.getName());
		}
		if(StringUtils.isNotBlank(casService.getServiceid())){
			queryWrapper.like(CasService::getServiceid,casService.getServiceid());
		}
		if(StringUtils.isNotBlank(casService.getDescription())){
			queryWrapper.like(CasService::getDescription,casService.getDescription());
		}
		if(StringUtils.isNotBlank(casService.getStatus())){
			queryWrapper.eq(CasService::getStatus,casService.getStatus());
		}
		queryWrapper.orderByAsc(CasService::getEvaluationOrder);
        List<CasService> data = casServiceService.list(queryWrapper);
        return new Result<List<CasService>>().success("查询成功",data);
    }

	/**
	  *   新增单点登录服务端信息
	 * @param casService 存储实体
	 * @return 存储实体
	 */
	@ApiOperation(value = "新增单点登录服务端信息",tags = {"单点登录服务端信息"})
	@PostMapping(value ="/casServices")
	@ResponseBody
	public Result<CasService> save(@RequestBody CasService casService) {
		if(StringUtils.isBlank(casService.getId())){
			casService.setId(UUID.randomUUID().toString().replace("-",""));
		}
		casServiceService.save(casService);
		redisUtil.del(RedisPrefixConstant.CAS_SERVICE);
		 return new Result<CasService>().success("新增成功", casService);
	}

	/**
	  *  修改单点登录服务端信息
	 * @param casService 修改实体
	 * @return 修改实体
	 */
	@ApiOperation(value = "修改单点登录服务端信息",tags = {"单点登录服务端信息"})
	@PutMapping(value = "/casServices")
	@ResponseBody
	public Result<CasService> update(@RequestBody CasService casService) {
		if(casServiceService.updateById(casService)){
			redisUtil.del(RedisPrefixConstant.CAS_SERVICE);
		    return new Result<CasService>().success("修改成功",casService);
		} else {
			return new Result<CasService>().error(404,"未找到对应实体");
		}
	}

	/**
	  *   通过id删除单点登录服务端信息
	 * @param id 删除实体主键
	 * @return
	 */
	@ApiOperation(value = "删除单点登录服务端信息",tags = {"单点登录服务端信息"})
	@DeleteMapping(value ="/casServices/{id}")
	@ResponseBody
	public Result<CasService> delete(@PathVariable(name="id") String id) {
        //是否需要先判断依赖删除
		if(casServiceService.removeById(id)){
			redisUtil.del(RedisPrefixConstant.CAS_SERVICE);
			return new Result<CasService>().success("删除成功");
		} else {
			return new Result<CasService>().error(404,"删除失败:未找到对应实体");
		}
	}

	/**
	  * 通过id查询单点登录服务端信息
	 * @param id 查询实体主键
	 * @return 实体
	 */
	@ApiOperation(value = "通过id查询单点登录服务端信息",tags = {"单点登录服务端信息"})
	@GetMapping(value = "/casServices/{id}")
	@ResponseBody
	public Result<CasService> queryById(@PathVariable(name="id") String id) {
       CasService casService = casServiceService.getById(id);
        if (casService == null) {
            return new Result<CasService>().error(404, "该单点登录服务端信息不存在");
        } else {
            return new Result<CasService>().success("查询成功",casService);
        }
	}

}
