var Party = {};

Party.upload = function(n) {
    $.ajaxFileUpload({
        url : contextpath + '/upload/uploadTempImg.action?id=file'+n+'&fileSizeLimit=2048',
        type : 'post',
        dataType : 'text',
        secureuri : false, // 一般设置为false
        fileSizeLimit : 2048,
        fileElementId : 'file'+n, // 上传文件的id、name属性名
        success : function(data, status) {
            if(data.indexOf("http") != -1) {
                var url = data;
                var html='<img src="'+url+'" />';
                if(n==1){
                    $("#certificateImg").val(url);
                    $("#certificateImgSpan").html(html);
                    $("#certificateImg_a").html('<a href="javascript:Party.deleteCertificateImg();"></a>');
                }else if(n==2){
                    $("#certificateOther").val(url);
                    $("#certificateOtherSpan").html(html);
                    $("#certificateOther_a").html('<a href="javascript:Party.deleteCertificateOther();"></a>');
                }else if(n==3){
                    $("#authorImg").val(url);
                    html='<img src="'+url+'" width="254px;" height="173px;">';
                    html += '<br><a href="javascript:;" onclick="Party.deleteAuthorImg()" class="image_juzhong" style="color: #5a8bff; display: inline-block; width: 273px; padding: 10px 0; text-align: center;">删除</a>';
                    $("#img_span").html(html);
                }else if(n==4){
                    $("#identifyImgA").val(url);
                    $("#identifyImgASpan").html(html);
                    $("#identifyImgA_a").html('<a href="javascript:Party.deleteIdentifyImgA();"></a>');
                }else if(n==5){
                    $("#identifyImgB").val(url);
                    $("#identifyImgBSpan").html(html);
                    $("#identifyImgB_a").html('<a href="javascript:Party.deleteIdentifyImgB();"></a>');
                }else if(n==6){
                    $("#imageUrl").val(url);
                    html='<img src="'+url+'" width="230px" height="230px"/>';
                    $("#imageUrlSpan").html(html);
                }
            }else {
                alert(data);
            }
        },
        error : function(data, status, e) {
            alert("出错了!");
        }
    });
}

Party.bindCompanyName=function(){
    var companyName = $("#companyName").val();
    $("#invoiceTitle").val(companyName);
}

Party.deleteCertificateImg=function(){
    $("#certificateImg").val("");
    $("#certificateImgSpan").html("");
    $("#certificateImg_a").html('');
}
Party.deleteCertificateOther=function(){
    $("#certificateOther").val("");
    $("#certificateOtherSpan").html("");
    $("#certificateOther_a").html('');
}
Party.deleteAuthorImg=function(){
    $("#authorImg").val("");
    $("#img_span").html("");
}
Party.deleteIdentifyImgA=function(){
    $("#identifyImgA").val("");
    $("#identifyImgASpan").html("");
    $("#identifyImgA_a").html('');
}
Party.deleteIdentifyImgB=function(){
    $("#identifyImgB").val("");
    $("#identifyImgBSpan").html("");
    $("#identifyImgB_a").html('');
}

