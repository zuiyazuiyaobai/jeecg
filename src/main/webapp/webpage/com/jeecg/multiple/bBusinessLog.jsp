<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="now" class="java.util.Date" scope="page"/>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
 <meta name="renderer" content="webkit">
 <meta name="applicable-device" content="pc,mobile">
 <meta http-equiv="Cache-Control" content="no-transform" />
 <meta name="MobileOptimized" content="width" />
 <meta name="HandheldFriendly" content="true" />
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
 <meta charset="UTF-8" />
 <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
 <title>业务日志信息</title>
 <t:base type="jquery,easyui,tools,DatePicker,fileupload"></t:base>
 <link rel="stylesheet" href="plug-in/businessLog/css/iconfont.css" type="text/css">
 <link rel="stylesheet" href="plug-in/businessLog/css/index.css" type="text/css">
 <link rel="stylesheet" href="plug-in/businessLog/css/htmleaf-demo.css" type="text/css">

 <%-- <script src = "plug-in/fileupload/js/myuploadfunction.js"></script>--%>
 <script type="text/javascript">


 </script>

</head>
<body>
<header><img src="plug-in/businessLog/img/progress-.png"/>阶段办理进度</header>
<nav>
 <div class="nav-left">
  <div class="left-icon iconfont icon-ren"></div>
  <div class="left-title">申请人：<span class="title-span">${bProjectBusiness.applyName}</span></div>
 </div>
 <div class="nav-right">
  <div class="right-title">时间：
   <span class="right-span"> <fmt:formatDate value="${now}" pattern="yyyy年MM月dd日" /> </span>
  </div>
  <div class="left-icon iconfont icon-shijian"></div>
 </div>
</nav>
<section>
 <div class="section-title iconfont icon-shezhi">&nbsp;办理阶段</div>
 <div id="section-center">
  <div class="xian"></div>
  <c:forEach items="${phaseList}" var="phase" varStatus="stuts">
   <c:if test="${stuts.index % 2 == 0}">
    <dl class="section-dl" style="cursor: pointer;" onclick="javascript:window.location.href='bProjectBusinessController.do?loadBusinessLog&phasesId=${phase.phases_id}&id=${bProjectBusiness.id}'">
     <dd class="section-dl"></dd>
     <c:if test="${phase.current_phases_status == 'will'}">
      <div class="round round-two"><div class="circle circle-two"><p class="circle-title">${phase.phases_sort}</p><p class="circle-p">阶段</p></div></div>
     </c:if>
     <c:if test="${phase.current_phases_status != 'will'}">
      <div class="round"><div class="circle"><p class="circle-title">${phase.phases_sort}</p><p class="circle-p">阶段</p></div></div>
     </c:if>
     <dt class="section-dt"></dt>
     <div class="dt-title">${phase.phases_name}</div>
    </dl>
   </c:if>
   <c:if test="${stuts.index % 2 != 0}">
    <dl class="section-dl" style="cursor: pointer;" onclick="javascript:window.location.href='bProjectBusinessController.do?loadBusinessLog&phasesId=${phase.phases_id}&id=${bProjectBusiness.id}'">
     <div class="dt-title dt-two">${phase.phases_name}</div>
     <dt class="section-dt-two"></dt>
     <c:if test="${phase.current_phases_status == 'will'}">
      <div class="round round-two"><div class="circle circle-two"><p class="circle-title">${phase.phases_sort}</p><p class="circle-p">阶段</p></div></div>
     </c:if>
     <c:if test="${phase.current_phases_status != 'will'}">
      <div class="round"><div class="circle"><p class="circle-title">${phase.phases_sort}</p><p class="circle-p">阶段</p></div></div>
     </c:if>
      <%--<div class="round round-two"><div class="circle circle-two"><p class="circle-title">${phase.phases_sort}</p><p class="circle-p">阶段</p></div></div>--%>
     <!--<dd class="section-dd">当阶段完成后就会变成黄色</dd>-->
    </dl>
   </c:if>

  </c:forEach>

  <%--
    <dl class="section-dl">
     <div class="dt-title dt-two">用地审批</div>
     <dt class="section-dt-two"></dt>
     <div class="round round-two"><div class="circle circle-two"><p class="circle-title">第二</p><p class="circle-p">阶段</p></div></div>
     <!--<dd class="section-dd">当阶段完成后就会变成黄色</dd>-->
    </dl>--%>
  <%-- <dl class="section-dl">
    <dd class="section-dd"></dd>
    <div class="round round-two"><div class="circle circle-two"><p class="circle-title">第三</p><p class="circle-p">阶段</p></div></div>
    <dt class="section-dt-three"></dt>
    <div class="dt-title dt-three">建设工程规划许可审批</div>
   </dl>
   <dl class="section-dl">
    <div class="dt-title dt-two">施工许可（开发报告）审批</div>
    <dt class="section-dt-two"></dt>
    <div class="round round-two"><div class="circle circle-two"><p class="circle-title">第四</p><p class="circle-p">阶段</p></div></div>
    <!--<dd class="section-dd">当阶段完成后就会变成黄色</dd>-->
   </dl>
   <dl class="section-dl">
    <dd class="section-dd"></dd>
    <div class="round round-two"><div class="circle circle-two"><p class="circle-title">第五</p><p class="circle-p">阶段</p></div></div>
    <dt class="section-dt-three"></dt>
    <div class="dt-title dt-three">工程竣工验收备案</div>
   </dl>--%>
 </div>
