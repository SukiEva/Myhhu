package github.sukieva.hhu.ui.activity.config

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.rounded.ManageSearch
import androidx.compose.material.icons.rounded.PendingActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.ProvideWindowInsets
import github.sukieva.hhu.R
import github.sukieva.hhu.ui.activity.base.InitView
import github.sukieva.hhu.ui.components.*
import github.sukieva.hhu.ui.theme.fontHead
import github.sukieva.hhu.utils.LogUtil
import github.sukieva.hhu.utils.browse
import github.sukieva.hhu.utils.warningToast


@ExperimentalMaterialApi
@Composable
fun ConfigView() {
    val model: ConfigViewModel = viewModel()
    if (model.isFetchData) {
        InitView {
            MyScaffold(topBar = { ConfigWebAppBar() }) {
                MyWebView(model.configUrl)
            }
        }
        return
    }
    model.showConfig()
    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
        InitView {
            MyScaffold(topBar = { ConfigAppBar() }) {
                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    item { GradeItem() }
                    item { CheckInItem() }
                    item { ScheduleItem() }
                }
            }
        }
    }
}

@Composable
fun ScheduleItem() {
    ListCard(
        title = stringResource(id = R.string.home_card_schedule),
        icon = Icons.Rounded.PendingActions,
        modifier = Modifier
            .padding(start = 22.dp, top = 12.dp)
            .height(50.dp)
            .clickable {
                browse("https://www.coolapk.com/apk/com.suda.yzune.wakeupschedule")
            }
    )
}

@ExperimentalMaterialApi
@Composable
fun GradeItem() {
    ExpandableCard(
        title = stringResource(id = R.string.home_card_results),
        icon = Icons.Rounded.ManageSearch,
        content = {
            val model: ConfigViewModel = viewModel()
            val accountState = remember { model.account }
            val passwordState = remember { model.password }
            Column {
                MyTextField(
                    state = accountState,
                    label = stringResource(id = R.string.config_account)
                )
                MyTextField(
                    state = passwordState,
                    label = stringResource(id = R.string.config_password),
                    visualTransformation = PasswordVisualTransformation()
                )
            }
        }
    )

}

@ExperimentalMaterialApi
@Composable
fun CheckInItem() {
    ExpandableCard(
        title = stringResource(id = R.string.home_card_checkin),
        icon = Icons.Outlined.CheckCircleOutline,
        content = {
            val model: ConfigViewModel = viewModel()
            val wid = remember { model.wid }
            val name = remember { model.name }
            val aid = remember { model.aid }
            val institute = remember { model.institute }
            val grade = remember { model.grade }
            val major = remember { model.major }
            val mclass = remember { model.mclass }
            val building = remember { model.building }
            val room = remember { model.room }
            val phone = remember { model.phone }
            val place = remember { model.place }
            Row {
                Text(
                    text = "请对照健康打卡填写\n还需填写上面的学号",
                    color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 35.dp)
                )
                OutlinedButton(
                    onClick = { model.fetchData() },
                    modifier = Modifier.padding(start = 80.dp)
                ) {
                    Text(text = stringResource(id = R.string.config_autofill))
                }
            }

            Column {
                MyTextField(
                    state = place,
                    label = stringResource(id = R.string.config_place)
                )
                MyTextField(
                    state = wid,
                    label = "wid（无需修改 自动填写）",
                    readOnly = true
                )
                MyTextField(
                    state = name,
                    label = stringResource(id = R.string.config_name)
                )
                MyTextField(
                    state = aid,
                    label = stringResource(id = R.string.config_aid)
                )
                MyTextField(
                    state = institute,
                    label = stringResource(id = R.string.config_institute)
                )
                MyTextField(
                    state = grade,
                    label = stringResource(id = R.string.config_grade)
                )
                MyTextField(
                    state = major,
                    label = stringResource(id = R.string.config_major)
                )
                MyTextField(
                    state = mclass,
                    label = stringResource(id = R.string.config_class)
                )
                MyTextField(
                    state = building,
                    label = stringResource(id = R.string.config_building)
                )
                MyTextField(
                    state = room,
                    label = stringResource(id = R.string.config_room)
                )
                MyTextField(
                    state = phone,
                    label = stringResource(id = R.string.config_phone)
                )
            }
        }
    )

}


@Composable
fun MyTextField(
    state: MutableState<String>,
    label: String,
    readOnly: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Row(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = state.value,
            readOnly = readOnly,
            visualTransformation = visualTransformation,
            onValueChange = { state.value = it },
            label = { Text(label) },
            textStyle = TextStyle(color = MaterialTheme.colors.fontHead),
            trailingIcon = @Composable {
                Image(imageVector = Icons.Filled.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable { state.value = "" })
            },
            modifier = Modifier.width(300.dp)
        )
    }

}

@Composable
fun ConfigAppBar() {
    val model: ConfigViewModel = viewModel()
    MaterialTopAppBar(
        title = stringResource(id = R.string.home_list_config),
        actions = {
            IconButton(onClick = {
                model.saveConfig()
                LogUtil.d("ConfigAppBar", "Save config")
            }) {
                Icon(Icons.Outlined.Save, contentDescription = "Save config")
            }
        }
    )
}

@Composable
fun ConfigWebAppBar() {
    val model: ConfigViewModel = viewModel()
    MaterialTopAppBar(
        title = stringResource(id = R.string.home_list_config),
        actions = {
            IconButton(onClick = {
                model.saveFetchData()
                LogUtil.d("ConfigAppBar", "Fetch data")
            }) {
                Icon(Icons.Outlined.Save, contentDescription = "Save config")
            }
        }
    )
}


@ExperimentalMaterialApi
@Preview
@Composable
fun ConfigViewPreview() {
    ConfigView()
}