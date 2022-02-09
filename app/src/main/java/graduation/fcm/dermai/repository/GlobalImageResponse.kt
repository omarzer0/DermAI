package graduation.fcm.dermai.repository

import graduation.fcm.dermai.domain.model.home.ScanResponse

interface GlobalImageResponse {
    fun onReply(imageScanResponse: ScanResponse)
}