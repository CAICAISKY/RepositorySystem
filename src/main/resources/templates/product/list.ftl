<!DOCTYPE html>
<html lang="zh-CN">
    <#include "../common/head.ftl">
    <body>
    <div class="container">
        <#--导航栏-->
        <#include "../common/nav-tabs.ftl">

        <#--搜索栏-->
        <#include "../common/search.ftl">

        <#--表格主体-->
        <div class="row">
            <div class="col-md-12">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>产品条码</th>
                        <th>产品名称</th>
                        <th>产品图片</th>
                        <th>产品货号</th>
                        <th>进货价</th>
                        <th>零售价</th>
                        <th>库存</th>
                        <th>上架件数</th>
                        <th>月售件数</th>
                        <th>更新时间</th>
                        <th colspan="3">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list productInfoList as productInfo>
                    <tr>
                        <td>${(productInfo.productCode)!""}</td>
                        <td>${(productInfo.productName)!""}</td>
                        <td>
                            <img height="100" width="100" alt="" src="${(productInfo.productIcon)!''}" class="img-rounded" onclick="showImage('${(productInfo.productIcon)!''}')"/>
                        </td>
                        <td>${(productInfo.productNo)!""}</td>
                        <td>${(productInfo.productPrice)!""}</td>
                        <td>${(productInfo.retailPrice)!""}</td>
                        <td>${(productInfo.productStock)!""}</td>
                        <td>${(productInfo.onSaleCount)!""}</td>
                        <td>${(productInfo.saleCount)!""}</td>
                        <td>
                            ${(productInfo.updateTime)?string('yyyy-MM-dd HH:mm:ss')!""}
                        </td>
                        <td>
                            <a data-toggle="modal" data-target="#myModal" onclick="setSaleProductId('${productInfo.productId}')">
                                卖出
                            </a>
                        </td>
                        <td>
                            <a href="/product/index?productId=${productInfo.productId}">修改</a>
                        </td>
                        <td>
                            <a href="/product/delete?productId=${productInfo.productId}&providerId=${providerId!""}">删除</a>
                        </td>
                    </tr>
                    </#list>
                    </tbody>
                    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title" id="myModalLabel">卖出</h4>
                                </div>
                                <div class="modal-body">
                                    <form role="form" method="post" action="/product/sale">
                                        <div class="form-group">
                                            <div class="input-group">
                                                <span class="input-group-addon">卖出件数</span>
                                                <input name="number" class="form-control" type="number" required/>
                                            </div>
                                        </div>
                                        <input id="saleProductId" hidden name="productId" type="text"/>
                                        <button type="submit" class="btn btn-primary">保存</button>
                                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </table>
                <#include "../common/pagination.ftl">
            </div>
        </div>
    </div>
    </body>

    <script>
        function showImage(source) {
            window.open(source)
        }

        function setSaleProductId(productId) {
            $('#saleProductId').val(productId)
        }

    </script>
</html>