var Role = {};

Role.deleteRole = function (id) {
    mizhu.confirm('提示', "确定删除吗？", function (flag) {
        if (flag) {
            $.ajax({
                type: 'POST',
                url: context + '/role/admin/list/deleteRole.action',
                data: {"roleId": id},
                success: function (data) {
                    mizhu.confirmAlert(data.msg,function(){
                        App.jump("/role/admin/list/listRole.action");
                    });

                }
            })
        }
    })
};


Role.submitForm = function () {
    if (!Role.createValidator.validate()) {
        return;
    }
    var id = $("#id").val();
    var name = $("#name").val();
    var description  =$("#description").val();
    var checkBox = $("input[name='systemRole']:checked");
    var systemRoles = "";
    $.each(checkBox,function(index,item){
        systemRoles = systemRoles+$(item).val();
        if((index+1) != checkBox.size()){
            systemRoles = systemRoles+",";
        }
    }) ;

    $.ajax({
        type:'POST',
        url:context+'/role/admin/list/save.action',
        data:{"id":id,"name":name,"description":description,"systemRoles":systemRoles},
        dataType:'json',
        success: function (result) {
                    if (result.msg == 'doubleName') {
                        mizhu.alert("角色名称已存在");
                    } else if (result.msg == "error") {
                        mizhu.alert("出现错误，稍后尝试");
                    } else {
                        App.jump('/role/admin/list/listRole.action');
                    }
                }
    })
};

Role.initAddValidator = function () {
    Role.createValidator = new Clover.validator.Validator("#role_form");
    Role.createValidator.addValidation("#description", "length<=30", "描述信息最多30字");
    Role.createValidator.addValidation("#name", "!length<=12", "角色名称最多12个字");
};



