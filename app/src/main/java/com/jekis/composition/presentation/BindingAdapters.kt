package com.jekis.composition.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.jekis.composition.R
import com.jekis.composition.domain.entity.GameResult

@BindingAdapter("requiredAnswers")
fun bindRequireAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.necessary_answers),
        count
    )
}

@BindingAdapter("scoreAnswers")
fun bindScoreAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.your_count), count
    )
}

@BindingAdapter("requirePercentage")
fun bindRequirePercentage(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.necessary_percentage), count
    )
}

@BindingAdapter("scorePercentage")
fun bindScorePercentage(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        textView.context.getString(
            R.string.percent_true_answers
        ), getPercentOfRightAnswers(gameResult).toString()
    )
}

private fun getPercentOfRightAnswers(gameResult: GameResult): Int {
    return if (gameResult.countOfQuestions == 0) {
        0
    } else {
        ((gameResult.countOfRightAnswers / gameResult.countOfQuestions.toDouble()) * 100).toInt()
    }
}

@BindingAdapter("setImage")
fun bindImage(imageView: ImageView, winner: Boolean) {
    imageView.setImageResource(getSmileResId(winner))
}

private fun getSmileResId(winner: Boolean): Int {
    return if (winner) {
        R.drawable.ic_smile
    } else {
        R.drawable.ic_sad_lamp
    }

}

