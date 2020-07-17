package com.autozi.cheke.web.party.facade;

import com.autozi.cheke.party.entity.Party;
import com.autozi.cheke.party.entity.PartyLog;
import com.autozi.cheke.party.type.IPartyType;
import com.autozi.cheke.service.party.IChekeFansService;
import com.autozi.cheke.service.party.IPartyService;
import com.autozi.cheke.service.user.IUserService;
import com.autozi.cheke.user.entity.User;
import com.autozi.cheke.web.party.vo.PartyView;
import com.autozi.cheke.web.upload.util.ImageUtils;
import com.autozi.common.utils.util1.UID;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * Created by lbm on 2017/11/28.
 */
@Component
public class PartyFacade {
    @Autowired
    private IPartyService partyService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IChekeFansService chekeFansService;

    public void addParty(PartyView partyView){
        //保存主体
        Party party = partyService.getPartyById(partyView.getId());
        convertToParty(partyView,party);
        //新增审核
        party.setStatus(IPartyType.STATUS_ADD_VERIFY);
        partyService.addParty(party);
    }

    public void editParty(PartyView partyView){
        if(partyView.getPartyClass()!=4){
            partyView.setInvoiceTitle(partyView.getCompanyName());
        }
        //保存主体
        Party party = partyService.getPartyById(partyView.getId());
        convertToParty(partyView,party);
        //根据状态判断是新增审核还是修改审核
        party.setStatus(IPartyType.STATUS_MODIFY_VERIFY);

        partyService.editParty(party);
    }

    private void convertToParty(PartyView partyView,Party party){
        // 处理图片
        //processImg(partyView,party);
        partyView.setName(party.getName());
        BeanUtils.copyProperties(partyView,party);
    }

    /**
     * 将临时目录中的图片存储到正式目录中
     * @param party
     */
    private void processImg(PartyView view,Party party){
        //营业执照
        String certificateImg = view.getCertificateImg();
        if(certificateImg!=null && !"".equals(certificateImg) && !certificateImg.equals(party.getCertificateImg())){
            String img = ImageUtils.storeImage(certificateImg);
            view.setCertificateImg(img);
        }

        //其它资格照片
        String certificateOther = view.getCertificateOther();
        if(certificateOther!=null && !"".equals(certificateOther) && !certificateOther.equals(party.getCertificateOther())){
            String img = ImageUtils.storeImage(certificateOther);
            view.setCertificateOther(img);
        }

        //授权证书
        String authorImg = view.getAuthorImg();
        if(authorImg!=null && !"".equals(authorImg) && !authorImg.equals(party.getAuthorImg())){
            String img = ImageUtils.storeImage(authorImg);
            view.setAuthorImg(img);
        }

        //身份证正面
        String identifyA = view.getIdentifyImgA();
        if(identifyA!=null && !"".equals(identifyA) && !identifyA.equals(party.getIdentifyImgA())){
            String img = ImageUtils.storeImage(identifyA);
            view.setIdentifyImgA(img);
        }

        //身份证反面
        String identifyB = view.getIdentifyImgB();
        if(identifyB!=null && !"".equals(identifyB) && !identifyB.equals(party.getIdentifyImgB())){
            String img = ImageUtils.storeImage(identifyB);
            view.setIdentifyImgB(img);
        }

    }

    public boolean checkChekeName(String name){
        HashMap<String,Object> filters = new HashMap<>();
        filters.put("name",name);
        List<Party> list = partyService.findList(filters);
        if(list!=null && list.size()>0){
            return true;
        }
        return false;
    }

    public Party getPartyById(Long partyId){
        Party party = partyService.getPartyById(partyId);
        return party;
    }

    public int getFansCount(Long partyId){
        return chekeFansService.getChekeFansCount(partyId);
    }

    public void modifyPassword(User user){
        userService.update(user);
    }

    public PartyLog getPartyLogByPartyId(Long partyId){
        PartyLog partyLog = partyService.getPartyLogByPartyId(partyId);
        return partyLog;
    }

    public void updateParty(Party party){
        partyService.update(party);
    }

    public static void main(String[] args) {
        Party p1 = new Party();
        Party p2 = new Party();
        p1.setName("p1");
        p2.setName("p2");
        BeanUtils.copyProperties(p1,p2);
        System.out.println(p2.getName());
    }
}
