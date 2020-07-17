var MedalRule = {};



MedalRule.validator = null;
MedalRule.initMedalRule = function() {
    //添加验证
    var validator = new Clover.validator.Validator("#form_data");
    MedalRule.validator = validator;
    validator.addValidation("[name='intro']", "!length<=100", "简介长度为1-100");

}

MedalRule.changeStatus = function(status) {
    $("#status").val(status);


    if(status == 0) { //待发布

        $("#create_time_span").show();
        $("#publish_time_span").hide();

        $(".qy_center_seach input").val("");
    }else if(status == 1) {
        $("#status_span").hide();
        $("#create_time_span").hide();
        $("#offline_time_span").hide();
        $("#publish_time_span").show();

        $(".qy_center_seach input").val("");

    }else if(status == 2) {
        $("#status_span").hide();
        $("#create_time_span").hide();
        $("#offline_time_span").show();
        $("#publish_time_span").hide();

        $(".qy_center_seach input").val("");
    }else if(status == -100) {
        $("#status_span").show();
        $("#create_time_span").show();
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
MedalRule.online = function(medalId) {
    mizhu.confirm('提示', '确认上线？', function(flag) {
        if(flag) {
            $.ajax({
                type: "POST",
                url: contextpath + '/medalRule/admin/update/onlineMedalRule.action',
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
MedalRule.offline = function(medalId) {
    mizhu.confirm('提示', '确认是否下线？', function(flag) {
        if(flag) {
            $.ajax({
                type: "POST",
                url: contextpath + '/medalRule/admin/update/offlineMedalRule.action',
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
 * 删除
 */
MedalRule.deleteMedal = function(id) {
    mizhu.confirm('提示', '确认删除该勋章规则？', function(flag) {
        if(flag) {
            $.ajax({
                type: "POST",
                url: contextpath + '/medalRule/admin/delete/deleteMedalRule.action',
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
MedalRule.medalRule = function(id) {

    $.ajax({
        type: "POST",
        url: contextpath + '/medalRule/admin/list/showMedalRule.action',
        data: {id: id},
        dataType: "json",
        cache: false,
        success: function (data) {
            if (data.code != "ok") {
                mizhu.alert(data.msg);
            } else {
                $("#rule_id").val(data.data.id);
                $("#medal_id").val(data.data.medalId);
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


MedalRule.saveOrUpdateRule = function() {
    if(MedalRule.validator.validate()) {

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
                    url:contextpath + '/medalRule/admin/create/createOrUpdateMedalRule.action',
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
                            App.jump("/medalRule/admin/list/listMedalRule.action");
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