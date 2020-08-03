var pathName = window.document.location.pathname;
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);


function hover(element) {
    element.setAttribute('src', projectName + '/img/favorite-24px.svg')
}

function unhover(element) {
    element.setAttribute('src', projectName + '/img/favorite_border-24px.svg')
}

function likesClick(element) {
    console.log(projectName);
    a = element.children;
    console.log(a);
    aa = a[1];
    aa.textContent = Number(aa.textContent) + 1;
    var classVal = aa.getAttribute("class");
    classVal = classVal.replace("is-hidden", "");
    aa.setAttribute("class", classVal);
    element.removeAttribute("onclick")
    a[0].removeAttribute("onmouseout");
    var mid = element.getAttribute("mid");
    $.ajax({
        url: projectName + '/message/likes?id=' + mid,
        success: function (result) {
            // 判断是否失败 若失败 则在前端提示
            console.log(result);
        },
        timeout: function (result) {
            // 失败 将前端恢复到之前状态
        }
    });
}


// 请求行为 Ajax
function submitAjax() {
    // $("#ajaxCard").show(1000);

    $.ajax({
        type: "POST",
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        url: projectName + '/message/saveAjax',
        data: JSON.stringify({
            content: $('textarea[name="content"]').val(),
            author: $('input[name="author"]').val(),
        }),
        success: function (result) {
            console.log(result);

            var id = result.id;

            submitSuccess(id);
        },
        error: function (result) {
            console.log("submitAjax 出现错误");
            console.log(result);
        }
    });
    return false;
}

// 页面加载完成 将AjaxCard隐藏 当请求成功的时候再显示出来
$(document).ready(function () {
    // 隐藏用于Ajax动态显示的模版
    $("#ajaxCard").hide();

    // 隐藏底部Toast模版
    $("#bd").fadeOut();

})

function testButton() {
    var newCard = $('#ajaxCard').clone(true, true).attr('id', 'newCard');
    console.log(newCard);
    $('#mlist').prepend(newCard);
    console.log(newCard);
    newCard.show(1000);
}

function submitSuccess(id) {
    var newCard = $('#ajaxCard').clone(true, true).attr('id', 'newCard');
    console.log(newCard);
    $('#mlist').prepend(newCard);
    console.log(newCard);

    // 设置值 并清除id

    $("#newCard #ajaxConten").text($('textarea[name="content"]').val());
    $("#newCard #ajaxAuthor").text($('input[name="author"]').val());
    $("#newCard #ajaxDate").text('1秒前');
    $('#newCard #ajaxConten').attr('id', '');
    $('#newCard #ajaxAuthor').attr('id', '');
    $('#newCard #ajaxDate').attr('id', '');
    // 删除按钮的链接 + 改成Ajax请求 不需要链接了
    // $('#newCard .delete').attr('href', projectName + '/message/deleteById?id=' + id);
    // 点赞按钮 所需的mid
    $('#newCard span').attr('mid', id);


    $("#newCard").show(1000);

    // 提交请求后 清空表单
    $('textarea[name="content"]').val("");
    $('input[name="author"]').val("");

}

function deleteAjax(element) {
    console.log(element);
    // 拿到单条message的根div
    element = $().add(element);
    var card = element.parent().parent().parent();
    card.attr("status", "now");
    console.log(card);
    mid = $("div[status='now'] span").attr('mid');
    $.ajax({
        url: projectName + '/message/deleteByIdAjax?id=' + mid,
        type: "POST",
        success: function (result) {
            console.log(result);

            if (result.status == 'error') {
                Toast(result.msg + '但已经从页面上上移除内容', 5000, 0);
            } else {
                Toast(result.msg);
            }
            card.hide(1000);
        }
    });
    card.attr("status", "");
    return false;
}

function Toast(msg, duration, status) {
    //status == 1 成功 绿色提醒 默认
    //status == 0 错误 红色提醒 ff6d8e
    if (status == 0) {
        console.log("Hello");
        $("#bd").css("background-color", "#ff6d8e");
    }
    duration = isNaN(duration) ? 3000 : duration;
    $("#bd strong").text(msg);
    $("#bd").fadeIn(500);
    setTimeout(function () {
        $("#bd").fadeOut(500);
    }, duration);
}