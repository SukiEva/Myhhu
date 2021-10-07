package github.sukieva.hhu.data.bean

data class Rank(
    var name: String,
    var major: String,
    var majorNum: Int,
    var averageGradePoint: Double,
    var averageGrade: Double,
    var averageRank: Int,
    var gradePoint: Double,
    var grade: Double,
    var Rank: Int
) {
    constructor() : this(
        "Error", "Error", 999, 5.0, 100.0, 1, 5.0, 100.0, 1
    )
}

