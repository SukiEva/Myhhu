package github.sukieva.hhu.data.bean


data class Course(
    var courseName: String,
    var courseAttributeName: String, // 必修 or 选修
    var courseScore: Double, // 成绩
    var gradePointScore: Double, // 成绩绩点
    var credit: Double, // 学分
    var academicYearCode: String, //学年 2019-2020
) {
    constructor() : this(
        "课程名", "必修",
        100.0, 5.0, 1.0,
        "2019-2020"
    )
}
