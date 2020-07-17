$(function(){
   /*左侧菜单*/
    setHeight();
    $(window).resize(function() {
        setHeight();
    });
    function setHeight(){
        var cenHeight = $(window).height()-$(".lf_header").height()-30;
        $(".lf_carLeft").css('height',cenHeight);
        var cenWeight = $(window).width()-$(".lf_carLeft").width();
        $(".lf_carRight").css('width',cenWeight);
    }
    $(".lf_left_icon_10 a").addClass('act');
    $('.lf_carList a').click(function(){
        if($(this).hasClass('act')){
            $(this).removeClass('act').next().hide();
        }else{
            $('.lf_carList a').removeClass('act');
            $('.lf_carList ul').hide();
            $('.lf_carList li').removeClass('act');
            $(this).addClass('act').next().show();
        }
    });
    $('.lf_carList li').click(function(){
        $('.lf_carList a').removeClass('act');
        $('.lf_carList li').removeClass('act');
        $(this).addClass('act');
    });
    
    $('.money_bt span').hover(function(){
        $(this).next().show();
    },function(){
        $(this).next().hide();
    });
    //带下拉列表的input不能输入
    $(".project_category input").attr("readonly","true");
    //点击文本框显示下拉菜单
	$(".project_category input").click(function(){
		$(this).next().show();
		return false;
	});
	//点击下拉当前值传输到输入框
	$(".border_lists a").click(function(){
		$(this).parent().prev().val($(this).text());
	});
    //点击body其他区域下拉菜单隐藏
	$("body").click(function(){
		$(".border_lists").hide();
	})
    $(".inves_all_money").on("click",function(e){
        $(".border_lists").hide();
        $(this).parent().find(".border_lists").show();
    })
    $('.qy_center_tab .clear li').click(function () {
        var index = $(this).index();
        $(".qy_center_tab .clear li").removeClass('act');
        $(this).attr('class',"act");
        $('.qy_center_mains').removeClass("act");
        $('.qy_center_mains').eq(index).addClass("act");
    });
    
    //遮罩层弹出框
    $(".bomb_close").click(function(){
		$(this).parent().parent().hide();
		$(".mask_float").hide();
	});
	$(".mask_float").click(function(){
		$(".mask_float").hide();
		$(".bomb_box").hide();
	})

	//用户注册协议弹框
	$("#car_retext").click(function(){
		$(".mask_float").show();
		$("#regist_agreement").show();
	});
	//点击我已了解关闭弹出框遮罩
	$("#my_got_it").click(function(){
		$(".mask_float").hide();
		$("#regist_agreement").hide();
	})
	$("#cancel_edit").click(function(){
		$(".mask_float").hide();
		$("#bomb_password").hide();
	})
	$("#confirm_edit").click(function(){
		$(".mask_float").hide();
		$("#bomb_password").hide();
	})
    $("*[name='menuUrl']").click(function(){
        top.frames[2].location.href =$(this).attr("url");
    });

    //新增分类弹框
    $("#add_fication").click(function(){
        $(".mask_float").show();
        $("#new_fication").show();
        //弹出框标题新增分类
        $("#new_fication .bomb_title").text("新增分类");
        $("#tag_id").val("");
        $("#tag_code").val("");
        $("#tag_name").val("");

        $("#article_channel_type").val("");
        $(".inves_all_money").val("请选择");
    });

    //修改分类弹框
    $(document).on('click','.edit_fication',function(){
        $(".mask_float").show();
        $("#new_fication").show();
        //弹出框标题修改分类
        $("#new_fication .bomb_title").text("修改分类");
        $("#article_channel_type").val($(this).parent().prev().prev().val());
        $("#tag_id").val($(this).parent().prev().val());
        $("#tag_code").val($(this).parent().prevAll(".infomy_code").text());
        $("#tag_name").val($(this).parent().prevAll(".infomy_name").text());

        var channelType = parseInt($("#article_channel_type").val());
        if(channelType != '') {
            var channelTypeName = "";
            switch(channelType) {
                case 1:
                    channelTypeName = "资讯"
                    break;
                case 2:
                    channelTypeName = "培训"
                    break;
                case 3:
                    channelTypeName = "项目"
                    break;
                default:
                    channelTypeName = "请选择"
            }
            $(".inves_all_money").val(channelTypeName);
        }


    });

    //点击确认关闭弹出框遮罩
    $(".confirmation_pwd").click(function(){
        //$(".mask_float").hide();
        //$("#new_fication").hide();
    })

    //点击取消关闭弹出框遮罩
    $(".fication_cancel").click(function(){
        $(".mask_float").hide();
        $("#new_fication").hide();
        $("#sort_popup").hide();
        $("#add_popup").hide();
        $("#hz_popup").hide();
        $("#new_rule").hide();
    })

    $(".qy_seach_cz").click(function(){
        $(".qy_center_seach input").val("");
    })


$("#onceCost").on('keyup', function (event) {
     var once_money = $(this);
        once_money.val(once_money.val().replace(/[^\d.]/g, "").replace(/^0{1}?([0-9]{1,})$/g, "0").
             //只允许一个小数点
              replace(/^\./g, "").replace(/\.{2,}/g, ".").replace(/\.0/g, "").
             //只能输入小数点后1位
              replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d).*$/, '$1$2.$3'));
    });
$(".inves_all_money").on('keyup', function (event) {
     var once_money = $(this);
        once_money.val(once_money.val().replace(/[^\d.]/g, ""));
        var val=once_money.val();
        if(val&&val>0){
            once_money.val(parseInt(val))
        }else{
             once_money.val("")
        }
    });
});
var Index={};
Index.isHashAuthor=function(){
    var verifyFlag = 0;
    var status = 0;
    $.ajax({
        type:'post',
        async:false,
        url:context+'/cheke/party/list/isHasAuthor.action',
        dataType:"json",
        success: function (data) {
            verifyFlag = data.verifyFlag;
            status = data.msg;
        }
    });
    if(verifyFlag==1){
        return true;
    }
    if(status==5){
        mizhu.alert("未完善信息，请先完善信息再进行该操作");
        return false;
    }else if(status==2 || status==3){
        mizhu.alert("用户还在审核中，不能进行该操作");
        return false;
    }else if(status==6){
        mizhu.alert("审核未通过，请重新上传资料再进行该操作");
        return false;
    }
    return false;
}






