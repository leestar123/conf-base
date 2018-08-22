package org.conf.application.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlUtils {

	public static final String XML_ATTRIBUTE="attribute";
	public static final String XML_NAME="name";
	public static final String XML_CHILDREN="childrenNode";
	public static final String XML_DATA="text";
	
	//private static final String NODE_STRUCT="struct";
	private static final String NODE_FIELD="field";
	private static final String NODE_ARRAY="array";

	/**
	 * 将element转换成XML的Map的全量描述
	 * @param element
	 * @return
	 */
	public static Map<String,Object> convertElement2Map(Element element)
	{
		Map<String,Object> mapEle=new HashMap<>();

		//设置xml元素的节点名称
		mapEle.put(XML_NAME, element.getName());

		//筛选元素的属性值
		Map<String,String> mapAtr=new HashMap<>();
		for(Object objAtr:element.attributes())
		{
			if(objAtr instanceof Attribute)
			{
				mapAtr.put(((Attribute) objAtr).getName(), ((Attribute) objAtr).getValue());
			}
		}
		mapEle.put(XML_ATTRIBUTE, mapAtr);

		//执行元素下一层节点的遍历
		Map<String,Object> mapChild=new HashMap<>();
		List<Map<String,Object>> list=new ArrayList<>();
		for(Object obj:element.elements())
		{
			if(obj instanceof Element)
			{
				mapChild= convertElement2Map((Element) obj);
				list.add(mapChild);
			}
		}
		mapEle.put(XML_CHILDREN, list);

		//取出单节点的文本值
		mapEle.put(XML_DATA, element.getText());
		return mapEle;
	}
	
	/**
	 * 将Map转换成Element的全量描述
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Element convertMap2Element(Map<String,Object> map)
	{
		//根据节点名称生成根节点
		Element root = DocumentHelper.createElement((String) map.get(XML_NAME));
		
		//根据属性生成属性值
		Map<String,String> mapAttr=(Map<String, String>) map.get(XML_ATTRIBUTE);
		for(Entry<String, String> entry : mapAttr.entrySet())
		{
			root.addAttribute(entry.getKey(), entry.getValue());
		}
		
		//设置子节点的层级
		List<Map<String,Object>> list=(ArrayList<Map<String, Object>>) map.get(XML_CHILDREN);
		for(Map<String,Object> objList:list)
		{
			root.add(convertMap2Element(objList));
		}
		
		//设置节点的文本值
		root.addText((String) map.get(XML_DATA));
		return root;
		
	}

	/**
	 * 将String型XML转换成Element
	 * @param xml
	 * @return
	 * @throws DocumentException
	 */
	public static Element convertStringXml2Element(String xml) throws DocumentException
	{
		Document document=DocumentHelper.parseText(xml);
		return document.getRootElement();
	}

	/**
	 * 通过传入sys-header，app-header，local-header，body等标志，产生相应的map简化描述（台州ESB专用）
	 * @param element
	 * @return
	 * @throws DocumentException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String,Object> getHeaderDoc2MapMini(String flag,String xml) throws DocumentException
	{
		Map<String,Object> retMap=new HashMap<>();

		Map<String,Object> mapRoot=convertElement2Map(convertStringXml2Element(xml));
		List<Map<String,Object>> listSysHeader=(ArrayList) mapRoot.get(XML_CHILDREN);
		for(Map<String,Object> mapHeader:listSysHeader)
		{
			if(flag.equals(mapHeader.get(XML_NAME)))
			{
				List<Map<String,Object>> dataNode=(List<Map<String, Object>>) mapHeader.get(XML_CHILDREN);
				retMap.put(flag, operHeadDataNode(dataNode.get(0)));
			}
		}
		return retMap;
	}
	
	
	/**
	 * 将由XML解析出来的map转换成需要处理的Map
	 * @param dataNode  XML转换出来的原始map
	 * @param retMap 代码需要返回的map
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map<String,Object> operHeadDataNode(Map<String,Object> dataNode)
	{
		Map<String,Object> retMap =new HashMap<>();
		Map<String, String> dataNodeAttr= (Map<String, String>) dataNode.get(XML_ATTRIBUTE);
		List<Map<String,Object>> listData=(ArrayList) dataNode.get(XML_CHILDREN);
		Map<String,Object> struct=listData.get(0);
		String key=dataNodeAttr.get("name");
		retMap.put(key, operStructNode(struct));
		return retMap;
		
	}
	
	/**
	 * 对struct节点进行操作
	 * @param structNode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static Map<String,Object> operStructNode(Map<String,Object> structNode)
	{
		Map<String,Object> retMap =new HashMap<>();
		List<Map<String,Object>> list=(List<Map<String, Object>>) structNode.get(XML_CHILDREN);
		for(Map<String,Object> map:list)    //此处的Map是二级打他节点
		{
			List<Map<String,Object>> listDataChild= (List<Map<String, Object>>) map.get(XML_CHILDREN);
			String key=(String) ((Map<String,Object>) map.get(XML_ATTRIBUTE)).get("name");
			Map<String,Object> dataChild=listDataChild.get(0);
			if(dataChild.get(XML_NAME).equals(NODE_FIELD))
			{
				retMap.put(key, dataChild.get(XML_DATA));
			}
			else if(dataChild.get(XML_NAME).equals(NODE_ARRAY))
			{
				retMap.put(key, operArrayNode(listDataChild));
			}
		}
		return retMap;
	}
	
	/**
	 * 对array节点进行操作
	 * @param arrayNode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<Map<String,Object>> operArrayNode(List<Map<String,Object>> arrayNodeList)
	{
		List<Map<String,Object>> retList=new ArrayList<>();
		for(Map<String,Object> arrayNode:arrayNodeList)
		{
			List<Map<String,Object>> list=(List<Map<String, Object>>) arrayNode.get(XML_CHILDREN);
			for(Map<String,Object> map:list)
			{
				retList.add(operStructNode(map));
			}
		}
		return retList;
	}
	

	public static String setHeaderMap2DocMini(String flag,Map<String,String> map)
	{
		Document doc = DocumentHelper.createDocument();
		Element service = doc.addElement("service");
		Element header = service.addElement(flag);
		Element data = header.addElement("data");
		switch(flag){
		case "sys-header":
			data.addAttribute("name", "SYS_HEAD");
			break;
		case "app-header":
			data.addAttribute("name", "APP_HEAD");
			break;
		case "local-header":
			data.addAttribute("name", "LOCAL_HEAD");
			break;
		}
		Element struct = data.addElement("struct");

		for (Entry<String, String> entry : map.entrySet()) {
			Element childEle=struct.addElement("data");
			childEle.addAttribute("name", entry.getKey());
			Element field=childEle.addElement("field");
			field.addAttribute("type", "string");
			field.addAttribute("length", "2");
			field.addAttribute("scale", "0");
			field.addText(entry.getValue());
		}
		System.out.println(doc.asXML());
		return doc.asXML();
	}
}