Party.validator = null;
Party.initValidator = function () {
    //添加验证
    var validator = new Clover.validator.Validator("#editForm");
    Party.validator = validator;
    validator.addValidation("#companyName", "required", "请填写公司名");
    validator.addValidation("#companyName", "!length<=50", "字数不多于50个");
    validator.addValidation("#description", "length<=30", "字数不多于30个");
    validator.addValidation("#socialCreditCode", "alpha");
    validator.addValidation("#socialCreditCode", "length>=15", "字数不能少于15位");
    validator.addValidation("#socialCreditCode", "length<=18", "字数不能多于18位");
    validator.addValidation("#invoiceTitle", "required", "请填写发票抬头");
    validator.addValidation("#invoiceTitle", "length<=150", "发票抬头过长，字数不多于150");
    validator.addValidation("#invoiceNumber", "required", "请填写纳税人识别号");
    validator.addValidation("#invoiceNumber", "length>=15", "纳税人识别号字数不少于15");
    validator.addValidation("#invoiceNumber", "length<=20", "纳税人识别号过长，字数不多于20");
    validator.addValidation("#invoiceAddress", "length<=150", "地址、电话过长，字数不多于150");
    validator.addValidation("#invoiceBank", "length<=150", "开户行及账号过长，字数不多于150");
    validator.addValidation("#connectorName", "required", "请填写真实姓名");
    validator.addValidation("#connectorName", "length<=32", "姓名过长，字数不多于32");
    validator.addValidation("#idNumber", "required", "请填写身份证号");
    validator.addValidation("#idNumber", function () {return Party.IdCardValidate($("#idNumber").val())}, "身份证号格式不正确");
    validator.addValidation("#mobile", "required", "请填写手机号码");
    validator.addValidation("#mobile", "integer", "请填写正确的手机号码格式");
    validator.addValidation("#mobile", "length=11", "手机号码为11位");
    validator.addValidation("#validateCode", "required", "请输入手机验证码");
    validator.addValidation("#validateCode", "integer", "手机验证码为数字");
};
Party.save = function(partyClass) {
    if(!Party.validator.validate()){
        return;
    }
    var className = "请选择公司类别！";
    var certificateImgMsg = "请上传企业营业执照扫描件！";
    var authorImgMsg = "请上传企业账号开通授权书";
    if(partyClass==2){
        className = "请选择机构类别";
        certificateImgMsg = "请上传组织机构代码证/营业执照扫描件！";
        authorImgMsg = "请上传机构账号开通授权书";
    }else if(partyClass==3){
        className = "请选择媒介类别";
        certificateImgMsg = "请上传组织机构代码证/营业执照扫描件！";
        authorImgMsg = "请上传媒介账号开通授权书";
    }
    //校验地区
    var areaThree = $("#areaThree").val();
    if(!areaThree || areaThree==""){
        mizhu.confirmAlert("请选择所在地！",function(flag){});
        return;
    }

    //校验公司类别
    var companyTypeName = $("#companyTypeName").val();
    if(!companyTypeName || companyTypeName==""){
        mizhu.confirmAlert(className,function(flag){});
        return;
    }

    //校验工商营业执照
    var certificateImg = $("#certificateImg").val();
    if(!certificateImg || certificateImg==""){
        mizhu.confirmAlert(certificateImgMsg,function(flag){});
        return;
    }

    //校验企业授权书
    var authorImg = $("#authorImg").val();
    if(!authorImg || authorImg==""){
        mizhu.confirmAlert(authorImgMsg,function(flag){});
        return;
    }

    //校验身份证正面
    var identifyImgA = $("#identifyImgA").val();
    if(!identifyImgA || identifyImgA==""){
        mizhu.confirmAlert("请上传手持身份证正面照",function(flag){});
        return;
    }

    //校验身份证反面
    var identifyImgB = $("#identifyImgB").val();
    if(!identifyImgB || identifyImgB==""){
        mizhu.confirmAlert("请上传身份证反面",function(flag){});
        return;
    }

    var datas=$("#editForm").serialize();
    //token = $('#FORM_TOKEN_KEY').val();
    jQuery.ajax({
        type:"POST",
        url:contextpath+'/cheke/party/add/addParty.action',
        data:datas,
        dataType:"json",
        error:function(XMLHttpRequest, textStatus, errorThrown){
            mizhu.alert('系统出现错误，请稍候再试'+errorThrown.toString());
        },
        success:function(data){
            var msg = data.msg;
            if("ok"==msg){
                mizhu.confirmAlert("提交成功！",function(flag){
                    window.location.href = contextpath+'/cheke/party/edit/userInfo.action';
                });
            }else{
                mizhu.confirmAlert(msg,function(flag){});
            }
        }
    });
}

