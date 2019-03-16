<!DOCTYPE html>
<html lang="zh-CN">
    <#include "../common/head.ftl">
<body>
<div class="container">
<#--导航栏-->
    <#include "../common/nav-tabs.ftl">

    <div class="row">
    <#--结算导航-->
        <#include "../common/nav-profit.ftl">
    <#--表格主体-->
        <div class="col-md-10 padding='0'">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>产品名称</th>
                    <th>产品货号</th>
                    <th>卖出件数</th>
                    <th>成本</th>
                    <th>销售金额</th>
                    <th>利润</th>
                </tr>
                </thead>
                <tbody>
                    <#list profitDetailList as profitDetail>
                    <tr>
                        <td>${(profitDetail.productName)!""}</td>
                        <td>${(profitDetail.productNo)!""}</td>
                        <td>${(profitDetail.saleCount)!""}</td>
                        <td>${(profitDetail.costPrice)!""}</td>
                        <td>${(profitDetail.saleAmount)!""}</td>
                        <td>${(profitDetail.profit)!""}</td>
                    </tr>
                    </#list>
                </tbody>
            </table>
            <#include "../common/pagination.ftl">
        </div>
    </div>
</div>
</body>
</html>