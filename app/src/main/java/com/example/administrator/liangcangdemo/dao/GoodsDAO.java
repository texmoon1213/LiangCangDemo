package com.example.administrator.liangcangdemo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by 李金桐 on 2017/3/27.
 * QQ: 474297694
 * 功能: xxxx
 */

public class GoodsDAO {
    private DBGoods mailDB;

    public GoodsDAO(Context mContext) {
        mailDB = new DBGoods(mContext);
    }

    public void addMail(GoodsBean goodsBean) {
        if (goodsBean == null) return;

        SQLiteDatabase readableDatabase = mailDB.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(TableField.MAIL_NUM, goodsBean.getNumber());
        values.put(TableField.MAIL_ID, goodsBean.getId());
        readableDatabase.replace(TableField.TABLE_NAME, null, values);
    }

    public void updataMail(GoodsBean goodsBean) {
        if (goodsBean == null) return;

        SQLiteDatabase readableDatabase = mailDB.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(TableField.MAIL_NUM, goodsBean.getNumber());
        readableDatabase.update(TableField.TABLE_NAME, values, "id=?", new String[]{goodsBean.getId() + ""});
    }

    public void deleteMail(GoodsBean goodsBean) {
        if (goodsBean == null) return;
        SQLiteDatabase database = mailDB.getReadableDatabase();
        database.delete(TableField.TABLE_NAME, "id=?", new String[]{goodsBean.getId() + ""});
    }

    public ArrayList<GoodsBean> getAllMail() {
        SQLiteDatabase database = mailDB.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TableField.TABLE_NAME, null);
        if (cursor == null) return null;

        ArrayList<GoodsBean> datas = new ArrayList<>();

        GoodsBean bean;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(TableField.MAIL_ID));
            int number = cursor.getInt(cursor.getColumnIndex(TableField.MAIL_NUM));
            bean = new GoodsBean(id, number);

            datas.add(bean);
        }
        cursor.close();
        return datas;
    }
}
