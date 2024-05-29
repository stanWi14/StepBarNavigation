package com.example.stepbaranimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.stepbaranimation.ui.theme.StepBarAnimationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StepBarAnimationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StepPages()
                }
            }
        }
    }
}

@Composable
fun StepPages() {
    var currentStep by remember { mutableStateOf(1) }
    val totalSteps = 6
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    // Set up the back press callback
    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (currentStep > 1) {
                    currentStep--
                } else {
                    isEnabled = false
                    backDispatcher?.onBackPressed()
                }
            }
        }
    }

    // Attach and detach the callback as necessary
    DisposableEffect(backDispatcher) {
        backDispatcher?.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StepProgressBar(currentStep, totalSteps)

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (currentStep) {
                1 -> {
                    Text(text = "First Page")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { currentStep++ }) {
                        Text(text = "Next")
                    }
                }

                2 -> {
                    Text(text = "Second Page")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { currentStep++ }) {
                        Text(text = "Next")
                    }
                }

                3 -> {
                    Text(text = "Third Page")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { currentStep++ }) {
                        Text(text = "Next")
                    }
                }

                4 -> {
                    Text(text = "Fourth Page")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { currentStep++ }) {
                        Text(text = "Next")
                    }
                }

                5 -> {
                    Text(text = "Fifth Page")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { currentStep++ }) {
                        Text(text = "Next")
                    }
                }

                6 -> {
                    Text(text = "Sixth Page")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { /* Finish action */ }) {
                        Text(text = "Finish")
                    }
                }
            }
        }
    }
}

@Composable
fun StepProgressBar(currentStep: Int, totalSteps: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..totalSteps) {
            if (i > 1) {
                val lineColor by animateColorAsState(
                    targetValue = if (i <= currentStep) Color.Blue else Color.Gray
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .background(lineColor)
                )
            }
            val circleColor by animateColorAsState(
                targetValue = if (i <= currentStep) Color.Blue else Color.Gray
            )
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(circleColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = i.toString(),
                    color = Color.White
                )
            }
        }
    }
}