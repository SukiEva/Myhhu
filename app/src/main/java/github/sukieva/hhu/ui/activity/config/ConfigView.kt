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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.ProvideWindowInsets
import github.sukieva.hhu.R
import github.sukieva.hhu.ui.activity.base.InitView
import github.sukieva.hhu.ui.components.ExpandableCard
import github.sukieva.hhu.ui.components.ListCard
import github.sukieva.hhu.ui.components.MaterialTopAppBar
import github.sukieva.hhu.ui.components.MyScaffold
import github.sukieva.hhu.ui.theme.fontHead
import github.sukieva.hhu.utils.LogUtil
import github.sukieva.hhu.utils.browse


@ExperimentalMaterialApi
@Composable
fun ConfigView() {
    val model: ConfigViewModel = viewModel()
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
                    label = "学号"
                )
                MyTextField(
                    state = passwordState,
                    label = "教务系统密码"
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
            val mclass = remember { model.mclass }
            val building = remember { model.building }
            val room = remember { model.room }
            val phone = remember { model.phone }
            Text(
                text = "请对照健康打卡填写\n还需填写上面的学号",
                color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 35.dp)
            )
            Column {
                MyTextField(
                    state = wid,
                    label = "wid（无需修改 自动填写）",
                    readOnly = true
                )
                MyTextField(
                    state = name,
                    label = "姓名"
                )
                MyTextField(
                    state = aid,
                    label = "身份证号"
                )
                MyTextField(
                    state = institute,
                    label = "学院"
                )
                MyTextField(
                    state = grade,
                    label = "年级"
                )
                MyTextField(
                    state = mclass,
                    label = "班级"
                )
                MyTextField(
                    state = building,
                    label = "宿舍楼"
                )
                MyTextField(
                    state = room,
                    label = "宿舍号"
                )
                MyTextField(
                    state = phone,
                    label = "电话号码"
                )
            }
        }
    )

}


@Composable
fun MyTextField(
    state: MutableState<String>,
    label: String,
    readOnly: Boolean = false
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
            onValueChange = { state.value = it },
            label = { Text(label) },
            textStyle = TextStyle(color = MaterialTheme.colors.fontHead),
            trailingIcon = @Composable {
                Image(imageVector = Icons.Filled.Clear, // 清除图标
                    contentDescription = null,
                    modifier = Modifier.clickable { state.value = "" }) // 给图标添加点击事件，点击就清空text
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


@ExperimentalMaterialApi
@Preview
@Composable
fun ConfigViewPreview() {
    ConfigView()
}