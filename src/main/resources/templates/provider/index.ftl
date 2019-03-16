<!DOCTYPE html>
<html lang="zh-CN">
    <#include "../common/head.ftl">
    <body>
    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <#include "../common/nav-tabs.ftl">
                <br/>
                <form role="form" method="post" action="/provider/save">
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">厂家名称</span>
                            <input name="providerName" class="form-control" type="text" required value="${(providerInfo.providerName)!""}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">厂家电话</span>
                            <input name="providerPhone" class="form-control" type="number" required value="${(providerInfo.providerPhone)!""}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">厂家联络人</span>
                            <input name="providerLiaison" class="form-control" type="text" value="${(providerInfo.providerLiaison)!""}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">厂家地址</span>
                            <input name="providerAddress" class="form-control" type="text" required value="${(providerInfo.providerAddress)!""}"/>
                        </div>
                    </div>
                    <input hidden name="providerId" type="text" value="${(providerInfo.providerId)!""}"/>
                    <input hidden name="groupId" type="text" value="${userVO.groupId}"/>
                    <button type="submit" class="btn btn-default">提交</button>
                </form>
            </div>
        </div>
    </div>
    </body>
</html>