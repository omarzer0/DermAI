package graduation.fcm.dermai.domain.model.auth

import graduation.fcm.dermai.core.BaseResponse

data class AuthResponse(
    val data: AuthData
) : BaseResponse()