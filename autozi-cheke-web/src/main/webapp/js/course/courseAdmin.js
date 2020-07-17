/***   Admin   ***/
var CourseAdmin = {};


/**
 * 下线
 * @param courseId
 */
CourseAdmin.offline = function(courseId) {
    mizhu.confirm('提示', '确认是否下线？', function(flag) {
        if(flag) {
            $.ajax({
                type: "POST",
                url: contextpath + '/course/admin/update/offlineCourse.action',
                data: {id: courseId},
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
 * 上线
 * @param courseId
 */
CourseAdmin.online = function(courseId) {
    mizhu.confirm('提示', '确认是否上线？', function(flag) {
        if(flag) {
            $.ajax({
                type: "POST",
                url: contextpath + '/course/admin/update/onlineCourse.action',
                data: {id: courseId},
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
CourseAdmin.auditCourse = function(flag) {
    var refuseReason = $("#refuseReason").val() || '';
    var courseId = $("#courseId").val();
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
        url:contextpath + '/course/admin/update/audit.action',
        data:{courseId:courseId,auditFlag:flag,refuseReason:refuseReason},
        dataType:"json",
        error:function(XMLHttpRequest, textStatus, errorThrown){
            alert('系统出现错误，请稍候再试');
        },
        success:function(data){
            if("ok"== data.code){
                mizhu.confirmAlert(alertMsg,function(flag){
                    App.jump("/course/admin/list/listCourse.action");
                });
            }else{
                mizhu.alert(data.msg);
            }
        }
    });
}



CourseAdmin.changeStatus = function(status) {
    $("#status").val(status);
    $("[name='type']").val("");
    //alert(status+"/"+typeof status);
    //alert(status);
    if(status == 0) { //待发布
        $("#batch_publish").hide();
        $("#batch_cannel").hide();
        $("#batch_offline").hide();
        $("#batch_copy").hide();

        //$("#create_time_span").show();
        $("#publish_time_span").hide();
        //$("#end_time_span").hide();
        $("#commit_time_span").hide();
        $("#aIsTask").val("");
        $(".qy_center_seach input").val("");
    }else if(status == 1) {
        $("#status_span").hide();

        $("#commit_time_span").show();
        $("#offline_time_span").hide();
        $("#publish_time_span").hide();

        $(".qy_center_seach input").val("");

    }else if(status == 2) {
        $("#status_span").hide();

        $("#commit_time_span").hide();
        $("#offline_time_span").hide();
        $("#publish_time_span").show();

        $(".qy_center_seach input").val("");
    }else if(status == 4) {
        $("#status_span").hide();

        $("#commit_time_span").hide();
        $("#offline_time_span").show();
        $("#publish_time_span").show();

        $(".qy_center_seach input").val("");
    }else if(status == -1){
        $("#status_span").hide();

        $("#commit_time_span").show();
        $("#offline_time_span").hide();
        $("#publish_time_span").hide();

        $("#allFilter").val("0");

        $(".qy_center_seach input").val("");

    }else if(status == -100) {
        $("#status_span").show();

        $("#commit_time_span").hide();
        $("#offline_time_span").hide();
        $("#publish_time_span").show();

        $("#allFilter").val("1");

        $(".qy_center_seach input").val("");
    }
    App.commonFormSubmit('filter_form');
}




//------------------------

CourseAdmin.jumpCreatePage = function() {
    if(!Index.isHashAuthor()) {
        return;
    }
    App.jump("/course/admin/create/createCourse.action");

}

/**
 * 发布
 * @param courseId
 */
CourseAdmin.publish = function(courseId) {
    mizhu.confirm('提示', '确认发布？', function(flag) {
        if(flag) {
            $.ajax({
                type: "POST",
                url: contextpath + '/course/admin/update/onlineCourse.action',
                data: {id: courseId},
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
 * 保存或修改广告排序
 */
CourseAdmin.saveOrUpdateSort = function() {
    if(CourseAdmin.validator.validate()) {
        var course_id = $("#course_id").val();
        var a_is_top = $("#a_is_top").val();
        var in_firstPage = $("#in_firstPage").val();

        //if(a_is_top==""){
        //    a_is_top=0;
        //}

        jQuery.ajax({
            type:"POST",
            url:contextpath + '/course/admin/update/sort.action',
            data:{
                a_is_top:a_is_top,
                a_is_top:a_is_top,
                in_firstPage:in_firstPage
            },
            dataType:"json",
            error:function(XMLHttpRequest, textStatus, errorThrown){
                alert('系统出现错误，请稍候再试');
            },
            success:function(data){
                if("ok"== data.code){
                    App.jump("/course/admin/list/listAd.action");
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
CourseAdmin.saveOrUpdate = function() {
    if(CourseAdmin.validator.validate()) {
        /* if($("#aIsTask").val() == 1) {
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
        $(":input[name='course_tag']:checked").each(function(){
            tagIds += $(this).val() + ","
        })
        tagIds = tagIds.substr(0,tagIds.length - 1);
        if(tagIds.length <= 0) {
            mizhu.alert("请先选择一个培训分类");
            return;
        }*/

        if($("#image").val() == '') {
            mizhu.alert("请上传课程封面");
            return;
        }
        if($("#body").val() == '') {
            mizhu.alert("培训正文不能为空");
            return;
        }
        if($("#type").val() == '') {
            mizhu.alert("请选择课程类型");
            return;
        }

        var data = $("#form_data").serialize();/*+ "&tag="+ tagIds*/

        var remindMsg = '确认提交';

        mizhu.confirm('提示', remindMsg, function(flag) {
            if(flag) {
                jQuery.ajax({
                    type:"POST",
                    url:contextpath + '/course/admin/create/createOrUpdateCourse.action',
                    data:data,
                    dataType:"json",
                    error:function(XMLHttpRequest, textStatus, errorThrown){
                        mizhu.alert('系统出现错误，请稍候再试');
                    },
                    success:function(data){
                        if("ok"== data.code){
                            App.jump("/course/admin/list/listCourse.action");
                        }else{
                            mizhu.alert(data.msg);
                        }
                    }
                });
            }
        });
    }
}

CourseAdmin.upload = function(n,fileSizeLimit) {
    $.ajaxFileUpload({
        url : contextpath + '/upload/uploadTempImg.action?id=file'+n+"&fileSizeLimit="+fileSizeLimit,
        type : 'post',
        dataType : 'text',
        secureuri : false, // 一般设置为false
        fileSizeLimit : 1024,
        fileElementId : 'file'+n, // 上传文件的id、name属性名
        success : function(data, status) {
            if(data.indexOf("http") != -1) {
                var url = data;
                html='<img src="'+url+'" /> <a href="javascript:;" onclick="CourseAdmin.deleteImg()" class="image_juzhong" style="color: red">删除</a> ';
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

CourseAdmin.validator = null;
CourseAdmin.initCourse = function(){

    //添加验证
    var validator = new Clover.validator.Validator("#form_data");
    CourseAdmin.validator = validator;
    validator.addValidation("[name='courseName']", "!length<=50", "标题长度为1-50");
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


    //查询页面

    $("#course_status a").click(function(){
        $("#status").val($(this).attr("data-title"));
    });

    $("#is_task a").click(function() {
        $("#aIsTask").val($(this).attr("data-title"));
    });

    $("#party_class a").click(function(){
        $("#partyClass").val($(this).attr("data-title"));
    });
    $("#project_type a").click(function(){
        $("#type").val($(this).attr("data-title"));
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

};


//添加文章至课程

CourseAdmin.addAticle=function(articleId,title) {
    //alert(title);
    $(".mask_float").show();
    $("#add_popup").show();
    $("#chapter_num").show();
    $("#del").hide();
    $("#new_fication .bomb_title").text("设置排序");
    $("#article_id").val(articleId);
    $("#title").text(title);

};


/**
 * 添加文章至课程
 * @param courseId
 */
CourseAdmin.addToCourse = function() {
    var articleId = $("#article_id").val();
    var courseId=$("#courseId").val();
    var num = $("#num").val();
    mizhu.confirm('提示', '确认是否添加？', function(flag) {
        if(flag) {
            $.ajax({
                type: "POST",
                url: contextpath + '/course/admin/update/addArticleToCourse.action',
                data: {articleId: articleId,
                    courseId:courseId,
                       num:num
                },
                dataType: "json",
                cache: false,
                success: function (data) {
                    if (data.code != "ok") {
                        mizhu.alert(data.msg);
                    } else {
                        $(".mask_float").hide();
                        $("#add_popup").hide();
                        //App.commonFormSubmit('filter_form');
                        App.jump("/course/admin/update/manageCourse.action?id="+courseId);
                    }

                }
            });
        }
    });
};

CourseAdmin.delAticle=function(articleId,title) {
    //alert(title);
    $(".mask_float").show();
    $("#add_popup").show();
    $("#chapter_num").hide();
    $("#add").hide();
    $("#del").show();
    $("#new_fication .bomb_title").text("删除章节"+title);
    $("#article_id").val(articleId);
    $("#title").text(title);

};

CourseAdmin.delFromCourse = function() {
    var articleId = $("#article_id").val();
    var courseId=$("#courseId").val();
    //alert(articleId);
    mizhu.confirm('提示', '确认是否删除？', function(flag) {
        if(flag) {
            $.ajax({
                type: "POST",
                url: contextpath + '/course/admin/update/delArticleFromCourse.action',
                data: {articleId: articleId
                },
                dataType: "json",
                cache: false,
                success: function (data) {
                    if (data.code != "ok") {
                        mizhu.alert(data.msg);
                    } else {
                        $(".mask_float").hide();
                        $("#add_popup").hide();
                        //App.commonFormSubmit('filter_form');
                        App.jump("/course/admin/update/manageCourse.action?id="+courseId);
                    }
                }
            });
        }
    });
};

