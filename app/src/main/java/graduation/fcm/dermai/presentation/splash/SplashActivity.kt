package graduation.fcm.dermai.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import graduation.fcm.dermai.common.SharedPreferenceManger
import graduation.fcm.dermai.presentation.auth.AuthActivity
import graduation.fcm.dermai.presentation.main.MainActivity
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferenceManger: SharedPreferenceManger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (sharedPreferenceManger.token != "" && sharedPreferenceManger.hasLoggedIn)
            startActivity(Intent(this, MainActivity::class.java))
        else
            startActivity(Intent(this, AuthActivity::class.java))

    }
}