package com.aj.jav.data_model;

import com.aj.jav.observer.GsonStatusInterface;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chris on 2017/12/6.
 */

public class MainListGson implements GsonStatusInterface {

    @Override
    public String getStatusMessage() {
        return getStatus().getMessage();
    }

    @Override
    public int getStatusCode() {
        return getStatus().getCode();
    }

    /**
     * status : {"code":200,"message":"success"}
     * response : {"videos":[{"video_id":"21923","video_title":"素人男女的對抗！男女混合 2[中文字幕]RCTD-032 ","actor":"桜ちなみ,松下美織","cover_url":{"portrait":"/imgs/thumb/39/7b81324c97.jpg?k=a1b3cbf80b7e1c6744fdea586006a055&t=1536833025","landscape":"/imgs/cover/f2/7b81324c97.jpg?k=34fe418c64b6a3c121e09cd8a952bc1a&t=1536833025"},"thumb":"/imgs/thumb/39/7b81324c97.jpg?k=a1b3cbf80b7e1c6744fdea586006a055&t=1536833025","cover":"/imgs/cover/f2/7b81324c97.jpg?k=34fe418c64b6a3c121e09cd8a952bc1a&t=1536833025","upload_date":1536831002,"release_date":1507132800,"video_duration":7800,"main_tag":["限免"],"second_tag":["中"],"video_like":false},{"video_id":"36017","video_title":"【数量限定】某サイトのレビューで炎上！？みんなの永遠の妹、人気No.1激似声優竹○彩○ 生写真3枚付き","actor":"素人","cover_url":{"portrait":"/imgs/thumb/8a/1188bbafba.jpg?k=0036bf15e4d2e26c4356194c04c91ed9&t=1536833025","landscape":"/imgs/cover/e3/1188bbafba.jpg?k=a59b31bf8e6749ee096013fec6867343&t=1536833025"},"thumb":"/imgs/thumb/8a/1188bbafba.jpg?k=0036bf15e4d2e26c4356194c04c91ed9&t=1536833025","cover":"/imgs/cover/e3/1188bbafba.jpg?k=a59b31bf8e6749ee096013fec6867343&t=1536833025","upload_date":1536829202,"release_date":1536854400,"video_duration":7200,"main_tag":["限免"],"second_tag":[],"video_like":false},{"video_id":"22581","video_title":"解禁真正中出し いきなり生精子10発注入！ 佐倉ねね","actor":"佐倉ねね","cover_url":{"portrait":"/imgs/thumb/d2/f7c8824f07.jpg?k=11b6fb7f2d84c5bdf5b6a1cd61b9fe9e&t=1536833025","landscape":"/imgs/cover/39/f7c8824f07.jpg?k=b3bb2204880709ecfa133a2e028e05bf&t=1536833025"},"thumb":"/imgs/thumb/d2/f7c8824f07.jpg?k=11b6fb7f2d84c5bdf5b6a1cd61b9fe9e&t=1536833025","cover":"/imgs/cover/39/f7c8824f07.jpg?k=b3bb2204880709ecfa133a2e028e05bf&t=1536833025","upload_date":1536827402,"release_date":1493568000,"video_duration":10200,"main_tag":["限免"],"second_tag":["中"],"video_like":false},{"video_id":"36018","video_title":"引っ越し先のお隣さんが露出多めのエッチな人妻さんだったので頼み込んでSEXや中出しの練習をさせてもらう事にした。 日向うみ","actor":"日向うみ","cover_url":{"portrait":"/imgs/thumb/2c/c280fd4bda.jpg?k=e92a9199f6b16bd3c66d6d0b636dcd98&t=1536833025","landscape":"/imgs/cover/e9/c280fd4bda.jpg?k=53c9a4e20ce2a92b01faa8a42459708f&t=1536833025"},"thumb":"/imgs/thumb/2c/c280fd4bda.jpg?k=e92a9199f6b16bd3c66d6d0b636dcd98&t=1536833025","cover":"/imgs/cover/e9/c280fd4bda.jpg?k=53c9a4e20ce2a92b01faa8a42459708f&t=1536833025","upload_date":1536825601,"release_date":1536768000,"video_duration":9000,"main_tag":["限免"],"second_tag":[],"video_like":false},{"video_id":"287","video_title":"班花竟然變成傳播妹後與我重逢！！現在負債累累的她不知是不是聞到事業成功的我身上的銅臭味，居然主動拿掉套子直接做愛！！還讓我中出！！2[中文字幕][IENE-750] ","actor":"西内るな,夏原あかり,深美せりな,三上絵理香","cover_url":{"portrait":"/imgs/thumb/fc/a2c4ed7b0c.jpg?k=f7805dad4217b86f5ab579c420c53a23&t=1536833025","landscape":"/imgs/cover/4a/a2c4ed7b0c.jpg?k=9631c7ac6838a92375a9c29c57dee6be&t=1536833025"},"thumb":"/imgs/thumb/fc/a2c4ed7b0c.jpg?k=f7805dad4217b86f5ab579c420c53a23&t=1536833025","cover":"/imgs/cover/4a/a2c4ed7b0c.jpg?k=9631c7ac6838a92375a9c29c57dee6be&t=1536833025","upload_date":1536823801,"release_date":1484755200,"video_duration":10800,"main_tag":["限免"],"second_tag":["中"],"video_like":false},{"video_id":"36019","video_title":"【数量限定】ひょっとこフェラチオ吸引ごっくん 妃月るい 生写真3枚付き","actor":"妃月るい","cover_url":{"portrait":"/imgs/thumb/56/62e16995d4.jpg?k=2d151193742e49e0536ea667631e0fce&t=1536833025","landscape":"/imgs/cover/cc/62e16995d4.jpg?k=781c00fc66a8ef80fd1fc5c271c14ee7&t=1536833025"}}]}
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
        @SerializedName("videos")
        private List<VideosBean> videos;

        @SerializedName("total_results")
        private int total_results;

        public List<VideosBean> getVideos() {
            return videos;
        }

        public void setVideos(List<VideosBean> videos) {
            this.videos = videos;
        }

        public int getTotal_results() {
            return total_results;
        }

        public void setTotal_results(int total_results) {
            this.total_results = total_results;
        }

        public static class VideosBean {
            /**
             * video_id : 21923
             * video_title : 素人男女的對抗！男女混合 2[中文字幕]RCTD-032
             * actor : 桜ちなみ,松下美織
             * cover_url : {"portrait":"/imgs/thumb/39/7b81324c97.jpg?k=a1b3cbf80b7e1c6744fdea586006a055&t=1536833025","landscape":"/imgs/cover/f2/7b81324c97.jpg?k=34fe418c64b6a3c121e09cd8a952bc1a&t=1536833025"}
             * thumb : /imgs/thumb/39/7b81324c97.jpg?k=a1b3cbf80b7e1c6744fdea586006a055&t=1536833025
             * cover : /imgs/cover/f2/7b81324c97.jpg?k=34fe418c64b6a3c121e09cd8a952bc1a&t=1536833025
             * upload_date : 1536831002
             * release_date : 1507132800
             * video_duration : 7800
             * main_tag : ["限免"]
             * second_tag : ["中"]
             * video_like : false
             */

