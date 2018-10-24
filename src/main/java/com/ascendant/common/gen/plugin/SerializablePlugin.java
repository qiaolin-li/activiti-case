package com.ascendant.common.gen.plugin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;

import com.ascendant.common.constants.SystemConstants;

/**
 * entity生成
 * @author qiaolin
 * @date 2017-09-25 19:43:56
 * 
 */ 

public class SerializablePlugin extends PluginAdapter {

	private FullyQualifiedJavaType serializable = new FullyQualifiedJavaType("java.io.Serializable");
	private FullyQualifiedJavaType data = new FullyQualifiedJavaType("lombok.Data");
	private FullyQualifiedJavaType noArgsConstructor = new FullyQualifiedJavaType("lombok.NoArgsConstructor");
	private FullyQualifiedJavaType equalsAndHashCode = new FullyQualifiedJavaType("lombok.EqualsAndHashCode");
    

	public boolean validate(List<String> warnings) {
		return true;
	}


	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		makeSerializable(topLevelClass, introspectedTable);
		return true;
	}

	@Override
	public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		makeSerializable(topLevelClass, introspectedTable);
		return true;
	}

	@Override
	public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		makeSerializable(topLevelClass, introspectedTable);
		return true;
	}

	protected void makeSerializable(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		if (topLevelClass.getSuperClass() != null) {
			String superName = topLevelClass.getSuperClass().getFullyQualifiedName();
			if ("".equals(superName.trim())) {
				topLevelClass.addImportedType(serializable);
				topLevelClass.addSuperInterface(serializable);
			}
		} else {
			topLevelClass.addImportedType(serializable);
			topLevelClass.addSuperInterface(serializable);
		}
		topLevelClass.addJavaDocLine("/**");
		topLevelClass.addJavaDocLine(" * @author " + SystemConstants.USERNAME);
		topLevelClass.addJavaDocLine(" * @date "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		topLevelClass.addJavaDocLine(" */");
		topLevelClass.addJavaDocLine("");
		
		topLevelClass.addImportedType(data);
		topLevelClass.addImportedType(noArgsConstructor);
		topLevelClass.addImportedType(equalsAndHashCode);
		topLevelClass.addAnnotation("@Data");
		topLevelClass.addAnnotation("@NoArgsConstructor");
		topLevelClass.addAnnotation("@EqualsAndHashCode(callSuper = false)");
		
        Field field = new Field();
        field.setFinal(true);
        field.setInitializationString("1L");  
        field.setName("serialVersionUID");  
        field.setStatic(true);
        field.setType(new FullyQualifiedJavaType("long"));  
        field.setVisibility(JavaVisibility.PRIVATE);
        context.getCommentGenerator().addFieldComment(field, introspectedTable);
	    topLevelClass.getFields().add(0,field);
	}
	
	
	/**
	 *  不生成Get方法
	 *  @author qiaolin
	 */
	@Override
	public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass,
			IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		return false;
	}
	
	/**
	 *  不生成Set方法
	 *  @author qiaolin
	 */
	@Override
	public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass,
			IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		return false;
	}
	
	@Override
	public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
			IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		String remarks = introspectedColumn.getRemarks();
		if (StringUtility.stringHasValue(remarks)) {
			try {
				//byte[] bytes = remarks.getBytes("utf-8");
				//remarks = new String(bytes , "gbk");
				field.addJavaDocLine("/** "+remarks+" */");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}



}
