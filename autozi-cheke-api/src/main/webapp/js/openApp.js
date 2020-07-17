/**
 * Created by ywy on 2017/7/17.
 */

var iphoneSchema = 'AutoziCK://';
var iphoneDownUrl = 'https://itunes.apple.com/cn/app/车客/id1328726800?mt=8';
var androidSchema = 'com.autozi.autozick';
var androidDownUrl = 'https://www.pgyer.com/autozi_ck';
var theParam = GetUrlParam();
var os = theParam['os'];
if(os==null || os==''){
    $('#download-footer').hide();
}

function cancleTip(e) {
    // var footer = $('#download-footer');
    var parent = $('#download-footer').parent();
    // console.log('h:'+parent.innerHeight() + '   h:'+parent.height());
    // parent.style.paddingBottom = parent.innerHeight() - parent.height() - 50;
    console.log(parent.css("padding-bottom"));
    parent.css("padding-bottom",parent.css("padding-bottom").replace('px', '') - 50);
    // parent.css("padding-bottom",parent.innerHeight() - parent.height() - 50);
    // console.log($('#download-footer').parent().style.padd);
    // $('#download-footer').parent().style.paddingBottom = $('#download-footer').parent().style.paddingBottom - 50;
    $('#download-footer').hide();
}

function openApp (e) {
    if(isWeixin()){
        // if ($(e).hasClass("ios")){
        //     window.location = iphoneDownUrl;//ios下载地址
        // }
        // else {
        //     window.location = androidDownUrl; //android下载地址
        // }

        if (navigator.userAgent.match(/(iPhone|iPod|iPad);?/i)) {
            window.location = iphoneDownUrl;//ios下载地址
        }
        else {
            $('.android-tip').show();
            $('html,body').animate({scrollTop:0},'slow');
        }

        }else{//非微信浏览器
        if (navigator.userAgent.match(/(iPhone|iPod|iPad);?/i)) {
            var loadDateTime = new Date();
            window.setTimeout(function() {
                var timeOutDateTime = new Date();
                if (timeOutDateTime - loadDateTime < 5000) {
                    window.location = iphoneDownUrl;//ios下载地址
                } else {
                    window.close();
                }
            },25);
            window.location = this.iphoneSchema;
        }else if (navigator.userAgent.match(/android/i)) {
            try {
                window.location = androidSchema;
                setTimeout(function(){
                    window.location = androidDownUrl; //android下载地址

                },500);
            } catch(e) {}
        }
    }
}

function isWeixin() {
    var ua = navigator.userAgent.toLowerCase();
    if (ua.match(/MicroMessenger/i) == "micromessenger") {
        return true;
    } else {
        return false;
    }
}

/*
var APPCommon = {
    iphoneSchema: 'TWCEYESTORM://',
    iphoneDownUrl: 'https://itunes.apple.com/cn/app/糖医生-糖尿病健康管理专家/id923809458?mt=8',
    androidSchema: 'scheme://com.xingbo/',
    androidDownUrl: 'http://webrs.xingbo.tv/app/XingboLive-release.apk',
    openApp: function(){
        var this_  =  this;
        //微信
        if(this_.isWeixin()){
            $(".weixin-tip").css("height",$(window).height());
            $(".weixin-tip").show();
            $('.weixin-tip').on('touchstart', function () {
                $(".weixin-tip").hide();
            });

        }else{//非微信浏览器
            if (navigator.userAgent.match(/(iPhone|iPod|iPad);?/i)) {
                var loadDateTime = new Date();
                window.setTimeout(function() {
                    var timeOutDateTime = new Date();
                    if (timeOutDateTime - loadDateTime < 5000) {
                        window.location = this_.iphoneDownUrl;//ios下载地址
                    } else {
                        window.close();
                    }
                },25);
                window.location = this.iphoneSchema;
            }else if (navigator.userAgent.match(/android/i)) {
                try {
                    window.location = this_.androidSchema;
                    setTimeout(function(){
                        window.location=this_.androidDownUrl; //android下载地址

                    },500);
                } catch(e) {}
            }
        }
    },
    isWeixin: function(){ //判断是否是微信
        var ua = navigator.userAgent.toLowerCase();
        if (ua.match(/MicroMessenger/i) == "micromessenger") {
            return true;
        } else {
            return false;
        }
    }

};
*/

var baseScheme = "TWCEYESTORM://",
    baseLink="http://m.xxxx.com?";
var createScheme=function(options,isLink){
    var urlScheme=isLink?baseLink:baseScheme;
    for(var item in options){
        urlScheme=urlScheme+item + '=' + encodeURIComponent(options[item]) + "&";
    }
    urlScheme = urlScheme.substring(0, urlScheme.length - 1);
    return isLink?urlScheme:encodeURIComponent(urlScheme);
}

// var openApp=function(){
//     //生成你的scheme你也可以选择外部传入
//     var localUrl=createScheme({type:1,id:"sdsdewe2122"});
//     var openIframe=createIframe();
//     if(isIos()){
//         //判断是否是ios,具体的判断函数自行百度
//         if(isGreaterThan9()){
//             //判断是否为ios9以上的版本,跟其他判断一样navigator.userAgent判断,ios会有带版本号
//             localUrl=createScheme({type:1,id:"sdsdewe2122"},true);//代码还可以优化一下
//             location.href = localUrl;//实际上不少产品会选择一开始将链接写入到用户需要点击的a标签里
//             return;
//         }
//         window.location.href = localUrl;
//         var loadDateTime = Date.now();
//         setTimeout(function () {
//             var timeOutDateTime = Date.now();
//             if (timeOutDateTime - loadDateTime < 1000) {
//                 window.location.href = "你的下载页面";
//             }
//         }, 25);
//     }else if(isAndroid()){
//         //判断是否是android，具体的判断函数自行百度
//         if (isChrome()) {
//             //chrome浏览器用iframe打不开得直接去打开，算一个坑
//             window.location.href = localUrl;
//         } else {
//             //抛出你的scheme
//             openIframe.src = localUrl;
//         }
//         setTimeout(function () {
//             window.location.href = "你的下载页面";
//         }, 500);
//     }else{
//         //主要是给winphone的用户准备的,实际都没测过，现在winphone不好找啊
//         openIframe.src = localUrl;
//         setTimeout(function () {
//             window.location.href = "你的下载页面";
//         }, 500);
//     }
// }