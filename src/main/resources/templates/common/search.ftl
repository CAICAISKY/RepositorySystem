<div class="row">
    <div class="col-md-1">
        <a href="/product/index" type="button" class="btn btn-default btn-info">添加产品</a>
    </div>
    <div class="col-md-3">
        <form method="post" action="${pageUrl}">
            <div class="input-group">
                <input type="text" name="productCode" class="form-control" placeholder="根据产品条码查找">
                <span class="input-group-btn">
                    <button class="btn btn-default" type="submit">查询</button>
                </span>
            </div>
        </form>
    </div>
    <div class="col-md-3">
        <form method="post" action="${pageUrl}">
            <div class="input-group">
                <input type="text" name="productNo" class="form-control" placeholder="根据产品货号查找">
                <span class="input-group-btn">
                    <button class="btn btn-default" type="submit">查询</button>
                </span>
            </div>
        </form>
    </div>
    <div class="col-md-5">
        <div class="btn-group pull-right">
            <button type="button" class="btn btn-success">选择厂家</button>
            <button type="button" class="btn btn-success dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <span class="caret"></span>
                <span class="sr-only">Toggle Dropdown</span>
            </button>
            <ul class="dropdown-menu">
                <#list providerVOList as providerVO>
                    <li><a href="${pageUrl}?providerId=${providerVO.providerId}">${providerVO.providerName}</a></li>
                </#list>
            </ul>
        </div>
    </div>
</div>