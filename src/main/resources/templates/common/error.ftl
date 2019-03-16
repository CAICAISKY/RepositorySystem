<!DOCTYPE html>
<html lang="zh-CN">
    <#include "./head.ftl">
    <body>
    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <div class="alert alert-dismissable alert-danger">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    <h4>
                        出现异常!
                    </h4>
                    <strong>${msg!""}</strong>
                    <a href="${url}" class="alert-link">3秒后自动返回</a>
                </div>
            </div>
        </div>
    </div>
    </body>
    <script>
        setTimeout(function () {
            location.href = "${url}"
        }, 3000)
    </script>
</html>