package com.lulian.driver.entity.server.resulte;

import com.lulian.driver.entity.server.ProofPhotoBean;

import java.io.Serializable;
import java.util.List;

/**
 * 运单详情对象
 * @author hxb
 */
public class WaybillDetail implements Serializable {

    private Plain FormDetail;
    private List<ProofPhotoBean> FormDetailImg;

    public Plain getFormDetail() {
        return FormDetail;
    }

    public List<ProofPhotoBean> getFormDetailImg() {
        return FormDetailImg;
    }

    public static class Plain{
        private String Id;
        private String Number;
        private String OnLoadAreaID;
        private String OnLoadAdress;
        private String UnLoadAreaID;
        private String OnLoadArea;
        private String UnLoadAddress;
        private String UnLoadArea;
        private int Status;
        private String Usertype;
        private String GoodsName;
        private String Weight;
        private String Volume;
        private double AutomobileLength;
        private String AutomobileTypName;
        private String Remark;
        private String CreateTime;
        private String UserName;
        private String UserPhone;
        private int UserStar;
        private String LeadersName;
        private String LeadersPhone;
        private int LeadersStar;
        private int Price;

        public String getId() {
            return Id;
        }

        public String getNumber() {
            return Number;
        }

        public String getOnLoadAreaID() {
            return OnLoadAreaID;
        }

        public String getOnLoadAdress() {
            return OnLoadAdress;
        }

        public String getUnLoadAreaID() {
            return UnLoadAreaID;
        }

        public String getOnLoadArea() {
            return OnLoadArea;
        }

        public String getUnLoadAddress() {
            return UnLoadAddress;
        }

        public String getUnLoadArea() {
            return UnLoadArea;
        }

        public int getStatus() {
            return Status;
        }

        public String getUsertype() {
            return Usertype;
        }

        public String getGoodsName() {
            return GoodsName;
        }

        public String getWeight() {
            return Weight;
        }

        public String getVolume() {
            return Volume;
        }

        public double getAutomobileLength() {
            return AutomobileLength;
        }

        public String getAutomobileTypName() {
            return AutomobileTypName;
        }

        public String getRemark() {
            return Remark;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public String getUserName() {
            return UserName;
        }

        public String getUserPhone() {
            return UserPhone;
        }

        public int getUserStar() {
            return UserStar;
        }

        public String getLeadersName() {
            return LeadersName;
        }

        public String getLeadersPhone() {
            return LeadersPhone;
        }

        public int getLeadersStar() {
            return LeadersStar;
        }

        public int getPrice() {
            return Price;
        }
    }

}
