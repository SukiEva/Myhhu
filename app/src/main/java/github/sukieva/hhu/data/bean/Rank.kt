package github.sukieva.hhu.data.bean

data class Rank(
    var name: String,
    var major: String,
    var majorNum: String,
    var averageGradePoint: String,
    var averageGrade: String,
    var averageRank: String,
    var gradePoint: String,
    var grade: String,
    var rank: String
) {
    constructor() : this(
        "Error", "Error", "999", "5.0", "100.0", "1", "5.0", "100.0", "1"
    )
}

