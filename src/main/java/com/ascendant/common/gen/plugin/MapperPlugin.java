package com.ascendant.common.gen.plugin;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.xml.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.ascendant.common.constants.SystemConstants;

/**
 * 生成 Mapper 类
 *  
 */
@SuppressWarnings("unused")
public class MapperPlugin extends PluginAdapter {

	private static final String DEFAULT_DAO_SUPER_CLASS = "BaseMapper"; 
	private static final String DEFAULT_DAO_SUPER_CLASS_Package = "com.ascendant.core.mapper.BaseMapper"; 

	private String daoTargetDir;
	private String daoTargetPackage;

	private String superMapper;
 
	private String expandDaoSuperClass;

	private ShellCallback shellCallback = null;

	public MapperPlugin() {
		shellCallback = new DefaultShellCallback(false);
	}



	public boolean validate(List<String> warnings) {
		daoTargetDir = properties.getProperty("targetProject");
		boolean valid = stringHasValue(daoTargetDir);

		daoTargetPackage = properties.getProperty("targetPackage");
		boolean valid2 = stringHasValue(daoTargetPackage);

		superMapper = properties.getProperty("superMapper");
		if (!stringHasValue(superMapper)) {
			superMapper = DEFAULT_DAO_SUPER_CLASS;
		}

		return valid && valid2;
	}

	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
		JavaFormatter javaFormatter = context.getJavaFormatter();
		List<GeneratedJavaFile> mapperJavaFiles = new ArrayList<GeneratedJavaFile>();
		for (GeneratedJavaFile javaFile : introspectedTable.getGeneratedJavaFiles()) {
			CompilationUnit unit = javaFile.getCompilationUnit();
			FullyQualifiedJavaType baseModelJavaType = unit.getType();

			String shortName = baseModelJavaType.getShortName();

			GeneratedJavaFile mapperJavafile = null;
			// CRUD Mapper
			Interface mapperInterface = new Interface(daoTargetPackage + "." + shortName + "Mapper");

			mapperInterface.setVisibility(JavaVisibility.PUBLIC);
			mapperInterface.addJavaDocLine("/**");
			mapperInterface.addJavaDocLine(" * @author "+ SystemConstants.USERNAME);
			mapperInterface.addJavaDocLine(" * @date "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			mapperInterface.addJavaDocLine(" */");
			mapperInterface.addJavaDocLine("");

			FullyQualifiedJavaType daoSuperType = new FullyQualifiedJavaType(superMapper);
			// 添加泛型支持
			daoSuperType.addTypeArgument(baseModelJavaType);
			
			mapperInterface.addImportedType(baseModelJavaType);
			mapperInterface.addImportedType(new FullyQualifiedJavaType(DEFAULT_DAO_SUPER_CLASS_Package));
			mapperInterface.addSuperInterface(daoSuperType);

			mapperJavafile = new GeneratedJavaFile(mapperInterface, daoTargetDir, javaFormatter);
			mapperJavaFiles.add(mapperJavafile);
		}
		return mapperJavaFiles;
	}
	
	@Override
	public boolean sqlMapInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
 
		List<Attribute> attributes = element.getAttributes();
		/*attributes.add(new Attribute("useGeneratedKeys", "true"));
		attributes.add(new Attribute("keyProperty", "id"));*/
		StringBuffer sb = new StringBuffer();
 
		 
		return true;
	}
	
	@Override
	public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
	    // 创建findById查询
        XmlElement findByIdElement = new XmlElement("select");
        findByIdElement.addAttribute(new Attribute("id", "findById"));
        findByIdElement.addAttribute(new Attribute("resultMap", "BaseResultMap"));


	 	//创建Select查询
        XmlElement select = new XmlElement("select");
        select.addAttribute(new Attribute("id", "findList"));
        select.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        select.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
		StringBuffer sbColumns = new StringBuffer();

		List<Element> elementList = new ArrayList<>();

		List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
		List<IntrospectedColumn> baseColumns = introspectedTable.getBaseColumns();

		primaryKeyColumns.forEach(p ->{
			sbColumns.append(p.getActualColumnName()).append(", ");
		});

		for (int i = 0; i < baseColumns.size(); i++){
            IntrospectedColumn column = baseColumns.get(i);
            String columnName = column.getActualColumnName();
			String javaProperty = column.getJavaProperty();
			sbColumns.append(columnName).append(", ");

			Element conditionElement = createIfElement(column, javaProperty);
            elementList.add(conditionElement);

		}

		StringBuffer sbJoin = getSqlSuffix(sbColumns, introspectedTable);

		StringBuffer findByIdSql = new StringBuffer(sbJoin.toString());
        findByIdSql.append(" WHERE ");


        for(int i = 0; i < primaryKeyColumns.size(); i++){
            IntrospectedColumn column = primaryKeyColumns.get(i);
            if(i > 0){
                findByIdSql.append(" AND ");
            }
            join(findByIdSql, column.getActualColumnName(), column.getJavaProperty());
        }

        findByIdElement.addElement(new TextElement(findByIdSql.toString()));

        sbJoin.append(" WHERE 1 = 1 ");
        select.addElement(new TextElement(sbJoin.toString() ));

        elementList.forEach(x ->{
            select.addElement(x);
        });



		XmlElement parentElement = document.getRootElement();

		addElement(parentElement, findByIdElement);
		addElement(parentElement, select);


        return true;
 
	}

    /**
     *  给父级元素新增一个元素
     * @param parentElement
     * @param newElement
     */
    private void addElement(XmlElement parentElement, Element newElement){
        // 新增一个空行
        parentElement.addElement(new TextElement(""));
        parentElement.addElement(newElement);
    }

    /**
     *  获取查询SQL 前缀
     * @param sbColumns
     * @param introspectedTable
     * @return
     */
    private StringBuffer getSqlSuffix(StringBuffer sbColumns, IntrospectedTable introspectedTable){
        StringBuffer sbJoin = new StringBuffer();
        sbJoin.append("SELECT ");
        sbJoin.append(sbColumns.toString().substring(0, sbColumns.length() -2 ));
        sbJoin.append(" FROM ");
        sbJoin.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        return sbJoin;
    }

    /**
     *
     * @param javaProperty  java filed name
     * @param fullyQualifiedName jva filed type
     * @return
     */
	private String createCondition(String javaProperty, String fullyQualifiedName){
        if(String.class.getName().equals(fullyQualifiedName)){
            return " " + javaProperty + " != null and " + javaProperty + " != '' ";
        }
		return  " " + javaProperty + " != null ";
	}


    /**
     *  创建查询If条件
     * @param column
     * @param javaProperty
     * @return
     */
	private Element createIfElement(IntrospectedColumn column, String javaProperty){
        XmlElement conditionElement = new XmlElement("if");
        FullyQualifiedJavaType fullyQualifiedJavaType = column.getFullyQualifiedJavaType();
        String fullyQualifiedName = fullyQualifiedJavaType.getFullyQualifiedName();
        conditionElement.addAttribute(new Attribute("test",createCondition(javaProperty, fullyQualifiedName)));

        StringBuffer text = new StringBuffer();
        text.append(" AND ");
        join(text, column.getActualColumnName(), javaProperty);
        conditionElement.addElement(new TextElement(text.toString()));
        return conditionElement;
    }

    /**
     *  拼接条件
     * @param sb
     * @param columnName
     * @param javaProperty
     */
    private void join(StringBuffer sb, String columnName, String javaProperty){
        sb.append(columnName);
        sb.append(" = ");
        sb.append("#{");
        sb.append(javaProperty);
        sb.append("}");
    }
	
}
