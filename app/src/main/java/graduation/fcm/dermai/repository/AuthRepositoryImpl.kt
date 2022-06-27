package graduation.fcm.dermai.repository

import graduation.fcm.dermai.data.remote.ApiService
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: ApiService
) {

    suspend fun register(name: String, email: String, password: String) =
        api.register(name, email, password)

    suspend fun login(email: String, password: String) = api.login(email, password)
}