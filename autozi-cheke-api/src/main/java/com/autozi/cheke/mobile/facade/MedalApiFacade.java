package com.autozi.cheke.mobile.facade;

import com.autozi.cheke.service.user.IMedalService;
import com.autozi.cheke.service.user.IPersonalMedalService;
import com.autozi.cheke.user.entity.Medal;
import com.autozi.cheke.user.entity.PersonalMedal;
import com.autozi.cheke.user.type.MedalStatus;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mingxin.li on 2018/6/20 18:02.
 */

@Component
public class MedalApiFacade {
    @Autowired
    private IMedalService medalService;

    @Autowired
    private IPersonalMedalService personalMedalService;

    public JSONArray listMedal(Map<String,Object> filter,Map<String,Object> filter_per){
        JSONArray array = new JSONArray();
        List<Medal> list = medalService.listMedal(filter);
        List<PersonalMedal> list_per = personalMedalService.listPersonalMedal(filter_per);


        if(list_per.size()>0){
            for (PersonalMedal personalMedal:list_per){
                for(int i=0;i<list.size();i++){
                    if(list.size()>0 && (personalMedal.getMedalId()).equals(list.get(i).getId())){
                        array.add(convertMedal(list.get(i),personalMedal));
                        list.remove(i);
                    }
                }
            }
        }
        for(Medal medal:list){
            array.add(convertMedal(medal));
        }

        return array;
    }

    private JSONObject convertMedal(Medal medal,PersonalMedal personalMedal) {

        JSONObject data = new JSONObject();
        //勋章Id
        data.put("medalId", medal.getId()==null?"":medal.getId());
        //勋章名称
        data.put("medalName", medal.getMedalName()==null?"":medal.getMedalName());
        //勋章图片地址
        data.put("image", medal.getImageActive()==null?"":medal.getImageActive());
        //勋章获得时间
        data.put("obtainTime", personalMedal.getCreateTime()==null?"" : new SimpleDateFormat("yyyy.MM.dd").format(personalMedal.getCreateTime()));
        //勋章状态
        data.put("status", personalMedal.getStatus()==null?"":personalMedal.getStatus());
        //勋章状态（CN）
        data.put("statusCN", personalMedal.getStatus()==null?"": MedalStatus.getNameByType(personalMedal.getStatus()));
        //勋章個數
        if(personalMedal.getNum()!=null && personalMedal.getNum() > 1){
            data.put("num", personalMedal.getNum()==null?"0":personalMedal.getNum());
        }

        return data;
    }

    private JSONObject convertMedal(Medal medal) {
        JSONObject data = new JSONObject();
        //勋章Id
        data.put("medalId", medal.getId()==null?"":medal.getId());
        //勋章名称
        data.put("medalName", medal.getMedalName()==null?"":medal.getMedalName());
        //勋章图片地址
        data.put("image", medal.getImage()==null?"":medal.getImage());
        //勋章状态
        data.put("status", medal.getStatus()==null?"":medal.getStatus());
        //勋章状态（CN）
        data.put("statusCN", medal.getStatus()==null?"": MedalStatus.getNameByType(medal.getStatus()));


        return data;
    }

    public PersonalMedal getPersonalMedalByFilter(Map<String,Object> filter){
        return personalMedalService.getPersonalMedalByFilter(filter);
    }

    public void updatePersonalMedal(PersonalMedal personalMedal){
        personalMedalService.updatePersonalMedal(personalMedal);
    }

    public void setPersonalMedalUnadorn(Long userId){
        Map<String,Object> filter = new HashMap<>();
        filter.put("userId",userId);
        filter.put("status",MedalStatus.ADORN.getType());
        List<PersonalMedal> list = personalMedalService.listPersonalMedal(filter);
        if(list.size()>0){
            PersonalMedal personalMedal = list.get(0);
            personalMedal.setStatus(MedalStatus.OBTAIN.getType());
            personalMedal.setUpdateTime(new Date());
            personalMedalService.updatePersonalMedal(personalMedal);

            this.reduceMedalAdornNum(personalMedal.getMedalId());
        }

    }

    public JSONObject getMedalInfo(Long medalId){
        JSONObject data = new JSONObject();

        Medal medal = medalService.getMedalInfo(medalId);
        if(medal == null){
            return null;
        }
        data.put("medalId",medal.getId());
        data.put("medalName",medal.getMedalName());
        data.put("image",medal.getImageActive());
        data.put("intro",medal.getIntro());
        data.put("obtainNum",medal.getObtainNum());
        data.put("adornNum",medal.getAdornNum());
        return data;
    }

    public boolean checkIsObtain(Long medalId,Long userId){
        Map<String,Object> filter = new HashMap<>();
        filter.put("medalId",medalId);
        filter.put("userId",userId);
        List<PersonalMedal> list = personalMedalService.listPersonalMedal(filter);
        if(list.size() != 0){
            return true;
        }
        return false;
    }

    public boolean checkIsAdorn(Long medalId,Long userId){
        Map<String,Object> filter = new HashMap<>();
        filter.put("medalId",medalId);
        filter.put("userId",userId);
        filter.put("status",MedalStatus.ADORN.getType());
        List<PersonalMedal> list = personalMedalService.listPersonalMedal(filter);
        if(list.size() != 0){
            return true;
        }
        return false;
    }

    public Medal getMedalById(Long medalId){
        Medal medal = medalService.getMedalInfo(medalId);
        if(medal == null){
            return null;
        }
        return medal;
    }

    public void countMedalObtainNum(Long medalId){
        Medal medal = medalService.getMedalInfo(Long.valueOf(medalId));
        if(medal != null){
            medal.setObtainNum(medal.getObtainNum() + 1);
            medal.setUpdateTime(new Date());
            medalService.updateMedalInf(medal);
        }
    }

    public void countMedalAdornNum(Long medalId){
        Medal medal = medalService.getMedalInfo(medalId);
        if(medal != null){
            medal.setAdornNum(medal.getAdornNum() + 1);
            medal.setUpdateTime(new Date());
            medalService.updateMedalInf(medal);
        }
    }

    private void reduceMedalAdornNum(Long medalId){
        Medal medal = medalService.getMedalInfo(Long.valueOf(medalId));
        if(medal != null){
            medal.setAdornNum((medal.getAdornNum() - 1)<0 ? 0 : (medal.getAdornNum() - 1));
            medal.setUpdateTime(new Date());
            medalService.updateMedalInf(medal);
        }
    }

}