Party.personValidator = null;
Party.initPersonValidator = function () {
    //添加验证
    var person = new Clover.validator.Validator("#editForm");
    Party.personValidator = person;
    person.addValidation("#companyName", "length<=50", "字数不多于50个");
    person.addValidation("#description", "length<=30", "字数不多于30个");
    person.addValidation("#socialCreditCode", "alpha");
    person.addValidation("#socialCreditCode", "length>=15", "字数不能少于15位");
    person.addValidation("#socialCreditCode", "length<=18", "字数不能多于18位");
    person.addValidation("#invoiceTitle", "length<=150", "发票抬头过长，字数不多于150");
    person.addValidation("#invoiceNumber", "length>=15", "纳税人识别号字数不少于15");
    person.addValidation("#invoiceNumber", "length<=20", "纳税人识别号过长，字数不多于20");
    person.addValidation("#invoiceAddress", "length<=150", "地址、电话过长，字数不多于150");
    person.addValidation("#invoiceBank", "length<=150", "开户行及账号过长，字数不多于150");
    person.addValidation("#connectorName", "required", "请填写真实姓名");
    person.addValidation("#connectorName", "length<=32", "姓名过长，字数不多于32");
    person.addValidation("#idNumber", "required", "请填写身份证号");
    person.addValidation("#idNumber", function () {return Party.IdCardValidate($("#idNumber").val())}, "身份证号格式不正确");
    person.addValidation("#mobile", "required", "请填写手机号码");
    person.addValidation("#mobile", "integer", "请填写正确的手机号码格式");
    person.addValidation("#mobile", "length=11", "手机号码为11位");
    person.addValidation("#validateCode", "required", "请输入手机验证码");
    person.addValidation("#validateCode", "integer", "手机验证码为数字");
};
Party.savePerson = function(partyClass) {
    if(!Party.personValidator.validate()){
        return;
    }

    //校验身份证正面
    var identifyImgA = $("#identifyImgA").val();
    if(!identifyImgA || identifyImgA==""){
        mizhu.confirmAlert("请上传手持身份证正面照",function(flag){});
        return;
    }

    //校验身份证反面
    var identifyImgB = $("#identifyImgB").val();
    if(!identifyImgB || identifyImgB==""){
        mizhu.confirmAlert("请上传身份证反面",function(flag){});
        return;
    }

    var datas=$("#editForm").serialize();
    //token = $('#FORM_TOKEN_KEY').val();
    jQuery.ajax({
        type:"POST",
        url:contextpath+'/cheke/party/add/addParty.action',
        data:datas,
        dataType:"json",
        error:function(XMLHttpRequest, textStatus, errorThrown){
            mizhu.alert('系统出现错误，请稍候再试'+errorThrown.toString());
        },
        success:function(data){
            var msg = data.msg;
            if("ok"==msg){
                mizhu.confirmAlert("提交成功！",function(flag){
                    window.location.href = contextpath+'/cheke/party/edit/userInfo.action';
                });
            }else{
                mizhu.confirmAlert(msg,function(flag){});
            }
        }
    });
}

