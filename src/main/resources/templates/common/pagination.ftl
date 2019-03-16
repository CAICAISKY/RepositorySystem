<ul class="pagination pull-right">
    <#if currentPage lte 1>
        <li class="disabled"><a href="#">上一页</a></li>
    <#elseif providerId??>
        <li><a href="${pageUrl}?providerId=${providerId}&page=${currentPage - 1}">上一页</a></li>
    <#else>
        <li><a href="${pageUrl}?page=${currentPage - 1}">上一页</a></li>
    </#if>

    <#list 1..totalPages as index>
        <#if index == 0>

        <#elseif currentPage == index>
            <li class="disabled"><a href="#">${index}</a></li>
        <#elseif providerId??>
            <li><a href="${pageUrl}?providerId=${providerId}&page=${index}">${index}</a></li>
        <#else>
            <li><a href="${pageUrl}?page=${index}">${index}</a></li>
        </#if>
    </#list>

    <#if currentPage gte totalPages>
        <li class="disabled"><a href="#">下一页</a></li>
    <#elseif providerId??>
        <li><a href="${pageUrl}?providerId=${providerId}&page=${currentPage + 1}">下一页</a></li>
    <#else>
        <li><a href="${pageUrl}?page=${currentPage + 1}">下一页</a></li>
    </#if>
</ul>