package com.example.myframeworks;

import android.annotation.SuppressLint;
import android.app.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.observables.ConnectableObservable;
import io.reactivex.rxjava3.observers.ResourceObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.AsyncSubject;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import io.reactivex.rxjava3.subjects.Subject;

import static java.lang.Thread.sleep;

public class HelloWorld {
    public static void main(String[] args) {
        /**
         * create operator
         */
//        Flowable.just("Hello world").subscribe(System.out::println);
        createExample();
        justExample();
        iterableExample();
        rangeExample();
//        intervalExample();
//        timerExample();
        actionExample();
        singleMaybeExample();
        completableExample();
        coldObservableExample();
//        try {
//            hotObservableExample();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        /**
         * filter operator example
         */
        filterOperatorExample();
        /**
         * conditional operator example
         */
        conditionalOperatorExample();
        /**
         * utilityAndErrorHandleExample
         */
        utilityAndErrorHandleExample();
        /**
         * publishSubjectExample
         */
        publishSubjectExample();
        behaviourSubjectExample();
        replaySubjectExample();
        asyncSubjectExample();
        customObservableOperator();
        threadingExample();

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

    /**
     * Single: always expect one value, when no item, we treat it as an error
     * Maybe:
     */
    public static void singleMaybeExample(){
        Single<String> single = Single.create(emmit->{
            String user = fetchUser();
            if(user!=null){
                emmit.onSuccess(user);
            } else {
                emmit.onError(new Exception("user not found"));
            }
        });
        single.subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull String s) {
                System.out.println(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println(e.getMessage());
            }
        });

