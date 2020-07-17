var Project = {};


Project.jumpCreatePage = function() {
    if(!Index.isHashAuthor()) {
        return;
    }
    App.jump("/project/yxpt/create/createArticle.action");

}

/**
 * 发布
 * @param articleId
 */
Project.publish = function(articleId) {
    mizhu.confirm('提示', '确认发布？', function(flag) {
        if(flag) {
            $.ajax({
                type: "POST",
                url: contextpath + '/project/yxpt/update/publishArticle.action',
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
Project.cancel = function(articleId) {
    mizhu.confirm('提示', '确认取消发布？', function(flag) {
        if(flag) {
            $.ajax({
                type: "POST",
                url: contextpath + '/project/yxpt/update/cancelPublishArticle.action',
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
Project.offline = function(articleId) {
    mizhu.confirm('提示', '确认下线？', function(flag) {
        if(flag) {
            $.ajax({
                type: "POST",
                url:contextpath +  '/project/yxpt/update/offLineArticle.action',
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
Project.copyArticle = function(articleId) {
    mizhu.confirm('提示', '确认复制？', function(flag) {
        if(flag) {
            $.ajax({
                type: "POST",
                url: contextpath + '/project/yxpt/update/copyArticle.action',
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
Project.batchPublish = function() {
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
        url: contextpath + '/project/yxpt/update/batchPublishArticle.action',
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
Project.batchCancel = function() {
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
        url: contextpath + '/project/yxpt/update/batchCancelArticle.action',
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
Project.batchOffline = function() {
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
        url:contextpath +  '/project/yxpt/update/batchOfflineArticle.action',
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
Project.batchCopy = function() {
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
        url: contextpath + '/project/yxpt/update/batchCopyArticle.action',
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
Project.saveOrUpdate = function() {
    if(Project.validator.validate()) {
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
            mizhu.alert("请先选择一个项目分类");
            return;
        }

        if($("#body").val() == '') {
            mizhu.alert("项目内容不能为空");
            return;
        }

        if($("#image").val() == '') {
            mizhu.alert("项目缩略图不能为空");
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
                    url:contextpath + '/project/yxpt/create/createOrUpdateArticle.action',
                    data:data,
                    dataType:"json",
                    error:function(XMLHttpRequest, textStatus, errorThrown){
                        alert('系统出现错误，请稍候再试');
                    },
                    success:function(data){
                        if("ok"== data.code){
                            App.jump("/project/yxpt/list/listArticle.action");
                        }else{
                            alert(data.msg);
                        }
                    }
                });
            }
        });
    }
}



Project.changeStatus = function(status) {
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

Project.deleteImg=function(){
    $("#image").val("");
    $("#projectImg_span").html("");
    $("#projectImg_span_delete").html('');
}

Project.upload = function(n,fileSizeLimit) {
    $.ajaxFileUpload({
        url : contextpath + '/upload/uploadTempImg.action?id=file'+n+"&fileSizeLimit="+fileSizeLimit,
        type : 'post',
        dataType : 'text',
        secureuri : false, // 一般设置为false
        fileSizeLimit : 100,
        fileElementId : 'file'+n, // 上传文件的id、name属性名
        success : function(data, status) {
            if(data.indexOf("http") != -1) {
                var url = data;
                html='<img src="'+url+'" /> <a href="javascript:;" onclick="Project.deleteImg()" class="image_juzhong" style="color: red">删除</a> ';
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

Project.validator = null;
Project.initProject = function(){

    //添加验证
    var validator = new Clover.validator.Validator("#form_data");
    Project.validator = validator;
    validator.addValidation("[name='title']", "!length<=30", "标题长度为1-30");
    validator.addValidation("[name='intro']", "!length<=30", "简介长度为1-30");
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
