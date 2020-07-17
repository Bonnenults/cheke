/***   Admin   ***/
var ArticleAdmin = {};


/**
 * 下线
 * @param articleId
 */
ArticleAdmin.offline = function(articleId) {
    mizhu.confirm('提示', '确认是否下线？', function(flag) {
        if(flag) {
            $.ajax({
                type: "POST",
                url: contextpath + '/article/admin/update/offLineArticle.action',
                data: {id: articleId},
                dataType: "json",
                cache: false,
                success: function (data) {
                    if (data.code != "ok") {
                        mizhu.alert(data.msg);
                    } else {
                        App.commonFormSubmit('filter_form');
                    }
                }
            });
        }
    });
};

/**
 * 审核通过还是不通过
 */
ArticleAdmin.auditArticle = function(flag) {
    var refuseReason = $("#refuseReason").val() || '';
    var articleId = $("#articleId").val();
    var alertMsg = "";

    if(flag == 0) {
        if(refuseReason == '') {
            mizhu.alert("请输入拒绝原因");
            return;
        }
        alertMsg = "拒绝成功";
    }else {
        alertMsg = "审核通过成功";
    }


    jQuery.ajax({
        type:"POST",
        url:contextpath + '/article/admin/update/audit.action',
        data:{articleId:articleId,auditFlag:flag,refuseReason:refuseReason},
        dataType:"json",
        error:function(XMLHttpRequest, textStatus, errorThrown){
            alert('系统出现错误，请稍候再试');
        },
        success:function(data){
            if("ok"== data.code){
                mizhu.confirmAlert(alertMsg,function(flag){
                    App.jump("/article/admin/list/listArticle.action");
                });
            }else{
                mizhu.alert(data.msg);
            }
        }
    });
}


/**
 * 复制到待发布
 * @param articleId
 */
ArticleAdmin.copyArticle = function(articleId) {
    mizhu.confirm('提示', '确认复制？', function(flag) {
        if(flag) {
            $.ajax({
                type: "POST",
                url:contextpath +  '/article/yxpt/update/copyArticle.action',
                data: {id: articleId},
                dataType: "json",
                cache: false,
                success: function (data) {
                    if (data.code != "ok") {
                        mizhu.alert(data.msg);
                    } else {
                        mizhu.alert("导入成功");
                        App.commonFormSubmit('filter_form');
                    }
                }
            });
        }
    });
};



ArticleAdmin.changeStatus = function(status) {
    $("#status").val(status);
    $("[name='type']").val("");
    $("[name='aIsTask']").val("");
    $("[name='partyClass']").val("");
    $("[name='channelType']").val("");

    //alert(status);
    if(status == 0) { //待发布
        $("#batch_publish").hide();
        $("#batch_cannel").hide();
        $("#batch_offline").hide();
        $("#batch_copy").hide();

        $("#create_time_span").show();
        $("#publish_time_span").hide();
        $("#end_time_span").hide();
        $("#commit_time_span").hide();
        $("#aIsTask").val("");
        $(".qy_center_seach input").val("");
    }else if(status == 1) {
        $("#status_span").hide();
        $("#batch_publish").hide();
        $("#batch_cannel").hide();

        $("#commit_time_span").show();
        $("#offline_time_span").hide();
        $("#publish_time_span").hide();

        $(".qy_center_seach input").val("");

    }else if(status == 2) {
        $("#status_span").hide();
        $("#batch_publish").hide();
        $("#batch_cannel").hide();

        $("#commit_time_span").hide();
        $("#offline_time_span").hide();
        $("#publish_time_span").show();

        $(".qy_center_seach input").val("");
    }else if(status == 4) {
        $("#status_span").hide();
        $("#batch_publish").hide();
        $("#batch_cannel").hide();

        $("#commit_time_span").hide();
        $("#offline_time_span").show();
        $("#publish_time_span").show();

        $(".qy_center_seach input").val("");
    }else if(status == -1){
        $("#status_span").hide();
        $("#batch_publish").hide();
        $("#batch_cannel").hide();

        $("#commit_time_span").show();
        $("#offline_time_span").hide();
        $("#publish_time_span").hide();

        $("#allFilter").val("0");

        $(".qy_center_seach input").val("");

    }else if(status == -100) {
        $("#status_span").show();
        $("#batch_publish").hide();
        $("#batch_cannel").hide();

        $("#commit_time_span").hide();
        $("#offline_time_span").hide();
        $("#publish_time_span").show();

        $("#allFilter").val("1");

        $(".qy_center_seach input").val("");
    }
    App.commonFormSubmit('filter_form');
}

ArticleAdmin.validator = null;
ArticleAdmin.initArticleAdmin = function(){
    //查询页面
    $("#channel_type a").click(function(){
        $("#channelType").val($(this).attr("data-title"));
    });

    $("#article_status a").click(function(){
        $("#status").val($(this).attr("data-title"));
    });

    $("#is_task a").click(function() {
        $("#aIsTask").val($(this).attr("data-title"));
    });

    $("#party_class a").click(function(){
        $("#partyClass").val($(this).attr("data-title"));
    });

    //添加验证
    var validator = new Clover.validator.Validator("#form_data");
    ArticleAdmin.validator = validator;
    validator.addValidation("[name='a_is_top']", "value>=0","排序序号必须大于等于0");
    var validator2 = new Clover.validator.Validator("#hz_popup");
    ArticleAdmin.validator2 = validator2;
    validator2.addValidation("[name='space']", "value>0","推送频率必须大于0");

}


//------------------------

