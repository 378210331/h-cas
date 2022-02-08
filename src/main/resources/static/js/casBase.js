/**
 * 判断是否已登录
 * @returns {boolean}
 */
function isLogin(){
    if (!window.localStorage) {
        alert('This browser does NOT support');
        return false;
    }
    const token = localStorage.getItem("TGC");
    if (token) {
        $.ajax({
            type: "GET",
            url: "/cas/ticket",
            async: false,
            beforeSend: function (request) {
                request.setRequestHeader("Authorization", token);
            },
            success: function (res) {
                if (res.success) {
                    sendRedirect(res.result);
                } else {
                    localStorage.removeItem("TGC");
                    return false;
                }
            },
            error: function (e) {
                localStorage.removeItem("TGC");
                return false;
            }
        });
    }else{
        return false;
    }
}

/**
 * 登出成功后回调
 * @param ticket
 */
function afterLoginSuccess(token,ticket){
    localStorage.setItem("TGC", token);
    sendRedirect(ticket);
}

/**
 * 跳转子系统
 * @param ticket
 */
function sendRedirect(ticket){
    var service = $("#service").val();
    if (service) {
        if(service.indexOf("?") > -1){
            window.location.replace(service + "&ticket=" + ticket);
        }else{
            window.location.replace(service + "?ticket=" + ticket);
        }
    } else {
        window.location.replace( "/cas/defaultSuccess");
    }
}
