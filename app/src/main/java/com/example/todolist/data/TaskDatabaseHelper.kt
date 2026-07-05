package com.example.todolist.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TaskDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_TASKS (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_TITLE TEXT NOT NULL,
                $COL_DESCRIPTION TEXT,
                $COL_CATEGORY TEXT,
                $COL_PRIORITY TEXT,
                $COL_DEADLINE TEXT,
                $COL_IS_DONE INTEGER DEFAULT 0
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TASKS")
        onCreate(db)
    }

    fun insertTask(task: Task): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_TITLE, task.title)
            put(COL_DESCRIPTION, task.description)
            put(COL_CATEGORY, task.category)
            put(COL_PRIORITY, task.priority)
            put(COL_DEADLINE, task.deadline)
            put(COL_IS_DONE, if (task.isDone) 1 else 0)
        }
        return db.insert(TABLE_TASKS, null, values)
    }

    fun getAllTasks(keyword: String = "", filter: String = "Semua"): List<Task> {
        val tasks = mutableListOf<Task>()
        val db = readableDatabase
        val whereParts = mutableListOf<String>()
        val args = mutableListOf<String>()

        if (keyword.isNotBlank()) {
            whereParts.add("($COL_TITLE LIKE ? OR $COL_DESCRIPTION LIKE ? OR $COL_CATEGORY LIKE ?)")
            val value = "%$keyword%"
            args.add(value)
            args.add(value)
            args.add(value)
        }

        when (filter) {
            "Selesai" -> whereParts.add("$COL_IS_DONE = 1")
            "Belum Selesai" -> whereParts.add("$COL_IS_DONE = 0")
            "Prioritas Tinggi" -> {
                whereParts.add("$COL_PRIORITY = ?")
                args.add("Tinggi")
            }
        }

        val selection = if (whereParts.isEmpty()) null else whereParts.joinToString(" AND ")
        val selectionArgs = if (args.isEmpty()) null else args.toTypedArray()

        val cursor = db.query(
            TABLE_TASKS,
            null,
            selection,
            selectionArgs,
            null,
            null,
            "$COL_ID DESC"
        )

        cursor.use {
            while (it.moveToNext()) {
                tasks.add(cursorToTask(it))
            }
        }
        return tasks
    }

    fun getTaskById(id: Long): Task? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_TASKS,
            null,
            "$COL_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        cursor.use {
            return if (it.moveToFirst()) cursorToTask(it) else null
        }
    }

    fun updateTask(task: Task): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_TITLE, task.title)
            put(COL_DESCRIPTION, task.description)
            put(COL_CATEGORY, task.category)
            put(COL_PRIORITY, task.priority)
            put(COL_DEADLINE, task.deadline)
            put(COL_IS_DONE, if (task.isDone) 1 else 0)
        }
        return db.update(TABLE_TASKS, values, "$COL_ID = ?", arrayOf(task.id.toString()))
    }

    fun updateTaskStatus(id: Long, isDone: Boolean): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_IS_DONE, if (isDone) 1 else 0)
        }
        return db.update(TABLE_TASKS, values, "$COL_ID = ?", arrayOf(id.toString()))
    }

    fun deleteTask(id: Long): Int {
        val db = writableDatabase
        return db.delete(TABLE_TASKS, "$COL_ID = ?", arrayOf(id.toString()))
    }

    private fun cursorToTask(cursor: android.database.Cursor): Task {
        return Task(
            id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID)),
            title = cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE)),
            description = cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION)) ?: "",
            category = cursor.getString(cursor.getColumnIndexOrThrow(COL_CATEGORY)) ?: "Umum",
            priority = cursor.getString(cursor.getColumnIndexOrThrow(COL_PRIORITY)) ?: "Sedang",
            deadline = cursor.getString(cursor.getColumnIndexOrThrow(COL_DEADLINE)) ?: "-",
            isDone = cursor.getInt(cursor.getColumnIndexOrThrow(COL_IS_DONE)) == 1
        )
    }

    companion object {
        private const val DATABASE_NAME = "taskmate.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_TASKS = "tasks"
        private const val COL_ID = "id"
        private const val COL_TITLE = "title"
        private const val COL_DESCRIPTION = "description"
        private const val COL_CATEGORY = "category"
        private const val COL_PRIORITY = "priority"
        private const val COL_DEADLINE = "deadline"
        private const val COL_IS_DONE = "is_done"
    }
}
