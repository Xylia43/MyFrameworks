package com.example.myframeworks;

import io.reactivex.rxjava3.core.*;

public class HelloWorld {
    public static void main(String[] args) {
//        Flowable.just("Hello world").subscribe(System.out::println);
        firstExample();
    }
    public static void firstExample(){
        System.out.println("hello");
        Observable<String> observable = Observable.create(emitter -> {
            emitter.onNext("click 01");
            emitter.onNext("click 02");
            emitter.onNext("click 03");
//            emitter.onError(new Throwable("it's an error"));
            emitter.onComplete();
                });
        observable.subscribe(emmit->{
            System.out.println(emmit);
        },throwable -> {
            System.out.println(throwable);
        },()->{
            System.out.println("completeee");
        });
    }
}