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
    var id = element.getAttribute("id");
    $.ajax({
        url: projectName + '/message/likes?id=' + id,
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

            // 从服务器再拿到 id 号 用于删除和点赞行为行为
            $("#ajaxConten").text($('textarea[name="content"]').val());
            $("#ajaxAuthor").text($('input[name="author"]').val());
            $("#ajaxDate").text('1秒前');
            $("#ajaxCard").show(1000);

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
    $("#ajaxCard").hide();
})