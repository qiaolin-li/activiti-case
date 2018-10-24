package com.ascendant.core.controller;

import com.ascendant.core.service.ActModelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.rest.editor.model.ModelSaveRestResource;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * 流程模型相关Controller
 */

@Controller
@RequestMapping(value = "act/model")
public class ActModelController implements ModelDataJsonConstants {

	@Autowired
	private ActModelService actModelService;


	protected static final Logger LOGGER = LoggerFactory.getLogger(ModelSaveRestResource.class);

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private ObjectMapper objectMapper;


	///**
	// * 流程模型列表
	// */
	//@ResponseBody
	//@RequiresPermissions("act:model:list")
	//@RequestMapping(value = "data")
	//public Map<String, Object> data(String category, HttpServletRequest request, HttpServletResponse response, Model model) {
	//	Page<org.activiti.engine.repository.Model> page = actModelService.modelList(
	//			new Page<org.activiti.engine.repository.Model>(request, response), category);
	//	return getBootstrapData(page);
	//}


	/**
	 * 流程模型列表
	 */
	//@RequiresPermissions("act:model:list")
	@RequestMapping(value = { "list", "" })
	public String modelList(String category, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/act/actModelList";
	}

	/**
	 * 创建模型
	 */
	//@RequiresPermissions("act:model:create")
	//@RequestMapping(value = "create", method = RequestMethod.GET)
	//public String create(Model model) {
	//	return "modules/act/actModelCreate";
	//}
	
	/**
	 * 创建模型
	 */
	@ResponseBody
	//@RequiresPermissions("act:model:create")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(String name, String key, String description, String category) {
		try {
			actModelService.create(name, key, description, category);
			return "添加模型成功";

		} catch (Exception e) {
			return "添加模型失败";
			//logger.error("创建模型失败：", e);
		}
	}

	/**
	 * 根据Model部署流程
	 */
	//@ResponseBody
	//@RequiresPermissions("act:model:deploy")
	//@RequestMapping(value = "deploy")
	//public AjaxJson deploy(String id, RedirectAttributes redirectAttributes) {
	//	AjaxJson j = new AjaxJson();
	//	String message = actModelService.deploy(id);
	//	j.setMsg(message);
	//	return j;
	//}
	
	/**
	 * 导出model的xml文件
	 */
	//@RequiresPermissions("act:model:export")
	@RequestMapping(value = "export")
	public void export(String id, HttpServletResponse response) {
		actModelService.export(id, response);
	}

	/**
	 * 更新Model分类
	 */
	//@ResponseBody
	//@RequiresPermissions("act:model:edit")
	//@RequestMapping(value = "updateCategory")
	//public AjaxJson updateCategory(String id, String category, RedirectAttributes redirectAttributes) {
	//	AjaxJson j = new AjaxJson();
	//	actModelService.updateCategory(id, category);
	//	j.setMsg("设置成功，模块ID=" + id);
	//	return j;
	//}
	
	/**
	 * 删除Model
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	//@ResponseBody
	//@RequiresPermissions("act:model:del")
	//@RequestMapping(value = "delete")
	//public AjaxJson delete(String id, RedirectAttributes redirectAttributes) {
	//	AjaxJson j = new AjaxJson();
	//	actModelService.delete(id);
	//	j.setMsg("删除成功，模型ID=" + id);
	//	return j;
	//}
	//
	/**
	 * 删除Model
	 * @param ids
	 * @param redirectAttributes
	 * @return
	 */
	//@ResponseBody
	//@RequiresPermissions("act:model:del")
	//@RequestMapping(value = "deleteAll")
	//public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
	//	String idArray[] =ids.split(",");
	//	for(String id : idArray){
	//		actModelService.delete(id);
	//	}
	//	AjaxJson j = new AjaxJson();
	//	j.setMsg("删除成功" );
	//	return j;
	//}

	/**
	 *  此方法用于覆盖Activiti model 保存的方法，
	 *  因为那个方法获取前台发送过来的参数时会报错
	 *  根据跟踪结果，为消息解析器无法解析出来，但不知道怎么修改！
	 * @see ModelSaveRestResource#saveModel(java.lang.String, org.springframework.util.MultiValueMap)
	 **/
	@RequestMapping(value="/{modelId}/save", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void saveModel(@PathVariable String modelId, @RequestParam("name") String name,
						  @RequestParam("json_xml") String json_xml, @RequestParam("svg_xml") String svg_xml,
						  @RequestParam("description") String description) {
		try {

			org.activiti.engine.repository.Model model = repositoryService.getModel(modelId);

			ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());

			modelJson.put(MODEL_NAME, name);
			modelJson.put(MODEL_DESCRIPTION, description);
			model.setMetaInfo(modelJson.toString());
			model.setName(name);

			repositoryService.saveModel(model);

			repositoryService.addModelEditorSource(model.getId(), json_xml.getBytes("utf-8"));

			InputStream svgStream = new ByteArrayInputStream(svg_xml.getBytes("utf-8"));
			TranscoderInput input = new TranscoderInput(svgStream);

			PNGTranscoder transcoder = new PNGTranscoder();
			// Setup output
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			TranscoderOutput output = new TranscoderOutput(outStream);

			// Do the transformation
			transcoder.transcode(input, output);
			final byte[] result = outStream.toByteArray();
			repositoryService.addModelEditorSourceExtra(model.getId(), result);
			outStream.close();

		} catch (Exception e) {
			LOGGER.error("Error saving model", e);
			throw new ActivitiException("Error saving model", e);
		}
	}
}