Party.edit = function(partyClass) {
    if(!Party.validator.validate()){
        return;
    }
    var className = "请选择公司类别！";
    var certificateImgMsg = "请上传企业营业执照扫描件！";
    var authorImgMsg = "请上传企业账号开通授权书";
    if(partyClass==2){
        className = "请选择机构类别";
        certificateImgMsg = "请上传组织机构代码证/营业执照扫描件！";
        authorImgMsg = "请上传机构账号开通授权书";
    }else if(partyClass==3){
        className = "请选择媒介类别";
        certificateImgMsg = "请上传组织机构代码证/营业执照扫描件！";
        authorImgMsg = "请上传媒介账号开通授权书";
    }
    //校验地区
    var areaThree = $("#areaThree").val();
    if(!areaThree || areaThree==""){
        mizhu.confirmAlert("请选择所在地！",function(flag){});
        return;
    }

    //校验公司类别
    var companyTypeName = $("#companyTypeName").val();
    if(!companyTypeName || companyTypeName==""){
        mizhu.confirmAlert(className,function(flag){});
        return;
    }

    //校验工商营业执照
    var certificateImg = $("#certificateImg").val();
    if(!certificateImg || certificateImg==""){
        mizhu.confirmAlert(certificateImgMsg,function(flag){});
        return;
    }

    //校验企业授权书
    var authorImg = $("#authorImg").val();
    if(!authorImg || authorImg==""){
        mizhu.confirmAlert(authorImgMsg,function(flag){});
        return;
    }

    //校验身份证正面
    var identifyImgA = $("#identifyImgA").val();
    if(!identifyImgA || identifyImgA==""){
        mizhu.confirmAlert("请上传手持身份证正面照",function(flag){});
        return;
    }

    //校验身份证反面
    var identifyImgB = $("#identifyImgB").val();
    if(!identifyImgB || identifyImgB==""){
        mizhu.confirmAlert("请上传身份证反面",function(flag){});
        return;
    }

    var datas=$("#editForm").serialize();
    //token = $('#FORM_TOKEN_KEY').val();
    jQuery.ajax({
        type:"POST",
        url:contextpath+'/cheke/party/edit/editParty.action',
        data:datas,
        dataType:"json",
        error:function(XMLHttpRequest, textStatus, errorThrown){
            mizhu.alert('系统出现错误，请稍候再试'+errorThrown.toString());
        },
        success:function(data){
            var msg = data.msg;
            if("ok"==msg){
                mizhu.confirmAlert("提交成功！",function(flag){
                    window.location.href = contextpath+'/cheke/party/edit/userInfo.action';
                });
            }else{
                mizhu.confirmAlert(msg,function(flag){});
            }
        }
    });
}

Party.editPerson = function() {
    if(!Party.personValidator.validate()){
        return;
    }

    //校验身份证正面
    var identifyImgA = $("#identifyImgA").val();
    if(!identifyImgA || identifyImgA==""){
        mizhu.confirmAlert("请上传手持身份证正面照",function(flag){});
        return;
    }

    //校验身份证反面
    var identifyImgB = $("#identifyImgB").val();
    if(!identifyImgB || identifyImgB==""){
        mizhu.confirmAlert("请上传身份证反面",function(flag){});
        return;
    }
    var datas=$("#editForm").serialize();
    //token = $('#FORM_TOKEN_KEY').val();
    jQuery.ajax({
        type:"POST",
        url:contextpath+'/cheke/party/edit/editParty.action',
        data:datas,
        dataType:"json",
        error:function(XMLHttpRequest, textStatus, errorThrown){
            mizhu.alert('系统出现错误，请稍候再试'+errorThrown.toString());
        },
        success:function(data){
            var msg = data.msg;
            if("ok"==msg){
                mizhu.confirmAlert("提交成功！",function(flag){
                    window.location.href = contextpath+'/cheke/party/edit/userInfo.action';
                });
            }else{
                mizhu.confirmAlert(msg,function(flag){});
            }
        }
    });
}

Party.initOneLevelArea=function(){
    $.ajax({
        type:'post',
        url:contextpath+'/basic/area/list/listByParentId.action?parentId=0',
        dataType:"json",
        success:function(data){
            data=data.list;
            var html='';
            for(var i=0;i<data.length;i++){
                html+='<a style="width: 127px;" href="javascript:void(0)" onclick="Party.initTwoLevelArea('+data[i].id+',\''+data[i].name+'\');">'+data[i].name+'</a>';
            }
            $("#areaOneOption").html(html);
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
            mizhu.alert('系统出现错误，请稍候再试');
        },
    });
}
Party.initTwoLevelArea=function(parentId,name){
    $("#areaTwo").val('');
    $("#areaThree").val('');
    $("#areaId").val('');
    $("#areaOne").val(name);
    $.ajax({
        type:'post',
        url:contextpath+'/basic/area/list/listByParentId.action?parentId='+parentId,
        dataType:"json",
        success:function(data){
            data = data.list;
            var html='';
            for(var i=0;i<data.length;i++){
                html+='<a style="width: 127px;" href="javascript:Party.initArea('+data[i].id+',\''+data[i].name+'\');">'+data[i].name+'</a>';
            }
            $("#areaTwoOption").html(html);
        }
    });
}
Party.initArea=function(parentId,name){
    $("#areaThree").val('');
    $("#areaId").val('');
    $("#areaTwo").val(name);
    $.ajax({
        type:'post',
        url:contextpath+'/basic/area/list/listByParentId.action?parentId='+parentId,
        dataType:"json",
        success:function(data){
            data=data.list;
            var html='';
            for(var i=0;i<data.length;i++){
                html+='<a style="width: 127px;" href="javascript:Party.selectArea('+data[i].id+',\''+data[i].name+'\');">'+data[i].name+'</a>';
            }
            $("#areaIdOption").html(html);
        }
    });
}

