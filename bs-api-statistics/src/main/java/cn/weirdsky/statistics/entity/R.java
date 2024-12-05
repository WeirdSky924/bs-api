package cn.weirdsky.statistics.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class R implements Serializable {

    private Integer code;
    private String message;
    private Object data;

    public R(Integer code, String msg, Object data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public R(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public R() {
    }

    public static R ok() {
        return new R(Code.OK, "成功！");
    }

    public static R error() {
        return new R(Code.ERROR, "失败！");
    }

    public static R error(Integer code, String msg) {
        return new R(code, msg);
    }

    public static R ok(Object data) {
        return new R(Code.GET_OK, "成功！", data);
    }

    public static R errorMsg(String msg) {
        return new R(Code.ERROR, msg);
    }

    public static R okMsg(String msg) {
        return new R(Code.OK, msg);
    }

    public static R ok(Integer code, String msg) {
        return new R(code, msg);
    }

    public static R okDel() {
        return R.ok(Code.DELETE_OK, "删除成功！");
    }

    public static R errorDel() {
        return R.error(Code.DELETE_ERR, "删除失败！");
    }

    public static R okSave() {
        return R.ok(Code.SAVE_OK, "保存成功！");
    }

    public static R errorSave() {
        return R.error(Code.SAVE_ERR, "保存失败！");
    }

    public static R okUpdate() {
        return R.ok(Code.UPDATE_OK, "修改成功！");
    }

    public static R errorUpdate() {
        return R.error(Code.UPDATE_ERR, "修改失败！");
    }
}
