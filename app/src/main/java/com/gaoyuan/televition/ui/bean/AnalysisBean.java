package com.gaoyuan.televition.ui.bean;

import java.util.List;

/**
 * Created by gaoyuan on 2018/5/17.
 */

public class AnalysisBean {


    /**
     * is_read : 1
     * list : [{"id":27,"content":"http://206dy.com/vip.php?url=","uptime":1526549131,"nums":0}]
     */

    private int is_read;
    private List<ListBean> list;

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 27
         * content : http://206dy.com/vip.php?url=
         * uptime : 1526549131
         * nums : 0
         */

        private int id;
        private String content;
        private int uptime;
        private int nums;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getUptime() {
            return uptime;
        }

        public void setUptime(int uptime) {
            this.uptime = uptime;
        }

        public int getNums() {
            return nums;
        }

        public void setNums(int nums) {
            this.nums = nums;
        }
    }
}
