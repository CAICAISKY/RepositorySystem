<!DOCTYPE html>
<html lang="zh-CN">
    <#include "../common/head.ftl">
    <body>
    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <form role="form" method="post" action="/user/login">
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">用户名</span>
                            <input name="userName" class="form-control" type="text" required/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">密码</span>
                            <input name="password" class="form-control" type="password" required/>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-default">提交</button>
                </form>
            </div>
        </div>
    </div>
    </body>
</html>