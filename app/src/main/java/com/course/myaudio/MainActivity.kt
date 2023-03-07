package com.course.myaudio

import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.course.myaudio.ui.theme.MyAudioTheme
import java.io.File

class MainActivity : ComponentActivity() {
    private lateinit var mediaPlayer: MediaPlayer

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            permissions(),
            REQUEST_CODE,
        )
        setContent {
            MyAudioTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        val webUrl = remember {
                            mutableStateOf("http://www.ee.cityu.edu.hk/~lmpo/ee5415/audioWeb.mp3")
                        }

                        Button(onClick = {
                            val uri = Uri.parse(
                                "android.resource://" +
                                    packageName + "/" + R.raw.audio,
                            )
                            mediaPlayer = MediaPlayer.create(applicationContext, uri)
                            mediaPlayer.start()
                        }) {
                            Text(text = "Play Resource audio")
                        }
                        Button(onClick = {
                            val file = File(
                                Environment.getExternalStorageDirectory(),
                                "audioSD.mp3",
                            )
                            val uri = Uri.fromFile(file)
                            mediaPlayer = MediaPlayer.create(applicationContext, uri)
                            mediaPlayer.start()
                        }) {
                            Text(text = "Play SD CARD audio")
                        }
                        Button(onClick = {
                            val uri = Uri.parse(webUrl.value)
                            mediaPlayer = MediaPlayer.create(applicationContext, uri)
                            mediaPlayer.start()
                        }) {
                            Text(text = "Play web audio")
                        }
                        TextField(
                            value = webUrl.value,
                            onValueChange = {
                                webUrl.value = it
                            },
                        )
                        Button(onClick = { mediaPlayer.stop() }) {
                            Text(text = "Stop")
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val REQUEST_CODE = 1
        var storge_permissions = arrayOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
        )

        @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
        var storge_permissions_33 = arrayOf(
            android.Manifest.permission.READ_MEDIA_AUDIO,
            android.Manifest.permission.READ_MEDIA_VIDEO,
        )
        fun permissions(): Array<String> {
            val p: Array<String> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                storge_permissions_33
            } else {
                storge_permissions
            }
            return p
        }
    }
}
