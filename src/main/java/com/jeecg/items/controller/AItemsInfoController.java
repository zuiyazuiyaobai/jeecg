package com.jeecg.items.controller;
import com.jeecg.items.entity.AItemsInfoEntity;
import com.jeecg.items.service.AItemsInfoServiceI;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.TreeChildCount;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import java.util.HashMap;
import org.jeecgframework.core.util.ExceptionUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.net.URI;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.jwt.util.GsonUtil;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**   
 * @Title: Controller  
 * @Description: A_ITEMS_INFO
 * @author onlineGenerator
 * @date 2018-05-26 19:36:32
 * @version V1.0   
 *
 */
@Api(value="AItemsInfo",description="A_ITEMS_INFO",tags="aItemsInfoController")
@Controller
@RequestMapping("/aItemsInfoController")
public class AItemsInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AItemsInfoController.class);

	@Autowired
	private AItemsInfoServiceI aItemsInfoService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * A_ITEMS_INFO列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/jeecg/items/aItemsInfoList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(AItemsInfoEntity aItemsInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(AItemsInfoEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, aItemsInfo, request.getParameterMap());
		try{
		//自定义追加查询条件
		String query_itemsChildName_begin = request.getParameter("itemsChildName_begin");
		String query_itemsChildName_end = request.getParameter("itemsChildName_end");
		if(StringUtil.isNotEmpty(query_itemsChildName_begin)){
			cq.ge("itemsChildName", Integer.parseInt(query_itemsChildName_begin));
		}
		if(StringUtil.isNotEmpty(query_itemsChildName_end)){
			cq.le("itemsChildName", Integer.parseInt(query_itemsChildName_end));
		}
		cq.addOrder("projectId", SortDirection.desc);
		cq.addOrder("phasesId", SortDirection.asc);
//		String query_deptName_begin = request.getParameter("deptName_begin");
//		String query_deptName_end = request.getParameter("deptName_end");
//		if(StringUtil.isNotEmpty(query_deptName_begin)){
//			cq.ge("deptName", Integer.parseInt(query_deptName_begin));
//		}
//		if(StringUtil.isNotEmpty(query_deptName_end)){
//			cq.le("deptName", Integer.parseInt(query_deptName_end));
//		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.aItemsInfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除A_ITEMS_INFO
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(AItemsInfoEntity aItemsInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		aItemsInfo = systemService.getEntity(AItemsInfoEntity.class, aItemsInfo.getId());
		message = "A_ITEMS_INFO删除成功";
		try{
			aItemsInfoService.delete(aItemsInfo);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "A_ITEMS_INFO删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除A_ITEMS_INFO
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "A_ITEMS_INFO删除成功";
		try{
			for(String id:ids.split(",")){
				AItemsInfoEntity aItemsInfo = systemService.getEntity(AItemsInfoEntity.class, 
				id
				);
				aItemsInfoService.delete(aItemsInfo);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "A_ITEMS_INFO删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加A_ITEMS_INFO
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(AItemsInfoEntity aItemsInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "A_ITEMS_INFO添加成功";
		try{
			aItemsInfoService.save(aItemsInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "A_ITEMS_INFO添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新A_ITEMS_INFO
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(AItemsInfoEntity aItemsInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "A_ITEMS_INFO更新成功";
		AItemsInfoEntity t = aItemsInfoService.get(AItemsInfoEntity.class, aItemsInfo.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(aItemsInfo, t);
			aItemsInfoService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "A_ITEMS_INFO更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * A_ITEMS_INFO新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(AItemsInfoEntity aItemsInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(aItemsInfo.getId())) {
			aItemsInfo = aItemsInfoService.getEntity(AItemsInfoEntity.class, aItemsInfo.getId());
			req.setAttribute("aItemsInfoPage", aItemsInfo);
		}
		return new ModelAndView("com/jeecg/items/aItemsInfo-add");
	}
	/**
	 * A_ITEMS_INFO编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(AItemsInfoEntity aItemsInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(aItemsInfo.getId())) {
			aItemsInfo = aItemsInfoService.getEntity(AItemsInfoEntity.class, aItemsInfo.getId());
			req.setAttribute("aItemsInfoPage", aItemsInfo);
		}
		return new ModelAndView("com/jeecg/items/aItemsInfo-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","aItemsInfoController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(AItemsInfoEntity aItemsInfo,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(AItemsInfoEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, aItemsInfo, request.getParameterMap());
		List<AItemsInfoEntity> aItemsInfos = this.aItemsInfoService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"A_ITEMS_INFO");
		modelMap.put(NormalExcelConstants.CLASS,AItemsInfoEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("A_ITEMS_INFO列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,aItemsInfos);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(AItemsInfoEntity aItemsInfo,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"A_ITEMS_INFO");
    	modelMap.put(NormalExcelConstants.CLASS,AItemsInfoEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("A_ITEMS_INFO列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<AItemsInfoEntity> listAItemsInfoEntitys = ExcelImportUtil.importExcel(file.getInputStream(),AItemsInfoEntity.class,params);
				for (AItemsInfoEntity aItemsInfo : listAItemsInfoEntitys) {
					aItemsInfoService.save(aItemsInfo);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="A_ITEMS_INFO列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<AItemsInfoEntity>> list() {
		List<AItemsInfoEntity> listAItemsInfos=aItemsInfoService.getList(AItemsInfoEntity.class);
		return Result.success(listAItemsInfos);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取A_ITEMS_INFO信息",notes="根据ID获取A_ITEMS_INFO信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		AItemsInfoEntity task = aItemsInfoService.get(AItemsInfoEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取A_ITEMS_INFO信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建A_ITEMS_INFO")
	public ResponseMessage<?> create(@ApiParam(name="A_ITEMS_INFO对象")@RequestBody AItemsInfoEntity aItemsInfo, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<AItemsInfoEntity>> failures = validator.validate(aItemsInfo);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			aItemsInfoService.save(aItemsInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("A_ITEMS_INFO信息保存失败");
		}
		return Result.success(aItemsInfo);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新A_ITEMS_INFO",notes="更新A_ITEMS_INFO")
	public ResponseMessage<?> update(@ApiParam(name="A_ITEMS_INFO对象")@RequestBody AItemsInfoEntity aItemsInfo) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<AItemsInfoEntity>> failures = validator.validate(aItemsInfo);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			aItemsInfoService.saveOrUpdate(aItemsInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新A_ITEMS_INFO信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新A_ITEMS_INFO信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除A_ITEMS_INFO")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			aItemsInfoService.deleteEntityById(AItemsInfoEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("A_ITEMS_INFO删除失败");
		}

		return Result.success();
	}


	@RequestMapping(params = "getItemsInfo")
	@ResponseBody
	public AjaxJson getItemsInfo(HttpServletRequest request, HttpServletResponse response){

		AjaxJson j = new AjaxJson();

		String orgIds = request.getParameter("orgIds");

		String[] ids = new String[]{};
		if(org.apache.commons.lang.StringUtils.isNotBlank(orgIds)){
			orgIds = orgIds.substring(0, orgIds.length()-1);
			ids = orgIds.split("\\,");
		}

		String parentid = request.getParameter("parentid");

		List<TSDepart> tSDeparts = new ArrayList<TSDepart>();

		StringBuffer hql = new StringBuffer(" from TSDepart t where 1=1 ");
		if(org.apache.commons.lang.StringUtils.isNotBlank(parentid)){

			TSDepart dePart = this.systemService.getEntity(TSDepart.class, parentid);

			hql.append(" and TSPDepart = ?");
			tSDeparts = this.systemService.findHql(hql.toString(), dePart);
		} else {
			hql.append(" and t.orgType = ?");
			tSDeparts = this.systemService.findHql(hql.toString(), "1");
		}
		List<Map<String,Object>> dateList = new ArrayList<Map<String,Object>>();
		if(tSDeparts.size()>0){
			Map<String,Object> map = null;
			String sql = null;
			Object[] params = null;
			for(TSDepart depart:tSDeparts){
				map = new HashMap<String,Object>();
				map.put("id", depart.getId());
				map.put("name", depart.getDepartname());

				map.put("code",depart.getOrgCode());

				if(ids.length>0){
					for(String id:ids){
						if(id.equals(depart.getId())){
							map.put("checked", true);
						}
					}
				}

				if(org.apache.commons.lang.StringUtils.isNotBlank(parentid)){
					map.put("pId", parentid);
				} else{
					map.put("pId", "1");
				}
				//根据id判断是否有子节点
				sql = "select count(1) from t_s_depart t where t.parentdepartid = ?";
				params = new Object[]{depart.getId()};
				long count = this.systemService.getCountForJdbcParam(sql, params);
				if(count>0){
					map.put("isParent",true);
				}
				dateList.add(map);
			}
		}
		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(dateList);
		System.out.println(jsonArray.toString());
		j.setMsg(jsonArray.toString());
		return j;
	}
}
