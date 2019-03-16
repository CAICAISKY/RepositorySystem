<!DOCTYPE html>
<html lang="zh-CN">
    <#include "../common/head.ftl">
    <body>
    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <#include "../common/nav-tabs.ftl">
                <a href="/provider/index" type="button" class="btn btn-default btn-info">添加厂家</a>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>厂家编号</th>
                            <th>厂家名称</th>
                            <th>厂家电话</th>
                            <th>厂家联络人</th>
                            <th>厂家地址</th>
                            <th colspan="2">操作</th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list providerInfoList as providerInfo>
                        <tr>
                            <td>${(providerInfo.providerId)!""}</td>
                            <td>${(providerInfo.providerName)!""}</td>
                            <td>${(providerInfo.providerPhone)!""}</td>
                            <td>${(providerInfo.providerLiaison)!""}</td>
                            <td>${(providerInfo.providerAddress)!""}</td>
                            <td>
                                <a href="/provider/index?providerId=${providerInfo.providerId}">修改</a>
                            </td>
                            <td>
                                <a href="/provider/delete?providerId=${providerInfo.providerId}">删除</a>
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