ArticleAdmin.jumpCreatePage = function() {
    if(!Index.isHashAuthor()) {
        return;
    }
    App.jump("/article/ad/create/createArticle.action");

}

/**
 * 发布
 * @param articleId
 */
ArticleAdmin.publish = function(articleId) {
    mizhu.confirm('提示', '确认发布？', function(flag) {
        if(flag) {
            $.ajax({
                type: "POST",
                url: contextpath + '/article/ad/update/publishAd.action',
                data: {id: articleId},
                dataType: "json",
                cache: false,
                success: function (data) {
                    if (data.code != "ok") {
                        mizhu.alert(data.msg);
                    } else {
                        App.commonFormSubmit('filter_form');
                    }
                }
            });
        }
    });
};

//设置广告排序弹框

ArticleAdmin.sortAticle=function(articleId) {

    $(".mask_float").show();
    $("#sort_popup").show();
    //弹出框标题新增分类
    $("#new_fication .bomb_title").text("设置排序");
    $("#article_id").val(articleId);
    $("#a_is_top").val(0);

};

/**
 * 保存或修改广告排序
 */
ArticleAdmin.saveOrUpdateSort = function() {
    if(ArticleAdmin.validator.validate()) {
        var article_id = $("#article_id").val();
        var a_is_top = $("#a_is_top").val();

        if(a_is_top==""){
            a_is_top=0;
        }else if(a_is_top<0){
            mizhu.alert("优先级序号必须大于等于0");
        }

        jQuery.ajax({
            type:"POST",
            url:contextpath + '/article/admin/update/sort.action',
            data:{
                articleId:article_id,
                a_is_top:a_is_top
            },
            dataType:"json",
            error:function(XMLHttpRequest, textStatus, errorThrown){
                alert('系统出现错误，请稍候再试');
            },
            success:function(data){

                $(".mask_float").hide();
                $("#sort_popup").hide();

                if (data.code != "ok") {
                    mizhu.alert(data.msg);
                } else {
                    App.commonFormSubmit('filter_form');

                }
            }
        });
    }
}

/**
 * 设置推送频率
 */
ArticleAdmin.setAdHz = function() {
    $(".mask_float").show();
    $("#hz_popup").show();
}
/**
 * 保存或修改推送频率
 */
ArticleAdmin.saveOrUpdateHz = function() {
    if(ArticleAdmin.validator.validate()) {
        var space = $("#space").val();
        if(space<=0){
            mizhu.alert("推送频率需大于0");
        }

        jQuery.ajax({
            type:"POST",
            url:contextpath + '/basic/properties/update/updateSpace.action',
            data:{
                space:space
            },
            dataType:"json",
            error:function(XMLHttpRequest, textStatus, errorThrown){
                alert('系统出现错误，请稍候再试');
            },
            success:function(data){
                $(".mask_float").hide();
                $("#hz_popup").hide();
                if("ok"== data.code){
                    mizhu.alert("修改成功");
                    App.commonFormSubmit('filter_form');
                }else{
                    mizhu.alert(data.msg);
                }
            }
        });
    }
}
/**
 * 保存或修改文章
 */
ArticleAdmin.saveOrUpdate = function() {
    if(Video.validator.validate()) {
        if($("#aIsTask").val() == 1) {
            //B<C<A 判断  start
            var allCost=Number($("#allCost").val()),
                onceCost=Number($("#onceCost").val()),
                mostCost=Number($("#mostCost").val());
            if(onceCost>=mostCost){
                mizhu.confirm('提示', '用户分享每次点击金额需小于用户分享点击总数最多获得金额' );
                return;
            }else if(onceCost>=allCost){
                mizhu.confirm('提示', '用户分享每次点击金额需小于费用总投入' );
                return;
            }else if( mostCost>=allCost){
                mizhu.confirm('提示', '费用总投入需大于用户分享点击总数最多获得金额' );
                return;
            }else if( onceCost<0.1){
                mizhu.confirm('提示', '推广按点击进行付费,单次点击费用不少于0.1元' );
                return;
            }else{

            }
            //B<C<A 判断  end
        }
        var tagIds = "";
        $(":input[name='article_tag']:checked").each(function(){
            tagIds += $(this).val() + ","
        })
        tagIds = tagIds.substr(0,tagIds.length - 1);
        if(tagIds.length <= 0) {
            mizhu.alert("请先选择一个培训分类");
            return;
        }

        if($("#image").val() == '') {
            mizhu.alert("请上传视频缩略图");
            return;
        }
        if($("#body").val() == '') {
            mizhu.alert("培训正文不能为空");
            return;
        }

        var data = $("#form_data").serialize() + "&tag="+ tagIds;

        var remindMsg = '';
        if($(":input[name='a_is_task']:checked").val() == 1) {
            remindMsg = "费用总投入："  + $("#allCost").val() + "元<br/>" + "单次点击："+ $("#onceCost").val() + "元<br/>" + "一个用户单条任务最多可获得：" + $("#mostCost").val() + "元<br/>";
        }else {
            remindMsg = "确认提交";
        }
        mizhu.confirm('提示', remindMsg, function(flag) {
            if(flag) {
                jQuery.ajax({
                    type:"POST",
                    url:contextpath + '/video/yxpt/create/createOrUpdateArticle.action',
                    data:data,
                    dataType:"json",
                    error:function(XMLHttpRequest, textStatus, errorThrown){
                        mizhu.alert('系统出现错误，请稍候再试');
                    },
                    success:function(data){
                        if("ok"== data.code){
                            App.jump("/video/yxpt/list/listArticle.action");
                        }else{
                            mizhu.alert(data.msg);
                        }
                    }
                });
            }
        });
    }
}
