package graduation.fcm.dermai.repository

import graduation.fcm.dermai.data.remote.ApiService
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: ApiService
) {
}