<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>A_PHASES_INFO</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="aPhasesInfoController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${aPhasesInfoPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								项目编号:
							</label>
						</td>
						<td class="value">
							<input id="phasesId" name="phasesId" type="text" style="width: 150px" class="inputxt" 		datatype="*" ignore="checked"  value='${aPhasesInfoPage.projectId}'/>
									<%--<t:dictSelect field="projectId" type="list" 		datatype="*" typeGroupCode=""   defaultVal="${aPhasesInfoPage.projectId}" hasLabel="false"  title="项目编号" ></t:dictSelect>--%>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">项目编号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								阶段编号:
							</label>
						</td>
						<td class="value">
						    <input id="phasesId" name="phasesId" type="text" style="width: 150px" class="inputxt" 		datatype="*" ignore="checked"  value='${aPhasesInfoPage.phasesId}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">阶段编号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								阶段名称:
							</label>
						</td>
						<td class="value">
						    <input id="phasesName" name="phasesName" type="text" style="width: 150px" class="inputxt" 		datatype="*" ignore="checked"  value='${aPhasesInfoPage.phasesName}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">阶段名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								备注:
							</label>
						</td>
						<td class="value">
						    <input id="remark" name="remark" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${aPhasesInfoPage.remark}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
					</tr>
				
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/jeecg/phases/aPhasesInfo.js"></script>		
