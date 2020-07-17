var Medal = {};



Medal.validator = null;
Medal.initMedal = function() {
    //添加验证
    var validator = new Clover.validator.Validator("#form_data");
    Medal.validator = validator;
    validator.addValidation("[name='medalName']", "!length<=10", "勋章名称长度为1-10");
    validator.addValidation("[name='intro']", "!length<=100", "简介长度为1-100");

}

Medal.uploadImage = function(n,fileSizeLimit) {
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
                html='<img src="'+url+'" /> <a href="javascript:;" onclick="Medal.deleteImg()" class="image_juzhong" style="color: red">删除</a> ';
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


Medal.uploadActiveImage = function(n,fileSizeLimit) {
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
                html='<img src="'+url+'" /> <a href="javascript:;" onclick="Medal.deleteImg()" class="image_juzhong" style="color: red">删除</a> ';
                $("#imageActive").val(url);
                $("#projectImgActive_span").html(html);
            }else {
                alert(data);
            }

        },
        error : function(data, status, e) {
            alert("出错了!");
        }
    });
}

/**
 * 保存或修改
 */
Medal.saveOrUpdate = function() {
    if(Medal.validator.validate()) {

        if($("#medalName").val() == '') {
            mizhu.alert("勋章名称");
            return;
        }

        if($("#intro").val() == '') {
            mizhu.alert("勋章简介不能为空");
            return;
        }



        if($("#image").val() == '') {
            mizhu.alert("请上传未激活勋章图");
            return;
        }
        if($("#imageActive").val() == '') {
            mizhu.alert("请上传未激活勋章图");
            return;
        }

        var data = $("#form_data").serialize();

        var remindMsg = '确认提交';

        mizhu.confirm('提示', remindMsg, function(flag) {
            if(flag) {
                jQuery.ajax({
                    type:"POST",
                    url:contextpath + '/medal/admin/create/createOrUpdateMedal.action',
                    data:data,
                    dataType:"json",
                    error:function(XMLHttpRequest, textStatus, errorThrown){
                        mizhu.alert('系统出现错误，请稍候再试');
                    },
                    success:function(data){
                        if("ok"== data.code){
                            App.jump("/medal/admin/list/listMedal.action");
                        }else{
                            mizhu.alert(data.msg);
                        }
                    }
                });
            }
        });
    }
}

Medal.jumpCreatePage = function() {
    if(!Index.isHashAuthor()) {
        return;
    }
    App.jump("/medal/admin/create/createMedal.action");

}

Medal.changeStatus = function(status) {
    $("#status").val(status);


    if(status == 0) { //待发布

        $("#publish_time_span").hide();

        $(".qy_center_seach input").val("");
    }else if(status == 1) {
        $("#status_span").hide();

        $("#offline_time_span").hide();
        $("#publish_time_span").hide();

        $(".qy_center_seach input").val("");

    }else if(status == 2) {
        $("#status_span").hide();

        $("#offline_time_span").hide();
        $("#publish_time_span").show();

        $(".qy_center_seach input").val("");
    }else if(status == -1){
        $("#status_span").hide();

        $("#offline_time_span").hide();
        $("#publish_time_span").hide();

        $("#allFilter").val("0");

        $(".qy_center_seach input").val("");

    }else if(status == -100) {
        $("#status_span").show();

        $("#offline_time_span").hide();
        $("#publish_time_span").show();

        $("#allFilter").val("1");

        $(".qy_center_seach input").val("");
    }
    App.commonFormSubmit('filter_form');
}

/**
 * 上线
 * @param medalId
 */
Medal.online = function(medalId) {
    mizhu.confirm('提示', '确认上线？', function(flag) {
        if(flag) {
            $.ajax({
                type: "POST",
                url: contextpath + '/medal/admin/update/onlineMedal.action',
                data: {id: medalId},
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
 * @param medalId
 */
Medal.offline = function(medalId) {
    mizhu.confirm('提示', '确认是否下线？', function(flag) {
        if(flag) {
            $.ajax({
                type: "POST",
                url: contextpath + '/medal/admin/update/offlineMedal.action',
                data: {id: medalId},
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
 * shanchu
 */
Medal.deleteMedal = function(id) {
    mizhu.confirm('提示', '确认删除该勋章？', function(flag) {
        if(flag) {
            $.ajax({
                type: "POST",
                url: contextpath + '/medal/admin/delete/deleteMedal.action',
                data: {id: id},
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

}


/**
 * 勋章规则
 */
Medal.medalRule = function(id) {

    $.ajax({
        type: "POST",
        url: contextpath + '/medal/admin/list/showMedalRule.action',
        data: {id: id},
        dataType: "json",
        cache: false,
        success: function (data) {
            if (data.code != "ok") {
                mizhu.alert(data.msg);
            } else {
                $("#rule_id").val(data.data.id);
                $("#medal_id").val(id);
                $("#rule_key").val(data.data.ruleKey);
                $("#rule_value").val(data.data.ruleValue);
                $("#intro").val(data.data.intro);
                var status = data.data.status;

                $("#statusSelect").val(status);

                $(".mask_float").show();
                $("#new_rule").show();
            }
        }
    });


Medal.saveOrUpdateRule = function() {
    if(Medal.validator.validate()) {

        var id = $("#rule_id").val();
        var medalId = $("#medal_id").val();
        var ruleKey = $("#rule_key").val();
        var ruleValue = $("#rule_value").val();
        var status = $("#statusSelect").val();
        var intro = $("#intro").val();

        if(rule_key == '') {
            mizhu.alert("规则KEY不能为空");
            return;
        }

        if(rule_value == '') {
            mizhu.alert("规则VALUE不能为空");
            return;
        }

        var remindMsg = '确认提交';

        mizhu.confirm('提示', remindMsg, function(flag) {
            if(flag) {
                jQuery.ajax({
                    type:"POST",
                    url:contextpath + '/medal/admin/create/createOrUpdateMedalRule.action',
                    data:{
                        id:id,
                        medalId:medalId,
                        ruleKey:ruleKey,
                        ruleValue:ruleValue,
                        intro:intro,
                        status:status
                    },
                    dataType:"json",
                    error:function(XMLHttpRequest, textStatus, errorThrown){
                        mizhu.alert('系统出现错误，请稍候再试');
                    },
                    success:function(data){
                        if("ok"== data.code){
                            App.jump("/medal/admin/list/listMedal.action");
                        }else{
                            mizhu.alert(data.msg);
                        }
                    }
                });
            }
        });
    }
}

}