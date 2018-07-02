package com.conf.client;

import com.bstek.urule.console.repository.RepositoryService;
import com.bstek.urule.console.repository.model.RepositoryFile;

/**
 * 
 * Urule接口调用
 * 
 * @author  lmx
 * @version  [版本号, 2018年6月30日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleInvokerService
{
    private RepositoryService repositoryService;
    
    public RepositoryService getRepositoryService()
    {
        return repositoryService;
    }

    public void setRepositoryService(RepositoryService repositoryService)
    {
        this.repositoryService = repositoryService;
    }

    /**
     * 校验节点是否存在
     * 
     * @param nodeName
     * @return
     * @see [类、类#方法、类#成员]
     */
    public boolean existCheck(String nodeName) {
        return true;
    }
    
    /**
     * 创建工程
     * 
     * @param projectName
     * @return
     * @throws Exception 
     * @see [类、类#方法、类#成员]
     */
    public boolean createProject(boolean classify, String projectName) throws Exception {
        //String projectName=req.getParameter("newProjectName");
        //projectName=Utils.decodeURL(projectName);
        //boolean classify = getClassify(req,resp);
        //User user=EnvironmentUtils.getLoginUser(new RequestContext(req,resp));
        RepositoryFile projectFileInfo=repositoryService.createProject(projectName,null,classify);
        return true;
    }
    
    /**
     * 删除工程、文件
     * 
     * @param path
     * @return
     * @see [类、类#方法、类#成员]
     */
    public boolean deleteFile(String path) {
        return true;
    }
    
    /**
     * 创建目录
     * 
     * @param classify
     * @param fullFolderName
     * @param projectName
     * @param types
     * @return
     * @see [类、类#方法、类#成员]
     */
    public boolean createFlolder(String classify, String fullFolderName, String projectName, String types) {
        return true;
    }
    
    /**
     * 创建文件
     * 
     * @param path
     * @param types
     * @return
     * @see [类、类#方法、类#成员]
     */
    public boolean createFile(String path, String types) {
        return true;
    }

    /**
     * 保存知识包
     * 
     * @param newVersion
     * @param projectName
     * @param xml
     * @return
     * @see [类、类#方法、类#成员]
     */
    public boolean saveResourcePackages(Boolean newVersion, String projectName, String xml) {
        return true;
    }
    
    /**
     * 自动发布知识包
     * 
     * @param files
     * @param packageId
     * @param project
     * @return
     * @see [类、类#方法、类#成员]
     */
    public boolean refreshKnowledgeCache(String files, String packageId, String project) {
        return true;
    }
}
