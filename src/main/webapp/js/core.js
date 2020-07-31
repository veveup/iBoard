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
