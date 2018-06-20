package com.ebitg.swagger.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ebitg.swagger.model.UserInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 人员信息控制类
 * 
 * @author XiaChengwei
 * @date: 2017-07-14 16:45:29
 */
@RestController
@RequestMapping(value = "/userInfo")
@Api(value = "用户信息", description = "用户信息")
public class UserInfoController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@ResponseBody
	@RequestMapping(value = "selectById", method = RequestMethod.GET)
	@ApiOperation(value = "根据id查询人员的所有信息", notes = "根据id查询人员的所有信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "人员信息id", required = true, paramType = "query") })
	public UserInfo selectById(Integer id) {
		return new UserInfo();
	}
}