package com.example.week05_1;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class WordPublisher {
    Word word = new Word();

    @RequestMapping(value = "/addBad/{word}", method = RequestMethod.GET)
    public ArrayList<String> addBadWord(@PathVariable("word") String word){
        this.word.badWords.add(word);
        System.out.println(this.word.badWords.toString());
        return this.word.badWords;
    };

    @RequestMapping(value = "/delBad/{word}", method = RequestMethod.GET)
    public ArrayList<String> delBadWord(@PathVariable("word") String word){
        this.word.badWords.remove(word);
        System.out.println(this.word.badWords.toString());
        return this.word.badWords;
    };

    @RequestMapping(value = "/addGood/{word}", method = RequestMethod.GET)
    public ArrayList<String> addGoodWord(@PathVariable("word") String word){
        this.word.goodWords.add(word);
        System.out.println(this.word.goodWords.toString());
        return this.word.goodWords;
    };

    @RequestMapping(value = "/delGood/{word}", method = RequestMethod.GET)
    public ArrayList<String> delGoodWord(@PathVariable("word") String word){
        this.word.goodWords.remove(word);
        System.out.println(this.word.goodWords.toString());
        return this.word.goodWords;
    };

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @RequestMapping(value = "/proof/{sentence}", method = RequestMethod.GET)
    public String proofSentence(@PathVariable("sentence") String sentence){
        boolean checkbadword = false;
        boolean checkgoodword = false;

        for (String word : this.word.badWords){
            if(sentence.contains((word))){
                checkbadword = true;
            }
        }
        for (String word : this.word.goodWords){
            if(sentence.contains((word))){
                checkgoodword = true;
            }
        }

        if(checkbadword && !checkgoodword){
            rabbitTemplate.convertAndSend("MyDirectExchange1", "bad", sentence);
            return "Found Bad Word";

        }else if (!checkbadword && checkgoodword){
            rabbitTemplate.convertAndSend("MyDirectExchange1", "good", sentence);
            return "Found Good Word";
        }else if (checkbadword && checkgoodword){
            rabbitTemplate.convertAndSend("MyDirectExchange2", "", sentence);
            return "Found Bad & Good Word";
        }
        return "";

    };
}
