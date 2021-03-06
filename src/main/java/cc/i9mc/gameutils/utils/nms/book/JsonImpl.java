package cc.i9mc.gameutils.utils.nms.book;

import cc.i9mc.gameutils.utils.JsonBuilderUtil;

public class JsonImpl {

    private final static String TEXT_FORMAT = "\"text\":\"%s\"";
    private final static String CLICK_FORMAT = "\"clickEvent\":{\"action\":\"%s\",\"value\":\"%s\"}";
    private final static String HOVER_FORMAT = "\"hoverEvent\":{\"action\":\"%s\",\"value\":\"%s\"}";
    private final static String INSERT_FORMAT = " \"insertion\":\"%s\"";
    /**
     * 消息文本
     */
    public String text;
    /**
     * 点击操作
     */
    public String clickActionName;
    /**
     * 点击数据
     */
    public String clickActionData;
    /**
     * 悬浮操作
     */
    public String hoverActionName;
    /**
     * 悬浮数据
     */
    public String hoverActionData;
    /**
     * 插入数据
     */
    public String insertionData;

    public JsonImpl() {
        this("");
    }

    public JsonImpl(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "JsonImpl [text=" + text + ", clickActionName=" + clickActionName + ", clickActionData="
                + clickActionData + ", hoverActionName=" + hoverActionName + ", hoverActionData=" + hoverActionData
                + ", insertionData=" + insertionData + "]";
    }

    /**
     * @return 是否有文本
     */
    public boolean hasText() {
        return text != null && !text.isEmpty();
    }

    /**
     * 写入Json
     *
     * @param str 流对象
     */
    public void writeJson(StringBuilder str) {
        str.append("{");
        str.append(String.format(TEXT_FORMAT, new JsonBuilderUtil(text)));
        if (clickActionName != null) {
            str.append(",");
            str.append(String.format(CLICK_FORMAT, clickActionName, new JsonBuilderUtil(clickActionData)));
        }
        if (hoverActionName != null) {
            str.append(",");
            str.append(String.format(HOVER_FORMAT, hoverActionName, new JsonBuilderUtil(hoverActionData)));
        }
        if (insertionData != null) {
            str.append(",");
            str.append(String.format(INSERT_FORMAT, new JsonBuilderUtil(insertionData)));
        }
        str.append("}");
    }
}
