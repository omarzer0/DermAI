package graduation.fcm.dermai.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import graduation.fcm.dermai.presentation.main.MainActivity

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //        if (sharedPreferences.hasLoggedIn)
//            startActivity(Intent(this, MainActivity::class.java))
//        else
//            startActivity(Intent(this, AuthActivity::class.java))

        startActivity(Intent(this, MainActivity::class.java))
    }
}