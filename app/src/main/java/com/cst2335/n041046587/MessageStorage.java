package com.cst2335.n041046587;

class MessageStorage {
    public String message;
    public boolean isSend;
    public long id;


    public MessageStorage(String message, boolean isSend, long id) {
        this.message = message;
        this.isSend = isSend;
        this.id = id;
    }

    public MessageStorage() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}