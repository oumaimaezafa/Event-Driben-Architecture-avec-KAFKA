package ma.oumaimaezafa.springcoudstreamskafka.service;


import ma.oumaimaezafa.springcoudstreamskafka.enteties.PageEvent;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Time;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.Duration;
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
    @Bean
    public Function<KStream<String,PageEvent>,KStream<String,Long>>kStreamFunction(){
        return (input)->{
          return input
                  .filter((k,v)->v.getDuration()>300)
                  .map((k,v)->new KeyValue<>(v.getName(),0L))
                  .groupBy((k,v)->k, Grouped.with(Serdes.String(),Serdes.Long()))
                   //sur l'ensemble des enrg qui ont ete observes sans les le derniers 5 s
                  .windowedBy(TimeWindows.of(Duration.ofMillis(5000)))
                  .count(Materialized.as("page-count"))
                  .toStream()
                  .map((k,v)->new KeyValue<>("=>"+k.window().startTime()+k.window().endTime()+k.key(),v));
            };
        }
    }

