var Video = {};

Video.jumpCreatePage = function() {
    if(!Index.isHashAuthor()) {
        return;
    }
    App.jump("/video/yxpt/create/createArticle.action");

}

/**
 * 发布
 * @param articleId
 */
Video.publish = function(articleId) {
    mizhu.confirm('提示', '确认发布？', function(flag) {
        if(flag) {
            $.ajax({
                type: "POST",
                url: contextpath + '/video/yxpt/update/publishArticle.action',
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
 * 取消发布
 * @param articleId
 */
Video.cancel = function(articleId) {
    mizhu.confirm('提示', '确认取消发布？', function(flag) {
        if(flag) {
            $.ajax({
                type: "POST",
                url:contextpath +  '/video/yxpt/update/cancelPublishArticle.action',
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
 * 下线
 * @param articleId
 */
Video.offline = function(articleId) {
    mizhu.confirm('提示', '确认下线？', function(flag) {
        if(flag) {
            $.ajax({
                type: "POST",
                url: contextpath + '/video/yxpt/update/offLineArticle.action',
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
 * 复制到待发布
 * @param articleId
 */
Video.copyArticle = function(articleId) {
    mizhu.confirm('提示', '确认复制？', function(flag) {
        if(flag) {
            $.ajax({
                type: "POST",
                url:contextpath +  '/video/yxpt/update/copyArticle.action',
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

/**
 * 批量发布
 */
Video.batchPublish = function() {
    var articleIds = "";
    $(":input[name='article_ids']:checked").each(function(){
        articleIds += $(this).val() + ","
    })
    articleIds = articleIds.substr(0,articleIds.length - 1);
    if(articleIds.length <= 0) {
        mizhu.alert("请先选择一条数据");
        return;
    }

    $.ajax({
        type: "POST",
        url: contextpath + '/video/yxpt/update/batchPublishArticle.action',
        data: {articleIds: articleIds},
        dataType: "json",
        cache: false,
        success: function (data) {
            if (data.code != "ok") {
                mizhu.alert(data.msg);
            } else {
                mizhu.confirmAlert("批量发布成功！",function(flag){
                    App.commonFormSubmit('filter_form');
                });
            }
        }
    });

}

/**
 * 批量取消发布
 */
Video.batchCancel = function() {
    var articleIds = "";
    $(":input[name='article_ids']:checked").each(function(){
        articleIds += $(this).val() + ","
    })
    articleIds = articleIds.substr(0,articleIds.length - 1);
    if(articleIds.length <= 0) {
        mizhu.alert("请先选择一条数据");
        return;
    }

    $.ajax({
        type: "POST",
        url: contextpath + '/video/yxpt/update/batchCancelArticle.action',
        data: {articleIds: articleIds},
        dataType: "json",
        cache: false,
        success: function (data) {
            if (data.code != "ok") {
                mizhu.alert(data.msg);
            } else {
                mizhu.confirmAlert("批量取消发布成功！",function(flag){
                    App.commonFormSubmit('filter_form');
                });
            }
        }
    });

}

/**
 * 批量下线
 */
Video.batchOffline = function() {
    var articleIds = "";
    $(":input[name='article_ids']:checked").each(function(){
        articleIds += $(this).val() + ","
    })
    articleIds = articleIds.substr(0,articleIds.length - 1);
    if(articleIds.length <= 0) {
        mizhu.alert("请先选择一条数据");
        return;
    }

    $.ajax({
        type: "POST",
        url: contextpath + '/video/yxpt/update/batchOfflineArticle.action',
        data: {articleIds: articleIds},
        dataType: "json",
        cache: false,
        success: function (data) {
            if (data.code != "ok") {
                mizhu.alert(data.msg);
            } else {
                mizhu.confirmAlert("批量下线成功！",function(flag){
                    App.commonFormSubmit('filter_form');
                });
            }
        }
    });

}

/**
 * 批量导入待发布
 */
Video.batchCopy = function() {
    var articleIds = "";
    $(":input[name='article_ids']:checked").each(function(){
        articleIds += $(this).val() + ","
    })
    articleIds = articleIds.substr(0,articleIds.length - 1);
    if(articleIds.length <= 0) {
        mizhu.alert("请先选择一条数据");
        return;
    }

    $.ajax({
        type: "POST",
        url: contextpath + '/video/yxpt/update/batchCopyArticle.action',
        data: {articleIds: articleIds},
        dataType: "json",
        cache: false,
        success: function (data) {
            if (data.code != "ok") {
                mizhu.alert(data.msg);
            } else {
                mizhu.confirmAlert("批量导入待发布！",function(flag){
                    App.commonFormSubmit('filter_form');
                });
            }
        }
    });

}




/**
 * 保存或修改文章
 */
Video.saveOrUpdate = function() {
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


Video.saveOrUpdateForadmin = function() {
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

        if($("#image").val() == '' ) {
            mizhu.alert("请上传缩略图");
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
                    url:contextpath + '/article/ad/create/createOrUpdateArticle.action',
                    data:data,
                    dataType:"json",
                    error:function(XMLHttpRequest, textStatus, errorThrown){
                        mizhu.alert('系统出现错误，请稍候再试');
                    },
                    success:function(data){
                        if("ok"== data.code){
                            App.jump("/article/ad/list/listAd.action");
                        }else{
                            mizhu.alert(data.msg);
                        }
                    }
                });
            }
        });
    }
}



Video.changeStatus = function(status) {
    $("#status").val(status);
    $("[name='type']").val("");
    $("[name='aIsTask']").val("");

    if(status == 0) {
        $("#batch_publish").show();
        $("#batch_cannel").hide();
        $("#batch_offline").hide();
        $("#batch_copy").hide();

        $("#create_time_span").show();
        $("#publish_time_span").hide();
        $("#end_time_span").hide();
        $("#commit_time_span").hide();

        $(".qy_center_seach input").val("");

    }else if(status == 1) {
        $("#batch_publish").hide();
        $("#batch_cannel").show();
        $("#batch_offline").hide();
        $("#batch_copy").hide();

        $("#create_time_span").hide();
        $("#publish_time_span").hide();
        $("#offline_time_span").hide();
        $("#commit_time_span").show();

        $(".qy_center_seach input").val("");

    }else if(status == 2) {
        $("#batch_publish").hide();
        $("#batch_cannel").hide();
        $("#batch_offline").show();
        $("#batch_copy").hide();

        $("#create_time_span").hide();
        $("#publish_time_span").show();
        $("#offline_time_span").hide();
        $("#commit_time_span").hide();

        $(".qy_center_seach input").val("");

    }else if(status == 4){
        $("#batch_publish").hide();
        $("#batch_cannel").hide();
        $("#batch_offline").hide();
        $("#batch_copy").show();

        $("#create_time_span").hide();
        $("#publish_time_span").show();
        $("#offline_time_span").show();
        $("#commit_time_span").hide();

        $(".qy_center_seach input").val("");
    }
    App.commonFormSubmit('filter_form');
}

Video.deleteImg=function(){
    $("#image").val("");
    $("#projectImg_span").html("");
    $("#projectImg_span_delete").html('');
}

Video.deleteImgShow=function(n){
    $("#image"+n).val("");
    $("#projectImg_span"+n).html("");
    $("#projectImg_span_delete").html('');
}

Video.upload = function(n,fileSizeLimit) {
    $.ajaxFileUpload({
        url : contextpath + '/upload/uploadTempImg.action?id=file'+n+"&fileSizeLimit="+fileSizeLimit,
        type : 'post',
        dataType : 'text',
        secureuri : false, // 一般设置为false
        fileSizeLimit : 5000,
        fileElementId : 'file'+n, // 上传文件的id、name属性名
        success : function(data, status) {
            if(data.indexOf("http") != -1) {
                mizhu.alert("上传成功")
                var url = data;
                html='<img src="'+url+'" /> <a href="javascript:;" onclick="Video.deleteImg()" class="image_juzhong" style="color: red">删除</a> ';
                $("#image").val(url);
                $("#projectImg_span").html(html);
            }else {
                alert(data);
            }

        },
        error : function(data, status, e) {
            alert("出错了!");
        }
    });
}

Video.imgupload = function(n,fileSizeLimit) {
    $.ajaxFileUpload({
        url : contextpath + '/upload/uploadTempImg.action?id=file'+n+"&fileSizeLimit="+fileSizeLimit,
        type : 'post',
        dataType : 'text',
        secureuri : false, // 一般设置为false
        fileSizeLimit : 5000,
        fileElementId : 'file'+n, // 上传文件的id、name属性名
        success : function(data, status) {
            if(data.indexOf("http") != -1) {
                var url = data;
                if(n==2){
                    html='<img src="'+url+'" /> <a href="javascript:;" onclick="Video.deleteImgShow(1)" class="image_juzhong" style="color: red">删除</a> ';
                    $("#image").val(url);
                }else{
                    html='<img src="'+url+'" /> <a href="javascript:;" onclick="Video.deleteImgShow('+(n-1)+')" class="image_juzhong" style="color: red">删除</a> ';
                    $("#image"+(n-1)).val(url);
                }
                $("#projectImg_span"+(n-1)).html(html);
            }else {
                alert(data);
            }

        },
        error : function(data, status, e) {
            alert("出错了!");
        }
    });
}

Video.qiniuUpload = function(n,fileSizeLimit) {
    $("#loading").show();
    $(".mask").show();

    $.ajaxFileUpload({
        url : contextpath + '/upload/qiniuUpload.action?id=video'+n+"&fileSizeLimit="+fileSizeLimit,
        type : 'post',
        dataType : 'text',
        secureuri : false, // 一般设置为false
        fileSizeLimit : 1024000,//1000M
        fileElementId : 'video'+n, // 上传文件的id、name属性名

        success : function(data, status) {
            $(".mask").hide();
            $("#loading").hide();
            if(data.indexOf("http") != -1) {
                var obj = eval('(' + data + ')');
                var url=obj.url;
                var fileurl=url.fileUrl;
                var imgurl=url.image;
                mizhu.alert("上传成功");
                html='<video src="'+fileurl+'" /> <a href="javascript:;" onclick="Video.deleteImg()" class="image_juzhong" style="color: red">删除</a> ';
                $("#video_url").val(fileurl);
                $("#image").val(imgurl);
                $("#projectVideo_span").html(html);
            }else {
                alert(data);
            }

        },
        error : function(data, status, e) {
            $(".mask").hide();
            $("#loading").hide();
            alert("出错了!");
        }
    });
}

Video.validator = null;
Video.initVideo = function(){
    //添加验证
    var validator = new Clover.validator.Validator("#form_data");
    Video.validator = validator;
    validator.addValidation("[name='title']", "!length<=50", "标题长度为1-50");
    validator.addValidation("[name='intro']", "!length<=100", "简介长度为1-100");
    validator.addValidation("[name='type_name']", "required");
    validator.addValidation("[name='source']", "!length<=10","来源长度为1-10");
    validator.addValidation("[name='is_forever']", "required");

    validator.addConditionalValidation("[name='beginTime']", "required", function() {
        return $(":input[name='is_forever']:checked").val() == 0;
    }, "请选择起止日期");
    validator.addConditionalValidation("[name='endTime']", "required", function() {
        return $(":input[name='is_forever']:checked").val() == 0;
    }, "请选择起止日期");
    validator.addConditionalValidation("[name='allCost']", "required", function() {
        return $("#aIsTask").val() == 1;
    }, "请填写总费用");
    validator.addConditionalValidation("[name='onceCost']", "required", function() {
        return $("#aIsTask").val() == 1;
    }, "请填写每次点击费用");
    validator.addConditionalValidation("[name='mostCost']", "required", function() {
        return $("#aIsTask").val() == 1;
    }, "请填写用户最多获得费用");

    //查询页面
    $("#project_type a").click(function(){
        $("#type").val($(this).attr("data-title"));
        var num =  $("#type").val();
        if(num == 31 || num == 1 || num == 2) {
            $("#video_upload").show();
            $("#single_upload").hide()
            $("#multi_upload").hide()

        }else if(num==32) {
            $("#video_upload").hide();
            $("#single_upload").show()
            $("#multi_upload").hide()
        }else{
            $("#video_upload").hide();
            $("#single_upload").hide()
            $("#multi_upload").show()
        }
    });

    $("#is_task a").click(function() {
        $("#aIsTask").val($(this).attr("data-title"));
    });


    //新增或修改页面
    $(":input[name='is_forever']").click(function(){
        var num = $(":input[name='is_forever']:checked").val();
        if(num == 1) {
            $("#beginEndTimeShow").hide();
            $("#beginTime").attr("value"," ");
            $("#endTime").attr("value"," ");
        }else {
            $("#beginEndTimeShow").show();
        }
    });

    $(":input[name='a_is_task']").click(function(){
        var num = $(":input[name='a_is_task']:checked").val();
        $("#aIsTask").val(num);
        if(num == 1) {
            $("#money_info").show();
            $("#allCost").val("");
            $("#onceCost").val("");
            $("#mostCost").val("");
        }else {
            $("#money_info").hide();
            $("#allCost").val("");
            $("#onceCost").val("");
            $("#mostCost").val("");
        }
    });

    $("body").on('click','#06',function () {
        $(".individual").prop("checked", this.checked);
    });
    $("body").on('click','.individual',function() {
        var $subs = $(".individual");
        $("#06").prop("checked",$subs.length == $subs.filter(":checked").length ? true : false);
    });


}

$(function(){
    var num =  $("#type").val();
    if(num == 31) {
        $("#video_upload").show();
        $("#single_upload").hide()
        $("#multi_upload").hide()

    }else if(num==32) {
        $("#video_upload").hide();
        $("#single_upload").show()
        $("#multi_upload").hide()
    }else if(num == 33){
        $("#video_upload").hide();
        $("#single_upload").hide()
        $("#multi_upload").show()
    }
})