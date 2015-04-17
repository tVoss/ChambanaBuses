package com.prettymuchabigdeal.chambanabuses.mtd.response;

import java.util.Date;

/**
 * Created by tyler on 1/31/15.
 */
public class Response {

    public String changesetID;
    public boolean newChangeset;
    //public Date time;
    public Status status;

    public static class Status {
        public int code;
        public String msg;
    }

}
