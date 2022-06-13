package db

import java.sql.*

fun executeQuery(query: String, rowHandle: (ResultSet) -> Unit) {
    val stmt = conn!!.createStatement()
    val resultSet = stmt.executeQuery(query)

    parseResult(resultSet, rowHandle)
}

fun executeQuery(query: String, params: List<String>, rowHandle: (ResultSet) -> Unit) {
    val statement = conn!!.prepareStatement(query)

    params.forEachIndexed { i, value ->
        statement.setString(i + 1, value)
    }

    parseResult(statement.executeQuery(), rowHandle)
}

fun insertQuery(query: String, params: List<Any>, rowHandle: ((ResultSet) -> Unit)? = null): Int {
    val statement = conn!!.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)

    params.forEachIndexed { i, value ->
        when (value) {
            is String -> statement.setString(i + 1, value)
            is Timestamp -> statement.setTimestamp(i + 1, value)
        }
    }

    statement.executeUpdate();

    var id = 0

    parseResult(statement.generatedKeys) {
        if (rowHandle != null) {
            rowHandle(it)
        } else {
            id = it.getInt(1)
        }
    }

    return id
}

private fun parseResult(resultSet: ResultSet?, rowHandle: (ResultSet) -> Unit) {
    try {
        while (resultSet?.next() == true) {
            try {
                rowHandle(resultSet)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    } catch (ex: SQLException) {
        // handle any errors
        ex.printStackTrace()
    } finally {
        // release resources
        if (resultSet != null) {
            try {
                resultSet.close()
            } catch (_: SQLException) {
            }
        }
    }
}
