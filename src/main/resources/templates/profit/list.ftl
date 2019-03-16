<!DOCTYPE html>
<html lang="zh-CN">
    <#include "../common/head.ftl">
<body>
<div class="container">
<#--导航栏-->
    <#include "../common/nav-tabs.ftl">
<#--结算按钮-->
    <div class="row">
        <div class="col-md-12">
            <a href="/profit/sum" type="button" class="btn btn-info pull-right">结算</a>
        </div>
    </div>

    <div class="row">
    <#--结算导航-->
        <#include "../common/nav-profit.ftl">
    <#--表格主体-->
        <div class="col-md-10 padding='0'">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>结算日期</th>
                    <th>销售总额</th>
                    <th>产品成本</th>
                    <th>盈利金额</th>
                    <th>详情</th>
                </tr>
                </thead>
                <tbody>
                    <#list profitInfoList as profitInfo>
                    <tr>
                        <td>${(profitInfo.updateTime)?string('yyyy-MM-dd')!""}</td>
                        <td>${(profitInfo.saleAmount)!""}</td>
                        <td>${(profitInfo.costPrice)!""}</td>
                        <td>${(profitInfo.profit)!""}</td>
                        <td>
                            <a href="/profit/details?profitId=${profitInfo.profitId}">查看</a>
                        </td>
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