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
    .admin-paylist {
        position: relative;
        top: 50px;
        color: #626262;
        font-size: 16px;
    }
    .admin-paylist p {
        margin: 10px 0;
    }
    .admin-paylist input,
    .admin-paylist label,
    .admin-paylist span {
        height: 36px;
        line-height: 36px;
        margin-right: 25px;
    }
    .admin-paylist #payOrder {
        width: 25px;
        display: inline-block;
        margin-right: 0;
        text-align: center;
    }
    .admin-paylist label {
        margin-right: 0;
    }
    .admin-paylist input {
        margin: 0 15px;
        padding: 0 5px;
        vertical-align: middle;
    }
    .admin-paylist input.readonly {
        background-color: #e0e0e0;
    }
    .admin-paylist ul {
        margin: 20px 0;
    }
    .admin-paylist button {
        height: 36px;
        line-height: 36px;
        margin-right: 15px;
        padding: 0 15px;
    }
    .admin-paylist hr {
        width: 100%;
    }

    .admin-paylist ul li {
        margin: 20px 0;
        padding-top: 20px;
        border-top: 1px dashed #e0e0e0;
    }
    .admin-paylist ul li:first-child {
        border: none;
        padding-top: 0;
    }

    .pay-select {
        width: auto;
        height: 38px;
        line-height: 38px;
        color: #626262;
        margin-right: 25px;
    }
    .confirm-btn,
    .offline-info,
    .paybank-info {
        margin: 10px 0 0 80px;
    }
    .admin-paylist button.confirm-btn {
        padding: 0 30px;
    }
    .offline-info span,
    .paybank-info span {
        width: 115px;
        margin: 0;
        display: inline-block;
    }
    .offline-info .item,
    .paybank-info .item {
        margin: 10px 0;
        display: inline-block;
    }
    .offline-info .item label,
    .paybank-info .item label {
        color: #d9322c;
    }
    .admin-paylist input.pay-transaction {
        width: 450px;
        max-width: 450px;
    }
    </style>

    <script type="text/javascript">
        loadOrderSplit();
    </script>
