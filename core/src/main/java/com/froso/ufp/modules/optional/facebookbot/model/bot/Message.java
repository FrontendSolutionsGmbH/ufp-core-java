package com.froso.ufp.modules.optional.facebookbot.model.bot;

import java.util.*;

/**
 * Created by ckleinhuix on 13/08/2016.
 */
public class Message {
    /*"mid":"mid.1457764197618:41d102a3e1ae206a38",
    "seq":73,
    "text":"hello, world!",
    "quick_reply": {
      "payload": "DEVELOPER_DEFINED_PAYLOAD"
    }
    */
    private String mid;
    private Long seq;
    private String text;
    private List<Attachment> attachments;
    private Attachment attachment;
    private List<QuickButton> quick_replies;
    private Postback quick_reply;

    public Postback getQuick_reply() {
        return quick_reply;
    }

    public void setQuick_reply(Postback quick_reply) {
        this.quick_reply = quick_reply;
    }

    /**
     * Gets attachment.
     *
     * @return the attachment
     */

    public Attachment getAttachment() {
        return attachment;
    }

    /**
     * Sets attachment.
     *
     * @param attachment the attachment
     */
    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    /**
     * Gets mid.
     *
     * @return the mid
     */
    public String getMid() {
        return mid;
    }

    /**
     * Sets mid.
     *
     * @param mid the mid
     */
    public void setMid(String mid) {
        this.mid = mid;
    }

    /**
     * Gets seq.
     *
     * @return the seq
     */
    public Long getSeq() {
        return seq;
    }

    /**
     * Sets seq.
     *
     * @param seq the seq
     */
    public void setSeq(Long seq) {
        this.seq = seq;
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets text.
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets attachments.
     *
     * @return the attachments
     */
    public List<Attachment> getAttachments() {

        return attachments;
    }

    /**
     * Sets attachments.
     *
     * @param attachments the attachments
     */
    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }


    /**
     * Gets quick replies.
     *
     * @return the quick replies
     */
    public List<QuickButton> getQuick_replies() {
        return quick_replies;
    }

    /**
     * Sets quick replies.
     *
     * @param quick_replies the quick replies
     */
    public void setQuick_replies(List<QuickButton> quick_replies) {
        this.quick_replies = quick_replies;
    }
}
