package com.example.week05_1;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class SentenceConsumer {
    protected Sentence sentence = new Sentence();

    @RabbitListener(queues = "BadWordQueue")
    public void addBadSentence(String s){
        this.sentence.badSentences.add(s);
        System.out.println("In addBadSentence Method : " + this.sentence.badSentences);
    }

    @RabbitListener(queues = "GoodWordQueue")
    public void addGoodSentence(String s){
        this.sentence.goodSentences.add(s);
        System.out.println("In addGoodSentence Method : " + this.sentence.goodSentences);
    }


}
