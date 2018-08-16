package com.conf.client;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bstek.urule.Utils;
import com.bstek.urule.builder.KnowledgeBase;
import com.bstek.urule.builder.KnowledgeBuilder;
import com.bstek.urule.builder.ResourceBase;
import com.bstek.urule.console.EnvironmentUtils;
import com.bstek.urule.console.User;
import com.bstek.urule.console.repository.Repository;
import com.bstek.urule.console.repository.RepositoryService;
import com.bstek.urule.console.repository.RepositoryServiceImpl;
import com.bstek.urule.console.repository.model.FileType;
import com.bstek.urule.console.repository.model.RepositoryFile;
import com.bstek.urule.console.repository.model.ResourceItem;
import com.bstek.urule.console.repository.model.ResourcePackage;
import com.bstek.urule.console.servlet.RequestContext;
import com.bstek.urule.console.servlet.RequestHolder;
import com.bstek.urule.console.servlet.respackage.HttpSessionKnowledgeCache;
import com.bstek.urule.model.GeneralEntity;
import com.bstek.urule.runtime.KnowledgePackage;
import com.bstek.urule.runtime.KnowledgeSession;
import com.bstek.urule.runtime.KnowledgeSessionFactory;
import com.bstek.urule.runtime.KnowledgeSessionImpl;
import com.bstek.urule.runtime.cache.CacheUtils;
import com.bstek.urule.runtime.event.KnowledgeEventListener;
import com.bstek.urule.runtime.rete.Context;
import com.bstek.urule.runtime.service.KnowledgeService;
import com.conf.common.ConfContext;
import com.conf.common.dto.BuildXMlDto;

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
	private final static Logger logger = LoggerFactory.getLogger(RuleInvokerService.class);
	    
    private static final String CLASSIFY_COOKIE_NAME = "_lib_classify";
    
    public static final String KB_KEY = "_kb";
    
    public static final String VCS_KEY = "_vcs";
    
    private RepositoryService repositoryService;
    
    private KnowledgeBuilder knowledgeBuilder;
    
    private HttpSessionKnowledgeCache httpSessionKnowledgeCache;
    
    private KnowledgeService knowledgeService;
    
    private KnowledgeEventListener knowledgeEventListener;
    
    public void setKnowledgeEventListener(KnowledgeEventListener knowledgeEventListener) {
		this.knowledgeEventListener = knowledgeEventListener;
	}

	/**
     * 校验节点是否存在
     * 
     * @param nodeName
     * @return
     * @throws Exception 
     * @see [类、类#方法、类#成员]
     */
    public boolean existCheck(String nodeName)
        throws Exception
    {
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
    public boolean fileExistCheck(String ruleName)
        throws Exception
    {
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
    public void createProject(String projectName)
        throws Exception
    {
        HttpServletRequest req = RequestHolder.getRequest();
        HttpServletResponse resp = RequestHolder.getResponse();
        boolean classify = getClassify(req, resp);
        User user = EnvironmentUtils.getLoginUser(new RequestContext(req, resp));
        repositoryService.createProject(projectName, user, classify);
    }
    
    /**
     * 删除工程、文件
     * 
     * @param path
     * @return
     * @throws Exception 
     * @see [类、类#方法、类#成员]
     */
    public void deleteFile(String path)
        throws Exception
    {
        HttpServletRequest req = RequestHolder.getRequest();
        HttpServletResponse resp = RequestHolder.getResponse();
        User user = EnvironmentUtils.getLoginUser(new RequestContext(req, resp));
        repositoryService.deleteFile(path, user);
    }
    
    /**
     * 创建目录
     * 
     * @param classify
     * @param fullFolderName
     * @param projectName
     * @param types
     * @return
     * @throws Exception 
     * @see [类、类#方法、类#成员]
     */
    public void createFlolder(String fullFolderName) throws Exception
    {
        HttpServletRequest req = RequestHolder.getRequest();
        HttpServletResponse resp = RequestHolder.getResponse();
        fullFolderName=Utils.decodeURL(fullFolderName);
        User user=EnvironmentUtils.getLoginUser(new RequestContext(req,resp));
        repositoryService.createDir(fullFolderName, user);
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
    public void createFile(String path, String types)
        throws Exception
    {
        FileType fileType = FileType.parse(types);
        StringBuilder content = generateContext(fileType);
        HttpServletRequest req = RequestHolder.getRequest();
        HttpServletResponse resp = RequestHolder.getResponse();
        User user = EnvironmentUtils.getLoginUser(new RequestContext(req, resp));
        repositoryService.createFile(path, content.toString(), user);
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
    public void saveResourcePackages(String projectName, String xml)
        throws Exception
    {
        String path = projectName + "/" + RepositoryServiceImpl.RES_PACKGE_FILE;
        HttpServletRequest req = RequestHolder.getRequest();
        HttpServletResponse resp = RequestHolder.getResponse();
        User user = EnvironmentUtils.getLoginUser(new RequestContext(req, resp));
        repositoryService.saveFile(path, xml, false, null, user);
        
    }
    
    /**
     * 保存文件内容
     * 
     * @param newVersion
     * @param projectName
     * @param xml
     * @return
     * @throws Exception 
     * @see [类、类#方法、类#成员]
     */
    public void saveFile(String path, String xml)
        throws Exception
    {
        HttpServletRequest req = RequestHolder.getRequest();
        HttpServletResponse resp = RequestHolder.getResponse();
        User user = EnvironmentUtils.getLoginUser(new RequestContext(req, resp));
        repositoryService.saveFile(path, xml, false, null, user);
    }
    
    /**
     * 自动发布知识包
     * 
     * @param files
     * @param packageId
     * @param project
     * @return
     * @throws Exception 
     * @see [类、类#方法、类#成员]
     */
    public void refreshKnowledgeCache(String files, String packageId, String project)
        throws Exception
    {
        String path = project + "/" + packageId;
        HttpServletRequest req = RequestHolder.getRequest();
        KnowledgeBase knowledgeBase = buildKnowledgeBase(req, files);
        KnowledgePackage knowledgePackage = knowledgeBase.getKnowledgePackage();
        CacheUtils.getKnowledgeCache().putKnowledge(path, knowledgePackage);
        repositoryService.loadClientConfigs(project);
    }
    
    /**
     * 文件拷贝
     * 
     * @param newFullPath
     * @param oldFullPath
     * @throws Exception 
     * @see [类、类#方法、类#成员]
     */
    public void copyFile(String newFullPath, String oldFullPath)
        throws Exception
    {
        newFullPath = Utils.decodeURL(newFullPath);
        oldFullPath = Utils.decodeURL(oldFullPath);
        HttpServletRequest req = RequestHolder.getRequest();
        HttpServletResponse resp = RequestHolder.getResponse();
        User user = EnvironmentUtils.getLoginUser(new RequestContext(req, resp));
        InputStream inputStream = repositoryService.readFile(oldFullPath, null);
        String content = IOUtils.toString(inputStream, "utf-8");
        inputStream.close();
        repositoryService.createFile(newFullPath, content, user);
    }
    
    /**
     * 知识包的调用
     * @param listener 
     * @param objList
     * @param objListUnCheck
     * @param processId
     * @throws Exception
     */
	public Map<String, Object> executeProcess(String files, List<GeneralEntity> objList, String processId)
        throws Exception
	{
		HttpServletRequest req = RequestHolder.getRequest();
		logger.debug("Begin to build KonwledgeBase");
		KnowledgeBase knowledgeBase = (KnowledgeBase) httpSessionKnowledgeCache.get(req, KB_KEY);
		if (knowledgeBase == null) {
			knowledgeBase = buildKnowledgeBase(req, files);
		}
		logger.debug("End to build Konwledge");
		KnowledgePackage knowledgePackage = knowledgeBase.getKnowledgePackage();
		// knowledgePackage = knowledgeService.getKnowledge(packageId);
		KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);
		if (session instanceof KnowledgeSessionImpl) 
		{
			//添加上下文
			Context context= ((KnowledgeSessionImpl) session).getContext();
			ConfContext.setContext(context);
		}
		session.addEventListener(knowledgeEventListener);
		for (Object objNeedChecked : objList) {
			session.insert(objNeedChecked);
		}
		session.startProcess(processId);
		session.writeLogFile();
		return session.getParameters();
	}
    
    private boolean getClassify(HttpServletRequest req, HttpServletResponse resp)
    {
    	if (req == null) {
    		return true;
    	}
        String classifyValue = req.getParameter("classify");
        if (StringUtils.isBlank(classifyValue))
        {
            Cookie[] cookies = req.getCookies();
            if (cookies != null)
            {
                for (Cookie cookie : cookies)
                {
                    if (CLASSIFY_COOKIE_NAME.equals(cookie.getName()))
                    {
                        classifyValue = cookie.getValue();
                        break;
                    }
                }
            }
        }
        else
        {
            Cookie classifyCookie = new Cookie(CLASSIFY_COOKIE_NAME, classifyValue);
            classifyCookie.setMaxAge(2100000000);
            resp.addCookie(classifyCookie);
        }
        boolean classify = true;
        if (StringUtils.isNotBlank(classifyValue))
        {
            classify = Boolean.valueOf(classifyValue);
        }
        return classify;
    }
    
    public StringBuilder generateContext(FileType fileType)
    {
        StringBuilder content = new StringBuilder();
        if (fileType.equals(FileType.UL))
        {
            content.append("rule \"rule01\"");
            content.append("\n");
            content.append("if");
            content.append("\r\n");
            content.append("then");
            content.append("\r\n");
            content.append("end");
        }
        else if (fileType.equals(FileType.DecisionTable))
        {
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
        }
        else if (fileType.equals(FileType.DecisionTree))
        {
            content.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            content.append("<decision-tree>");
            content.append("<variable-tree-node></variable-tree-node>");
            content.append("</decision-tree>");
        }
        else if (fileType.equals(FileType.ScriptDecisionTable))
        {
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
        }
        else if (fileType.equals(FileType.Scorecard))
        {
            content.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            content.append("<scorecard scoring-type=\"sum\" assign-target-type=\"none\">");
            content.append("</scorecard>");
        }
        else
        {
            String name = getRootTagName(fileType);
            content.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            content.append("<" + name + ">");
            content.append("</" + name + ">");
        }
        return content;
    }
    
    private String getRootTagName(FileType type)
    {
        String root = null;
        switch (type)
        {
            case ActionLibrary:
                root = "action-library";
                break;
            case ConstantLibrary:
                root = "constant-library";
                break;
            case DecisionTable:
                root = "decision-table";
                break;
            case DecisionTree:
                root = "decision-tree";
                break;
            case ParameterLibrary:
                root = "parameter-library";
                break;
            case RuleFlow:
                root = "rule-flow";
                break;
            case Ruleset:
                root = "rule-set";
                break;
            case ScriptDecisionTable:
                root = "script-decision-table";
                break;
            case VariableLibrary:
                root = "variable-library";
                break;
            case UL:
                root = "script";
                break;
            case Scorecard:
                root = "scorecard";
                break;
            case DIR:
                throw new IllegalArgumentException("Unsupport filetype : " + type);
        }
        return root;
    }
    
    public List<ResourcePackage> loadProjectResourcePackages(String project) throws Exception {
        return repositoryService.loadProjectResourcePackages("/" + project); 
    }
    
    /**
     * 构建知识包对象
     * 
     * @param bind 包含productId、processId、flowPath、nodeName字段
     * @param packages
     */
	public void generateRLXML(List<BuildXMlDto> bind, List<ResourcePackage> packages) {
		for (BuildXMlDto dto : bind) {
			generateRLXML("/" + dto.getNodeName(), dto.getProductId(), dto.getFlowId(), dto.getPath(), packages);
		}
	}
    
    public void generateRLXML(String project, String id, String flowId, String flowPath, List<ResourcePackage> packages)
    {
        if (StringUtils.isBlank(id) || StringUtils.isBlank(flowId))
            return;
        boolean flag = false;
        for (ResourcePackage resourcePackage : packages)
        {
            if (resourcePackage.getId().equals(id) && resourcePackage.getProject().equals(project))
            {
                flag = true;
                boolean subFlag = false;
                for (ResourceItem item : resourcePackage.getResourceItems())
                {
                    if (item.getPackageId().equals(id))
                    {
                        subFlag = true;
                        break;
                    }
                }
                if (!subFlag)
                {
                    ResourceItem item = new ResourceItem();
                    item.setPackageId(id);
                    item.setName(flowId);
                    item.setPath("jcr:" + flowPath);
                    item.setVersion("LATEST");
                    resourcePackage.getResourceItems().add(item);
                }
                break;
            }
        }
        if (!flag)
        {
            ResourcePackage newPackage = new ResourcePackage();
            newPackage.setCreateDate(new Date());
            newPackage.setId(id);
            newPackage.setName(id);
            newPackage.setProject(project);
            List<ResourceItem> listItem = new ArrayList<>();
            ResourceItem item = new ResourceItem();
            item.setPackageId(id);
            item.setName(flowId);
            item.setPath("jcr:" + flowPath);
            item.setVersion("LATEST");
            listItem.add(item);
            newPackage.setResourceItems(listItem);
            packages.add(newPackage);
        }
    }
    
    public StringBuilder buildXML(List<ResourcePackage> packages) 
    {
        StringBuilder content = new StringBuilder();
        content.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        content.append("<res-packages>");
        for (ResourcePackage resourcePackage : packages)
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = formatter.format(resourcePackage.getCreateDate());
            content.append("<res-package  id='" + resourcePackage.getId() + "' name='" + resourcePackage.getName()
            + "' create_date='" + date + "'>");
            for (ResourceItem item : resourcePackage.getResourceItems())
            {
                content.append("<res-package-item  name='" + item.getName() + "' path='" + item.getPath()
                    + "' version='" + item.getVersion() + "'/>");
            }
            content.append("</res-package>");
        }
        content.append("</res-packages>");
        return content;
    }
    
    private KnowledgeBase buildKnowledgeBase(HttpServletRequest req, String files)
        throws IOException
    {
        files = Utils.decodeURL(files);
        ResourceBase resourceBase = knowledgeBuilder.newResourceBase();
        String[] paths = files.split(";");
        for (String path : paths)
        {
            String[] subpaths = path.split(",");
            path = subpaths[0];
            String version = null;
            if (subpaths.length > 1)
            {
                version = subpaths[1];
            }
            resourceBase.addResource(path, version);
        }
        KnowledgeBase knowledgeBase = knowledgeBuilder.buildKnowledgeBase(resourceBase);
        httpSessionKnowledgeCache.remove(req, KB_KEY);
        httpSessionKnowledgeCache.put(req, KB_KEY, knowledgeBase);
        return knowledgeBase;
    }
    
    /**
     * 获取流程文件对象
     * 
     * @param path
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public Document getFileSource(String path)
        throws Exception
    {
        InputStream inputStream = null;
        path = Utils.decodeURL(path);
        try
        {
            inputStream = repositoryService.readFile(path, null);
            String content = IOUtils.toString(inputStream, "utf-8");
            return DocumentHelper.parseText(content);
        }
        finally
        {
            if (inputStream != null)
                inputStream.close();
        }
    }
    
    public KnowledgeBuilder getKnowledgeBuilder()
    {
        return knowledgeBuilder;
    }
    
    public void setKnowledgeBuilder(KnowledgeBuilder knowledgeBuilder)
    {
        this.knowledgeBuilder = knowledgeBuilder;
    }
    
    public HttpSessionKnowledgeCache getHttpSessionKnowledgeCache()
    {
        return httpSessionKnowledgeCache;
    }
    
    public void setHttpSessionKnowledgeCache(HttpSessionKnowledgeCache httpSessionKnowledgeCache)
    {
        this.httpSessionKnowledgeCache = httpSessionKnowledgeCache;
    }
    
    public RepositoryService getRepositoryService()
    {
        return repositoryService;
    }
    
    public void setRepositoryService(RepositoryService repositoryService)
    {
        this.repositoryService = repositoryService;
    }
    
    public KnowledgeService getKnowledgeService()
    {
        return knowledgeService;
    }
    
    public void setKnowledgeService(KnowledgeService knowledgeService)
    {
        this.knowledgeService = knowledgeService;
    }
    
    /**
     * 
     * <一句话功能简述>
     * <功能详细描述>
     * @param projectName
     * @param types
     * @param searchFileName
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public List<String> getDirectories(String projectName, FileType[] types, String searchFileName)
        throws Exception
    {
        HttpServletRequest req = RequestHolder.getRequest();
        HttpServletResponse resp = RequestHolder.getResponse();
        boolean classify = getClassify(req, resp);
        User user = EnvironmentUtils.getLoginUser(new RequestContext(req, resp));
        Repository repo = repositoryService.loadRepository(projectName, user, classify, types, searchFileName);
        List<String> fileList = new ArrayList<>();
        List<String> result = new ArrayList<>();
        RepositoryFile root = repo.getRootFile();
        paraseRepo(root, fileList);
        for (String file : fileList)
        {
            for (FileType type : types)
            {
                if (file.endsWith(type.toString()))
                {
                    result.add(file);
                    break;
                }
            }
        }
        return result;
    }
    
    /**
     * 解析获取文件列表
     * 
     * @param root
     * @param pathList
     * @see [类、类#方法、类#成员]
     */
    public void paraseRepo (RepositoryFile root, List<String> pathList) {
        if (root.getChildren() != null && root.getChildren().size() > 0) {
            for (RepositoryFile file : root.getChildren())
            {
                paraseRepo(file, pathList);
            }
        } else {
            String fullPath = root.getFullPath();
            pathList.add(fullPath);
        }
    }
    
}
