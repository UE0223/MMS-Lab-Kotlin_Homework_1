package com.example.test_hw_1

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    // ✅ 定義手勢常數（避免使用 0/1/2）
    private val SCISSOR = 0
    private val STONE = 1
    private val PAPER = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val edName = findViewById<EditText>(R.id.ed_name)
        val tvText = findViewById<TextView>(R.id.tv_text)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val btnMora = findViewById<Button>(R.id.btn_mora)
        val tvName = findViewById<TextView>(R.id.tv_name)
        val tvWinner = findViewById<TextView>(R.id.tv_winner)
        val tvMyMora = findViewById<TextView>(R.id.tv_mmora)
        val tvTargetMora = findViewById<TextView>(R.id.tv_cmora)

        btnMora.setOnClickListener {

            if (edName.text.isEmpty()) {
                tvText.text = "請輸入玩家姓名"
                return@setOnClickListener
            }

            val playerName = edName.text.toString()
            val targetMora = (SCISSOR..PAPER).random()   // ✅ 取 0~2 更語意化

            val myMora = when (radioGroup.checkedRadioButtonId) {
                R.id.btn_scissor -> SCISSOR
                R.id.btn_stone -> STONE
                else -> PAPER
            }

            tvName.text = "名字\n$playerName"
            tvMyMora.text = "我方出拳\n${getMoraString(myMora)}"
            tvTargetMora.text = "電腦出拳\n${getMoraString(targetMora)}"

            // ✅ 語意化勝負判斷
            when {
                myMora == targetMora -> {
                    tvWinner.text = "勝利者\n平手"
                    tvText.text = "平局，請再試一次！"
                }
                (myMora == SCISSOR && targetMora == PAPER) ||
                        (myMora == STONE && targetMora == SCISSOR) ||
                        (myMora == PAPER && targetMora == STONE) -> {
                    tvWinner.text = "勝利者\n$playerName"
                    tvText.text = "恭喜你獲勝了！！！"
                }
                else -> {
                    tvWinner.text = "勝利者\n電腦"
                    tvText.text = "可惜，電腦獲勝了！"
                }
            }
        }
    }

    private fun getMoraString(mora: Int): String = when (mora) {
        SCISSOR -> "剪刀"
        STONE -> "石頭"
        else -> "布"
    }
}