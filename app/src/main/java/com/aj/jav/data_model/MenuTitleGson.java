package com.aj.jav.data_model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chris on 2017/12/6.
 */

public class MenuTitleGson {

    /**
     * status : {"code":200,"message":"success"}
     * response : {"menus":{"long":[{"menu_id":0,"menu_name":"最新"},{"menu_id":0,"menu_name":"排行"},{"menu_id":2,"menu_name":"无码"},{"menu_id":9,"menu_name":"独家"},{"menu_id":10,"menu_name":"中文"}],"short":[{"menu_id":0,"menu_name":"最新"},{"menu_id":0,"menu_name":"排行"},{"menu_id":6,"menu_name":"自拍"},{"menu_id":7,"menu_name":"偷拍"},{"menu_id":11,"menu_name":"无码"}]}}
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
        /**
         * menus : {"long":[{"menu_id":0,"menu_name":"最新"},{"menu_id":0,"menu_name":"排行"},{"menu_id":2,"menu_name":"无码"},{"menu_id":9,"menu_name":"独家"},{"menu_id":10,"menu_name":"中文"}],"short":[{"menu_id":0,"menu_name":"最新"},{"menu_id":0,"menu_name":"排行"},{"menu_id":6,"menu_name":"自拍"},{"menu_id":7,"menu_name":"偷拍"},{"menu_id":11,"menu_name":"无码"}]}
         */

        @SerializedName("menus")
        private MenusBean menus;

        public MenusBean getMenus() {
            return menus;
        }

        public void setMenus(MenusBean menus) {
            this.menus = menus;
        }

        public static class MenusBean {
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
                 * menu_id : 0
                 * menu_name : 最新
                 */

                @SerializedName("menu_id")
                private int menu_id;

                @SerializedName("menu_name")
                private String menu_name;

                public int getMenu_id() {
                    return menu_id;
                }

                public void setMenu_id(int menu_id) {
                    this.menu_id = menu_id;
                }

                public String getMenu_name() {
                    return menu_name;
                }

                public void setMenu_name(String menu_name) {
                    this.menu_name = menu_name;
                }
            }

            public static class ShortBean {
                /**
                 * menu_id : 0
                 * menu_name : 最新
                 */

                @SerializedName("menu_id")
                private int menu_id;

                @SerializedName("menu_name")
                private String menu_name;

                public int getMenu_id() {
                    return menu_id;
                }

                public void setMenu_id(int menu_id) {
                    this.menu_id = menu_id;
                }

                public String getMenu_name() {
                    return menu_name;
                }

                public void setMenu_name(String menu_name) {
                    this.menu_name = menu_name;
                }
            }
        }
    }
}