</section>
<div class="footer">
 <div class="footer-title">
  <div class="footer-img"><img src="plug-in/businessLog/img/top-b.png"/></div>
  <div class="footer-bottom"><img src="plug-in/businessLog/img/line-y.png"/></div>
 </div>
 <div class="main-timeline">

  <c:forEach items="${childBusinessList}" var="childBusiness" varStatus="stuts">
  <c:if test="${stuts.index % 2 == 0}">
  <div class="timeline">
   <div class="timeline-icon"></div>
   <div class="timeline-content right">
    <c:if test="${childBusiness.status == '1'}">
    <div class="timelines Two" >
     </c:if>
     <c:if test="${childBusiness.status != '1'}">
     <div class="timelines Two last" >
      </c:if>
      <div class="content-one"></div>
      <div class="Two-title">
       <p class="content-p iconfont icon-tongji">&nbsp;${childBusiness.dept_name}</p>
       <p class="content-op">${childBusiness.items_name}</p>
      </div>
     </div>
     <div class="content-right">
      <div class="conter-title">
       <span class="conter-span">${childBusiness.status  == '1' ? '已办结' : '审核中'}</span>
       <img src="plug-in/businessLog/img/01-full.png" />
      </div>
      <div class="bottom-title">剩余<span class="bottom-span">${childBusiness.ssgzr}</span>工作日</div>
     </div>
    </div>
   </div>

   </c:if>

   <c:if test="${stuts.index % 2 != 0}">
   <div class="timeline">
    <div class="timeline-icon"> </div>
    <div class="timeline-content" >
     <c:if test="${childBusiness.status == '1'}">
     <div class="timelines One" >
      </c:if>
      <c:if test="${childBusiness.status != '1'}">
      <div class="timelines One last" >
       </c:if>
       <div class="content-one"></div>
       <p class="content-p iconfont icon-zhiyeguihua">&nbsp;${childBusiness.dept_name}</p>
       <p class="content-op">${childBusiness.items_name}</p>
      </div>
      <div class="content-right One-right" >
       <div class="conter-title">
        <span class="conter-span">${childBusiness.status  == '1' ? '已办结' : '审核中'}</span>
        <img src="plug-in/businessLog/img/03-transition.png" />
       </div>
       <div class="bottom-title">剩余<span class="bottom-span">${childBusiness.ssgzr}</span>工作日</div>
      </div>
     </div>
    </div>
    </c:if>




    </c:forEach>
    <%--
      <div class="timeline">
       <div class="timeline-icon">

       </div>
       <div class="timeline-content right">
        <div class="timelines Two" >
         <div class="content-one"></div>
         <div class="Two-title">
          <p class="content-p iconfont icon-shezhi">&nbsp;国土分局</p>
          <p class="content-op">无</p>
         </div>
        </div>
        <div class="content-right">
         <div class="conter-title">
          <span class="conter-span">审核中</span>
          <img src="plug-in/businessLog/img/04-warning.png" />
         </div>
         <div class="bottom-title">超期<span class="bottom-span">1</span>工作日</div>
        </div>
       </div>
      </div>
      <div class="timeline">
       <div class="timeline-icon"> </div>
       <div class="timeline-content" >
        <div class="timelines One" >
         <div class="content-one"></div>
         <p class="content-p iconfont icon-diyouhaohuanbao">&nbsp;建设环保局</p>
         <p class="content-op">建设项目环境影响评价</p>
         <p class="content-op">设计勘测招投标备案</p>
        </div>
        <div class="content-right One-right" >
         <div class="conter-title">
          <span class="conter-span span-wan">已办结</span>
          <img src="plug-in/businessLog/img/05-sigh.png" />
         </div>
         <div class="bottom-title">超期<span class="bottom-span">2</span>工作日</div>
        </div>
       </div>
      </div>
      <div class="timeline list">
       <div class="timeline-icon">

       </div>
       <div class="timeline-content right">
        <div class="timelines Two last" >
         <div class="content-one"></div>
         <div class="Two-title">
          <p class="content-p iconfont icon-shezhi">&nbsp;国土分局</p>
          <p class="content-op">无</p>
         </div>
        </div>
        <div class="content-right">
         <div class="conter-title">
          <span class="conter-span">未受理</span>
          <!--<img src="plug-in/businessLog/img/04-warning.png" />-->
         </div>
         <!--<div class="bottom-title">超期<span class="bottom-span">1</span>工作日</div>-->
        </div>
       </div>
      </div>--%>

   </div>
  </div>
</body>
<script src = "webpage/com/jeecg/multiple/bProjectBusinessList.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
    });


    //查看
    function accept(id,tableName){
        createwindow("查看", "AcertificatesUploadController.do?goUpdate&id="+id);
    }
    //材料上传
    function uploadcertificate(id){
        createwindow("材料上传", "AcertificatesUploadController.do?certificateList&id="+id);
    }
    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'AcertificatesUploadController.do?upload', "bProjectBusinessList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("AcertificatesUploadController.do?exportXls","bProjectBusinessList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("AcertificatesUploadController.do?exportXlsByT","bProjectBusinessList");
    }

</script>