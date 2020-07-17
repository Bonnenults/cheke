var LeaveWord = {};

LeaveWord.initLeaveWord = function(){
    //查询页面
    $("#channel_type a").click(function(){
        $("#channelType").val($(this).attr("title"));
    });

}
