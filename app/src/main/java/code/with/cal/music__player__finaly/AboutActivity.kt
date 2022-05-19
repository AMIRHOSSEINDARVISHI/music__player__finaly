package code.with.cal.music__player__finaly

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import code.with.cal.music__player__finaly.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(MainActivity.currentThemeNav[MainActivity.themeIndex])
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "About"
        binding.aboutText.text = aboutText()
    }
    private fun aboutText(): String{
        return "Developed By:Amirhossein_Darvishi" +
        "https://github.com/AMIRHOSSEINDARVISHI"
    }
}