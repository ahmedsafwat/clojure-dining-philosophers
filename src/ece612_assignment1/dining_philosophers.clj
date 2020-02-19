(ns ece612-assignment1.dining-philosophers)

(defrecord Fork
  ;This is a Fork class that has two attributes name and in-use
  [name in-use])

(comment
  ;Initializing all the forks that we need
  ;These forks will be used by the philposophers later
  )
(def fork1
  (->Fork "Fork-1" (atom 0)))
(def fork2
  (->Fork "Fork-2" (atom 0)))
(def fork3
  (->Fork "Fork-3" (atom 0)))
(def fork4
  (->Fork "Fork-4" (atom 0)))
(def fork5
  (->Fork "Fork-5" (atom 0)))

(defn print-fork-status
  "Printing the status of all the forks to help monitor the overall operation and check if there is any deadlock"
  []
  (println (str "fork1 use: " @(:in-use fork1)
  ", fork2 use: " @(:in-use fork2)
  ", fork3 use: " @(:in-use fork3)
  ", fork4 use: " @(:in-use fork4)
  ", fork5 use: " @(:in-use fork5))))


(defprotocol Action
  ;This is an interface for the actions the philiospoher can do.
  (eat [this])
  (think [this]))

(defrecord Philosopher
  ;A Philosopher class that has three attributes:
  ;name: the name of the philosopher
  ;lef-fork, and the right-fork the philiospoher will use for eating
  ;It implemnts two interfaces Action and Runnable so it can run as a separate thread
  [name left-fork right-fork]
  Action
  (eat [this]
       (let [left-fork (:left-fork this)
             right-fork (:right-fork this)]
         (locking left-fork
           (locking right-fork
             (try
               (print-fork-status) ; Printing the forks status before using them (to see which ones will be used)
               (swap! (:in-use left-fork) inc)
               (swap! (:in-use right-fork) inc)
               (println (str (:name this) " is Eating."))
               (print-fork-status) ; Printing the forks status after locking them and using them to eat
               (Thread/sleep 3000)
               (swap! (:in-use left-fork) dec)
               (swap! (:in-use right-fork) dec)
               (catch InterruptedException ie ; when interupted for shutdown reset the forks use status
                 (reset! (:in-use left-fork) 0)
                 (reset! (:in-use right-fork) 0)))))))
  (think[this]
          (println (str (:name this) " is Thinking."))
          (Thread/sleep 3000))
  Runnable
  (run [this]
       (while (not (.isInterrupted (. Thread (currentThread))))
       (do
         (think this)
         (eat this)))))


;Instantiating the philosophers

(def philosophers
  [(->Philosopher "Philosopher-1" fork1 fork2)
   (->Philosopher "Philosopher-2" fork2 fork3)
   (->Philosopher "Philosopher-3" fork3 fork4)
   (->Philosopher "Philosopher-4" fork4 fork5)
   (->Philosopher "Philosopher-5" fork1 fork5)]); switching the forks here to avoid deadlock

(defn start
  "Builds all the philosophers threads and start them all"
  []
  (let [
         threads (map #(Thread. % (:name %)) philosophers); creating the threads
        ]
    (map (fn[thread] (.start thread) thread) threads)));starting the threads

(defn -main
  "main function similar the the Java main function"
  []
   (let [threads (start)]
     (print (map #(.getName %) threads))))
