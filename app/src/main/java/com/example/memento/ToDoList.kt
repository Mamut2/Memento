package com.example.memento

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

val tasks = listOf(
    "Become king of the pirates",
    "Eat",
    "Sleep",
    "Romance Dawn",
    "Orange Town",
    "Syrup Village",
    "Baratie",
    "Arlong Park",
    "Loguetown",
    "Warship Island",
    "Reverse Mountain",
    "Whisky Peak",
    "Little Garden",
    "Drum Island",
    "Alabasta",
    "Post-Alabasta"
)

val doneTasks = listOf<String>()

@OptIn(ExperimentalFoundationApi::class, FlowPreview::class)
@Composable
fun CategorizedLazyColumn(
    categories: List<Category>,
    modifier: Modifier = Modifier
){
    val toDoList = remember { mutableStateListOf<String>() }
    val doneList = remember { mutableStateListOf<String>() }

    categories.forEach {
        it.items.forEach { text->
            if(it.name == "To-Do"){
                toDoList.add(text)
            }
            else doneList.add(text)
        }
    }

    LazyColumn(modifier, verticalArrangement = Arrangement.spacedBy(5.dp)){
        categories.forEach{ category ->
            when(category.name) {
                "To-Do" -> {
                    if (!toDoList.isEmpty()) {
                        stickyHeader {
                            CategoryHeader(category.name)
                        }
                    }

                    items(
                        items = toDoList,
                        key = { it }
                    ) { text ->
                        SwipeToDeleteContainer(
                            item = text,
                            onDelete = {
                                doneList.add(text)
                                toDoList.remove(text)
                            },
                            content = {
                                CategoryItem(text = text)
                            }
                        )
                    }
                }
                "Done" -> {
                    if (!doneList.isEmpty()) {
                        stickyHeader {
                            CategoryHeader(category.name)
                        }
                    }

                    items(
                        items = doneList,
                        key = { it.plus("Done") }
                    ) { text ->
                        SwipeToDeleteContainer(
                            item = text,
                            onDelete = {
                                doneList.remove(text)
                            },
                            content = {
                                CategoryItem(text = text)
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: () -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = {value->
            if(value == SwipeToDismissBoxValue.StartToEnd){
                coroutineScope.launch {
                    isRemoved = true
                    delay(500)
                    onDelete()
                }
                true
            }
            else false
        }
    )

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                DeleteBackground(swipeToDismissBoxState = state)
            },
            content = { content(item) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBackground(
    swipeToDismissBoxState: SwipeToDismissBoxState
)
{
    val color = if(swipeToDismissBoxState.dismissDirection == SwipeToDismissBoxValue.StartToEnd) {
        Color.Green
    } else Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.CenterStart
    ){
        Image(painter = painterResource(
            id = R.drawable.one_piece_monkey_d__luffy_cropped_cropped),
            contentScale = ContentScale.None,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            colorFilter = ColorFilter.tint(color, blendMode = BlendMode.DstIn)
        )

        Icon(
            Icons.Filled.Check,
            contentDescription = "Localized description",
            tint = color,
            modifier = Modifier
                .fillMaxHeight(),
        )
    }


}