package com.ebitg.activiti;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.DynamicBpmnService;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oracle.net.aso.r;

public class ActivitiDemo {
	private static final Logger LOG = LoggerFactory.getLogger(ActivitiDemo.class);
	private ProcessEngine processEngine;
	private RuntimeService runtimeService;
	private RepositoryService repositoryService;
	private TaskService taskService;
	private ManagementService managementService;
	private IdentityService identityService;
	private HistoryService historyService;
	private FormService formService;
	private DynamicBpmnService dynamicBpmnService;

	@Before
	public void init() {
		processEngine = ProcessEngines.getDefaultProcessEngine();
		runtimeService = processEngine.getRuntimeService();
		repositoryService = processEngine.getRepositoryService();
		taskService = processEngine.getTaskService();
		managementService = processEngine.getManagementService();
		identityService = processEngine.getIdentityService();
		historyService = processEngine.getHistoryService();
		formService = processEngine.getFormService();
		dynamicBpmnService = processEngine.getDynamicBpmnService();
	}

	@Test
	public void test01() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("employeeName", "Kermit");
		variables.put("numberOfDays", new Integer(4));
		variables.put("vacationMotivation", "I'm really tired!");
		RuntimeService runtimeService = processEngine.getRuntimeService();

		ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
		List<ProcessInstance> list = processInstanceQuery.list();
		LOG.info("Number of process instances: " + runtimeService.createProcessInstanceQuery().count());
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("vacationRequest", variables);
	}

	public void test02() {
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
	}
}
