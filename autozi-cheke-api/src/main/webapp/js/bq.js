$(function(){
    //获取要定位元素距离浏览器顶部的距离
    var ele=$(".video_title").offset();
    var navH = 0;
    if(ele!=undefined){
        navH = ele.top;

    }
    var videoTitle = $(".video_title");
    //滚动条事件
    $(window).scroll(function(){
        //获取滚动条的滑动距离
        var scroH = $(this).scrollTop();
        //滚动条的滑动距离大于等于定位元素距离浏览器顶部的距离，就固定，反之就不固定
        if(scroH>=navH){
            //videoTitle.css({"position":"fixed","top":0,"left":'0',"z-index":'99','-webkit-overflow-scroll':'touch'});
            $(".video_title_two").css({opacity:"1"});
            //$(".add_video_title").css("height",$(".video_title").height()+'px');
        }else if(scroH<navH){
             //$(".add_video_title").css("height","0px");
            //videoTitle.css({"position":"static"});
            $(".video_title_two").css({opacity:"0"});
        }
    })

    //所有的p 下面带标签内带border属性的 宽度自适应
    $(".tex_center img[border='0']").attr("style","width: auto !important;display:inline-block;");
    //$(".tex_center img[border='0']").each(function(){
    //    if($(this).attr("border") == 0){
    //        $(this).attr("style","width: auto !important;display:inline-block;");
    //    }
    //})

})

