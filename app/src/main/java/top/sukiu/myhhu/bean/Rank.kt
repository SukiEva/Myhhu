package top.sukiu.myhhu.bean

import java.io.Serializable

data class Rank(
    var Name:String,
    var Major:String,
    var MajorNum:Int,
    var AvarageGradePoint:Double,
    var AvarageGrade:Double,
    var AvarageRank:Int,
    var GradePoint:Double,
    var Grade:Double,
    var Rank:Int
): Serializable