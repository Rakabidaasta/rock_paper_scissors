package com.example.rps

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.example.rps.databinding.FragmentFirstBinding
import android.content.SharedPreferences
import android.util.Log
import java.lang.NumberFormatException


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        val rock = view.findViewById(R.id.rock) as ImageButton;
        val scissors = view.findViewById(R.id.scissors) as ImageButton;
        val paper = view.findViewById(R.id.paper) as ImageButton;
        val btnRestart = view.findViewById(R.id.restart_game) as Button;

        var choice = "";
        val choiceImage = view.findViewById(R.id.choice) as ImageView;

        rock.setOnClickListener {
            choice = "rock";
            // Toast.makeText(this@MainActivity, "You clicked rock.", Toast.LENGTH_SHORT).show()
            choiceImage.setImageResource(R.drawable.rock);
            choiceComp(choice);
            updateScore();
        }

        scissors.setOnClickListener {
            choice = "scissors";
            choiceImage.setImageResource(R.drawable.scissors);
            choiceComp(choice);
            updateScore();
        }

        paper.setOnClickListener {
            choice = "paper";
            choiceImage.setImageResource(R.drawable.paper);
            choiceComp(choice);
            updateScore();
        }

        btnRestart.setOnClickListener {
            // Toast.makeText(this@MainActivity, "You clicked rock.", Toast.LENGTH_SHORT).show()
            restartGame();
        }
    }

    fun choiceComp (choice: String)
    {
        val compChoice = (0..2).random();
        val choiceCompImage = requireView().findViewById(R.id.choiceComp) as ImageView;
        val result = requireView().findViewById(R.id.result) as TextView;

        when (compChoice) {
            0 -> {
                choiceCompImage.setImageResource(R.drawable.rock);
                when (choice) {
                    "rock" -> result.text = "Результат: ничья";
                    "scissors" -> result.text = "Результат: проигрыш";
                    "paper" -> result.text = "Результат: победа";
                }
            };
            1 -> {
                choiceCompImage.setImageResource(R.drawable.scissors);
                when (choice) {
                    "rock" -> result.text = "Результат: победа";
                    "scissors" -> result.text = "Результат: ничья";
                    "paper" -> result.text = "Результат: проигрыш";
                }
            };
            2 -> {
                choiceCompImage.setImageResource(R.drawable.paper);
                when (choice) {
                    "rock" -> result.text = "Результат: проигрыш";
                    "scissors" -> result.text = "Результат: победа";
                    "paper" -> result.text = "Результат: ничья";
                }
            };
        }
    }

    fun updateScore()
    {
        val result = requireView().findViewById(R.id.result) as TextView;
//        val btnSaveRes = requireView().findViewById(R.id.button_first) as Button;
        var resultText = result.text.split(": ")[1];

        val score = requireView().findViewById(R.id.score) as TextView;
        val scores = score.text.split(":");

        var userScore = scores[0].substring(5).toInt();
        var compScore = scores[1].toInt();

        val rock = requireView().findViewById(R.id.rock) as ImageButton;
        val scissors = requireView().findViewById(R.id.scissors) as ImageButton;
        val paper = requireView().findViewById(R.id.paper) as ImageButton;
        var btnRestart = requireView().findViewById(R.id.restart_game) as Button;

        if (resultText == "победа") {
            userScore += 1;
        } else if (resultText == "проигрыш") {
            compScore += 1;
        }

        var prefs = PreferenceManager.getDefaultSharedPreferences(context)
        var maxCountRounds = prefs.getString("count_rounds", "5")
        var countRounds = 0

        try {
            countRounds = maxCountRounds!!.toInt()
        } catch (e: NumberFormatException) {
            countRounds = 5;
        }

        if (userScore + compScore >= countRounds) {
            rock.isClickable = false;
            scissors.isClickable = false;
            paper.isClickable = false;
            result.text = "Игра закончилась со счётом " + userScore + " : " + compScore;

            btnRestart.visibility = View.VISIBLE;
            score.visibility = View.INVISIBLE;
        }

        score.text = "Счёт " + userScore.toString() + ":" + compScore.toString();
    }

    fun restartGame()
    {
        val result = requireView().findViewById(R.id.result) as TextView;

        val score = requireView().findViewById(R.id.score) as TextView;
        val rock = requireView().findViewById(R.id.rock) as ImageButton;
        val scissors = requireView().findViewById(R.id.scissors) as ImageButton;
        val paper = requireView().findViewById(R.id.paper) as ImageButton;
        var btnRestart = requireView().findViewById(R.id.restart_game) as Button;

        rock.isClickable = true;
        scissors.isClickable = true;
        paper.isClickable = true;
        result.text = "Результат: ничья";

        btnRestart.visibility = View.INVISIBLE;
        score.visibility = View.VISIBLE;

        score.text = "Счёт 0:0";
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}