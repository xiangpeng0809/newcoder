$(function(){
   $("#uploadForm").submit(upload);
});

function upload() {
    $.ajax({
        url: "http://up-z2.qiniup.com",
        method: "post",
        processData: false,
        contentType: false,
        data: new FormData($("#uploadForm")[0]),
        success: function(data) {
            if(data && data.code == 0) {
                // 更新头像
                $.post(
                    CONTEXT_PATH + "/user/header/url",
                    {"fileName":$("input[name='key']").val()},
                    function(data){
                        data = $.parseJSON(data);
                        if (data.code == 0) {
                            window.location.href = CONTEXT_PATH + "/index";
                        } else {
                            alert(data.msg);
                        }
                    }
                );
            } else {
                alert("上传失败！");
            }
        }
    });

    return false;
}