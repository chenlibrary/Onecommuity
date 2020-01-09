function doPost() {
    var questionId=$("#questionId").val();
    var content=$("#textareaText").val();
    if(!content){
        alert("回复内容不能为空");
        return
    }
    reply("post",1,questionId,content);

}

function doComment(obj) {
    console.log("id");
    var commentId=$(obj).parent().attr('id').split("-")[1];
    var content=$("#replyTow-"+commentId).val();
    console.log(commentId);
    console.log(content);
    if(!content){
        alert("回复内容不能为空");
        return
    }
   // reply("post",2,commentId,content);

}

function reply(way,mold,questionId,content) {
    $.ajax({
        type: way,
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parentId":questionId,
            "type":mold,
            "content":content
        }),
        success: function(data){
            if(data.code==200){
                window.location.reload();
                //  $("#edit").hide();
            }else{
                if(data.code==2003){
                    var isAccepted=confirm(data.message);
                    if(isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=b06ad67b925eb2fb8319&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }else{
                        alert(data.message)
                    }
                }

            }
        },
        dataType: "json"
    });
}

function commentTwoHideAndShow(e){
    var id=e.getAttribute("data-id");
    var commmentid=$("#comment-"+id);
    var show=e.getAttribute("data-show");
    if(show){
        e.removeAttribute("data-show");
        commmentid.removeClass("in");
    }else{
        $.getJSON( "/comment/"+id, function( data ) {
            console.log(data);
            var subCommment=$("#comment-"+id);
            $.each( data.data, function( index, comment) {
                subCommment.prepend($("<p></p>").text(comment.content));
            });

        });

        e.setAttribute("data-show","in");
        commmentid.addClass("in");
    }

}