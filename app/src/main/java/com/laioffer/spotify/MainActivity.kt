package com.laioffer.spotify

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import coil.compose.AsyncImage
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.laioffer.spotify.database.DatabaseDao
import com.laioffer.spotify.datamodel.Album
import com.laioffer.spotify.network.NetworkApi
import com.laioffer.spotify.network.NetworkModule
import com.laioffer.spotify.player.PlayerBar
import com.laioffer.spotify.player.PlayerViewModel
import com.laioffer.spotify.ui.theme.SpotifyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

// customized extend AppCompatActivity
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    // private val TAG = "lifecycle"
    @Inject
    lateinit var api: NetworkApi
    @Inject
    lateinit var databaseDao: DatabaseDao

    private val playerViewModel: PlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Log.d(TAG, "We are at onCreate()")
//        setContent {
//            SpotifyTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    AlbumCover()
//                }
//            }
//        }
        setContentView(R.layout.activity_main)

        // get bottom bar
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)

        // get navhost
        val navHostFragment =supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        // get controller
        val navController = navHostFragment.navController

        // set graph to nav controller
        navController.setGraph(R.navigation.nav_graph)

        NavigationUI.setupWithNavController(navView, navController)

        // bind bottom bar with nav controller
        // https://stackoverflow.com/questions/70703505/navigationui-not-working-correctly-with-bottom-navigation-view-implementation
        navView.setOnItemSelectedListener{
            NavigationUI.onNavDestinationSelected(it, navController)
            navController.popBackStack(it.itemId, inclusive = false)
            true
        }

        val playerBar = findViewById<ComposeView>(R.id.player_bar)
        playerBar.apply {
            setContent {
                MaterialTheme(colors = darkColors()) {
                    PlayerBar(
                        playerViewModel
                    )
                }
            }
        }


        GlobalScope.launch(Dispatchers.IO) {
//            val retrofit = NetworkModule.provideRetrofit()
//            val api = retrofit.create(NetworkApi::class.java)

            val response = api.getHomeFeed().execute().body()
            Log.d("Network", response.toString())
        }

//        GlobalScope.launch {
//            withContext(Dispatchers.IO) {
//                val album = Album(
//                    id = 1,
//                    name =  "Hexagonal",
//                    year = "2008",
//                    cover = "https://upload.wikimedia.org/wikipedia/en/6/6d/Leessang-Hexagonal_%28cover%29.jpg",
//                    artists = "Lesssang",
//                    description = "Leessang (Korean: 리쌍) was a South Korean hip hop duo, composed of Kang Hee-gun (Gary or Garie) and Gil Seong-joon (Gil)"
//                )
//                databaseDao.favoriteAlbum(album)
//            }
//        }

        // Player test
//        val player = ExoPlayer.Builder(this).build()
//                                  // static func
//        val mediaItem = MediaItem.fromUri("http://10.0.2.2:8080/songs/LeeSSang_Hexagonal.mp3")
//        // Set the media item to be played.
//        player.setMediaItem(mediaItem)
//        player.prepare()
//        player.play()

    }
}


//@Composable
//fun AlbumCover() {
//    Column {
//        Box(modifier = Modifier.size(160.dp)) {
//            AsyncImage(
//                model = "https://upload.wikimedia.org/wikipedia/en/d/d1/Stillfantasy.jpg",
//                contentDescription = null,
//                modifier = Modifier.fillMaxSize(),
//                contentScale = ContentScale.FillBounds
//            )
//            Text(
//                text = "Still Fantasy",
//                color = Color.White,
//                modifier = Modifier
//                    .padding(8.dp)
//                    .align(Alignment.BottomStart)
//            )
//        }
//        Text(
//            text = "Jay Chou",
//            color = Color.White,
//            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
//            modifier = Modifier.padding(top = 4.dp, start = 8.dp)
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun AlbumCoverPreview() {
//    SpotifyTheme {
//        Surface {
//            AlbumCover()
//        }
//    }
//}
//
//@Composable
//fun HelloContent() {
//
//    // state
//    var name by remember { mutableStateOf("") }
//
//    // (state, setState) = ""
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        Text(
//            text = "Hello!",
//            modifier = Modifier.padding(bottom = 8.dp),
//            style = MaterialTheme.typography.body2  // font
//        )
//        OutlinedTextField(
//            value = name,
//            onValueChange = {
//                name = it
//            },
//            label = { Text("Name") }
//        )
//    }
//}
//
//@Composable
//fun StatelessHelloContent(name: String, onNameChange: (String) -> Unit) {
//    Column(modifier = Modifier.padding(16.dp)) {
//        if (name.isNotEmpty()) {
//            Text(
//                text = "Hello, $name!",
//                modifier = Modifier.padding(bottom = 8.dp),
//                style = MaterialTheme.typography.body2
//            )
//        }
//        OutlinedTextField(
//            value = name,
//            onValueChange = onNameChange,
//            label = { Text("Name") }
//        )
//    }
//}
//
//@Preview
//@Composable
//fun HelloContentPreview() {
//    SpotifyTheme {
//        Surface {
//            HelloContent()
//        }
//    }
//}

// inside class MainActivity : AppCompatActivity()
//    override fun onStart() {
//        super.onStart()
//        Log.d(TAG, "We are at onStart()")
//    }
//
//    override fun onResume() {
//        super.onResume()
//        Log.d(TAG, "We are at onResume()")
//    }
//
//    override fun onPause() {
//        super.onPause()
//        Log.d(TAG, "We are at onPause()")
//    }
//
//    override fun onStop() {
//        super.onStop()
//        Log.d(TAG, "We are at onStop()")
//    }
//
//    override fun onDestroy() {
//        Log.d(TAG, "We are at onDestroy()")
//        super.onDestroy()
//    }

//@Composable
//fun ArtistCardBox() {
//    Box {
//        Text("Alfred Sisley")
//        Text("3 minutes ago")
//    }
//}
//
//@Composable
//fun ArtistCardColumn() {
//    Column {
//        Text("Alfred Sisley")
//        Text("3 minutes ago")
//    }
//}
//
//@Composable
//fun ArtistCardRow() {
//    Row {
//        Text("Alfred Sisley")
//        Text("3 minutes ago")
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ArtistCardBoxPreview() {
//    SpotifyTheme {
//        Surface {
//            ArtistCardBox()
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ArtistCardColumnPreview() {
//    SpotifyTheme {
//        Surface {
//            ArtistCardColumn()
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ArtistCardRowPreview() {
//    SpotifyTheme {
//        Surface {
//            ArtistCardRow()
//        }
//    }
//}

//@Composable
//fun Greeting(name: String) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//            .padding(24.dp)
//            .background(Color.Blue)
//    ) {
//        Text(text = "Hello,")
//        Text(text = name)
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    SpotifyTheme {
//        Surface {
//            Greeting("Android")
//        }
//    }
//}