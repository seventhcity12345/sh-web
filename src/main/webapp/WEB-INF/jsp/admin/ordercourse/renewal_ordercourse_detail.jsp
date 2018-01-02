<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/taglib.jsp"%>
<%
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragrma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   
    <style>
    [ng-cloak],
    [data-ng-cloak],
    [x-ng-cloak],
    .ng-cloak,
    .x-ng-cloak {
        visibility: hidden !important;
    }
    .invisible {
        visibility: hidden;
    }
    .red {
        color: #d9322c;
    }
    .green-text {
        font-size: 14px;
        color: #00894f;
    }
    
    .admin-recontract-detail {
        width: 520px;
        margin: 10px auto;
        font-size: 14px;
    }
    .admin-recd {
        border: 1px solid #959595;
        border-top: none;
        overflow: hidden;
    }
    .admin-recd:first-child {
        border-top: 1px solid #959595;
    }
    .admin-recd p {
        padding: 5px 10px;
    }
    .admin-recd span {
        padding: 0 20px;
        margin-right: 10px;
        border: 1px solid #626262;
        background-color: #bfbfbf;
        color: #626262;
        text-align: center;
    }
    
    </style>

    <script type="text/javascript">
    adminRecontractDetail();
    </script>
</head>
    <input type="hidden" id="hiddenRenewalContract" name="hiddenRenewalContract" value="${order_id}"/>
    <div class="max-width admin-recontract-detail" data-ng-controller="adminRecontractDetailCtrl" id="adminRecontractDetail">
        <div class="admin-recd" data-ng-repeat="item in viewModel" ng-cloak>
            <p ng-if="$index==0"><label>原合同</label></p>
            <p ng-if="$index!=0"><label ng-cloak>续约合同{{$index}}</label></p>
            <p><label>课程包名称：</label><span ng-cloak>{{item.course_package_name}}</span></p>
            <p><label>合同开始日期：</label><span ng-cloak>{{item.start_order_time | date: 'yyyy.MM.dd'}}</span><label>合同结束日期：</label><span ng-cloak>{{item.end_order_time | date: 'yyyy.MM.dd'}}</span></p>
            <p ng-cloak><label>原价：</label><span>{{item.total_show_price}}</span><label>优惠价：</label><span>{{item.total_real_price}}</span></p>
            <p ng-if="$index==0"><label>实际付款金额：</label><span ng-cloak>{{item.total_final_price}}</span></p>
            <p ng-if="$index!=0"><label>补差金额：</label><span ng-cloak>{{item.total_final_price}}</span></p>
        </div>
    </div>
</html>