</head>
    <input type="hidden" id="hidden_order_id_by_split" name="hidden_order_id_by_split" value="${param.order_id}"/>
    <div class="max-width admin-paylist" data-ng-controller="adminOrderListCtrl" id="adminPayOrderList">
        <p>
            <label>总金额：</label>
            <span ng-cloak>{{ totalPayPrice }}</span>
        </p>
        <p>
            <label>还需支付金额：</label>
            <span ng-cloak>{{ shouldPayPrice }}</span>
            <label>已支付金额：</label>
            <span ng-cloak>{{ havePaidPrice }}</span>
            <label ng-if="isOverflow" class="red" ng-cloak>已超出金额：</label>
            <label ng-if="!isOverflow" ng-cloak>剩余可拆分金额：</label>
            <span ng-cloak ng-class="{'red': isOverflow}">{{ canSplitPrice }}</span>
        </p>
        <hr>
       <!--  <ul id="payList">
            <li data-ng-repeat="item in viewModel track by $index | limitTo: payListLimit()" ng-class="{'paid-done': item.split_status == 1}" ng-cloak>
                <form class="adminPayListForm" name="adminPayListForm{{$index}}">
                <label ng-class="{'lightgray-text': item.split_status == 1 || item.split_status == 3}">第<span id="payOrder">{{ $index + 1 }}</span>笔</label>
                <input type="text" ng-class="{'readonly': item.split_status == 1 || item.split_status == 3 || studentRole.id == 2}" maxlength="6" ng-keydown="validateNumber($event)" ng-readonly="item.split_status == 1 || item.split_status == 3 || studentRole.id == 2" ng-value="item.split_price" ng-change="inputChange()" ng-model="item.split_price">
                <span>元</span>
                <select class="pay-select" ng-disabled="item.split_status == 1 || item.split_status == 3" ng-model="item.payTypeSelect" ng-if="studentRole.id != 2" ng-change="payTypeChange($index, item.payTypeSelect)" ng-options="x.name for x in payTypeOptions">
                </select>
                <span class="green-text" ng-class="{'invisible': item.split_status == 0 }" ng-if="item.split_status != 2 && item.split_status != 3">已支付</span>
                <span class="red" ng-class="{'invisible': item.split_status == 3}" ng-if="item.split_status == 2">待确认</span>
                <span class="green-text" ng-class="{'invisible': item.split_status == 2}" ng-if="item.split_status == 3">已确认</span>
                <span id="btnGroup">
                    <button id="minusPayItem" ng-click="minusPayItemBtn($index)" ng-if="(item.split_status == 0 || item.split_status == 2) && studentRole.id != 2">删除</button>
                    <button id="addPayItem" ng-click="addPayItemBtn($event)" ng-if="$index == 0 && studentRole.id != 2">增加</button>
                </span>
                <div class="offline-info" ng-if="item.payTypeSelect.id != 0">
                    <div class="item"><span>线下销售姓名</span><input type="text" ng-class="{'readonly': item.split_status == 1 || item.split_status == 3}" ng-readonly="item.split_status == 1 || item.split_status == 3" name="pay_cc_name" ng-model="item.pay_cc_name" maxlength="20" placeholder="0-20位销售姓名"></div><br>
                    <div class="item"><span>城市</span><input type="text" ng-class="{'readonly': item.split_status == 1 || item.split_status == 3}" ng-readonly="item.split_status == 1 || item.split_status == 3" name="pay_city_name" ng-model="item.pay_city_name" maxlength="20" placeholder="0-20位城市名称"></div>
                    <div class="item"><span>中心</span><input type="text" ng-class="{'readonly': item.split_status == 1 || item.split_status == 3}" ng-readonly="item.split_status == 1 || item.split_status == 3" name="pay_center_name" ng-model="item.pay_center_name" maxlength="20" placeholder="0-20位中心名称"></div>
                </div>
                <div class="paybank-info" ng-if="item.payTypeSelect.id != 0">
                    <div class="item"><span>支付银行</span><input type="text" ng-class="{'readonly': item.split_status == 1 || item.split_status == 3}" ng-readonly="item.split_status == 1 || item.split_status == 3" name="pay_bank" ng-model="item.pay_bank" maxlength="20" placeholder="0-20位银行名称"></div><br>
                    <div class="item"><span>支付成功流水号</span><input class="pay-transaction" ng-class="{'readonly': item.split_status == 1 || item.split_status == 3}" ng-readonly="item.split_status == 1 || item.split_status == 3" type="text" name="pay_success_sequence" ng-model="item.pay_success_sequence" maxlength="40" placeholder="8-40位英文和数字"></div>
                </div>
                <button class="confirm-btn" ng-if="isConfirmBtnShow(studentRole, item.payTypeSelect) && item.split_status != 3" ng-click="confirmPaySplitBtn($index, item.key_id)">确认</button>
                </form>
            </li>
        </ul> -->
         <ul id="payList">
            <li data-ng-repeat="item in viewModel track by $index | limitTo: payListLimit()" ng-class="{'paid-done': item.split_status == 1}" ng-cloak>
                <form class="adminPayListForm" name="adminPayListForm{{$index}}">
                <label ng-class="{'lightgray-text': item.split_status == 1 || item.split_status == 3||item.split_status == 4||item.split_status == 5||item.split_status == 6}">第<span id="payOrder">{{ $index + 1 }}</span>笔</label>
                <input type="text" ng-class="{'readonly': item.split_status == 1 || item.split_status == 3||item.split_status == 5||item.split_status == 6|| studentRole.id == 2}" maxlength="6" ng-keydown="validateNumber($event)" ng-readonly="item.split_status == 1 || item.split_status == 3 ||item.split_status == 5||item.split_status == 6|| studentRole.id == 2" ng-value="item.split_price" ng-change="inputChange()" ng-model="item.split_price">
                <span>元</span>
                <select class="pay-select" ng-disabled="item.split_status == 1 || item.split_status == 3 ||item.split_status == 5||item.split_status == 6" ng-model="item.payTypeSelect" ng-if="studentRole.id != 2" ng-change="payTypeChange($index, item.payTypeSelect)" ng-options="x.name for x in payTypeOptions">
                </select>
                <span class="green-text" ng-class="{'invisible': item.split_status == 0 }" ng-if="item.split_status <2">已支付</span>
                <span class="red" ng-class="{'invisible': item.split_status == 3}" ng-if="item.split_status == 2">待确认</span>
                <span class="green-text" ng-class="{'invisible': item.split_status == 2}" ng-if="item.split_status == 3">已确认</span>
                   <span class="red" ng-if="item.split_status == 4">未申请</span>
                <span class="green-text"  ng-if="item.split_status == 5">申请中</span>
                   <span class="red" ng-if="item.split_status == 6">申请成功</span>
                <span class="green-text"  ng-if="item.split_status == 7">申请失败</span>
                <span id="btnGroup">
                    <button id="minusPayItem" ng-click="minusPayItemBtn($index)" ng-if="(item.split_status == 0 || item.split_status == 2|| item.split_status == 4|| item.split_status == 7) && studentRole.id != 2">删除</button>
                    <button id="addPayItem" ng-click="addPayItemBtn($event)" ng-if="$index == 0 && studentRole.id != 2">增加</button>
                </span>
                <div class="offline-info" ng-if="item.payTypeSelect.id == 1||item.payTypeSelect.id == 2||item.payTypeSelect.id == 3">
                    <div class="item"><span>线下销售姓名</span><input type="text" ng-class="{'readonly': item.split_status == 1 || item.split_status == 3}" ng-readonly="item.split_status == 1 || item.split_status == 3" name="pay_cc_name" ng-model="item.pay_cc_name" maxlength="20" placeholder="0-20位销售姓名"></div><br>
                    <div class="item"><span>城市</span><input type="text" ng-class="{'readonly': item.split_status == 1 || item.split_status == 3}" ng-readonly="item.split_status == 1 || item.split_status == 3" name="pay_city_name" ng-model="item.pay_city_name" maxlength="20" placeholder="0-20位城市名称"></div>
                    <div class="item"><span>中心</span><input type="text" ng-class="{'readonly': item.split_status == 1 || item.split_status == 3}" ng-readonly="item.split_status == 1 || item.split_status == 3" name="pay_center_name" ng-model="item.pay_center_name" maxlength="20" placeholder="0-20位中心名称"></div>
                </div>
                <div class="paybank-info" ng-if="item.payTypeSelect.id == 1||item.payTypeSelect.id == 2||item.payTypeSelect.id == 3">
                    <div class="item"><span>支付银行</span><input type="text" ng-class="{'readonly': item.split_status == 1 || item.split_status == 3}" ng-readonly="item.split_status == 1 || item.split_status == 3" name="pay_bank" ng-model="item.pay_bank" maxlength="20" placeholder="0-20位银行名称"></div><br>
                    <div class="item"><span>支付成功流水号</span><input class="pay-transaction" ng-class="{'readonly': item.split_status == 1 || item.split_status == 3}" ng-readonly="item.split_status == 1 || item.split_status == 3" type="text" name="pay_success_sequence" ng-model="item.pay_success_sequence" maxlength="40" placeholder="8-40位英文和数字"></div>
                </div>
                <button class="confirm-btn" ng-if="isConfirmBtnShow(studentRole, item.payTypeSelect) && item.split_status != 3" ng-click="confirmPaySplitBtn($index, item.key_id)">确认</button>
                </form>
            </li>
        </ul>
        <hr>
        <p>
            <button id="sentForm" ng-click="sentFormBtn()">发送</button> 
        </p>
    </div>
    
    <!-- 隐藏域 -->
    <div style="display: none;">
        <input type="hidden" id="hidden_cc_name" value="${cc.ccName }" />
        <input type="hidden" id="hidden_cc_belong_city" value="${cc.ccBelongCity }" />
        <input type="hidden" id="hidden_cc_belong_center" value="${cc.ccBelongCenter }" />
    </div>
</html>
