package db

import java.sql.*
import java.util.*

var conn: Connection? = null

fun dbConnect() {
    val connectionProps = Properties()
    connectionProps["user"] = "root"
    connectionProps["password"] = "password"

    try {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance()
        conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/chat", connectionProps)
    } catch (ex: SQLException) {
        // handle any errors
        ex.printStackTrace()
    } catch (ex: Exception) {
        // handle any errors
        ex.printStackTrace()
    }
}
