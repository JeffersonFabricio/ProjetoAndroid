package fabricio.jefferson.listadetarefas.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import fabricio.jefferson.listadetarefas.model.User

class MyDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        // Versão database
        private val DATABASE_VERSION = 1

        // Nome da base de dados
        private val DATABASE_NAME = "UserManager.db"

        // Nome da tabela
        private val TABLE_USER = "USER"

        // Nomes das colunas
        private val COLUMN_USER_ID = "USER_ID"
        private val COLUMN_USER_NAME = "USER_NAME"
        private val COLUMN_USER_CPF = "USER_CPF"
        private val COLUMN_USER_EMAIL = "USER_EMAIL"
        private val COLUMN_USER_LOGIN= "USER_LOGIN"
        private val COLUMN_USER_PASSWORD = "USER_PASSWORD"
    }

    // create table sql
    private val CREATE_USER_TABLE = (
            "CREATE TABLE IF NOT EXISTS " +
                    "${TABLE_USER} (" +
                    "${COLUMN_USER_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${COLUMN_USER_NAME} TEXT," +
                    "${COLUMN_USER_CPF} TEXT," +
                    "${COLUMN_USER_EMAIL} TEXT," +
                    "${COLUMN_USER_LOGIN} TEXT," +
                    "${COLUMN_USER_PASSWORD} TEXT" + ")"
            )

    // drop table sql query
    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        //Deleta a tabela se ela já existe
        db.execSQL(DROP_USER_TABLE)

        // Cria as tabelas denovo
        onCreate(db)

    }

    fun getAllUser(): List<User> {

        // array of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID, COLUMN_USER_NAME, COLUMN_USER_CPF,
            COLUMN_USER_EMAIL, COLUMN_USER_LOGIN, COLUMN_USER_PASSWORD)

        // sorting orders
        val sortOrder = "$COLUMN_USER_NAME ASC"
        val userList = ArrayList<User>()

        val db = this.readableDatabase

        // query the user table
        val cursor = db.query(TABLE_USER, //Table to query
            columns,            //columns to return
            null,     //columns for the WHERE clause
            null,  //The values for the WHERE clause
            null,      //group the rows
            null,       //filter by row groups
            sortOrder)         //The sort order
        if (cursor.moveToFirst()) {
            do {
                val user = User(
                    id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt(),
                    name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
                    cpf = cursor.getString(cursor.getColumnIndex(COLUMN_USER_CPF)),
                    email = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)),
                    login = cursor.getString(cursor.getColumnIndex(COLUMN_USER_LOGIN)),
                    password = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD))
                )

                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }

    fun addUser(user: User) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.name)
        values.put(COLUMN_USER_CPF, user.cpf)
        values.put(COLUMN_USER_EMAIL, user.email)
        values.put(COLUMN_USER_LOGIN, user.login)
        values.put(COLUMN_USER_PASSWORD, user.password)

        // Inserindo registro
        db.insert(TABLE_USER, null, values)
        db.close()
    }

    fun updateUser(user: User) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.name)
        values.put(COLUMN_USER_CPF, user.cpf)
        values.put(COLUMN_USER_EMAIL, user.email)
        values.put(COLUMN_USER_LOGIN, user.login)
        values.put(COLUMN_USER_PASSWORD, user.password)

        // atualizando registro
        db.update(TABLE_USER, values, "$COLUMN_USER_ID = ?",
            arrayOf(user.id.toString()))
        db.close()
    }

    fun deleteUser(user: User) {

        val db = this.writableDatabase
        // delete user record by id
        db.delete(TABLE_USER, "$COLUMN_USER_ID = ?",
            arrayOf(user.id.toString()))
        db.close()

    }

    fun checkUser(email: String): Boolean {

        // array of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID)
        val db = this.readableDatabase

        // selection criteria
        val selection = "$COLUMN_USER_EMAIL = ?"

        // selection argument
        val selectionArgs = arrayOf(email)

        // query user table with condition
        val cursor = db.query(TABLE_USER, //Table to query
            columns,        //columns to return
            selection,      //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null)  //The sort order

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0) {
            return true
        }

        return false
    }

    fun checkUser(login: String, password: String): Boolean {

        // array of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID)

        val db = this.readableDatabase

        // selection criteria
        val selection = "$COLUMN_USER_LOGIN = ? AND $COLUMN_USER_PASSWORD = ?"

        // selection arguments
        val selectionArgs = arrayOf(login, password)

        // query user table with conditions
        val cursor = db.query(TABLE_USER, //Table to query
            columns, //columns to return
            selection, //columns for the WHERE clause
            selectionArgs, //The values for the WHERE clause
            null,  //group the rows
            null, //filter by row groups
            null) //The sort order

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0)
            return true
        return false
    }

}
