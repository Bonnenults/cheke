/**
 * Created by ywy on 2017/7/17.
 */
(function () {

    var theParam = GetUrlParam();
    var articleId = theParam['articleId'];
    var userId =  theParam['userId'];
    var token =  theParam['token'];
    var time =  theParam['time'];
    var timeCheckValue =  theParam['timeCheckValue'];
    var currentProgress =  theParam['currentProgress'];

    // var articleId = $("#articleId").val();
    // var userId = $("#userId").val();
    // var contextpath = '${contextpath}';
    var contextpath = 'http://api.cheke.autozi.com';

    var shouldReportState = true;
    if (isNull(articleId) && isNull(token)){
        shouldReportState = false;
    }

    // 当前学习状态
    var currentState = 0;

    // 保存播放进度
    function savePlayProgress(){
        var progress = getVideoCurrentProgress()
        // 获取不到进度直接返回
        if (progress == null){
            return;
        }
        if (!shouldReportState) {
            return;
        }
        var timestamp = (new Date()).valueOf();
        // console.log("timestamp:"+ timestamp + "  t+k:" + (timestamp + ktime) + "  timeCheckValue:" + md5(timestamp + ktime))
        $.ajax({
            type: "POST",
            url:contextpath + '/course/tk/saveCourseProgress.api',
            data: {articleId: articleId,token:token,status:currentState,currentProgress:parseInt(progress),"time":time, "timeCheckValue":timeCheckValue},
            dataType: "json",
            cache: false,
            success: function (data) {
                if (data.code == "ok") {

                }
            }
        });


    }

    // 获取视频信息
    function getVideoInfo(){

        var url = "http://p8r297h5h.bkt.clouddn.com/1494808223824.mp4?e=1526370109&token=u1jJB9MNIvmfr29c-6Ta93nMbzj3fJEyzqCM-ALN:R7wYhfhDp6XhEjPUwNdFth29MhI="
        var player = videojs('my-player')
        player.src({src: url, type: "video/mp4"});

    }

    // 获取视频当前播放进度
    function  getVideoCurrentProgress() {
        var player = videojs('my-player')
        if (!isNull(player)){
            return player.currentTime()
        }
        return null;
    }


    $(document).ready(function () {
        var player = videojs('my-player', {}, function () {
            console.log('Good to go!');
            /*this.currentTime(5);*/
            // this.play(); // if you don't trust autoplay for some reason

            if(!isNull(currentProgress)){

                this.on('loadedmetadata', function(){
                    if (this.duration() > parseInt(currentProgress) +1) {
                        this.currentTime(currentProgress);
                    }
                });
            }

        })



        // 获取视频url
        //getVideoInfo()

        // 退出或刷新回调
        window.onbeforeunload = onbeforeunload_handler;
        function onbeforeunload_handler()
        {
            savePlayProgress()
            return;
        }


        player.width(window.screen.width - 30);
        player.on('play', function () {
            console.log('开始/恢复播放');
            player.play()
            currentState = 1;
            savePlayProgress()
        });
        player.on('pause', function () {
            console.log('暂停播放');
            currentState = 1;
            savePlayProgress()
        });
        player.on('ended', function () {
            console.log('结束播放');
            currentState = 2;
            savePlayProgress()
        });

        // var myPlayer = videojs('my-player');
        // var myPlayer = $('#my-player')
        // myPlayer.currentTime(20);
        // myPlayer.width(300);
        // myPlayer.height(480);
        // myPlayer.play();
        // getVideoDetail({
        //     'videoId' : videoId,
        //     'languageCode' : languageCode
        // });
        function getVideoDetail(param) {
            $.ajax({
                type: "POST",
                url:  AJAXURL + '/video/getVideoRecordById',
                dataType:"json",
                data: JSON.stringify(param),
                beforeSend: function(){
                },
                success: function(data) {
                    console.log(data);
                    if(data.code == 0) {
                        var field = data.data.field;
                        var video = data.data.video;
                        // $('#soure-play').src = 'video/' + video.videoUrl;
                        $('#video-play').attr('src', video.videoUrl);
                        $('#video-play').poster = video.videoCoverUrl;
                        $('.video-title').html(video.skiFieldName + video.skiCameraName + video.skiWayName);
                        $('.video-time').text(video.videoDate);
                        $('.field-desc').html(field.fieldDescription);
                        $('.field-image').attr('src',field.fieldImg);
                        // image1.src = field.fieldImg;
                        // $('.img-path img').src = field.fieldImg;

                        jQuery.i18n.properties({
                            name: 'strings', //资源文件名称
                            path: '../js/i18n/', //资源文件路径
                            mode: 'both', //用Map的方式使用资源文件中的值
                            language: languagePath,
                            callback: function () {//加载成功后设置显示内容
                                $('.video-play-count').text($.i18n.prop('播放') + video.videoPlayCount + $.i18n.prop('次'));

                            }
                        });
                    }
                    else
                    {
                        errorBox(data.errMsg);
                    }
                },
                error: function(){
                    errorConfirm('网络错误，是否重新加载');
                }
            });
        }
    });
})();

function getVideoFrame(){
    var elem = $('#my-player');
    if (isNull(elem)) {
        return -1;
    }
    var w = getVideoWidth();
    var h = getVideoHeight();
    var x = getVideoOriginX();
    var y = getVideoOriginY();

    return x + ',' + y + ',' + w + ',' + h;
}

function getVideoWidth(){
    var elem = $('#my-player');
    if (isNull(elem)) {
        return -1;
    }

    return elem.width();
}

function getVideoHeight(){
    var elem = $('#my-player');
    if (isNull(elem)) {
        return -1;
    }

    return elem.height();
}


function getVideoOriginY(){
    var elem = $('#my-player');
    if (isNull(elem)) {
        return -1;
    }

    return elem.offset().top;
}

function getVideoOriginX(){
    var elem = $('#my-player');
    if (isNull(elem)) {
        return -1;
    }

    return elem.offset().left;
}
