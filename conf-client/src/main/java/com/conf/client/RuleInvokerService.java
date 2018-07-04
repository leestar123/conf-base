package com.conf.client;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.bstek.urule.Utils;
import com.bstek.urule.console.EnvironmentUtils;
import com.bstek.urule.console.User;
import com.bstek.urule.console.repository.RepositoryService;
import com.bstek.urule.console.repository.RepositoryServiceImpl;
import com.bstek.urule.console.repository.model.FileType;
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
     * @throws Exception 
     * @see [类、类#方法、类#成员]
     */
    public boolean existCheck(String nodeName) throws Exception {
        return repositoryService.fileExistCheck(nodeName);
    }
    
    /**
     * 校验规则是否存在
     * 
     * @param nodeName
     * @return
     * @throws Exception 
     * @see [类、类#方法、类#成员]
     */
    public boolean fileExistCheck(String ruleName) throws Exception {
        return repositoryService.fileExistCheck(ruleName);
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
     * @throws Exception 
     * @see [类、类#方法、类#成员]
     */
    public void deleteFile(String path) throws Exception {
    	HttpServletRequest req = RequestHolder.getRequest();
        HttpServletResponse resp = RequestHolder.getResponse();
        User user=EnvironmentUtils.getLoginUser(new RequestContext(req,resp));
        repositoryService.deleteFile(path,user);
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
    public boolean createFlolder(String fullFolderName, String projectName, String types) {
        HttpServletRequest req = RequestHolder.getRequest();
        HttpServletResponse resp = RequestHolder.getResponse();
        boolean classify = getClassify(req,resp);
        return true;
    }
    
    /**
     * 创建文件
     * 
     * @param path
     * @param types
     * @return
     * @throws Exception 
     * @see [类、类#方法、类#成员]
     */
    public void createFile(String path, String types) throws Exception {
        FileType fileType=FileType.parse(types);
        StringBuilder content = generateContext(fileType);
        HttpServletRequest req = RequestHolder.getRequest();
        HttpServletResponse resp = RequestHolder.getResponse();
        User user=EnvironmentUtils.getLoginUser(new RequestContext(req,resp));
        repositoryService.createFile(path, content.toString(),user);
    }

    /**
     * 保存知识包
     * 
     * @param newVersion
     * @param projectName
     * @param xml
     * @return
     * @throws Exception 
     * @see [类、类#方法、类#成员]
     */
    public void saveResourcePackages(Boolean newVersion, String projectName, String xml) throws Exception {
    	String path=projectName+"/"+"___res__package__file__";
        HttpServletRequest req = RequestHolder.getRequest();
        HttpServletResponse resp = RequestHolder.getResponse();
        User user=EnvironmentUtils.getLoginUser(new RequestContext(req,resp));
		repositoryService.saveFile(path, xml, false,null,user);

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
    
    /**
     * 文件拷贝
     * 
     * @param newFullPath
     * @param oldFullPath
     * @throws Exception 
     * @see [类、类#方法、类#成员]
     */
    public void copyFile(String newFullPath, String oldFullPath) throws Exception {
    	newFullPath=Utils.decodeURL(newFullPath);
		oldFullPath=Utils.decodeURL(oldFullPath);
		HttpServletRequest req = RequestHolder.getRequest();
        HttpServletResponse resp = RequestHolder.getResponse();
        User user=EnvironmentUtils.getLoginUser(new RequestContext(req,resp));
        InputStream inputStream=repositoryService.readFile(oldFullPath, null);
		String content=IOUtils.toString(inputStream, "utf-8");
		inputStream.close();
		repositoryService.createFile(newFullPath, content,user);
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
    
    public  StringBuilder generateContext(FileType fileType)
    {
        StringBuilder content=new StringBuilder();
        if(fileType.equals(FileType.UL)) {
            content.append("rule \"rule01\"");
            content.append("\n");
            content.append("if");
            content.append("\r\n");
            content.append("then");
            content.append("\r\n");
            content.append("end");
        }else if(fileType.equals(FileType.DecisionTable)) {
            content.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            content.append("<decision-table>");
            content.append("<cell row=\"0\" col=\"2\" rowspan=\"1\"></cell>");
            content.append("<cell row=\"0\" col=\"1\" rowspan=\"1\">");
            content.append("<joint type=\"and\"/>");
            content.append("</cell>");
            content.append("<cell row=\"0\" col=\"0\" rowspan=\"1\">");
            content.append("<joint type=\"and\"/>");
            content.append("</cell>");
            content.append("<cell row=\"1\" col=\"2\" rowspan=\"1\">");
            content.append("</cell>");
            content.append("<cell row=\"1\" col=\"1\" rowspan=\"1\">");
            content.append("<joint type=\"and\"/>");
            content.append("</cell>");
            content.append("<cell row=\"1\" col=\"0\" rowspan=\"1\">");
            content.append("<joint type=\"and\"/>");
            content.append("</cell>");
            content.append("<row num=\"0\" height=\"40\"/>");
            content.append("<row num=\"1\" height=\"40\"/>");
            content.append("<col num=\"0\" width=\"120\" type=\"Criteria\"/>");
            content.append("<col num=\"1\" width=\"120\" type=\"Criteria\"/>");
            content.append("<col num=\"2\" width=\"200\" type=\"Assignment\"/>");
            content.append("</decision-table>");
        }else if(fileType.equals(FileType.DecisionTree)){
            content.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            content.append("<decision-tree>");
            content.append("<variable-tree-node></variable-tree-node>");
            content.append("</decision-tree>");
        }else if(fileType.equals(FileType.ScriptDecisionTable)) {
            content.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            content.append("<script-decision-table>");
            content.append("<script-cell row=\"0\" col=\"2\" rowspan=\"1\"></script-cell>");
            content.append("<script-cell row=\"0\" col=\"1\" rowspan=\"1\"></script-cell>");
            content.append("<script-cell row=\"0\" col=\"0\" rowspan=\"1\"></script-cell>");
            content.append("<script-cell row=\"1\" col=\"2\" rowspan=\"1\"></script-cell>");
            content.append("<script-cell row=\"1\" col=\"1\" rowspan=\"1\"></script-cell>");
            content.append("<script-cell row=\"1\" col=\"0\" rowspan=\"1\"></script-cell>");
            content.append("<row num=\"0\" height=\"40\"/>");
            content.append("<row num=\"1\" height=\"40\"/>");
            content.append("<col num=\"0\" width=\"120\" type=\"Criteria\"/>");
            content.append("<col num=\"1\" width=\"120\" type=\"Criteria\"/>");
            content.append("<col num=\"2\" width=\"200\" type=\"Assignment\"/>");
            content.append("</script-decision-table>");
        }else if(fileType.equals(FileType.Scorecard)){
            content.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            content.append("<scorecard scoring-type=\"sum\" assign-target-type=\"none\">");
            content.append("</scorecard>");
        }else{
            String name = getRootTagName(fileType);
            content.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            content.append("<" + name + ">");
            content.append("</" + name + ">");
        }
        return content;
    }
    
    private  String getRootTagName(FileType type)
    {
        String root = null;
        switch(type){
        case ActionLibrary:
            root="action-library";
            break;
        case ConstantLibrary:
            root="constant-library";
            break;
        case DecisionTable:
            root="decision-table";
            break;
        case DecisionTree:
            root="decision-tree";
            break;
        case ParameterLibrary:
            root="parameter-library";
            break;
        case RuleFlow:
            root="rule-flow";
            break;
        case Ruleset:
            root="rule-set";
            break;
        case ScriptDecisionTable:
            root="script-decision-table";
            break;
        case VariableLibrary:
            root="variable-library";
            break;
        case UL:
            root="script";
            break;
        case Scorecard:
            root="scorecard";
            break;
        case DIR:
            throw new IllegalArgumentException("Unsupport filetype : "+type);
        }
        return root;
    }
    
    public  StringBuilder generateRLXML(String id,String packageName,String fileName,String path)
    {
    	StringBuilder content=new StringBuilder();
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String formatStr =formatter.format(new Date());
    	content.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        content.append("<res-packages>");
        content.append("<res-package  id='"+id+"' name='"+packageName+"' create_date='"+formatStr+"'>");
        content.append("<res-package-item  name='"+fileName+"' path='jcr:"+path+"' version='LATEST'/>");
        content.append("</res-package></res-packages>");
    	return content;
    }
}
