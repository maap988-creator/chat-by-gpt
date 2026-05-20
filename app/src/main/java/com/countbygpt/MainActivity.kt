package com.countbygpt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.*
import androidx.navigation.NavType
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val folders = remember {

                mutableStateListOf(

                    Folder(
                        id = 1,
                        name = "Materiais",
                        counters = mutableListOf(

                            Counter(
                                id = 1,
                                name = "Parafusos",
                                value = 15,
                                description = "Caixa azul"
                            ),

                            Counter(
                                id = 2,
                                name = "Cabos",
                                value = 8,
                                description = "Estoque principal"
                            )

                        )
                    ),

                    Folder(
                        id = 2,
                        name = "Ferramentas",
                        counters = mutableListOf(

                            Counter(
                                id = 3,
                                name = "Martelos",
                                value = 4,
                                description = "Oficina"
                            )

                        )
                    )

                )
            }

            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "folders"
            ) {

                composable("folders") {

                    FolderListScreen(
                        folders = folders,
                        onFolderClick = { folderId ->

                            navController.navigate(
                                "counters/$folderId"
                            )

                        }
                    )

                }

                composable(
                    route = "counters/{folderId}",

                    arguments = listOf(
                        navArgument("folderId") {
                            type = NavType.IntType
                        }
                    )
                ) { backStackEntry ->

                    val folderId =
                        backStackEntry.arguments?.getInt("folderId")

                    val folder =
                        folders.find { it.id == folderId }

                    folder?.let {

                        CounterListScreen(it)

                    }

                }

            }

        }

    }

}

@Composable
fun FolderListScreen(
    folders: List<Folder>,
    onFolderClick: (Int) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF101010),
                        Color(0xFF1A1A1A)
                    )
                )
            )
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(folders) { folder ->

                FolderCard(
                    folder = folder,
                    onClick = {
                        onFolderClick(folder.id)
                    }
                )

            }

        }

    }

}

@Composable
fun FolderCard(
    folder: Folder,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },

        shape = RoundedCornerShape(28.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(0x33FFFFFF)
        )
    ) {

        Column(
            modifier = Modifier.padding(24.dp)
        ) {

            Text(
                text = folder.name,
                fontSize = 26.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${folder.counters.size} contadores",
                fontSize = 14.sp
            )

        }

    }

}

@Composable
fun CounterListScreen(folder: Folder) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF101010),
                        Color(0xFF1A1A1A)
                    )
                )
            )
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(folder.counters) { counter ->

                CounterCard(counter)

            }

        }

    }

}

@Composable
fun CounterCard(counter: Counter) {

    var value by remember {
        mutableStateOf(counter.value)
    }

    Card(
        shape = RoundedCornerShape(28.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(0x33FFFFFF)
        )
    ) {

        Column(
            modifier = Modifier.padding(24.dp)
        ) {

            Text(
                text = counter.name,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = counter.description,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "$value",
                fontSize = 42.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row {

                Button(
                    onClick = {
                        value--
                    }
                ) {
                    Text("-1")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        value++
                    }
                ) {
                    Text("+1")
                }

            }

        }

    }

}