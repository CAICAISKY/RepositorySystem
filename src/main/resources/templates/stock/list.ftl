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
                    <th>产品库存</th>
                    <th>在售件数</th>
                    <th colspan="2">操作</th>
                </tr>
                </thead>
                <tbody>
                    <#list stockVOList as stockVO>
                    <tr>
                        <td>${(stockVO.productCode)!""}</td>
                        <td>${(stockVO.productName)!""}</td>
                        <td>
                            <img height="100" width="100" alt="" src="${(stockVO.productIcon)!''}" class="img-rounded" onclick="showImage('${(stockVO.productIcon)!''}')"/>
                        </td>
                        <td>${(stockVO.productNo)!""}</td>
                        <td>${(stockVO.productStock)!""}</td>
                        <td>${(stockVO.onSaleCount)!""}</td>
                        <td>
                            <a data-toggle="modal" data-target="#myEnter" onclick="enterStock('${stockVO.productId}')">进库</a>
                        </td>
                        <td>
                            <a data-toggle="modal" data-target="#myOut" onclick="outStock('${stockVO.productId}')">出库</a>
                        </td>
                        <div class="modal fade" id="myOut" tabindex="-1" role="dialog" aria-labelledby="myOutLabel">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                        <h4 class="modal-title" id="myOutLabel">出库</h4>
                                    </div>
                                    <div class="modal-body">
                                        <form role="form" method="post" action="/stock/out">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <span class="input-group-addon">出库件数</span>
                                                    <input name="number" class="form-control" type="number" required/>
                                                </div>
                                            </div>
                                            <input id="outProductId" hidden name="productId" type="text"/>
                                            <button type="submit" class="btn btn-primary">保存</button>
                                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal fade" id="myEnter" tabindex="-1" role="dialog" aria-labelledby="myEnterLabel">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                        <h4 class="modal-title" id="myEnterLabel">进库</h4>
                                    </div>
                                    <div class="modal-body">
                                        <form role="form" method="post" action="/stock/enter">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <span class="input-group-addon">进库件数</span>
                                                    <input name="number" class="form-control" type="number" required/>
                                                </div>
                                            </div>
                                            <input id="enterProductId" hidden name="productId" type="text"/>
                                            <button type="submit" class="btn btn-primary">保存</button>
                                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </tr>
                    </#list>
                </tbody>
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

    function outStock(productId) {
        $('#outProductId').val(productId)
    }

    function enterStock(productId) {
        $('#enterProductId').val(productId)
    }
</script>
</html>