package repositories

import db.executeQuery
import db.insertQuery
import model.User

object UsersRepository {
    fun getAllUsers(): List<User> {
        val result = mutableListOf<User>()

        executeQuery("SELECT * FROM user") {
            result.add(
                User(
                    id = it.getInt("id"),
                    login = it.getString("login"),
                    name = it.getString("name"),
                )
            )
        }

        return result
    }

    fun findUser(login: String): User? {
        var user: User? = null

        executeQuery("SELECT * FROM user WHERE login = ?", listOf(login)) {
            user = User(
                id = it.getInt("id"),
                login = login,
                name = it.getString("name"),
            )
        }

        return user
    }

    fun createUser(login: String, name: String): User {
        val id = insertQuery("INSERT INTO user (login, name) VALUES (?, ?)", listOf(login, name))

        return User(id = id, login = login, name = name)
    }
}
