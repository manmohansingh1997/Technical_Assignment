package com.yoovis.yooquiz.DBUsers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

import java.util.ArrayList


class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun addUser(user: User): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBUsers.COLUMN_USER_MOBILE, user.usermobile)

        values.put(DBUsers.COLUMN_USER_PASSWORD, user.password)


        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(DBUsers.TABLE_NAME, null, values)
        Log.e("DB", "User added" + user.usermobile + "::" + newRowId)
        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteUser(userid: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBUsers.COLUMN_USER_MOBILE + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(userid)
        // Issue SQL statement.
        db.delete(DBUsers.TABLE_NAME, selection, selectionArgs)
        Log.e("DB", "User deleted" + userid)

        return true
    }

    fun updateUser(user: User): User? {
        val db = this.writableDatabase
        var res: Int

        // Creating content values
        val values = ContentValues()
        values.put(DBUsers.COLUMN_USER_MOBILE, user.usermobile)
        values.put(DBUsers.COLUMN_USER_PASSWORD, user.password)


        // update row in students table base on students.is value
        Log.e("DB", "User updating" + user.usermobile + "::" + user.usermobile)

        res = db.update(
            DBUsers.TABLE_NAME, values, "${DBUsers.COLUMN_USER_MOBILE} = ?",
            arrayOf(user.usermobile.toString())
        )
        return if (res == 1) {
            user.usermobile?.let { readUser(it) }
        } else {
            User()
        }
    }

    fun readUser(userid: String): User {
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(
                "select * from " + DBUsers.TABLE_NAME + " WHERE " + DBUsers.COLUMN_USER_MOBILE + "='" + userid + "'",
                null
            )
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return User()
        }

        var usermobile: String

        var pass: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                usermobile = cursor.getString(cursor.getColumnIndex(DBUsers.COLUMN_USER_MOBILE))
                pass = cursor.getString(cursor.getColumnIndex(DBUsers.COLUMN_USER_PASSWORD))

                Log.e("DB", "User updating:" + userid + "-Name:" + usermobile)
                return User(usermobile, pass)

            }
        }
        return User()
    }

    fun readAllUsers(): ArrayList<User> {
        val users = ArrayList<User>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBUsers.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var userMobile: String
        var pass: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                userMobile = cursor.getString(cursor.getColumnIndex(DBUsers.COLUMN_USER_MOBILE))

                pass = cursor.getString(cursor.getColumnIndex(DBUsers.COLUMN_USER_PASSWORD))

                Log.e("DB", "User readAll:" + userMobile + "-::Pass::" + pass)
                users.add(User(userMobile, pass))
                cursor.moveToNext()
            }
        }
        return users
    }

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "YoovisQuiz"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBUsers.TABLE_NAME + " (" +
                    DBUsers.COLUMN_USER_MOBILE + " TEXT PRIMARY KEY," +

                    DBUsers.COLUMN_USER_PASSWORD + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBUsers.TABLE_NAME
    }
}