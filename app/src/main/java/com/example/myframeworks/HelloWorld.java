package com.example.myframeworks;

import android.app.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;

public class HelloWorld {
    public static void main(String[] args) {
//        Flowable.just("Hello world").subscribe(System.out::println);
        createExample();
        justExample();
        iterableExample();
        rangeExample();
//        intervalExample();
//        timerExample();
        actionExample();
    }
    public static void createExample(){
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
    public static void justExample(){
        Observable<String> observable = Observable.just("second click 1","second click 2");
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                System.out.println(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("finished");
            }
        };
        observable.subscribe(observer);
    }
    public static void iterableExample(){
        List<String> list = new ArrayList<>();
        list.add("cat");
        list.add("dog");
        Observable<String> observable = Observable.fromIterable(list);
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(String o) {
                System.out.println(o);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("animous done");
            }
        };
        observable.subscribe(observer);
    }
    public static void rangeExample(){
        Observable<Integer> observable = Observable.range(2,5);
        observable.subscribe(i->{
            System.out.println(i);
        });
    }
    public static void intervalExample(){
        Observable<Long> observable = Observable.interval(1, TimeUnit.SECONDS);
        observable.subscribe(l->{
            System.out.println(l);
        });
        new Scanner(System.in).nextLine();
    }
    public static void timerExample(){
        Observable<Long> observable = Observable.timer(3,TimeUnit.SECONDS);
        observable.subscribe(t->{
            System.out.println(t+" seconds later");
        });
        new Scanner(System.in).nextLine();
    }
    public static void actionExample(){
        Action action = ()->{System.out.println("it's action start");};
        Completable completable = Completable.fromAction(action);
        completable.subscribe(()->System.out.println("it's action end"));
    }
}