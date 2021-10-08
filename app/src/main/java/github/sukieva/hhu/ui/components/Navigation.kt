package github.sukieva.hhu.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import github.sukieva.hhu.R
import github.sukieva.hhu.data.bean.Course
import github.sukieva.hhu.ui.activity.base.InitView
import github.sukieva.hhu.ui.activity.results.ResultsViewModel
import github.sukieva.hhu.ui.theme.Teal200
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun GradesScreen(navController: NavController, model: ResultsViewModel) {
    val load = remember { model.isLoaded }
    InitView {
        MyScaffold(
            topBar = {
                MaterialTopAppBar(title = stringResource(id = R.string.results_title))
            },
            bottomBar = {
                BottomNav(navController)
            }
        ) {
            if (load.value) GradesPager(model)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun RankScreen(navController: NavController, model: ResultsViewModel) {
    InitView {
        MyScaffold(
            topBar = {
                MaterialTopAppBar(title = stringResource(id = R.string.results_title))
            },
            bottomBar = {
                BottomNav(navController)
            }
        ) {
            val rank = model.rank
            val topRate = (rank.rank.toDouble() / rank.majorNum.toDouble()) * 100
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AnimatableSun(Modifier.size(200.dp))
                Spacer(modifier = Modifier.height(20.dp))
                RankListItem("成绩单", "平均", "推优", true)
                RankListItem("绩点", rank.averageGradePoint, rank.gradePoint)
                RankListItem("成绩", rank.averageGrade, rank.grade)
                RankListItem("排名", rank.averageRank, rank.rank)
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = "当前排名 ${rank.rank}/${rank.majorNum}, Top ${topRate.toInt()}%",
                    textAlign = TextAlign.Center,
                    color = Teal200
                )
            }
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun RankListItem(
    left: String = "绩点",
    middle: String = "1.0",
    right: String = "2.0",
    isTitle: Boolean = false
) {
    ListItem(
        text = {
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = left, modifier = Modifier.width(100.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = middle, modifier = Modifier.width(100.dp),
                    textAlign = TextAlign.Center,
                    color = if (isTitle) Color.Unspecified else Teal200
                )
                Text(
                    text = right, modifier = Modifier.width(100.dp),
                    textAlign = TextAlign.Center,
                    color = if (isTitle) Color.Unspecified else Teal200
                )
            }
        }
    )
}

@Composable
@ExperimentalMaterialApi
@ExperimentalPagerApi
fun GradesPager(model: ResultsViewModel) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val tabs = model.termCode
    val terms = model.termGrade
    ScrollableTabRow(
        // Our selected tab is our current page
        selectedTabIndex = pagerState.currentPage,
        // Override the indicator, using the provided pagerTabIndicatorOffset modifier
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        },
        backgroundColor = MaterialTheme.colors.background
    ) { // Add tabs for all of our pages
        tabs.forEachIndexed { index, title ->
            Tab(
                text = { Text(title) },
                selected = pagerState.currentPage == index,
                onClick = {
                    // Animate to the selected page when clicked
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
    HorizontalPager(
        count = tabs.size,
        state = pagerState,
    ) {
        val courses = terms[pagerState.currentPage]
        println("==>  ${pagerState.currentPage}  ${courses.size}")
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 30.dp, top = 10.dp,bottom = 80.dp)
        ) {
            courses.forEach {
                item { CourseItem(it) }
            }
        }
    }
}

@Composable
fun CourseItem(course: Course) {
    Column(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = course.courseName,
                fontSize = 18.sp,
                modifier = Modifier.width(300.dp)
            )
            Text(
                text = course.courseScore,
                textAlign = TextAlign.End,
                fontSize = 18.sp,
                color = Teal200
                // modifier = Modifier.width(IntrinsicSize.Max)
            )
        }
    }
}


