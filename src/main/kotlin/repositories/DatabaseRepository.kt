package repositories

import db.executeQuery
import db.insertQuery
import model.DirectMessage
import model.GlobalMessage
import model.User
import java.sql.Timestamp
import java.time.Instant

object DatabaseRepository {
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

    fun getGlobalMessages(): List<GlobalMessage> {
        val result = mutableListOf<GlobalMessage>()

        executeQuery("SELECT * FROM global_message ORDER BY created_at") {
            result.add(
                GlobalMessage(
                    id = it.getInt("id"),
                    userId = it.getInt("user_id"),
                    text = it.getString("text"),
                    createdAt = it.getTimestamp("created_at").toString(),
                )
            )
        }

        return result
    }

    fun createGlobalMessage(userId: Int, text: String): GlobalMessage {
        val timestamp = Timestamp.from(Instant.now())
        val id =
            insertQuery("INSERT INTO global_message (user_id, text, created_at) VALUES($userId, ?, ?)", listOf(text, timestamp))

        return GlobalMessage(id = id, userId = userId, text = text, createdAt = timestamp.toString())
    }

    fun getAllDirectMessages(userId: Int, userId2: Int): List<DirectMessage> {
        val result = mutableListOf<DirectMessage>()
        executeQuery("SELECT * FROM direct_message WHERE (sender_id = $userId AND recipient_id = $userId2) OR (sender_id = $userId2 AND recipient_id = $userId) ORDER BY created_at") {
            result.add(
                DirectMessage(
                    id = it.getInt("id"),
                    senderId = it.getInt("sender_id"),
                    recipientId = it.getInt("recipient_id"),
                    text = it.getString("text"),
                    createdAt = it.getTimestamp("created_at").toString(),
                )
            )
        }

        return result
    }

    fun createDirectMessage(senderId: Int, recipientId: Int, text: String): DirectMessage {
        val timestamp = Timestamp.from(Instant.now())
        val id = insertQuery(
            "INSERT INTO direct_message (sender_id, recipient_id, text, created_at) VALUES($senderId, $recipientId, ?, ?)",
            listOf(text, timestamp)
        )

        return DirectMessage(
            id = id,
            senderId = senderId,
            recipientId = recipientId,
            text = text,
            createdAt = timestamp.toString()
        )
    }
}
