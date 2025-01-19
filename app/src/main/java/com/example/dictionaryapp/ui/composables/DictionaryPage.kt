package com.example.dictionaryapp.ui.composables

import android.graphics.Paint.Align
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dictionaryapp.R
import com.example.dictionaryapp.ui.viewmodel.DictionaryResponse
import com.example.dictionaryapp.ui.viewmodel.DictionaryViewModel
import com.example.dictionaryapp.utility.playAudio


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun DictionaryPage(viewModel: DictionaryViewModel) {

    var wordToSearchFor by remember { mutableStateOf("") }

    val dictionaryResult = viewModel.dictionaryResult.observeAsState()

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = wordToSearchFor,
                onValueChange = {
                    wordToSearchFor = it
                },
                label = {
                    Text(text = stringResource(id = R.string.word_to_search))
                }
            )

            IconButton(
                onClick = {
                    viewModel.getMeaningOfWord(wordToSearchFor)
                    keyboardController?.hide()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search_icon)
                )
            }
        }

        when(val value = dictionaryResult.value) {
            is DictionaryResponse.Error -> {
                Text(text = value.errMsg, modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp))
            }
            DictionaryResponse.Loading -> {
                CircularProgressIndicator()
            }
            is DictionaryResponse.Success -> {
                val dicItem = value.data[0]
                val word = dicItem.word
                Text(
                    text = "Word: $word",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
                val definitions = dicItem.meanings[0].definitions
                definitions.forEachIndexed { index, def ->
                    Text(
                        text = "Definition ${index + 1}: ${def.definition}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
                
                Spacer(modifier = Modifier.height(30.dp))
                
                for(pho in dicItem.phonetics) {
                    if (pho.audio.length > 0 && pho.audio.endsWith(".mp3")) {
                        //Text(text = pho.audio, modifier = Modifier.fillMaxWidth().padding(4.dp))
                        Text(text = "Click the play icon below to head the word")
                        IconButton(
                            onClick = { playAudio(pho.audio) },
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .size(100.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.PlayArrow,
                                contentDescription = "Play Icon"
                            )
                        }
                        break
                    } else {
                        Text(text = "Audio not found")
                        break
                    }
                }
//                dicItem.phonetics.forEach { pho ->
//                    if (pho.audio.length > 0 && pho.audio.endsWith(".mp3")) {
//                        Text(text = pho.audio, modifier = Modifier.fillMaxWidth().padding(4.dp))
//                    }
//                }
            }
            null -> {}
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    DictionaryAppTheme {
//        DictionaryPage()
//    }
//}