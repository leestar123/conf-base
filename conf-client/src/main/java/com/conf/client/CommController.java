package com.conf.client;

import java.util.Map;

public interface CommController
{

    public Map<String, ? extends Object> createNode(Map<String, ? extends Object> data);
    
    public Map<String, ? extends Object> queryNodeList(Map<String, ? extends Object> data);
    
    public Map<String, ? extends Object> deleteNode(Map<String, ? extends Object> data);
    
    public Map<String, ? extends Object> queryRuleByNode(Map<String, ? extends Object> data);
    
    public Map<String, ? extends Object> queryRuleList(Map<String, ? extends Object> data);
    
    public Map<String, ? extends Object> addRuleByNode(Map<String, ? extends Object> data);
    
    public Map<String, ? extends Object> deleteRuleByNode(Map<String, ? extends Object> data);
    
    public Map<String, ? extends Object> queryNodeByProduct(Map<String, ? extends Object> data);
    
    public Map<String, ? extends Object> addNodeByProduct(Map<String, ? extends Object> data);
    
    public Map<String, ? extends Object> deleteNodeByProduct(Map<String, ? extends Object> data);
    
    public Map<String, ? extends Object> batchQueryNodeByProduct(Map<String, ? extends Object> data);
    
    public Map<String, ? extends Object> modifyNodeByProduct(Map<String, ? extends Object> data);
    
    public Map<String, ? extends Object> createRule(Map<String, ? extends Object> data);
    
    public Map<String, ? extends Object> ruleflowDesigner(Map<String, ? extends Object> data);
    
    public Map<String, ? extends Object> publishKnowledge(Map<String, ? extends Object> data);
    
    public Map<String, ? extends Object> excuteKnowledge(Map<String, ? extends Object> data);

}
