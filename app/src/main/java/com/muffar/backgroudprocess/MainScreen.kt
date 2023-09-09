package com.muffar.backgroudprocess

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.muffar.backgroudprocess.ui.component.RuntimePermissionsDialog

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
) {
    var status by remember {
        mutableStateOf("")
    }

    viewModel.workStatus.observe(LocalLifecycleOwner.current) {
        status = it
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        RuntimePermissionsDialog(
            Manifest.permission.POST_NOTIFICATIONS,
            onPermissionDenied = {},
            onPermissionGranted = {},
        )
    }

    Box(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Button(
                onClick = { viewModel.runNotification() }
            ) {
                Text(stringResource(R.string.activate_notification))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                viewModel.cancelNotification()
            }) {
                Text(stringResource(R.string.cancel_notification))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Status notifikasi : $status",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}