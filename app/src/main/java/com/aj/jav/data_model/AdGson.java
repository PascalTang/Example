package com.aj.jav.data_model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pascal on 2018/7/12.
 */

public class AdGson {
    /**
     * status : {"code":200,"message":"success"}
     * response : {"long":[{"ad_title":"网上真人真钱赌博!可提款!免费注册最高送1888元!","ad_img_url":"http://youxi8888.cc/dabing/xj1.gif","ad_link_url":"4159988.com"},{"ad_title":"网上真人真钱赌博!可提款!免费注册最高送1888元!","ad_img_url":"http://youxi8888.cc/dabing/xj1.gif","ad_link_url":"4159988.com"},{"ad_title":"网上真人真钱赌博!可提款!免费注册最高送1888元!","ad_img_url":"http://youxi8888.cc/dabing/xj1.gif","ad_link_url":"4159988.com"},{"ad_title":"网上真人真钱赌博!可提款!免费注册最高送1888元!","ad_img_url":"http://youxi8888.cc/dabing/xj1.gif","ad_link_url":"4159988.com"},{"ad_title":"网上真人真钱赌博!可提款!免费注册最高送1888元!","ad_img_url":"http://youxi8888.cc/dabing/xj1.gif","ad_link_url":"4159988.com"}],"short":[{"ad_title":"网上真人真钱赌博!可提款!免费注册最高送1888元!","ad_img_url":"http://youxi8888.cc/dabing/xj1.gif","ad_link_url":"4159988.com"},{"ad_title":"网上真人真钱赌博!可提款!免费注册最高送1888元!","ad_img_url":"http://youxi8888.cc/dabing/xj1.gif","ad_link_url":"4159988.com"},{"ad_title":"网上真人真钱赌博!可提款!免费注册最高送1888元!","ad_img_url":"http://youxi8888.cc/dabing/xj1.gif","ad_link_url":"4159988.com"},{"ad_title":"网上真人真钱赌博!可提款!免费注册最高送1888元!","ad_img_url":"http://youxi8888.cc/dabing/xj1.gif","ad_link_url":"4159988.com"},{"ad_title":"网上真人真钱赌博!可提款!免费注册最高送1888元!","ad_img_url":"http://youxi8888.cc/dabing/xj1.gif","ad_link_url":"4159988.com"}]}
     */

    @SerializedName("status")
    private StatusBean status;

    @SerializedName("response")
    private ResponseBean response;

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class StatusBean {
        /**
         * code : 200
         * message : success
         */

        @SerializedName("code")
        private int code;

        @SerializedName("message")
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class ResponseBean {
        @SerializedName("long")
        private List<LongBean> longX;
        @SerializedName("short")
        private List<ShortBean> shortX;

        public List<LongBean> getLongX() {
            return longX;
        }

        public void setLongX(List<LongBean> longX) {
            this.longX = longX;
        }

        public List<ShortBean> getShortX() {
            return shortX;
        }

        public void setShortX(List<ShortBean> shortX) {
            this.shortX = shortX;
        }

        public static class LongBean {
            /**
             * ad_title : 网上真人真钱赌博!可提款!免费注册最高送1888元!
             * ad_img_url : http://youxi8888.cc/dabing/xj1.gif
             * ad_link_url : 4159988.com
             */

            @SerializedName("ad_title")
            private String ad_title;

            @SerializedName("ad_img_url")
            private String ad_img_url;

            @SerializedName("ad_link_url")
            private String ad_link_url;

            public String getAd_title() {
                return ad_title;
            }

            public void setAd_title(String ad_title) {
                this.ad_title = ad_title;
            }

            public String getAd_img_url() {
                return ad_img_url;
            }

            public void setAd_img_url(String ad_img_url) {
                this.ad_img_url = ad_img_url;
            }

            public String getAd_link_url() {
                return ad_link_url;
            }

            public void setAd_link_url(String ad_link_url) {
                this.ad_link_url = ad_link_url;
            }
        }

        public static class ShortBean {
            /**
             * ad_title : 网上真人真钱赌博!可提款!免费注册最高送1888元!
             * ad_img_url : http://youxi8888.cc/dabing/xj1.gif
             * ad_link_url : 4159988.com
             */

            @SerializedName("ad_title")
            private String ad_title;

            @SerializedName("ad_img_url")
            private String ad_img_url;

            @SerializedName("ad_link_url")
            private String ad_link_url;

            public String getAd_title() {
                return ad_title;
            }

            public void setAd_title(String ad_title) {
                this.ad_title = ad_title;
            }

            public String getAd_img_url() {
                return ad_img_url;
            }

            public void setAd_img_url(String ad_img_url) {
                this.ad_img_url = ad_img_url;
            }

            public String getAd_link_url() {
                return ad_link_url;
            }

            public void setAd_link_url(String ad_link_url) {
                this.ad_link_url = ad_link_url;
            }
        }
    }
}
