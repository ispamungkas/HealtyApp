package com.example.healtyapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.service.autofill.OnClickAction
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.healtyapp.ui.theme.HealtyAppTheme
import java.util.Locale

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HealtyAppTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                Scaffold(
                    Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigation(
                            item = listOf(
                                BottomNavItem(
                                    name = "Home",
                                    route = "home",
                                    icon = Icons.Default.Home
                                ),
                                BottomNavItem(
                                    name = "Profile",
                                    route= "profile",
                                    icon = Icons.Default.Face
                                )
                            ),
                            navController = navController,
                            onClickAction = {
                                navController.navigate( it.route )
                            }
                        )
                    }
                ) {
                    Navigation(navController = navController)
                }
            }
        }
    }

    @Composable
    fun HomeScreen(
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )
            SearchBar()
            HomeSection(text = R.string.align_your_body) {
                AlignYourBodyRow()
            }
            HomeSection(text = R.string.favorite_collection) {
                FavoriteCardRow()
            }
            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun BottomNavigation(
        modifier: Modifier = Modifier,
        item: List<BottomNavItem>,
        navController: NavController,
        onClickAction: (BottomNavItem) -> Unit
    ) {
        val backstage = navController.currentBackStackEntryAsState()

        NavigationBar(
            modifier = Modifier,
            containerColor = Color.DarkGray,
            tonalElevation = 5.dp
        ) {
            item.forEach { item ->
                val selected = item.route == backstage.value?.destination?.route
                NavigationBarItem(
                    selected = selected,
                    onClick = { onClickAction(item) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Green,
                        unselectedIconColor = Color.Gray
                    ), icon = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (item.badgeCount > 0) {
                                BadgedBox(badge = {
                                    Text(text = item.badgeCount.toString())
                                }) {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.name
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name
                                )
                            }

                            if (selected) {
                                Text(
                                    text = item.name,
                                    textAlign = TextAlign.Center,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                )
            }
        }
    }

    @Composable
    private fun Navigation(
        modifier: Modifier = Modifier,
        navController: NavHostController
    ) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen()
            }
            composable("profile") {
                ProfileScreen()
            }
        }
    }

    @Composable
    fun ProfileScreen() {
        Column() {
            Text("profile screen")
        }
    }

    @Composable
    fun HomeSection(
        @StringRes text: Int,
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    ) {
        Column(modifier) {
            Text(
                stringResource(id = text).uppercase(Locale.getDefault()),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .paddingFromBaseline(top = 40.dp, bottom = 16.dp)
                    .padding(horizontal = 16.dp),
            )
            content()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun HomeSectionPreview() {
        HealtyAppTheme() {
            HomeSection(text = R.string.align_your_body) {
                AlignYourBodyRow()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SearchBar(
        modifier: Modifier = Modifier
    ) {
        TextField(
            value = "",
            onValueChange = {},
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "search_icon")
            },
            placeholder = {
                Text(text = stringResource(id = R.string.search))
            },
            colors = TextFieldDefaults.textFieldColors(
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledTextColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(56.dp)
        )
    }

    @Composable
    fun AlignYourBody(
        modifier: Modifier = Modifier,
        @StringRes text: Int,
        @DrawableRes drawable: Int
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(
                    id = drawable
                ),
                contentDescription = "gambar_peregangan",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape)
            )
            Text(
                text = stringResource(
                    id = text
                ),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 8.dp)
            )
        }
    }

    var data = listOf<Map<String, Int>>(
        mapOf(
            "drawable" to R.drawable.img,
            "text" to R.string.invension
        ),
        mapOf(
            "drawable" to R.drawable.img,
            "text" to R.string.invension
        ),
        mapOf(
            "drawable" to R.drawable.img,
            "text" to R.string.invension
        ),
        mapOf(
            "drawable" to R.drawable.img,
            "text" to R.string.invension
        ),
        mapOf(
            "drawable" to R.drawable.img,
            "text" to R.string.invension
        ),
    )

    var dataMeditasi = listOf<Map<String, Int>>(
        mapOf(
            "drawable" to R.drawable.naturemeditation,
            "text" to R.string.invension
        ),
        mapOf(
            "drawable" to R.drawable.naturemeditation,
            "text" to R.string.invension
        ),
        mapOf(
            "drawable" to R.drawable.naturemeditation,
            "text" to R.string.invension
        ),
        mapOf(
            "drawable" to R.drawable.naturemeditation,
            "text" to R.string.invension
        ),
        mapOf(
            "drawable" to R.drawable.naturemeditation,
            "text" to R.string.invension
        ),
    )

    @Composable
    fun AlignYourBodyRow(
        modifier: Modifier = Modifier
    ) {
        LazyRow(
            modifier = Modifier,
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(data) { item ->
                AlignYourBody(text = item.getValue("text"), drawable = item.getValue("drawable"))
            }
        }
    }

    @Composable
    fun FavoriteCard(
        modifier: Modifier = Modifier,
        @DrawableRes drawable: Int,
        @StringRes text: Int
    ) {
        Surface(
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
        ) {
            Row(
                modifier = Modifier
                    .width(192.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = drawable),
                    contentDescription = "gambar_sayur",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(56.dp)
                )
                Text(
                    text = stringResource(id = text),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }

    @Composable
    fun FavoriteCardRow(
        modifier: Modifier = Modifier
    ) {
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            modifier = Modifier
                .height(120.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = {
                items(dataMeditasi) { item ->
                    FavoriteCard(
                        drawable = item.getValue("drawable"),
                        text = item.getValue("text"),
                        modifier = Modifier
                            .height(56.dp)
                    )
                }
            },

            )
    }

    @Preview(showBackground = true)
    @Composable
    fun ItemPreview() {
        HealtyAppTheme {
            AlignYourBodyRow()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun CardPreview() {
        HealtyAppTheme {
            FavoriteCardRow()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        HealtyAppTheme {
            SearchBar()
        }
    }
}  