Party.getPhoneValidateCode=function(that){
    var mobile = $("#mobile").val();
    var phoneValidator = new Clover.validator.Validator("#editForm");
    phoneValidator.addValidation("#mobile", "required", "手机号不能为空");
    phoneValidator.addValidation("#mobile", "integer", "请填写正确的手机号码格式");
    phoneValidator.addValidation("#mobile", "length=11", "手机号码为11位");
    if(phoneValidator.validate()){
        Party.settime(that);
        $.ajax({
            type:'post',
            url:contextpath+'/cheke/party/edit/getPhoneValidateCode.action?mobile='+mobile,
            dataType:"json",
            success:function(data){
                if(data.msg=="ok"){
                    mizhu.confirmAlert("验证码发送成功",function(flag){});
                }else{
                    mizhu.confirmAlert(data.msg,function(flag){});
                }
            }
        });
    }
}

Party.openPhonePasswordDialog=function(){
    Party.closeSubmitBox();
    $("#phoneCode").val("");
    $("#password3").val("");
    $("#password4").val("");
    $(".mask_float").show();
    $("#phone_password").show();
}
Party.closePhonePasswordDialog=function(){
    $(".mask_float").hide();
    $("#phone_password").hide();
}

Party.getPhoneCode=function(that){
    var phoneCode = $("#phoneCode").val();
    var phoneValidator = new Clover.validator.Validator("#phone_password_form");
    phoneValidator.addValidation("#phoneCode", "required", "手机号不能为空");
    phoneValidator.addValidation("#phoneCode", "integer", "请填写正确的手机号码格式");
    phoneValidator.addValidation("#phoneCode", "length=11", "手机号码为11位");
    if(phoneValidator.validate()){
        Party.settime(that);
        $.ajax({
            type:'post',
            url:contextpath+'/cheke/party/edit/getPhoneValidateCode.action?mobile='+phoneCode,
            dataType:"json",
            success:function(data){
                if(data.msg=="ok"){
                    mizhu.confirmAlert("验证码发送成功",function(flag){});
                }else{
                    mizhu.confirmAlert(data.msg,function(flag){});
                }
            }
        });
    }
}

Party.selectArea = function (id,name) {
    $("#areaId").val(id);
    $("#areaThree").val(name);
}

Party.selectPartyClass=function(self,type){
    window.location.href=contextpath+"/cheke/party/add/toAddParty.action?partyClass="+type;
}

Party.selectCompanyType=function(key){
    $("#companyType").val(key);
}

Party.selectMediaType=function(key){
    $("#mediaType").val(key);
}
var countdown=60;
Party.settime=function(val) {
    if (countdown == 0) {
        val.removeAttribute("disabled");
        val.value="获取验证码";
        $("#get_code").css({"background":"#5a8bff","color":"#ffffff"});
        countdown = 60;
        return;
    } else {
        val.setAttribute("disabled", true);
        val.value="倒计时(" + countdown + ")";
        countdown--;
        $("#get_code").css({"background":"#96a2be","color":"#ffffff"});
    }
    setTimeout(function() {
        Party.settime(val)
    },1000)
}

