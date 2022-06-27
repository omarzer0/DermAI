package graduation.fcm.dermai.domain.model.home


data class Question(
    val key: String,
    val questionText: String,
    val answer: List<String>,
    val questionType: QuestionType
)

data class QuestionToSend(
    val key: String,
    val answer: String,
)

enum class QuestionType {
    POLAR,
    MULTI_CHOICE
}



// [
//  {
//    "key":"q1",
//    "answer":"yes"
//  },
//  {
//    "key":"q1",
//    "answer":"yes"
//  }
// ]
