package com.ascendant.activiti;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qiaolin
 * @version 2018/10/22
 **/


@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitTest {


    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IdentityService identityService;


    @Test
    public void testService() {
        System.out.println(runtimeService);
        System.out.println(repositoryService);
        System.out.println(taskService);
    }

    /**
     *  测试部署流程
     */
    @Test
    public void testDeploymentProcess(){
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("bpmn/Leave.bpmn")
                .name("请假测试").deploy();
        Assert.assertNotNull("部署失败！", deploy);
        System.out.println("部署成功！ 部署Id ==> " + deploy.getId());
    }

    @Test
    public void testStartProcess(){
        Map<String, Object> var = new HashMap<>();
        var.put("startMark", "启动动了！");
        ProcessInstance leave = runtimeService.startProcessInstanceByKey("leave", var);
        Assert.assertNotNull(leave);
        System.out.println("启动完成！");
    }


    @Test
    public void testComplete(){
        Task task = taskService.createTaskQuery().taskCandidateOrAssigned("dipeng").singleResult();
        Assert.assertNotNull("任务不能为空！", task);
        Map<String, Object> var = new HashMap<>();
        var.put("myMark", "qiaolinLi");
        taskService.complete(task.getId(), var);
        System.out.println("任务完成！");
    }



}
