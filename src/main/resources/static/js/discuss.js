$(function(){
    $("#topBtn").click(setTop);
    $("#goodBtn").click(setGood);
    $("#deleteBtn").click(setDelete);
})

function like(btn, entityType, entityId, entityUserId, postId) {
    $.post(
        CONTEXT_PATH + "/like",
        {"entityType":entityType,"entityId":entityId,"entityUserId":entityUserId,"postId":postId},
        function(data) {
            data=$.parseJSON(data);
            if(data.code == 0) {
                $(btn).children("i").text(data.likeCount);
                $(btn).children("b").text(data.likeStatus==1?'已赞':'赞');
            } else {
                alert(data.msg);
            }
        }
    );
}

// 置顶
function setTop() {
    $.post(
        CONTEXT_PATH + "/discuss/top",
        {"id":$("#postId").val()},
        function(data) {
            data=$.parseJSON(data);
            if (data.code == 0) {
                $("#topBtn").attr("disabled","disabled");
            } else {
                alert(data.msg);
            }
        }
    );
}

// 加精
function setGood() {
    $.post(
        CONTEXT_PATH + "/discuss/good",
        {"id":$("#postId").val()},
        function(data) {
            data=$.parseJSON(data);
            if (data.code == 0) {
                $("#goodBtn").attr("disabled","disabled");
            } else {
                alert(data.msg);
            }
        }
    );
}

// 拉黑
function setDelete() {
    $.post(
        CONTEXT_PATH + "/discuss/delete",
        {"id":$("#postId").val()},
        function(data) {
            data=$.parseJSON(data);
            if (data.code == 0) {
                location.href = CONTEXT_PATH + "/index";
            } else {
                alert(data.msg);
            }
        }
    );
}