            @SerializedName("video_id")
            private String video_id;

            @SerializedName("video_title")
            private String video_title;

            @SerializedName("actor")
            private String actor;

            @SerializedName("thumb")
            private String thumb;

            @SerializedName("cover")
            private String cover;

            @SerializedName("upload_date")
            private long upload_date;

            @SerializedName("release_date")
            private long release_date;

            @SerializedName("video_duration")
            private int video_duration;

            @SerializedName("video_like")
            private boolean video_like;

            @SerializedName("main_tag")
            private List<String> main_tag;

            @SerializedName("second_tag")
            private List<String> second_tag;

            public String getVideo_id() {
                return video_id;
            }

            public void setVideo_id(String video_id) {
                this.video_id = video_id;
            }

            public String getVideo_title() {
                return video_title;
            }

            public void setVideo_title(String video_title) {
                this.video_title = video_title;
            }

            public String getActor() {
                return actor;
            }

            public void setActor(String actor) {
                this.actor = actor;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public long getUpload_date() {
                return upload_date;
            }

            public void setUpload_date(long upload_date) {
                this.upload_date = upload_date;
            }

            public long getRelease_date() {
                return release_date;
            }

            public void setRelease_date(long release_date) {
                this.release_date = release_date;
            }

            public int getVideo_duration() {
                return video_duration;
            }

            public void setVideo_duration(int video_duration) {
                this.video_duration = video_duration;
            }

            public boolean isVideo_like() {
                return video_like;
            }

            public void setVideo_like(boolean video_like) {
                this.video_like = video_like;
            }

            public List<String> getMain_tag() {
                return main_tag;
            }

            public void setMain_tag(List<String> main_tag) {
                this.main_tag = main_tag;
            }

            public List<String> getSecond_tag() {
                return second_tag;
            }

            public void setSecond_tag(List<String> second_tag) {
                this.second_tag = second_tag;
            }
        }
    }
}
