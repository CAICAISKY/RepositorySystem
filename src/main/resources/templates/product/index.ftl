<!DOCTYPE html>
<html lang="zh-CN">
    <#include "../common/head.ftl">
    <body>
    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <#include "../common/nav-tabs.ftl">
                <br/>
                <form role="form" method="post" action="/product/save" enctype="multipart/form-data">
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">厂家</span>
                            <select name="providerId" class="form-select-button form-control">
                                <#list providerVOList as providerVO>
                                    <#if (productInfo.providerId) ?? && (productInfo.providerId) == (providerVO.providerId)>
                                        <option selected value="${providerVO.providerId}">${providerVO.providerName}</option>
                                    <#else>
                                        <option value="${providerVO.providerId}">${providerVO.providerName}</option>
                                    </#if>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">产品名称</span>
                            <input name="productName" class="form-control" type="text" value="${(productInfo.productName)!""}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">产品货号</span>
                            <input name="productNo" class="form-control" type="text" value="${(productInfo.productNo)!""}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">产品条码</span>
                            <input name="productCode" class="form-control" type="number" value="${(productInfo.productCode)!""}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">产品图片</span>
                            <#--<input name="productIcon" class="form-control" type="text" value="${(productInfo.productIcon)!""}"/>-->
                            <input name="file" type="file"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">产品库存</span>
                            <input name="productStock" class="form-control" type="number" value="${(productInfo.productStock)!0}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">卖出件数</span>
                            <input name="saleCount" class="form-control" type="number" value="${(productInfo.saleCount)!0}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">在售件数</span>
                            <input name="onSaleCount" class="form-control" type="number" value="${(productInfo.onSaleCount)!0}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">进货价格</span>
                            <input name="productPrice" class="form-control" type="text" value="${(productInfo.productPrice)!0}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">零售价格</span>
                            <input name="retailPrice" class="form-control" type="text" value="${(productInfo.retailPrice)!0}"/>
                        </div>
                    </div>
                    <input hidden name="productId" type="text" value="${(productInfo.productId)!""}"/>
                    <input hidden name="groupId" type="text" value="${userVO.groupId}"/>
                    <button type="submit" class="btn btn-default">提交</button>
                </form>
            </div>
        </div>
    </div>
    </body>
</html>