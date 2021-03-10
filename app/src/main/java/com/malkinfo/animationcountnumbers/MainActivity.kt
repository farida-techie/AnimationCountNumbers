package com.malkinfo.animationcountnumbers


import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.malkinfo.animationcountnumbers.ui.theme.AnimationCountNumbersTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationCountNumbersTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MAnimation()
                }
            }
        }
    }

}

enum class BoxState{SMALL, LARGE}

@Composable
fun MAnimation(){
    val colorss:Color
    val resour:Painter
    val boxStates by remember{ mutableStateOf(BoxState.SMALL)}
    var transition = updateTransition(targetState = boxStates)
    val infiniteTransition = rememberInfiniteTransition()
    val poisition  = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
                easing = FastOutSlowInEasing
            ),
            RepeatMode.Restart
        )
    )
    if (poisition.value <= 0.5f){
        colorss = Color.Magenta

    }
    else{
        colorss = Color.Red

    }
    var pul = transition.animateFloat {state->
        when(state){
            BoxState.LARGE->poisition.value * 180f
            BoxState.SMALL->poisition.value *60f
        }
    }

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
            ){
        /**add Image*/
        Image(
            painter = painterResource(id = R.drawable.ic_favorite),
            contentDescription ="favorite",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(pul.value.dp)
                .width(pul.value.dp),
            colorFilter = ColorFilter.lighting(colorss,add = colorss)
        )

    }

}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AnimationCountNumbersTheme {
      MAnimation()
    }
}