Party.editParty=function(){
    $('#nb_userInfo_main_view').hide();
    $('#nb_userInfo_main_edit').show();
    $('#submitButtonDiv').show();
}

Party.cancelEdit=function(){
    $('#nb_userInfo_main_view').show();
    $('#nb_userInfo_main_edit').hide();
    $('#submitButtonDiv').hide();
}

Party.modifyPassword=function(){
    var password = $("#password").val();
    var password1 = $("#password1").val();
    var password2 = $("#password2").val();
    var passwordValidator = new Clover.validator.Validator("#modify_password_form");
    passwordValidator.addValidation("#password", "required");
    passwordValidator.addValidation("#password", "length>=6","密码长度必须为6-15位");
    passwordValidator.addValidation("#password", "length<=15","密码长度必须为6-15位");
    passwordValidator.addValidation("#password", "alpha");       //为password添加字符验证,只能包含字母,数字及下划线
    passwordValidator.addValidation("#password1", "required");
    passwordValidator.addValidation("#password1", "length>=6","密码长度必须为6-15位");
    passwordValidator.addValidation("#password1", "length<=15","密码长度必须为6-15位");
    passwordValidator.addValidation("#password1", "alpha");       //为password添加字符验证,只能包含字母,数字及下划线
    passwordValidator.addValidation("#password2", "required");
    passwordValidator.addValidation("#password2", "length>=6","密码长度必须为6-15位");
    passwordValidator.addValidation("#password2", "length<=15","密码长度必须为6-15位");
    passwordValidator.addValidation("#password2", "alpha");       //为password添加字符验证,只能包含字母,数字及下划线

    if(passwordValidator.validate()){
        if(password1!=password2){
            mizhu.alert("两次输入的密码不一致！");
            return;
        }
        $.ajax({
            type:'post',
            url:contextpath+'/cheke/party/edit/modifyPassword.action?password1='+password1+'&password2='+password2+"&password="+password,
            dataType:"json",
            success:function(data){
                if(data.msg=="ok"){
                    mizhu.alert("密码修改成功");
                    Party.closeSubmitBox();
                }else{
                    mizhu.alert(data.msg);
                }
            }
        });
    }
}

Party.modifyPhonePassword=function(){
    var phoneCode = $("#phoneValidateCode").val();
    var password3 = $("#password3").val();
    var password4 = $("#password4").val();
    var passwordValidator = new Clover.validator.Validator("#phone_password_form");
    passwordValidator.addValidation("#password3", "required");
    passwordValidator.addValidation("#password3", "length>=6","密码长度必须为6-15位");
    passwordValidator.addValidation("#password3", "length<=15","密码长度必须为6-15位");
    passwordValidator.addValidation("#password3", "alpha");       //为password添加字符验证,只能包含字母,数字及下划线
    passwordValidator.addValidation("#password4", "required");
    passwordValidator.addValidation("#password4", "length>=6","密码长度必须为6-15位");
    passwordValidator.addValidation("#password4", "length<=15","密码长度必须为6-15位");
    passwordValidator.addValidation("#password4", "alpha");       //为password添加字符验证,只能包含字母,数字及下划线

    if(passwordValidator.validate()){
        if(password3!=password4){
            mizhu.alert("两次输入的密码不一致！");
            return;
        }
        $.ajax({
            type:'post',
            url:contextpath+'/cheke/party/edit/modifyPasswordByPhone.action?password1='+password3+'&password2='+password4+"&phoneCode="+phoneCode,
            dataType:"json",
            success:function(data){
                if(data.msg=="ok"){
                    mizhu.alert("密码修改成功");
                    Party.closePhonePasswordDialog();
                }else{
                    mizhu.alert(data.msg);
                }
            }
        });
    }
}

Party.openModifyDialog=function(){
    $("#password").val("");
    $("#password1").val("");
    $("#password2").val("");
    $(".mask_float").show();
    $("#bomb_password").show();
}

