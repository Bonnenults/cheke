package com.autozi.cheke.mobile.facade;

import java.text.SimpleDateFormat;
import java.util.*;

import com.autozi.cheke.basic.entity.Notice;
import com.autozi.cheke.service.basic.INoticeService;
import com.autozi.cheke.settle.entity.AccountLog;
import com.autozi.common.utils.util2.CommonUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autozi.cheke.party.entity.ChekeLetter;
import com.autozi.cheke.party.entity.ChekeLetterRelation;
import com.autozi.cheke.party.entity.Party;
import com.autozi.cheke.service.party.IChekeLetterService;
import com.autozi.cheke.service.party.IPartyService;
import com.autozi.cheke.service.user.IUserService;
import com.autozi.cheke.user.entity.PersonalParty;
import com.autozi.cheke.user.entity.User;
import com.autozi.common.core.page.Page;

@Component
public class MessageApiFacade {

	@Autowired
	private IChekeLetterService chekeLetterService;
	@Autowired
	private IPartyService partyService;
	@Autowired
	private IUserService userService;
	@Autowired
	private INoticeService noticeService;
	
	
	public void sendMessage(Long userId, Long partyId, String content) {
		ChekeLetter chekeLetter = new ChekeLetter();
		chekeLetter.setFromUserId(userId);
		chekeLetter.setContent(content);
		chekeLetter.setToUserId(partyId);
		chekeLetterService.toCheke(chekeLetter);
	}


	public JSONArray getTaskList(Page<Map<String,Object>> page, Map<String, Object> filters) {
		Page<ChekeLetterRelation> _page = new Page<>();
		CommonUtils.pageConversion(page, _page);
		_page = chekeLetterService.findPageForMap(_page, filters);
		JSONArray array = new JSONArray();
		for (ChekeLetterRelation relation : _page.getResult()) {
			JSONObject obj = new JSONObject();
			Party party = partyService.getPartyById(relation.getPartyId());
			obj.put("partyName", party.getName()==null?"":party.getName());
			obj.put("partyImage", party.getImageUrl()==null?"":party.getImageUrl());
			obj.put("content", relation.getContent()==null?"":relation.getContent());
			obj.put("relationId", relation.getId());
			obj.put("partyId", party.getId());

			String timeStr = "";
			try{
				Date time = relation.getUpdateTime();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date nowDate = new Date();
				String startStr = format.format(nowDate);
				startStr += " 00:00:00";
				long timeMills = nowDate.getTime() - time.getTime();
				long startMills = nowDate.getTime()-format.parse(startStr).getTime();
				if(timeMills>startMills){
					timeStr = new SimpleDateFormat("MM月dd日").format(time);
				}else{
					timeStr = new SimpleDateFormat("HH:mm").format(time);
				}
			}catch (Exception e){
				e.printStackTrace();
			}
			obj.put("time", timeStr);
			array.add(obj);
		}
		CommonUtils.pageConversion(_page, page);
		return array;
	}


	public JSONArray getMessageHistory(Long userId, Long relationId) {
		ChekeLetterRelation relation = chekeLetterService.getChekeLetterRelationById(relationId);
		List<ChekeLetter> letterList = chekeLetterService.getListByRelationId(relationId);
		User user = userService.getUserById(userId);
		PersonalParty personalParty = userService.getPersonalPartyById(user.getPartyId());
		Party party = partyService.getPartyById(relation.getPartyId());
		JSONArray array = new JSONArray();
		for (ChekeLetter chekeLetter : letterList) {
			JSONObject obj = new JSONObject();
			obj.put("content", chekeLetter.getContent());
			obj.put("createTime", new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(chekeLetter.getCreateTime()));
			if(chekeLetter.getFromUserId().longValue()==userId.longValue()){
				obj.put("fomer", "tk");
				obj.put("fomerImage", personalParty.getImageUrl()==null?"":personalParty.getImageUrl());
			}else{
				obj.put("fomer", "ck");
				obj.put("fomerImage", party.getImageUrl()==null?"":party.getImageUrl());
			}
			array.add(obj);
		}
		return array;
	}

	public JSONArray getMessage(Long userId, Long partyId) {
		Page<ChekeLetterRelation> page = new Page<>();
		Map<String,Object> filters = new HashMap<>();
		filters.put("userId",userId);
		filters.put("partyId",partyId);
		page = chekeLetterService.findPageForMap(page,filters);
		ChekeLetterRelation relation = null;
		if(page.getResult()!=null && page.getResult().size()>0){
			relation = page.getResult().get(0);
		}
		if(relation==null){
			return new JSONArray();
		}
		List<ChekeLetter> letterList = chekeLetterService.getListByRelationId(relation.getId());
		User user = userService.getUserById(userId);
		PersonalParty personalParty = userService.getPersonalPartyById(user.getPartyId());
		Party party = partyService.getPartyById(relation.getPartyId());
		JSONArray array = new JSONArray();
		for (ChekeLetter chekeLetter : letterList) {
			JSONObject obj = new JSONObject();
			obj.put("content", chekeLetter.getContent());
			obj.put("createTime", new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(chekeLetter.getCreateTime()));
			if(chekeLetter.getFromUserId().longValue()==userId.longValue()){
				obj.put("fomer", "tk");
				obj.put("fomerImage", personalParty.getImageUrl());
			}else{
				obj.put("fomer", "ck");
				obj.put("fomerImage", party.getImageUrl());
			}
			array.add(obj);
		}
		return array;
	}


	public JSONArray getNoticeList(Page<Notice> page, Map<String, Object> filters) {
		page = noticeService.findPageForMap(page, filters);
		JSONArray array = new JSONArray();
		for (Notice notice : page.getResult()) {
			JSONObject obj = new JSONObject();
			obj.put("noticeId", notice.getId());
			obj.put("title", notice.getTitle());
			obj.put("content", notice.getContent());
			obj.put("status", notice.getStatus());
			SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			obj.put("time", format.format(notice.getCreateTime()));
			array.add(obj);
		}
		return array;
	}


	public JSONObject getNoticeDetail(Long id, Long noticeId) {
		Notice notice = noticeService.getNoticeById(noticeId);
		JSONObject data = new JSONObject();
		data.put("title", notice.getTitle());
		data.put("content", notice.getContent());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		data.put("time", format.format(notice.getCreateTime()));
		//将通知置为已读
		Notice notice1 = new Notice();
		notice1.setId(noticeId);
		notice1.setStatus(1);
		noticeService.update(notice1);
		return data;
	}

	public static void main(String[] args) throws Exception{
		String timeStr = "";
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = format1.parse("2018-01-25 23:59:59");

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = new Date();
		String startStr = format.format(nowDate);
		startStr += " 00:00:00";
		long timeMills = nowDate.getTime() - time.getTime();
		long startMills = nowDate.getTime()-format.parse(startStr).getTime();
		if(timeMills>startMills){
			timeStr = new SimpleDateFormat("MM月dd日").format(time);
		}else{
			timeStr = new SimpleDateFormat("HH:mm").format(time);
		}
		System.out.println(timeStr);
	}

}
