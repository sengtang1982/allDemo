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
 * ��Ա��Ϣ������
 * 
 * @author XiaChengwei
 * @date: 2017-07-14 16:45:29
 */
@RestController
@RequestMapping(value = "/userInfo")
@Api(value = "�û���Ϣ", description = "�û���Ϣ")
public class UserInfoController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@ResponseBody
	@RequestMapping(value = "selectById", method = RequestMethod.GET)
	@ApiOperation(value = "����id��ѯ��Ա��������Ϣ", notes = "����id��ѯ��Ա��������Ϣ")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "��Ա��Ϣid", required = true, paramType = "query") })
	public UserInfo selectById(Integer id) {
		return new UserInfo();
	}
}