        Maybe<String> maybe = Maybe.create(emitter -> {
            String fileContent = readFile();
            if(fileContent!=null){
                emitter.onSuccess(fileContent);
            } else {
                emitter.onComplete();
            }
        });
        maybe.subscribe(new MaybeObserver<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull String s) {
                System.out.println(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("it's complete");
            }
        });
    }

    private static String readFile() {
        return null;
    }

    private static String fetchUser() {
        return "Yan";
    }
    public static void completableExample(){
        Completable completable = Completable.fromAction(deleteItemFromAction());
        completable.subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {
                System.out.println("Deleting complete");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private static Action deleteItemFromAction() {
        return new Action() {
            @Override
            public void run() throws Throwable {
                System.out.println("Deleting item from DB");
            }
        };
    }
    @SuppressLint("CheckResult")
    public static void flowableExample(){
        Observable.range(1,1000)
                .map(Item::new)
                .subscribe(item -> {
                    sleep(1000);
                    System.out.println("Received my item" + item.id + '\n');
                });

        Observable.range(1,1000)
                .map(Item::new)
                .observeOn(Schedulers.io())// action work on a new thread
                .subscribe(item -> {
                    sleep(1000);
                    System.out.println("Received my item" + item.id + '\n');
                });
        Flowable.range(1,1000)
                // flowable , divide task into portions, default 128 item a portion.
                .map(Item::new)
                .observeOn(Schedulers.io())
                .subscribe(item -> {
                    sleep(1000);
                    System.out.println("Received my item" + item.id + '\n');
                });
    }

    private static class Item {
        int id;
        public Item(Integer id) {
            this.id = id;
        }
    }

    /**
     * observer 1 a
     * observer 1 b
     * observer 1 c
     * observer 2 a b c same as observer 1
     * observer 3 a b c same as observer 1
     */
    public static void coldObservableExample(){
        Observable<String> observable = Observable.just("a","b","c");
        observable.subscribe((item)->System.out.println("observer 1" + item));
        observable.subscribe((item)->System.out.println("observer 2" + item));
        observable.subscribe((item)->System.out.println("observer 3" + item));
    }

    /**
     * observer 1,sec: 0
     * observer 1,sec: 1
     * observer 1,sec: 2
     * observer 1,sec: 3
     * observer 1,sec: 4
     * observer 1,sec: 5
     * observer 2,sec: 5
     * observer 1,sec: 6
     * observer 2,sec: 6
     * observer 1,sec: 7
     * observer 2,sec: 7
     * ...
     * @throws InterruptedException
     */
    public static void hotObservableExample() throws InterruptedException {
        ConnectableObservable observable = Observable.interval(1, TimeUnit.SECONDS).publish();
        observable.connect();// this means the hot observable start to emmit items.
        observable.subscribe(item->{
            System.out.println("observer 1,sec: " + item);
        });
        Thread.sleep(5000);
        observable.subscribe(item->{
            System.out.println("observer 2,sec: " + item);
        });
        Thread.sleep(50000);
    }

    /**
     * if you don't need emissions from some stream, you should always dispose this.
     */
    public static void disposableExample(){
        // example 1
        Observable<Long> observable = Observable.interval(1, TimeUnit.SECONDS);
        Disposable disposable = observable.subscribe(item->System.out.println("item:"+ item));
        if(disposable.isDisposed()){// ?
            disposable.dispose();
        }
        // example 2
        /**
         * composite disposable is basically a collection which can hold disposable objects
         * helpful use in multi streams and you want to free up resource at once
         */
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        observable.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(@NonNull Long aLong) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        compositeDisposable.dispose();// free up all the resources, no memory leak no memory overhead
        // example 3, work with object of type resource observer
        ResourceObserver<Long> resourceObserver = new ResourceObserver<Long>() {
            @Override
            public void onNext(@NonNull Long aLong) {
                System.out.println("item"+ aLong);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(resourceObserver);
        resourceObserver.dispose();
    }
    @SuppressLint("CheckResult")
    public static void filterOperatorExample(){
        Observable.just("hello","my","cat")
                .filter(item->item.length()!=2) // emit the items satisfy this condition
                .subscribe(System.out::println);
        // prints ->
        // Hello
        // cat

        Observable.just("hello","my","cat")
                .take(2) // emit 2 items
                .subscribe(System.out::println);
        // prints ->
        // Hello
        // my

        Observable.just("hello","my","cat")
                .skip(2) // skip 2 items
                .subscribe(System.out::println);
        // prints ->
        // cat

        Observable.just("hello","hello","hello")
                .distinct() // duplicates will be filter out
                .subscribe(System.out::println);
        // prints ->
        // hello

        Observable.just("hello","hola","hay")
                .first("no item emit") // pass the first item to downstream.
                // set up the default value in case no item generate.
                .subscribe(System.out::println);
        // prints ->
        // hello

        Observable.just("hello","hola","hay")
                .last("no item emit") // pass the last item to downstream.
                // set up the default value in case no item generate.
                .subscribe(System.out::println);
        // prints ->
        // hay

    }
    @SuppressLint("CheckResult")
    public static void conditionalOperatorExample(){
        Observable.just("hello","my","cat")
                .takeWhile(item->item.length()<=3) //
                .subscribe(System.out::println);
        // prints ->
        // my
        // cat

        Observable.just(2,3,4,5)
                .skipWhile(item->item<4) //
                .subscribe(System.out::println);
        // prints ->
        // 4
        // 5

        Observable.just("jack","rain","kate")
                .all(item->item.length()==4) //
                .subscribe(System.out::println);
        // prints ->
        // true

        Observable.just("jack","rain","kate")
                .any(item->item.length()==3) //
                .subscribe(System.out::println);
        // prints ->
        // false

        Observable.just("jack","rain","kate")
                .filter(item->item.length()==3)
                .defaultIfEmpty("YxY")
                .subscribe(System.out::println);
        // prints ->
        // YxY

        Observable.just("jack","rain","kate")
                .filter(item->item.length()==3)
                .switchIfEmpty(Observable.just("hello","other","source"))
                .subscribe(System.out::println);
        // prints ->
        // hello
        // other
        // source
    }

    /**
     * transforming Observables
     * map
     * sorted
     * scan
     * buffer
     * groupBy
     * flatMap
     * toList
     */
    @SuppressLint("CheckResult")
    public static void transformingExample(){
        Observable.just(1,2,3)
                .map(item->String.valueOf(item))
                .subscribe(System.out::println);
        // prints ->
        // "1"
        // "2"
        // "3"

        Observable.just(3,1,2,4)
                .sorted()
                .subscribe(System.out::println);
        // prints ->
        // 1
        // 2
        // 3
        // 4
        Observable.just(3,1,2)
                .scan((accumulator,item)->accumulator + item)
                // when 3 is generate, accumulator=0 item = 3 sum= 3
                // when 1 is generate, accumulator=3 item = 1 sum= 4
                // when 2 is generate, accumulator=4 item = 2 sum= 6
                .subscribe(System.out::println);
        // prints ->
        // 3
        // 4
        // 6

        Observable.range(0,10)
                .buffer(3)// items will be generate in a batch of 3
                .subscribe(System.out::println);
        // prints ->
        // [0,1,2]
        // [3,4,5]
        // [6,7,8]
        // [9]

        Observable.just("a","b","ab","bc","abc","bcd")
                .groupBy(String::length)
                .flatMapSingle(Observable::toList)
                .subscribe(System.out::println);
        // prints ->
        // [a,b]
        // [ab,bc]
        // [abc,bcd]

        Observable.just(1,2,3)
                .flatMap(item->Observable.just(item*2))
                .subscribe(System.out::println);
        // prints ->
        // 2
        // 4
        // 6

        Observable.just(1,2,3)
                .toList()
                .subscribe(System.out::println);
        // prints -> [1,2,3]
    }

    /**
     * combining observables
     * mergeWith : the order depends on the emit timing, not always ob1 then ob2
     * zipWith
     */
    @SuppressLint("CheckResult")
    public static void combiningExample(){
        Observable.just(1,2)
                .mergeWith(Observable.just(3,4))
                .subscribe(System.out::println);
        // prints -> (order could be change)
        // 1
        // 2
        // 3
        // 4
        Observable obs1 = Observable.just("A","B");
        Observable obs2 = Observable.just("C","D");
        obs1.zipWith(obs2,(item1,item2)->{
            return String.format("%s%s",item1,item2);
        }).subscribe(finalResult->System.out.println("item: " + finalResult));
        // prints ->
        // item: AC
        // item: BD

    }

    /**
     * --utility ErrorHandle
     * delay
     * timeout
     * observeOn
     * subscribeOn
     * doOnNext
     * doOnDisposes
     * --error handle
     * retry
     * onErrorReturnItem
     * onErrorResumeWith
     */
    @SuppressLint("CheckResult")
    public static void utilityAndErrorHandleExample(){
        Observable.just("after xx seconds")
                .delay(2,TimeUnit.SECONDS)
                .subscribe(System.out::println);
        // prints after 2 seconds ->
        // after xx seconds
        /**
         * timeout is useful when you want specific maximum amount time between emission items
         */
        Observable.just("timeout instance")
                .timeout(1,TimeUnit.SECONDS) // in here, if the observable not emit anything in 1 seconds,
                // onError method will be called.
                .subscribe(System.out::println);
        // prints ->
        // timeout instance

        /**
         * observeOn change the thread on which we subscribe item
         */
        System.out.println(Thread.currentThread().getName());
        Observable.just("observeOn instance")
                .observeOn(Schedulers.newThread())
                .subscribe(item->{
                    System.out.println(Thread.currentThread().getName());
                    System.out.println(item);
                });
        // prints ->
        // main
        // RxNewThreadScheduler-1
        // observeOn instance
        /**
         * subscribeOn change the thread on which we emit items
         */
        System.out.println(Thread.currentThread().getName());
        Observable.just("subscribeOn instance")
                .subscribeOn(Schedulers.newThread())
                .subscribe(item->{
                    System.out.println(Thread.currentThread().getName());
                    System.out.println(item);
                });
        // prints ->
        // main
        // RxNewThreadScheduler-1
        // subscribeOn instance

        /**
         * emit 1, doOnNext 1
         */
        Observable.just(1,2,2)
                .doOnNext(item->System.out.println("Log some info"))
                .filter(item->item==2)
                .subscribe(System.out::println);
        // prints ->
        // Log some info
        // Log some info
        // 2
        // Log some info
        // 2
        /** Disposable
         * release no longer need resources here
         */
        Disposable disposable = Observable.timer(1,TimeUnit.SECONDS)
                .doOnDispose(()->System.out.println("Disposed Called"))
                .subscribe(System.out::println);
        disposable.dispose();
        // prints ->
        // Disposed Called

        /**
         * retry
         */
        Observable.just(2,1,0)
                .map(item->2/item)
                .retry(1)// retry the time when face error
                .subscribe(item-> System.out.println(item),
                        throwable -> System.out.println(throwable.getMessage()));
        // prints ->
        // 1
        // 2
        // 1
        // 2
        // /divide by zero
        /**
         * onErrorReturnItem
         * when error, the item behind be dropped out
         */
        Observable.just(2,1,0,1)
                .map(item->2/item)
                .onErrorReturnItem(-1)
                .subscribe(System.out::println);
        // prints ->
        // 1
        // 2
        // -1
        /**
         * onErrorResumeWith
         * when error, the item behind be dropped out
         */
        Observable.just(2,1,0,2)
                .map(item->2/item)
                .onErrorResumeWith(Observable.just(5,6,7))
                .subscribe(System.out::println);
        // prints ->
        // 1
        // 2
        // 5
        // 6
        // 7

    }

    /**
     * received item: 0
     * received item: 0
     * received item: 1
     * received item: 1
     * received item: 2
     * received item: 2
     * received item: 3
     * received item: 3
     * received item: 4
     * received item: 4
     */
    public static void publishSubjectExample(){
        // create two sources
        Observable<Long> source1 = Observable.interval(1,TimeUnit.SECONDS);
        Observable<Long> source2 = Observable.interval(1,TimeUnit.SECONDS);
        // create PublishSubject Object
        Subject<Long> subject = PublishSubject.create();
        // subscribe to the PublishSubject object
        subject.subscribe(item->System.out.println("received item: " + item));
        // still need to tell the subject that it will receive items from two different sources
        source1.subscribe(subject);
        source2.subscribe(subject);

        // prevent program from exiting
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * achieve only latest item
     * person 1 listen song: 1
     * person 1 listen song: 2
     * person 1 listen song: 3
     * person 1 listen song: 4
     * person 2 listen song: 4
     * person 1 listen song: 5
     * person 2 listen song: 5
     * person 1 listen song: 6
     * person 2 listen song: 6
     * person 3 listen song: 6
     */
    @SuppressLint("CheckResult")
    public static void behaviourSubjectExample(){
        // represent radio play in room
        Subject<Integer> subject = BehaviorSubject.create();
        // person 1 enter the room and start listen song
        subject.subscribe(item->System.out.println("person 1 listen song: " + item));
        subject.onNext(1);
        subject.onNext(2);
        subject.onNext(3);
        subject.onNext(4);
        // person 2 enter the room and start listen song
        subject.subscribe(item->System.out.println("person 2 listen song: " + item));
        subject.onNext(5);
        subject.onNext(6);
        subject.subscribe(item->System.out.println("person 3 listen song: " + item));

    }

    /**
     * receiver will get all the items no matter when subscribed
     * student A received : Music
     * student A received : Movie
     * student A received : Novice
     * student B received : Music
     * student B received : Movie
     * student B received : Novice
     */
    public static void replaySubjectExample(){
        // represent teacher
        Subject<String> subject = ReplaySubject.create();
        // student A entered the classroom
        subject.subscribe(item->System.out.println("student A received : " + item));
        // teacher talks about some important topics
        subject.onNext("Music");
        subject.onNext("Movie");
        subject.onNext("Novice");
        // student B entered the classroom late
        subject.subscribe(item->System.out.println("student B received : " + item));
    }

    /**
     * only get the last item
     * Async student A received : Async science
     * Async student B received : Async science
     */
    public static void asyncSubjectExample(){
        // represent teacher
        Subject<String> subject = AsyncSubject.create();
        // student A entered the classroom
        subject.subscribe(item->System.out.println("Async student A received : " + item));
        // teacher talks about some important topics
        subject.onNext("Async Music");
        subject.onNext("Async Movie");
        subject.onNext("Async Novice");
        // student B entered the classroom late
        subject.subscribe(item->System.out.println("Async student B received : " + item));
        subject.onNext("Async math");
        subject.onNext("Async science");
        // teacher ends the class
        subject.onComplete();
    }

    /**
     * custom observable operator
     */
    public static void customObservableOperator(){

        ObservableOperator<Integer,Integer> takeEven = new ObservableOperator<Integer, Integer>() {
            @Override
            public @NonNull Observer<? super Integer> apply(@NonNull Observer<? super Integer> observer) throws Throwable {
                return new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        if(integer%2==0){
                            observer.onNext(integer);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                };
            }
        };
        Observable.just(1,2,3,4)
                .lift(takeEven)
                .subscribe(item->System.out.println("lift my even: " + item));
    }

    /**
     * subscribeOn
     * observeOn
     * when main thread down, the 1onRxComputationThreadPool also down
     * --- when only --.subscribeOn(Schedulers.computation())
     * pushing item 1onRxComputationThreadPool-6 Thread
     * receiving item 1onRxComputationThreadPool-6 Thread
     * pushing item 2onRxComputationThreadPool-6 Thread
     * receiving item 2onRxComputationThreadPool-6 Thread
     * pushing item 3onRxComputationThreadPool-6 Thread
     * receiving item 3onRxComputationThreadPool-6 Thread
     * --- when both , and observeOn after doOnNext
     * pushing item 1onRxComputationThreadPool-6 Thread
     * pushing item 2onRxComputationThreadPool-6 Thread
     * pushing item 3onRxComputationThreadPool-6 Thread
     * receiving item 1onRxSingleScheduler-1 Thread
     * receiving item 2onRxSingleScheduler-1 Thread
     * receiving item 3onRxSingleScheduler-1 Thread
     * ---- when only .observeOn(Schedulers.single())
     * pushing item 1 on RxSingleScheduler-1 Thread
     * receiving item 1 on RxSingleScheduler-1 Thread
     * pushing item 2 on RxSingleScheduler-1 Thread
     * receiving item 2 on RxSingleScheduler-1 Thread
     * pushing item 3 on RxSingleScheduler-1 Thread
     * receiving item 3 on RxSingleScheduler-1 Thread
     */
    @SuppressLint("CheckResult")
    public static void threadingExample(){
        // subscribe
        Observable.just(1,2,3)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.single())
                .doOnNext(item->System.out.println("pushing item " + item + " on " + Thread.currentThread().getName() + " Thread"))
                .subscribe(item->System.out.println("receiving item " + item + " on " + Thread.currentThread().getName() + " Thread"));

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}