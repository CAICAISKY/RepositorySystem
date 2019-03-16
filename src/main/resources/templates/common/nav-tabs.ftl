
<div class="row">
    <div class="col-md-12">
        <ul class="nav nav-pills">
            <li <#if pageUrl == "/provider/list">class="active"</#if> >
                <a href="/provider/list">厂家</a>
            </li>
            <li <#if pageUrl == "/product/list">class="active"</#if> >
                <a href="/product/list">产品</a>
            </li>
            <li <#if pageUrl == "/stock/list">class="active"</#if> >
                <a href="/stock/list">库存</a>
            </li>
           <#-- <li  <#if pageUrl == "/order/list">class="active"</#if> >
                <a href="/wait/index">进货</a>
            </li>-->
            <li <#if pageUrl == "/profit/list">class="active"</#if> >
                <a href="/profit/list">盈利</a>
            </li>
            <li class="dropdown pull-right">
                <a href="#" data-toggle="dropdown" class="dropdown-toggle">${userVO.userName}<strong class="caret"></strong></a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="/user/logout">退出</a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</div><br/>