Party.closeSubmitBox=function(){
    $(".mask_float").hide();
    $("#bomb_password").hide();
}

var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];    // 加权因子
var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];            // 身份证验证位值.10代表X
Party.IdCardValidate=function(idCard) {
    var idCard = Party.trim(idCard.replace(/ /g, ""));               //去掉字符串头尾空格
    if (idCard.length == 15) {
        return Party.isValidityBrithBy15IdCard(idCard);       //进行15位身份证的验证
    } else if (idCard.length == 18) {
        var a_idCard = idCard.split("");                // 得到身份证数组
        if(Party.isValidityBrithBy18IdCard(idCard)&&Party.isTrueValidateCodeBy18IdCard(a_idCard)){   //进行18位身份证的基本验证和第18位的验证
            return true;
        }else {
            return false;
        }
    } else {
        return false;
    }
}
/**
 * 判断身份证号码为18位时最后的验证位是否正确
 * @param a_idCard 身份证号码数组
 * @return
 */
Party.isTrueValidateCodeBy18IdCard=function(a_idCard) {
    var sum = 0;                             // 声明加权求和变量
    if (a_idCard[17].toLowerCase() == 'x') {
        a_idCard[17] = 10;                    // 将最后位为x的验证码替换为10方便后续操作
    }
    for ( var i = 0; i < 17; i++) {
        sum += Wi[i] * a_idCard[i];            // 加权求和
    }
    valCodePosition = sum % 11;                // 得到验证码所位置
    if (a_idCard[17] == ValideCode[valCodePosition]) {
        return true;
    } else {
        return false;
    }
}
/**
 * 验证18位数身份证号码中的生日是否是有效生日
 * @param idCard 18位书身份证字符串
 * @return
 */
Party.isValidityBrithBy18IdCard=function(idCard18){
    var year =  idCard18.substring(6,10);
    var month = idCard18.substring(10,12);
    var day = idCard18.substring(12,14);
    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));
    // 这里用getFullYear()获取年份，避免千年虫问题
    if(temp_date.getFullYear()!=parseFloat(year)
        ||temp_date.getMonth()!=parseFloat(month)-1
        ||temp_date.getDate()!=parseFloat(day)){
        return false;
    }else{
        return true;
    }
}
/**
 * 验证15位数身份证号码中的生日是否是有效生日
 * @param idCard15 15位书身份证字符串
 * @return
 */
Party.isValidityBrithBy15IdCard=function(idCard15){
    var year =  idCard15.substring(6,8);
    var month = idCard15.substring(8,10);
    var day = idCard15.substring(10,12);
    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));
    // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法
    if(temp_date.getYear()!=parseFloat(year)
        ||temp_date.getMonth()!=parseFloat(month)-1
        ||temp_date.getDate()!=parseFloat(day)){
        return false;
    }else{
        return true;
    }
}
//去掉字符串头尾空格
Party.trim=function(str) {
    return str.replace(/(^\s*)|(\s*$)/g, "");
}

Party.uploadImgUrl=function(){
    var imgUrl=$("#imageUrl").val();
    if(!imgUrl||imgUrl==""){
        mizhu.alert("请先上传头像再提交");
        return;
    }
    jQuery.ajax({
        type:"POST",
        url:contextpath+'/cheke/party/add/uploadImgUrl.action',
        data:{"imgUrl":imgUrl},
        dataType:"json",
        success:function(data){
            var msg = data.msg;
            if("ok"==msg){
                mizhu.confirmAlert("提交成功！",function(flag){
                    //window.location.href = contextpath+'/cheke/party/edit/userInfo.action';
                });
            }else{
                mizhu.alert(msg);
            }
        }
    });
}

Party.toUploadImage=function(){
    window.location.href=contextpath+"/cheke/party/add/toUploadImage.action";
}

Party.cancelImageUrl=function(){
    $("#imageUrlSpan").html("");
}

Party.toUpStep = function() {
    window.location.href=contextpath+"/cheke/party/edit/userInfo.action";
}
