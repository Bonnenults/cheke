package com.autozi.cheke.mobile.facade;

import com.autozi.cheke.service.user.IChekeCommentService;
import com.autozi.cheke.service.user.IUserService;
import com.autozi.cheke.user.entity.*;

import com.autozi.cheke.user.type.MedalStatus;
import com.autozi.common.core.page.Page;
import com.autozi.common.utils.util2.CommonUtils;
import com.autozi.common.utils.util2.DateUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Administrator
 * @Date: 2018/6/23 15:28
 * @Description:
 */

@Component
public class CommentApiFacade {
    @Autowired
    private IChekeCommentService chekeCommentService;
    @Autowired
    private IUserService userService;
	@Autowired
    private MedalApiFacade medalApiFacade;

    public Long saveComment(Long replyId,Long userId,Long courseId,String content,Integer isSub){
        ChekeComment chekeComment = new ChekeComment();
        if(replyId != null && !"".equals(replyId)){
            chekeComment.setReplyId(replyId);
        }
        if(userId != null && !"".equals(userId)){
            chekeComment.setUserId(userId);
        }
        if(courseId != null && !"".equals(courseId)){
            chekeComment.setCourseId(courseId);
        }
        if(StringUtils.isNotBlank(content)){
            chekeComment.setContent(content);
        }
        chekeComment.setIsSub(isSub);
        chekeComment.setCreateTime(new Date());

        Long id = chekeCommentService.addChekeComment(chekeComment);

        if(isSub == 1){
            ChekeComment temp = chekeCommentService.getChekeCommentById(replyId);
            if(temp!=null){
                temp.setReplyCount(temp.getReplyCount()==null ? 1 : (temp.getReplyCount()+1));
                temp.setUpdateTime(new Date());
                chekeCommentService.updateChekeComment(temp);
            }

        }
        return id;
    }

    public JSONArray findCommentByPage(Page<Map<String, Object>> page, Map<String, Object> filters,User user) throws Exception {
        JSONArray array = new JSONArray();
        Page<ChekeComment> _page = new Page<ChekeComment>();
        CommonUtils.pageConversion(page, _page);
        _page = chekeCommentService.findCommentPage(_page, filters);

        for (ChekeComment chekeComment : _page.getResult()) {
            array.add(convertChekeComment(chekeComment,user));
        }

        CommonUtils.pageConversion(_page, page);
        return array;
    }

    private JSONObject convertChekeComment(ChekeComment chekeComment,User user){
        JSONObject data = new JSONObject();
        if(chekeComment == null){
            return null;
        }
        User createUser = userService.getUserById(chekeComment.getUserId());
        PersonalParty personalParty = userService.getPersonalPartyById(createUser.getPartyId());
        //头像
        data.put("imageUrl", personalParty.getImageUrl()==null?"":personalParty.getImageUrl());
		//勋章

        Map<String,Object> filter = new HashMap<>();
        filter.put("userId",chekeComment.getUserId());
        filter.put("status", MedalStatus.ADORN.getType());
        PersonalMedal personalMedal = medalApiFacade.getPersonalMedalByFilter(filter);
        if(personalMedal != null){
            Medal medal = medalApiFacade.getMedalById(personalMedal.getMedalId());
            if(medal != null){
                data.put("medalUrl", medal.getImageActive()==null?"":medal.getImageActive());
            }
        }else{
            data.put("medalUrl", "");
        }

        //推客号
        data.put("loginName", createUser.getLoginName()==null?"":createUser.getLoginName());
        //昵称
        data.put("name", createUser.getName()==null?"":createUser.getName());
        //评论ID
        data.put("commentId", chekeComment.getId()==null?"":chekeComment.getId());
        //评论内容
        data.put("content", chekeComment.getContent()==null?"":chekeComment.getContent());
        //评论条数
        data.put("replyCount", chekeComment.getReplyCount()==null?"0":chekeComment.getReplyCount());
        //评论时间
        String createTimeCN = "";
        Long createTime = chekeComment.getCreateTime().getTime();
        Long nowTime = System.currentTimeMillis();
        boolean isToday = false;
        try {
            isToday=chekeComment.getCreateTime().after(DateUtils.getTodayBegin());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(nowTime - createTime<60*60*1000){//1小时以内
            createTimeCN = "刚刚";
        }else if(isToday){
            createTimeCN = new SimpleDateFormat("HH:mm").format(chekeComment.getCreateTime());
        }else{
            createTimeCN = new SimpleDateFormat("MM月dd日").format(chekeComment.getCreateTime());
        }
        data.put("createTime", chekeComment.getCreateTime()==null?"":new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(chekeComment.getCreateTime()));
        data.put("createTimeCN", createTimeCN);
        //删除权限
        Integer deleteFlag = 0; //0:非本人评论，不允许删除
        if(user!=null && user.getId().equals(createUser.getId())){
            deleteFlag = 1;
        }
        data.put("deleteFlag", deleteFlag);

        return data;
    }

    public JSONObject addChekeCommentCallBack(Long id,User user){

        if(id == null || "".equals(id)){
            return null;
        }

        ChekeComment chekeComment = chekeCommentService.getChekeCommentById(id);
        if(chekeComment == null){
            return null;
        }

        return convertChekeComment(chekeComment,user);
    }

    public void deleteComment(Long commentId,User user){
        ChekeComment chekeComment = chekeCommentService.getChekeCommentById(commentId);
        if(chekeComment != null && chekeComment.getUserId().equals(user.getId())){
            Map<String, Object> filters = new HashMap<String, Object>();
            filters.put("replyId",chekeComment.getId());
            List<ChekeComment> list = chekeCommentService.getCommentListByFilter(filters);
            for (ChekeComment comment : list) {
                if(comment != null){
                    chekeCommentService.deleteChekeComment(comment);
                }
            }
            chekeCommentService.deleteChekeComment(chekeComment);
        }
    }
}
