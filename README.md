# conf-base
全局配置中心，实现各种组件的可配置化
## 配置文件
*	application.properties
	*	urule.repository.dir:urule文件存储地方，事先需要建立
	*	scan.package.name：扫描包下所有含有@Rule注解的类，可通过逗号(,)扫描多个包
	*	urule.repository.databasetype：如果urule文件采用数据库方式存储，则需要配置。否则不需要
	*	urule.repository.datasourcename：配合 urule.repository.databasetype使用，配置数据源具体内容见application.yml
	*	urule.debugToFile：是否开启调用日志文件输出，true-开启，flase-关闭。
*	application.yml
	*	spring.datasource:配置对应的数据源信息
	*	server.port:配置访问端口
## 配置ActionBean
*	决策流中的动作Bean需要实现FlowAction接口
*	给类添加@Rule注解，注解包含以下属性
	*	name：Bean展示名称
	*	remark：注解说明
	*	verison：注解版本号，当版本号高于上一个版本号时，则重新加载
*	如果类中存在属性，则需要给属性添加注解@ActionParam
	*	desc：属性中文描述
	*	defaultValue：属性的默认值

## 规则组件
针对自定义的规则组件，实现针对规则组件中的规则可配置化。如果某一条规则执行失败则整个组件则执行完成。
### 实现原理
*   规则的定义
    *   创建类Class，并且定义各种方法，则各种方法即为可执行的规则。
        *   实现：通过在方法上添加@rule注解，实现规则的扫描
        *   优点：通过类的方法定义规则可实习规则处理的灵活性 
        *   缺点：必须事先定义好所有需要的规则。
        *   完善：后期考虑通过jar包方式在线导入新的规则，实现透明发布
*   组件定义
    *   创建类class，并且定义各种方法，则各种方法则即为可执行的组件
        *   实现：通过在方法上添加@confNode(type=rule)，实现规则组件的扫描
        *   优点：在组件执行完规则后，可自定义执行一段内嵌代码逻辑
        *   缺点：必须事先定义海鸥所有需要的组件
        *   完善：后期考虑通过jar包方式在线导入新的规则，实现透明发布
    *   创建类class，并且编写方法ruleMethod用于统一处理所有的规则组件
        *   实现：在界面自定义一个组件，并且规划组件的规则。根据定义好的组件逐一执行规则。
        *   优点：组件实现完全自定义，不需要实现定义好组件
        *   缺点：规则执行完成后无后续处理逻辑过程
        *   完善：实现抽象的方法，针对后续逻辑处理
*   客户端使用
    *   组件服务根据项目需要，是以HTTP、Dubbo、SpringCloud方式暴露服务自行选择