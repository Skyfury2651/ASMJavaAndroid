package com.example.asm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "ASM";
    public static final int DB_VERSION = 1;
    public static final String PAYMENTS_TABLE = "TBL_PAYMENT";
    public static final String CATEGORIES_TABLE_NAME = "TBL_CATEGORY";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String CATEGORY_ID = "category_id";
    public static final String DETAIL = "detail";
    public static final String AMOUNT = "amount";
    public static final String DESCRIPTION = "description";
    public static final String PAYMENT_DATE = "payment_date";


    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlPayment = "CREATE TABLE " + PAYMENTS_TABLE + "( " +
                ID + " INTEGER PRIMARY KEY, " +
                CATEGORY_ID + " INTEGER," +
                NAME + " TEXT, " +
                PAYMENT_DATE + "DATE," +
                DETAIL + " TEXT, " +
                AMOUNT + " REAL, " +
                DESCRIPTION + " TEXT," +
                " FOREIGN KEY( " + CATEGORY_ID + ")" + " REFERENCES " + CATEGORIES_TABLE_NAME + "(" + ID + "));";
        sqLiteDatabase.execSQL(sqlPayment);

        String sqlCategory = "CREATE TABLE " + CATEGORIES_TABLE_NAME + "( " +
                ID + " INTEGER PRIMARY KEY, " +
                NAME + " TEXT);";
        sqLiteDatabase.execSQL(sqlCategory);

        String sqlSeedCategory = "INSERT INTO " + CATEGORIES_TABLE_NAME + " (" + NAME + " ) VALUES"
                + "('Category 1')" +
                ", ('Category 2')";
        sqLiteDatabase.execSQL(sqlSeedCategory);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + PAYMENTS_TABLE;
        sqLiteDatabase.execSQL(sql);
        String sql2 = "DROP TABLE IF EXISTS " + CATEGORIES_TABLE_NAME;
        sqLiteDatabase.execSQL(sql2);
        onCreate(sqLiteDatabase);
    }

    public Cursor getAllPayments() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + PAYMENTS_TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }

    public Cursor getAllCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + CATEGORIES_TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }

    public String addPayment(String name, int categoryId, String detail, String paymentDate, String amount, String description) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(CATEGORY_ID, categoryId);
        contentValues.put(PAYMENT_DATE, paymentDate);
        contentValues.put(DETAIL, detail);
        contentValues.put(AMOUNT, amount);
        contentValues.put(DESCRIPTION, description);
        long isAdd = db.insert(PAYMENTS_TABLE, null, contentValues);
        if (isAdd == -1) {
            return "Add Fail";
        }
        db.close();
        return "Add Success";
    }

    public String addCategory(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        long isAdd = db.insert(CATEGORIES_TABLE_NAME, null, contentValues);
        if (isAdd == -1) {
            return "Add Fail";
        }
        db.close();
        return "Add Success";
    }

    public String updatePayment(int id, String name, int categoryId, String detail, String paymentDate, double amount, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(CATEGORY_ID, categoryId);
        contentValues.put(DETAIL, detail);
        contentValues.put(PAYMENT_DATE, paymentDate);
        contentValues.put(AMOUNT, amount);
        contentValues.put(DESCRIPTION, description);
        long isUpdate = db.update(PAYMENTS_TABLE, contentValues, ID + " = ? ", new String[]{id + ""});
        if (isUpdate > 0) {
            return "Update Success";
        }
        db.close();
        return "Update Failed";
    }

    public String updateCategory(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        long isUpdate = db.update(CATEGORIES_TABLE_NAME, contentValues, ID + " = ? ", new String[]{id + ""});
        if (isUpdate > 0) {
            return "Update Success";
        }
        db.close();
        return "Update Failed";
    }

    public String deleteCategory(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int isDelete = db.delete(CATEGORIES_TABLE_NAME, ID + " = ? ", new String[]{id + ""});
        if (isDelete > 0) {
            return "delete success";
        }
        db.close();
        return "delete failed";
    }

    public String deletePayment(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int isDelete = db.delete(PAYMENTS_TABLE, ID + " = ? ", new String[]{id + ""});
        if (isDelete > 0) {
            return "delete success";
        }
        db.close();
        return "delete failed";
    }
}