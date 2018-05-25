package com.conf.cache.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.conf.cache.common.dto.FlowRequestDto;
import com.conf.cache.common.dto.NodeRequestDto;

@RestController
@RequestMapping("/cache")
public class CacheController {

	/**
	 * 查询节点信息
	 * 
	 * @param dto
	 */
	@RequestMapping(value="queryNode", method=RequestMethod.POST)
	public void getNodeCache(@RequestBody NodeRequestDto dto) {
		
		
	}
	
	/**
	 * 查询流程信息
	 * 
	 * @param dto
	 */
	@RequestMapping(value="queryFlow", method=RequestMethod.POST)
	public void getFlowCache(@RequestBody FlowRequestDto dto) {
		
	}
	
	/**
	 * 更新缓存信息
	 * 
	 * @param dto
	 */
	@RequestMapping(value="update", method=RequestMethod.POST)
	public void updateCache(@RequestBody NodeRequestDto dto) {
		
	}
}
