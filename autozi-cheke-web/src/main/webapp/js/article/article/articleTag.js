var ArticleTag = {};


/**
 * 保存或修改文章分类
 */
ArticleTag.saveOrUpdateTag = function() {
    if(ArticleTag.validator.validate()) {
        var id = $("#tag_id").val();
        var name = $("#tag_name").val();
        var code = $("#tag_code").val();
        var channelType = $("#article_channel_type").val();

        if(channelType == '') {
            mizhu.alert("请选择一个频道");
            return;
        }

        if(code==""){
            code=1000;
        }

        jQuery.ajax({
            type:"POST",
            url:contextpath + '/article/admin/create/createOrUpdateArticleTag.action',
            data:{
                id:id,
                name:name,
                code:code,
                channelType:channelType
            },
            dataType:"json",
            error:function(XMLHttpRequest, textStatus, errorThrown){
                alert('系统出现错误，请稍候再试');
            },
            success:function(data){
                if("ok"== data.code){
                    App.jump("/article/admin/list/listArticleTag.action");
                }else{
                    mizhu.alert(data.msg);
                }
            }
        });
    }
}

/**
 * 保存或修改文章分类
 */
ArticleTag.deleteTag = function(id) {
    mizhu.confirm('提示', '确认删除该分类？', function(flag) {
        if(flag) {
            $.ajax({
                type: "POST",
                url: contextpath + '/article/admin/delete/deleteArticleTag.action',
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

ArticleTag.validator = null;
ArticleTag.initArticleTag = function() {
    //添加验证
    var validator = new Clover.validator.Validator("#form_data");
    ArticleTag.validator = validator;
    validator.addValidation("[name='tag_code']", "integer","序号必须为正整数");
    validator.addValidation("[name='tag_code']", "value<=1000","序号要小于1000");
    validator.addValidation("[name='tag_name']", "!length<=15", "分类名称长度为1-15");

    $("#channel_type a").click(function(){
        $("#article_channel_type").val($(this).attr("data-title"));
    });
}