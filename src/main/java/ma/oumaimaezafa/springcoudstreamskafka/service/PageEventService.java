package ma.oumaimaezafa.springcoudstreamskafka.service;


import ma.oumaimaezafa.springcoudstreamskafka.enteties.PageEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class PageEventService {
    //2eme case
    @Bean
    public Consumer<PageEvent> pageEventConsumer(){
        return (input)->{
            System.out.println("**************************");
            System.out.println(input.toString());
            System.out.println("**********");
        };
    }
//3eme case
    @Bean
    public Supplier<PageEvent> pageEventSupplier(){
        return ()-> new PageEvent(
                Math.random()>0.5 ?"P1":"P2",
                Math.random()>0.5 ?"U1":"U2",
                new Date(),
                new Random().nextInt(9000));

    }
    //4 eme case
    @Bean
    public Function<PageEvent,PageEvent> pageEventFunction(){
        return (input)->{
            //appliquer un traitement
           input.setName("L :"+input.getName().length());
           input.setUser("UUUUUUUU");
           return input;
        };
    }

    //5 eme case

}
