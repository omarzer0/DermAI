package graduation.fcm.dermai.common

//    fun globalUploadImage(img: MultipartBody.Part) {
//        applicationScope.launch(exceptionHandler) {
//            Log.e("globalUploadImage", "globalUploadImage" )
//            val response = api.uploadDiseaseImage(img, pref.token)
//            if (response.isSuccessful) {
//                val body = response.body() ?: return@launch
//                Log.e("globalUploadImage", "globalUploadImage: $response" )
//                globalImageResponse?.apply { onReply(body) }
//            } else {
//                val errorResponse = ScanResponse(ScanData(emptyList())).apply {
//                    error = response.message()
//                }
//                globalImageResponse?.apply { onReply(errorResponse) }
//                toastMy(app,response.message())
//            }
//        }
//
//    }
//
//    private var globalImageResponse: GlobalImageResponse? = null
//
//    fun registerGlobalImageResponse(imageResponse: GlobalImageResponse) {
//        globalImageResponse = imageResponse
//    }
//
//    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
//        applicationScope.launch(Dispatchers.Main) {
//            val throwableMessage = throwable.localizedMessage
//            Log.e("HomeRepositoryImpl", "error $throwableMessage")
//            val errorMessage =
//                if (throwableMessage != null && throwableMessage.contains("No address associated with hostname"))
//                    "Check your internet connection!"
//                else UNKNOWN_ERROR
//
//            val errorResponse = ScanResponse(ScanData(emptyList())).apply {
//                error = errorMessage
//            }
//            globalImageResponse?.apply { onReply(errorResponse) }
//            toastMy(app,errorMessage)
//        }
//    }


//init {
//        repo.registerGlobalImageResponse(this)
//    }
//
//    override fun onReply(imageScanResponse: ScanResponse) {
//        Log.e("onReply", "onReply: $imageScanResponse")
//        _scanResult.value = Event(imageScanResponse)
//    }

//    fun globalUploadImage(img: MultipartBody.Part) {
//        repo.globalUploadImage(img)
//    }