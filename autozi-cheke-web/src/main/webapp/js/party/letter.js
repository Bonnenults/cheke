var Letter = {};

Letter.viewDetail=function(relationId){
    $(".mask_float").show();
    $("#returns_info").show();
    var ran = Math.floor(Math.random()*100000+100000);
    $("#ran").val(ran);
    $.ajax({
        url:contextpath+'/letter/cheke/list/getListByRelationId.action?relationId='+relationId,
        dataType:"json",
        success:function(data){
            var letterList = data.list;
            var userId = data.user.id;
            var partyId = data.party.id;
            var userName = data.user.name;
            var partyName = data.party.name;
            var html='';
            var userImg ="";
            if(data.user.imageUrl!=""){
                userImg = data.user.imageUrl;
            }else{
                userImg = contextpath+"/styles/images/index_top.png";
            }
            var partyImg ="";
            if(data.party.imageUrl!=""){
                partyImg = data.party.imageUrl;
            }else{
                partyImg = contextpath+"/styles/images/index_top.png";
            }

            for(var i=0;i<letterList.length;i++){
                var letter = letterList[i];
                var fromUserId = letter.fromUserId;
                var content = letter.content;
                if(fromUserId==userId){
                    html +='<div class="chat_left">';
                    html +='<img src="'+userImg+'" width="42px" height="42px"/>';
                    html +='<div class="chat_center">';
                    //html +=;
                    html +='<span class="chat_center_tel">'+userName+'</span>';
                    //html +='<span class="chat_center_tel">'+letter.createTime+'</span>';
                    html +='<p class="chat_center_text"><strong class="chat_text_icon"></strong>'+content+'</p>';
                    html +='</div>';
                    html +='</div>';
                }else{
                    html +='<div class="chat_right">';
                    html +='<div class="chat_center">';
                    html +='<strong class="chat_text_icon"></strong>';
                    html +='<span class="chat_center_tel">'+partyName+'</span>';
                    html +='<p class="chat_center_text">'+content+'</p>';
                    html +='</div>';
                    html +='<img src="'+partyImg+'" width="42px" height="42px"/>';
                    html +='</div>';
                }
            }
            $("#letterContent").html(html);
            $("#relationId").val(relationId);
            $("#userId").val(userId);
            $("#userName").val(data.user.name);
            $("#userImg").val(userImg);
            $("#partyId").val(partyId);
            $("#partyName").val(partyName);
            $("#partyImg").val(partyImg);
            $("#returns_info .return_box").scrollTop($("#returns_info .return_box")[0].scrollHeight );
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
            mizhu.alert('系统出现错误，请稍候再试');
        },
    });
    Letter.checkNewLetter(relationId,ran);
}

Letter.checkNewLetter=function(relationId,ran){
    $.ajax({
        url:contextpath+'/letter/cheke/list/checkNewLetter.action?relationId='+relationId+'&ran='+ran,
        dataType:"json",
        success:function(data){
            var random = $("#ran").val();
            if(data.flag=="ok" && data.ran==random){
                var letterList = data.list;
                var userName = data.user.name;
                var html='';
                var userImg ="";
                if(data.user.imageUrl!=""){
                    userImg = data.user.imageUrl;
                }else{
                    userImg = contextpath+"/styles/images/index_top.png";
                }

                for(var i=0;i<letterList.length;i++){
                    var letter = letterList[i];
                    var content = letter.content;
                    html +='<div class="chat_left">';
                    html +='<img src="'+userImg+'" width="42px" height="42px"/>';
                    html +='<div class="chat_center">';
                    //html +=;
                    html +='<span class="chat_center_tel">'+userName+'</span>';
                    //html +='<span class="chat_center_tel">'+letter.createTime+'</span>';
                    html +='<p class="chat_center_text"><strong class="chat_text_icon"></strong>'+content+'</p>';
                    html +='</div>';
                    html +='</div>';
                }
                $("#letterContent").append(html);
                $("#returns_info .return_box").scrollTop($("#returns_info .return_box")[0].scrollHeight );
                setTimeout(function() {
                    Letter.checkNewLetter(relationId,ran)
                },2000);
            }else{
                if($("#returns_info").is(":visible") && data.ran==random){
                    setTimeout(function() {
                        Letter.checkNewLetter(relationId,ran)
                    },2000);
                }
            }
        }
    });
}

Letter.returnLetter=function(){
    var content = $("#content").val();
    var userId = $("#userId").val();
    var userName = $("#userName").val();
    var userImg = $("#userImg").val();
    var partyId = $("#partyId").val();
    var partyName = $("#partyName").val();
    var partyImg = $("#partyImg").val();
    var relationId = $("#relationId").val();
    if(!content){
        mizhu.confirmAlert("请输入回复内容",function(flag){});
        return;
    }
    if(content.length>100){
        mizhu.confirmAlert("回复字数不能超过100个",function(flag){});
        return;
    }
    $.ajax({
        url:contextpath+'/letter/cheke/return/returnLetter.action',
        data:{"fromUserId":partyId,"toUserId":userId,"relationId":relationId,"content":content},
        dataType:"json",
        success:function(data){
            if(data.msg=="ok"){
                var html = '';
                html +='<div class="chat_right">';
                html +='<div class="chat_center">';
                html +='<strong class="chat_text_icon"></strong>';
                html +='<span class="chat_center_tel">'+partyName+'</span>';
                html +='<p class="chat_center_text">'+content+'</p>';
                html +='</div>';
                html +='<img src="'+partyImg+'"/>';
                html +='</div>';
                $("#letterContent").append(html);
                //top.frames[0].location.href ="/top.action";
                //回复私信条数滚动条在最底部
                 $("#returns_info .return_box").scrollTop($("#returns_info .return_box")[0].scrollHeight );
            }
            $("#content").val("");
            $(".qy_seach_cx").click();
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
            mizhu.alert('系统出现错误，请稍候再试');
        },
    });
}

Letter.viewPartyDetail = function(partyId){
    window.location.href=contextpath+'/letter/admin/list/listPartyLetter.action?partyId='+partyId;
}