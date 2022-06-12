package com.example.baasiccomposecodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.baasiccomposecodelab.ui.theme.BaasicComposeCodelabTheme

class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaasicComposeCodelabTheme {
                MyApp((1..1000).toList())
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun MyApp(names: List<Int> = (1..3).toList()) {
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colors.background) {
        LazyColumn {
            items(items = names) { name ->
                Greeting(name)
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun Greeting(name: Int) {
    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    val expandButtonText = if (isExpanded) "Show else" else "Show more"
    Surface(
        color = MaterialTheme.colors.primary, modifier = Modifier
            .padding(24.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .weight(1f)
                ) {
                    Text(text = "Hello,")
                    Text(text = "$name!")
                }
                OutlinedButton(
                    onClick = { isExpanded = !isExpanded }, modifier = Modifier
                        .padding(24.dp)
                ) {
                    Text(text = expandButtonText)
                }
            }
            AnimatedVisibility(visible = isExpanded) {
                Box(modifier = Modifier.size(50.dp))
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BaasicComposeCodelabTheme {
        MyApp(listOf(1, 2))
    }
}