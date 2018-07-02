package com.conf.client;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.bstek.urule.console.EnvironmentUtils;
import com.bstek.urule.console.User;
import com.bstek.urule.console.repository.RepositoryService;
import com.bstek.urule.console.servlet.RequestContext;
import com.bstek.urule.console.servlet.RequestHolder;

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
    private static final String CLASSIFY_COOKIE_NAME="_lib_classify";
    
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
    public void createProject(String projectName) throws Exception {
        HttpServletRequest req = RequestHolder.getRequest();
        HttpServletResponse resp = RequestHolder.getResponse();
        boolean classify = getClassify(req,resp);
        User user=EnvironmentUtils.getLoginUser(new RequestContext(req,resp));
        repositoryService.createProject(projectName,user,classify);
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
    
    private boolean getClassify(HttpServletRequest req,HttpServletResponse resp) {
        String classifyValue=req.getParameter("classify");
        if(StringUtils.isBlank(classifyValue)){
            Cookie[] cookies=req.getCookies();
            if(cookies!=null){              
                for(Cookie cookie:cookies){
                    if(CLASSIFY_COOKIE_NAME.equals(cookie.getName())){
                        classifyValue=cookie.getValue();
                        break;
                    }
                }
            }
        }else{
            Cookie classifyCookie=new Cookie(CLASSIFY_COOKIE_NAME,classifyValue);
            classifyCookie.setMaxAge(2100000000);
            resp.addCookie(classifyCookie);
        }
        boolean classify=true;
        if(StringUtils.isNotBlank(classifyValue)){
            classify=Boolean.valueOf(classifyValue);
        }
        return classify;
    }
}
