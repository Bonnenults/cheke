function fmoney(s, n)
{
   n = n > 0 && n <= 20 ? n : 2;
   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
   var l = s.split(".")[0].split("").reverse(),
   r = s.split(".")[1];
   t = "";
   for(i = 0; i < l.length; i ++ )
   {
      t += l[i] +"";
   }
   return t.split("").reverse().join("") + "." + r;
}


function accDiv(arg1,arg2){
var t1=0,t2=0,r1,r2;
try{t1=arg1.toString().split(".")[1].length}catch(e){}
try{t2=arg2.toString().split(".")[1].length}catch(e){}
with(Math){
r1=Number(arg1.toString().replace(".",""))
r2=Number(arg2.toString().replace(".",""))
return (r1/r2)*pow(10,t2-t1);
}
}
//给Number类型增加一个div方法，调用起来更加方便。
Number.prototype.div = function (arg){
return accDiv(this, arg);
}
//乘法函数，用来得到精确的乘法结果
//说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
//调用：accMul(arg1,arg2)
//返回值：arg1乘以arg2的精确结果
function accMul(arg1,arg2)
{
var m=0,s1=arg1.toString(),s2=arg2.toString();
try{m+=s1.split(".")[1].length}catch(e){}
try{m+=s2.split(".")[1].length}catch(e){}
return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
}
//给Number类型增加一个mul方法，调用起来更加方便。
Number.prototype.mul = function (arg){
return accMul(this, arg);
}
//加法函数，用来得到精确的加法结果
//说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
//调用：accAdd(arg1,arg2)
//返回值：arg1加上arg2的精确结果
function accAdd(arg1,arg2){
var r1,r2,m;
try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
m=Math.pow(10,Math.max(r1,r2))
return (arg1*m+arg2*m)/m
}
//给Number类型增加一个add方法，调用起来更加方便。
Number.prototype.add = function (arg){
return accAdd(this,arg);
}

function accSub(arg1,arg2){
     var r1,r2,m,n;
     try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
     try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
     m=Math.pow(10,Math.max(r1,r2));
     //last modify by deeka
     //动态控制精度长度
     n=(r1>=r2)?r1:r2;
     return ((arg1*m-arg2*m)/m).toFixed(n);
}

//给Number类型增加一个mul方法，调用起来更加方便。
Number.prototype.sub = function (arg){
return accSub(this, arg);
};

/**
 * 通用表单提交事件
 */
var App={};
App.commonFormSubmit = function(formId){
	$('#search_condition').val($('#'+formId).serialize());
	PageInfo.jumpToPage(1);
};


/**
 * 对话框中的查询
 * 
 * @param formId
 * @param data_container
 */
App.commonFormSubmitForDialog = function(formId,data_container){
	var url = $('#'+formId).attr('action');
    $('#search_condition_dialog').val($('#'+formId).serialize());
	PageInfo.jumpToPageSpecial(1,url);
};

/**
 * 对话框中的查询
 * 需要传入替换的位置Id
 * @param formId
 * @param data_container
 */
App.commonFormSubmitForDialog2 = function (formId, data_container) {
    var url = $('#' + formId).attr('action');
    PageInfo.jumpToPageSpecial2(1, url,data_container,$('#' + formId).serialize());
};

/**
 * 页面无查询条件的表单提交
 */
App.commonFormSubmitNoCodition = function(formId){
	PageInfo.jumpToPage(1);
};

/**
 * 页面跳转
 */
App.jump = function(url){
	window.location.href = contextpath+url;
};
/**
 * 
 * @param url
 */
App.openWindow = function(url){
	window.open(contextpath+url, "_blank");
};


App.selectDiv = function(targetInput,val){
   $("#"+targetInput).val(val);
};
